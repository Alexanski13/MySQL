package gamestore.constants;

import gamestore.domain.dtos.game.GameDto;

public enum Validations {
    ;
    public static final String EMAIL_PATTERN = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$";
    public static final String EMAIL_NOT_VALID_MESSAGE = "Incorrect email.";

    public static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,}$";

    public static final String USERNAME_OR_PASSWORD_NOT_VALID_MESSAGE = "Incorrect username / password";

    public static final String CONFIRM_PASSWORD_INVALID = "Passwords do not match";

    public static final String COMMAND_NOT_FOUND = "Command not found";


}
