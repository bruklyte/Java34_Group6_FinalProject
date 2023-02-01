import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

public class DataBase {

    public static int loginData(Connection conn, String insertedUsername, String insertedPassword) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = '" + insertedUsername + "' and password = '" + insertedPassword + "'";
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        if (resultSet.next()) {
            return 1;
        } else {
            return 0;
        }
    }

    public static void insertData(Connection conn, String insertedUsername, String insertedPassword, String newFullName, String newDateOFBirth) throws SQLException {

        String sql = "INSERT INTO users (username, password, fullname, dateofbirth) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, insertedUsername);
        statement.setString(2, insertedPassword);
        statement.setString(3, newFullName);
        statement.setString(4, newDateOFBirth);

        int rowInserted = statement.executeUpdate();

        if (rowInserted > 0) {
            System.out.println("Registration of a user was successful");
        } else {
            System.out.println("Something went wrong");
        }
    }

    public static void printChoicesList() {
        System.out.println("\nPress");
        System.out.println("\t 0 - To print choice options");
        System.out.println("\t 1 - Doctors list");
        System.out.println("\t 2 - Select appointment date and time");
        System.out.println("\t 3 - Your appointments list");
        System.out.println("\t 4 - Cancel the appointment");
        System.out.println("\t 5 - To quit the application");
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
                System.out.println("A new appointment was inserted successfully");
            } else {
                System.out.println("Something went wrong");
            }
        } else if (column == 5) {
            String sql = "INSERT INTO appointments (doctor, date, 10_AM) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, chosenDoctor);
            statement.setString(2, String.valueOf(selectedDate));
            statement.setInt(3, getUserID(conn, insertedUsername));
            int rowInserted = statement.executeUpdate();

            if (rowInserted > 0) {
                System.out.println("A new appointment was inserted successfully");
            } else {
                System.out.println("Something went wrong");
            }
        } else if (column == 6) {
            String sql = "INSERT INTO appointments (doctor, date, 2_PM) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, chosenDoctor);
            statement.setString(2, String.valueOf(selectedDate));
            statement.setInt(3, getUserID(conn, insertedUsername));
            int rowInserted = statement.executeUpdate();

            if (rowInserted > 0) {
                System.out.println("A new appointment was inserted successfully");
            } else {
                System.out.println("Something went wrong");
            }
        } else if (column == 7) {
            String sql = "INSERT INTO appointments (doctor, date, 3_PM) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, chosenDoctor);
            statement.setString(2, String.valueOf(selectedDate));
            statement.setInt(3, getUserID(conn, insertedUsername));
            int rowInserted = statement.executeUpdate();

            if (rowInserted > 0) {
                System.out.println("A new appointment was inserted successfully");
            } else {
                System.out.println("Something went wrong");
            }
        } else if (column == 8) {
            String sql = "INSERT INTO appointments (doctor, date, 4_PM) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, chosenDoctor);
            statement.setString(2, String.valueOf(selectedDate));
            statement.setInt(3, getUserID(conn, insertedUsername));
            int rowInserted = statement.executeUpdate();

            if (rowInserted > 0) {
                System.out.println("A new appointment was inserted successfully");
            } else {
                System.out.println("Something went wrong");
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

    public static void printFreeTimes(Connection conn, int chosenDoctor, LocalDate selectedDate) throws SQLException {
        Statement statement = conn.createStatement();
        String sql = "SELECT * FROM appointments WHERE doctor = '" +chosenDoctor+ "' AND date = '" +selectedDate+ "'";

        ResultSet rs = statement.executeQuery(sql);
        if (!rs.next()) {
            System.out.println("No data in the table");
        } else {
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            for (int i = 3; i <= columnCount; i++) {
                String columnName = rsmd.getColumnName(i);
                if (rs.getObject(i) == null) {
                    System.out.print(columnName + " ");
                }
            }
        }
        System.out.println();
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
                System.out.println("A new appointment was inserted successfully");
            } else {
                System.out.println("Something went wrong");
            }
        } else if (columnName.equals("10_AM")) {
            String sql = "UPDATE appointments SET 10_AM = ? WHERE doctor = ? AND date = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, getUserID(conn, insertedUsername));
            statement.setInt(2, chosenDoctor);
            statement.setString(3, String.valueOf(selectedDate));

            int rowInserted = statement.executeUpdate();

            if (rowInserted > 0) {
                System.out.println("A new appointment was inserted successfully");
            } else {
                System.out.println("Something went wrong");
            }
        } else if (columnName.equals("2_PM")) {
            String sql = "UPDATE appointments SET 2_PM = ? WHERE doctor = ? AND date = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, getUserID(conn, insertedUsername));
            statement.setInt(2, chosenDoctor);
            statement.setString(3, String.valueOf(selectedDate));

            int rowInserted = statement.executeUpdate();

            if (rowInserted > 0) {
                System.out.println("A new appointment was inserted successfully");
            } else {
                System.out.println("Something went wrong");
            }
        } else if (columnName.equals("3_PM")) {
            String sql = "UPDATE appointments SET 3_PM = ? WHERE doctor = ? AND date = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, getUserID(conn, insertedUsername));
            statement.setInt(2, chosenDoctor);
            statement.setString(3, String.valueOf(selectedDate));

            int rowInserted = statement.executeUpdate();

            if (rowInserted > 0) {
                System.out.println("A new appointment was inserted successfully");
            } else {
                System.out.println("Something went wrong");
            }
        } else if (columnName.equals("4_PM")) {
            String sql = "UPDATE appointments SET 4_PM = ? WHERE doctor = ? AND date = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, getUserID(conn, insertedUsername));
            statement.setInt(2, chosenDoctor);
            statement.setString(3, String.valueOf(selectedDate));

            int rowInserted = statement.executeUpdate();

            if (rowInserted > 0) {
                System.out.println("A new appointment was inserted successfully");
            } else {
                System.out.println("Something went wrong");
            }

        }else {
            System.out.println("Wrong time");
        }
    }
    public static void printingMyAppointments (Connection conn, String insertedUsername) throws SQLException{
        int userID = getUserID(conn, insertedUsername);
        Statement stmt = null;
        ResultSet rs = null;
        stmt = conn.createStatement();
        String query = "SELECT a.Date, a.9_AM, a.10_AM, a.2_PM, a.3_PM, a.4_PM, d.DoctorsFullName " +
                "FROM Appointments a JOIN doctors d ON a.doctor = d.DoctorsID " +
                "WHERE " +
                " 9_AM = '" + userID + "' OR " +
                "  10_AM = '" + userID + "' OR " +
                "  2_PM = '" + userID + "' OR " +
                "  3_PM = '" + userID + "' OR " +
                "  4_PM = '" + userID + "'";
        rs = stmt.executeQuery(query);
        if (!rs.next()){
            System.out.println("You don't have any appointments yet");
        }
        rs = stmt.executeQuery(query);
        while (rs.next()) {
            String date = rs.getString("Date");
            String doctorName = rs.getString("DoctorsFullName");
            String columnName = "";
            if (userID == rs.getInt("9_AM")) {
                columnName = "9_AM";
            } else if (userID == rs.getInt("10_AM")) {
                columnName = "10_AM";
            } else if (userID== rs.getInt("2_PM")) {
                columnName = "2_PM";
            } else if (userID == rs.getInt("3_PM")) {
                columnName = "3_PM";
            } else if (userID == rs.getInt("4_PM")) {
                columnName = "4_PM";
            }
            System.out.println("Doctor Name: " + doctorName + ", Date: " + date + ", Time: " + columnName);
        }
    }

    public static int checkingIfUserAlreadyHaveAppointment (Connection conn, LocalDate selectedDate, String insertedUsername) throws SQLException {
        int userID = getUserID(conn, insertedUsername);
        Statement stmt = conn.createStatement();
        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT date FROM appointments WHERE date = '" + selectedDate + "' AND (" +
                "  9_AM = '" + userID + "' OR " +
                "  10_AM = '" + userID + "' OR " +
                "  2_PM = '" + userID + "' OR " +
                "  3_PM = '" + userID + "' OR " +
                "  4_PM = '" + userID + "')");

        if (rs.next()) {
            return 1;
        } else {
            return 0;
        }
    }

    public static void deletingAppointments (Connection conn, LocalDate selectedDate, String columnName ) throws SQLException{
        if (columnName.equals("9_AM")){
            String sql = "UPDATE appointments SET 9_AM = null WHERE date = '" + selectedDate + "'";
            PreparedStatement statement = conn.prepareStatement(sql);

            int rowInserted = statement.executeUpdate();

            if (rowInserted > 0) {
                System.out.println("An appointment was deleted successfully");
            } else {
                System.out.println("Something went wrong");
            }

        } else if (columnName.equals("10_AM")) {
            String sql = "UPDATE appointments SET 10_AM = null WHERE date = '" + selectedDate + "'";
            PreparedStatement statement = conn.prepareStatement(sql);

            int rowInserted = statement.executeUpdate();

            if (rowInserted > 0) {
                System.out.println("An appointment was deleted successfully");
            } else {
                System.out.println("Something went wrong");
            }

        } else if (columnName.equals("2_PM")) {
            String sql = "UPDATE appointments SET 2_PM = null WHERE date = '" + selectedDate + "'";
            PreparedStatement statement = conn.prepareStatement(sql);

            int rowInserted = statement.executeUpdate();

            if (rowInserted > 0) {
                System.out.println("An appointment was deleted successfully");
            } else {
                System.out.println("Something went wrong");
            }

        } else if (columnName.equals("3_PM")) {
            String sql = "UPDATE appointments SET 3_PM = null WHERE date = '" + selectedDate + "'";
            PreparedStatement statement = conn.prepareStatement(sql);

            int rowInserted = statement.executeUpdate();

            if (rowInserted > 0) {
                System.out.println("An appointment was deleted successfully");
            } else {
                System.out.println("Something went wrong");
            }

        } else if (columnName.equals("4_PM")) {
            String sql = "UPDATE appointments SET 4_PM = null WHERE date = '" + selectedDate + "'";
            PreparedStatement statement = conn.prepareStatement(sql);

            int rowInserted = statement.executeUpdate();

            if (rowInserted > 0) {
                System.out.println("An appointment was deleted successfully");
            } else {
                System.out.println("Something went wrong");
            }

        }else {
            System.out.println("Wrong time");
        }
    }
}
