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
        try {
            logger.info("locators is [{}]", locators);
            return new AdminFactory().addLocator(locators).createAdmin();
        }
        catch (Exception e){
            logger.info("Excption" + e);
            return null;
        }
        finally {
            logger.info("Get the Admin");
        }
    }

    public String getLocators() {
        return locators;
    }

    public void setLocators(String locators) {
        this.locators = locators;
    }
}
