package com.workintech.library_automation.interfaces;

import com.workintech.library_automation.models.LibraryRelated.Library;
import com.workintech.library_automation.models.LibraryRelated.LibraryReadingMaterial;

public interface ILibraryMember {

    void handIn(long barcode, Library library);
    void borrow(long barcode, Library library);
}
