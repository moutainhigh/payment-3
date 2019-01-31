package com.f.dpay.tunnel;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(TYPE)
public @interface Tunnel {

	int tunnelId() default -1;

	String desc() default "";
}
