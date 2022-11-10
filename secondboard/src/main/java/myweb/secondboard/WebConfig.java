package myweb.secondboard;

import myweb.secondboard.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration 일단 적용 안시킴
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new LoginCheckInterceptor())
        .order(1)
        .addPathPatterns("/**") //모든 경로에 대해 허용
        .excludePathPatterns("/", "/members/new", "/login", "/logout",
            "/css/**", "/*.ico", "/error", "/js/**", "/images/**", "/matching/home", "/tournament", "/ranking/home", "/boards/home", "/club");
  }
}
