package dev.mvc.product_image;

import java.util.List;
import jakarta.servlet.http.HttpServletRequest; // javax -> jakarta (최신 버전 기준)

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.market.Market; 
import dev.mvc.tool.Upload;

@Controller
@RequestMapping("/product_image") // 공통 경로 추출
public class ProductImageCont {
  
  @Autowired
  @Qualifier("dev.mvc.product_image.ProductImageProc")
  private ProductImageProcInter productImageProc;

  public ProductImageCont() {
    System.out.println("-> ProductImageCont created.");
  }

  /**
   * 다중 상품 이미지 등록 처리
   * POST: /product_image/create.do
   */
  @PostMapping(value="/create.do")
  public ModelAndView create(HttpServletRequest request, ProductImageVO productImageVO) {
    ModelAndView mav = new ModelAndView();
    
    // 1. 저장 경로 설정 (Market 설정 클래스 활용)
    String upDir = Market.getUploadDir(); 
    
    // 2. 다중 파일 리스트 처리
    List<MultipartFile> files = productImageVO.getFilesMF();
    
    int count = 0;
    if (files != null && !files.isEmpty()) {
      for (MultipartFile mf : files) {
        if (mf != null && mf.getSize() > 0) {
          // A. 파일 물리적 저장 및 변조된파일명 반환
          String file_name_saved = Upload.saveFileSpring(mf, upDir);
          
          // B. 개별 이미지 정보 설정
          ProductImageVO newImage = new ProductImageVO();
          newImage.setProduct_id(productImageVO.getProduct_id()); 
          newImage.setFile_name(file_name_saved);
          newImage.setFile_path("/product/storage/"); // 가상 경로
          
          // C. 첫 번째 사진만 대표 이미지(Y)로 설정
          newImage.setIs_main(count == 0 ? "Y" : "N");
          
          // D. 출력 순서 부여
          newImage.setImage_order(count + 1);
          
          // E. DB 등록
          this.productImageProc.create(newImage);
          
          count++;
        }
      }
    }
    
    // 등록 완료 후 마켓 목록으로 이동
    mav.setViewName("redirect:/market/list.do"); 
    return mav;
  }

  /**
   * 특정 상품에 속한 이미지 목록 보기
   * GET: /product_image/list_by_product_id.do?product_id=1
   */
  @GetMapping(value="/list_by_product_id.do")
  public ModelAndView list_by_product_id(int product_id) {
    ModelAndView mav = new ModelAndView();
    
    List<ProductImageVO> list = this.productImageProc.list_by_product_id(product_id);
    
    mav.addObject("list", list);
    mav.addObject("product_id", product_id);
    
    // 뷰 경로 맨 앞 / 제거
    mav.setViewName("product_image/list_by_product_id"); 
    return mav;
  }
  
  /**
   * 이미지 개별 삭제
   * POST: /product_image/delete.do
   */
  @PostMapping(value="/delete.do")
  public ModelAndView delete(int image_id, int product_id) {
    ModelAndView mav = new ModelAndView();
    
    // DB 정보 삭제
    this.productImageProc.delete(image_id);
    
    mav.setViewName("redirect:/product_image/list_by_product_id.do?product_id=" + product_id);
    return mav;
  }
}