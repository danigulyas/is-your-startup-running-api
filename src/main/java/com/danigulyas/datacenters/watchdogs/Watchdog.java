package com.danigulyas.datacenters.watchdogs;

import org.joda.time.DateTime;

import java.util.List;

public interface Watchdog {
    public String getName();
    public Status getStatus();
    public DateTime getLastRefreshed();
    public DateTime getLastQueried();
    public List<Watchdog> getChildServices();
    public void refresh() throws Throwable;
}
