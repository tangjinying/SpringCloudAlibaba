package com.tjy.logging.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {
	/**
	 * 日志内容
	 * @return {String}
	 */
	String value();
}