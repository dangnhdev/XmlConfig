package org.dangnh.xmlconfig.Converter;

import java.lang.reflect.Method;

/**
 * Created by DangNH on 5/4/2016.
 */
public interface IConverter {
    Object convert(Method targetMethod, Class<?> targetType, String data);
}
