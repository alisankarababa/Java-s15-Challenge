package com.workintech.library_automation.models.LibraryRelated;

import java.util.Objects;

public class LibraryReadingMaterial {

    private long barcode;
    private boolean isAvailable;
    private boolean isDamaged;

    private ReadingMaterial readingMaterial;

    public LibraryReadingMaterial(long barcode, ReadingMaterial readingMaterial) {
        this.barcode = barcode;
        this.readingMaterial = readingMaterial;
        this.isAvailable = true;
    }

    public long getBarcode() {
        return barcode;
    }

    protected void setBarcode(long barcode) {
        this.barcode = barcode;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public boolean isDamaged() {
        return isDamaged;
    }

    protected void setDamaged(boolean damaged) {
        isDamaged = damaged;
    }

    protected void setAvailable(boolean available) {
        isAvailable = available;
    }

    public ReadingMaterial getReadingMaterial() {
        return readingMaterial;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LibraryReadingMaterial that = (LibraryReadingMaterial) o;
        return barcode == that.barcode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(barcode);
    }

    @Override
    public String toString() {
        return  "barcode=" + barcode +
                ", isAvailable=" + isAvailable +
                ", " + readingMaterial;
    }
}
