package hello.exception.servlet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
public class ErrorPageController {
    //오류가 났을 때 보여주기 위한 컨트롤러  (발생시키는 컨트롤러가 아님 !)
    //WAS가 재요청시 여기로 온다 !

    //RequestDispatcher 상수로 정의되어 있음     // RequestDispatcher.ERROR_EXCEPTION 이렇게 들어있음.
    public static final String ERROR_EXCEPTION = "javax.servlet.error.exception";
    public static final String ERROR_EXCEPTION_TYPE = "javax.servlet.error.exception_type";
    public static final String ERROR_MESSAGE = "javax.servlet.error.message";
    public static final String ERROR_REQUEST_URI = "javax.servlet.error.request_uri";
    public static final String ERROR_SERVLET_NAME = "javax.servlet.error.servlet_name";
    public static final String ERROR_STATUS_CODE = "javax.servlet.error.status_code";
    //exception이 터져서 WAS까지 가면 WAS가 request.setAttribute 로 어떤 uri에서 오류가 발생했는지 오류 메시지는 뭔지 다 담아놓는다.


    @RequestMapping("/error-page/404")
    public String errorPage404(HttpServletRequest request, HttpServletResponse response) {
        log.info("error page 404 ");
        printErrorInfo(request);
        return "error-page/404";
    }

    @RequestMapping("/error-page/500")
    public String errorPage500(HttpServletRequest request, HttpServletResponse response) {
        log.info("error page 500 ");
        printErrorInfo(request);
        return "error-page/500";
    }

    @RequestMapping(value = "/error-page/500", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> errorPage500Api(HttpServletRequest request, HttpServletResponse response) {
        //클라이언트가 요청시 Accept가 Application/json으로 되어있어야 여기가 호출된다.
        //타입이 ResponseEntity이니 리턴될때 다시 Json으로.
        log.info("Api ErrorPage 500");
        Map<String, Object> result = new HashMap<>();
        Exception ex = (Exception)request.getAttribute(ERROR_EXCEPTION);        //익셉션을 꺼낸다
        result.put("status",request.getAttribute(ERROR_STATUS_CODE));           //500
        result.put("message", ex.getMessage());          //우리가 던졌던 "잘못된 사용자"

        //Http  상태 코드를 정의해주자
        Integer statusCode = (Integer)request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);//숫자 코드라 정수로 받을 수 있음.
        return new ResponseEntity<>(result, HttpStatus.valueOf(statusCode));        //상태코드, HttpStatus의 valueof메소드      //다시 JSON으로 리턴
    }

    private void printErrorInfo(HttpServletRequest request) {
        log.info("ERROR_EXCEPTION : {}",request.getAttribute(ERROR_EXCEPTION));
        log.info("ERROR_EXCEPTION_TYPE : {}", request.getAttribute(ERROR_EXCEPTION_TYPE));
        log.info("ERROR_MESSAGE : {}", request.getAttribute(ERROR_MESSAGE));
        log.info("ERROR_REQUEST_URI : {}", request.getAttribute(ERROR_REQUEST_URI));
        log.info("ERROR_SERVLET_NAME : {}", request.getAttribute(ERROR_SERVLET_NAME));
        log.info("ERROR_STATUS_CODE : {}", request.getAttribute(ERROR_STATUS_CODE));

        log.info("dispatchType ={}", request.getDispatcherType());
    }
}
