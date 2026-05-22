package dev.mvc.paw;

import java.util.ArrayList;
import java.util.Collections; // 무작위 셔플을 위해 추가
import java.util.List;        // 하위 리스트 추출을 위해 추가

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import dev.mvc.match_post.MatchPostProcInter;
import dev.mvc.match_post.MatchPostVO;
import dev.mvc.breed_image.Breed_imageProcInter;
import dev.mvc.breed_image.Breed_imageVO;

@Controller
public class HomeCont {

    @Autowired
    @Qualifier("dev.mvc.match_post.MatchPostProc")
    private MatchPostProcInter matchPostProc;

    @Autowired
    @Qualifier("dev.mvc.breed_image.Breed_imageProc")
    private Breed_imageProcInter breed_imageProc;

    public HomeCont() {
        System.out.println("-> HomeCont created.");
    }

    @GetMapping("/")
    public String home(Model model) {

        // ❤️ 오늘의 매칭
        ArrayList<MatchPostVO> todayMatchList = this.matchPostProc.list_today_match();
        model.addAttribute("todayMatchList", todayMatchList);

        // 🐶 오늘의 댕댕이 (랜덤 노출 처리)
        ArrayList<Breed_imageVO> allGallery = this.breed_imageProc.list_all("");
        ArrayList<Breed_imageVO> randomGallery = new ArrayList<>();

        if (allGallery != null && !allGallery.isEmpty()) {
            // 1. 가져온 전체 리스트를 랜덤하게 무작위로 섞습니다.
            Collections.shuffle(allGallery);

            // 2. 메인에 노출할 원하는 개수를 지정합니다. (예시: 6개)
            int limitCount = Math.min(allGallery.size(), 6); 

            // 3. 섞인 리스트에서 지정한 개수만큼만 잘라서 새 리스트에 담습니다.
            for (int i = 0; i < limitCount; i++) {
                randomGallery.add(allGallery.get(i));
            }
        }

        // 4. 기존 이름 그대로 model에 담아주면 HTML 수정할 필요가 없습니다.
        model.addAttribute("randomGallery", randomGallery);

        return "index";
    }
}