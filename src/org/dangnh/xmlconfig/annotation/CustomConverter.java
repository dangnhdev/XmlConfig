package org.dangnh.xmlconfig.annotation;

import org.dangnh.xmlconfig.Converter.Converter;

/**
 * Created by DangNH on 5/5/2016.
 */
public @interface CustomConverter {
    Class<? extends Converter> value();
}
