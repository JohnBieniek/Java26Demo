package com.JohnBieniek.Java26Demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.JohnBieniek.Java26Demo.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
}