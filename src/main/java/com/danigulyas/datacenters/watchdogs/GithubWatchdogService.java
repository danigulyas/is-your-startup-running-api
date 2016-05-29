package com.danigulyas.datacenters.watchdogs;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.joda.time.DateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GithubWatchdogService implements Watchdog{

    private String API_URL = "https://status.github.com/api/status.json";

    private String name = "GitHub";
    private Status status = Status.UNKNOWN;

    private DateTime lastRefreshed = new DateTime('0');
    private DateTime lastQueried = new DateTime('0');
    private List<Watchdog> childrenServices = new ArrayList<Watchdog>();

    public GithubWatchdogService() {
        Unirest.setObjectMapper(new ObjectMapper() {
            private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
                    = new com.fasterxml.jackson.databind.ObjectMapper();

            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return jacksonObjectMapper.readValue(value, valueType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public String writeValue(Object value) {
                try {
                    return jacksonObjectMapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public List<Watchdog> getChildServices() {
        return childrenServices;
    }

    public String getName() {
        return name;
    }

    public Status getStatus() {
        return this.status;
    }

    public DateTime getLastRefreshed() {
        return lastRefreshed;
    }

    public DateTime getLastQueried() {
        return lastQueried;
    }


    public static class GithubHealthResponse {

        @JsonProperty
        private String status;
        public String getStatus() {
            return status;
        }

        @JsonProperty("last_updated")
        private String lastUpdated;
        public String getLastUpdated() {
            return lastUpdated;
        }
    }

    public void refresh() throws UnirestException{
        HttpResponse<GithubHealthResponse> healthResponse = Unirest.get(this.API_URL).asObject(GithubHealthResponse.class);
        GithubHealthResponse response = healthResponse.getBody();
        String status = response.getStatus();

        if(status.equals("good")) this.status = Status.OK;
        if(status.equals("minor")) this.status = Status.QUESTIONABLE;
        if(status.equals("major")) this.status = Status.NOT_OK;

        this.lastQueried = new DateTime();
        this.lastRefreshed = new DateTime(response.getLastUpdated());
        System.out.println(this.toString());
    }

    @Override
    public String toString() {
        return "WatchdogService{" +
                "API_URL='" + API_URL + '\'' +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", lastRefreshed=" + lastRefreshed +
                ", lastQueried=" + lastQueried +
                '}';
    }
}

