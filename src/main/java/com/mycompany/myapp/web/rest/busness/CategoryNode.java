package com.mycompany.myapp.web.rest.busness;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vladimir on 18.12.2016.
 */
public class CategoryNode {
    String id;
    String parentId;

    List<CategoryNode> childs = new ArrayList<>();

    String name;
    String url;
    int level;

    public CategoryNode(String id){
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CategoryNode that = (CategoryNode) o;

        return getId().equals(that.getId());

    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
