package hello.exception;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class WebServerCustomizer implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

    // 아래 상수들이 RequestDispatcher에 정의되어 있음.
    // 이것들이 에러가 발생하면 에러페이지에 정보로 담긴다.
    String ERROR_EXCEPTION = "jakarta.servlet.error.exception";
    String ERROR_EXCEPTION_TYPE = "jakarta.servlet.error.exception_type";
    String ERROR_MESSAGE = "jakarta.servlet.error.message";
    String ERROR_REQUEST_URI = "jakarta.servlet.error.request_uri";
    String ERROR_SERVLET_NAME = "jakarta.servlet.error.servlet_name";


    @Override
    public void customize(ConfigurableWebServerFactory factory) {

        ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND, "/error-page/404");
        ErrorPage errorPage500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error-page/500");
        ErrorPage errorPageEx = new ErrorPage(RuntimeException.class, "/error-page/500");

        factory.addErrorPages(errorPage404, errorPage500, errorPageEx);
    }


}
