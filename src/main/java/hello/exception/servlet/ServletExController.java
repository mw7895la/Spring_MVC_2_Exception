package hello.exception.servlet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Controller
public class ServletExController  {

    @GetMapping("/error-ex")        //
    public void errorEx() {
        throw new RuntimeException("예외 발생 !!");     //이 예외가 WAS까지 전달되면, 서버 내부에서 처리할 수 없는 오류가 생겼구나 하고 500 반환
        //매핑이 안된 경로 요청하면 톰캣이 기본으로 제공하는 404 오류가 발생한다.

        //Exception 터진건 서블릿 컨테이너는 무조건 500으로 내보낸다. // 내가 직접 예외 상태코드랑 HTTP 상태코드를 작성하는 방법도 있다.
    }


    @GetMapping("/error-400")
    public void error400(HttpServletResponse response) throws IOException {
        response.sendError(400, "400 오류 !");        // F12 의 Status 확인 msg는 지금 안보인다.
    }

    @GetMapping("/error-404")
    public void error404(HttpServletResponse response) throws IOException {
        response.sendError(404, "404 오류 !");        // F12 의 Status 확인 msg는 지금 안보인다.
    }



    @GetMapping("/error-500")
    public void error500(HttpServletResponse response) throws IOException {
        response.sendError(500,"500 오류 !");         // F12 의 Status 확인
        //컨트롤러가 sendError 하면, 정상적인 흐름으로 리턴 리턴해서 WAS까지 올라왔다. WAS가 sendError 를 보고 response 내부에 오류 발생했다는 상태를 저장.
    }
}
