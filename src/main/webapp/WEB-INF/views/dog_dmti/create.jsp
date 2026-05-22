<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>DMTI 검사하기</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <style>
        .question-card { margin-bottom: 30px; padding: 20px; border-radius: 15px; background: #f8f9fa; }
        .q-title { font-weight: bold; margin-bottom: 15px; }
    </style>
</head>
<body>

<div class="container mt-5" style="max-width: 700px;">
    <h2 class="text-center mb-5">🐾 우리 강아지 성향 검사 (DMTI)</h2>
    
    <form name="frm" id="frm" method="post" action="/dog_dmti/create.do">
        <input type="hidden" name="dogNo" value="${param.dogNo}">
        
        <input type="hidden" name="ePer" id="ePer">
        <input type="hidden" name="sPer" id="sPer">
        <input type="hidden" name="aPer" id="aPer">
        <input type="hidden" name="oPer" id="oPer">

        <div class="question-card shadow-sm">
            <p class="q-title text-danger">1. 새로운 장소에 갔을 때 우리 강아지는?</p>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="e1" value="25" required>
                <label class="form-check-label">꼬리를 흔들며 이곳저곳 신나게 탐색한다 (외향)</label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="e1" value="0">
                <label class="form-check-label">주인 곁을 떠나지 않고 경계하며 천천히 움직인다 (내향)</label>
            </div>
        </div>

        <div class="question-card shadow-sm">
            <p class="q-title text-warning">2. 다른 강아지가 다가왔을 때 우리 강아지는?</p>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="s1" value="25" required>
                <label class="form-check-label">먼저 다가가 코를 맞대며 인사한다 (사교)</label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="s1" value="0">
                <label class="form-check-label">무관심하거나 혼자만의 시간을 보낸다 (독립)</label>
            </div>
        </div>

        <div class="question-card shadow-sm">
            <p class="q-title text-info">3. 주인이 귀가했을 때 우리 강아지의 반응은?</p>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="a1" value="25" required>
                <label class="form-check-label">몸 전체를 흔들며 격하게 반겨준다 (애정)</label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="a1" value="0">
                <label class="form-check-label">슬쩍 쳐다보거나 꼬리만 살짝 흔들고 제자리에 있는다 (이성)</label>
            </div>
        </div>

        <div class="question-card shadow-sm">
            <p class="q-title text-success">4. '앉아', '기다려' 같은 명령을 내렸을 때?</p>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="o1" value="25" required>
                <label class="form-check-label">간식이 없어도 곧잘 따라하며 집중한다 (복종)</label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="o1" value="0">
                <label class="form-check-label">하고 싶을 때만 하거나 딴청을 피운다 (자유)</label>
            </div>
        </div>

        <div class="d-grid gap-2 mt-5 mb-5">
            <button type="button" onclick="send()" class="btn btn-primary btn-lg">분석 결과 제출하기</button>
        </div>
    </form>
</div>

<script>
    function send() {
        // 1. 필수 체크 여부 확인
        let forms = document.getElementById('frm');
        if (!forms.checkValidity()) {
            alert('모든 문항에 답변해주세요!');
            return;
        }

        // 2. 점수 합산 (예시: 각 지표당 문항이 4개라면 25점씩 배정)
        // 여기서는 문항이 1개씩이므로 선택된 값을 그대로 %로 활용 (나중에 문항을 늘리면 합산 로직 추가)
        let e_val = $('input[name="e1"]:checked').val();
        let s_val = $('input[name="s1"]:checked').val();
        let a_val = $('input[name="a1"]:checked').val();
        let o_val = $('input[name="o1"]:checked').val();

        // 3. 히든 필드에 값 세팅 (100점 만점 기준 환산 필요)
        // 현재는 문항이 하나라 25점이 만점이라면 4를 곱하는 식의 로직이 들어갈 수 있음
        document.getElementById('ePer').value = parseInt(e_val) * 4; // 임시로 100% 환산
        document.getElementById('sPer').value = parseInt(s_val) * 4;
        document.getElementById('aPer').value = parseInt(a_val) * 4;
        document.getElementById('oPer').value = parseInt(o_val) * 4;

        // 4. 전송
        document.frm.submit();
    }
</script>

</body>
</html>