package com.example.common.network.models;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by myotive on 2/12/2017.
 */

public class Owner implements Serializable {
    @Expose
    private String id;
    @Expose
    private String login;
    @Expose
    private String avatar_url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }
}
