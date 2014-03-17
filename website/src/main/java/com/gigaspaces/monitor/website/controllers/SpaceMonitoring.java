package com.gigaspaces.monitor.website.controllers;

import org.openspaces.admin.space.SpaceInstance;
import org.openspaces.core.GigaSpace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by aharon on 3/16/14.
 */
public class SpaceMonitoring {
    private String name;

    private SpaceInstance spaceInstance;

    private static Logger logger = LoggerFactory.getLogger(SpaceMonitoring.class);

    public boolean isReadWriteOk() {
        try {
            Data data = new Data( "test");

            GigaSpace gspace = spaceInstance.getGigaSpace();

            gspace.write(data);
            gspace.read(data);
            return true;
        } catch (Exception e) {
            logger.error("unable to write",e);
            return false;
        }
    }

    public String getName() {
        return name;
    }

    public void setSpaceInstance(SpaceInstance space) {
        this.spaceInstance = space;
    }

    public void setName(String name) {
        this.name = name;
    }
}
