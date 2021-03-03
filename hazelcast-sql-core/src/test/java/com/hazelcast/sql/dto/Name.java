package com.hazelcast.sql.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Name implements Serializable {

    @JsonProperty("first")
    private String first;
    @JsonProperty("last")
    private String last;

    public Name(@JsonProperty("first") String first, @JsonProperty("last") String last) {
        this.first = first;
        this.last = last;
    }

    public String getFirst() {
        return first;
    }

    public String getLast() {
        return last;
    }
}
