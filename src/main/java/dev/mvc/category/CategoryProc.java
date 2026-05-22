package dev.mvc.category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("dev.mvc.category.CategoryProc")
public class CategoryProc implements CategoryProcInter {
  
  @Autowired
  private CategoryDAOInter categoryDAO;
  
  public CategoryProc() {
    // 기본 생성자
  }

  /** 등록 */
  @Override
  public int create(CategoryVO categoryVO) {
    return this.categoryDAO.create(categoryVO);
  }

  /** 전체 목록 */
  @Override
  public ArrayList<CategoryVO> list_all() {
    return this.categoryDAO.list_all();
  }

  /** 조회 */
  @Override
  public CategoryVO read(int cat_no) {
    return this.categoryDAO.read(cat_no);
  }

  /** 수정 */
  @Override
  public int update(CategoryVO categoryVO) {
    return this.categoryDAO.update(categoryVO);
  }

  /** 삭제 */
  @Override
  public int delete(int cat_no) {
    return this.categoryDAO.delete(cat_no);
  }

  /** 순서 ↑ */
  @Override
  public int update_seqno_forward(int cat_no) {
    return this.categoryDAO.update_seqno_forward(cat_no);
  }

  /** 순서 ↓ */
  @Override
  public int update_seqno_backward(int cat_no) {
    return this.categoryDAO.update_seqno_backward(cat_no);
  }

  /** 공개 */
  @Override
  public int update_visible_y(int cat_no) {
    return this.categoryDAO.update_visible_y(cat_no);
  }

  /** 비공개 */
  @Override
  public int update_visible_n(int cat_no) {
    return this.categoryDAO.update_visible_n(cat_no);
  }

  /** 공개 목록 */
  @Override
  public ArrayList<CategoryVO> list_all_visible() {
    return this.categoryDAO.list_all_visible();
  }

  /** 검색 */
  @Override
  public ArrayList<CategoryVO> list_search(String word) {
    return this.categoryDAO.list_search(word);
  }

  /** 검색 + 페이징 */
  @Override
  public ArrayList<CategoryVO> list_search_paging(String word, int now_page, int record_per_page) {
    int begin = (now_page - 1) * record_per_page;
    int start_num = begin + 1;
    int end_num = begin + record_per_page;

    Map<String, Object> map = new HashMap<>();
    map.put("word", word);
    map.put("start_num", start_num);
    map.put("end_num", end_num);

    return this.categoryDAO.list_search_paging(map);
  }

  /** 검색 개수 */
  @Override
  public int list_search_count(String word) {
    return this.categoryDAO.list_search_count(word);
  }

  /** 메뉴 생성 */
  @Override
  public ArrayList<CategoryVOMenu> menu() {
    ArrayList<CategoryVOMenu> menu = new ArrayList<>();

    // CAT_TYPE 기준으로 그룹 (INFO, BOARD, SHOP...)
    ArrayList<String> typeList = this.categoryDAO.gen_type();

    for (String type : typeList) {
      CategoryVOMenu voMenu = new CategoryVOMenu();
      voMenu.setCat_type(type);

      ArrayList<CategoryVO> list = this.categoryDAO.list_by_type(type);
      voMenu.setList_cat_name(list);

      if (list.size() > 0) {
        menu.add(voMenu);
      }
    }

    return menu;
  }

