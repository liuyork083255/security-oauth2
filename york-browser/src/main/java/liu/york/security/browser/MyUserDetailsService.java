package liu.york.security.browser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * 自定义userDetailsService
 * 添加 @Component 可以让security自定配置 UserDetailsService
 */
@Component
public class MyUserDetailsService implements UserDetailsService{

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("登陆用户名：" + username);

        // User 还有一个很多的参数的构造方法，其中一些是否被冻结、是否锁定等等


        /*
         * 1 这里返回的对象其实是从数据库查询出来的
         * 2 然后 spring security 会将这个返回的对象 和 我们登陆的用户名和密码进行对比
         * 3 对比过程中会使用 加密器
         *      如果没有注入一个加密器，那么前端传入123456 就可以通过验证
         *      如果注入了一个加密器，那么security会使用这个加密器
         */
        return new User(
                username,       // 数据库查询到的用户名
                "123456",  // 数据库查询到的密码
                // 数据库查询到的的权限列表，需要告诉 security 当前拥有哪些权限
                AuthorityUtils.commaSeparatedStringToAuthorityList("ADMIN"));
    }
}
