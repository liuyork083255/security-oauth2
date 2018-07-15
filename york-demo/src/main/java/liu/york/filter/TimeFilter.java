package liu.york.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * 1 实现过滤器 直接实现 Filter 接口
 * 2 将过滤器生效直接使用注解 @Component 注入到Bean容器中即可
 * 3 默认所有请求都会经过这个过滤器
 *      但是在很多情况下，我们会使用第三方的filter，但是它们filter是没有Component这个注解的
 *      这个时候就需要我们使用
 * 4 filter 机制是javax的，不是spring的东西，所有这种拦截机制的粒度是不能作用在方法的级别，更不能作用在参数级别
 *      url级别：filter
 *      方法级别：interceptor
 *      参数级别：aspectJ
 */
//@Component
public class TimeFilter implements Filter{
    /**
     * 过滤器初始化
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) {
        System.out.println("time filter init ...");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        System.out.println("time filter start");

        long start = System.currentTimeMillis();
        filterChain.doFilter(request, response);
        System.out.println("request spent time =====>" + (System.currentTimeMillis() - start));

        System.out.println("time filter finish");
    }

    /**
     * 过滤器销毁方法
     */
    @Override
    public void destroy() {
        System.out.println("time filter destroy ...");
    }
}
