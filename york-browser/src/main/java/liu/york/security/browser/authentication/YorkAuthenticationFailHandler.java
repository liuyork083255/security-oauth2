package liu.york.security.browser.authentication;

import com.alibaba.fastjson.JSON;
import liu.york.security.core.properties.LoginType;
import liu.york.security.core.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义失败处理器
 * 这个逻辑就是在认证的时候 比如用户名没有找打，密码没有匹配到，security就会跑出异常，默认是security自己处理，如果我们这里
 * 自己实现，那么抛出异常就会被捕获，然后跳转到这个失败处理器来
 *
 * spring-security 默认的处理器是 {@link org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler}
 *      认证失败后会默认跳转到一个失败的页面上，如果这个页面我们配置了就会跳转到我们的失败页面上
 *      一般我们可以直接继承这个类
 *  如果不用这个特性，自己也可以直接实现 {@link AuthenticationFailureHandler}
 *
 */
@Component("yorkAuthenticationFailHandler")
public class YorkAuthenticationFailHandler extends SimpleUrlAuthenticationFailureHandler {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        logger.info("login fail");

        if(LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())){
            // 默认如果不设置状态码，那么默认是200
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(JSON.toJSONString(exception));
        }else {
            // 如果请求类型是 不是 json 类型，那么就调用父类的方法，其实就是跳转
            super.onAuthenticationFailure(request, response, exception);
        }
    }
}
