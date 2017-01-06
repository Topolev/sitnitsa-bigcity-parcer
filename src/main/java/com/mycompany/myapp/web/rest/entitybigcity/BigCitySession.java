package com.mycompany.myapp.web.rest.entitybigcity;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.MapUtils;

import java.util.*;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class BigCitySession {
    private Map<String, Object> attributes;

    public BigCitySession() {
        attributes = Collections.synchronizedMap(new HashMap<String, Object>());
    }

    public <T> T get(Object key) {
        return (T) attributes.get(key);
    }

    public <T> T remove(Object key) {
        return (T) attributes.remove(key);
    }

    public <T> T put(Object key, T value) {
        return (T) attributes.put(key.toString(), value);
    }
}
