package dev.mvc.community;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import dev.mvc.attachfile.AttachfileProcInter; 
import dev.mvc.attachfile.AttachfileVO;
import dev.mvc.tool.Tool;
import dev.mvc.tool.Upload;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/community")
public class CommunityCont {
  
  @Autowired
  @Qualifier("dev.mvc.community.CommunityProc")
  private CommunityProcInter communityProc;

  /** 사진 정보 저장 및 조회를 위한 의존성 주입 */
  @Autowired
  @Qualifier("dev.mvc.attachfile.AttachfileProc")
  private AttachfileProcInter attachfileProc;

  /**
   * [갤러리 목록] 검색, 페이징, 게시판 분류(cateno) 포함
   */
  @GetMapping("/list")
  public String list(Model model, 
                     @RequestParam(name="cat_no", defaultValue="1") int cat_no, 
                     @RequestParam(name="cateno", defaultValue="0") int cateno, // [추가] 0:전체, 1~4:분류
                     @RequestParam(name="word", defaultValue="") String word,
                     @RequestParam(name="now_page", defaultValue="1") int now_page) {
    
    HashMap<String, Object> map = new HashMap<>();
    map.put("cat_no", cat_no);
    map.put("cateno", cateno); // [추가] MyBatis로 전달할 맵에 저장
    map.put("word", word.trim());
    
    // 갤러리형은 한 줄에 3개씩 배치되므로 9개 단위가 적당함
    int record_per_page = 9; 
    int start_num = ((now_page - 1) * record_per_page) + 1;
    int end_num = now_page * record_per_page;
    
    map.put("start_num", start_num);
    map.put("end_num", end_num);

    // SQL에서 cateno 필터링이 적용된 리스트를 가져옴
    ArrayList<CommunityVO> list = this.communityProc.list_by_community_search_paging(map);
    int search_count = this.communityProc.search_count(map);

    // 뷰(HTML)에서 쓰기 위해 모델에 담기
    model.addAttribute("list", list);
    model.addAttribute("cat_no", cat_no);
    model.addAttribute("cateno", cateno); // [추가] HTML 탭의 'active' 클래스 결정용
    model.addAttribute("word", word);
    model.addAttribute("search_count", search_count);
    model.addAttribute("now_page", now_page);

    // 페이징 버튼 HTML 생성 (생략된 기존 로직이 있다면 여기에 포함)
    // String paging = this.communityProc.pagingBox(cat_no, cateno, now_page, word);
    // model.addAttribute("paging", paging);

    return "community/list_by_community"; 
  }
  /**
   * [등록 폼] 카테고리 정보를 유지하며 이동
   */
  @GetMapping("/create")
  public String create(Model model, 
                       @RequestParam(name="cat_no") int cat_no, 
                       @RequestParam(name="breedno", defaultValue="0") int breedno) {
    model.addAttribute("cat_no", cat_no);
    model.addAttribute("breedno", breedno);
    return "community/create";
  }

