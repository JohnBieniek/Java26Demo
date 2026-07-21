package com.JohnBieniek.Java26Demo.model.organization;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String department;
    private int salary;
    private int bonus;
    private String phoneNumber;
    private LocalDateTime deletedAt;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    protected Employee() {}

    public Employee(String name, String department, int salary, int bonus, String phoneNumber,
                    LocalDateTime deletedAt, Team team) {
        this.name = name;
        this.department = department;
        this.salary = salary;
        this.bonus = bonus;
        this.phoneNumber = phoneNumber;
        this.deletedAt = deletedAt;
        this.team = team;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public int getSalary() {
        return salary;
    }

    public int getBonus() {
        return bonus;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public Team getTeam() {
        return team;
    }

    public void update(String name, String department, int salary, int bonus, String phoneNumber, Team team) {
        this.name = name;
        this.department = department;
        this.salary = salary;
        this.bonus = bonus;
        this.phoneNumber = phoneNumber;
        this.team = team;
    }

    public void softDelete() {
        this.deletedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Employee{id=" + id + ", name='" + name + "', department='" + department + "', salary=" + salary
                + ", bonus=" + bonus + ", phoneNumber='" + phoneNumber + "', deletedAt=" + deletedAt + '}';
    }
}
