package org.dangnh.xmlconfig;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by DangNH on 5/4/2016.
 */
class PropertiesInvocationHandler implements InvocationHandler {
    private final PropertiesManager propsManager;

    public PropertiesInvocationHandler(PropertiesManager propsManager) {
        this.propsManager = propsManager;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

    }

    private Object propertiesParser(Method method, Object[] args){
        String key = MethodUtils.getKey(method);
        String value = propsManager.getProperty(key);
    }
}
