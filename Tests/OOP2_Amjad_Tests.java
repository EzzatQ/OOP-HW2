package OOP2.Tests;

import java.util.Iterator;
import java.util.Objects;

import OOP2.Solution.PersonImpl;
import org.junit.Assert;

import org.junit.Test;

import OOP2.Provided.FaceOOP;
import OOP2.Provided.Person;
import OOP2.Provided.PersonAlreadyInSystemException;
import OOP2.Provided.*;
import OOP2.Provided.Status;
import OOP2.Solution.FaceOOPImpl;

public class OOP2_Amjad_Tests {
	@Test
	public void ExampleTest()
	{


////////////////////////////////////////////////////PART A/////////////////////////////////////////////////////////////
		System.out.println("");
		System.out.println("TESTING PersonImpl.....");
		Person pA = new PersonImpl(100,"Anne");
		Person pB = new PersonImpl(200,"Ben");
		Person pC = new PersonImpl(300,"Siwar");
		Person pD = new PersonImpl(400,"Amjad");
		System.out.println("SUCCESS: PersonImpl");
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("");
		System.out.println("TESTING StatusImpl.....");
		Status statA1 = pA.postStatus("[pA] Anne's Status #1");
		Status statA2 = pA.postStatus("[pA] Anne's Status #1");
		System.out.println("SUCCESS: StatusImpl");
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("");
		System.out.println("TESTING data collection functions.....");
		Assert.assertTrue(statA1.getId()==0);
		Assert.assertTrue(Objects.equals(statA1.getContent(), "[pA] Anne's Status #1"));
		Assert.assertTrue(statA1.getPublisher()==pA);

		Assert.assertTrue(statA2.getId()==1);
		Assert.assertTrue(Objects.equals(statA2.getContent(), "[pA] Anne's Status #1"));
		Assert.assertTrue(!Objects.equals(statA2.getPublisher(), pB));
		System.out.println("SUCCESS: data collection functions");
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("");
		System.out.println("TESTING like & unlike functionality.....");
		Assert.assertTrue(statA1.getLikesCount()==0);
		statA1.like(pA);
		Assert.assertTrue(statA1.getLikesCount()==1);
		statA1.unlike(pA);
		Assert.assertTrue(statA1.getLikesCount()==0);
		statA1.like(pA);
		statA1.like(pA);
		statA1.like(pA);
		statA1.like(pA);
		Assert.assertTrue(statA1.getLikesCount()==1);
		statA1.like(pB);
		statA1.like(pC);
		statA1.like(pD);
		Assert.assertTrue(statA1.getLikesCount()==4);
		System.out.println("SUCCESS: like & unlike functionality");

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("");
		System.out.println("TESTING equals after being Overrided......");
		Assert.assertTrue(!statA1.equals(statA2));
		Assert.assertTrue(statA1.equals(statA1));
		Assert.assertTrue(statA2.equals(statA2));
		System.out.println("SUCCESS: equals after being Overrided");

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("");
		/*   TESTING PersonImpl   */
		System.out.println("TESTING PersonImpl Data Collection.....");

		/* Checking data collection functions */
		Assert.assertTrue(pA.getId()==100);
		Assert.assertTrue(pA.getName()=="Anne");
		Assert.assertTrue(pB.getId()==200);
		Assert.assertTrue(pB.getName()=="Ben");
		Assert.assertTrue(pC.getId()==300);
		Assert.assertTrue(pC.getName()=="Siwar");
		Assert.assertTrue(pD.getId()==400);
		Assert.assertTrue(pD.getName()=="Amjad");
		System.out.println("SUCCESS: PersonImpl Data Collection");
		
		/* !! postStatus was tested above !! */

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("");
		System.out.println("TESTING Person.equals()....");
		Person tmp1 = new PersonImpl(100 , "temporary person");
		//they share the same id; therefore they are the same person.
		Assert.assertTrue(tmp1.equals(pA));
		System.out.println("SUCCESS: Person.equals()");

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("");
		System.out.println("TESTING addFriend(Person p).....");

		try {
			pA.addFriend(pB);
			pB.addFriend(pA);
			Assert.assertTrue(pA.getFriends().contains(pB));
			Assert.assertTrue(pB.getFriends().contains(pA));
			pB.addFriend(pC);
			pC.addFriend(pB);
			Assert.assertTrue(pC.getFriends().contains(pB));
			Assert.assertTrue(pB.getFriends().contains(pC));
			pC.addFriend(pD);
			pD.addFriend(pC);
			Assert.assertTrue(pC.getFriends().contains(pD));
			Assert.assertTrue(pD.getFriends().contains(pC));
			Assert.assertTrue(pA.getFriends().size()==1);
			Assert.assertTrue(pB.getFriends().size()==2);
			Assert.assertTrue(pC.getFriends().size()==2);
			Assert.assertTrue(pD.getFriends().size()==1);
		}catch(Exception e){
			Assert.fail();
		}

		System.out.println("SUCCESS: addFriend(Person p)");

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("");
		System.out.println("TESTING getStatusesRecent.....");
		Status statA3 =pA.postStatus("[pA] Anne's Status #3");
		statA3.like(pA);
		statA3.like(pB);
		statA3.like(pC);
		Status statA4 =pA.postStatus("[pA] Anne's Status #4");
		statA4.like(pA);
		statA4.like(pB);
		Iterator<Status> stIt = pA.getStatusesRecent().iterator();
		Assert.assertEquals(stIt.next(), statA4);
		Assert.assertEquals(stIt.next(), statA3);
		Assert.assertEquals(stIt.next(), statA2);
		Assert.assertEquals(stIt.next(), statA1);
		System.out.println("SUCCESS: getStatusesRecent");

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("");
		System.out.println("TESTING getStatusesPopular.....");
		// likes: statA1 = 4 , statA2 = 0 , statA3 = 3 , statA4 = 2 SO ORDER WOULD BE A1,A3,A4,A2

		stIt = pA.getStatusesPopular().iterator();
		Assert.assertEquals(stIt.next(), statA1);
		Assert.assertEquals(stIt.next(), statA3);
		Assert.assertEquals(stIt.next(), statA4);
		Assert.assertEquals(stIt.next(), statA2);

		System.out.println("SUCCESS: getStatusesPopular");

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////PART B//////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("");
		FaceOOP fo = new FaceOOPImpl();
		Person fo_pA = null, fo_pB = null , fo_pC = null , fo_pD = null;
		try {
		//Checking implementation
			System.out.println("⎾¯TESTING joinFaceOOP.....");
			fo_pA = fo.joinFaceOOP(100, "Anne");
			fo_pB = fo.joinFaceOOP(200, "Ben");
			fo_pC = fo.joinFaceOOP(300,"Siwar");
			fo_pD = fo.joinFaceOOP(400,"Amjad");
		} catch (PersonAlreadyInSystemException e) {
			Assert.fail();
		}

		System.out.println("|		TESTING PersonAlreadyInSystemException in joinFaceOOP....");
		try{
			Person fo_pE = fo.joinFaceOOP(400,"Majd");

			System.out.println("|		FAILED: PersonAlreadyInSystemException in joinFaceOOP!!");
			Assert.fail();
		} catch (Exception e) {
			System.out.println("|		SUCCESS: PersonAlreadyInSystemException in joinFaceOOP Works!!");
		}
		System.out.println("|_SUCCESS: joinFaceOOP");

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("");
		System.out.println("TESTING size()....");
		Assert.assertTrue(fo.size()==4);
		System.out.println("SUCCESS: size()");

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("");
		System.out.println("⎾¯TESTING getUser(Integer id)....");
		try {
			Assert.assertTrue(fo.getUser(100).equals(fo_pA));
			Assert.assertTrue(fo.getUser(200).equals(fo_pB));
			Assert.assertTrue(fo.getUser(300).equals(fo_pC));
			Assert.assertTrue(fo.getUser(400).equals(fo_pD));
		} catch (Exception e) {
			Assert.fail();
		}

		System.out.println("|		TESTING PersonNotInSystemException in getUser.....");
		try {
			fo.getUser(101);

			System.out.println("|		FAILED: PersonNotInSystemException in getUser!!");
			Assert.fail();
		} catch (PersonNotInSystemException e) {
			System.out.println("|		SUCCESS: PersonNotInSystemException in getUser Works!!");
		}
		System.out.println("|_SUCCESS: getUser(Integer id)");

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("");
		System.out.println("⎾¯TESTING addFriendship.....");
		try {
			fo.addFriendship(fo_pA, fo_pB);
			Assert.assertTrue(fo_pA.getFriends().contains(fo_pB));
			Assert.assertTrue(fo_pB.getFriends().contains(fo_pA));
		} catch (Exception e) {
			Assert.fail();
		}


		System.out.println("|		TESTING SamePersonException in addFriendship.......");
		try {
			fo.addFriendship(fo_pA, fo_pA);

			System.out.println("|		FAILED: SamePersonException in addFriendship!!");
			Assert.fail();
		}
		catch (Exception e) {
			System.out.println("|		SUCCESS: SamePersonException in addFriendship Works!!");
		}


		System.out.println("|		TESTING ConnectionAlreadyExistException in addFriendship........");
		try {
			fo.addFriendship(fo_pA, fo_pB);

			System.out.println("|		FAILED: ConnectionAlreadyExistException in addFriendship!!");
			Assert.fail();
		}
		catch (Exception e) {
			System.out.println("|		SUCCESS: ConnectionAlreadyExistException in addFriendship WORKS!!");
		}



		System.out.println("|		TESTING PersonNotInSystemException in addFriendship.....");
		try {
			Person tmp2 = new PersonImpl(500,"Tom");
			fo.addFriendship(fo_pA, tmp2);
			System.out.println("|		FAILED: PersonNotInSystemException in addFriendship!!");
			Assert.fail();
		}
		catch (Exception e) {
			System.out.println("|		SUCCESS: PersonNotInSystemException in addFriendship WORKS!!");
		}
		System.out.println("|_SUCCESS: addFriendship!!");


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		System.out.println("");
		System.out.println("⎾¯¯TESTING getFeedByRecent(Person p).....");
		try {
			Status s1 = fo_pA.postStatus("#A1");
			Status s2 = fo_pA.postStatus("#A2");
			Status s3 = fo_pA.postStatus("#A3");
			Status s4 = fo_pA.postStatus("#A4");
			Status s5 = fo_pA.postStatus("#A5");
			Status s6 = fo_pC.postStatus("#C1");
			fo.addFriendship(fo_pB,fo_pC);
			StatusIterator sti = fo.getFeedByRecent(fo_pB);
			Assert.assertEquals(sti.next(), s5);
			Assert.assertEquals(sti.next(), s4);
			Assert.assertEquals(sti.next(), s3);
			Assert.assertEquals(sti.next(), s2);
			Assert.assertEquals(sti.next(), s1);
			Assert.assertEquals(sti.next(), s6);
			

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

			System.out.println("|⎾¯TESTING getFeedByPopular(Person p).....");
			s1.like(fo_pA);
			s1.like(fo_pB);
			s1.like(fo_pC);
			s1.like(fo_pD);
			s3.like(fo_pA);
			s3.like(fo_pB);
			s3.like(fo_pC);
			s2.like(fo_pA);
			s2.like(fo_pB);
			s5.like(fo_pA);
			// sorted by likes counts: s1 s3 s2 s5 s4 s6
			StatusIterator sti2 = fo.getFeedByPopular(fo_pB);
			Assert.assertEquals(sti2.next(), s1);
			Assert.assertEquals(sti2.next(), s3);
			Assert.assertEquals(sti2.next(), s2);
			Assert.assertEquals(sti2.next(), s5);
			Assert.assertEquals(sti2.next(), s4);
			Assert.assertEquals(sti2.next(), s6);
		} catch (Exception e) {
			Assert.fail();
		}

		System.out.println("||		TESTING PersonNotInSystemException in getFeedByRecent ");
		try {
			Person tmp2 = new PersonImpl(500,"Tom");
			StatusIterator sti3 = fo.getFeedByRecent(tmp2);
			System.out.println("||		FAILED: PersonNotInSystemException in getFeedByRecent!!");
			Assert.fail();
		} catch (PersonNotInSystemException e) {
			System.out.println("||		SUCCESS: PersonNotInSystemException in getFeedByRecent Works!!");
		}


		System.out.println("||		TESTING PersonNotInSystemException in getFeedByPopular ");
		try {
			Person tmp3 = new PersonImpl(500,"Tom");
			StatusIterator sti4 = fo.getFeedByPopular(tmp3);

			System.out.println("||		FAILED: PersonNotInSystemException in getFeedByPopular!!");
			Assert.fail();
		} catch (PersonNotInSystemException e) {
			System.out.println("||		SUCCESS: PersonNotInSystemException in getFeedByPopular Works!!");
		}

		System.out.println("||_SUCCESS: getFeedByRecent(Person p)");
		System.out.println("|__SUCCESS: getFeedByPopular(Person p)");

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		System.out.println("");
		System.out.println("⎾¯TESTING rank(Source,Target).....");
		FaceOOP fo2 = new FaceOOPImpl();
		Person fo2_pA = null, fo2_pB = null , fo2_pC = null , fo2_pD = null;
		try {
			fo2_pA= fo2.joinFaceOOP(1,"A");
			fo2_pB= fo2.joinFaceOOP(2,"B");
			fo2_pC= fo2.joinFaceOOP(3,"C");
			fo2_pD= fo2.joinFaceOOP(4,"D");
			fo2.addFriendship(fo2_pA,fo2_pB);
			fo2.addFriendship(fo2_pB,fo2_pC);
			fo2.addFriendship(fo2_pC,fo2_pD);
			Integer x = fo2.rank(fo2_pA,fo2_pD);
			Assert.assertTrue(x==3);
			fo2.addFriendship(fo2_pA,fo2_pD);
			x = fo2.rank(fo2_pA,fo2_pD);
			Assert.assertTrue(x==1);
		}catch(Exception e){}

		System.out.println("|		TESTING PersonNotInSystemException in rank ");
		try {
			Person tmp4 = new PersonImpl(500,"Tom");
			fo2.rank(tmp4,fo2_pA);
			System.out.println("|		FAILED: PersonNotInSystemException in rank!!");
			Assert.fail();
		} catch (Exception e) {
			System.out.println("|		SUCCESS: PersonNotInSystemException in rank Works!!");
		}

		System.out.println("|		TESTING ConnectionDoesNotExistException in rank ");
		try {
			Person fo2_pE = fo2.joinFaceOOP(5,"E");
			fo2.rank(fo2_pE,fo2_pA);
			System.out.println("|		FAILED: ConnectionDoesNotExistException in rank!!");
			Assert.fail();
		} catch (Exception e) {
			System.out.println("|		SUCCESS: ConnectionDoesNotExistException in rank Works!!");
		}
		System.out.println("|_SUCCESS: rank(Source,Target)");

	}
}
