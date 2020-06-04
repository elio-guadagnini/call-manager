package call.customlists;

import call.person.Person;

import java.util.*;

public class ExcludedParticipants implements CustomList{

    private final HashMap<String, Person> excludedParticipants;

    public ExcludedParticipants() {
        excludedParticipants = new HashMap<String, Person>();
    }

    @Override
    public void add(Person element) {
        excludedParticipants.put(element.getId(), element);
    }

    @Override
    public int size() {
        return excludedParticipants.size();
    }

    public Person getTuple(Person element) { return excludedParticipants.get(element.getId()); }

    @Override
    public boolean contains(Person element) { return excludedParticipants.containsKey(element.getId()); }

    public void removeTuple(Person element) { excludedParticipants.remove(element.getId()); }

    @Override
    public void allParticipants() {
        System.out.println("Excluded participants:");
        for (Map.Entry<String, Person> entry : excludedParticipants.entrySet()) {
            Person element = entry.getValue();
            element.printValues();
        }
    }
}
