package dev.mvc.match_post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import dev.mvc.match_apply.MatchApplyProcInter;

@Component
public class MatchScheduler {

  @Autowired
  @Qualifier("dev.mvc.match_apply.MatchApplyProc")
  private MatchApplyProcInter matchApplyProc;

  /**
   * 1분마다 실행
   *
   * apply_status = 2 (매칭완료)
   *
   * 약속 시간이 되면
   * apply_status = 3 (산책중)
   */
  @Scheduled(fixedRate = 60000)
  public void autoStartWalk() {

    int cnt = this.matchApplyProc.auto_start_walk();

    if(cnt > 0) {
      System.out.println("-> 자동 산책 시작 처리 완료: " + cnt);
    }

  }

}