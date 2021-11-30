package OOP2.Tests;

import java.util.Iterator;

import OOP2.Provided.StatusIterator;
import org.junit.Assert;

import org.junit.Test;

import OOP2.Provided.FaceOOP;
import OOP2.Provided.Person;
import OOP2.Provided.PersonAlreadyInSystemException;
import OOP2.Provided.Status;
import OOP2.Solution.FaceOOPImpl;

public class Example {
	@Test
	public void ExampleTest()
	{
		FaceOOP fo = new FaceOOPImpl();
		Person pA = null, pB = null;
		try {
			pA = fo.joinFaceOOP(100, "Anne");
			pB = fo.joinFaceOOP(300, "Ben");
		} catch (PersonAlreadyInSystemException e) {
			Assert.fail();
		}
		Status statA = pA.postStatus("Going to Thailand!!");
		Status statB = pA.postStatus("Love you all!");
		
		statA.like(pB);
		Assert.assertTrue(statA.getLikesCount() == 1);
		
		Iterator<Status> stIt = pA.getStatusesRecent().iterator();
		Assert.assertEquals(stIt.next(), statB);
		Assert.assertEquals(stIt.next(), statA);

		stIt = pA.getStatusesPopular().iterator();
		Assert.assertEquals(stIt.next(), statA);
		Assert.assertEquals(stIt.next(), statB);

		Status statC = pB.postStatus("Going to Thailand!!");
		Status statD = pB.postStatus("Love you all!");

		try {
			fo.addFriendship(pA, pB);
		} catch (Exception e) {
			Assert.fail();
		}
		StatusIterator itr = null;
		try {
			itr = fo.getFeedByRecent(pA);
		} catch (Exception e) {
			Assert.fail();
		}
		Assert.assertNull(itr.next());

		try {
			itr = fo.getFeedByRecent(pB);
		} catch (Exception e) {
			Assert.fail();
		}
		Assert.assertEquals(itr.next(), statC);
		Assert.assertEquals(itr.next(), statD);

		
		try {
			Assert.assertEquals(1, (int) fo.rank(pA, pB));
		} catch (Exception e) {
			Assert.fail();
		} 
	}
}
