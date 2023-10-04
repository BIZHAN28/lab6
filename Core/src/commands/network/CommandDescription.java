package commands.network;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Class that holds command name, description, count of arguments, type of arguments, and object info.
 */

public class CommandDescription implements Serializable {
    private final String name;

    private final int argsCount;

    private final Class<?> requiredObjectType;

    public CommandDescription(String name, int argumentCount, Class<?> requiredObjectType) {
        this.name = name;
        this.argsCount = argumentCount;
        this.requiredObjectType = requiredObjectType;
    }

    public String getName() {
        return name;
    }

    public int getArgsCount() {
        return argsCount;
    }

    public Class<?> getRequiredObjectType() {
        return requiredObjectType;
    }


}
