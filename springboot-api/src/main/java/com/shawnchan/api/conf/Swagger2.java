package com.shawnchan.api.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2 {

    //基本配置
    @Bean
    public Docket buildDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.shawnchan.api"))
                .paths(PathSelectors.any())
                .build();
    }

    //api文档信息
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("Spring Boot + Swagger2 模板项目")
                //创建人
                .contact(new Contact("Shawn Chan", "https://xxx.xxxxxxxxxxxxxx.xxx", "xxxxxx@xx.xxx"))
                //版本号
                .version("1.0")
                //描述
                .description("小程序接口")
                .build();
    }

}
