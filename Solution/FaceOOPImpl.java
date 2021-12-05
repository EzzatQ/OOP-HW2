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

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
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
		return new StatusIteratorImpl(p, SortType.RECENT);
	}

	@Override
	public StatusIterator getFeedByPopular(Person p) throws PersonNotInSystemException {
		getUser(p.getId());
		return new StatusIteratorImpl(p, SortType.POPULAR);
	}

	@Override
	public Integer rank(Person source, Person target)
		throws PersonNotInSystemException, ConnectionDoesNotExistException {
		getUser(source.getId());
		getUser(target.getId());
		Integer maxId = users.stream().map(Person::getId).max(Integer::compareTo).orElse(-1);
		int[] discovered = new int[maxId+1];
		for (int i = 1; i < maxId + 1; i++) {
			discovered[i] = -1;
		}
		Queue<Person> q = new ArrayDeque<>();
		q.add(source);
		discovered[source.getId()] = 0;
		int res = rank_aux(target, q, discovered);
		if(res == -1){
			throw new ConnectionDoesNotExistException();
		}
		return res;
	}

	private Integer rank_aux(Person target, Queue<Person> q, int[] discovered) {
		if(q.isEmpty()) return -1;
		Person v = q.remove();
		if(v.equals(target)) return discovered[v.getId()];
		for(Person friend: v.getFriends()){
			if(discovered[friend.getId()] == -1) {
				discovered[friend.getId()] = discovered[v.getId()] + 1;
				q.add(friend);
			}
		}
		return rank_aux(target, q, discovered);

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

	private static Iterator<Status> itr;

	StatusIteratorImpl(Iterable<Status> statuses){
		itr = statuses.iterator();
	}

	StatusIteratorImpl(Person user, FaceOOPImpl.SortType sortType){
		LinkedList<Status> sortedStatuses = new LinkedList<Status>();
		switch(sortType){
			case RECENT: {

				user.getFriends().stream().sorted((p1, p2) -> p1.getId().compareTo(p2.getId())).map(Person::getStatusesRecent).forEach(s -> s.forEach(sortedStatuses::add));
			} case POPULAR: {
				user.getFriends().stream().sorted((p1, p2) -> p1.getId().compareTo(p2.getId())).map(Person::getStatusesPopular).forEach(s -> s.forEach(sortedStatuses::add));
			}
		}
		itr = sortedStatuses.iterator();
	}

	@Override
	public boolean hasNext() {
		return itr.hasNext();
	}

	@Override
	public Status next() {
		return itr.next();
	}
}