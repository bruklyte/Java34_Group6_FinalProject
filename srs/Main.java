import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String dbURL = "jdbc:mysql://localhost:3306/java34";
        String username = "root";
        String password = "Password1234!";
        Scanner scanner = new Scanner(System.in);
        LocalDate today = LocalDate.now();
        LocalDate nextWeek = today.plus(7, ChronoUnit.DAYS);
        String insertedUsername = null;
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
                    //printChoicesList(); - printing choices twice

                } else if (action == 'l') {
                    System.out.println("Enter username and password");
                    insertedUsername = scanner.nextLine();
                    String insertedPassword = scanner.nextLine();
                    loginData(conn, insertedUsername, insertedPassword);
                }

                //Scanner scanner = new Scanner(System.in);
                boolean quit = false;

                int choice = 0;
                while (!quit) {      // "!" perjungia i oposite value
                    System.out.print("Enter your choice");
                    printChoicesList();
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
                                System.out.println("Please choose a doctor (1,2,3)");
                                printDoctorsList(conn);
                                int chosenDoctor = scanner.nextInt();
                                scanner.nextLine();

                                System.out.println("Please choose a date between today (" + today + ") and next week (" + nextWeek + "):");

                                LocalDate selectedDate = LocalDate.parse(scanner.nextLine());
                                if (selectedDate.isAfter(nextWeek) || selectedDate.isBefore(today)) {
                                    System.out.println("Invalid date. Please choose a date between " + today + " and " + nextWeek);
                                } else {
                                    System.out.println("You have selected " + selectedDate);
                                }
                                if (checkingDate(conn, chosenDoctor, selectedDate) > 0) {
                                    System.out.println("Please choose a free appointment time");
                                    printFreeTimes(conn);
                                    String columName = scanner.nextLine();
                                    appointingFreeSlot(conn, chosenDoctor, selectedDate, insertedUsername, columName);

                                } else {
                                    System.out.println("Please choose an appointments time (1,2,3,4,5)");
                                    printTimes(conn);
                                    int column = scanner.nextInt() + 3;
                                    appointing(conn, chosenDoctor, selectedDate, insertedUsername, column);
                                }

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
    public static void insertData(Connection conn, String newUsername, String newPassword, String newFullName, String newDateOFBirth) throws SQLException {

        String sql = "INSERT INTO users (username, password, fullname, dateofbirth) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, newUsername);
        statement.setString(2, newPassword);
        statement.setString(3, newFullName);
        statement.setString(4, newDateOFBirth);

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


        for (int i = 3; i <= columnsNumber; i++) {
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
    public static int checkingDate(Connection conn, int chosenDoctor, LocalDate selectedDate) throws SQLException {
        String sql = "SELECT FROM appointments (doctor, date) VALUES (?, ?)";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM appointments WHERE doctor = '" + chosenDoctor + "' AND date = '" + selectedDate + "'");
        if (!rs.next()) {
            return 0;
        } else {
            return 1;
        }
    }

    public static void printFreeTimes(Connection conn) throws SQLException {
        Statement statement = conn.createStatement();
        String sql = "SELECT * FROM appointments";

        ResultSet rs = statement.executeQuery(sql);
        if (!rs.next()) {
            System.out.println("No data in the table");
        } else {
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = rsmd.getColumnName(i);
                if (rs.getObject(i) == null) {
                    System.out.print(columnName + " ");
                }
            }
        }
    }

    public static void appointingFreeSlot (Connection conn, int chosenDoctor, LocalDate selectedDate, String insertedUsername, String columnName ) throws SQLException{
        if (columnName.equals("9_AM")){
            String sql = "UPDATE appointments SET 9_AM = ? WHERE doctor = ? AND date = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, getUserID(conn, insertedUsername));
            statement.setInt(2, chosenDoctor);
            statement.setString(3, String.valueOf(selectedDate));

            int rowInserted = statement.executeUpdate();

            if (rowInserted > 0) {
                System.out.println("A new appointment was inserted succesfully");
            } else {
                System.out.println("Smthng went wrong");
            }


        } else if (columnName.equals("10_AM")) {
            String sql = "UPDATE appointments SET 10_AM = ? WHERE doctor = ? AND date = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, getUserID(conn, insertedUsername));
            statement.setInt(2, chosenDoctor);
            statement.setString(3, String.valueOf(selectedDate));

            int rowInserted = statement.executeUpdate();

            if (rowInserted > 0) {
                System.out.println("A new appointment was inserted succesfully");
            } else {
                System.out.println("Smthng went wrong");
            }

        } else if (columnName.equals("2_PM")) {
            String sql = "UPDATE appointments SET 2_PM = ? WHERE doctor = ? AND date = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, getUserID(conn, insertedUsername));
            statement.setInt(2, chosenDoctor);
            statement.setString(3, String.valueOf(selectedDate));

            int rowInserted = statement.executeUpdate();

            if (rowInserted > 0) {
                System.out.println("A new appointment was inserted succesfully");
            } else {
                System.out.println("Smthng went wrong");
            }

        } else if (columnName.equals("3_PM")) {
            String sql = "UPDATE appointments SET 3_PM = ? WHERE doctor = ? AND date = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, getUserID(conn, insertedUsername));
            statement.setInt(2, chosenDoctor);
            statement.setString(3, String.valueOf(selectedDate));

            int rowInserted = statement.executeUpdate();

            if (rowInserted > 0) {
                System.out.println("A new appointment was inserted succesfully");
            } else {
                System.out.println("Smthng went wrong");
            }

        } else if (columnName.equals("4_PM")) {
            String sql = "UPDATE appointments SET 4_PM = ? WHERE doctor = ? AND date = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, getUserID(conn, insertedUsername));
            statement.setInt(2, chosenDoctor);
            statement.setString(3, String.valueOf(selectedDate));

            int rowInserted = statement.executeUpdate();

            if (rowInserted > 0) {
                System.out.println("A new appointment was inserted succesfully");
            } else {
                System.out.println("Smthng went wrong");
            }

        }else {
            System.out.println("Wrong time");
        }
    }
    }
