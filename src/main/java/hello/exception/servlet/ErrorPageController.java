package hello.exception.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static jakarta.servlet.RequestDispatcher.*;

/**
 * **ErrorPageController**는 **BasicErrorController**에 의해 대체됩니다.
 * 현재 코드에서 ErrorPageController가 오류 발생 시 커스텀 오류 페이지를 처리하고 있습니다.
 * BasicErrorController를 적용하면 이 부분이 자동으로 처리되며,
 * 기본적인 오류 정보(상태 코드, 예외 메시지 등)를 출력하는 페이지를 제공할 수 있습니다.
 */
@Slf4j
@Controller
public class ErrorPageController {

    @RequestMapping("/error-page/404")
    public String errorPAge404(HttpServletRequest request, HttpServletResponse response) {
        log.info("errorPAge 404");
        printErrorInfo(request);
        return "error-page/404";
    }

    @RequestMapping("/error-page/500")
    public String errorPAge500(HttpServletRequest request, HttpServletResponse response) {
        log.info("errorPAge 500");
        printErrorInfo(request);
        return "error-page/500";
    }

    private void printErrorInfo(HttpServletRequest request) {

        log.info("ERROR_EXCEPTION: {}", request.getAttribute(ERROR_EXCEPTION));
        log.info("ERROR_EXCEPTION_TYPE: {}", request.getAttribute(ERROR_EXCEPTION_TYPE));
        log.info("ERROR_MESSAGE: {}", request.getAttribute(ERROR_MESSAGE));
        log.info("ERROR_REQUEST_URI: {}", request.getAttribute(ERROR_REQUEST_URI));
        log.info("ERROR_SERVLET_NAME: {}", request.getAttribute(ERROR_SERVLET_NAME));
        log.info("ERROR_STATUS_CODE: {}", request.getAttribute(ERROR_STATUS_CODE));
        log.info("dispatchType= {}", request.getDispatcherType());
    }
}
