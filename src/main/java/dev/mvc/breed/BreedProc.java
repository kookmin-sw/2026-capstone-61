package dev.mvc.breed;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.mvc.tool.Security;

@Component("dev.mvc.breed.BreedProc")
public class BreedProc implements BreedProcInter {
  
  @Autowired
  private Security security;
  
  @Autowired
  private BreedDAOInter breedDAO;

  @Override
  public int create(BreedVO breedVO) {
    return this.breedDAO.create(breedVO);
  }

  @Override
  public ArrayList<BreedVO> list_all() {
    return this.breedDAO.list_all();
  }

  @Override
  public ArrayList<BreedVO> list_by_cat_no(int cat_no) {
    return this.breedDAO.list_by_cat_no(cat_no);
  }

  @Override
  public BreedVO read(int breedno) {
    return this.breedDAO.read(breedno);
  }

  @Override
  public ArrayList<BreedVO> list_by_cat_no_search(HashMap<String, Object> hashMap) {
    return this.breedDAO.list_by_cat_no_search(hashMap);
  }

  @Override
  public int list_by_cat_no_search_count(HashMap<String, Object> hashMap) {
    return this.breedDAO.list_by_cat_no_search_count(hashMap);
  }

  @Override
  public ArrayList<BreedVO> list_by_cat_no_search_paging(HashMap<String, Object> map) {

    int now_page = (int)map.get("now_page");
    int record_per_page = 6;

    int start_num = ((now_page - 1) * record_per_page) + 1;
    int end_num = now_page * record_per_page;

    map.put("start_num", start_num);
    map.put("end_num", end_num);

    return this.breedDAO.list_by_cat_no_search_paging(map);
  }

  @Override
  public String pagingBox(int cat_no, int now_page, String word, String list_file, int search_count, 
                          int record_per_page, int page_per_block) {
    
    int total_page = (int) (Math.ceil((double) search_count / record_per_page));
    int total_grp = (int) (Math.ceil((double) total_page / page_per_block));
    int now_grp = (int) (Math.ceil((double) now_page / page_per_block));

    int start_page = ((now_grp - 1) * page_per_block) + 1;
    int end_page = (now_grp * page_per_block);

    StringBuilder str = new StringBuilder();
    str.append("<style type='text/css'>");
    str.append("#paging {text-align: center; margin-top: 10px; font-size: 1.1em;}");
    str.append("#paging A:link {text-decoration:none; color:black;}");
    str.append("#paging A:hover{background-color: #f8f9fa; color:#ff6b81;}");
    str.append(".span_box_1{border: 1px solid #ffccd2; padding:5px 12px; margin:0 3px; border-radius:5px;}");
    str.append(".span_box_2{background-color: #ff6b81; color:#fff; border:1px solid #ff6b81; padding:5px 12px; margin:0 3px; border-radius:5px; font-weight:bold;}");
    str.append("</style>");
    str.append("<div id='paging'>");

    int _now_page = (now_grp - 1) * page_per_block;
    if (now_grp >= 2) {
      str.append("<span class='span_box_1'><a href='" + list_file + "?cat_no="+cat_no+"&word=" + word + "&now_page=" + _now_page + "'>이전</a></span>");
    }

    for (int i = start_page; i <= end_page; i++) {
      if (i > total_page) break;
      if (now_page == i) {
        str.append("<span class='span_box_2'>" + i + "</span>");
      } else {
        str.append("<span class='span_box_1'><a href='" + list_file + "?cat_no="+cat_no+"&word=" + word + "&now_page=" + i + "'>" + i + "</a></span>");
      }
    }

    _now_page = (now_grp * page_per_block) + 1;
    if (now_grp < total_grp) {
      str.append("<span class='span_box_1'><a href='" + list_file + "?cat_no="+cat_no+"&word=" + word + "&now_page=" + _now_page + "'>다음</a></span>");
    }

    str.append("</div>");

    return str.toString();
  }

  @Override
  public int update_text(BreedVO breedVO) {
    return this.breedDAO.update_text(breedVO);
  }



  @Override
  public int delete(int breedno) {
    return this.breedDAO.delete(breedno);
  }

  @Override
  public int count_by_cat_no(int cat_no) {
    return this.breedDAO.count_by_cat_no(cat_no);
  }

  @Override
  public int delete_by_cat_no(int cat_no) {
    return this.breedDAO.delete_by_cat_no(cat_no);
  }

  @Override
  public int count_by_memberno(int memberno) {
    return this.breedDAO.count_by_memberno(memberno);
  }

  @Override
  public int update_viewcnt(int breedno) {
    return this.breedDAO.update_viewcnt(breedno);
  }

  // 🔥 여기 핵심 수정
  @Override
  public int recom(int breedno) {
    return this.breedDAO.recom(breedno);
  }

  @Override
  public int recom_cancel(int breedno) {
    return this.breedDAO.recom_cancel(breedno);
  }


  @Override
  public ArrayList<BreedVO> list_all_search(String word) {
    return this.breedDAO.list_all_search(word);
  }
  @Override
  public BreedVO readByName(String breed_name) {
      // 여기서 빨간줄이 난다면 breedDAO 인터페이스에 readByName이 없거나 저장이 안된 것임!
      return this.breedDAO.readByName(breed_name); 
  }
  /**
   * 전체 품종 수
   */
  @Override
  public int count_all() {

    return this.breedDAO.count_all();

  }

}