package org.dangnh.xmlconfig;

import java.lang.annotation.Documented;

/**
 * Created by dangg on 5/3/2016.
 */
//@Documented
public @interface ClassPreamble {
    String author();
    String date();
    int currentRevision() default 1;
    String lastModified() default "N/A";
    String lastModifiedBy() default "N/A";
    // Note use of array
    String[] reviewers();
}