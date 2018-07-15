package liu.york.web.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

@RestController
public class AsyncController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 进入主线程，此线程是tomcat管理的
     * 如果这个线程池满了，那么别的请求再来，就会阻塞
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/order1")
    public String order1() throws InterruptedException {
        logger.info("main thread start ...");

        TimeUnit.SECONDS.sleep(5);

        logger.info("main thread end ...");
        return "success";
    }

    /**
     * 这种方式实现可以让主线程回收去处理别的请求
     * 因为主线程里面启动了 callable 就会立马往后执行，而不会等到 callable 结束
     * 而这里 Callable 返回值String会直接返回给前台
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/order2")
    public Callable<String> order2() throws InterruptedException {
        logger.info("主线程开始");

        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                logger.info("副线程开始");
                TimeUnit.SECONDS.sleep(5);
                logger.info("副线程结束");
                return "success";
            }
        };

        logger.info("主线程结束");
        return callable;
    }
}
