package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Superhero;

public interface SuperheroRepo extends JpaRepository<Superhero, Integer> {
	List<Superhero> findByPower(String power);
}
