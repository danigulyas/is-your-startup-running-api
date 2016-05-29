package com.danigulyas.datacenters;

import com.danigulyas.datacenters.resources.ReportResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class DatacenterApplication extends Application<DatacenterApplicationConfiguration> {
    public static void main(String[] args) throws Exception {
        new DatacenterApplication().run(args);
    }

    public String getName() {
        return "Datacenter health monitor";
    }

    @Override
    public void initialize(Bootstrap<DatacenterApplicationConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(DatacenterApplicationConfiguration configuration,
                    Environment environment) {
        final ReportResource resource = new ReportResource();
        environment.jersey().register(resource);
    }

}
