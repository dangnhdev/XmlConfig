package org.dangnh.xmlconfig;

import org.dangnh.xmlconfig.annotation.Source;
import org.dangnh.xmlconfig.exception.SourceNotDefinedException;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * Created by DangNH on 5/4/2016.
 */
class PropertiesManager {
    private final Class<?> clazz;
    private final Properties properties = new Properties();

    public PropertiesManager(Class<?> clazz) {
        this.clazz = clazz;
        loadDefault(properties, clazz);
        loadFromFile(properties, clazz);
    }

    private static void loadDefault(Properties properties, Class<?> clazz){
        Method[] methods = clazz.getMethods();
        for (Method method: methods){
            String key = MethodUtils.getKey(method);
            String defaultValue = MethodUtils.getDefaultValue(method);
            if (defaultValue != null) properties.put(key, defaultValue);
        }
    }

    private static void loadFromFile(Properties properties, Class<?> clazz){
        XmlToPropParser parser = new XmlToPropParser();
        String source = getSourceString(clazz);
        try {
            parser.load(properties, source);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getSourceString(Class<?> clazz) {
        Source source = clazz.getAnnotation(Source.class);
        if (source == null) throw new SourceNotDefinedException();
        return source.value();
    }

    public String getProperty(String key){
        return properties.getProperty(key);
    }
}
