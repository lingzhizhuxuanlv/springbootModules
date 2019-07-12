package com.lingzhizhuxuanlv.springbootModules.web.conf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

@Configuration
@ComponentScan(basePackages = {"com.lingzhizhuxuanlv.springbootModules.web", "com.lingzhizhuxuanlv.springbootModules.core"})
@MapperScan(basePackages = "com.lingzhizhuxuanlv.springbootModules.core.dao")
public class AppConfig implements WebMvcConfigurer {

    @Autowired
    private Environment env;

    /**
     * tomcat资源映射
     * http://localhost:8080/demo/file/**
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String realPath = "";
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {
            realPath = env.getProperty("upload.w-upload");
        } else {
            realPath = env.getProperty("upload.l-upload");
        }
        //下载路径
        String download = env.getProperty("download");
        //设置下载路径映射
        registry.addResourceHandler(download + "/**")
                .addResourceLocations("file:" + realPath + "/");
    }

    /**
     * freemarker视图解析
     */
    @Bean
    public FreeMarkerViewResolver freeMarkerViewResolver() {
        //templates为根目录，配置视图解析
        FreeMarkerViewResolver freeMarkerViewResolver = new FreeMarkerViewResolver();
        //配置视图类
        freeMarkerViewResolver.setViewClass(MyFreeMarkerView.class);
        //关闭缓存
        freeMarkerViewResolver.setCache(false);
        //设置前缀
        freeMarkerViewResolver.setPrefix("");
        //设置后缀
        freeMarkerViewResolver.setSuffix(".ftl");
        //配置内容类型
        freeMarkerViewResolver.setContentType("text/html;charset=UTF-8");
        return freeMarkerViewResolver;
    }

    /**
     * redisTemplate序列化
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // 设置value和key的序列化规则
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(jdkSerializationRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(jdkSerializationRedisSerializer);
        return redisTemplate;
    }

    /**
     * 设置默认跳转页
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/","/web/toLogin");
    }

    /**
     * 配置拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new WebInterceptor()).addPathPatterns("/web/**").excludePathPatterns("/web/toLogin", "/web/login","/web/layui/**");
    }

}
