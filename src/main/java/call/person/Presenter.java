package call.person;

import call.customthread.SimpleThread;

import java.util.concurrent.Future;

public class Presenter extends Participant {

    private static Presenter presenterInstance = new Presenter(null, null, null);

    private String id, name, surname;
    private boolean presenting;

    private SimpleThread presenterThread;
    private Future threadReference;

    /*
        / this class works as an assignment to the call.person.Participant who becomes presenter.
        / He or She gaines the function to display a presentation
     */
    private Presenter(String id, String name, String surname) {
        super(id, name, surname);
        presenting = false;
    }

    public static Presenter getInstance() {
        return presenterInstance;
    }

    public void init(String id, String name, String surname) {
        setId(id);
        setName(name);
        setSurname(surname);

        presenterThread = new SimpleThread("presenter");
        threadReference = null;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public SimpleThread getThread() { return presenterThread; }

    public Future getThreadReference() {
        return threadReference;
    }

    public void setThreadReference(Future threadReference) {
        this.threadReference = threadReference;
    }

    public boolean getPresenting() {
        return presenting;
    }

    public void setPresenting(boolean presenting) {
        this.presenting = presenting;
    }

    public void startPresenting() { System.out.println("Presentation started"); }

    public void stopPresenting() { System.out.println("Presentation ended up"); }

    public void printValues() { System.out.println(getId()+" "+getName()+" "+getSurname()+" "+getPresenting()); }
}
