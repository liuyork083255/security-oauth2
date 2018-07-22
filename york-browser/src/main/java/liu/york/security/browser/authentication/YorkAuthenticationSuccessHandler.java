package liu.york.security.browser.authentication;

import com.alibaba.fastjson.JSON;
import liu.york.security.core.properties.LoginType;
import liu.york.security.core.properties.SecurityProperties;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登陆成功自定义处理handler
 * security 处理认证成功后会有默认的成功处理器，它的一个特点就是如果 用户访问 /user/info 然后转到登陆页面，登陆成功后就又会跳回/user/info
 * 这个逻辑就是默认处理器实现的
 *
 * 由于默认成功处理器有一个跳转回去的功能，所以我们们一般会直接继承这个类 {@link SavedRequestAwareAuthenticationSuccessHandler}
 *      如果不需要这个特性，就完全可以实现接口 {@link AuthenticationSuccessHandler}
 *
 */
@Component("yorkAuthenticationSuccessHandler")
public class YorkAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SecurityProperties securityProperties;

    /**
     *
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        logger.info("login success");

        if(LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())){

            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(JSON.toJSONString(authentication));
        }else {
            // 如果请求类型是 不是 json 类型，那么就调用父类的方法，其实就是跳转
            super.onAuthenticationSuccess(request, response, authentication);
        }


    }
}
