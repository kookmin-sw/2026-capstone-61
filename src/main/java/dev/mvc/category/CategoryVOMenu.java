package dev.mvc.category;

import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class CategoryVOMenu {
    /** 카테고리 번호 */
    private int cat_no; 

    /** 카테고리 유형 (그룹명) */
    private String cat_type;
    
    /** * 리스트의 제네릭 타입을 CategoryVO로 수정 
     * (기존에 DedVO로 되어있으면 빨간줄이 뜹니다)
     */
    private ArrayList<CategoryVO> list_cat_name; 
}