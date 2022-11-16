package com.techstart.elasticcore.util;

import org.springframework.data.elasticsearch.annotations.Document;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Meta {
    String index();

}

