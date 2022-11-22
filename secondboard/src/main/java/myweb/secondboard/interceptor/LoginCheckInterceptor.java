package myweb.secondboard.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import myweb.secondboard.web.SessionConst;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

  //로그인 한 유저 허용 범위
  private static final String[] whitelist = {"/boards/boardAdd", "/boards/new", "/lesson/lessonAdd",
    "/lesson/new", "/boardDelete/**", "/api/notice/noticeDelete/**", "/notice/update/**", "/notice/like",
    "/question/questionAdd", "/question/new", "/notice/new", "/club/memberDelete",
    "/club/delete", "/club/memberBan/*", "/club/join", "/members/update/password/*", "/members/withdrawl/**",
    "/notice/noticeAdd", "/members/update/**", "/members/profile/**", "/club/save", "/club/update",
    "/matching/new", "/manager/**", "/tournament/new", "/matching/player/add", "/matching/save",
    "/matching/delete/memberDelete/**", "/matching/update/**", "/boards/update/**", "/members/profileUpdate",
    "/api/question/**", "/api/comments/**", "/question/update/**", "/club/visitor/**",
    "/boards/like", "/boards/report", "/notice/like", "/notice/report", "/lesson/like", "/lesson/report",
    "/question/like", "/question/report"};

  public static final String LOG_ID = "logId";

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    String requestURI = request.getRequestURI();
    log.info("인증 체크 인터셉터 실행{}", requestURI);

    HttpSession session = request.getSession(false);

    if (error404Check(requestURI)) {
      log.info("에러 페이지로 이동");
      response.sendError(404);
      return false;
    }

    if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
      log.info("미인증 사용자 요청");
      response.sendRedirect("/login?redirectURL=" + requestURI);
      return false;
    }
    return true;
  }

  //postHandle 은 exception 터지면 호출 X
  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse
      response, Object handler, ModelAndView modelAndView) throws Exception {
    log.info("postHandle [{}]", modelAndView);
  }

  //항상 호출
  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse
      response, Object handler, Exception ex) throws Exception {
    String requestURI = request.getRequestURI();
    String logId = (String) request.getAttribute(LOG_ID);
    log.info("RESPONSE [{}][{}][{}]", logId, request.getDispatcherType(),
        requestURI);
    if (ex != null) {
      log.error("afterCompletion error!!", ex);
    }
  }

  private boolean error404Check(String requestURI) {
    return !PatternMatchUtils.simpleMatch(whitelist, requestURI);
  }

}
