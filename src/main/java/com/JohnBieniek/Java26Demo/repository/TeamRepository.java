package com.JohnBieniek.Java26Demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.JohnBieniek.Java26Demo.model.organization.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
