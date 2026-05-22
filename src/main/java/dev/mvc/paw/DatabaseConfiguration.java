package dev.mvc.paw;

import javax.sql.DataSource;

import org.mybatis.spring.annotation.MapperScan;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration // Spring Boot 환경 설정 파일임으로 읽어들여 설정 할 것
@PropertySource("classpath:/application.properties")  // 설정 관련 파일 위치
@MapperScan(basePackages= {"dev.mvc.paw",
										"dev.mvc.mypage",
										"dev.mvc.dog",
										"dev.mvc.dog_dbti",
                                        "dev.mvc.chat",
                                        "dev.mvc.ai_config",
                                        "dev.mvc.ai_actionlog",
                                        "dev.mvc.chat_memory",
                                        "dev.mvc.member",
                                        "dev.mvc.friend",
                                        "dev.mvc.messenger",
                                        "dev.mvc.mlogin",
                                        "dev.mvc.comments",
                                        "dev.mvc.cate",
                                        "dev.mvc.category",
                                        "dev.mvc.breed",
                                        "dev.mvc.breed_recom",
                                        "dev.mvc.breed_image",
                                        "dev.mvc.attachfile",
                                        "dev.mvc.market",
                                        "dev.mvc.product_image",
                                        "dev.mvc.market_cart",
                                        "dev.mvc.market_order",
                                        "dev.mvc.market_order_item",
                                        "dev.mvc.market_review",
                                        "dev.mvc.reply",
                                        "dev.mvc.recom",
                                        "dev.mvc.match_post",
                                        "dev.mvc.match_apply",
                                        "dev.mvc.match_review",
                                        "dev.mvc.match_comment",
                                        "dev.mvc.community"}) // 개발 package(테이블)
public class DatabaseConfiguration {
    
    @Autowired
    private ApplicationContext applicationContext;
    
    @Bean
    @ConfigurationProperties(prefix="spring.datasource.hikari")  // 설정 파일의 접두사 선언 spring.datasource.hikari....
    public HikariConfig hikariConfig() {
        return new HikariConfig();
    }
    
    @Bean
    public DataSource dataSource() throws Exception{
        DataSource dataSource = new HikariDataSource(hikariConfig());
        System.out.println(dataSource.toString());  // 정상적으로 연결 되었는지 해시코드로 확인
        return dataSource;
    }
    
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception{
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        // "/src/main/resources/mybatis" 폴더의 파일명이 "xml"로 끝나는 파일 매핑
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:/mybatis/**/*.xml"));
        
        return sqlSessionFactoryBean.getObject();
    }
    
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
