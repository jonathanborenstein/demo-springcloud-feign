package com.example.demo;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableZuulProxy
public class PersonClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonClientApplication.class, args);
		
	}
}

@FeignClient("person-service")
interface PersonReader {
	
	@GetMapping("/")
	List<Person> read();
	
	@GetMapping("/person")
	Person getPerson(@RequestParam ("id") Long id);
	
	@PostMapping("/person")
	Person addPerson(@RequestBody Person person);
	
}

@RestController
class RController {
	
	private final PersonReader reader;
	
	public RController(PersonReader reader) {
		this.reader = reader;
	}

	@GetMapping("/")
	public List<Person> persons() {
		return this.reader.read();
	}
	
	@GetMapping("/person")
	public Person getPerson(@RequestParam("id") Long id){
		return this.reader.getPerson(id);
	}
	
	@PostMapping("/person")
	public void addPerson(@RequestBody Person person){
		this.reader.addPerson(person);
	}
}

class Person {
	
	private Long id;
	
	private String name;
	
	public Person() {}
	
	public Person(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}