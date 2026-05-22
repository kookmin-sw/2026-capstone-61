package dev.mvc.dog;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/dog") // 공통 URL: 이 컨트롤러의 모든 주소는 /dog로 시작합니다.
public class DogCont {

    @Autowired
    @Qualifier("dev.mvc.dog.DogProc") // 우리가 만든 DogProc을 주입받습니다.
    private DogProcInter dogProc;

    /**
     * 1. 강아지 등록 화면
     * http://localhost:9091/dog/create.do
     */
    @GetMapping("/create.do")
    public String create(HttpSession session) {

        // 🐾 로그인 체크
        if (session.getAttribute("memberno") == null) {
            return "redirect:/member/login";
        }

        return "dog/create";
    }

    /**
     * 2. 강아지 등록 처리
     */
    @PostMapping("/create.do")
    public String create_proc(DogVO dogVO, HttpSession session) {

        // 🐾 로그인 체크
        if (session.getAttribute("memberno") == null) {
            return "redirect:/member/login";
        }

        // 🐾 세션에서 회원 번호 가져오기
        int memberno = (int) session.getAttribute("memberno");
        dogVO.setMemberno(memberno);

        // 🐾 업로드 파일 객체
        MultipartFile mf = dogVO.getFile1MF();

        // 🐾 파일 정보 초기화
        String file1 = "";
        String file1saved = "";
        long size1 = 0;

        // 🐾 실제 업로드 폴더 경로
        String upDir = Dog.getUploadDir();

        // 🐾 파일이 존재하면 업로드 진행
        if (mf != null && !mf.isEmpty()) {

            // 🐾 원본 파일명
            file1 = mf.getOriginalFilename();

            // 🐾 파일 크기
            size1 = mf.getSize();

            // 🐾 저장 파일명 생성
            file1saved = System.currentTimeMillis() + "_" + file1;

            try {

                // 🐾 폴더 없으면 생성
                java.io.File dir = new java.io.File(upDir);

                if (!dir.exists()) {
                    dir.mkdirs();
                }

                // 🐾 실제 저장 파일 생성
                java.io.File saveFile = new java.io.File(upDir, file1saved);

                // 🐾 실제 업로드 실행
                mf.transferTo(saveFile);

            } catch (Exception e) {
                System.out.println("-> 파일 저장 실패: " + e.getMessage());
            }
        }

        // 🐾 VO에 파일 정보 저장
        dogVO.setFile1(file1);
        dogVO.setFile1saved(file1saved);
        dogVO.setThumb1(file1saved);
        dogVO.setSize1(size1);

        // 🐾 DB 저장
        int cnt = dogProc.create(dogVO);

        // 🐾 성공 시 목록 이동
        if (cnt == 1) {
            return "redirect:/dog/list.do";
        } else {
            return "dog/msg";
        }
    }

    /**
     * 3. 내 강아지 목록
     * http://localhost:9091/dog/list.do
     */
    @GetMapping("/list.do")
    public String list(HttpSession session, Model model) {

        // 🐾 로그인 체크
        if (session.getAttribute("memberno") == null) {
            return "redirect:/member/login";
        }

        // 🐾 회원 번호 가져오기
        int memberno = (int) session.getAttribute("memberno");

        // 🐾 회원 강아지 목록 조회
        List<DogVO> list = dogProc.list_by_memberno(memberno);

        // 🐾 JSP 전달
        model.addAttribute("list", list);

        return "dog/list";
    }

    /**
     * 4. 강아지 상세 조회
     * http://localhost:9091/dog/read.do?dogno=1
     */
    @GetMapping("/read.do")
    public String read(int dogno, Model model) {

        // 🐾 강아지 정보 조회
        DogVO dogVO = this.dogProc.read(dogno);

        // 🐾 화면 전달
        model.addAttribute("dogVO", dogVO);

        return "dog/read";
    }

    /**
     * 5. 강아지 수정 화면
     * http://localhost:9091/dog/update.do?dogno=1
     */
    @GetMapping("/update.do")
    public String update(HttpSession session, int dogno, Model model) {

        // 🐾 로그인 체크
        if (session.getAttribute("memberno") == null) {
            return "redirect:/member/login";
        }

        // 🐾 기존 정보 조회
        DogVO dogVO = this.dogProc.read(dogno);

        // 🐾 화면 전달
        model.addAttribute("dogVO", dogVO);

        return "dog/update";
    }

    /**
     * 6. 강아지 수정 처리
     */
    @PostMapping("/update.do")
    public String update_proc(DogVO dogVO, HttpSession session) {

        // 🐾 기존 데이터 조회
        DogVO oldDogVO = this.dogProc.read(dogVO.getDogno());

        // 🐾 업로드 파일
        MultipartFile mf = dogVO.getFile1MF();

        // 🐾 업로드 폴더
        String upDir = Dog.getUploadDir();

        // 🐾 새 파일 업로드 시
        if (mf != null && !mf.isEmpty()) {

            // 🐾 원본 파일명
            String file1 = mf.getOriginalFilename();

            // 🐾 저장 파일명 생성
            String file1saved = System.currentTimeMillis() + "_" + file1;

            // 🐾 파일 크기
            long size1 = mf.getSize();

            try {

                // 🐾 새 파일 저장
                java.io.File saveFile = new java.io.File(upDir, file1saved);

                mf.transferTo(saveFile);

                // 🐾 기존 파일 삭제
                if (oldDogVO.getFile1saved() != null &&
                    !oldDogVO.getFile1saved().isEmpty()) {

                    java.io.File oldFile =
                        new java.io.File(upDir, oldDogVO.getFile1saved());

                    if (oldFile.exists()) {
                        oldFile.delete();
                    }
                }

                // 🐾 새 파일 정보 저장
                dogVO.setFile1(file1);
                dogVO.setFile1saved(file1saved);
                dogVO.setThumb1(file1saved);
                dogVO.setSize1(size1);

            } catch (Exception e) {
                System.out.println("-> 파일 수정 실패: " + e.getMessage());
            }

        } else {

            // 🐾 새 파일 없으면 기존 파일 유지
            dogVO.setFile1(oldDogVO.getFile1());
            dogVO.setFile1saved(oldDogVO.getFile1saved());
            dogVO.setThumb1(oldDogVO.getThumb1());
            dogVO.setSize1(oldDogVO.getSize1());
        }

        // 🐾 DB 수정
        int cnt = this.dogProc.update(dogVO);

        // 🐾 성공 시 상세 페이지 이동
        if (cnt == 1) {
            return "redirect:/dog/read.do?dogno=" + dogVO.getDogno();
        } else {
            return "dog/msg";
        }
    }

    /**
     * 7. 강아지 삭제 처리
     * 상세 페이지에서 바로 삭제 실행
     */
    @GetMapping("/delete.do")
    public String delete_proc(int dogno) {

        // 🐾 기존 데이터 조회
        DogVO dogVO = this.dogProc.read(dogno);

        // 🐾 업로드 폴더 경로
        String upDir = Dog.getUploadDir();

        // 🐾 실제 이미지 파일 삭제
        if (dogVO.getFile1saved() != null &&
            !dogVO.getFile1saved().isEmpty()) {

            java.io.File file =
                new java.io.File(upDir, dogVO.getFile1saved());

            if (file.exists()) {
                file.delete();
            }
        }

        // 🐾 DB 삭제
        int cnt = this.dogProc.delete(dogno);

        // 🐾 성공 시 목록 이동
        if (cnt == 1) {
            return "redirect:/dog/list.do";
        } else {
            return "dog/msg";
        }
    }
}