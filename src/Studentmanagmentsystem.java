import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
//create arrays to store details
public class Studentmanagmentsystem {
    public static final int MAX_STUDENTS = 100;
    private static Student[] students = new Student[MAX_STUDENTS];
    private static int studentCount = 0;
//Main class
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        while (true) {
            displayMenu();
            try {
                int choice = scan.nextInt();
                scan.nextLine(); // Consume newline;
                switch (choice) {
                    case 1:
                        checkAvailableSeats();
                        break;
                    case 2:
                        registerStudent(scan);
                        break;
                    case 3:
                        deleteStudent(scan);
                        break;
                    case 4:
                        findStudent(scan);
                        break;
                    case 5:
                        storeStudent(scan);
                        break;
                    case 6:
                        loadStudent(scan);
                        break;
                    case 7:
                        viewStudents();
                        break;
                    case 8:
                        Managestudentdetails(scan);
                        break;
                    case 0:
                        System.out.println("Exiting the program.");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scan.nextLine(); // Consume the invalid input to avoid an infinite loop
            }
        }
    }
//main display menu to work the code
    public static void displayMenu() {
        System.out.println("\n........ University Management System .........");
        System.out.println("1. Check available seats");
        System.out.println("2. Register student (with ID and Name)");
        System.out.println("3. Delete student (with ID)");
        System.out.println("4. Find student (with ID)");
        System.out.println("5. Store student details into a file");
        System.out.println("6. Load student details from a file");
        System.out.println("7. View the list of students (sorted by name)");
        System.out.println("8. Manage student details (Add student name / Update module marks / Generate system summary / Generate complete summary).");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

//if how many seats are available
    public static void checkAvailableSeats() {
        int availableSeats = MAX_STUDENTS - studentCount;
        System.out.println("Available seats: " + availableSeats);
    }
    //register students to booked seats
    public static void registerStudent(Scanner scan) {
        if (studentCount >= MAX_STUDENTS) {
            System.out.println("Sorry, No available seats");
            return;
        }
        System.out.print("Enter student ID: ");
        String id = scan.next();

        // Check if ID length is exactly 8 characters
        if (id.length() != 8) {
            System.out.println("Invalid ID length. ID must be exactly 8 characters long.");
            return;
        }

        System.out.print("Enter student name: ");
        String name = scan.next();

        students[studentCount++] = new Student(id, name);
        System.out.println("Student Registered successfully.");
    }
    //if you want delete added detail
    public static void deleteStudent(Scanner scan) {
        System.out.print("Enter student ID to delete: ");
        String id = scan.next();
        boolean found = false;
        for (int i = 0; i < studentCount; i++) {
            if (students[i].getId().equals(id)) {
                students[i] = students[studentCount - 1];
                students[--studentCount] = null;
                System.out.println("Student deleted successfully.");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Student not found.");
        }
    }
    //if you want find added details or booked details
    public static void findStudent(Scanner scan) {
        System.out.print("Enter student ID to find: ");
        String id = scan.next();
        boolean found = false;
        for (int i = 0; i < studentCount; i++) {
            if (students[i].getId().equals(id)) {
                System.out.println("Student found: " + students[i]);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Student not found.");
        }
    }
    //Store details in to the text file
    public static void storeStudent(Scanner scan) {
        try (FileWriter writer = new FileWriter("students.txt")) {
            for (int i = 0; i < studentCount; i++) {
                writer.write(students[i].getId() + "," + students[i].getName());
                for (Module module : students[i].getModules()) {
                    if (module != null) {
                        writer.write("," + module.getModuleName() + "," + module.getMarks());
                    }
                }
                writer.write("\n");
            }
            System.out.println("Student details stored successfully.");
        } catch (IOException e) {
            System.out.println("Error storing student details: " + e.getMessage());
        }
    }


    //Store details into the system
    public static void loadStudent(Scanner scan) {
        try (Scanner fileScanner = new Scanner(new File("students.txt"))) {
            studentCount = 0;
            while (fileScanner.hasNextLine() && studentCount < MAX_STUDENTS) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");
                Student student = new Student(parts[0], parts[1]);
                for (int i = 2; i < parts.length; i += 2) {
                    student.setModule((i - 2) / 2, new Module(parts[i], Integer.parseInt(parts[i + 1])));
                }
                students[studentCount++] = student;
            }
            System.out.println("Student details loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading student details: " + e.getMessage());
        }
    }
    //if we want student view details
    public static void viewStudents() {
        if (studentCount == 0) {
            System.out.println("No students registered yet.");
            return;
        }
        sortStudentsByName();
        System.out.println("List of students (sorted by name):");
        for (int i = 0; i < studentCount; i++) {
            System.out.println(students[i]);
        }
    }
    //Student details sort by name
    public static void sortStudentsByName() {
        for (int i = 0; i < studentCount - 1; i++) {
            for (int j = i + 1; j < studentCount; j++) {
                if (students[i].getName().compareTo(students[j].getName()) > 0) {
                    Student temp = students[i];
                    students[i] = students[j];
                    students[j] = temp;
                }
            }
        }
    }
    //Submenu manage students  details
    public static void Managestudentdetails(Scanner scan) {
        System.out.println("Submenu:");
        System.out.println("a. Add student name");
        System.out.println("b. Update module marks for a student");
        System.out.println("c. Generate system summary");
        System.out.println("d. Generate complete summary ");
        System.out.print("Enter your choice: ");
        char choice = scan.next().charAt(0);
        scan.nextLine(); // Consume newline
        switch (choice) {
            case 'a':
                addStudentName(scan);
                break;
            case 'b':
                updateModuleMarks(scan);
                break;
            case 'c':
                generatesummary();
                break;
            case 'd':
                generateCompleteReport();
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }
    //manage student details if we added name update this method for that
    public static void addStudentName(Scanner scan) {
        System.out.print("Enter student ID to add name: ");
        String id = scan.nextLine();
        for (int i = 0; i < studentCount; i++) {
            if (students[i].getId().equals(id)) {
                System.out.print("Enter student name: ");
                String name = scan.nextLine();
                students[i].setName(name);
                System.out.println("Student name updated successfully.");
                return;
            }
        }
        System.out.println("Student name not found.");

    }
    //update 3 of module marks
    public static void updateModuleMarks(Scanner scan) {
        System.out.print("Enter student ID to update module marks: ");
        String id = scan.nextLine();
        for (int i = 0; i < studentCount; i++) {
            if (students[i].getId().equals(id)) {
                for (int j = 0; j < students[i].getModules().length; j++) {
                    int marks;
                    while (true) {
                        System.out.print("Enter marks for Module " + (j + 1) + " (0-100): ");
                        marks = scan.nextInt();
                        if (marks >= 0 && marks <= 100) {
                            break;
                        } else {
                            System.out.println("Invalid marks. Please enter a value between 0 and 100.");
                        }
                    }
                    students[i].setModule(j, new Module("Module " + (j + 1), marks));
                }
                System.out.println("Module marks updated successfully.");
                return;
            }
        }
        System.out.println("Student ID not found.");


    }
    //generate summary of details
    public static void generatesummary() {
        System.out.println("Generate summary...: ");
        System.out.println("Total number of students: " + studentCount);

        int studentsScoringMoreThan40InAllModules = 0;
        for (int i = 0; i < studentCount; i++) {
            boolean allModulesAbove40 = true;
            for (Module module : students[i].getModules()) {
                if (module ==null || module.getMarks() <= 40) {
                    allModulesAbove40 = false;
                    break;
                }
            }
            if (allModulesAbove40) {
                studentsScoringMoreThan40InAllModules++;
            }
        }
        System.out.println("Total number of students scoring more than 40 in all modules: " + studentsScoringMoreThan40InAllModules);
    }
    public static void generateCompleteReport() {
        if (studentCount == 0) {
            System.out.println("No students registered yet.");
            return;
        }

        // Sort students by average marks (highest to lowest)
        sortStudentsByAverageMarks();

        System.out.println("\nComplete Report:");

        for (int i = 0; i < studentCount; i++) {
            Student student = students[i];
            System.out.println("Student ID: " + student.getId());
            System.out.println("Student Name: " + student.getName());
            Module[] modules = student.getModules();
            for (Module module : modules) {
                if (module != null) {
                    System.out.println(module);
                }
            }
            System.out.println("Total Marks: " + getTotalMarks(student));
            System.out.println("Average Marks: " + student.getAverageMarks());
            System.out.println("Grade: " + student.getGrade());
            System.out.println();
        }

    }
    public static void sortStudentsByAverageMarks() {
        for (int i = 0; i < studentCount - 1; i++) {
            for (int j = i + 1; j < studentCount; j++) {
                if (students[i].getAverageMarks() < students[j].getAverageMarks()) {
                    Student temp = students[i];
                    students[i] = students[j];
                    students[j] = temp;
                }


            }
        }
    }
    public static int getTotalMarks(Student student) {
        int totalMarks = 0;
        Module[] modules = student.getModules();
        for (Module module : modules) {
            if (module != null) {
                totalMarks += module.getMarks();
            }
        }
        return totalMarks;
    }







}
