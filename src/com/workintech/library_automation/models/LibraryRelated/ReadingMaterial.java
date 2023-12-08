package com.workintech.library_automation.models.LibraryRelated;

import com.workintech.library_automation.enums.Category;
import com.workintech.library_automation.enums.DeweyField;
import com.workintech.library_automation.interfaces.IDewey;

import java.util.Objects;

public abstract class ReadingMaterial implements IDewey {

    private String title;
    private Category category;

    public ReadingMaterial(String title, Category category) {
        this.title = title;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String generateDeweyRepresentation() {
        String categoryPair = IDewey.generateFieldValuePair(DeweyField.CATEGORY, this.category.name());
        String titlePair = IDewey.generateFieldValuePair(DeweyField.TITLE, this.title);

        return IDewey.combineFieldValuePairs(categoryPair, titlePair);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReadingMaterial that = (ReadingMaterial) o;
        return Objects.equals(generateDeweyRepresentation(), that.generateDeweyRepresentation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(generateDeweyRepresentation());
    }

    @Override
    public String toString() {
        return "title=" + title +
               ", category=" + category;
    }
}
