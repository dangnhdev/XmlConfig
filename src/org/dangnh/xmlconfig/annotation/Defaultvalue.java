package org.dangnh.xmlconfig.annotation;

import java.lang.annotation.*;

/**
 * Created by DangNH on 5/4/2016.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface DefaultValue {
    String value();
}
