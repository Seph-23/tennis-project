package myweb.secondboard;

import myweb.secondboard.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addInterceptors(InterceptorRegistry registry) {

    registry.addInterceptor(new LoginCheckInterceptor())
        .order(1)
        .addPathPatterns("/**") //모든 경로에 대해 허용
        .excludePathPatterns("/", "/members/new", "/login", "/logout", "/errors/**",
            "/css/**", "/*.ico", "/error/**", "/js/**", "/images/**", "/matching/home",
          "/tournament", "/ranking/home", "/boards/home", "/club", "/boards/detail/**",
          "/oauth/kakao/**", "/notice/home", "/notice/detail/**", "/question/home",
          "/question/detail/**", "/lesson/home", "/lesson/detail/**", "/login/modal/**",
          "/api/members/*", "/matching/matchingListUpdate/*", "/members/find/password/**",
            "/members/find/**", "members/find", "/api/sms/check/sendSMS/**",
            "/matching/detail/**", "/club/detail/**"); // 인터셉터에서 제외할 패턴 : 로그인 안한 회원
  }
}
