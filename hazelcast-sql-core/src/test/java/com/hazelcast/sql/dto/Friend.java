package com.hazelcast.sql.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Friend implements Serializable {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    private String name;

    public Friend(@JsonProperty("id") Integer id, @JsonProperty("name") String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
