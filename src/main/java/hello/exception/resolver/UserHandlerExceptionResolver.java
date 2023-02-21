package hello.exception.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.exception.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.event.TableColumnModelListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class UserHandlerExceptionResolver implements HandlerExceptionResolver {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        try {
            if (ex instanceof UserException) {
                log.info("userException resolver to 400");
                //Http header에 따라 2가지를 처리해야됨 HTML or JSON
                String acceptHeader = request.getHeader("accept");

                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                if ("application/json".equals(acceptHeader)) {
                    Map<String, Object> errorResult = new HashMap<>();
                    errorResult.put("ex", ex.getClass());       //ex 예외 클래스 정보 넣어준다
                    errorResult.put("message", ex.getMessage());

                    String result = objectMapper.writeValueAsString(errorResult);//Java 객체를 JSON으로

                    //ModelAndView를 반환하기 때문에 response에 다 세팅을 해줘야해.
                    response.setContentType("application/json");
                    response.setCharacterEncoding("utf-8");
                    //response.getWriter().write(errorResult);        //그냥 하면 타입 오류 errorResult를 JSON으로 바꿔줘야한다..
                    response.getWriter().write(result);
                    //이제 response에 http 응답 바디에 json데이터가 들어간다 response.getWriter().write() - Http응답바디에 넣을 것.
                    return new ModelAndView();      //비어있는걸 넘겨.  예외는 먹어버리지만 예외는 정상적으로 리턴되기 때문에 서블릿 컨테이너(WAS)까지 response가 그대로 전달될거야 그럼 바로 출력이 되고 끝나.
                    //출력이 되고 그냥 끝나버림. WAS로 오류가 넘어가는 일은 없음 즉, /error로 가는일은 없음.
                }else{
                    // text/html 의 경우

                    return new ModelAndView("error/500");   //templates/error/500.html이 호출된다.
                }
            }
        } catch (IOException e) {
            log.error("resolver ex ", e);
        }

        return null;
    }
}
