package liu.york.config;

import liu.york.filter.TimeFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class WebConfig {

    /**
     * 1 如果我们自己的filter 不想使用@Component注入，也可以使用下面这个方法
     * 2 使用这种方法还有好处就是 自己可以控制对那些 URL 进行拦截生效
     *
     * @return
     */
//    @Bean
//    public FilterRegistrationBean timeFilter(){
//        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//        registrationBean.setFilter(new TimeFilter());
//        registrationBean.setUrlPatterns(Arrays.asList("/hello/*","/user/*"));
//        return registrationBean;
//    }
}
