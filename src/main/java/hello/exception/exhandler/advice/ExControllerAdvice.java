package hello.exception.exhandler.advice;

import hello.exception.exception.UserException;
import hello.exception.exhandler.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages ="hello.exception.api" )       //@ControllerAdvice(@Component를 가지고있음) + @ResponseBody
public class ExControllerAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExHandler(IllegalArgumentException e) {
        log.error("[exceptionHandler] ex ", e);
        return new ErrorResult("BAD", e.getMessage());
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
    }
}
