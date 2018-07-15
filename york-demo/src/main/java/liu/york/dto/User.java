package liu.york.dto;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModelProperty;
import liu.york.validator.MyConstraint;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

/**
 * 使用 JsonView 三步骤
 *      1 使用接口来声明多个视图
 *      2 在值对象的get方法上指定视图
 *      3 在Controller方法上指定视图
 */
public class User {

    /**
     * 使用 @JsonView 来控制返回结果 哪些字段需要展示，哪些字段不需要
     */
    public interface UserSimpleView {}
    public interface UserDetailView extends UserSimpleView {}

    @NotBlank(message = "自定义异常信息...")
    private String username;
    @ApiModelProperty(value = "swagger 字段说明")
    private String password;
    /*
     * 对于时间类型，最好使用时间戳，因为不同地方可能展示类型不同，所以这里采用时间戳
     * 然后将时间戳转换成自己想要的格式
     */
    @MyConstraint //自定义的校验器
    private Date birthday;

    public String getUsername() {
        return username;
    }

    /**
     * 控制展示视图
     * @param username
     */
    @JsonView(UserSimpleView.class)
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    /**
     * 控制展示视图
     * 由于 UserDetailView 继承 UserSimpleView ，所以展示 UserDetailView 的时候也会展示 UserSimpleView
     * @param password
     */
    @JsonView(UserDetailView.class)
    public void setPassword(String password) {
        this.password = password;
    }
}
