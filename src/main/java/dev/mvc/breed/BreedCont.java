package dev.mvc.breed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.mvc.breed_image.Breed_imageVO;
import dev.mvc.breed_image.Breed_imageProcInter;
import dev.mvc.category.CategoryProcInter;
import dev.mvc.category.CategoryVO;
import dev.mvc.member.MemberProcInter;
import dev.mvc.tool.Tool;
import dev.mvc.tool.Upload;

@RequestMapping(value = "/breed")
@Controller
public class BreedCont {

  @Autowired
  @Qualifier("dev.mvc.member.MemberProc")
  private MemberProcInter memberProc;

  @Autowired
  @Qualifier("dev.mvc.category.CategoryProc")
  private CategoryProcInter categoryProc;

  @Autowired
  @Qualifier("dev.mvc.breed.BreedProc")
  private BreedProcInter breedProc;

  @Autowired
  @Qualifier("dev.mvc.breed_image.Breed_imageProc")
  private Breed_imageProcInter breed_imageProc;

  public BreedCont() {
    System.out.println("-> BreedCont created.");
  }

  /**
   * 메시지 출력 및 리다이렉트 지원
   * 배포 환경에서 url 앞에 /가 붙어 넘어오는 경우를 대비해 처리
   */
  @GetMapping(value = "/msg")
  public String msg(Model model, String url) {
    if (url != null && url.startsWith("/")) {
        url = url.substring(1); // 앞의 / 제거
    }
    return url; 
  }

  /**
   * 카테고리별 목록 + 검색 + 페이징
   */
  @GetMapping(value = "/list_by_cat_no")
  public String list_by_cat_no(HttpSession session, Model model, 
      @RequestParam(name = "cat_no", defaultValue = "0") int cat_no,
      @RequestParam(name = "word", defaultValue = "") String word,
      @RequestParam(name = "now_page", defaultValue = "1") int now_page) {

    CategoryVO categoryVO = this.categoryProc.read(cat_no);
    model.addAttribute("categoryVO", categoryVO);

    HashMap<String, Object> map = new HashMap<>();
    map.put("cat_no", cat_no);
    map.put("word", word.trim());
    map.put("now_page", now_page);

    // ❌ 기존 코드 (주석 처리) : 페이징 6개 제한 조건이 있던 기존 호출문
    // ArrayList<BreedVO> list = this.breedProc.list_by_cat_no_search_paging(map);

    // ➔ 변경 코드 (적용) : 페이징 없이 해당 카테고리의 검색된 전체 리스트를 가져옵니다.
    ArrayList<BreedVO> list = this.breedProc.list_by_cat_no_search(map);

    // 각 품종별 메인 이미지 매핑 과정은 그대로 유지
    for (BreedVO breedVO : list) {
        Breed_imageVO mainImg = this.breed_imageProc.read_main_img(breedVO.getBreedno());
        breedVO.setMainImg(mainImg);
    }
    
    model.addAttribute("list", list);

    // 전체 개수 산출은 유지하되, 하단 페이징 바 생성 로직은 빈 문자열 처리하거나 제거합니다.
    int search_count = this.breedProc.list_by_cat_no_search_count(map);
    
    // ➔ 하단 페이징 버튼(1, 2, 3...) 조립 박스를 화면에 출력하지 않도록 빈 값 처리
    String paging = ""; 
        
    model.addAttribute("paging", paging);
    model.addAttribute("now_page", now_page);
    model.addAttribute("search_count", search_count);

    return "breed/list_by_cat_no"; 
  }

  /**
   * 조회 (상세 정보)
   */
  @GetMapping(value = "/read")
  public String read(Model model, int breedno, 
      @RequestParam(name="word", defaultValue="") String word, 
      @RequestParam(name="now_page", defaultValue="1") int now_page) {
      
      BreedVO breedVO = this.breedProc.read(breedno);
      
      if (breedVO == null) {
          return "redirect:/breed/list_by_cat_no"; 
      }
      
      List<Breed_imageVO> imgList = this.breed_imageProc.list_by_breedno(breedno);
      model.addAttribute("imgList", imgList);
      model.addAttribute("breedVO", breedVO);

      CategoryVO categoryVO = this.categoryProc.read(breedVO.getCat_no());
      model.addAttribute("categoryVO", categoryVO);

      this.breedProc.update_viewcnt(breedno);

      model.addAttribute("word", word);
      model.addAttribute("now_page", now_page);

      return "breed/read"; // 슬래시 제거 확인
  }

