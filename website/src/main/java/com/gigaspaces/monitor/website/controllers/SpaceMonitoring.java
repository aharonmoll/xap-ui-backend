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
            Data data1 = new Data(0, "test");
            Data data2 = new Data(1, "test");

            GigaSpace gspace = spaceInstance.getGigaSpace();

            gspace.write(data1, 100);
            gspace.write(data2, 100);
            gspace.take(data1, 100);
            gspace.take(data2, 100);
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
