package OOP2.Solution;

import OOP2.Provided.Person;
import OOP2.Provided.Status;

import java.util.LinkedList;
import java.util.Objects;

public class StatusImpl implements Status {

	private final Integer id;
	private final Person person;
	private final String content;
	private LinkedList<Person> likes;
	/*
	 * A constructor that receives the status publisher, the text of the status
	 *  and the id of the status.
	 */
	public StatusImpl(Person publisher, String content, Integer id) {
		this.person = publisher;
		this.content = content;
		this.id = id;
		this.likes = new LinkedList<Person>();
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public Person getPublisher() {
		return person;
	}

	@Override
	public String getContent() {
		return content;
	}

	@Override
	public void like(Person p) {
		if(likes.contains(p)) {
			return;
		}
		likes.add(p);
	}

	@Override
	public void unlike(Person p) {
		if(!likes.contains(p)) {
			return;
		}
		likes.remove(p);
	}

	@Override
	public Integer getLikesCount() {
		return likes.size();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		StatusImpl status = (StatusImpl) o;
		return id.equals(status.id) && person.equals(status.person) && content.equals(status.content) && likes
			.equals(status.likes);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, person, content, likes);
	}
}
