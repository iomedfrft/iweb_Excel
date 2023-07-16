package ExcelInOut.Service.Entity;

import java.lang.annotation.*;

/**
 * @author hss
 * @date 2023/7/14 18:48
 */
@Target(ElementType.FIELD)//使用范围
@Retention(RetentionPolicy.RUNTIME)//生命周期
@Documented//该注解被包含在Javadoc中
public @interface Excel {
    /**
     * 表头
     * @return
     */
    String value() default "";

    /**
     * 列索引
     * @return
     */
    int columnIndex() default 0;
}
