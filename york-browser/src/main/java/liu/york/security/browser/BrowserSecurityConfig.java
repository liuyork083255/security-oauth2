package liu.york.security.browser;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.access.ExceptionTranslationFilter;

/**
 *      security 过滤链是由多个过滤器组成的链，一个请求进入，都会一个个经过这个链，
 * 而链中的过滤器则会根据自己的规则判断是否需要拦截，请求最后会进入 {@link FilterSecurityInterceptor} 过滤器
 * 而这个过滤会判断当前请求是否可以访问真正的资源，判断依据就是根据 {@link BrowserSecurityConfig#configure(HttpSecurity)} 里面配置
 *      在{@link FilterSecurityInterceptor} 过滤器前面会有一个 {@link ExceptionTranslationFilter} 过滤器，
 * 就是用于捕获FilterSecurityInterceptor抛出的异常，然后根据异常类型和信息，引导用户登录时form 还是 basic 或者是自己的实现逻辑
 * 案例4-2：
 *      1 用户第一次直接请求资源，没有登录，那么这个请求会直接到最后一个过滤器 FilterSecurityInterceptor，因为前面的过滤器比如 form basic
 *          都是有自己的过滤规则的，比如form默认只会拦截 /login+POST 请求，所以前面的这些过滤器都不会生效
 *      2 在 FilterSecurityInterceptor 里面会根据当前请求是否需要认证，依据是 {@link BrowserSecurityConfig#configure(HttpSecurity)} 里面配置
 *          如果需要认证，那么会抛出异常，这个异常会被 ExceptionTranslationFilter 捕获
 *      3 在 ExceptionTranslationFilter 里面会根据抛出异常的类型，重定向登录页面(前提是已经配置了)
 *      4 这个时候用户页面就会出现登录的字样，比如 form 表单，用户输入后再次提交，那么请求就会被相应的过滤器拦截(前提是已经配置了过滤器)
 *      5 在认证通过后 会跳转到 用户第一次请求的 URL， 这个时候又会进入 FilterSecurityInterceptor，这个时候这个过滤器发现已经认证和授权，放行!!!
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter{

    /**
     * 覆盖这个方法，那么自己需要提供自己一个认证逻辑
     * 默认不覆盖那么 security 会使用 basic 过滤器
     *
     * 如果覆盖整个方法没有提供一个过滤器，那么security是不会生效的，也就是所有资源都是可以访问的
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.formLogin()//自定义表单过滤器
        http.httpBasic()//自定义默认basic过滤器
                .and()
                .authorizeRequests()//请求授权，说明下面的配置都是授权配置
                .anyRequest()//对所有的请求
                .fullyAuthenticated();//都需要身份认证
    }
}
