package org.dangnh.xmlconfig.Converter;

import java.lang.reflect.Method;

/**
 * Created by DangNH on 5/4/2016.
 */
public class ToPrimitiveConverter implements IConverter{

    @Override
    public Object convert(Method targetMethod, Class<?> targetType, String data) {
        if (targetType.isPrimitive()){
            if (targetType == Byte.TYPE) return Byte.parseByte(data);
            if (targetType == Short.TYPE) return Short.parseShort(data);
            if (targetType == Integer.TYPE) return Integer.parseInt(data);
            if (targetType == Long.TYPE) return Long.parseLong(data);
            if (targetType == Boolean.TYPE) return Boolean.parseBoolean(data);
            if (targetType == Float.TYPE) return Float.parseFloat(data);
            if (targetType == Double.TYPE) return Double.parseDouble(data);
        }
        return null;
    }
}