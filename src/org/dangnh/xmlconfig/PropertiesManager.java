package org.dangnh.xmlconfig;

import org.dangnh.xmlconfig.annotation.Source;
import org.dangnh.xmlconfig.exception.SourceNotDefinedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * Created by DangNH on 5/4/2016.
 */
class PropertiesManager {
    private static final Logger log = LoggerFactory.getLogger(PropertiesManager.class);
    private final Properties properties = new Properties();

    PropertiesManager(Class<?> clazz) {
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
        XmlToPropsParser parser = new XmlToPropsParser();
        String source = getSourceString(clazz);
        try {
            parser.load(properties, source);
        } catch (IOException e) {
            log.error("Error while load config from file", e);
        }
    }

    private static String getSourceString(Class<?> clazz) {
        Source source = clazz.getAnnotation(Source.class);
        if (source == null) throw new SourceNotDefinedException();
        return source.value();
    }

    String getProperty(String key) {
        return properties.getProperty(key);
    }
}
