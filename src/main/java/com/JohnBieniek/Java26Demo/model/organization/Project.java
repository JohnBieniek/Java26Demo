package com.JohnBieniek.Java26Demo.model.organization;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int budget;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    protected Project() {}

    public Project(String name, int budget, Team team) {
        this.name = name;
        this.budget = budget;
        this.team = team;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getBudget() {
        return budget;
    }

    public Team getTeam() {
        return team;
    }
}
