package test2.prblm1.spring.person;


import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
@Service
public class PersonService {
	@Autowired
	private PersonRepository personRepo;
	
	public List<Person> getPersons(){
		List<Person> persons=new ArrayList<Person>();
		personRepo.findAll().forEach(persons::add);
		
		return persons;
	}
	public Page<Person> getPersons(Pageable pageable){
		return personRepo.findAll( pageable);
	}
	
	public Optional<Person> getPerson(String id){
		return personRepo.findById(id);
	}
	
	public List<Person> getPersons(final Visible visible){
		List<Person> list=personRepo.findAll();
		
		list.stream().filter(p->(visible.first_name==null||p.getFirst_name().equals(visible.first_name))&&
								(visible.last_name==null||p.getLast_name().equals(visible.last_name))&&
								(visible.age==null||p.getAge().equals(visible.age))&&
								(visible.favourite_colour==null||p.getFavourite_colour().equals(visible.last_name))
				).forEach(p->list.add(p));
		
		
		return list;
	}
	
	
	public void putPerson(Person person) {
		personRepo.save(person);
	}
	public void putPerson(Visible visible) {
		personRepo.save(PersonConversion.toPerson(visible));
	}
	public void remove(String id) {
		personRepo.deleteById(id);
	}
	public void remove(Person person) {
		personRepo.delete(person);
	}

}
