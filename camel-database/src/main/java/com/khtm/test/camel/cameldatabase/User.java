package com.khtm.test.camel.cameldatabase;

public class User {
    private int id;
    private String name;
    private String family;
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("[id => %d, name => %s, family => %s, status => %s]",
                this.id, this.name, this.family, this.status);
    }
}
