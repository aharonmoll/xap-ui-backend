package com.gigaspaces.monitor.website.controllers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.openspaces.admin.Admin;
import org.openspaces.admin.AdminFactory;
import org.openspaces.admin.machine.Machine;
import org.openspaces.admin.machine.Machines;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by aharon on 3/12/14.
 */
@Controller
public class MonitorController {

    private static Logger logger = LoggerFactory.getLogger(MonitorController.class);

    Admin admin = new AdminFactory().addLocators("localhost:4174").createAdmin();

    @RequestMapping(value = "/xapstatistics" , method = RequestMethod.GET)
    @ResponseBody
    public String printWelcome() {
        return toJson(admin);
    }

    public String toJson(Admin admin){
        ObjectMapper mapper = new XmlMapper();
        mapper.addMixInAnnotations(Admin.class, AdminMixin.class);
        mapper.addMixInAnnotations(Machines.class, MachinesMixin.class);
        mapper.addMixInAnnotations(Machine.class, MachineMixin.class);

        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(admin);
        } catch (IOException e) {
            throw new RuntimeException("unable to get admin info",e);
        }
    }

    @JsonSerialize(using = AdminSerializer.class) public static interface AdminMixin{ }
    @JsonSerialize(using = MachinesSerializer.class) public static interface MachinesMixin{ }
    @JsonSerialize(using = MachineSerializer.class) public static interface MachineMixin{ }

    public static class AdminSerializer extends JsonSerializer<Admin> {

        @Override
        public void serialize(Admin admin, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("gscCount", admin.getGridServiceContainers().getSize());
            jsonGenerator.writeNumberField("gsmCount", admin.getGridServiceManagers().getSize());
            jsonGenerator.writeNumberField("lusCount", admin.getLookupServices().getSize());
            jsonGenerator.writeObjectField("machines", admin.getMachines());
            jsonGenerator.writeEndObject();
        }
    }

    public static class MachinesSerializer extends JsonSerializer<Machines>{
        @Override
        public void serialize(Machines machines, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
            jsonGenerator.writeObject(machines.getMachines());
        }
    }

    public static class MachineSerializer extends JsonSerializer<Machine>{
        @Override
        public void serialize(Machine machine, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("actualFreePhysicalMemorySizeInMB", machine.getOperatingSystem().getStatistics().getActualFreePhysicalMemorySizeInMB());
            jsonGenerator.writeNumberField("cpuPerc", machine.getOperatingSystem().getStatistics().getCpuPerc());
            jsonGenerator.writeEndObject();
        }
    }

}
