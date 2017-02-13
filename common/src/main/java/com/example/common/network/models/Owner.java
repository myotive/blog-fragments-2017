package com.example.common.network.models;

import com.google.gson.annotations.Expose;

import java.util.UUID;

/**
 * Created by myotive on 2/12/2017.
 */

public class Owner {
    @Expose
    String id;
    @Expose
    String login;
    @Expose
    String avatar_url;
}
