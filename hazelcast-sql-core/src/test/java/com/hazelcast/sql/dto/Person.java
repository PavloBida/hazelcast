package com.hazelcast.sql.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "_id",
        "index",
        "guid",
        "isActive",
        "balance",
        "picture",
        "age",
        "eyeColor",
        "name",
        "company",
        "email",
        "phone",
        "address",
        "about",
        "registered",
        "latitude",
        "longitude",
        "tags",
        "range",
        "friends",
        "greeting",
        "favoriteFruit"
})
public class Person implements Serializable {

    @JsonProperty("_id")
    private String id;
    @JsonProperty("index")
    private Integer index;
    @JsonProperty("guid")
    private String guid;
    @JsonProperty("isActive")
    private Boolean isActive;
    @JsonProperty("balance")
    private double balance;
    @JsonProperty("picture")
    private String picture;
    @JsonProperty("age")
    private Integer age;
    @JsonProperty("eyeColor")
    private String eyeColor;
    @JsonProperty("name")
    private Name name;
    @JsonProperty("company")
    private String company;
    @JsonProperty("email")
    private String email;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("address")
    private String address;
    @JsonProperty("about")
    private String about;
    @JsonProperty("registered")
    private LocalDateTime registered;
    @JsonProperty("latitude")
    private String latitude;
    @JsonProperty("longitude")
    private String longitude;
    @JsonProperty("tags")
    private List<String> tags;
    @JsonProperty("range")
    private int range;
    @JsonProperty("friends")
    private List<Friend> friends;
    @JsonProperty("greeting")
    private String greeting;
    @JsonProperty("favoriteFruit")
    private String favoriteFruit;

    @JsonCreator
    public Person(@JsonProperty("_id") String id, @JsonProperty("index") Integer index, @JsonProperty("guid") String guid,
                  @JsonProperty("isActive") Boolean isActive, @JsonProperty("balance") String balance,
                  @JsonProperty("picture") String picture, @JsonProperty("age") Integer age,
                  @JsonProperty("eyeColor") String eyeColor, @JsonProperty("name") Name name,
                  @JsonProperty("company") String company, @JsonProperty("email") String email,
                  @JsonProperty("phone") String phone, @JsonProperty("address") String address,
                  @JsonProperty("about") String about, @JsonProperty("registered") Date registered,
                  @JsonProperty("latitude") String latitude, @JsonProperty("longitude") String longitude,
                  @JsonProperty("tags") List<String> tags, @JsonProperty("range") int range,
                  @JsonProperty("friends") List<Friend> friends, @JsonProperty("greeting") String greeting,
                  @JsonProperty("favoriteFruit") String favoriteFruit) {
        this.id = id;
        this.index = index;
        this.guid = guid;
        this.isActive = isActive;
        this.balance = Double.parseDouble(balance);
        this.picture = picture;
        this.age = age;
        this.eyeColor = eyeColor;
        this.name = name;
        this.company = company;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.about = about;
        this.registered = registered.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        this.latitude = latitude;
        this.longitude = longitude;
        this.tags = tags;
        this.range = range;
        this.friends = friends;
        this.greeting = greeting;
        this.favoriteFruit = favoriteFruit;
    }

    public String getId() {
        return id;
    }

    public Integer getIndex() {
        return index;
    }

    public String getGuid() {
        return guid;
    }

    public Boolean getActive() {
        return isActive;
    }

    public double getBalance() {
        return balance;
    }

    public String getPicture() {
        return picture;
    }

    public Integer getAge() {
        return age;
    }

    public String getEyeColor() {
        return eyeColor;
    }

    public Name getName() {
        return name;
    }

    public String getCompany() {
        return company;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getAbout() {
        return about;
    }

    public LocalDateTime getRegistered() {
        return registered;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public List<String> getTags() {
        return tags;
    }

    public int getRange() {
        return range;
    }

    public List<Friend> getFriends() {
        return friends;
    }

    public String getGreeting() {
        return greeting;
    }

    public String getFavoriteFruit() {
        return favoriteFruit;
    }

}
