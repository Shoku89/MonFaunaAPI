package com.monfauna.MonFaunaAPI.model;


public class SpecieType {

    private Integer id;
    private String name;

    public SpecieType() {

    }

    public SpecieType(String name){
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SpecieType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

