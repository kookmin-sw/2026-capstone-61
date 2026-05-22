package dev.mvc.paw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

// S3 기능을 아예 안 쓰겠다고 스프링에게 확실히 알려주는 설정입니다.
@SpringBootApplication(excludeName = {
    "io.awspring.cloud.autoconfigure.s3.S3AutoConfiguration"
})
@ComponentScan(basePackages = {"dev.mvc"})
@EnableScheduling // 스케줄러 활성화
public class PawApplication {

    public static void main(String[] args) {
        SpringApplication.run(PawApplication.class, args);
    }

}