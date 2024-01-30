import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;

public class CheckoutSystemTest {

    private CheckoutSystem checkoutSystem;

    @Before
    public void setUp() {
        checkoutSystem = new CheckoutSystem();

        // Populate the catalog with some sample books
        checkoutSystem.catalog.add(new Book("Book1", "Author1", 5));
        checkoutSystem.catalog.add(new Book("Book2", "Author2", 8));
        checkoutSystem.catalog.add(new Book("Book3", "Author3", 10));
    }

    @Test
    public void testBookSelectionAndCheckout() {
        // Simulate user input for checkout
        String input = "2\nBook1\n2\nBook2\n3\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        checkoutSystem.checkoutBooks();

        // Verify that the checkoutBooks list is updated correctly
        assertEquals(2, checkoutSystem.checkoutBooks.size());
        assertEquals("Book1", checkoutSystem.checkoutBooks.get(0).title);
        assertEquals(2, checkoutSystem.checkoutBooks.get(0).quantity);
        assertEquals("Book2", checkoutSystem.checkoutBooks.get(1).title);
        assertEquals(3, checkoutSystem.checkoutBooks.get(1).quantity);
    }

    @Test
    public void testQuantityValidation() {
        // Simulate user input with invalid quantity
        String input = "1\nBook1\n-2\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Ensure that the system prompts the user to re-enter the quantity
        assertEquals(-1, checkoutSystem.checkoutBooks());
    }

    @Test
    public void testDueDateCalculation() {
        // Set up checkout books
        checkoutSystem.checkoutBooks.add(new Book("Book1", "Author1", 2));
        checkoutSystem.checkoutBooks.add(new Book("Book2", "Author2", 3));

        checkoutSystem.calculateDueDates();

        // Add assertions to verify due date calculations (since this is date-based, it might need adjustments)
    }

    @Test
    public void testLateFeeCalculation() {
        // Set up checkout books with overdue book
        Date dueDate = new Date(System.currentTimeMillis() - (14 * 24 * 60 * 60 * 1000)); // 14 days ago
        Book overdueBook = new Book("OverdueBook", "OverdueAuthor", 1);
        overdueBook.dueDate = dueDate;
        checkoutSystem.checkoutBooks.add(overdueBook);

        checkoutSystem.calculateLateFees();

        // Add assertions to verify late fee calculations
    }

    @Test
    public void testBookAvailability() {
        // Set up checkout books with an unavailable book
        checkoutSystem.catalog.get(0).quantity = 0; // Make Book1 unavailable
        checkoutSystem.checkoutBooks.add(new Book("Book1", "Author1", 1));

        // Ensure that an error message is displayed
        String expectedOutput = "Error: Book not available or quantity not sufficient. Please reselect.";
        assertEquals(expectedOutput, getSystemOutput(checkoutSystem::checkoutBooks));
    }

    @Test
    public void testMaximumBooksPerCheckout() {
        // Simulate user input with more than 10 books
        String input = "11\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Ensure that an error message is displayed
        String expectedOutput = "Error: Maximum books per checkout is 10.";
        assertEquals(expectedOutput, getSystemOutput(checkoutSystem::checkoutBooks));
    }

    @Test
    public void testUserConfirmation() {
        // Set up checkout books
        checkoutSystem.checkoutBooks.add(new Book("Book1", "Author1", 2));
        checkoutSystem.checkoutBooks.add(new Book("Book2", "Author2", 3));

        // Simulate user input for confirmation
        String input = "yes\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Ensure that the confirmation message is displayed
        String expectedOutput = "Checkout confirmed. Thank you!";
        assertEquals(expectedOutput, getSystemOutput(checkoutSystem::userConfirmation));
    }

    @Test
    public void testReturnProcess() {
        // Set up checkout books
        checkoutSystem.checkoutBooks.add(new Book("Book1", "Author1", 2));
        checkoutSystem.checkoutBooks.add(new Book("Book2", "Author2", 3));

        // Implement test for book return process
        // ...

        // Assertions for return process
    }

    // Helper method to capture system output for testing
    private String getSystemOutput(Runnable action) {
        // Redirect System.out to capture the output
        InputStream originalIn = System.in;
        StringPrintStream printStream = new StringPrintStream(System.out);
        System.setOut(printStream);

        // Run the action
        action.run();

        // Reset System.in and capture the output
        System.setIn(originalIn);
        System.setOut(System.out);
        return printStream.toString();
    }
}

class StringPrintStream extends PrintStream {
    private ByteArrayOutputStream bos;

    public StringPrintStream(OutputStream out) {
        super(out);
        bos = new ByteArrayOutputStream();
    }

    @Override
    public void write(int b) {
        super.write(b);
        bos.write(b);
    }

    @Override
    public void write(byte[] b, int off, int len) {
        super.write(b, off, len);
        bos.write(b, off, len);
    }

    @Override
    public String toString() {
        return bos.toString();
    }
}
