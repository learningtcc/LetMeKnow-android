package com.singsoft.letmeknow.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by meidan.zemer on 1/31/2017.
 */

public class ContactPoint extends EntityBase{
    @SerializedName("contact-point-name")
    private String name;
    @SerializedName("contact-point-id")
    private String ContactPointId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactPointId() {
        return ContactPointId;
    }

    public void setContactPointId(String contactPointId) {
        ContactPointId = contactPointId;
    }
}
