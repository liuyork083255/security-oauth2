package liu.york.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 捕获指定异常
     * @param e
     * @return
     */
    @ExceptionHandler(UserNotExistException.class)
    public Map<String, Object> handlerUserNotExistException(UserNotExistException e){
        System.out.println("catch error of UserNotExistException  =====> " + e.getMessage());
        return  null;
    }

}
