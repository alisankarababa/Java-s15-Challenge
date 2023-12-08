package com.workintech.library_automation.models.LibraryRelated;

import com.workintech.library_automation.enums.Category;
import com.workintech.library_automation.enums.DeweyField;
import com.workintech.library_automation.interfaces.IDewey;

public class Magazine extends ReadingMaterial {


    private int issue;
    public Magazine(String title, Category category, int issue) {
        super(title, category);
        this.issue = issue;
    }

    public int getIssue() {
        return issue;
    }

    public void setIssue(int issue) {
        this.issue = issue;
    }

    @Override
    public String generateDeweyRepresentation() {

        String superStrDewey = super.generateDeweyRepresentation();
        String issuePair = IDewey.generateFieldValuePair(DeweyField.ISSUE, String.valueOf(this.issue));
        String thisStrDewey = IDewey.combineFieldValuePairs(issuePair);

        return IDewey.combineDeweyStrings(superStrDewey, thisStrDewey);
    }


    @Override
    public String toString() {
        return super.toString() + ", issue=" + issue;
    }
}
