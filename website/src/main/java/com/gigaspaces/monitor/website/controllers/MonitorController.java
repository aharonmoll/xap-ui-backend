package com.gigaspaces.monitor.website.controllers;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.openspaces.admin.Admin;
import org.openspaces.admin.AdminFactory;
import org.openspaces.admin.machine.Machine;
import org.openspaces.admin.machine.Machines;
import org.openspaces.admin.space.Space;
import org.openspaces.admin.space.SpacePartition;
import org.openspaces.admin.space.Spaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by aharon on 3/12/14.
 */
@Controller
public class MonitorController {

    private static Logger logger = LoggerFactory.getLogger(MonitorController.class);

    @Autowired
    Admin admin = null;

    @Autowired
    BeanFactory beanFactory = null;


    @RequestMapping( value = "/guy")
    @ResponseBody
    public String guy() throws IOException {
        List<Integer> numbers = new LinkedList<Integer>();
        numbers.add(1);
        numbers.add(2);

        Map<String,Object> someObj = new HashMap<String, Object>();

        someObj.put("data",numbers);

        return new XmlMapper().writeValueAsString(someObj);
    }

    @RequestMapping( value = "/testLocators")
    @ResponseBody
    public String testLocators() {
        return beanFactory.getLocators();
    }

    @RequestMapping(value = "/xapstatistics", method = RequestMethod.GET)
    @ResponseBody
    public String printWelcome() {

        return toJson(admin);
    }



    @RequestMapping(value = "/settings", method = RequestMethod.GET)
    @ResponseBody
    public String setSettings( @RequestParam String locators ){
        logger.info("reinitializing locators with [{}]", locators);
//        "localhost:4174"
        admin = new AdminFactory().addLocators(locators).createAdmin();
        return "ok";
    }

    public String toJson(Admin admin) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.addMixInAnnotations(Admin.class, AdminMixin.class);
        mapper.addMixInAnnotations(Machines.class, MachinesMixin.class);
        mapper.addMixInAnnotations(Machine.class, MachineMixin.class);

        try {

            JsonNode jsonNode = mapper.valueToTree(admin);
//            logger.info(" I got a json " + jsonNode );
            String jsonResult = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(admin);
            JsonNode jsonNode1 = mapper.readTree(jsonResult);
//            return jsonResult;
            return new XmlMapper().writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode1);
        } catch (IOException e) {
            logger.error("unable to write XML", e);
            throw new RuntimeException("unable to get admin info", e);
        }
    }




    @JsonSerialize(using = AdminSerializer.class)
    public static interface AdminMixin {
    }

    @JsonSerialize(using = MachinesSerializer.class)
    public static interface MachinesMixin {
    }

    @JsonSerialize(using = MachineSerializer.class)
    public static interface MachineMixin {
    }

    public static class AdminSerializer extends JsonSerializer<Admin> {

        @Override
        public void serialize(Admin admin, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
            logger.info("serializing admin");
            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("gscCount", admin.getGridServiceContainers().getSize());
            jsonGenerator.writeNumberField("gsmCount", admin.getGridServiceManagers().getSize());
            jsonGenerator.writeNumberField("lusCount", admin.getLookupServices().getSize());




            jsonGenerator.writeObjectFieldStart("machines");
            jsonGenerator.writeObjectField("machine", admin.getMachines().getMachines());
            jsonGenerator.writeEndObject();


            jsonGenerator.writeObjectFieldStart("spaces");
            List<SpaceMonitoring> spaceMonitorings = new LinkedList<SpaceMonitoring>();
            Spaces spaces = admin.getSpaces();

            for (Space singleSpace : spaces) {
                for (SpacePartition spacePartition : singleSpace.getPartitions()) {

                    SpaceMonitoring spaceMonitoring = new SpaceMonitoring();
                    spaceMonitoring.setName(spacePartition.getPrimary().getSpaceInstanceName());
                    spaceMonitoring.setSpaceInstance(spacePartition.getPrimary());
                    spaceMonitorings.add(spaceMonitoring);
                }
            }
            logger.info("num of spaces is : " + spaceMonitorings.size());
            jsonGenerator.writeObjectField("space", spaceMonitorings);
            jsonGenerator.writeEndObject();

            jsonGenerator.writeEndObject();
        }
    }

    public static class MachinesSerializer extends JsonSerializer<Machines> {
        @Override
        public void serialize(Machines machines, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
            jsonGenerator.writeObject(machines.getMachines());
        }
    }

    public static class MachineSerializer extends JsonSerializer<Machine> {
        @Override
        public void serialize(Machine machine, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeObjectField("machineName",machine.getHostName());
            jsonGenerator.writeNumberField("actualFreePhysicalMemorySizeInMB", machine.getOperatingSystem().getStatistics().getActualFreePhysicalMemorySizeInMB());
            jsonGenerator.writeNumberField("cpuPerc", machine.getOperatingSystem().getStatistics().getCpuPerc());
            jsonGenerator.writeEndObject();
        }
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
}
