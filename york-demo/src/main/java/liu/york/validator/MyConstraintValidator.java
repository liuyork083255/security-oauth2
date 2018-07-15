package liu.york.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 1 泛型第二个参数表示注解类能放在上面类型上，如果是String，那么这个注解只能注解在String 字段上
 * 2 spring 看见 ConstraintValidator 这个类，会自动将 MyConstraintValidator 注入到 Bean 容器中
 */
public class MyConstraintValidator implements ConstraintValidator<MyConstraint,Object> {
    /**
     * 这个方法是这个校验器初始化需要的逻辑
     * @param myConstraint
     */
    @Override
    public void initialize(MyConstraint myConstraint) {

    }

    /**
     * 真正校验的逻辑
     *
     *
     * @param o
     * @param constraintValidatorContext
     * @return
     */
    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {


        /*
         * 返回true 表示通过校验
         * 返回false 表示校验失败
         */
        return false;
    }
}
