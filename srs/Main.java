import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static UserInfo patient = new UserInfo();

    public static void main(String[] args) {

        boolean quit = false;
        int choice = 0;
        printChoicesList();
        while (!quit){      // "!" perjungia i oposite value
            System.out.println("Enter your choise");

            try {
                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice){
                    case 0:
                        //print all options
                        printChoicesList();         //   need to create method -> below
                        break;
                    case 1:
                        // DoctorsList - to choose appointment time;
                        // doctorsList.printDoctorsList();
                        // appointmentTime.chooseAppointmentTime()
                        break;
                    case 2:
                        //MyAppointments;
                        //myAppointments.printMyAppointments();
                        break;
                    case 3:
                        //Cancel the appointment();
                        //cancel.cancelMyAppointment();
                        break;
                    case 4:
                        quit = true;
                        break;
                    default:
                        System.out.println("Input not valid (0-4)");
                        break;
                }
            }catch (InputMismatchException e){
                System.err.println("Wrong input!");
                scanner.nextLine();
            }
        }
    }

    private static void printChoicesList(){
        System.out.println("\nPress");
        System.out.println("\t 0 - To print choice options");
        System.out.println("\t 1 - Doctors list - to choose appointment time");
        System.out.println("\t 2 - My appointments");
        System.out.println("\t 3 - Cancel the appointment");
        System.out.println("\t 4 - To quit the application");
    }

    private static void printDoctorsList()  {
        System.out.println();
    }

    private static void chooseAppointmentTime(){
        System.out.println();

    }

    private static void printMyAppointments(){      //search for appointment and print
        System.out.println();

    }

    private static void cancelMyAppointment(){      //delete the appointment
        System.out.println();

    }

}