  @Override
  public String pagingBox(int now_page, String word, String list_file, int search_count, 
                                      int record_per_page, int page_per_block){    
    // 전체 페이지 수: (double)1/10 -> 0.1 -> 1 페이지, (double)12/10 -> 1.2 페이지 -> 2 페이지
    int total_page = (int)(Math.ceil((double)search_count / record_per_page));
    // 전체 그룹  수: (double)1/10 -> 0.1 -> 1 그룹, (double)12/10 -> 1.2 그룹-> 2 그룹
    int total_grp = (int)(Math.ceil((double)total_page / page_per_block)); 
    // 현재 그룹 번호: (double)13/10 -> 1.3 -> 2 그룹
    int now_grp = (int)(Math.ceil((double)now_page / page_per_block));  
    
    // 1 group: 1, 2, 3 ... 9, 10
    // 2 group: 11, 12 ... 19, 20
    // 3 group: 21, 22 ... 29, 30
    int start_page = ((now_grp - 1) * page_per_block) + 1; // 특정 그룹의 시작 페이지  
    int end_page = (now_grp * page_per_block);               // 특정 그룹의 마지막 페이지   
     
    StringBuffer str = new StringBuffer(); // String class 보다 문자열 추가등의 편집시 속도가 빠름 
    
    // style이 java 파일에 명시되는 경우는 로직에 따라 css가 영향을 많이 받는 경우에 사용하는 방법
    str.append("<style type='text/css'>"); 
    str.append("  #paging {text-align: center; margin-top: 5px; font-size: 1em;}"); 
    str.append("  #paging A:link {text-decoration:none; color:black; font-size: 1em;}"); 
    str.append("  #paging A:hover{text-decoration:none; background-color: #FFFFFF; color:black; font-size: 1em;}"); 
    str.append("  #paging A:visited {text-decoration:none;color:black; font-size: 1em;}"); 
    str.append("  .span_box_1{"); 
    str.append("    text-align: center;");    
    str.append("    font-size: 1em;"); 
    str.append("    border: 1px;"); 
    str.append("    border-style: solid;"); 
    str.append("    border-color: #cccccc;"); 
    str.append("    padding:1px 6px 1px 6px; /*위, 오른쪽, 아래, 왼쪽*/"); 
    str.append("    margin:1px 2px 1px 2px; /*위, 오른쪽, 아래, 왼쪽*/"); 
    str.append("  }"); 
    str.append("  .span_box_2{"); 
    str.append("    text-align: center;");    
    str.append("    background-color: #668db4;"); 
    str.append("    color: #FFFFFF;"); 
    str.append("    font-size: 1em;"); 
    str.append("    border: 1px;"); 
    str.append("    border-style: solid;"); 
    str.append("    border-color: #cccccc;"); 
    str.append("    padding:1px 6px 1px 6px; /*위, 오른쪽, 아래, 왼쪽*/"); 
    str.append("    margin:1px 2px 1px 2px; /*위, 오른쪽, 아래, 왼쪽*/"); 
    str.append("  }"); 
    str.append("</style>"); 
    str.append("<DIV id='paging'>"); 
//    str.append("현재 페이지: " + nowPage + " / " + totalPage + "  "); 
 
    // 이전 10개 페이지로 이동
    // now_grp: 1 (1 ~ 10 page)
    // now_grp: 2 (11 ~ 20 page)
    // now_grp: 3 (21 ~ 30 page) 
    // 현재 2그룹일 경우: (2 - 1) * 10 = 1그룹의 마지막 페이지 10
    // 현재 3그룹일 경우: (3 - 1) * 10 = 2그룹의 마지막 페이지 20
    int _now_page = (now_grp - 1) * page_per_block;  
    if (now_grp >= 2){ // 현재 그룹번호가 2이상이면 페이지수가 11페이지 이상임으로 이전 그룹으로 갈수 있는 링크 생성 
      str.append("<span class='span_box_1'><A href='"+list_file+"?&word="+word+"&now_page="+_now_page+"'>이전</A></span>"); 
    } 
 
    // 중앙의 페이지 목록
    for(int i=start_page; i<=end_page; i++){ 
      if (i > total_page){ // 마지막 페이지를 넘어갔다면 페이 출력 종료
        break; 
      } 
  
      if (now_page == i){ // 목록에 출력하는 페이지가 현재페이지와 같다면 CSS 강조(차별을 둠)
        str.append("<span class='span_box_2'>"+i+"</span>"); // 현재 페이지, 강조 
      }else{
        // 현재 페이지가 아닌 페이지는 이동이 가능하도록 링크를 설정
        str.append("<span class='span_box_1'><A href='"+list_file+"?word="+word+"&now_page="+i+"'>"+i+"</A></span>");   
      } 
    } 
 
    // 10개 다음 페이지로 이동
    // nowGrp: 1 (1 ~ 10 page),  nowGrp: 2 (11 ~ 20 page),  nowGrp: 3 (21 ~ 30 page) 
    // 현재 페이지 5일경우 -> 현재 1그룹: (1 * 10) + 1 = 2그룹의 시작페이지 11
    // 현재 페이지 15일경우 -> 현재 2그룹: (2 * 10) + 1 = 3그룹의 시작페이지 21
    // 현재 페이지 25일경우 -> 현재 3그룹: (3 * 10) + 1 = 4그룹의 시작페이지 31
    _now_page = (now_grp * page_per_block)+1; //  최대 페이지수 + 1 
    if (now_grp < total_grp){ 
      str.append("<span class='span_box_1'><A href='"+list_file+"?&word="+word+"&now_page="+_now_page+"'>다음</A></span>"); 
    } 
    str.append("</DIV>"); 
     
    return str.toString(); 
  }

  @Override
  public ArrayList<CategoryVO> list_by_type(String cat_type) {
      // DAO의 list_by_type 호출
      return this.categoryDAO.list_by_type(cat_type);
  }
  

}