package studentmgmt;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner in = new Scanner(System.in);
    private static final StudentDatabase db = new StudentDatabase();

    public static void main(String[] args) {
        System.out.println("=== Student Management (Console) ===");
        loop:
        while (true) {
            printMenu();
            String choice = prompt("Choose an option");
            switch (choice) {
                case "1": addStudent(); break;
                case "2": viewStudent(); break;
                case "3": editStudentCourses(); break;
                case "4": deleteStudent(); break;
                case "5": listAll(); break;
                case "6": renameStudent(); break;
                case "0": System.out.println("Bye."); break loop;
                default: System.out.println("Invalid choice."); 
            }
        }
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("1) Add student");
        System.out.println("2) View student (by ID)");
        System.out.println("3) Edit a student's classes/grades");
        System.out.println("4) Delete student");
        System.out.println("5) List all students");
        System.out.println("6) Rename student");
        System.out.println("0) Exit");
    }

    private static String prompt(String label) {
        System.out.print(label + ": ");
        return in.nextLine().trim();
    }

    private static void addStudent() {
        try {
            String id = prompt("New student ID");
            String name = prompt("Full name");
            db.addStudent(id, name);
            System.out.println("Added " + id + " — " + name);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void viewStudent() {
        String id = prompt("Student ID");
        Student s = db.getById(id);
        if (s == null) {
            System.out.println("Not found.");
            return;
        }
        System.out.println(s);
    }

    private static void listAll() {
        if (db.allStudents().isEmpty()) {
            System.out.println("No students yet.");
            return;
        }
        for (Student s : db.allStudents()) {
            System.out.println(s);
            System.out.println();
        }
    }

    private static void deleteStudent() {
        String id = prompt("Student ID to delete");
        boolean ok = db.deleteStudent(id);
        if (ok) System.out.println("Deleted " + id);
        else System.out.println("Not found.");
    }

    private static void renameStudent() {
        String id = prompt("Student ID to rename");
        Student s = db.getById(id);
        if (s == null) {
            System.out.println("Not found.");
            return;
        }
        String newName = prompt("New full name");
        try {
            s.setName(newName);
            System.out.println("Renamed to: " + s.getName());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void editStudentCourses() {
        String id = prompt("Student ID to edit");
        Student s = db.getById(id);
        if (s == null) {
            System.out.println("Not found.");
            return;
        }
        while (true) {
            System.out.println();
            System.out.println("Editing " + s.getId() + " — " + s.getName());
            Double gpa = s.computeGPA();
            System.out.println("Current GPA: " + (gpa == null ? "N/A" : String.format("%.2f", gpa)));
            System.out.println("Courses:");
            if (s.getCourses().isEmpty()) {
                System.out.println("  (none)");
            } else {
                for (var e : s.getCourses().entrySet()) {
                    System.out.println("  " + e.getKey() + " : " + e.getValue());
                }
            }
            System.out.println();
            System.out.println(" a) Add/Update course");
            System.out.println(" r) Remove course");
            System.out.println(" b) Back to main menu");
            String c = prompt("Choose");
            switch (c.toLowerCase()) {
                case "a":
                    String course = prompt("Course code (e.g., MATH308)");
                    String grade  = prompt("Grade (A, A-, B+, ... or P/NP/I/W)");
                    try {
                        s.addOrUpdateCourse(course, grade);
                        System.out.println("Saved.");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case "r":
                    String rc = prompt("Course code to remove");
                    if (s.removeCourse(rc)) System.out.println("Removed.");
                    else System.out.println("Course not found.");
                    break;
                case "b":
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}