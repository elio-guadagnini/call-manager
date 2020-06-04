package call.customlists;

import call.person.CallAdministrator;
import call.person.Person;

import java.util.*;

public class Participants implements CustomList{

    private final HashMap<String, Person> participants;

    public Participants(CallAdministrator callAdmin) {
        participants = new HashMap<String, Person>();
        participants.put(callAdmin.getId(), callAdmin);
    }

    @Override
    public void add(Person element) {
        participants.put(element.getId(), element);
    }

    @Override
    public int size() {
        return participants.size();
    }

    public Person getTuple(Person element) { return participants.get(element.getId()); }

    public Person getTuple(int index) {
        Person element = null;
        for (Map.Entry<String, Person> entry : participants.entrySet()) {
            element = entry.getValue();
            index -= 1;
            if (index == 0)
                break;
        }
        return element;
    }

    @Override
    public boolean contains(Person element) { return participants.containsKey(element.getId()); }

    public void removeTuple(Person element) { participants.remove(element.getId()); }

    @Override
    public void allParticipants() {
        System.out.println("Participants:");
        for (Map.Entry<String, Person> entry : participants.entrySet()) {
            Person element = entry.getValue();
            element.printValues();
        }
    }
}
