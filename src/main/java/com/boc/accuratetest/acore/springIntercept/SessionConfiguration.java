package com.boc.accuratetest.acore.springIntercept;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/**
 * springboot的拦截器配置
 * @author tom
 *
 */
@Configuration
public class SessionConfiguration implements WebMvcConfigurer{
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new IntercptSessionCheck()).addPathPatterns("/**");
    }
}
