package hello.exception.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.exception.exception.UserException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * WebConfig에 수동으로 등록하여 사용.
 * 수동 등록이 선호되는 이유는 명확한 제어, 우선순위 지정, 그리고 전역적인 일관성 유지 때문.
 * 특히, 예외 처리기(HandlerExceptionResolver)와 같은 핵심적인 기능을 다룰 때는 자동 스캔 방식보다는
 * 명시적으로 등록하는 것이 더 나음.
 */
@Slf4j
public class UserHandlerExceptionResolver implements HandlerExceptionResolver {

    // java 객체를 json 문자열로, 혹은 그 반대로 바꿔준다.
    private final ObjectMapper objectMapper = new ObjectMapper();

    /*
    JSON 요청: 예외 정보를 맵에 담고, **ObjectMapper**로 JSON 문자열로 변환한 후 응답 본문에 기록.
    HTML 요청: 500.html 오류 페이지를 렌더링하여 반환.
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        try {
            if (ex instanceof UserException) {
                log.info("UserExcepton reslover to 400");
                String acceptHeader = request.getHeader("accept");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                if ("application/json".equals(acceptHeader)) {
                    Map<String, Object> errorResult = new HashMap<>();
                    errorResult.put("ex", ex.getClass());
                    errorResult.put("message", ex.getMessage());

                    String result = objectMapper.writeValueAsString(errorResult);

                    response.setContentType("application/json");
                    response.setCharacterEncoding("utf-8");
                    response.getWriter().write(result);
                    return new ModelAndView();
                } else {
                    // TEXT/HTML
                    return new ModelAndView("error/500");
                }
            }
        } catch (IOException e) {
            log.info("resolver ex", e);
        }
        return null;
    }

}
