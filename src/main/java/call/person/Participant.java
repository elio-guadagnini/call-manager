package call.person;

import call.customthread.SimpleThread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Future;

public class Participant implements Person{

    private String id, name, surname;
    private Date exclusionDate;

    private SimpleThread participantThread;
    private Future threadReference;

    public Participant(String id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.exclusionDate = null;

        participantThread = new SimpleThread("participant");
        threadReference = null;
    }

    @Override
    public String getId() { return id; }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSurname() {
        return surname;
    }

    public Date getExclusionDate() {
        return exclusionDate;
    }

    public void setExclusionDate() {
        exclusionDate = now();
        System.out.println(formatDate(exclusionDate));
    }

    @Override
    public SimpleThread getThread () { return participantThread; }

    @Override
    public Future getThreadReference() { return threadReference; }

    @Override
    public void setThreadReference(Future threadReference) {
        this.threadReference = threadReference;
    }

    @Override
    public void printValues() { System.out.println(getId()+" "+getName()+" "+getSurname()+" "+formatDate(getExclusionDate())); }

    private Date now() { return new Date(); }

    private String formatDate(Date date) {
        if (date != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            return formatter.format(date);
        }
        return "";
    }
}
