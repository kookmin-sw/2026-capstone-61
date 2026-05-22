package dev.mvc.category;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import dev.mvc.member.MemberProcInter;
import dev.mvc.tool.Tool;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RequestMapping("/category")
@Controller
public class CategoryCont {

  @Autowired
  @Qualifier("dev.mvc.category.CategoryProc")
  private CategoryProcInter categoryProc;

  @Autowired
  @Qualifier("dev.mvc.member.MemberProc")
  private MemberProcInter memberProc;

  public int record_per_page = Category.RECORD_PER_PAGE;
  public int page_per_block = Category.PAGE_PER_BLOCK;

  public CategoryCont() {
    System.out.println("-> CategoryCont created.");
  }

  /** 등록 처리 */
  @PostMapping("/create")
  public String create(HttpSession session, Model model,
      @Valid CategoryVO categoryVO, BindingResult bindingResult,
      @RequestParam(name="word", defaultValue="") String word,
      @RequestParam(name="now_page", defaultValue="1") int now_page) {

    if (!memberProc.isMemberAdmin(session)) {
      return "redirect:/member/login_form_need";
    }

    if (bindingResult.hasErrors()) {
      ArrayList<CategoryVO> list = categoryProc.list_search_paging(word, now_page, record_per_page);
      model.addAttribute("list", list);
      return "category/list_search";
    }

    int cnt = categoryProc.create(categoryVO);
    return (cnt == 1) ? "redirect:/category/list_search" : "category/msg";
  }

  /** 수정 폼 */
  @GetMapping("/update/{cat_no}")
  public String update(Model model, @PathVariable int cat_no,
                       @RequestParam(name="word", defaultValue="") String word,
                       @RequestParam(name="now_page", defaultValue="1") int now_page) {

    model.addAttribute("categoryVO", categoryProc.read(cat_no));
    model.addAttribute("word", word);
    model.addAttribute("now_page", now_page);

    return "category/update";
  }

  /** 수정 처리 */
  @PostMapping("/update")
  public String update_process(HttpSession session, Model model,
                               @Valid CategoryVO categoryVO, BindingResult bindingResult,
                               @RequestParam(name="word", defaultValue="") String word,
                               @RequestParam(name="now_page", defaultValue="1") int now_page) {

    if (!memberProc.isMemberAdmin(session)) {
      return "redirect:/member/login_form_need";
    }

    if (bindingResult.hasErrors()) {
      model.addAttribute("categoryVO", categoryVO);
      return "category/update";
    }

    int cnt = categoryProc.update(categoryVO);
    return (cnt == 1)
        ? "redirect:/category/list_search?word=" + Tool.encode(word) + "&now_page=" + now_page
        : "category/msg";
  }

  /** 삭제 처리 */
  @PostMapping("/delete")
  public String delete_process(Model model,
                               @RequestParam int cat_no,
                               @RequestParam(name="word", defaultValue="") String word,
                               @RequestParam(name="now_page", defaultValue="1") int now_page) {

    int cnt = categoryProc.delete(cat_no);
    if (cnt == 1) {
      return "redirect:/category/list_search?word=" + Tool.encode(word) + "&now_page=" + now_page;
    } else {
      model.addAttribute("code", "delete_fail");
      return "category/msg";
    }
  }
//카테고리 타입별 목록
  @GetMapping("/list_by_type")
  public String listByType(@RequestParam("cat_type") String cat_type, Model model) {
      ArrayList<CategoryVO> list = categoryProc.list_by_type(cat_type);
      model.addAttribute("list_cat_by_type", list);
      model.addAttribute("cat_type", cat_type);
      return "category/list_by_type";
  }
  /** 목록 + 검색 + 페이징 */
  @GetMapping("/list_search")
  public String list_search(Model model,
                            @RequestParam(name="word", defaultValue="") String word,
                            @RequestParam(name="now_page", defaultValue="1") int now_page) {

    ArrayList<CategoryVO> list = categoryProc.list_search_paging(word, now_page, record_per_page);
    int search_count = categoryProc.list_search_count(word);
    String paging = categoryProc.pagingBox(now_page, word, "/category/list_search",
                                           search_count, record_per_page, page_per_block);

    model.addAttribute("list", list);
    model.addAttribute("search_count", search_count);
    model.addAttribute("paging", paging);
    model.addAttribute("word", word);
    model.addAttribute("now_page", now_page);
    model.addAttribute("categoryVO", new CategoryVO());

    return "category/list_search";
  }

  /** 상세 조회 */
  @GetMapping("/read/{cat_no}")
  public String read(Model model, @PathVariable int cat_no,
                     @RequestParam(name="word", defaultValue="") String word,
                     @RequestParam(name="now_page", defaultValue="1") int now_page) {

    model.addAttribute("categoryVO", categoryProc.read(cat_no));
    model.addAttribute("word", word);
    model.addAttribute("now_page", now_page);

    return "category/read";
  }

  /** 순서 변경 */
  @GetMapping("/update_seqno_forward/{cat_no}")
  public String update_seqno_forward(@PathVariable int cat_no,
                                     @RequestParam(name="word", defaultValue="") String word,
                                     @RequestParam(name="now_page", defaultValue="1") int now_page) {
    categoryProc.update_seqno_forward(cat_no);
    return redirectRead(cat_no, word, now_page);
  }

  @GetMapping("/update_seqno_backward/{cat_no}")
  public String update_seqno_backward(@PathVariable int cat_no,
                                      @RequestParam(name="word", defaultValue="") String word,
                                      @RequestParam(name="now_page", defaultValue="1") int now_page) {
    categoryProc.update_seqno_backward(cat_no);
    return redirectRead(cat_no, word, now_page);
  }

  /** 공개/비공개 설정 */
  @GetMapping("/update_visible_y/{cat_no}")
  public String update_visible_y(@PathVariable int cat_no,
                                 @RequestParam(name="word", defaultValue="") String word) {
    categoryProc.update_visible_y(cat_no);
    return "redirect:/category/list_search?word=" + Tool.encode(word);
  }

  @GetMapping("/update_visible_n/{cat_no}")
  public String update_visible_n(@PathVariable int cat_no,
                                 @RequestParam(name="word", defaultValue="") String word) {
    categoryProc.update_visible_n(cat_no);
    return "redirect:/category/list_search?word=" + Tool.encode(word);
  }

  /** 공통 리다이렉트 메소드 */
  private String redirectRead(int cat_no, String word, int now_page) {
    return "redirect:/category/read/" + cat_no + "?word=" + Tool.encode(word) + "&now_page=" + now_page;
  }
}