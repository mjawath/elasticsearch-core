package com.techstart.elasticcore.indexer;

import com.techstart.elasticcore.util.Meta;

import java.util.Map;

/**
 * Created by jawa on 11/4/2020.
 */
@Meta(index = "shops")
public class OutletDTO extends DTOIndex{


    private String id;
    private String name;
    private String description;

    private LocationDTO location;
    private String category;

    public Map getKeyval() {
        return keyval;
    }

    public void setKeyval(Map keyval) {
        this.keyval = keyval;
    }

    private Map<String,String> keyval;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



}
