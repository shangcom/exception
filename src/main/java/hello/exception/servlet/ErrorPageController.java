package hello.exception.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

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
    public String errorPage500(HttpServletRequest request, HttpServletResponse response) {
        log.info("errorPAge 500");
        printErrorInfo(request);
        return "error-page/500";
    }

    /**
     * ResponseEntity를 통해 result라는 map에 status와 message를 담아, HTTP 상태 코드와 함께 클라이언트에 전달.
     * header는 없음
     * body는 Map(result).
     * result는 json 형식으로 응답 본문을 구성함.
     * status는 ApiExceptionController에서 json 요청 시 ex가 들어왔을 경우 자동으로
     * 예외 관련 정보가 저장된 것을 HttpStatus.valueOf(statusCode))로 가져와서 사용.
     * @return ResponseEntity<T> : <T>는 body에 들어갈 객체. header, body, status로 구성됨.
     * HttpStatus status : value(상태 코드 숫자값), reasoPhrase(상태코드 설명), series(상태 코드 범위)
     */
    @RequestMapping(value = "error-page/500", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> errorPage500Api(
            HttpServletRequest request, HttpServletResponse response) {

        log.info("API errorPage 500");

        Map<String, Object> result = new HashMap<>();
        Exception ex = (Exception) request.getAttribute(ERROR_EXCEPTION);
        result.put("status", request.getAttribute(ERROR_STATUS_CODE));
        //응답 JSON 데이터에 상태 코드를 포함하기 위해.
        result.put("message", ex.getMessage());

        Integer statusCode = (Integer) request.getAttribute(ERROR_STATUS_CODE);
        //HTTP 응답 자체의 상태 코드를 지정하기 위해.
        return new ResponseEntity<>(result, HttpStatus.valueOf(statusCode));
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
