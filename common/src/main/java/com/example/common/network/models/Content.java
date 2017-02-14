package com.example.common.network.models;

import com.google.gson.annotations.Expose;

/**
 * Created by myotive on 2/12/2017.
 */

public class Content {
    @Expose
    private String name;
    @Expose
    private String path;
    @Expose
    private String html_url;
    @Expose
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
