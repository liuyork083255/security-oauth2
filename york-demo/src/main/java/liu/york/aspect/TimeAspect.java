package liu.york.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;

import java.util.Arrays;

/**
 * 切片需要生效，则可以需要两个注解
 *
 * 请求 >  controller
 *      filter -> interceptor -> controllerAdvice -> aspect -> controller
 *      返回结果则是倒序返回，比如抛出异常
 *
 */
@Aspect
@Component
public class TimeAspect {

    /**
     * 切点
     *      1 {@link Before} 表示在切面执行前执行
     *      2 {@link After}  表示在切面执行完后执行
     *      3 {@link AfterThrowing} 表示切面如果跑出异常，就会执行
     *      4 {@link Around} 这个方式覆盖了上面三种，这也是我们经常使用的方式
     * 切面表达式
     *      第一个*：任何返回值
     *      中间：表示类名和方法，这里的第二个* 表示任意的方法
     *      后面的点点：表示任意的参数
     *
     *      如果需要细看，可以进入spring官网查看
     *          https://docs.spring.io/spring/docs/5.0.8.BUILD-SNAPSHOT/spring-framework-reference/core.html#aop
     *
     * 返回值：
     *      如果使用 around 包围切点，那么需要一个返回值，也就是被拦截的方法的返回值
     *
     */
    @Around("execution(* liu.york.web.controller.UserController.*(..))")
    public Object aspect(ProceedingJoinPoint point){

        // point 是包含拦截的方法信息的对象
        System.out.println("time aspect start");

        /*
         * 会返回被拦截方法的所有参数
         */
        Object[] args = point.getArgs();

        Arrays.asList(args).forEach(arg -> {
            // 可以根据自己的需求进行判断 或者 修改
            System.out.println(arg);
        });


        /*
         * 这个方法就是真正调用被拦截的方法
         * 其返回值就是被拦截的方法的返回值
         */
        Object result = null;
        try {
            result = point.proceed();
        } catch (Throwable throwable) {
            // 捕获 被拦截方法 跑出的异常   注意其类型是 Throwable， Exception使其子类
        }


        System.out.println("time aspect end");

        return result;

    }
}
