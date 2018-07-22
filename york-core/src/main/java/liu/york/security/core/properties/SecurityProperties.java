package liu.york.security.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 这个注解会读取配置文件中所有已 york.security 开头的配置项
 */
@ConfigurationProperties(prefix = "york.security")
public class SecurityProperties {
    /**
     * 而 browser 结尾的都会读取到下面这个 browser 对象中
     * 比如 york.security.browser.Xxx  就映射到下面browser对象里面的Xxx属性里面
     */
    private BrowserProperties browser = new BrowserProperties();

    public BrowserProperties getBrowser() {
        return browser;
    }

    public void setBrowser(BrowserProperties browser) {
        this.browser = browser;
    }
}
