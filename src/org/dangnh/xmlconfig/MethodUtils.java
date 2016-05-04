package org.dangnh.xmlconfig;

import org.dangnh.xmlconfig.annotation.DefaultValue;
import org.dangnh.xmlconfig.annotation.Key;

import java.lang.reflect.Method;

/**
 * Created by DangNH on 5/4/2016.
 */
final class MethodUtils {
    private MethodUtils(){}

    static String getKey(Method method){
        Key key = method.getAnnotation(Key.class);
        return (key == null) ? method.getName() : key.value();
    }

    static String getDefaultValue(Method method){
        DefaultValue defaultValue = method.getAnnotation(DefaultValue.class);
        return defaultValue == null? null : defaultValue.value();
    }
}
