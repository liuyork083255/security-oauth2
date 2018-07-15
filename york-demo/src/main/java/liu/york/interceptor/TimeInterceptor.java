package liu.york.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 1 拦截器是用于拦截方法级别的
 * 2 跟过滤器不同的是：过滤器直接加上 @Component 就可以生效
 *      但是 interceptor 加了这个注解，是不能生效的
 * 3 拦截器拦截的粒度只能是方法名，不能获取到参数
 *      需要拦截参数，则是 aspectJ
 */
@Component
public class TimeInterceptor implements HandlerInterceptor{
    /**
     * 拦截方法前会被调用
     * @param httpServletRequest
     * @param httpServletResponse
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        System.out.println("preHandler");

        /*
         * 1 interceptor 可以拦截粒度在方法级别，就是靠第三个参数
         * 2 可以转换为 HandlerMethod 类型，然后获取方法名，判断是否要进行拦截
         */
        System.out.println(((HandlerMethod)handler).getBean().getClass().getName());
        System.out.println(((HandlerMethod)handler).getMethod().getName());

        /*
         * 返回true：放心，可以进入后面方法
         * 返回false：进行拦截
         */
        return true;
    }

    /**
     * 这个方法是在被拦截的方法执行完后会被调用  注意：如果被拦截的方法抛出异常，那么是不会执行
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle");


    }

    /**
     * 这个方法是在被拦截的方法执行后会被调用   注意：跟上面的却别是被拦截的方法不管有没有跑出异常，都会执行这个方法
     * 1 第三个参数 exception
     *      就是用来获取异常的，如果被拦截方法没有跑出异常，那么这个参数是 null
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param e
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        System.out.println("afterCompletion");



    }
}
