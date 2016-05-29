package com.danigulyas.datacenters.observer;

import com.danigulyas.datacenters.watchdogs.GithubWatchdogService;
import com.danigulyas.datacenters.watchdogs.Watchdog;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WatchdogObserver {
    private List<Watchdog> watchdogs = new ArrayList<Watchdog>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public WatchdogObserver() {
        //schedule to run observed watchdog
        this.scheduler.scheduleAtFixedRate(this.refresher, 0, 10, TimeUnit.SECONDS);
        this.watchdogs.add(new GithubWatchdogService());
    }

    final Runnable refresher = new Runnable() {
        public void run() {
            try {
                for (Watchdog watchdog : watchdogs) {
                    System.out.println("Calling refresh on " + watchdog.getName());
                    watchdog.refresh();
                }
            } catch (Throwable e) {
               System.out.println("shit happened" + e.getMessage());
                e.printStackTrace();
            }
        }
    };

    public List<Report> getReports() {
        List<Report> reportList = new ArrayList<Report>();

        for (Watchdog watchdog : this.watchdogs) {
            reportList.add(new Report(watchdog));
        }

        return reportList;
    }
}
