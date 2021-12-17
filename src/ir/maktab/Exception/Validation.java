package ir.maktab.Exception;

public class Validation {
    public boolean validateUserInfo(String fullName, String email, String nationalId) throws InvalidEmailException, InvalidNationalIDException, InvalidNameException {
        return validateFullName(fullName) && validateEmail(email) && validateNationalID(nationalId);
    }

    private boolean validateNationalID(String nationalId) throws InvalidNationalIDException {
        if (nationalId.matches("[0-9]+") && nationalId.length() == 10) {
            return true;
        }
        throw new InvalidNationalIDException("invalid national Id or user name");
    }

    private boolean validateEmail(String email) throws InvalidEmailException {
        if (email.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
            return true;
        }
        throw new InvalidEmailException("invalid Email!");
    }

    public boolean validatePhoneNumber(String phoneNumber) throws InvalidPhoneNumberException {
        if (phoneNumber.matches("[0-9]+") && phoneNumber.startsWith("09") && phoneNumber.length() == 11) {
            return true;
        }
        throw new InvalidPhoneNumberException("invalid phoneNumber!");
    }

    private boolean validateFullName(String fullName) throws InvalidNameException {
        if (fullName.length() > 4 && fullName.matches("[a-zA-Z]*")) {
            return true;
        }
        throw new InvalidNameException("invalid name!");
    }

    public boolean validateInput(String number) throws InvalidInputException {
        if (number.matches("[0-9]*")) {
            return true;
        }
        throw new InvalidInputException("invalid input!");
    }

}
