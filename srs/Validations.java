import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validations {
    public static boolean DOBisValid(String newDateOFBirth) {
        if (newDateOFBirth == null || !newDateOFBirth.matches("\\d{4}-[01]\\d-[0-3]\\d")) {
            return false;
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        df.setLenient(false);
        try {
            df.parse(newDateOFBirth);
            return true;
        } catch (ParseException ex) {
            return false;
        }
    }
    public class FullNameValidator {
        private static Pattern fullNamePattern = Pattern.compile("^[A-Z][a-z]+ [A-Z][a-z]+$");

        public static boolean isValidFullName(String name) {
            Matcher matcher = fullNamePattern.matcher(name);
            return matcher.matches();
        }
    }

    //a string that starts with one or more alphanumeric characters, followed by zero or more sequences of a dot or underscore, and one or more alphanumeric characters, until the end of the string.
    public class UserNameValidator {
        private static Pattern userNamePattern = Pattern.compile("^[a-zA-Z0-9]+([._]?[a-zA-Z0-9]+)*$");

        public static boolean isValidUsername(String username) {
            Matcher matcher = userNamePattern.matcher(username);
            return matcher.matches();
        }
    }

    // at least one digit, one lowercase letter, one uppercase letter, one special character (@#$%^&+=) and has a minimum length of 8 characters.
    public class PasswordValidator {
        private static Pattern passwordPattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");

        public static boolean isValidPassword(String password) {
            Matcher matcher = passwordPattern.matcher(password);
            return matcher.matches();
        }
    }

    }




}
