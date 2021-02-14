package test2.prblm1.spring.person;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import test2.prblm1.spring.security.AuthRequest;
import test2.prblm1.spring.security.AuthResponse;
import test2.prblm1.spring.security.JwtUtil;
import test2.prblm1.spring.security.UserDetailService;


@RestController
public class RequestController {
	@Autowired
	private PersonService personService;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserDetailService userDetailService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@RequestMapping("/")
	public String test() {
		return "200";
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/person")
	public String add(@RequestBody Visible visible) {
		try {
			personService.putPerson(visible);
			return "201";
		}catch(Exception e) {
			return "500";
		}
	}
	
	
	
	@RequestMapping(method=RequestMethod.GET,value="/person")
	public ResponseObject<ArrayList<Visible>> list() {
		try {
			ArrayList<Visible> visibles=new ArrayList<Visible>();
			personService.getPersons().forEach(p->{
				visibles.add(PersonConversion.toVisible(p));
			});
		return new ResponseObject<ArrayList<Visible>>(visibles);
		}catch(Exception e) {
			return null;
		}
	}
	
	
	@RequestMapping(method=RequestMethod.PUT,value="/person/{full_name}")
	public String remove(@RequestBody Visible visible,@PathVariable String full_name) {
		try {
			try {
				personService.putPerson(((PersonConversion.toPerson(visible)).setId(getId(full_name))));
				return "200";
			}catch(EmptyResultDataAccessException e) {
				return "404";
			}
		}catch(Exception exception) {
			return "500";
		}
	}
	
	@RequestMapping(method=RequestMethod.DELETE,value="/person/{full_name}")
	public String removeName(@PathVariable String full_name) {
		try {
			try {
				personService.remove(getId(full_name));
				return "200";
			}catch(EmptyResultDataAccessException e) {
				return "404";
			}
		}catch(Exception exception) {
			return "500";
		}
	}
	
	
	@RequestMapping(method=RequestMethod.DELETE,value="/person")
	public String removeObject(@RequestBody Visible visible) {
		try {
			try {
				personService.remove(getId(visible));
				return "200";
			}catch(EmptyResultDataAccessException e) {
				return "404";
			}
		}catch(Exception exception) {
			return "500";
		}
	}
	
	//person?page=1&size=2
	@RequestMapping(method=RequestMethod.GET,value="/person_page")                      
	public ResponseObject<List<Visible>> getlist_page_visibles(Pageable pageable) {
		List<Visible> visibles=new ArrayList<Visible>();
		personService.getPersons(pageable).forEach(p->{
			visibles.add(PersonConversion.toVisible(p));
		});
		return new ResponseObject<List<Visible>>(visibles);
	}
	
	
	
	@RequestMapping(method=RequestMethod.POST,value="/persons")
	public String add_list(@RequestBody ResponseObject<ArrayList<Visible>> object) {
		try {
			object.persons.forEach(v->personService.putPerson(PersonConversion.toPerson(v)));
			
			return "200";
		}catch(Exception e) {
			return "500";
		}
	}
	
	
	
	@RequestMapping(method=RequestMethod.GET,value="/admin_list")
	public ResponseObject<Page<Person>> getlist_page(Pageable pageable) {
		return new ResponseObject<Page<Person>>(personService.getPersons(pageable));
	}
	
	
	@RequestMapping(method=RequestMethod.GET,value="/admin_get/{id}")
	public Optional<Person> getPerson(@RequestBody String id) {
		return personService.getPerson(id);
	}
	
	
	
	@RequestMapping(method=RequestMethod.GET,value="/admin_list_match")
	public List<Person> getlist_page(String first_name,String last_name,String age,String favourite_colour) {
		try {
			return personService.getPersons(new Visible(first_name,last_name,age,favourite_colour));
		}catch(Exception e) {
			return null;
		}
	}

	@RequestMapping(value="/authenticate",method=RequestMethod.POST)
	public ResponseEntity<?> createToken(@RequestBody AuthRequest authRequest) throws Exception {
		try{
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword()));
		}catch(BadCredentialsException bce) {
			throw new Exception("Failed to authenticate");
		}
		final UserDetails userDetails =userDetailService
				.loadUserByUsername(authRequest.getUsername());
		final String jwt=jwtUtil.generateToken(userDetails);
	
		return ResponseEntity.ok(new AuthResponse(jwt));
	}
	
	
	
	
	@RequestMapping(method=RequestMethod.DELETE,value="/admin_list/{id}")
	public String removeId(@PathVariable String id) {
		try {
			try {
				personService.remove(id);
				return "200";
			}catch(EmptyResultDataAccessException e) {
				return "404";
			}
		}catch(Exception exception) {
			return "500";
		}
	}
	
	
	private String getId(Visible visible) {
		return personService.getPersons()
			.stream()
			.filter(p->(p.getFirst_name().equals(visible.first_name)
					&&p.getLast_name().equals(visible.last_name)
					&&p.getAge().equals(visible.age)
					&&p.getFavourite_colour().equals(visible.favourite_colour)))
			.findFirst()
			.get()
			.getId();
	}
	private String getId(String full_name) {
		return personService.getPersons()
			.stream()
			.filter((p->(p.getFirst_name()+p.getLast_name()).equals(full_name)))
			.findFirst()
			.get()
			.getId();
			
	}
	
	
}
