package com.gs;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.junit.Test;
import org.openspaces.admin.Admin;
import org.openspaces.admin.AdminFactory;
import org.openspaces.admin.application.Application;
import org.openspaces.admin.application.Applications;
import org.openspaces.admin.gsc.GridServiceContainer;
import org.openspaces.admin.machine.Machine;
import org.openspaces.admin.machine.Machines;
import org.openspaces.admin.machine.events.MachineAddedEventListener;
import org.openspaces.admin.machine.events.MachineAddedEventManager;
import org.openspaces.admin.os.OperatingSystem;
import org.openspaces.admin.os.OperatingSystemStatistics;
import org.openspaces.admin.os.OperatingSystems;
import org.openspaces.admin.pu.ProcessingUnit;
import org.openspaces.admin.pu.ProcessingUnitInstance;
import org.openspaces.admin.pu.ProcessingUnitPartition;
import org.openspaces.admin.pu.ProcessingUnits;
import org.openspaces.admin.space.SpaceInstance;
import org.openspaces.admin.space.SpaceInstanceRuntimeDetails;
import org.openspaces.admin.vm.VirtualMachine;
import org.openspaces.admin.vm.VirtualMachineDetails;
import org.openspaces.admin.vm.VirtualMachineStatistics;
import org.openspaces.admin.vm.VirtualMachines;
import org.openspaces.admin.zone.config.ExactZonesConfig;
import org.openspaces.core.properties.BeanLevelProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: guym
 * Date: 12/12/13
 * Time: 5:01 PM
 */
public class TestAdminPool {


    private static Logger logger = LoggerFactory.getLogger(TestAdminPool.class);

    @Test
    public void testAdminPool() throws IOException {
        String groups = "gigaspaces-9.7.0-XAPPremium-m7";
        Admin admin = new AdminFactory().addGroup(groups).createAdmin();
        MachineAddedEventManager machineAdded = admin.getMachines().getMachineAdded();

        try {
            Thread.sleep(1000);
        } catch (Exception e) {
        }


        machineAdded.add(new MachineAddedEventListener() {
            @Override
            public void machineAdded(Machine machine) {
                logger.info("machine added [{}]", machine);
            }
        });

        toJson(admin);


    }

    private void toJson(Admin admin) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        SerializationConfig config = mapper.getSerializationConfig();
        config.addMixInAnnotations(Admin.class, AdminMixin.class);
        config.addMixInAnnotations(Application.class, ApplicationMixin.class);
        config.addMixInAnnotations(Applications.class, ApplicationsMixin.class);
        config.addMixInAnnotations(ProcessingUnits.class, ProcessingUnitsMixin.class);
        config.addMixInAnnotations(ProcessingUnit.class, ProcessingUnitMixin.class);
        config.addMixInAnnotations(Machines.class, MachinesMixin.class);
        config.addMixInAnnotations(Machine.class, MachineMixin.class);
        config.addMixInAnnotations(OperatingSystem.class, OperatingSystemMixin.class);
        config.addMixInAnnotations(VirtualMachines.class, VirtualMachinesMixin.class);
        config.addMixInAnnotations(VirtualMachine.class, VirtualMachineMixin.class);
        config.addMixInAnnotations(OperatingSystemStatistics.class, OperatingSystemStatisticsMixin.class);
        config.addMixInAnnotations(OperatingSystemStatistics.NetworkStatistics.class, NetworkStatisticsMixin.class);
        config.addMixInAnnotations(VirtualMachineDetails.class, VirtualMachineDetailsMixin.class);
        config.addMixInAnnotations(ProcessingUnitInstance.class, ProcessingUnitInstanceMixin.class);
        config.addMixInAnnotations(SpaceInstance.class, SpaceInstanceMixin.class);
        config.addMixInAnnotations(VirtualMachineStatistics.class, VirtualMachineStatisticsMixin.class);
        config.addMixInAnnotations(BeanLevelProperties.class, BeanLevelPropertiesMixin.class);
        config.addMixInAnnotations(ProcessingUnitPartition.class, ProcessingUnitPartitionMixin.class);
        config.addMixInAnnotations(GridServiceContainer.class, GridServiceContainerMixin.class);
        config.addMixInAnnotations(ExactZonesConfig.class, ExactZonesConfigMixin.class);
        config.addMixInAnnotations(SpaceInstanceRuntimeDetails.class, SpaceInstanceRuntimeDetailsMixin.class);
        config.addMixInAnnotations(OperatingSystems.class, OperatingSystemsMixin.class);


