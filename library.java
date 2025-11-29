import java.util.Scanner;


public class library2 {

    // DECLARING OF ARRAY
    static String[] categories = {"Fiction", "Non-Fiction", "Science", "History"};
    static String[][] bookTitles = {
            // Fiction
            {
                    "The Lost Tale", "Whispers in the Wind", "Midnight Echoes", "Starlit Promises",
                    "The Hidden Key", "Shadows of Tomorrow", "A Journey Home", "Tales of the Bazaar",
                    "The Last Guardian", "Dreamcatcher"
            },
            // Non-Fiction
            {
                    "Age of AI", "A Brief History of Time", "Bio Ethics Today", "Global Warming: The Facts",
                    "The Space Race Chronicle", "Art Theory and Practice", "Economics 101", "The Human Brain",
                    "Anatomy of the Body", "Practical Philosophy"
            },
            // Science
            {
                    "Physics Fundamentals", "Chemistry in Action", "The Biology Lab", "Astronomy for Beginners",
                    "Introduction to Robotics", "Genetics and You", "Quantum Mechanics Simplified", "Environmental Science",
                    "Nanotechnology Today", "Computational Models"
            },
            // History
            {
                    "Medieval Ages", "World War Chronicles", "Ancient Civilizations", "Revolutions and Reforms",
                    "The Colonial Era", "Empire Builders", "Modern History Explained", "The Silk Road Saga",
                    "Historical Biography: Leaders", "Cultural Heritage"
            }
    };

    // Parallel arrays to hold borrower info and access info
    static String[][] bookBorrowers = new String[4][10];

    static double[] paymentInfo = new double[4];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.println("Welcome to the City Library System");
            System.out.println("What would you like to do? ~~Choose a number~~");
            System.out.println("1 |Borrow Book|");
            System.out.println("2 |Return Book|");
            System.out.println("3 |Check Available Books|");
            System.out.println("4 |Search Book|");
            System.out.println("5 |Exit|");
            System.out.print("Enter option: ");
            choice = sc.nextInt();
            System.out.println(" ");

