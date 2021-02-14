package test2.prblm1.spring.person;

public class PersonConversion {
	public static Person toPerson(final Visible visible) {
		return new Person(visible.first_name, visible.last_name, visible.age, visible.favourite_colour);
	}
	public static Visible toVisible(final Person person) {
		return new Visible(person.getFirst_name(), person.getLast_name(), person.getAge(), person.getFavourite_colour());
	}
}
