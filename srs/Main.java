import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String dbURL = "jdbc:mysql://localhost:3306/java34";
        String username = "root";
        String password = "Password123!";
        Scanner scanner = new Scanner(System.in);
        LocalDate today = LocalDate.now();
        LocalDate nextWeek = today.plus(7, ChronoUnit.DAYS);
        Statement stmt = null;
        String insertedUsername;
        char again = 'y';

        try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {
            System.out.println("Welcome");

            while (again == 'y') {
                System.out.println("You want to login or register?");
                System.out.println("l - login");
                System.out.println("r - register");

                char action = scanner.nextLine().charAt(0);

                if (action == 'r') {
                    System.out.println("Enter username");
                    String newUsername = scanner.nextLine();

                    System.out.println("Enter password");
                    String newPassword = scanner.nextLine();

                    System.out.println("Enter full name");
                    String newFullName = scanner.nextLine();

                    System.out.println("Enter date of birth");
                    String newDateOFBirth = scanner.nextLine();

                    insertData(conn, newUsername, newPassword, newFullName, newDateOFBirth);
                    //System.out.println("You are registered");
                    printChoicesList();

                } else if (action == 'l') {
                    System.out.println("Enter username and password");
                    insertedUsername = scanner.nextLine();
                    String insertedPassword = scanner.nextLine();
                    loginData(conn, insertedUsername, insertedPassword);
                    printChoicesList();
                } else {
                    System.out.println("Invalid input");
                }


               // printChoicesList();
                //Scanner scanner = new Scanner(System.in);
                boolean quit = false;

                int choice = 0;
                while (!quit) {      // "!" perjungia i oposite value
                    System.out.println("Enter your choise");
                    try {
                        choice = scanner.nextInt();
                        scanner.nextLine();

                        switch (choice) {
                            case 0:
                                //print all options
                                printChoicesList();         //   need to create method -> below
                                break;
                            case 1:
                                // DoctorsList - to choose appointment time;
                                // doctorsList.printDoctorsList();
                                printDoctorsList(conn);
                                break;
                            case 2:
                                //Select appointment date and time;
                                chooseAppointmentTime();
                                break;
                            case 3:
                                //MyAppointments;
                                //myAppointments.printMyAppointments();
                                printMyAppointments();
                                break;
                            case 4:
                                //Cancel the appointment();
                                //cancel.cancelMyAppointment();
                                cancelMyAppointment();
                                break;
                            case 5:
                                quit = true;
                                break;
                            default:
                                System.out.println("Input not valid (0-4)");
                                break;
                        }
                    } catch (InputMismatchException e) {
                        System.err.println("Wrong input!");
                        scanner.nextLine();
                    }

                }
                System.out.println("DO you want to login as another user? y/n ");
                again = scanner.nextLine().charAt(0);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void loginData(Connection conn, String insertedUsername, String insertedPassword) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = '" + insertedUsername + "' and password = '" + insertedPassword + "'";
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        if (resultSet.next()) {
            System.out.println("You have logged in successfully");
            choicesAfterLogin();
        } else {
            System.out.println("Wrong username or password");
        }


    }
    /*//read data metodas
    public static void readData(Connection conn) throws SQLException {

        String sql = "SELECT * FROM users";
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        int count = 0;

        while (resultSet.next()) {

            String username = resultSet.getString(2);
            String password = resultSet.getString(3);
            String fullName = resultSet.getString("fullname");
            String email = resultSet.getString("email");

            String output = "User #%d: %s - %s - %s - %s ";
            System.out.println(String.format(output, ++count, username, password, fullName, email));


        }

    }*/

    //insert patient
    public static void insertData(Connection conn, String username, String password, String fullname, String dateofbirth) throws SQLException {

        String sql = "INSERT INTO users (username, password, fullname, dateofbirth) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, username);
        statement.setString(2, password);
        statement.setString(3, fullname);
        statement.setString(4, dateofbirth);

        int rowInserted = statement.executeUpdate();

        if (rowInserted > 0) {
            System.out.println("Registration of a user was successful");
            choicesAfterLogin();
        } else {
            System.out.println("Something went wrong");
        }
    }

    //delete appointment
    public static void deleteData(Connection conn, String username) throws SQLException {
        String sql = "DELETE FROM users WHERE username = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, username);

        if (statement.executeUpdate() > 0) {
            System.out.println("User deleted successfully");
        } else {
            System.out.println("Something went wrong");
        }
    }

    private static void printChoicesList() {
        System.out.println("\nPress");
        System.out.println("\t 0 - To print choice options");
        System.out.println("\t 1 - Doctors list");
        System.out.println("\t 2 - Select appointment date and time");
        System.out.println("\t 3 - Your appointments list");
        System.out.println("\t 4 - Cancel the appointment");
        System.out.println("\t 5 - To quit the application");
    }


    private static void chooseAppointmentTime() {
        System.out.println("Test for choosing appointment");

    }

    private static void printMyAppointments() {      //search for appointment and print
        System.out.println("Test - Just a test for Printing appointments list");

    }

    private static void cancelMyAppointment() {      //delete the appointment
        System.out.println("Test - for canceling appointment");

    }

    public static void choicesAfterLogin() {
        Scanner scanner = new Scanner(System.in);
        boolean quit = false;
        int choice = 0;


    }

    public static void printDoctorsList(Connection conn) throws SQLException {
        String sql = "SELECT * FROM doctors";
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        System.out.println("List of Doctors:");

        while (resultSet.next()) {

            int doctorID = resultSet.getInt(1);
            String doctorsName = resultSet.getString(2);

            System.out.printf("No. %d:  %s \n",
                    resultSet.getInt(1), resultSet.getString(2));
        }

    }

    public static void printTimes(Connection conn) throws SQLException {
        Statement statement = conn.createStatement();
        String sql = "SELECT * FROM appointments";

        ResultSet resultSet = statement.executeQuery(sql);
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int columnsNumber = rsmd.getColumnCount();


        for (int i = 3; i <= columnsNumber - 1; i++) {
            String columnName = rsmd.getColumnName(i);
            System.out.print(columnName + " ");
        }

    }

    public static int getUserID(Connection conn, String insertedUsername) throws SQLException {
        String sql = "SELECT userID FROM users WHERE username = '" + insertedUsername + "'";
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        if (resultSet.next()) {
            return resultSet.getInt(1);
        } else {
            return 0;
        }

    }

    public static void appointing(Connection conn, int chosenDoctor, LocalDate selectedDate, String insertedUsername, int column) throws SQLException {

        if (column == 4) {
            String sql = "INSERT INTO appointments (doctor, date, 9_AM) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, chosenDoctor);
            statement.setString(2, String.valueOf(selectedDate));
            statement.setInt(3, getUserID(conn, insertedUsername));
            int rowInserted = statement.executeUpdate();

            if (rowInserted > 0) {
                System.out.println("A new appointment was inserted succesfully");
            } else {
                System.out.println("Smthng went wrong");
            }
        } else if (column == 5) {
            String sql = "INSERT INTO appointments (doctor, date, 10_AM) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, chosenDoctor);
            statement.setString(2, String.valueOf(selectedDate));
            statement.setInt(3, getUserID(conn, insertedUsername));
            int rowInserted = statement.executeUpdate();

            if (rowInserted > 0) {
                System.out.println("A new appointment was inserted succesfully");
            } else {
                System.out.println("Smthng went wrong");
            }
        } else if (column == 6) {
            String sql = "INSERT INTO appointments (doctor, date, 2_PM) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, chosenDoctor);
            statement.setString(2, String.valueOf(selectedDate));
            statement.setInt(3, getUserID(conn, insertedUsername));
            int rowInserted = statement.executeUpdate();

            if (rowInserted > 0) {
                System.out.println("A new appointment was inserted succesfully");
            } else {
                System.out.println("Smthng went wrong");
            }
        } else if (column == 7) {
            String sql = "INSERT INTO appointments (doctor, date, 3_PM) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, chosenDoctor);
            statement.setString(2, String.valueOf(selectedDate));
            statement.setInt(3, getUserID(conn, insertedUsername));
            int rowInserted = statement.executeUpdate();

            if (rowInserted > 0) {
                System.out.println("A new appointment was inserted succesfully");
            } else {
                System.out.println("Smthng went wrong");
            }
        } else if (column == 8) {
            String sql = "INSERT INTO appointments (doctor, date, 4_PM) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, chosenDoctor);
            statement.setString(2, String.valueOf(selectedDate));
            statement.setInt(3, getUserID(conn, insertedUsername));
            int rowInserted = statement.executeUpdate();

            if (rowInserted > 0) {
                System.out.println("A new appointment was inserted succesfully");
            } else {
                System.out.println("Smthng went wrong");
            }
        } else {
            System.out.println("Wrong data");
        }


    }
}