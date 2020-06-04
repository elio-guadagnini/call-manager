package call.customlists;

import call.person.Person;

import java.util.Iterator;
import java.util.List;

public interface CustomList {

    List<Person> list = null;

    void add(Person element);

    int size();

    Person getTuple(Person element);

    void removeTuple(Person person);

    boolean contains(Person element);

    void allParticipants();
}
