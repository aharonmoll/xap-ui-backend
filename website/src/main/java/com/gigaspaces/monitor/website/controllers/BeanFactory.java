package com.gigaspaces.monitor.website.controllers;

import org.openspaces.admin.Admin;
import org.openspaces.admin.AdminFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by aharon on 3/17/14.
 */
public class BeanFactory {

    public String locators;

    private static Logger logger = LoggerFactory.getLogger(BeanFactory.class);

    public Admin getAdmin(){
        logger.info("locators is [{}]", locators);
        return new AdminFactory().addLocator(locators).createAdmin();
    }

    public String getLocators() {
        return locators;
    }

    public void setLocators(String locators) {
        this.locators = locators;
    }
}
