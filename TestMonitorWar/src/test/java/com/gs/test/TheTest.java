package com.gs.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openspaces.admin.Admin;
import org.openspaces.admin.AdminFactory;
import org.openspaces.admin.gsa.GridServiceAgent;
import org.openspaces.admin.gsm.GridServiceManager;
import org.openspaces.admin.pu.ProcessingUnit;
import org.openspaces.admin.pu.ProcessingUnitDeployment;
import org.openspaces.pu.container.jee.JeeServiceDetails;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by aharon on 12/21/15.
 */
public class TheTest {
    Runner gsaRunner;
    Admin admin;


    @Before
    public void beforeTest() throws InterruptedException, IOException {
        Map<String, String> envs = new HashMap<String, String>();
        envs.put("JAVA_HOME", System.getenv("JAVA_HOME"));
        envs.put("LOOKUPGROUPS", System.getenv("LOOKUPGROUPS"));
        gsaRunner = new Runner(System.getenv("XAP_HOME"), envs);
        gsaRunner.setWaitForTermination(false);

        String path = System.getenv("XAP_HOME") + "/bin/gs-agent.sh";
        gsaRunner.getCommands().add(path);


        gsaRunner.or(new StringPredicate("GSM started successfully") {

            @Override
            public boolean customTest(String input) {
                return input.contains(match);
            }
        });


        gsaRunner.startAndWait();

        admin = new AdminFactory().addGroup(System.getenv("LOOKUPGROUPS")).createAdmin();
        Assert.assertTrue(admin.getGridServiceAgents().waitFor(1, 10, TimeUnit.SECONDS));
    }


    @Test
    public void test() throws IOException, InterruptedException {

        ProcessingUnitDeployment deployment;
        String current = new java.io.File( "../website/target/website-1.0-SNAPSHOT.war" ).getCanonicalPath();
        System.out.println("Current dir:"+current);
        deployment = new ProcessingUnitDeployment(current);
        GridServiceManager gsm = admin.getGridServiceManagers().waitForAtLeastOne(5, TimeUnit.MINUTES);
        ProcessingUnit pu = gsm.deploy(deployment);
        Thread.sleep(8000);
        String url = "http://"+ pu.getInstances()[0].getServiceDetailsByServiceId(JeeServiceDetails.ID).getDescription()+"/xapstatistics";
        System.out.println("URL= "+url);
        HTTPUtils.HTTPGetRequest request = new HTTPUtils.HTTPGetRequest(url);
        HTTPUtils.HTTPSession session = new HTTPUtils.HTTPSession();
        HTTPUtils.HTTPResponse httpResult = session.get(request);
        Assert.assertEquals(200, httpResult.getStatusCode());
    }

    @After
    public void afterTest() {
        System.out.println("After Test");
        GridServiceAgent gsa = admin.getGridServiceAgents().waitForAtLeastOne(10, TimeUnit.SECONDS);

            gsa.shutdown();

    }
}
