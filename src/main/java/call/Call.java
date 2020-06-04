package call;

import call.customlists.ExcludedParticipants;
import call.customlists.Participants;
import call.person.CallAdministrator;
import call.person.Participant;
import call.person.Person;
import call.person.Presenter;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.*;

public class Call {

    private CallAdministrator callAdmin;
    private Presenter presenter;
    private Participants participants;
    private ExcludedParticipants excludedParticipants;

    private ExecutorService executor;

    public Call(String adminName, String adminSurname) {
        callAdmin = CallAdministrator.getInstance();
        callAdmin.initAdmin("1", adminName, adminSurname);

        presenter = Presenter.getInstance();

        participants = new Participants(callAdmin);
        excludedParticipants = new ExcludedParticipants();

        executor = Executors.newFixedThreadPool(30); // max 30 clients
        executor.submit(callAdmin.getThread());
    }

    public CallAdministrator getAdmin() { return callAdmin; }

    public Presenter getPresenter() { return presenter; }

    public Participants getParticipants() { return participants; }

    public ExcludedParticipants getExcludedParticipants() { return excludedParticipants; }

    public void addParticipant(Person element) {
        if (!participants.contains(element)) {
            participants.add(element);
            Future<?> future = executor.submit(element.getThread());
            element.setThreadReference(future);

            CallManagerApplication.print("Participant added.");
        } else { CallManagerApplication.print("Participant already present in call."); }
        // whether it is present in excluded participants it is removed from that list
        if(excludedParticipants.contains(element)) {
            Participant prs = (Participant) excludedParticipants.getTuple(element);
            excludedParticipants.removeTuple(prs);
        }
    }

    public void removeParticipant(Participant element) {
        if (participants.contains(element)) {
            Participant prs = (Participant) participants.getTuple(element);
            prs.setExclusionDate();
            participants.removeTuple(prs);
            excludedParticipants.add(prs);
            CallManagerApplication.print("Participant removed.");
        } else {
            CallManagerApplication.print("Participant not found in current participants. Impossible to remove.");
        }
    }

    public void allParticipants() {
        participants.allParticipants();
    }

    public void allExcludedParticipants() {
        excludedParticipants.allParticipants();
    }

    public void giveRandomPresenterPermission() {
        Random rand = new Random();
        int intRandom = rand.nextInt(participants.size());
        System.out.println(intRandom);
        Person prsntr = participants.getTuple(intRandom);
        System.out.println(prsntr.getName());
        givePresenterPermission(prsntr.getId(), prsntr.getName(), prsntr.getSurname());
    }
    // here the code may be optimized by moving away the controls in the below function since I got the random presenter from
    // the HashMap there is no need to check it again

    public void givePresenterPermission(String id, String name, String surname) {
        Participant temp = new Participant(id, name, surname);
        if (participants.contains(temp)) {
            // the presenter must be in call to be converted as presenter
            Person prs = (Person) participants.getTuple(temp);
            presenter.init(prs.getId(), prs.getName(), prs.getSurname());
            Future<?> future = executor.submit(presenter.getThread());
            presenter.setThreadReference(future);

            CallManagerApplication.print("participant "+id+" "+name+" "+surname+" has now access to presenting privileges");
        } else { CallManagerApplication.print("Participant not in call."); }
    }

    public void removePresenterPermission() {
        try {
            presenter.getThreadReference().cancel(true);
            presenter.init(null, null, null);
            CallManagerApplication.print("Permissions removed");
        } catch (NullPointerException e) { CallManagerApplication.print("No presenter was selected"); }
    }

    public void startPresentation() {
        if (!presenter.getPresenting()) {
            presenter.setPresenting(true);
            presenter.startPresenting();
        } else { CallManagerApplication.print("Already presenting. Impossible to start a new presentation session."); }
    }

    public void stopPresentation() {
        if (presenter.getPresenting()) {
            presenter.setPresenting(false);
            presenter.stopPresenting();
        } else { CallManagerApplication.print("No presentation is active. Impossible to stop."); }
    }

    public void performCallOperations() {
        boolean exitFlag = true;
        Scanner scanner = new Scanner(System.in);
        String operation, id, name, surname;
        do {
            CallManagerApplication.print("What do you want to do? Possible actions are addParticipant, removeParticipant, \n" +
                    "addPresenter, addRandomPresenter, removePresenter, startPresenting, stopPresenting, seeCallStatus or exit");
            operation = scanner.next();

            switch (operation) {
                case "addParticipant":
                    CallManagerApplication.print("Participant id:");
                    id = scanner.next();
                    CallManagerApplication.print("Participant name:");
                    name = scanner.next();
                    CallManagerApplication.print("Participant surname:");
                    surname = scanner.next();
                    addParticipant(new Participant(id, name, surname));
                    break;
                case "removeParticipant":
                    CallManagerApplication.print("Participant id:");
                    id = scanner.next();
                    CallManagerApplication.print("Participant name:");
                    name = scanner.next();
                    CallManagerApplication.print("Participant surname:");
                    surname = scanner.next();
                    removeParticipant(new Participant(id, name, surname));
                    break;
                case "seeCallStatus":
                    allParticipants();
                    allExcludedParticipants();
                    break;
                case "addPresenter":
                    CallManagerApplication.print("Please insert a name of a active participant");
                    CallManagerApplication.print("Participant id:");
                    id = scanner.next();
                    CallManagerApplication.print("Participant name:");
                    name = scanner.next();
                    CallManagerApplication.print("Participant surname:");
                    surname = scanner.next();
                    givePresenterPermission(id, name, surname);
                    break;
                case "addRandomPresenter":
                    giveRandomPresenterPermission();
                    break;
                case "removePresenter":
                    removePresenterPermission();
                    break;
                case "startPresenting":
                    startPresentation();
                    break;
                case "stopPresenting":
                    stopPresentation();
                    break;
                case "exit":
                    exitFlag = false;
                    break;
                default:
                    CallManagerApplication.print("None of operations was selected.");
            }
        } while(exitFlag);
    }

    public void closeCall() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException ex) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
