package dev.mvc.product_image;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import dev.mvc.tool.Tool;

@Getter @Setter @ToString
public class ProductImageVO {
    private int image_id;
    private int product_id;
    private String file_name = "";
    private String file_path = "";
    private String is_main = "N";
    private int image_order = 1;
    private String reg_date;
    
    private MultipartFile file1MF;
    private List<MultipartFile> filesMF;

    // ★ 파일 경로를 반환하는 정적 메서드 추가 ★
    public static synchronized String getUploadDir() {
        return Tool.getUploadPath("product", "storage");
    }
}