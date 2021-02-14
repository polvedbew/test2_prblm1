package test2.prblm1.spring.person;

public class ResponseObject<T> {
	public T persons;
	
	public ResponseObject(T visibles) {
		this.persons=visibles;
	}
	
}
