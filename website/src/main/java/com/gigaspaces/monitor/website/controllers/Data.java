package com.gigaspaces.monitor.website.controllers;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceRouting;


@SpaceClass
public class Data {
    private Integer id;
    private String name;

    public Data(){};

    public Data(Integer id, String name)
    {
        this.id=id;
        this.name=name;
    }

    @SpaceId(autoGenerate=false)
    @SpaceRouting
    public Integer getId() { return id;}

    public void setId(Integer id) {  this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
}
