package continuousIntegration;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

class Book {
    String title;
    String author;
    int quantity;
    boolean available;

    public Book(String title, String author, int quantity) {
        this.title = title;
        this.author = author;
        this.quantity = quantity;
        this.available = true;
    }
}

class CheckoutSystem {
    ArrayList<Book> catalog = new ArrayList<>();
    ArrayList<Book> checkoutBooks = new ArrayList<>();

    public void displayCatalog() {
        System.out.println("Catalog:");
        for (Book book : catalog) {
            System.out.println("Title: " + book.title + ", Author: " + book.author + ", Quantity: " + book.quantity);
        }
    }

    public void checkoutBooks() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of books to checkout (max 10): ");
        int numBooks = scanner.nextInt();

        if (numBooks > 10) {
            System.out.println("Error: Maximum books per checkout is 10.");
            return;
        }

        for (int i = 0; i < numBooks; i++) {
            System.out.println("Enter book title: ");
            String title = scanner.next();
            System.out.println("Enter quantity: ");
            int quantity = scanner.nextInt();

            // Find the book in the catalog
            Book selectedBook = null;
            for (Book book : catalog) {
                if (book.title.equalsIgnoreCase(title) && book.quantity >= quantity) {
                    selectedBook = book;
                    break;
                }
            }

            if (selectedBook == null) {
                System.out.println("Error: Book not available or quantity not sufficient. Please reselect.");
                return;
            }

            selectedBook.quantity -= quantity;
            checkoutBooks.add(new Book(selectedBook.title, selectedBook.author, quantity));
        }

        System.out.println("Selected books for checkout:");
        for (Book book : checkoutBooks) {
            System.out.println("Title: " + book.title + ", Quantity: " + book.quantity);
        }

        // Implement due date calculation, user confirmation, and other features as needed.
    }

    // Other methods for due date calculation, late fee calculation, return process, etc.
}

public class LibraryManagementSystem {
    public static void main(String[] args) {
        CheckoutSystem checkoutSystem = new CheckoutSystem();

        // Populate the catalog with some sample books
        checkoutSystem.catalog.add(new Book("Book1", "Author1", 5));
        checkoutSystem.catalog.add(new Book("Book2", "Author2", 8));
        checkoutSystem.catalog.add(new Book("Book3", "Author3", 10));

        checkoutSystem.displayCatalog();
        checkoutSystem.checkoutBooks();

        // Add more functionality based on the requirements.
    }
}