        logger.info("writing as string");
        logger.info("{}", mapper.writeValueAsString(admin));
        logger.info("writing as string end");

        logger.info("writing as string second time");
        logger.info("{}", mapper.writeValueAsString(admin));
        logger.info("writing as string second time end");
    }


    @JsonSerialize(using = AdminSerializer.class) public static interface AdminMixin { }
    @JsonSerialize(using = ApplicationsSerializer.class) public static interface ApplicationsMixin { }
    @JsonSerialize(using = ApplicationSerializer.class) public static interface ApplicationMixin { }
    @JsonSerialize(using = ProcessingUnitsSerializer.class) public static interface ProcessingUnitsMixin { }
    @JsonSerialize(using = ProcessingUnitSerializer.class) public static interface ProcessingUnitMixin { }
    @JsonSerialize(using = MachinesSerializer.class) public static interface MachinesMixin { }
    @JsonSerialize(using = MachineSerializer.class) public static interface MachineMixin { }
    @JsonSerialize(using = OperatingSystemSerializer.class) public static interface OperatingSystemMixin { }
    @JsonSerialize(using = OperatingSystemStatisticsSerializer.class) public static interface OperatingSystemStatisticsMixin { }
    @JsonSerialize(using = NetworkStatisticsSerializer.class) public static interface NetworkStatisticsMixin { }
    @JsonSerialize(using = VirtualMachinesSerializer.class) public static interface VirtualMachinesMixin { }
    @JsonSerialize(using = VirtualMachineSerializer.class) public static interface VirtualMachineMixin { }
    @JsonSerialize(using = VirtualMachineDetailsSerializer.class) public static interface VirtualMachineDetailsMixin { }
    @JsonSerialize(using = ProcessingUnitInstanceSerializer.class) public static interface ProcessingUnitInstanceMixin { }
    @JsonSerialize(using = SpaceInstanceSerializer.class) public static interface SpaceInstanceMixin { }
    @JsonSerialize(using = VirtualMachineStatisticsSerializer.class) public static interface VirtualMachineStatisticsMixin { }
    @JsonSerialize(using = BeanLevelPropertiesSerializer.class) public static interface BeanLevelPropertiesMixin { }
    @JsonSerialize(using = ProcessingUnitPartitionSerializer.class) public static interface ProcessingUnitPartitionMixin { }
    @JsonSerialize(using = GridServiceContainerSerializer.class) public static interface GridServiceContainerMixin { }
    @JsonSerialize(using = ExactZonesConfigSerializer.class) public static interface ExactZonesConfigMixin { }
    @JsonSerialize(using = SpaceInstanceRuntimeDetailsSerializer.class) public static interface SpaceInstanceRuntimeDetailsMixin { }
    @JsonSerialize(using = OperatingSystemsSerializer.class) public static interface OperatingSystemsMixin { }




    public static class AdminSerializer extends JsonSerializer<Admin> {
        @Override
        public void serialize(Admin value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
            jgen.writeStartObject();
            jgen.writeObjectField("applications", value.getApplications());
            jgen.writeObjectField("machines", value.getMachines());
            jgen.writeObjectField("operatingSystems", value.getOperatingSystems());
            jgen.writeEndObject();
        }
    }

    public static class OperatingSystemsSerializer extends JsonSerializer<OperatingSystems>{
        @Override
        public void serialize(OperatingSystems value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
            jgen.writeStartObject();
            jgen.writeObjectField("items", value.getOperatingSystems());
//            jgen.writeObjectField("statistics", value.getStatistics());
            jgen.writeEndObject();
        }
    }