            if (choice == 1) {
                System.out.println("Selected: Borrow Book");
                System.out.println(" ");
                borrowBook();

            }
            else if (choice == 2) {
                System.out.println("Selected: Return Book");
                System.out.println(" ");
                returnBook();

            }
            else if (choice == 3) {
                System.out.println("Selected: Check Available Books");
                System.out.println(" ");
                checkAvailableBooks();

            }
            else if (choice == 4) {
                System.out.println("Selected: Search Book");
                System.out.println(" ");
                searchBook();

            }
            else if (choice > 5) {
                System.out.println("Invalid number, please try again.");
            }

        } while (choice != 5);
        System.out.println("Thank you for using the Library Management System!");
        System.out.println("Closing System...");
        System.exit(0);
    }

    static void borrowBook() {
        Scanner sc = new Scanner(System.in);
        System.out.println("--- Borrow Book Section ---");

        // 1. Select category
        int cat = selectCategory();

        // 2. Show available books
        System.out.println("Available " + categories[cat] + " Books:");
        boolean hasAvailable = false;

        for (int i = 0; i < bookTitles[cat].length; i++) {
            if (bookBorrowers[cat][i] == null) {
                System.out.println((i + 1) + ". " + bookTitles[cat][i]);
                hasAvailable = true;
            }
        }

        if (!hasAvailable) {
            System.out.println("No available books in this category.");
            return;
        }

        // 3. Select book
        int bookNum = readInt("Enter the book number you want to borrow: ",
                1, bookTitles[cat].length);
        bookNum -= 1;
        System.out.println(" ");

        if (bookBorrowers[cat][bookNum] != null) {
            System.out.println("Sorry, that book is already borrowed.");
            return;
        }

        // 4. Choose access type
        System.out.println("Choose Access Type:");
        System.out.println("1. Overnight (₱50)");
        System.out.println("2. Multi-Day (₱100 × number of days)");
        System.out.println("3. Monthly (₱1000)");
        System.out.println(" ");

        int accessType = readInt("Enter access type: ", 1, 3);

        int days = 0;
        double totalCost = 0;

        if (accessType == 1) {
            totalCost = 50;
        }
        else if (accessType == 2) {
            days = readInt("Enter number of days: ", 1, 365);
            totalCost = 100 * days;
        }
        else if (accessType == 3) {
            totalCost = 1000;
        }


        // 5. Enter borrower name
        System.out.print("Enter your full name: ");
        String borrower = sc.nextLine();
        System.out.println(" ");

        // 6. Save borrowing record
        bookBorrowers[cat][bookNum] = borrower;

        // 7. Borrowing summary
        System.out.println("--- BORROWING SUMMARY ---");
        System.out.println("Category: " + categories[cat]);
        System.out.println("Book: " + bookTitles[cat][bookNum]);
        System.out.println("Borrower: " + borrower);

        String accessName = "";
        if (accessType == 1) accessName = "Overnight";
        if (accessType == 2) accessName = "Multi-Day (" + days + " day/s)";
        if (accessType == 3) accessName = "Monthly";

        System.out.println("Access Type: " + accessName);
        System.out.println("Total Cost: ₱" + totalCost);

        // 8. Payment section
        System.out.println("Proceeding to payment...");
        System.out.println(" ");
        payment(totalCost);
    }


    static void returnBook() {
        Scanner sc = new Scanner(System.in);
        System.out.println("--- Return Book Section ---");


        System.out.print("Enter borrower's full name: ");
        String nameToFind = sc.nextLine();

        boolean found = false;

        for (int i = 0; i < bookBorrowers.length; i++) {

            for (int j = 0; j < bookBorrowers[i].length; j++) {

                if (bookBorrowers[i][j] != null && bookBorrowers[i][j].equalsIgnoreCase(nameToFind)) {

                    System.out.println("Found book: '" + bookTitles[i][j] + "' (Category: " + categories[i] + ")");

                    bookBorrowers[i][j] = null;

                    System.out.println("Return Processed:");
                    System.out.println("Book successfully returned by " + nameToFind + ".");
                    System.out.println("Book slot is now available.");

                    found = true;

                    return;
                }
            }
        }

        if (!found) {
            System.out.println("No active borrowing record found for: " + nameToFind);
        }
        System.out.println();
    }

    static void checkAvailableBooks() {
        Scanner sc = new Scanner(System.in);

        int cat = selectCategory();

        System.out.println();
        System.out.println("Available " + categories[cat] + " Books:");

        boolean thereIsAvailableBook = false;

        for (int i = 0; i < bookTitles[cat].length; i++) {

            if (bookBorrowers[cat][i] == null) {
                thereIsAvailableBook = true;
                System.out.println((i + 1) + ". " + bookTitles[cat][i]);
            }
        }

        if (!thereIsAvailableBook) {
            System.out.println("No available copies in this category right now.");
        }
        //return to main menu or quit, serve as stopper
        System.out.println();
        System.out.println("\nWhat would you like to do next?");
        System.out.println("1. Return to Main Menu");
        System.out.println("2. Quit System");

        int option = readInt("Enter option: ", 1, 2);

        if (option == 1) {
            return;  // Goes back to main menu loop
        } else if (option == 2) {
            System.out.println("Thank you for using the Library Management System!");
            System.exit(0);
        }
    }

    static int selectCategory() {

        System.out.println();
        System.out.println("Select Book Category:");

        for (int i = 0; i < categories.length; i++) {
            System.out.println((i + 1) + ". " + categories[i]);
        }

        int cat = readInt("Enter category number: ", 1, categories.length);
        System.out.println(" ");

        return cat - 1;
    }

    static int readInt(String prompt, int min, int max) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print(prompt);

            int number = sc.nextInt();

            if (number >= min && number <= max) {
                return number;

            } else {
                System.out.println("Please type a number between " + min + " and " + max + ".");
            }
        }
    }

    public static boolean payment(double totalCost) {
        Scanner sc = new Scanner(System.in);

        System.out.println(" PAYMENT SECTION ");
        System.out.println("You need to pay: ₱" + totalCost);

        System.out.print("Enter your payment amount: ₱");
        System.out.println(" ");
        double amountPaid = sc.nextDouble();

        paymentInfo[0] = totalCost;
        paymentInfo[1] = amountPaid;

        if (amountPaid < totalCost) {
            System.out.println(" PAYMENT FAILED");
            System.out.println("You did not give enough money.");
            paymentInfo[3] = 0;
            return false;
        }

        double change = amountPaid - totalCost;
        paymentInfo[2] = change;
        paymentInfo[3] = 1;

        System.out.println(" PAYMENT SUCCESSFUL!");
        System.out.println("Change:"+ change);
        System.out.println("Borrowing is now confirmed.");

        return true;
    }

    public static void displayPaymentDetails() {
        System.out.println(" LAST PAYMENT SUMMARY ");

        if (paymentInfo[3] == 1) {
            System.out.println("Status: SUCCESS");
            System.out.println("Amount Due:" + paymentInfo[0]);
            System.out.println("Amount Paid:" + paymentInfo[1]);
            System.out.println("Change: " + paymentInfo[2]);
        } else {
            System.out.println("Status: FAILED");
            System.out.println("No successful payment recorded.");
        }
    }

    static void searchBook() {
        Scanner sc = new Scanner(System.in);
        System.out.println("--- Search Book Section ---");

        System.out.print("Enter book title or keyword to search: ");
        String keyword = sc.nextLine().toLowerCase();

        boolean found = false;

        System.out.println("Search Results:");
        for (int i = 0; i < bookTitles.length; i++) {
            for (int j = 0; j < bookTitles[i].length; j++) {

                if (bookTitles[i][j].toLowerCase().contains(keyword)) {
                    found = true;

                    String status = (bookBorrowers[i][j] == null)
                            ? "Available"
                            : "Borrowed by: " + bookBorrowers[i][j];

                    System.out.println("Title: " + bookTitles[i][j]);
                    System.out.println("Category: " + categories[i]);
                    System.out.println("Status: " + status);
                }
            }
        }

        if (!found) {
            System.out.println("No books found matching keyword: " + keyword);
        }

        System.out.println();
    }


}
