package hello.exception;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class WebServerCustomizer implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {
    //여기에 ErrorPage 등록하고  ErrorPageController에서 매핑 설정.

    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        //오류를 커스터마이징 하는것.
        ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND, "/error-page/404");//404 예외가 발생하면 /error-page/400 이라는 컨트롤러를 호출해라.

        ErrorPage errorPage500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error-page/500");

        ErrorPage errorPageEx = new ErrorPage(RuntimeException.class, "/error-page/500");       //RuntimeException 뿐만 아니라 그 자식 예외들도 /error-page/500 컨트롤러로 보내준다.
        //이렇게 만들고 이제 등록하자.

        factory.addErrorPages(errorPage404, errorPage500, errorPageEx);

        // ** 서버 내부에서 오류가 발생해서 WAS까지 올라왔어 그럼  WAS가 다시 /error-page-404처럼 경로를 호출해서 컨트롤러가 호출된다.
        //그래서 오류 페이지를 처리할 컨트롤러가 필요하다.

        //서블릿 컨테이너는 ErrorPage 정보를 가질 수 있다.
        //스프링 부트가 로딩 될 때 이러한 ErrorPage 정보를 생성해서 서블릿 컨테이너에 전달한다.

        //빈 등록만 했는데, 실행되었다면 어디선가 해당 빈을 실행 했다는 것.  -->  스프링부트는 빈 등록만 하면 내부에 지정된 로직에 따라 실행되는 것들이 많다.
    }
}
