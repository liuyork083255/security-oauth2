package liu.york.security.core.properties;

public class BrowserProperties {

    // 这个会映射 login-page 字段   可以给出默认值，如果用户没有配置，那么就会使用默认值
    private String loginPage = "/login.html";

    // 注入枚举类型   配置文件中直接就写 loginType = REDIRECT  or loginType = JSON 就可以注入到这个属性来了
    private LoginType loginType = LoginType.JSON;

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }
}
