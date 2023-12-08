package com.workintech.library_automation.models.People;

import com.workintech.library_automation.interfaces.IDewey;
import com.workintech.library_automation.interfaces.ILibraryMember;
import com.workintech.library_automation.models.LibraryRelated.Library;
import com.workintech.library_automation.models.LibraryRelated.LibraryReadingMaterial;
import com.workintech.library_automation.models.LibraryRelated.ReadingMaterial;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Person implements IDewey, ILibraryMember {
    private long id;
    private String firstName;
    private String lastName;
    private double assets;

    private List<LibraryReadingMaterial> books;

    public Person(long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.assets = 1000;
        this.books = new LinkedList<>();
    }

    public List<LibraryReadingMaterial> getBooks() {
        return books;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String generateDeweyRepresentation() {
        return String.valueOf(id);
    }

    @Override
    public void handIn(long barcode , Library library) {

        LibraryReadingMaterial foundItem = null;
        for(LibraryReadingMaterial item : books) {

            if(item.getBarcode() == barcode) {
                foundItem = item;
                break;
            }
        }
        if(foundItem != null) {
            books.remove(foundItem);
            assets += library.receive(foundItem);
        }

    }

    @Override
    public void borrow(long barcode, Library library) {

        double rentingFee = library.getFeeRenting();
        if(rentingFee > assets) {
            System.out.println(this + "does not have enough money to rent a book");
            return;
        }
        var received = library.lend(barcode, this, rentingFee);
        if(received != null) {
            assets-=rentingFee;
            books.add(received);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return  "id=" + id +
                ", firstName=" + firstName +
                ", lastName=" + lastName +
                ", books=" + books;
    }

}
