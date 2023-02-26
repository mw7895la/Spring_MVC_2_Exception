package hello.exception.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.BAD_REQUEST, reason="error.bad")
public class BadRequestException extends RuntimeException {     //RuntimeException이 IlleaglArgumentException의 부모임.
    //원래 ex 터지면 500 예외가 나와야 함.
    //@ResponseStatus를 추가했다.
    //우리 이전에 UserException 만들면서 Exception 핸들러 다 구현해줬는데 @ResponseStatus얘가 그런걸 해줌.
}
