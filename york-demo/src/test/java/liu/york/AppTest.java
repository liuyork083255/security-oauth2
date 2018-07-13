package liu.york;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * 单元测试
 *      1 如何使用 mockMvc 对象来模拟 rest ful 请求
 *      2 json path 的使用
 *      3 设置参数和 返回预期返回结果
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTest
{
    /**
     * Rigorous Test :-)
     */

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Before
    public void init(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldAnswerWithTrue() throws Exception {
        mockMvc.perform(get("/query/users")
                /*
                 * 设置请求参数  完成和方法参数映射
                 */
                .param("username","LiuYork")
                .param("age","100")
                /*
                 * 这是请求头参数
                 */
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                /*
                 * 希望返回结果是 OK  也就是 200 状态码
                 */
                .andExpect(status().isOk())
                /*
                 * 希望返回的结果是个集合
                 *      $ 表示 真个后台返回的数据，如果返回的是list，那么就是一个集合
                 *          具体使用可以去 GitHub  搜索 jsonpath
                 *      $.length() 表示集合的长度
                 *      .value(3)  表示长度希望是3
                 */
                .andExpect(jsonPath("$.length()").value(3));
    }

    /**
     * 分页查询调试
     * @throws Exception
     */
    @Test
    public void page() throws Exception {
        mockMvc.perform(get("/page")
                .param("username","LiuYork")
                .param("size","100")
                .param("page","10")
                .param("sort","username,desc")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    /**
     * rest 请求
     * @throws Exception
     */
    @Test
    public void info() throws Exception {
        mockMvc.perform(get("/user/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("york"))
        ;
    }

    /**
     * jsonView 测试
     * @throws Exception
     */
    @Test
    public void jsonView() throws Exception {
        String contentAsString = mockMvc.perform(get("/view/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        /*
         * 打印出来的信息可以发现   如果controller方法上加上了 @JsonView(User.UserSimpleView.class) 那么只会返回 username
         * 如果不加 这个注解，那么 username 和 password 都会返回
         */
        System.out.println(contentAsString);
    }
}