  /**
   * 사진 수정 폼
   */
  @GetMapping(value = "/update_file")
  public String update_file(HttpSession session, Model model, int breedno,
                            @RequestParam(name="word", defaultValue="") String word, 
                            @RequestParam(name="now_page", defaultValue="1") int now_page) {
    if (this.memberProc.isMemberAdmin(session)) {
      BreedVO breedVO = this.breedProc.read(breedno);
      model.addAttribute("breedVO", breedVO);
      
      CategoryVO categoryVO = this.categoryProc.read(breedVO.getCat_no());
      model.addAttribute("categoryVO", categoryVO);

      List<Breed_imageVO> imgList = this.breed_imageProc.list_by_breedno(breedno); 
      model.addAttribute("imgList", imgList); 

      model.addAttribute("word", word);
      model.addAttribute("now_page", now_page);

      return "breed/update_file"; // 슬래시 제거 확인
    } else {
      return "redirect:/member/login";
    }
  }

  /**
   * 사진 수정 처리
   */
  @PostMapping(value = "/update_file")
  public String update_file_proc(
          HttpSession session, 
          BreedVO breedVO, 
          @RequestParam("file1MF") List<MultipartFile> file1MF, 
          int now_page, 
          String word, 
          RedirectAttributes ra) {
      
      if (this.memberProc.isMemberAdmin(session)) {
          int breedno = breedVO.getBreedno();
          
          String upDir = Breed.getUploadDir();

          List<Breed_imageVO> oldImgList = this.breed_imageProc.list_by_breedno(breedno);
          for (Breed_imageVO oldImg : oldImgList) {
              Tool.deleteFile(upDir, oldImg.getSaved_name()); 
          }
          this.breed_imageProc.delete_by_breedno(breedno); 

          for (MultipartFile mf : file1MF) {
              if (mf != null && mf.getSize() > 0) {
                  String saved_name = Upload.saveFileSpring(mf, upDir);
                  String file_name = mf.getOriginalFilename();
                  long file_size = mf.getSize();
                  
                  Breed_imageVO breed_imageVO = new Breed_imageVO();
                  breed_imageVO.setBreedno(breedno);
                  breed_imageVO.setOriginal_name(file_name);
                  breed_imageVO.setSaved_name(saved_name);
                  breed_imageVO.setF_size(file_size); 
                  breed_imageVO.setFile_path("-"); 
                  breed_imageVO.setThumb_path("-"); 

                  this.breed_imageProc.create(breed_imageVO);
              }
          }

          ra.addAttribute("breedno", breedno);
          ra.addAttribute("now_page", now_page);
          ra.addAttribute("word", word);
          
          return "redirect:/breed/read"; 
      } else {
          return "redirect:/member/login";
      }
  }
 
  /**
   * 정보 수정 폼
   */
  @GetMapping(value = "/update_text")
  public String update_text(HttpSession session, Model model, int breedno, RedirectAttributes ra) {
    if (this.memberProc.isMemberAdmin(session)) {
      BreedVO breedVO = this.breedProc.read(breedno);
      model.addAttribute("breedVO", breedVO);
      
      CategoryVO categoryVO = this.categoryProc.read(breedVO.getCat_no());
      model.addAttribute("categoryVO", categoryVO);

      return "breed/update_text"; // 슬래시 제거 확인
    } else {
      return "redirect:/member/login";
    }
  }

  /**
   * 수정 처리
   */
  @PostMapping(value = "/update_text")
  public String update_text(HttpSession session, BreedVO breedVO, RedirectAttributes ra) {
    if (this.memberProc.isMemberAdmin(session)) {
      this.breedProc.update_text(breedVO);
      ra.addAttribute("breedno", breedVO.getBreedno());
      return "redirect:/breed/read"; 
    } else {
      return "redirect:/member/login";
    }
  }

  /**
   * 삭제 처리
   */
  @PostMapping(value = "/delete")
  public String delete(HttpSession session, int breedno, int cat_no, RedirectAttributes ra) {
    if (this.memberProc.isMemberAdmin(session)) {
      this.breedProc.delete(breedno);
      ra.addAttribute("cat_no", cat_no);
      return "redirect:/breed/list_by_cat_no";
    } else {
      return "redirect:/member/login";
    }
  }

  /**
   * 추천 기능
   */
  @GetMapping(value = "/recom")
  @ResponseBody
  public String recom(int breedno) {
    this.breedProc.recom(breedno);
    BreedVO breedVO = this.breedProc.read(breedno);
    return "{\"cnt\":" + breedVO.getRecom() + "}";
  }
}