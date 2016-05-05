package org.dangnh.xmlconfig.Converter;

import org.dangnh.xmlconfig.annotation.CustomConverter;

import java.lang.reflect.Method;

/**
 * Created by DangNH on 5/5/2016.
 */
public enum DefaultConverter {

    ToPrimitiveConverter {
        @Override
        Object tryConvert(Method targetMethod, Class<?> targetType, String data) {
            if (targetType.isPrimitive()) {
                if (targetType == Byte.TYPE) return Byte.parseByte(data);
                if (targetType == Short.TYPE) return Short.parseShort(data);
                if (targetType == Integer.TYPE) return Integer.parseInt(data);
                if (targetType == Long.TYPE) return Long.parseLong(data);
                if (targetType == Boolean.TYPE) return Boolean.parseBoolean(data);
                if (targetType == Float.TYPE) return Float.parseFloat(data);
                if (targetType == Double.TYPE) return Double.parseDouble(data);
            }
            return SpecialConvertedResult.SKIP;
        }
    },
    ConverterAnnotation {
        @Override
        Object tryConvert(Method targetMethod, Class<?> targetType, String data) {
            CustomConverter converterAnnotation = targetMethod.getAnnotation(CustomConverter.class);
            if (converterAnnotation == null) return SpecialConvertedResult.SKIP;

            Class<? extends Converter> customConverterClass = converterAnnotation.value();
            Converter converter;
            try {
                converter = customConverterClass.newInstance();
            } catch (InstantiationException e) {
                throw new RuntimeException("Converter class can't be instantiated", e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Converter class can't be accessed", e);
            }
            Object convertedResult = converter.convert(targetMethod, targetType, data);
            return convertedResult;
        }
    },
    ToStringConverter {
        @Override
        Object tryConvert(Method targetMethod, Class<?> targetType, String data) {
            return data;
        }
    };

    public static Object convert(Method targetMethod, Class<?> targetType, String data) {
        for (DefaultConverter converter : values()) {
            Object convertedValue = converter.tryConvert(targetMethod, targetType, data);
            if (convertedValue != SpecialConvertedResult.SKIP)
                return convertedValue;
        }
        return null;
    }

    abstract Object tryConvert(Method targetMethod, Class<?> targetType, String data);

    private enum SpecialConvertedResult {
        SKIP
    }
}
