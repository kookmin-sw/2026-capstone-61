package dev.mvc.attachfile;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/attachfile")
public class AttachfileCont {

    @Autowired
    @Qualifier("dev.mvc.attachfile.AttachfileProc")
    private AttachfileProcInter attachfileProc;

    /**
     * 1. [관리자 기능] 강아지 사진 여부 업데이트 (AJAX 방식)
     * 엉뚱한 사진을 발견했을 때 'N'으로 바꾸거나, 검토 후 'Y'로 바꿀 때 사용
     */
    @ResponseBody
    @PostMapping("/update_is_dog")
    public String update_is_dog(int attachfileno, String is_dog) {
        // Proc를 통해 DB의 is_dog 컬럼 값을 변경합니다 ('Y' or 'N')
        int cnt = this.attachfileProc.update_is_dog(attachfileno, is_dog);
        
        JSONObject json = new JSONObject();
        json.put("cnt", cnt); // 수정 성공 여부 (1: 성공)
        
        return json.toString();
    }

    /**
     * 2. [수정 기능] 게시글 수정 페이지에서 특정 사진만 삭제할 때
     * 서버 파일 삭제 로직은 Proc에 넣고, 여기서는 DB 레코드 삭제 요청만 받습니다.
     */
    @ResponseBody
    @PostMapping("/delete")
    public String delete(int attachfileno) {
        // 실제 파일 삭제 로직(File.delete())은 Proc에서 수행하도록 권장합니다.
        int cnt = this.attachfileProc.delete(attachfileno);
        
        JSONObject json = new JSONObject();
        json.put("cnt", cnt);
        
        return json.toString();
    }
    
    /**
     * 3. [다운로드 기능] 원본 사진을 다운로드하고 싶을 때 (선택 사항)
     * 필요한 경우 fupname을 가지고 실제 파일을 스트림으로 쏴주는 로직을 추가합니다.
     */
}