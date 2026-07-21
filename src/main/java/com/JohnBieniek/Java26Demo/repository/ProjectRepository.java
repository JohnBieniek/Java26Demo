package com.JohnBieniek.Java26Demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.JohnBieniek.Java26Demo.model.organization.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    java.util.List<Project> findByTeamIdInOrderByNameAsc(java.util.Collection<Long> teamIds);
}
