package com.danigulyas.datacenters.watchdogs;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public enum Status {
    OK              ("OK"),  //calls constructor with value 3
    NOT_OK          ("NOT_OK"),  //calls constructor with value 2
    UNKNOWN         ("UNKNOWN"),
    QUESTIONABLE    ("QUESTIONABLE")   //calls constructor with value 1
    ; // semicolon needed when fields / methods follow


    private final String status;

    Status(String status) {
        this.status = status;
    }

    @JsonProperty
    public String getStatus() {
        return this.status;
    }

}