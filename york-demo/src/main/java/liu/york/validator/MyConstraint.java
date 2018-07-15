package liu.york.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD}) // 该注解可以存放的位置  方法和属性字段
@Retention(RetentionPolicy.RUNTIME) // 运行时的注解
// 这个注解 java 里面用于校验使用的，validatedBy 表示这个注解处理的逻辑用哪个类来处理
@Constraint(validatedBy = MyConstraintValidator.class)
public @interface MyConstraint {

    // 自定义一个校验器，必须有下面三个属性       教程是说用处不大

    String message() default "默认错误信息";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
