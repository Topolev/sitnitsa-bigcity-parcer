package com.mycompany.myapp.web.rest.busness;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vladimir on 18.12.2016.
 */
public class ExtractProductLinks {
    String rootUrl;
    List<Link> categoryUrls = new ArrayList<>();
    String selector;

    public ExtractProductLinks(){}

    public String getRootUrl() {
        return rootUrl;
    }

    public List<Link> getCategoryUrls() {
        return categoryUrls;
    }

    public String getSelector() {
        return selector;
    }
}
