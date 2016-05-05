package org.dangnh.xmlconfig.annotation;

import org.dangnh.xmlconfig.Converter.Converter;

import java.lang.annotation.*;

/**
 * Created by DangNH on 5/5/2016.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface CustomConverter {
    Class<? extends Converter> value();
}
