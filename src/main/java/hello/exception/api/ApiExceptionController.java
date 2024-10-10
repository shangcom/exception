package hello.exception.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ApiExceptionController {

    /**
     * **RuntimeException**은 Spring Boot에서 기본적으로 500 Internal Server Error로 처리됩니다.
     * WebServerCustomizer 클래스에서 500 오류가 발생할 경우 /error-page/500 경로로 요청을 리디렉션
     * **ErrorPageController**의 errorPage500() 메서드는 /error-page/500 경로로 들어온 요청 처리
     * 예외 처리가 HTML 페이지로 이루어지는 것은 API 클라이언트(예: Postman, 모바일 앱, 프론트엔드 애플리케이션)에게
     * 부적절한 응답 형식을 제공. API 클라이언트는 JSON이나 XML 같은 형식의 데이터를 기대하기 때문에,
     * HTML 응답은 클라이언트가 오류를 적절하게 처리하지 못하게 만들 수 있음
     */
    @GetMapping("/api/members/{id}")
    public MemberDto getMember(@PathVariable("id") String id) {

      /*
       예외가 발생하면, Spring이 자동으로 HttpServletRequest 객체에 예외 정보를 추가합니다.
       개발자가 직접 request.addAttribute()를 호출하지 않아도, Spring의 내장 예외 처리에 의해
       예외와 관련된 정보들이 request 객체에 담깁니다.
       아래 if문 통해 RuntimeException에 메시지로 "잘못된 사용자"가 담기고, 그것이 request에 저장된다.
      */
        if (id.equals("ex")) {
            throw new RuntimeException("잘못된 사용자");
        }
        if (id.equals("bad")) {
            throw new IllegalArgumentException("잘못된 입력값");
        }
        return new MemberDto(id, "hello" + id);
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String memberId;
        private String name;
    }
}
