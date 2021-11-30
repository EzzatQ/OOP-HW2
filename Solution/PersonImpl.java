package OOP2.Solution;

import OOP2.Provided.ConnectionAlreadyExistException;
import OOP2.Provided.Person;
import OOP2.Provided.SamePersonException;
import OOP2.Provided.Status;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;
import java.util.stream.Collectors;

public class PersonImpl implements Person {

	private final Integer id;
	private final String name;
	private LinkedList<Status> statuses;
	private LinkedList<Person> friends;
	private Integer lastStatusId;
	/**
	 * Constructor receiving person's id and name.
	 */
	public PersonImpl(Integer id, String name) {
		this.id = id;
		this.name = name;
		this.statuses = new LinkedList<Status>();
		this.friends = new LinkedList<Person>();
		this.lastStatusId = 0;
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Status postStatus(String content) {
		StatusImpl status = new StatusImpl(this, content, lastStatusId);
		lastStatusId++;
		statuses.add(status);
		return status;
	}

	@Override
	public void addFriend(Person p) throws SamePersonException, ConnectionAlreadyExistException {
		if(this.equals(p)){
			throw new SamePersonException();
		}
		if(friends.contains(p)){
			throw new ConnectionAlreadyExistException();
		}
		friends.add(p);
	}

	@Override
	public Collection<Person> getFriends() {
		return friends;
	}

	@Override
	public Iterable<Status> getStatusesRecent() {
		return statuses.stream().sorted((p1, p2) -> -p1.getId().compareTo(p2.getId())).collect(Collectors.toList());
	}

	@Override
	public Iterable<Status> getStatusesPopular() {
		return statuses.stream().sorted((p1, p2) -> -p1.getLikesCount().compareTo(p2.getLikesCount())).collect(Collectors.toList());
	}

	@Override
	public int compareTo(Person o) {
		return -id.compareTo(o.getId());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		PersonImpl person = (PersonImpl) o;
		return id.equals(person.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
