package call.person;

import call.customthread.SimpleThread;

import java.util.concurrent.Future;

public interface Person {

    String name = null, surname = null;
    boolean admin = false;

    String getId();

    String getName();

    String getSurname();

    SimpleThread getThread();

    Future getThreadReference();

    void setThreadReference(Future threadReference);

    void printValues();
}
