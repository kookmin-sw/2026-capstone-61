package dev.mvc.paw;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import dev.mvc.breed.Breed;
import dev.mvc.community.Community;
import dev.mvc.member.Member;
import dev.mvc.dog.Dog;
import dev.mvc.market.Market;
import dev.mvc.market_review.MarketReview;
import dev.mvc.messenger.Messenger;

import dev.mvc.match_post.Match_post; // Match 클래스 임포트 추가 (패키지 경로 확인 필요)

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // 1. Member
        registry.addResourceHandler("/member/storage/**")
                .addResourceLocations("file:" + Member.getUploadDir());

        // 2. Dog
        registry.addResourceHandler("/dog/storage/**")
                .addResourceLocations("file:" + Dog.getUploadDir());

        // 3. Community
        registry.addResourceHandler("/community/storage/**")
                .addResourceLocations("file:" + Community.getUploadDir());

        // 4. Breed
        registry.addResourceHandler("/breed/storage/**")
                .addResourceLocations("file:" + Breed.getUploadDir());

        // 5. Market (Product)
        registry.addResourceHandler("/product/storage/**")
                .addResourceLocations("file:" + Market.getUploadDir());

        // 6. Market Review
        registry.addResourceHandler("/market_review/storage/**")
        		.addResourceLocations("file:" + MarketReview.getUploadDir());

        // 7. Messenger
        registry.addResourceHandler("/messenger/storage/**")
                .addResourceLocations("file:" + Messenger.getUploadDir());

     // 8. Match Post 
        registry.addResourceHandler("/match_post/storage/**") // HTML 코드와 동일하게 '_' 추가
                .addResourceLocations("file:" + Match_post.getUploadDir());
    }
    
}