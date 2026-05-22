 package dev.mvc.member;

import dev.mvc.tool.Tool;

public class Member {
  /** 페이지당 출력할 레코드 갯수 */
  public static int RECORD_PER_PAGE = 10;

  /** 블럭당 페이지 수, 하나의 블럭은 10개의 페이지로 구성됨 */
  public static int PAGE_PER_BLOCK = 10;

  // Windows, VMWare, AWS cloud 절대 경로 설정
  public static synchronized String getUploadDir() {
      return Tool.getUploadPath("member", "storage");
  }

}
