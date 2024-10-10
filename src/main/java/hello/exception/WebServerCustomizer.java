package hello.exception;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.http.HttpStatus;

/**
 * **WebServerCustomizer**는 특정 HTTP 상태 코드나 예외에 대해 커스텀 오류 페이지를 설정하는 역할을 합니다.
 * 하지만, **BasicErrorController**는 Spring Boot의 자동 설정에 의해 /error 경로로 모든 오류를 리디렉션하므로,
 * 오류 페이지를 별도로 설정할 필요가 없습니다.
 * Spring Boot는 기본적으로 모든 오류를 /error 경로로 처리하며, 해당 경로에서 오류 상태에 맞는 응답을 생성합니다.
 */

//@Component
public class WebServerCustomizer implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {
    @Override
    public void customize(ConfigurableWebServerFactory factory) {

        ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND, "/error-page/404");
        ErrorPage errorPage500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error-page/500");
        ErrorPage errorPageEx = new ErrorPage(RuntimeException.class, "/error-page/500");
        factory.addErrorPages(errorPage404, errorPage500, errorPageEx);
    }


    // 아래 상수들이 RequestDispatcher에 정의되어 있음.
    // 이것들이 에러가 발생하면 에러페이지에 정보로 담긴다.
    String ERROR_EXCEPTION = "jakarta.servlet.error.exception";
    String ERROR_EXCEPTION_TYPE = "jakarta.servlet.error.exception_type";
    String ERROR_MESSAGE = "jakarta.servlet.error.message";
    String ERROR_REQUEST_URI = "jakarta.servlet.error.request_uri";
    String ERROR_SERVLET_NAME = "jakarta.servlet.error.servlet_name";

}
