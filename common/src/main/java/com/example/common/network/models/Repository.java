package com.example.common.network.models;

import com.google.gson.annotations.Expose;

import org.joda.time.DateTime;

import java.io.Serializable;

/**
 * Created by myotive on 2/12/2017.
 */

public class Repository implements Serializable {
    @Expose
    private String id;
    @Expose
    private String name;
    @Expose
    private String description;
    @Expose
    private Owner owner;
    @Expose
    private DateTime created_at;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public DateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(DateTime created_at) {
        this.created_at = created_at;
    }
}
