package com.workintech.library_automation.models.LibraryRelated;

import com.workintech.library_automation.enums.Category;
import com.workintech.library_automation.enums.DeweyField;
import com.workintech.library_automation.interfaces.IDewey;

public class Encyclopedia extends ReadingMaterial {

    private Character startingLetter;
    private Character endingLetter;

    public Encyclopedia(String title, Character startingLetter, Character endingLetter) {
        super(title, Category.ENCYCLOPEDIA);
        this.startingLetter = startingLetter;
        this.endingLetter = endingLetter;
    }

    public Character getStartingLetter() {
        return startingLetter;
    }

    public void setStartingLetter(Character startingLetter) {
        this.startingLetter = startingLetter;
    }

    public Character getEndingLetter() {
        return endingLetter;
    }

    public void setEndingLetter(Character endingLetter) {
        this.endingLetter = endingLetter;
    }

    public String generateDeweyRepresentation() {

        String superStrDewey = super.generateDeweyRepresentation();
        String letterRangePair = IDewey.generateFieldValuePair(DeweyField.LETTER_RANGE, startingLetter.toString().toUpperCase() + endingLetter.toString().toUpperCase());
        String thisStrDewey = IDewey.combineFieldValuePairs(letterRangePair);

        return IDewey.combineDeweyStrings(superStrDewey, thisStrDewey);
    }

    @Override
    public String toString() {
        return super.toString() +  ", " + startingLetter + "-" + endingLetter;
    }
}
