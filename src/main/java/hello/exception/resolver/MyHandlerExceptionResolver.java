package hello.exception.resolver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class MyHandlerExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        //ex라는 익셉션이 넘어왔어 그러면 정상적인 ModelAndView로 반환해주면 돼.

        log.info("call resolver", ex);       //exception 같은 경우는 { } 안해줘도 된다.

        try {
            if (ex instanceof IllegalArgumentException) {
                log.info("IllegalArgumentException resolver to 400");
                //서버 내부에서 뭐든지 IllegalArgumentException 터지면 무조건 400 에러로 한다.
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
                //이 예외를 여기서 먹어버리자. 400과 익셉션의 메시지를 담는다.
                return new ModelAndView();
                //그리고 새로운 ModelAndView를 반환한다. 빈값으로 넘기면 정상적으로 WAS까지 리턴이 된다. 정상적으로 리턴하고 여기서는 예외를 먹었다. WAS에서 sendError 호출하고 400으로 왔네? 하고 인식함.
            }
        }catch(IOException e){
            log.error("resolver ex", e);
        }
        return null;        //null로 리턴하면 다음 ExceptionResolver가 있는지 보고 없으면 서블릿 밖으로 예외가 다시 터진다. 즉 여긴 IllegalArgumentException만 400으로 바꿔준다.
        //RuntimeExcpetion이면 return이 null이고 WAS로 가서 원래대로 500에러 터짐.
    }
}
