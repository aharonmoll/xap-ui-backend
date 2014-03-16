package com.gigaspaces.monitor.website.controllers;

import org.openspaces.admin.space.SpaceInstance;
import org.openspaces.core.GigaSpace;

/**
 * Created by aharon on 3/16/14.
 */
public class SpaceMonitoring {
    public String name;

    private SpaceInstance spaceInstance;

    public boolean isReadWriteOk() {
        try {
            Data data = new Data(0, "test");

            GigaSpace gspace = spaceInstance.getGigaSpace();

            gspace.write(data);
            gspace.read(data);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void setSpaceInstance(SpaceInstance space) {
        this.spaceInstance = space;
    }

    public void setName(String name) {
        this.name = name;
    }
}
