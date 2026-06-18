package com.JohnBieniek.Java26Demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.JohnBieniek.Java26Demo.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
