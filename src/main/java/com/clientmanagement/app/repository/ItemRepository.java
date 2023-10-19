package com.clientmanagement.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clientmanagement.app.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {

}
