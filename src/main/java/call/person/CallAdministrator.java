package call.person;

import call.customthread.SimpleThread;

import java.util.concurrent.Future;

public class CallAdministrator implements Person{

    private static CallAdministrator instance = new CallAdministrator();
    private String id, name, surname;

    private SimpleThread adminThread;
    private Future threadReference;

    /*
        / the application is designed to manage a single call. In case I want to handle multiple instances, the singleton
        / implementation would no longer be a suitable option.
    */
    public static CallAdministrator getInstance() {
        return instance;
    }

    public void initAdmin(String id, String name, String surname) {
        instance.setId(id);
        instance.setName(name);
        instance.setSurname(surname);

        adminThread = new SimpleThread("admin");
        threadReference = null;
    }

    @Override
    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public SimpleThread getThread() { return adminThread; }

    @Override
    public Future getThreadReference() { return threadReference; }

    @Override
    public void setThreadReference(Future threadReference) {
        this.threadReference = threadReference;
    }

    @Override
    public void printValues() { System.out.println(getId()+" "+getName()+" "+getSurname()); }
}
