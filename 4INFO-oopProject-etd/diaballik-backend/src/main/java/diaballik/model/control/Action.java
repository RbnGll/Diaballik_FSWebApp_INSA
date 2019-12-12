package diaballik.model.control;

import diaballik.model.exception.CommandException;

public interface Action {

    boolean exe();

    boolean canDo() throws CommandException;

}
