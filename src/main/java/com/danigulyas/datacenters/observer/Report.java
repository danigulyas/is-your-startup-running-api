package com.danigulyas.datacenters.observer;

import com.danigulyas.datacenters.watchdogs.Status;
import com.danigulyas.datacenters.watchdogs.Watchdog;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.tools.javac.util.List;
import org.joda.time.DateTime;

/**
 * Created by dani on 29/05/16.
 */
public class Report {
    private String name;
    private Status status;
    private DateTime lastRefreshed = new DateTime('0');
    private DateTime lastQueried = new DateTime('0');
    private List<Report> childServices;

    @JsonProperty
    public String getName() {
        return name;
    }

    @JsonProperty
    public Status getStatus() {
        return status;
    }

    @JsonProperty
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss a z")
    public DateTime getLastRefreshed() {
        return lastRefreshed;
    }

    @JsonProperty
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss a z")
    public DateTime getLastQueried() {
        return lastQueried;
    }

    @JsonProperty
    @JsonFormat(shape=JsonFormat.Shape.ARRAY)
    private List<Report> getChildServices() {
        return childServices;
    }

    public Report(Watchdog watchdog) {
        this.name = watchdog.getName();
        this.status = watchdog.getStatus();
        this.lastRefreshed = watchdog.getLastRefreshed();
        this.lastQueried = watchdog.getLastQueried();

        for(Watchdog childWatchdog : watchdog.getChildServices()) {
            this.childServices.append(new Report(childWatchdog));
        }
    }
}

