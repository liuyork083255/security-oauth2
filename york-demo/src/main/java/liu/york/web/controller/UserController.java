package liu.york.web.controller;


import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiOperation;
import liu.york.dto.User;
import liu.york.dto.UserQueryCondition;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
     * 2 可以映射是因为传入的参数格式是 username=LiuYork&password=123456
     *      如果传入的是 json 字符串，name必须 使用 @RequestBody 注解
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
//    @GetMapping("/page")
//    public List<User> page(String username, @PageableDefault(page = 1, size = 10, sort = {"username,asc"}) Pageable pageable){
//
//        System.out.println(JSON.toJSONString(pageable, true));
//
//        return Arrays.asList(new User(), new User(), new User());
//    }

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

    /**
     * 1 利用 hibernate 校验字段
     * @param user
     */
    @PostMapping("/json")
    @ApiOperation(value = "swagger api 接口说明")
    public void json(@Valid @RequestBody User user){
        System.out.println(JSON.toJSONString(user, true));
    }

    /**
     * 1 BindingResult 必须和 @Valid 搭配使用，如果加上了这个参数，那么校验失败是不会跑出异常，而是会进入方法体内部
     * 2 hibernate 校验实例：
     *      @NotNull    值不能为空
     *      @Null       值必须为空
     *      @Pattern    字符串必须满足正则表达式
     *      @Size       集合的数量必须满足 min max 之间
     *      @Email      字符串必须是Email 格式
     *      @Length     字符串长度必须满足 min max 之间
     *      @NotBlank   字符串必须不为空且长度不能为0
     *      @NoteEmpty  字符串不能为null，集合有元素   可以放在字符串也可以放在集合上
     *      @Range      数字必须在 min max 之间
     *      @SafeHtml   字符串是安全的 html
     *      @URL        字符串是合法 URL
     *      @Future     值必须是未来的日期
     *      @Past       值必须是过去的日期
     *      @Max        值必须小于等于value指定的值，不能注解在字符串类型属相上
     *      @Min        值必须大于等于value指定的值，不能注解在字符串类型属相上
     * @param user
     * @param errors
     */
    @PostMapping("/bind")
    public void bindingResult(@Valid @RequestBody User user, BindingResult errors){
        /*
         * 1 如果user 的注解校验没有通过，那么 errors.hasErrors() true
         *      if 里面是将所有错误信息循环并且打印出来
         */
        if(errors.hasErrors()){

            errors.getAllErrors().forEach(error -> {
                FieldError fieldError = (FieldError) error;
                String message = fieldError.getField() + "：" + error.getDefaultMessage();
                System.out.println(message);
            });
        }
        System.out.println("past quest ... ");
    }
}
































