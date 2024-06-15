package com.jyong.springboot.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Jyong
 * @date 2024/6/10 11:07
 * @description
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RoleAuthorization {
    Role[] value() default {};
}

enum Role {
    /**
     * 管理员
     */
    ADMIN,

    /**
     * 用户
     */
    USER,
    /**
     * 所有人
     */
    EVERYONE;
}
