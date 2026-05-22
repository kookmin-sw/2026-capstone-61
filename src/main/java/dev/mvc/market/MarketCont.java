package dev.mvc.market;

import java.util.List;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.product_image.ProductImageProcInter;
import dev.mvc.product_image.ProductImageVO;
import dev.mvc.tool.Upload;

@Controller
@RequestMapping("/market")
public class MarketCont {

    @Autowired
    @Qualifier("marketProc")
    private MarketProcInter marketProc;

    @Autowired
    @Qualifier("dev.mvc.product_image.ProductImageProc")
    private ProductImageProcInter productImageProc;

    public MarketCont() {
        System.out.println("-> MarketCont created.");
    }

    // 1. 상품 등록 폼
    @GetMapping("/create.do")
    public ModelAndView create() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("market/create"); // 앞의 / 제거
        return mav;
    }

    // 2. 상품 등록 처리
    @PostMapping("/create.do")
    public ModelAndView create(HttpServletRequest request, 
                               MarketVO marketVO, 
                               ProductImageVO productImageVO) {
        ModelAndView mav = new ModelAndView();
        
        int cnt = this.marketProc.insertProduct(marketVO);
        
        if (cnt == 1) {
            List<MultipartFile> files = productImageVO.getFilesMF();
            this.saveImages(marketVO.getProductId(), files); // 이미지 저장 공통 메서드 호출
            
            mav.setViewName("redirect:/market/list.do"); 
        } else {
            mav.addObject("code", "create_fail");
            mav.setViewName("market/msg"); // 앞의 / 제거
        }
        
        return mav;
    }

    // 3. 전체 상품 목록 조회
    @GetMapping("/list.do")
    public ModelAndView list(
            @RequestParam(value = "className", defaultValue = "") String className,
            @RequestParam(value = "word", defaultValue = "") String word) {

        ModelAndView mav = new ModelAndView();
        List<MarketVO> list = this.marketProc.getProductListSearch(className, word); 
        
        mav.addObject("list", list);
        mav.addObject("word", word);
        mav.addObject("className", className);
        
        mav.setViewName("market/list"); // 앞의 / 제거
        
        return mav;
    }

    // 4. 상품 상세 조회
    @GetMapping("/read.do")
    public ModelAndView read(int productId) {
        ModelAndView mav = new ModelAndView();
        
        MarketVO marketVO = this.marketProc.getProductDetail(productId);
        List<ProductImageVO> fileList = this.productImageProc.list_by_product_id(productId);
        
        for (ProductImageVO img : fileList) {
            if ("Y".equals(img.getIs_main())) {
                marketVO.setThumbnailName(img.getFile_name());
                break;
            }
        }
        
        mav.addObject("marketVO", marketVO);
        mav.addObject("fileList", fileList);
        mav.setViewName("market/read"); // 앞의 / 제거
        
        return mav;
    }

    // 5. 상품 삭제
    @PostMapping("/delete.do")
    public String delete(int productId) {
        // 1. 자식 테이블(이미지) 데이터 먼저 삭제
        this.productImageProc.delete_by_product_id(productId); 
        
        // 2. 부모 테이블(상품) 데이터 삭제
        this.marketProc.deleteProduct(productId);
        
        return "redirect:/market/list.do";
    }

    // 6. 수정 폼 (GET)
    @GetMapping("/update.do")
    public ModelAndView update_form(int productId) {
        ModelAndView mav = new ModelAndView();
        MarketVO marketVO = this.marketProc.getProductDetail(productId);
        
        mav.addObject("marketVO", marketVO);
        mav.setViewName("market/update"); // 앞의 / 제거
        return mav;
    }

    // 7. 수정 처리 (POST)
    @PostMapping("/update.do")
    public String update_proc(MarketVO marketVO, ProductImageVO productImageVO) {
        this.marketProc.updateProduct(marketVO);
        
        List<MultipartFile> files = productImageVO.getFilesMF();
        
        if (files != null && !files.isEmpty() && files.get(0).getSize() > 0) {
            // 기존 사진 DB 데이터 삭제
            this.productImageProc.delete_by_product_id(marketVO.getProductId()); 
            // 새로운 사진 저장
            this.saveImages(marketVO.getProductId(), files);
        }
        
        return "redirect:/market/read.do?productId=" + marketVO.getProductId();
    }

    /**
     * 이미지 저장 공통 로직 (중복 제거용)
     */
    private void saveImages(int productId, List<MultipartFile> files) {
        if (files == null) return;

        String upDir = Market.getUploadDir();
        int img_count = 0;

        for (MultipartFile mf : files) {
            if (mf != null && mf.getSize() > 0) {
                String file_name_saved = Upload.saveFileSpring(mf, upDir);
                
                ProductImageVO newImage = new ProductImageVO();
                newImage.setProduct_id(productId);
                newImage.setFile_name(file_name_saved);
                newImage.setFile_path("/product/storage/");
                newImage.setIs_main(img_count == 0 ? "Y" : "N");
                newImage.setImage_order(img_count + 1);
                
                this.productImageProc.create(newImage);
                img_count++;
            }
        }
    }
}