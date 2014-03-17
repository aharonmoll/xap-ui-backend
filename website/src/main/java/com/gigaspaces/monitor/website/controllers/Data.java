package com.gigaspaces.monitor.website.controllers;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceRouting;


@SpaceClass
public class Data {
    private String id;
    private String name;

    public Data(){};

    public Data(String name) {
        this.name = name;
    }

    public Data(String id, String name)
    {
        this.id=id;
        this.name=name;
    }

    @SpaceId(autoGenerate=true)
    public String getId() { return id;}

    public void setId(String id) {  this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
}
