package test2.prblm1.spring.person;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Person {
	@Id 
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String id;
	
	
	private String first_name;
	private String last_name,
	age,
	favourite_colour;
	
	public Person() {
	}
	public Person(String first_name, String last_name, String age, String favourite_colour) {
		this.first_name=first_name;
		this.last_name=last_name;
		this.age=age;
		this.favourite_colour=favourite_colour;
	}
	public String getId() {
		return id;
	}
	public Person setId(String id) {
		if(id!=null) {
			this.id=id;
		}
		return this;
	}

	public String getFirst_name() {
		if(first_name==null) {
			return "";
		}else {
			return first_name;
		}
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		if(last_name==null) {
			return "";
		}else {
			return last_name;
		}
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getAge() {
		if(last_name==null) {
			return "";
		}else {
			return age;
		}
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getFavourite_colour() {
		if(last_name==null) {
			return "";
		}else {
			return favourite_colour;
		}
	}

	public void setFavourite_colour(String favourite_colour) {
		this.favourite_colour = favourite_colour;
	}
	
	
	
	
	
}
