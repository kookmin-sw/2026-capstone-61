CREATE OR REPLACE PROCEDURE proc_accept_match (
    p_applyno IN NUMBER
) AS

    -- =====================================================
    -- 기본 변수
    -- =====================================================
    v_matchno      NUMBER;
    v_host_id      VARCHAR2(50);
    v_applier_id   VARCHAR2(50);

    v_new_room_no  NUMBER;

    v_status       NUMBER;
    v_exist_cnt    NUMBER;

    -- =====================================================
    -- 게시글 정보
    -- =====================================================
    v_title        VARCHAR2(200);
    v_mplace       VARCHAR2(200);
    v_mdate        VARCHAR2(100);

BEGIN

    ----------------------------------------------------------------
    -- 1. 신청 상태 확인
    ----------------------------------------------------------------
    SELECT apply_status
      INTO v_status
      FROM match_apply
     WHERE applyno = p_applyno;

    -- 이미 수락된 경우
    IF v_status = 2 THEN

        RAISE_APPLICATION_ERROR(
            -20001,
            '이미 수락된 신청입니다.'
        );

    END IF;

    ----------------------------------------------------------------
    -- 2. 신청 정보 + 게시글 정보 조회
    ----------------------------------------------------------------
    SELECT
           a.matchno,
           m1.id,
           m2.id,
           p.title,
           p.mplace,
           TO_CHAR(
               p.mdate,
               'YYYY-MM-DD HH24:MI'
           )

      INTO
           v_matchno,
           v_host_id,
           v_applier_id,
           v_title,
           v_mplace,
           v_mdate

      FROM match_apply a

      JOIN match_post p
        ON a.matchno = p.matchno

      JOIN member m1
        ON p.memberno = m1.memberno

      JOIN member m2
        ON a.memberno = m2.memberno

     WHERE a.applyno = p_applyno;

    ----------------------------------------------------------------
    -- 3. 신청 수락 처리
    ----------------------------------------------------------------

    -- 선택된 신청 수락
    UPDATE match_apply
       SET apply_status = 2,
           success_yn = 1,
           accept_date = SYSDATE
     WHERE applyno = p_applyno;

    -- 다른 신청 자동 거절
    UPDATE match_apply
       SET apply_status = 3,
           cancel_date = SYSDATE
     WHERE matchno = v_matchno
       AND applyno != p_applyno
       AND apply_status = 1;

    -- 모집글 상태 변경
    -- 1 모집중
    -- 2 모집마감
    UPDATE match_post
       SET status = 2,
           current_member = current_member + 1
     WHERE matchno = v_matchno;

    ----------------------------------------------------------------
    -- 4. 기존 채팅방 존재 여부 확인
    ----------------------------------------------------------------
    SELECT COUNT(*)
      INTO v_exist_cnt
      FROM (
            SELECT room_no
              FROM messenger_member
             WHERE user_id = v_host_id

            INTERSECT

            SELECT room_no
              FROM messenger_member
             WHERE user_id = v_applier_id
      );

    ----------------------------------------------------------------
    -- 5. 채팅방 생성
    ----------------------------------------------------------------
    IF v_exist_cnt = 0 THEN

        -- 새 채팅방 번호
        v_new_room_no := messenger_room_seq.NEXTVAL;

        -- 채팅방 생성
        INSERT INTO messenger_room (
            room_no,
            rdate
        )
        VALUES (
            v_new_room_no,
            SYSDATE
        );

        -- 호스트 추가
        INSERT INTO messenger_member (
            member_no,
            room_no,
            user_id
        )
        VALUES (
            messenger_member_seq.NEXTVAL,
            v_new_room_no,
            v_host_id
        );

        -- 신청자 추가
        INSERT INTO messenger_member (
            member_no,
            room_no,
            user_id
        )
        VALUES (
            messenger_member_seq.NEXTVAL,
            v_new_room_no,
            v_applier_id
        );

    ELSE

        ----------------------------------------------------------------
        -- 기존 채팅방 조회
        ----------------------------------------------------------------
        SELECT room_no
          INTO v_new_room_no
          FROM (
                SELECT room_no
                  FROM messenger_member
                 WHERE user_id = v_host_id

                INTERSECT

                SELECT room_no
                  FROM messenger_member
                 WHERE user_id = v_applier_id
          )
         WHERE ROWNUM = 1;

    END IF;

    ----------------------------------------------------------------
    -- 6. 시스템 메시지 자동 전송
    ----------------------------------------------------------------
    INSERT INTO messenger_data (
        msg_no,
        room_no,
        sender_id,
        content,
        mdate,
        is_read
    )
    VALUES (
        messenger_data_seq.NEXTVAL,
        v_new_room_no,
        'SYSTEM',

        '🎉 산책 매칭이 성사되었습니다!'
        || CHR(10) ||

        '📌 제목 : '
        || v_title
        || CHR(10) ||

        '📍 장소 : '
        || v_mplace
        || CHR(10) ||

        '🕒 시간 : '
        || v_mdate
        || CHR(10) ||

        '채팅으로 상세 내용을 정해보세요 🐶',

        SYSDATE,

        'N'
    );

    ----------------------------------------------------------------
    -- 커밋
    ----------------------------------------------------------------
    COMMIT;

EXCEPTION

    ----------------------------------------------------------------
    -- 예외 처리
    ----------------------------------------------------------------
    WHEN OTHERS THEN

        ROLLBACK;

        RAISE;

END;
/


