package com.awstan.timetaskutil.timeTask.annotation;

import java.lang.annotation.*;

/**
 * @Author pw7563
 * @Date 2025/1/13 10:43
 * usage
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface Description {

    String description() default "";

}
