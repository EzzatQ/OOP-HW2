package OOP2.Solution;

import OOP2.Provided.ConnectionAlreadyExistException;
import OOP2.Provided.ConnectionDoesNotExistException;
import OOP2.Provided.FaceOOP;
import OOP2.Provided.Person;
import OOP2.Provided.Status;
import OOP2.Provided.PersonAlreadyInSystemException;
import OOP2.Provided.PersonNotInSystemException;
import OOP2.Provided.SamePersonException;
import OOP2.Provided.StatusIterator;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class FaceOOPImpl implements FaceOOP {

	protected LinkedList<Person> users;

	/**
	 * Constructor - receives no parameters and initializes the system.
	 */
	public FaceOOPImpl() {
		this.users = new LinkedList<Person>();
	}

	@Override
	public Person joinFaceOOP(Integer id, String name) throws PersonAlreadyInSystemException {
		boolean alreadyExists = users.stream().map(Person::getId).collect(Collectors.toList()).contains(id);
		if(alreadyExists){
			throw new PersonAlreadyInSystemException();
		}
		final Person person = new PersonImpl(id, name);
		users.add(person);
		return person;
	}

	@Override
	public int size() {
		return users.size();
	}

	@Override
	public Person getUser(Integer id) throws PersonNotInSystemException {
		Person person = users.stream().filter(person1 -> person1.getId().equals(id)).findAny().orElse(null);
		if(person == null){
			throw new PersonNotInSystemException();
		}
		return person;
	}

	@Override
	public void addFriendship(Person p1, Person p2)
		throws PersonNotInSystemException, SamePersonException, ConnectionAlreadyExistException {
		if(p1.equals(p2)){
			throw new SamePersonException();
		}
		//check is p1 and p2 in system. if not these calls will throw a PersonNotInSystemException.
		getUser(p1.getId());
		getUser(p2.getId());

		if(areFriends(p1, p2)){
			throw new ConnectionAlreadyExistException();
		}

		p1.addFriend(p2);
		p2.addFriend(p1);
	}

	@Override
	public StatusIterator getFeedByRecent(Person p) throws PersonNotInSystemException {
		getUser(p.getId());
		return (StatusIterator) p.getStatusesRecent().iterator();
	}

	@Override
	public StatusIterator getFeedByPopular(Person p) throws PersonNotInSystemException {
		getUser(p.getId());
		return (StatusIterator) p.getStatusesPopular().iterator();
	}

	@Override
	public Integer rank(Person source, Person target)
		throws PersonNotInSystemException, ConnectionDoesNotExistException {
		return null;
	}

	@Override
	public Iterator<Person> iterator() {
		return users.iterator();
	}

	private boolean areFriends(Person p1, Person p2) {
		Person p3 = p1.getFriends().stream().filter(person -> person.equals(p2)).findAny().orElse(null);
		Person p4 = p2.getFriends().stream().filter(person -> person.equals(p1)).findAny().orElse(null);
		return p3 != null || p4 != null;
	}

	public enum SortType {
		RECENT,
		POPULAR
	}

}

class StatusIteratorImpl implements StatusIterator {

	private static List<Status> sortedStatuses;

	StatusIteratorImpl(List<Person> users, FaceOOPImpl.SortType sortType){
		sortedStatuses = Collections.emptyList();
		switch(sortType){
			case RECENT: {
				users.stream().sorted((p1, p2) -> p1.getId().compareTo(p2.getId())).map(Person::getStatusesRecent).forEach(s -> s.forEach(s1 -> sortedStatuses.add(s1)));
			} case POPULAR: {
				users.stream().sorted((p1, p2) -> p1.getId().compareTo(p2.getId())).map(Person::getStatusesPopular).forEach(s -> s.forEach(s1 -> sortedStatuses.add(s1)));
			}
		}
	}

	@Override
	public boolean hasNext() {
		return false;
	}

	@Override
	public Status next() {
		return null;
	}
}