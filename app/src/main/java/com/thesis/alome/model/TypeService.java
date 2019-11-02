package com.thesis.alome.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TypeService{

    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("typeName")
    @Expose
    private String typeName;
    @SerializedName("services")
    @Expose
    private List<Service> services = null;

    public TypeService() {
    }

    public TypeService(long id, String typeName, List<Service> services) {
        super();
        this.id = id;
        this.typeName = typeName;
        this.services = services;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }
}
