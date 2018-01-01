/**
 * 
 */
package com.zg.kyrie.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author kyrie liu
 * @date Dec 28, 2017
 * @time 9:28:44 PM
 * @version 1.0
 */
@Documented
@Target({ElementType.TYPE,ElementType.METHOD})
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {
	String value() default "";
}