  /**
   * [등록 처리] 세션 정보 추출 및 Community 설정 클래스 반영
   */
  @PostMapping("/create")
  public String create(HttpSession session, 
                       CommunityVO communityVO, 
                       List<MultipartFile> fnamesMF) { 

      // -------------------------------------------------------------------
      // 1. 세션 체크 및 기본 정보 세팅 (ORA-01400 에러 해결 구간)
      // -------------------------------------------------------------------
      Object memberno_obj = session.getAttribute("memberno");
      Object nickname_obj = session.getAttribute("nickname"); // 세션에서 닉네임 추출

      if (memberno_obj == null || nickname_obj == null) {
          // 로그인 정보가 없으면 로그인 페이지로 리다이렉트
          return "redirect:/member/login"; 
      }
      
      int memberno = (int)memberno_obj;
      String nickname = (String)nickname_obj;
      
      // [중요] VO에 작성자 정보가 담겨야 DB의 NOT NULL 제약조건을 통과합니다.
      communityVO.setMemberno(memberno);
      communityVO.setNickname(nickname); 

      // -------------------------------------------------------------------
      // 2. 게시글 본문 저장
      // -------------------------------------------------------------------
      int cnt = this.communityProc.create(communityVO);
      
      if (cnt == 1) { 
          int communityno = communityVO.getCommunityno(); 
          
          // 3. 파일 리스트 반복 처리
          for (MultipartFile multipartFile : fnamesMF) {
              if (multipartFile.getSize() > 0) { 
                  
                  // A. 운영체제별 저장 경로 산출 (설정 클래스 사용)
                  // 패키지 경로에 주의하세요 (dev.mvc.community.Community)
                  String storagePath = dev.mvc.community.Community.getUploadDir();
                  
                  // B. 물리적 파일 저장 (Upload.java 사용)
                  String fupname = Upload.saveFileSpring(multipartFile, storagePath);
                  
                  if (fupname != null && fupname.length() > 0) { 
                      // C. 썸네일 생성 (Tool.java 사용)
                      String thumb = Tool.preview(storagePath, fupname, 200, 150);
                      
                      // D. AttachfileVO 세팅 및 DB 저장
                      AttachfileVO attachfileVO = new AttachfileVO();
                      attachfileVO.setCommunityno(communityno);
                      attachfileVO.setFname(multipartFile.getOriginalFilename());
                      attachfileVO.setFupname(fupname);
                      attachfileVO.setThumb(thumb);
                      attachfileVO.setFsize(multipartFile.getSize());

                      this.attachfileProc.create(attachfileVO);
                  }
              }
          }
      }
      return "redirect:/community/list?cat_no=" + communityVO.getCat_no();
  }
  /**
   * [삭제 처리] DB 레코드 및 관련 파일 관리
   * int breedno -> Integer breedno로 변경하여 null 허용
   */
  @PostMapping("/delete") 
  public String delete_proc(
      @RequestParam(name="communityno") int communityno, 
      @RequestParam(name="cat_no") int cat_no, 
      @RequestParam(name="breedno", defaultValue="0") Integer breedno) { // Integer로 변경 및 기본값 0 설정
      
      // ON DELETE CASCADE 설정으로 관련 attachfile 레코드는 자동 삭제됨
      this.communityProc.delete(communityno);
      
      // breedno가 null일 경우를 대비해 안전하게 리다이렉트
      return "redirect:/community/list?cat_no=" + cat_no + "&breedno=" + (breedno == null ? 0 : breedno);
  }
  /**
   * [수정 폼] 기존 글의 정보와 사진 목록을 가져와서 출력
   */
  @GetMapping("/update")
  public String update(int communityno, Model model) {
    // 1. 기존 게시글 텍스트 정보 읽기
    CommunityVO communityVO = this.communityProc.read(communityno);
    
    // 2. 기존 등록된 사진 목록 읽기 (수정 페이지에서 보여주기 위함)
    List<AttachfileVO> fileList = this.attachfileProc.list_by_communityno(communityno);
    
    model.addAttribute("communityVO", communityVO);
    model.addAttribute("fileList", fileList);
    
    return "community/update"; // community/update.html 실행
  }
  @PostMapping("/update")
  public String update_proc(HttpSession session, 
                            CommunityVO communityVO, 
                            List<MultipartFile> fnamesMF) {
      
      // 1. 기존 게시글의 텍스트 정보(제목, 내용 등) 업데이트
      int cnt = this.communityProc.update(communityVO);
      
      if (cnt == 1) { // 텍스트 수정 성공 시
          int communityno = communityVO.getCommunityno();
          
          // 2. 추가로 업로드된 파일이 있는지 확인
          for (MultipartFile multipartFile : fnamesMF) {
              if (!multipartFile.isEmpty()) { // 파일이 전송되었다면
                  
                  String storagePath = Community.getUploadDir();
                  // 파일 저장 및 물리적 파일 생성 (기존 create 로직 재사용)
                  String fupname = Upload.saveFileSpring(multipartFile, storagePath);
                  
                  if (fupname != null) {
                      String thumb = Tool.preview(storagePath, fupname, 200, 150);
                      
                      // AttachfileVO 생성 및 DB 저장
                      AttachfileVO attachfileVO = new AttachfileVO();
                      attachfileVO.setCommunityno(communityno);
                      attachfileVO.setFname(multipartFile.getOriginalFilename());
                      attachfileVO.setFupname(fupname);
                      attachfileVO.setThumb(thumb);
                      attachfileVO.setFsize(multipartFile.getSize());

                      this.attachfileProc.create(attachfileVO);
                  }
              }
          }
      }
      
      // 수정 완료 후 상세 페이지로 이동
      return "redirect:/community/read?communityno=" + communityVO.getCommunityno();
  }
  /**
   * [상세 조회] 글 본문과 모든 첨부 사진 목록을 출력
   */
  @GetMapping("/read")
  public String read(int communityno, Model model) {
    // 텍스트 본문 정보
    CommunityVO communityVO = this.communityProc.read(communityno);
    
    // 해당 글에 딸린 모든 사진 목록 (상세페이지 슬라이드나 리스트용)
    List<AttachfileVO> fileList = this.attachfileProc.list_by_communityno(communityno);
    
    model.addAttribute("communityVO", communityVO);
    model.addAttribute("fileList", fileList); 
    
    return "community/read";
  }
}