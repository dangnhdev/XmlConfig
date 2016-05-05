package org.dangnh.xmlconfig;

import java.lang.reflect.Proxy;

/**
 * Created by DangNH on 5/4/2016.
 */
public class ConfigFactory {

    @SuppressWarnings("unchecked")
    public static <T> T create(Class<T> clazz) {
        PropertiesManager propsMng = new PropertiesManager(clazz);
        PropertiesInvocationHandler handler = new PropertiesInvocationHandler(propsMng);
        T proxy = (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, handler);
        return proxy;
    }
}
