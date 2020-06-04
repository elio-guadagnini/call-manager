package call;

import call.customlists.ExcludedParticipants;
import call.customlists.Participants;
import call.person.CallAdministrator;
import call.person.Participant;
import call.person.Presenter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.*;

public class CallTest {

    private Call firstCall;

    @Before
    public void init() {
        firstCall = new Call("Elio", "Guadagnini");
    }

    // a workaround to check whether the application can be executed in 5 seconds
    @Test(timeout = 5000)
    public void collectTests() {
        giveRandomPresenterPermissionTest();
        givePresenterPermissionTest();
        removePresenterPermissionTest();
        startPresentationTest();
        stopPresentationTest();
    }

    @Test
    public void giveRandomPresenterPermissionTest() {
        firstCall.giveRandomPresenterPermission();
        Presenter testPresenter = firstCall.getPresenter();
        assertEquals("Elio", testPresenter.getName());
        assertEquals("Guadagnini", testPresenter.getSurname());
    }

    @Test
    public void givePresenterPermissionTest() {
        firstCall.removePresenterPermission();

        firstCall.givePresenterPermission(null, "Elio", "Guadagnini");
        Presenter testPresenter = firstCall.getPresenter();
        assertNotEquals("Elio", testPresenter.getName());
        assertNotEquals("Guadagnini", testPresenter.getSurname());
    }

    @Test
    public void removePresenterPermissionTest() {
        firstCall.removePresenterPermission();
        Presenter presenter = firstCall.getPresenter();
        assertNull(presenter.getName());
        assertNull(presenter.getSurname());
    }

    @Test
    public void startPresentationTest() {
        firstCall.giveRandomPresenterPermission();
        Presenter presenter = firstCall.getPresenter();
        firstCall.startPresentation();

        assertTrue(presenter.getPresenting());
    }

    @Test
    public void stopPresentationTest() {
        firstCall.giveRandomPresenterPermission();
        Presenter presenter = firstCall.getPresenter();
        firstCall.stopPresentation();
        assertFalse(presenter.getPresenting());
    }

    @After
    public void destroy() {
        firstCall = null;
    }
}