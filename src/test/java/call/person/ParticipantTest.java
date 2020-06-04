package call.person;

import call.customthread.SimpleThread;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.security.Principal;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.Assert.*;

public class ParticipantTest {

    private Participant firstParticipant;
    private Participant newPart;

    private ExecutorService executor = Executors.newFixedThreadPool(30);

    @Before
    public void init() {
        firstParticipant = new Participant("1", "Elio", "Guadagnini");
    }

    @Test
    public void getIdTest() {
        assertEquals("1", firstParticipant.getId());
    }

    @Test
    public void getNameTest() {
        assertEquals("Elio", firstParticipant.getName());
    }

    @Test
    public void getSurnameTest() {
        assertEquals("Guadagnini", firstParticipant.getSurname());
    }

    @Test
    public void getExclusionDateTest() {
        assertNull(firstParticipant.getExclusionDate());
    }

    @Test
    public void setExclusionDateTest() {
        Date formerDate = firstParticipant.getExclusionDate();
        firstParticipant.setExclusionDate();
        assertNotEquals(formerDate, firstParticipant.getExclusionDate());
    }

    @Test
    public void getThreadTest() {
        SimpleThread participantThread = firstParticipant.getThread();
        executor.execute(participantThread);

        assertEquals(" pool size = 1", executor.toString().split("\\,")[1]);
    }

    @Test
    public void getThreadReferenceTest() {
        newPart = new Participant("20", "New", "Participant");

        assertNull(newPart.getThreadReference());
    }

    @Test
    public void setThreadReferenceTest() {
        newPart = new Participant("20", "New", "Participant");

        Future<?> future = executor.submit(newPart.getThread());
        newPart.setThreadReference(future);

        assertNotNull(newPart.getThreadReference());
    }

    @After
    public void destroy() {
        firstParticipant = null;
        executor.shutdown();
    }
}