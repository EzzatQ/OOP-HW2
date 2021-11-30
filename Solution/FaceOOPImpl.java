package OOP2.Solution;

import OOP2.Provided.ConnectionAlreadyExistException;
import OOP2.Provided.ConnectionDoesNotExistException;
import OOP2.Provided.FaceOOP;
import OOP2.Provided.Person;
import OOP2.Provided.PersonAlreadyInSystemException;
import OOP2.Provided.PersonNotInSystemException;
import OOP2.Provided.SamePersonException;
import OOP2.Provided.StatusIterator;

import java.util.Iterator;

public class FaceOOPImpl implements FaceOOP {

	/**
	 * Constructor - receives no parameters and initializes the system.
	 */
	public FaceOOPImpl()
	{}

	@Override
	public Person joinFaceOOP(Integer id, String name) throws PersonAlreadyInSystemException {
		return null;
	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public Person getUser(Integer id) throws PersonNotInSystemException {
		return null;
	}

	@Override
	public void addFriendship(Person p1, Person p2)
		throws PersonNotInSystemException, SamePersonException, ConnectionAlreadyExistException {

	}

	@Override
	public StatusIterator getFeedByRecent(Person p) throws PersonNotInSystemException {
		return null;
	}

	@Override
	public StatusIterator getFeedByPopular(Person p) throws PersonNotInSystemException {
		return null;
	}

	@Override
	public Integer rank(Person source, Person target)
		throws PersonNotInSystemException, ConnectionDoesNotExistException {
		return null;
	}

	@Override
	public Iterator<Person> iterator() {
		return null;
	}
}
