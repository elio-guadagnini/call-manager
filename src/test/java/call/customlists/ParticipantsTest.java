package call.customlists;

import call.Call;
import call.person.CallAdministrator;
import call.person.Participant;
import call.person.Person;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class ParticipantsTest {

    private Participants parts;

    @Before
    public void init() {
        CallAdministrator callAdmin = CallAdministrator.getInstance();
        callAdmin.initAdmin("1", "Elio", "Guadagnini");

        parts = new Participants(callAdmin);

        parts.add(new Participant("2", "Anna", "Rossi"));
        parts.add(new Participant("3", "Mario", "Rossi"));
    }

    @Test
    public void addTest() {
        parts.add(new Participant("4", "Test", "Test2"));
        assertEquals(4, parts.size());
    }

    @Test
    public void removeTupleTest() {
        parts.removeTuple(new Participant("4", "Test", "Test2"));
        assertEquals(3, parts.size());
    }

    @Test
    public void sizeTest() {
        assertEquals(3, parts.size());
    }

    @Test(expected = NullPointerException.class)
    public void getTupleTest() {
        Person firstParticipant = parts.getTuple(new Participant("4", "Test", "Test2"));
        assertEquals("4", firstParticipant.getId());
        assertEquals("Test", firstParticipant.getName());
        assertEquals("Test2", firstParticipant.getSurname());
    }

    @Test
    public void getTupleTest2() {
        Person firstParticipant = parts.getTuple(new Participant("3", "Mario", "Rossi"));
        assertEquals("3", firstParticipant.getId());
        assertNotEquals("Maria", firstParticipant.getName());
        assertEquals("Rossi", firstParticipant.getSurname());
    }

    @Test
    public void containsTest() {
        assertTrue(parts.contains(new Participant("2", "Anna", "Rossi")));
    }

    @Test
    public void containsTest2() {
        assertFalse(parts.contains(new Participant("100", "Anna", "Rossi")));
    }

    @After
    public void destroy() {
        parts = null;
    }
}