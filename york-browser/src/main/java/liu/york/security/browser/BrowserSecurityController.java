package liu.york.security.browser;


import liu.york.security.browser.support.SimpleResponse;
import liu.york.security.core.properties.SecurityProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class BrowserSecurityController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 这个是spring用于缓存请求的cache
     * 这里的用处是：
     *      1 用户第一次请求资源 比如 /user/info
     *      2 由于没有登陆，那么会被拦截，跳转到一个登陆页面，比如这里的 /authentication/require
     *      3 如果登陆成功后，那么需要重新跳回到原来用户请求的资源路径  就是上面的/user/info
     *      4 这样做的好处就是用户不用再次输入路径请求资源了
     */
    private RequestCache requestCache = new HttpSessionRequestCache();

    /**
     * 用于跳转的工具
     */
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 当需要认证是跳转到这里
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/authentication/require")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED) // 如果if 里面的逻辑跳转了，那么这个状态码是不会生效的，只有指行下面的return才会生效
    public SimpleResponse requireAuth(HttpServletRequest request, HttpServletResponse response) throws IOException {
        /*
         * 这个地方就会保存用户第一次请求的url，比如第一次请求 /user/info 路径，
         * 那么跳转到这个方法以后，savedRequest就会保存这个地址，而不是保存这个controller 的 /authentication/require 地址
         */
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        if (savedRequest != null) {
            // 这个 targetUrl 就是跳转到这个请求之前的url   比如 /user/info 请求
            String targetUrl = savedRequest.getRedirectUrl();
            logger.info("引发跳转的请求是:" + targetUrl);
            // 这里只会对html结尾的请求才会生效
            if (StringUtils.endsWithIgnoreCase(targetUrl, ".html")) {
                // 第三个参数就是要跳转的 URL    如果这里跳转了，那么下面的逻辑是不会再执行的，类似 return 功能
                redirectStrategy.sendRedirect(request, response, securityProperties.getBrowser().getLoginPage());
            }
        }

        return new SimpleResponse("访问的服务需要身份认证，请引导用户到登录页");
    }
}
