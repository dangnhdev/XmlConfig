package org.dangnh.xmlconfig.proxytest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * Created by dangg on 5/4/2016.
 */
public class TestProxy {
    public static void main(String[] args) {
        InvocationHandler handler = new MyInvocationHandler();
        Foo f = (Foo) Proxy.newProxyInstance(
                Foo.class.getClassLoader(),
                new Class[]{Foo.class},
                handler);
        System.out.println(f.bar());
    }
}
