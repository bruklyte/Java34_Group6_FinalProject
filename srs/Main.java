import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        String dbURL = "jdbc:mysql://localhost:3306/final_project";
        String username = "root";
        String password = "12345";
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

                    String newDateOFBirth, newFullName, newUsername, newPassword;
                    boolean dobValid, nameValid, usernameValid, passwordValid;

                    do{
                    System.out.println("Enter username");
                    newUsername = scanner.nextLine();
                    usernameValid = Validations.UserNameValidator.isValidUsername(username);
                    if (!usernameValid) {
                        System.out.println("Invalid username format. Please try again.");
                    }
                    } while (!usernameValid);

                    do{
                    System.out.println("Enter password, containing at least one digit, one lowercase letter, one uppercase letter, one special character and not shorter than 8 characters");
                    newPassword = scanner.nextLine();
                    passwordValid = Validations.PasswordValidator.isValidPassword(newPassword);
                    if (!passwordValid) {
                        System.out.println("Your password should contain at least one digit, one lowercase letter, one uppercase letter, one special character and be not shorter than 8 characters. Please try again.");
                    }
                    } while (!passwordValid);

                    do{
                    System.out.println("Enter full name");
                    newFullName = scanner.nextLine();
                    nameValid = Validations.FullNameValidator.isValidFullName(newFullName);
                    if (!nameValid) {
                        System.out.println("Please enter your name and surname and try again.");
                        }
                    } while (!nameValid);

                    do{
                        System.out.println("Enter your date of birth (YYYY-MM-DD):");
                        newDateOFBirth = scanner.nextLine();
                        dobValid = Validations.DOBisValid(newDateOFBirth);
                        if (!dobValid) {
                            System.out.println("Please check your date of birth and try again.");
                        }
                    } while (!dobValid);


                    DataBase.insertData(conn, newUsername, newPassword, newFullName, newDateOFBirth);
                    //System.out.println("You are registered");
                    //printChoicesList(); - printing choices twice

                } else if (action == 'l') {
                    while (true){
                        System.out.println("Please enter your username");
                        insertedUsername = scanner.nextLine();
                        System.out.println("Please enter your password");
                        String insertedPassword = scanner.nextLine();

                        if (DataBase.loginData(conn, insertedUsername, insertedPassword) > 0) {
                            System.out.println("You have logged in successfuly");
                            break;

                        } else {
                            System.out.println("Wrong username or password");
                        }
                    }
                }

                //Scanner scanner = new Scanner(System.in);
                boolean quit = false;

                int choice = 0;
                while (!quit) {
                    System.out.print("Enter your choice");
                    DataBase.printChoicesList();
                    try {
                        choice = scanner.nextInt();
                        scanner.nextLine();

                        switch (choice) {
                            case 0:
                                DataBase.printChoicesList();
                                break;
                            case 1:
                                // DoctorsList - to choose appointment time;
                                // doctorsList.printDoctorsList();
                                DataBase.printDoctorsList(conn);
                                break;
                            case 2:
                                //Select appointment date and time;
                                System.out.println("Please choose a doctor (1,2,3,4)");
                                DataBase.printDoctorsList(conn);
                                int chosenDoctor = scanner.nextInt();
                                System.out.println();
                                scanner.nextLine();
                                LocalDate selectedDate;
                                while(true){
                                    System.out.println("Please choose a date between today (" + today + ") and next week (" + nextWeek + "):");
                                    selectedDate = LocalDate.parse(scanner.nextLine());
                                    if (selectedDate.isAfter(nextWeek) || selectedDate.isBefore(today)) {
                                        System.out.println("Invalid date. Please choose a date between " + today + " and " + nextWeek);
                                    } else if (DataBase.checkingIfUserAlreadyHaveAppointment(conn, selectedDate, insertedUsername) > 0){
                                        System.out.println("You already have registration on that day");
                                    } else {
                                        System.out.println("You have selected " + selectedDate);
                                        break;
                                    }
                                }

                                if (DataBase.checkingDate(conn, chosenDoctor, selectedDate) > 0) {
                                    System.out.println("Please choose a free appointment time");
                                    DataBase.printFreeTimes(conn);
                                    String columName = scanner.nextLine();
                                    DataBase.appointingFreeSlot(conn, chosenDoctor, selectedDate, insertedUsername, columName);

                                } else {
                                    System.out.println("Please choose an appointments time (1,2,3,4,5)");
                                    DataBase.printTimes(conn);
                                    System.out.println();
                                    int column = scanner.nextInt() + 3;
                                    DataBase.appointing(conn, chosenDoctor, selectedDate, insertedUsername, column);
                                }
                                break;
                            case 3:
                                DataBase.printingMyAppointments(conn, insertedUsername);


                                break;
                            case 4:
                                System.out.println("Please enter the appointment date");
                                selectedDate = LocalDate.parse(scanner.nextLine());

                                if(DataBase.checkingIfUserAlreadyHaveAppointment(conn, selectedDate, insertedUsername) > 0){
                                    System.out.println("Please enter the appointment time (9_AM, 10_AM, 2_PM, 3_PM, 4_PM)");
                                    String columnName = scanner.nextLine();
                                    DataBase.deletingAppointments(conn, selectedDate, columnName);
                                }else {
                                    System.out.println("You don't have an appointment on that day");
                                }
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
    }
