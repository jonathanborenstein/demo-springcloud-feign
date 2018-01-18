package com.example.demo;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
public class PersonServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonServiceApplication.class, args);
	}
	
	@Bean
	CommandLineRunner command(PersonRepository repo) {
		return args -> {
			
			Person p = new Person("Jon");
			Person p1 = new Person("Dave");
			repo.save(p);
			repo.save(p1);
			
		};
	}
}

@RestController
class RController {
	
	private final PersonRepository personRepository;
	
	public RController(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	@GetMapping("/")
	public List<Person> getPeople(){
		return personRepository.findAll();
	}
	
	@GetMapping("/person")
	public Person getPerson(@RequestParam("id") Long id){
		return personRepository.findOne(id);
	}
	
	@PostMapping("/person")
	public Person addPerson(@RequestBody Person person){
		return personRepository.save(person);
	}
	
}

@Entity
class Person {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
	public Person() {
	
	}
	
	public Person(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}

interface PersonRepository extends JpaRepository<Person, Long> {
	
}