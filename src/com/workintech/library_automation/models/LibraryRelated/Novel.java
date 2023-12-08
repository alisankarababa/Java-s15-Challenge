package com.workintech.library_automation.models.LibraryRelated;

import com.workintech.library_automation.enums.Category;
import com.workintech.library_automation.enums.DeweyField;
import com.workintech.library_automation.interfaces.IDewey;
import com.workintech.library_automation.models.People.Person;

public class Novel extends ReadingMaterial {

    private Person author;

    public Novel(String title, Category category, Person author) {
        super(title, category);
        this.author = author;
    }

    public Person getAuthor() {
        return author;
    }

    public void setAuthor(Person author) {
        this.author = author;
    }

    @Override
    public String generateDeweyRepresentation() {

        String superStrDewey = super.generateDeweyRepresentation();
        String authorPair = IDewey.generateFieldValuePair(DeweyField.AUTHOR, author.generateDeweyRepresentation());
        String thisStrDewey = IDewey.combineFieldValuePairs(authorPair);

        return IDewey.combineDeweyStrings(superStrDewey, thisStrDewey);
    }

    @Override
    public String toString() {
        return super.toString() + ", author=" + author;
    }

}
