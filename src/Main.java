import com.workintech.library_automation.enums.Category;
import com.workintech.library_automation.enums.Department;
import com.workintech.library_automation.enums.Rank;
import com.workintech.library_automation.models.LibraryRelated.*;
import com.workintech.library_automation.models.People.FacultyMember;
import com.workintech.library_automation.models.People.Person;
import com.workintech.library_automation.models.People.Student;


public class Main {
    public static void main(String[] args) {

        Library library = new Library("Aptullah Kuran");


        Person jkRow = new Person(1,"Joanne Kathleen", "Rowling");
        Person georgeRR = new Person(2,"George RR", "Martin");

        Person hawking = new FacultyMember(3, "Stephen", "Hawking", Department.PHYSICS, Rank.PROFESSOR);


        System.out.println("->Adding books to library.....");
        library.add(new Novel("Harry Potter 1", Category.FANTASY, jkRow));
        library.add(new Novel("Harry Potter 1", Category.FANTASY, jkRow));
        library.add(new Novel("Harry Potter 2", Category.FANTASY, jkRow));
        library.add(new Novel("GOT 1", Category.FANTASY, georgeRR));
        library.add(new Novel("GOT 1", Category.FANTASY, georgeRR));
        library.add(new Novel("GOT 2", Category.FANTASY, georgeRR));
        library.add(new Novel("GOT 3", Category.FANTASY, georgeRR));

        library.add(new Novel("A Story In SpaceTime", Category.ADVENTURE, hawking));

        library.add(new Magazine("NewYork Times", Category.NON_FICTION, 350));
        library.add(new Magazine("NewYork Times", Category.NON_FICTION, 350));
        library.add(new Magazine("NewYork Times", Category.NON_FICTION, 400));

        library.add(new Encyclopedia("Britannica", 'A', 'D'));
        System.out.println("->Books added. Now displaying library collection...");
        System.out.println(library);
        System.out.println("--------------------------------------------------");


        System.out.println("->Getting all books in the category of " + Category.FANTASY);
        var categoryFantasy = library.queryAll(Category.FANTASY);
        for (LibraryReadingMaterial item : categoryFantasy) {
            System.out.println(item);
        }
        System.out.println("--------------------------------------------------");

        System.out.println("->Getting all books of JK Rowling");
        var booksOfJK = library.queryAll(jkRow);
        for (LibraryReadingMaterial item : booksOfJK) {
            System.out.println(item);
        }
        System.out.println("--------------------------------------------------");


        System.out.println("->Deleting all Harry Potter 1 books");
        library.remove(new Novel("Harry Potter 1", Category.FANTASY, jkRow));
        System.out.println("->library collection after removing Harry Potter 1 books");
        System.out.println(library);
        System.out.println("--------------------------------------------------");

        System.out.println("->Getting all books named NewYork Times");
        var newYorkTimes = library.queryAll("NewYork Times");
        for(var item : newYorkTimes) {
            System.out.println(item);
        }
        System.out.println("--------------------------------------------------");

        System.out.println("->Getting book with barcode 5");
        var book5 = library.query(5);
        System.out.println(book5);
        System.out.println("--------------------------------------------------");


        System.out.println("->Ali Veli borrowing books...");
        Person aliVeli = new Student(35, "Ali", "Veli", Department.COMPUTER_SCIENCE);
        aliVeli.borrow(5, library);
        aliVeli.borrow(6, library);
        aliVeli.borrow(7, library);
        aliVeli.borrow(8, library);
        aliVeli.borrow(9, library);
        aliVeli.borrow(10, library);
        System.out.println("**books of student...");
        var booksOfAliVeli = aliVeli.getBooks();
        for(var book : booksOfAliVeli) {
            System.out.println(book);
        }
        System.out.println("--------------------------------------------------");

        System.out.println("->Ali Veli returning one book");
        aliVeli.handIn(booksOfAliVeli.get(0).getBarcode(), library);
        System.out.println("->Books of Ali Veli after returning one book");
        for(var book : booksOfAliVeli) {
            System.out.println(book);
        }
        System.out.println("--------------------------------------------------");

        Person mehmetKara = new Student(39, "Mehmet", "Kara", Department.PHYSICS);
        System.out.println("->Mehmet trying to borrow a book AliVeli has");
        mehmetKara.borrow(9, library);
        System.out.println("--------------------------------------------------");

    }
}