import java.sql.*;
import java.util.Scanner;

public class Main{
    private static final Scanner scanner = new Scanner(System.in);
    static Connection con;
    static {
        try {
            con = DB.getConnection();
        } catch (Exception e) {
            System.out.println("Failed to connect: " + e.getMessage());
        }
    }

    public static void addStudent() throws Exception {
        if (con == null) {
            System.out.println("Connection failed");
            return;
        }
        System.out.println("Enter name :");
        String name = scanner.nextLine();

        System.out.println("Enter age :");
        int age;
        try {
            age = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid age");
            return;
        }
        System.out.println("Enter course :");
        String course = scanner.nextLine();
        String query = "INSERT INTO student(name, age, course) VALUES (?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, course);
            int result = ps.executeUpdate();
            if (result > 0) {
                System.out.println("Student added successfully");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void updateStudent() throws Exception {
        if (con == null) {
            System.out.println("Connection failed");
            return;
        }
        System.out.print("Enter Student ID: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID");
            return;
        }
        System.out.print("Enter New Course: ");
        String course = scanner.nextLine();
        String query = "UPDATE student SET course=? WHERE id=?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, course);
            ps.setInt(2, id);
            int result = ps.executeUpdate();
            if (result > 0) {
                System.out.println("Student updated successfully");
            } else {
                System.out.println("No student found with ID " + id);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void viewStudent() throws Exception {
        if (con == null) {
            System.out.println("Connection failed");
            return;
        }
        System.out.println("Enter Student id:");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID");
            return;
        }
        String query = "SELECT * FROM student WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Student Details:");
                    System.out.println("ID: " + rs.getInt("id"));
                    System.out.println("Name: " + rs.getString("name"));
                    System.out.println("Age: " + rs.getInt("age"));
                    System.out.println("Course: " + rs.getString("course"));
                } else {
                    System.out.println("Student not found!");
                }
            }
        }
    }

 public static void listAllStudents() {
        if (con == null) {
            System.out.println("Connection failed");
            return;
        }
        String query = "SELECT * FROM student";
        try (PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            System.out.println("-------- All Students --------");
            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.println("ID: " + rs.getInt("id")
                        + " | Name: " + rs.getString("name")
                        + " | Age: " + rs.getInt("age")
                        + " | Course: " + rs.getString("course"));
            }
            if (!found) {
                System.out.println("No students found.");
            }
            System.out.println("------------------------------");
        } catch (SQLException e) {
            System.out.println("Error listing students: " + e.getMessage());
        }
    }
    public static void deleteStudent() throws Exception {
        if (con == null) {
            System.out.println("Connection failed");
            return;
        }
        System.out.println("Enter id :");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID");
            return;
        }
        String query = "DELETE FROM student WHERE id=?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, id);
            int result = ps.executeUpdate();
            if (result > 0) {
                System.out.println("Student Deleted Successfully");
            } else {
                System.out.println("No student found with ID " + id);
            }
        }
    }
    public static void main(String[] args) throws Exception {
        while (true) {
            System.out.println("1. Add student");
            System.out.println("2. Update student");
            System.out.println("3. View student");
            System.out.println("4. listAllStudents");
            System.out.println("5. Delete student");
            System.out.println("6. Exit");
            System.out.print("Enter Choice: ");
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid Choice");
                continue;
            }
            switch (choice) {
                case 1 -> addStudent();
                case 2 -> updateStudent();
                case 3 -> viewStudent();
                case 4 -> listAllStudents();
                case 5 -> deleteStudent();
                case 6 -> {
                    System.out.println("Goodbye!");
                    scanner.close();
                    if (con != null) {
                        con.close();
                    }
                    return;
                }
                default -> System.out.println("Invalid Choice");
            }
        }
    }
}

