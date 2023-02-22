package hello.exception.api;

import hello.exception.exception.UserException;
import hello.exception.exhandler.ErrorResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class ApiExceptionV2Controller {

    //@ExceptionHandler은 뭐냐, ApiExceptionV2Controller 컨트롤러에서 뭔가 예외가 발생하면(여기서는 IllegalArgumentException) @ExceptionHandler어노테이션이 붙은 illegalExHandler()가 잡는다.
    //그리고 안에 로직대로 호출이 되는데 이 컨트롤러는 @RestController라 ErrorResult가 그대로 JSON으로 반환이 된다.
    //@ExceptionHandler 은 여기 컨트롤러에서 발생한 예외만 적용된다 다른 컨트롤러 상관 없다. 그래서 아래 getMember() 메소드를 복사해서 가져온 것.

/*    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExHandler(IllegalArgumentException e) {
        log.error("[exceptionHandler] ex ", e);
        return new ErrorResult("BAD", e.getMessage());          //@RestController였기에 응답이 JSON으로 나간것.
    }

    @ExceptionHandler//(UserException.class) 해줘도 되는데, 아래 그냥 파라미터로 받아도 된다. 똑같다.
    public ResponseEntity<ErrorResult> userExceptionhandler(UserException e){
        log.error("[exceptionHandler] ex 는 아래에 표시",  e);
        ErrorResult errorResult = new ErrorResult("USER-EX", e.getMessage());
        return new ResponseEntity<ErrorResult>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandler(Exception e) {
        log.error("[exceptionHandler] 예외 는 아래에 표시", e);
        return new ErrorResult("EX", "내부 오류");
    }*/

    @GetMapping("/api2/members/{id}")
    public MemberDto getMember(@PathVariable("id") String id) {

        if (id.equals("ex")) {      //id로 넘어온게 ex면 런타임 에러 발생. 아니라면 MemberDto가 반환
            throw new RuntimeException("잘못된 사용자");
        }
        if (id.equals("bad")) {
            throw new IllegalArgumentException("잘못된 입력값");
        }

        if (id.equals("user-ex")) {
            throw new UserException("사용자 오류");
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
