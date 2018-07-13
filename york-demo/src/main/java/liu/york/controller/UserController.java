package liu.york.controller;


import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonView;
import liu.york.dto.User;
import liu.york.dto.UserQueryCondition;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class UserController {

    /**
     *  1 参数映射
     *  2 是否必须
     *  3 参数名称可以重命名
     *  4 默认值设置
     * @param username
     * @return
     */
    @GetMapping("/user")
    public List<User> user(@RequestParam(required = true, name = "username", defaultValue = "") String username){
        System.out.println(username);
        return Arrays.asList(new User(), new User(), new User());
    }


    /**
     * 1 这里没有加 @RequestBody 注解也是可以完成映射的
     * @param userQueryCondition
     * @return
     */
    @GetMapping("/query/users")
    public List<User> queryUser(UserQueryCondition userQueryCondition){
        System.out.println(JSON.toJSONString(userQueryCondition, true));;
        return Arrays.asList(new User(), new User(), new User());
    }

    /**
     * 1 spring-data 里面的一个分页对象
     * 2 如果前台没有传分页数据，那么默认
     *      @PageableDefault(page = 1, size = 10, sort = {"username,asc"})
     *
     * @return
     */
    @GetMapping("/page")
    public List<User> page(String username, @PageableDefault(page = 1, size = 10, sort = {"username,asc"}) Pageable pageable){

        System.out.println(JSON.toJSONString(pageable, true));

        return Arrays.asList(new User(), new User(), new User());
    }

    /**
     * 1 rest 请求参数映射到方法参数中
     *      url 使用{}  方法中使用 @PathVariable   默认名称保持一致  比如都是 id
     * 2 如果参数不一致
     *      使用 @PathVariable(name = "id")  下面就会将url中的id 传入给 userId
     * 3 URL 中的 /user/{id} 中的{} 可以使用正则表达式
     *      比如必须 整数  ====>  /user/{id:\\d+}
     *
     * @param userId
     * @return
     */
    @GetMapping("/user/{id:\\d+}")
    public User getInfo(@PathVariable(name = "id") String userId){
        User user = new User();
        System.out.println("id   =============>" + userId);
        user.setUsername("york");
        user.setPassword("123456");
        return user;
    }


    /**
     * 测试用 @JsonView
     * @return
     */
    @JsonView(User.UserSimpleView.class)
    @GetMapping("/view/user")
    public User getUser(){
        User user = new User();
        user.setUsername("york");
        user.setPassword("123456");
        return user;
    }
}
































