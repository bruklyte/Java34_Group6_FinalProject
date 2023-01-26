import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBConnection {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/java34","root", "Password123!" );
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");

            System.out.println("\tPatients table:");   //users table
            while (rs.next()){
                System.out.printf("Patients ID: %d \t| User name: %s \t| Password: %s \t| Patients full name: %s \t| Date of birth: %s\n",
                        rs.getInt(1),rs.getString(2),rs.getString(3), rs.getString(4),rs.getString(5));
            }
            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/java34","root", "Password123!" );
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM doctors");
            System.out.println("\tDoctors table:");
            while (rs.next()){
                System.out.printf("ID %d \t| Doctors name: %s \n",
                        rs.getInt(1),rs.getString(2));
            }
            con.close();


        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/java34","root", "Password123!" );
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM appointments");
            System.out.println("\tAppointments table:");
            while (rs.next()){
                System.out.printf("Doctor ID: %d \t| Date: %s \t| 9_AM: %s \t| 10_AM %s \t| 2_PM: %s \t| 3_PM %s \t| 4_PM %s \n",
                        rs.getInt(1),rs.getDate(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getString(6), rs.getString(7));
            }
            con.close();


        } catch (Exception e) {
            System.out.println(e);
        }

    }

}
