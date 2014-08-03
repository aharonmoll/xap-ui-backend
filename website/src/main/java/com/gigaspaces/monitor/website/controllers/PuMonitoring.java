package com.gigaspaces.monitor.website.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by aharon on 3/16/14.
 */
public class PuMonitoring {
    private String name;

    private int plannedNumOfInstances;

    private int numOfActiveInstances;

    private static Logger logger = LoggerFactory.getLogger(PuMonitoring.class);

    public String getName() {
        return name;
    }

    public int getPlannedNumOfInstances(){ return plannedNumOfInstances;}

    public int getNumOfActiveInstances(){ return numOfActiveInstances;}

    public void setName(String name) {
        this.name = name;
    }

    public void setPlannedNumOfInstances(int num) { this.plannedNumOfInstances=num;}

    public void setNumOfActiveInstances(int num) { this.numOfActiveInstances=num;}
}
