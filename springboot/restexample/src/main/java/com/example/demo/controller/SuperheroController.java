package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.SuperheroRepo;
import com.example.demo.model.Superhero;

@RestController
public class SuperheroController {

	@Autowired
	SuperheroRepo repo;

	//welcome page
	@RequestMapping("/")
	public String Home() {
		return "Welcome to superhero api";
	}
	
	//To get the list of all superheroes details
	@GetMapping("/superheroes")
	public List<Superhero> getSuperheroes() {
		return repo.findAll();
	}
    
	//To update a superhero
	@PutMapping("/superheroes")
	public Superhero CreateOrUpdate(@RequestBody Superhero superhero) {
		repo.save(superhero);
		System.out.println("updated");
		return superhero;
	}
    
	//To add a superhero
	@PostMapping(path = "/superheroes")
	public Superhero addSuperhero(@RequestBody Superhero superhero) {
		repo.save(superhero);
		return superhero;
	}

	//To look up for a specific superhero details
	@GetMapping("/superheroes/{id}")
	public Optional<Superhero> getSuperhero(@PathVariable("id") int id) {
		return repo.findById(id);
	}

	//To delete a specific superhero 
	@DeleteMapping("/superheroes/{id}")
	public Optional<Superhero> deleteSuperhero(@PathVariable("id") int id) {
		repo.delete(repo.getOne(id));
		return repo.findById(id);
	}

}
