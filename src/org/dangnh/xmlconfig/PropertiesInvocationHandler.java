package org.dangnh.xmlconfig;

import org.dangnh.xmlconfig.Converter.DefaultConverter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by DangNH on 5/4/2016.
 */
class PropertiesInvocationHandler implements InvocationHandler {
    private final PropertiesManager propsManager;

    PropertiesInvocationHandler(PropertiesManager propsManager) {
        this.propsManager = propsManager;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return propertiesParser(method, args);
    }

    private Object propertiesParser(Method method, Object[] args){
        String key = MethodUtils.getKey(method);
        String value = propsManager.getProperty(key);
        if (value == null) return null;
        Object result = DefaultConverter.convert(method, method.getReturnType(), value);
        return result;
    }
}
