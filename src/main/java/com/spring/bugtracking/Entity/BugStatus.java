package com.spring.bugtracking.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class BugStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    String statusName;

    public long getId() {
        return id;
    }

    public String getStatusName() {
        return statusName;
    }

    public BugStatus setStatusName(String statusName) {
        this.statusName = statusName;
        return this;
    }
}
