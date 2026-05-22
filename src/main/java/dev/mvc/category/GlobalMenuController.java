package dev.mvc.category;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice  // 모든 컨트롤러에 적용
public class GlobalMenuController {

    @Autowired
    @Qualifier("dev.mvc.category.CategoryProc")
    private CategoryProcInter categoryProc;

    /** 모든 뷰에서 공통 메뉴(menu)를 사용 가능하도록 설정 */
    @ModelAttribute("menu")
    public ArrayList<CategoryVOMenu> populateMenu() {
        return categoryProc.menu();
    }
}