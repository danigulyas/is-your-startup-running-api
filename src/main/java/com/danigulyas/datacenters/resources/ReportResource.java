package com.danigulyas.datacenters.resources;

import com.codahale.metrics.annotation.Timed;
import com.danigulyas.datacenters.observer.Report;
import com.danigulyas.datacenters.observer.WatchdogObserver;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/report")
@Produces(MediaType.APPLICATION_JSON)
public class ReportResource {
    private WatchdogObserver observer = new WatchdogObserver();

    public ReportResource() {};

    @GET
    @Timed
    public List<Report> getReports() {
        return observer.getReports();
    }
}
