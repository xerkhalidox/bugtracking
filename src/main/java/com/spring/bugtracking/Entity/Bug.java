package com.spring.bugtracking.Entity;

import jakarta.persistence.*;

@Entity
public class Bug {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String title;

    private String description;

    private String expectedDescription;

    private String actualDescription;

    @OneToOne
    @JoinColumn(name = "reporter_id")
    private Person reporter;

    @OneToOne
    @JoinColumn(name = "developer_id")
    private Person developer;

    @OneToOne
    @JoinColumn(name = "bug_status_id")
    private BugStatus bugStatusId;

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExpectedDescription() {
        return expectedDescription;
    }

    public void setExpectedDescription(String expectedDescription) {
        this.expectedDescription = expectedDescription;
    }

    public String getActualDescription() {
        return actualDescription;
    }

    public void setActualDescription(String actualDescription) {
        this.actualDescription = actualDescription;
    }

    public Person getReporter() {
        return reporter;
    }

    public void setReporter(Person reporter) {
        this.reporter = reporter;
    }

    public Person getDeveloper() {
        return developer;
    }

    public void setDeveloper(Person developer) {
        this.developer = developer;
    }

    public BugStatus getBugStatusId() {
        return bugStatusId;
    }

    public void setBugStatusId(BugStatus bugStatusId) {
        this.bugStatusId = bugStatusId;
    }
}