//    public static class OperatingSystemStatisticsSerializer extends JsonSerializer<OperatingSystemStatistics>{
//        @Override
//        public void serialize(OperatingSystemStatistics value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
//            jgen.writeStartObject();
//
//            jgen.writeEndObject();
//
//        }
//    }

    public static class ApplicationsSerializer extends JsonSerializer<Applications> {
        @Override
        public void serialize(Applications value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
            jgen.writeObject(value.getApplications());
        }
    }

    public static class ApplicationSerializer extends JsonSerializer<Application> {
        @Override
        public void serialize(Application value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
            jgen.writeStartObject();
            jgen.writeObjectField("name", value.getName());
            jgen.writeObjectField("processingUnits", value.getProcessingUnits());
            jgen.writeEndObject();
        }
    }

    public static class ProcessingUnitsSerializer extends JsonSerializer<ProcessingUnits> {
        @Override
        public void serialize(ProcessingUnits value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
            jgen.writeObject(value.getProcessingUnits());
        }
    }


    public static class ProcessingUnitSerializer extends JsonSerializer<ProcessingUnit> {
        @Override
        public void serialize(ProcessingUnit value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
            jgen.writeStartObject();
            jgen.writeStringField("name", value.getName());
            jgen.writeNumberField("numberOfBackups", value.getNumberOfBackups());
            jgen.writeNumberField("maxInstancesPerMachine", value.getMaxInstancesPerMachine());
            jgen.writeObjectField("beanLevelProperties",value.getBeanLevelProperties());
//            jgen.writeObject(value.getProcessingUnits());
            jgen.writeEndObject();
        }
    }

    public static class BeanLevelPropertiesSerializer extends JsonSerializer<BeanLevelProperties>{
        @Override
        public void serialize(BeanLevelProperties value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
            jgen.writeStartObject();
            jgen.writeObjectField("contextProperties", value.getContextProperties());
            jgen.writeObjectField("beans",value.getBeans());
            jgen.writeEndObject();

        }
    }



    public static class MachinesSerializer extends JsonSerializer<Machines> {

        @Override
        public void serialize(Machines value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
            jgen.writeObject(value.getMachines());
        }
    }


    public static class MachineSerializer extends JsonSerializer<Machine> {

        @Override
        public void serialize(Machine value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
            jgen.writeStartObject();
            jgen.writeStringField("hostName", value.getHostName());
            jgen.writeStringField("hostAddress", value.getHostAddress());
            jgen.writeStringField("uid", value.getUid());
            jgen.writeObjectField("operatingSystem", value.getOperatingSystem());
            jgen.writeObjectField("virtualMachines", value.getVirtualMachines());
            jgen.writeEndObject();
        }
    }

    public static class OperatingSystemSerializer extends JsonSerializer<OperatingSystem>{
        @Override
        public void serialize(OperatingSystem value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
            jgen.writeStartObject();
//            jgen.writeStringField("uid", value.getUid());
//            jgen.writeNumberField("currentTimeMillis", value.getCurrentTimeInMillis());
//            jgen.writeNumberField("timeDelta", value.getTimeDelta());
//            jgen.writeObjectField("statistics", value.getStatistics());
//            jgen.writeObjectField("statisticsTimeline", value.getStatistics().getTimeline());
            jgen.writeEndObject();
        }
    }

    public static class VirtualMachinesSerializer extends JsonSerializer<VirtualMachines>{
        @Override
        public void serialize(VirtualMachines value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
            jgen.writeObject( value.getVirtualMachines());
        }
    }

    public static class VirtualMachineSerializer extends JsonSerializer<VirtualMachine>{
        @Override
        public void serialize(VirtualMachine value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
            jgen.writeStartObject();
            jgen.writeObjectField("details", value.getDetails());
            jgen.writeObjectField("processingUnitInstances", value.getProcessingUnitInstances());
            jgen.writeObjectField("spaceInstance", value.getSpaceInstances());
//            jgen.writeObjectField("statistics",value.getStatistics());
            jgen.writeStringField("uid", value.getUid());
            jgen.writeEndObject();
        }
    }

    public static class VirtualMachineDetailsSerializer extends JsonSerializer<VirtualMachineDetails> {
        @Override
        public void serialize(VirtualMachineDetails value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
            jgen.writeStartObject();
            jgen.writeBooleanField("isNA", value.isNA());
            jgen.writeStringField("jmxUrl", value.getJmxUrl());
            jgen.writeStringField("uid", value.getUid());
            jgen.writeStringField("vmName", value.getVmName());
            jgen.writeStringField("vmVersion", value.getVmVersion());
            jgen.writeStringField("vmVendor", value.getVmVendor());
            jgen.writeNumberField("startTime", value.getStartTime());
            jgen.writeNumberField("pid", value.getPid());
            jgen.writeStringField("bootClassPath", value.getBootClassPath());
            jgen.writeStringField("classPath", value.getClassPath());
            jgen.writeObjectField("inputArguments", value.getInputArguments());
            jgen.writeObjectField("systemProperties", value.getSystemProperties());
            jgen.writeObjectField("environmentVariables", value.getEnvironmentVariables());
            jgen.writeNumberField("memoryHeapInitInBytes", value.getMemoryHeapInitInBytes());
            jgen.writeNumberField("memoryHeapInitInMB", value.getMemoryHeapInitInMB());
            jgen.writeNumberField("memoryHeapInitInGB", value.getMemoryHeapInitInGB());
            jgen.writeNumberField("memoryHeapMaxInBytes", value.getMemoryHeapMaxInBytes());
            jgen.writeNumberField("memoryHeapMaxInMB", value.getMemoryHeapMaxInMB());
            jgen.writeNumberField("memoryHeapMaxInGB", value.getMemoryHeapMaxInGB());
            jgen.writeNumberField("memoryNonHeapInitInBytes", value.getMemoryNonHeapInitInBytes());
            jgen.writeNumberField("memoryNonHeapInitInMB", value.getMemoryNonHeapInitInMB());
            jgen.writeNumberField("memoryNonHeapInitInGB", value.getMemoryNonHeapInitInGB());
            jgen.writeNumberField("memoryNonHeapMaxInBytes", value.getMemoryNonHeapMaxInBytes());
            jgen.writeNumberField("memoryNonHeapMaxInMB", value.getMemoryNonHeapMaxInMB());
            jgen.writeNumberField("memoryNonHeapMaxInGB", value.getMemoryNonHeapMaxInGB());
            jgen.writeEndObject();
        }
    }

    public static class ProcessingUnitInstanceSerializer extends JsonSerializer<ProcessingUnitInstance>{
        @Override
        public void serialize(ProcessingUnitInstance value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
            jgen.writeStartObject();

            jgen.writeStringField("processingUnitInstanceName", value.getProcessingUnitInstanceName());

            jgen.writeObjectField("instanceId", value.getInstanceId());
            jgen.writeNumberField("backupId",value.getBackupId());
            jgen.writeObjectField("processingUnit", value.getProcessingUnit());
            jgen.writeStringField("name", value.getName());
            jgen.writeObjectField("clusterInfo", value.getClusterInfo());
            jgen.writeObjectField("properties", value.getProperties());
            jgen.writeObjectField("gridServiceContainer", value.getGridServiceContainer());
            jgen.writeObjectField("partition", value.getPartition());
            jgen.writeObjectField("serviceDetailsByServiceId", value.getServiceDetailsByServiceId());
            jgen.writeObjectField("serviceDetailsByServiceType", value.getServiceDetailsByServiceType());
            jgen.writeObjectField("eventContainerDetails", value.getEventContainerDetails());
            jgen.writeObjectField("pollingEventContainerDetails", value.getPollingEventContainerDetails());
            jgen.writeObjectField("notifyEventContainerDetails", value.getNotifyEventContainerDetails());
            jgen.writeObjectField("asyncPollingEventContainerDetails", value.getAsyncPollingEventContainerDetails());
            jgen.writeObjectField("remotingDetails", value.getRemotingDetails());
            jgen.writeObjectField("spaceDetails", value.getSpaceDetails());
            jgen.writeObjectField("embeddedSpaceDetails", value.getEmbeddedSpaceDetails());
            jgen.writeObjectField("embeddedSpacesDetails", value.getEmbeddedSpacesDetails());
            jgen.writeObjectField("memcachedDetails", value.getMemcachedDetails());
            jgen.writeBooleanField("isEmbeddedSpaces", value.isEmbeddedSpaces());
            jgen.writeObjectField("spaceInstance", value.getSpaceInstance());
            jgen.writeObjectField("spaceInstances", value.getSpaceInstances());
            jgen.writeBooleanField("isJee", value.isJee());
            jgen.writeObjectField("jeeDetails", value.getJeeDetails());
//            jgen.writeObjectField("statistics", value.getStatistics());
            jgen.writeObjectField("memberAliveIndicatorStatus", value.getMemberAliveIndicatorStatus());

            jgen.writeEndObject();
        }
    }


    public static class GridServiceContainerSerializer extends JsonSerializer<GridServiceContainer>{
        @Override
        public void serialize(GridServiceContainer value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
            jgen.writeStartObject();
            jgen.writeObjectField("exactZone",value.getExactZones());
            jgen.writeNumberField("agentId", value.getAgentId());
            jgen.writeStringField("uid", value.getUid());
            jgen.writeEndObject();

        }
    }


    public static class ExactZonesConfigSerializer extends JsonSerializer<ExactZonesConfig>{
        @Override
        public void serialize(ExactZonesConfig value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
            jgen.writeStartObject();
            jgen.writeObjectField("properties", value.getProperties());
            jgen.writeObjectField("zones", value.getZones());
            jgen.writeEndObject();

        }
    }


    public static class ProcessingUnitPartitionSerializer extends JsonSerializer<ProcessingUnitPartition>{
        @Override
        public void serialize(ProcessingUnitPartition value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
            jgen.writeStartObject();

            jgen.writeNumberField("partitionId", value.getPartitionId());
//            jgen.writeObjectField("backup", value.getBackup());
//            jgen.writeObjectField("instances", value.getInstances());
//            jgen.writeObjectField("primary", value.getPrimary());
            jgen.writeObjectField("processingUnit",value.getProcessingUnit());

            jgen.writeEndObject();

        }
    }

    public static class SpaceInstanceSerializer extends JsonSerializer<SpaceInstance>{
        @Override
        public void serialize(SpaceInstance value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
            jgen.writeStartObject();

            jgen.writeStringField("uid", value.getUid());
            jgen.writeBooleanField("isMonitoring", value.isMonitoring());
            jgen.writeStringField("spaceInstanceName", value.getSpaceInstanceName());
            jgen.writeNumberField("instanceId",value.getInstanceId());
            jgen.writeNumberField("backupId",value.getBackupId());
            jgen.writeStringField("mode", value.getMode().name());
//        SpaceURL getSpaceUrl();
//            jgen.writeObjectField("spaceInstanceStatistics", value.getStatistics());
            jgen.writeObjectField("runtimeDetails", value.getRuntimeDetails());
//            jgen.writeObjectField("space", value.getSpace());
//            jgen.writeObjectField("partition", value.getPartition());
//            jgen.writeObjectField("replicationTargets", value.getReplicationTargets());
            jgen.writeEndObject();

        }
    }

    public static class SpaceInstanceRuntimeDetailsSerializer extends JsonSerializer<SpaceInstanceRuntimeDetails>{
        @Override
        public void serialize(SpaceInstanceRuntimeDetails value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
            jgen.writeStartObject();

            jgen.writeEndObject();

        }
    }

    public static class VirtualMachineStatisticsSerializer extends JsonSerializer<VirtualMachineStatistics>{
        @Override
        public void serialize(VirtualMachineStatistics value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
            jgen.writeStartObject();

            jgen.writeEndObject();

        }
    }

    public static class OperatingSystemStatisticsSerializer extends JsonSerializer<OperatingSystemStatistics>{
        @Override
        public void serialize(OperatingSystemStatistics value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
            jgen.writeStartObject();

//            jgen.writeBooleanField("isNA", value.isNA());
//            jgen.writeNumberField("timestamp", value.getTimestamp());
//            jgen.writeNumberField("adminTimestamp", value.getAdminTimestamp());
//            jgen.writeObjectField("details", value.getDetails());
//            jgen.writeObjectField("timeline", value.getTimeline());
//            jgen.writeObjectField("previous", value.getPrevious());
//            jgen.writeNumberField("freeSwapSpaceSizeInBytes", value.getFreeSwapSpaceSizeInBytes());
//            jgen.writeNumberField("freeSwapSpaceSizeInMB", value.getFreeSwapSpaceSizeInMB());
//            jgen.writeNumberField("freeSwapSpaceSizeInGB", value.getFreeSwapSpaceSizeInGB());
//            jgen.writeNumberField("swapSpaceUsedPerc", value.getSwapSpaceUsedPerc());
//            jgen.writeNumberField("freePhysicalMemorySizeInBytes", value.getFreePhysicalMemorySizeInBytes());
//            jgen.writeNumberField("freePhysicalMemorySizeInMB", value.getFreePhysicalMemorySizeInMB());
//            jgen.writeNumberField("freePhysicalMemorySizeInGB", value.getFreePhysicalMemorySizeInGB());
//            jgen.writeNumberField("actualFreePhysicalMemorySizeInBytes", value.getActualFreePhysicalMemorySizeInBytes());
//            jgen.writeNumberField("actualFreePhysicalMemorySizeInMB", value.getActualFreePhysicalMemorySizeInMB());
//            jgen.writeNumberField("actualFreePhysicalMemorySizeInGB", value.getActualFreePhysicalMemorySizeInGB());
//            jgen.writeNumberField("physicalMemoryUsedPerc", value.getPhysicalMemoryUsedPerc());
//            jgen.writeNumberField("actualPhysicalMemoryUsedPerc", value.getActualPhysicalMemoryUsedPerc());
//            jgen.writeNumberField("cpuPerc", value.getCpuPerc());
//            jgen.writeStringField("cpuPercFormatted", value.getCpuPercFormatted());
//            jgen.writeObjectField("networkStats", value.getNetworkStats());
            jgen.writeEndObject();
        }
    }

    public static class NetworkStatisticsSerializer extends JsonSerializer<OperatingSystemStatistics.NetworkStatistics>{
        @Override
        public void serialize(OperatingSystemStatistics.NetworkStatistics value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
            jgen.writeStartObject();

//            jgen.writeStringField("name", value.getName());
//            jgen.writeNumberField("rxBytes", value.getRxBytes());
//            jgen.writeNumberField("rxBytesPerSecond", value.getRxBytesPerSecond());
//            jgen.writeNumberField("txBytes", value.getTxBytes());
//            jgen.writeNumberField("txBytesPerSecond", value.getTxBytesPerSecond());
//            jgen.writeNumberField("rxPackets", value.getRxPackets());
//            jgen.writeNumberField("rxPacketsPerSecond", value.getRxPacketsPerSecond());
//            jgen.writeNumberField("txPackets", value.getTxPackets());
//            jgen.writeNumberField("txPacketsPerSecond", value.getTxPacketsPerSecond());
//            jgen.writeNumberField("rxErrors", value.getRxErrors());
//            jgen.writeNumberField("txErrors", value.getTxErrors());
//            jgen.writeNumberField("rxDropped", value.getRxDropped());
//            jgen.writeNumberField("txDropped", value.getTxDropped());
//            jgen.writeObjectField("previous", value.getPrevious());

            jgen.writeEndObject();

        }
    }

}
