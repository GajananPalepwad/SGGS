package com.sggs.sggs.model;

import androidx.recyclerview.widget.RecyclerView;

public class AdmitCardModel {

    public AdmitCardModel(String key, String value) {
        this.key = key;
        this.value = value;
    }

    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    private String value;


}
