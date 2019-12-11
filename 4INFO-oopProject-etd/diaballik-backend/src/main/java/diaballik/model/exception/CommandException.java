package diaballik.model.exception;

import diaballik.model.control.Command;

public class CommandException extends Exception {

    public CommandException() {super();};

    public CommandException(String message) {
        super(message);
    }

}
