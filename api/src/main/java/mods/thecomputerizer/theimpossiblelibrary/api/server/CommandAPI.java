package mods.thecomputerizer.theimpossiblelibrary.api.server;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.function.Function;

/**
 * Needs to be registered while the server is starting up (call MinecraftServerAPI#registerCommand to register it)
 */
@Getter
public abstract class CommandAPI {

    protected final CommandAPI parent;
    protected final String name;
    protected final ArgType type;
    protected final boolean executionNode;
    protected final Collection<CommandAPI> subCommands;
    protected String exceptionKey;
    protected Object[] exceptionArgs;

    public CommandAPI(String name, CommandAPI parent, ArgType type, boolean executionNode) {
        this.name = name;
        this.parent = parent;
        this.type = type;
        this.executionNode = executionNode;
        this.subCommands = new HashSet<>();
    }

    @IndirectCallers
    public void addSubCommand(CommandAPI cmd) {
        this.subCommands.add(cmd);
    }

    public abstract void execute(MinecraftServerAPI<?> server, CommandSenderAPI<?> sender, String ... args) throws Exception;
    public abstract List<String> getTabCompletions(MinecraftServerAPI<?> server, CommandSenderAPI<?> sender, String ... args);

    /**
     * The actual unparsed string should be the last element in the array after the server or whatever else is needed
     * to parse it correctly.
     */
    @IndirectCallers
    public <T> T parseArgAs(Object[] arg, Class<T> ignored) throws Exception {
        return this.type.parseArg(arg);
    }

    public abstract void prepareExceptionInfo();

    public enum ArgType {

        BOOLEAN(1,1,obj -> Boolean.parseBoolean(obj[0].toString())),
        BLOCK_POS(3,3,obj -> CommandHelper.parseBlockPos((EntityAPI<?,?>)obj[0],obj[1].toString())),
        BYTE(1,1,obj -> Byte.parseByte(obj[0].toString())),
        DOUBLE(1,1,obj -> Double.parseDouble(obj[0].toString())),
        ENTITY(1,1,obj -> {
            try {
                return CommandHelper.parseEntity((MinecraftServerAPI<?>)obj[0],(CommandSenderAPI<?>)obj[1],obj[2].toString());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }),
        FLOAT(1,1,obj -> Boolean.parseBoolean(obj[0].toString())),
        INTEGER(1,1,obj -> Boolean.parseBoolean(obj[0].toString())),
        LONG(1,1,obj -> Boolean.parseBoolean(obj[0].toString())),
        PLAYER(1,1,obj -> {
            try {
                return CommandHelper.parsePlayer((MinecraftServerAPI<?>)obj[0],(CommandSenderAPI<?>)obj[1],obj[2].toString());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }),
        POS(3,3,obj -> CommandHelper.parsePos((EntityAPI<?,?>)obj[0],obj[1].toString())),
        ROOT(1,1,obj -> obj[0].toString()),
        SHORT(1,1,obj -> Boolean.parseBoolean(obj[0].toString())),
        STRING(1,1,obj -> obj[0].toString());

        private final int minLength;
        private final int maxLength;
        private final Function<Object[],?> parser;

        <T> ArgType(int minLength, int maxLength, Function<Object[],T> parser) {
            this.minLength = minLength;
            this.maxLength = maxLength;
            this.parser = parser;
        }

        @SuppressWarnings("unchecked")
        public <T> T parseArg(Object[] objs) throws Exception {
            return ((Function<Object[],T>)this.parser).apply(objs);
        }
    }
}