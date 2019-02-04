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

	@RequestMapping("/")
	public String Home() {
		return "home.jsp";
	}

	@GetMapping("/superheroes")
	public List<Superhero> getSuperheroes() {
		return repo.findAll();
	}

	@PutMapping("/superheroes")
	public Superhero CreateOrUpdate(@RequestBody Superhero superhero) {
		repo.save(superhero);
		System.out.println("updated");
		return superhero;
	}

	@PostMapping(path = "/superheroes")
	public Superhero addSuperhero(@RequestBody Superhero superhero) {
		repo.save(superhero);
		return superhero;
	}

	@DeleteMapping(path = "/superheroes")
	public List<Superhero> deleteAllSuperhero() {
		repo.deleteAll();
		return repo.findAll();
	}

	@GetMapping("/superheroes/{id}")
	public Optional<Superhero> getSuperhero(@PathVariable("id") int id) {
		return repo.findById(id);
	}

	@DeleteMapping("/superheroes/{id}")
	public Optional<Superhero> deleteSuperhero(@PathVariable("id") int id) {
		repo.delete(repo.getOne(id));
		return repo.findById(id);
	}

}
