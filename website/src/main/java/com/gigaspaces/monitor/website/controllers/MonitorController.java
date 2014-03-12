package com.gigaspaces.monitor.website.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by aharon on 3/12/14.
 */
@Controller
public class MonitorController {


    @RequestMapping(value = "/xapstatistics" , method = RequestMethod.GET)
    @ResponseBody
    public XapStatistics printWelcome() {
        XapStatistics result = new XapStatistics();


        //code that collects statistics

        return result;
    }

    public static class MachineStatistics {
        public String machineId = "";
        public int componentsCount = 0;
        public int availableMemory = 0;
        public int cpu = 0;
        public int readWriteTime = 0;

        public String getMachineId() {
            return machineId;
        }

        public void setMachineId(String machineId) {
            this.machineId = machineId;
        }

        public int getComponentsCount() {
            return componentsCount;
        }

        public void setComponentsCount(int componentsCount) {
            this.componentsCount = componentsCount;
        }

        public int getAvailableMemory() {
            return availableMemory;
        }

        public void setAvailableMemory(int availableMemory) {
            this.availableMemory = availableMemory;
        }

        public int getCpu() {
            return cpu;
        }

        public void setCpu(int cpu) {
            this.cpu = cpu;
        }

        public int getReadWriteTime() {
            return readWriteTime;
        }

        public void setReadWriteTime(int readWriteTime) {
            this.readWriteTime = readWriteTime;
        }
    }

    public static class XapStatistics {
        public int gscs = 0;
        public int gsms = 0;
        List<MachineStatistics> machineStatisticsList = new LinkedList<MachineStatistics>();

        public int getGscs() {
            return gscs;
        }

        public void setGscs(int gscs) {
            this.gscs = gscs;
        }

        public int getGsms() {
            return gsms;
        }

        public void setGsms(int gsms) {
            this.gsms = gsms;
        }

        public List<MachineStatistics> getMachineStatisticsList() {
            return machineStatisticsList;
        }

        public void setMachineStatisticsList(List<MachineStatistics> machineStatisticsList) {
            this.machineStatisticsList = machineStatisticsList;
        }
    }
}
