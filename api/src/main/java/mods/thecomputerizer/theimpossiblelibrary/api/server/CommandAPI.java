package mods.thecomputerizer.theimpossiblelibrary.api.server;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Misc;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import org.apache.logging.log4j.util.TriConsumer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
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
        if(Objects.nonNull(this.parent)) this.parent.addSubCommand(this);
    }
    
    @IndirectCallers
    public void addBasicSubCommand(String name, ArgType type, TriConsumer<MinecraftServerAPI<?>,CommandSenderAPI<?>,String[]> executor) {
        addSubCommand(new CommandAPI(name,this,type,Objects.nonNull(executor)) {
            @Override public void execute(MinecraftServerAPI<?> server, CommandSenderAPI<?> sender, String input, String remaining) {
                if(Objects.nonNull(executor)) executor.accept(server,sender,new String[]{input,remaining});
            }
            
            @Override public List<String> getTabCompletions(
                    MinecraftServerAPI<?> server, CommandSenderAPI<?> sender, String input, String remaining) {
                return Collections.emptyList();
            }
            
            @Override public void prepareExceptionInfo() {}
        });
    }

    @IndirectCallers
    public void addSubCommand(CommandAPI cmd) {
        this.subCommands.add(cmd);
    }
    
    protected void buildStack(List<CommandAPI> stack) {
        stack.add(this);
        if(Objects.nonNull(this.parent)) this.parent.buildStack(stack);
    }
    
    public void execute(Object unwrappedSender, String input, String remaining) throws Exception {
        execute(getServer(),getSender(unwrappedSender),input,remaining);
    }

    public abstract void execute(MinecraftServerAPI<?> server, CommandSenderAPI<?> sender, String input,
            String remaining) throws Exception;
    
    protected String getErrorKey(String ... extras) {
        String[] keys = new String[extras.length+1];
        keys[0] = "error";
        System.arraycopy(extras,0,keys,1,extras.length);
        return getMessageKey(keys);
    }
    
    protected String getMessageKey(String ... keys) {
        StringJoiner joiner = new StringJoiner(".");
        joiner.add("commands");
        for(CommandAPI node : getStackReverse()) joiner.add(node.getName());
        for(String key : keys) joiner.add(key);
        return joiner.toString();
    }
    
    public CommandAPI getRoot() {
        List<CommandAPI> stack = getStack();
        return stack.get(stack.size()-1);
    }
    
    public String getRootName() {
        return getRoot().getName();
    }
    
    public CommandSenderAPI<?> getSender(Object sender) {
        return WrapperHelper.wrapCommandSender(sender);
    }
    
    public MinecraftServerAPI<?> getServer() {
        return ServerHelper.getAPI();
    }
    
    public List<CommandAPI> getStack() {
        List<CommandAPI> stack = new ArrayList<>();
        buildStack(stack);
        return stack;
    }
    
    public List<CommandAPI> getStackReverse() {
        List<CommandAPI> stack = getStack();
        Collections.reverse(stack);
        return stack;
    }
    
    public List<String> getTabCompletions(Object unwrappedSender, String input, String remaining) {
        return getTabCompletions(getServer(),getSender(unwrappedSender),input,remaining);
    }
    
    public abstract List<String> getTabCompletions(MinecraftServerAPI<?> server, CommandSenderAPI<?> sender,
            String input, String remaining);
    
    protected String getUsageKey() {
        return getMessageKey("usage");
    }
    
    protected boolean hasParentInput(String input) {
        CommandAPI parent = getParent();
        if(Objects.isNull(parent) || parent==getRoot()) return true;
        String[] args = input.split(" ");
        List<CommandAPI> stack = getStackReverse();
        for(int i=args.length-1;i>=0;i--) {
            String arg = args[i].charAt(0)=='/' ? args[i].substring(1) : args[i];
            if(i>=stack.size() || !stack.get(i).isValidInput(arg)) return false;
        }
        return true;
    }
    
    protected boolean isValidInput(String input) {
        switch(getType()) {
            case BOOLEAN: return Misc.equalsAny(input,"true","false");
            case BYTE: return isValidNumber(input,Byte::parseByte);
            case DOUBLE: return isValidNumber(input,Double::parseDouble);
            case FLOAT: return isValidNumber(input,Float::parseFloat);
            case INTEGER: return isValidNumber(input,Integer::parseInt);
            case LITERAL: return getName().equals(input);
            case ROOT: return getRoot()==this;
            case LONG: return isValidNumber(input,Long::parseLong);
            case SHORT: return isValidNumber(input,Short::parseShort);
            case STRING: return isValidString(input);
            default: return true;
        }
    }
    
    /**
     * Only string type commands should override this
     */
    protected boolean isValidString(String input) {
        return true;
    }
    
    protected boolean isValidNumber(String input, Function<String,Number> func) {
        try {
            func.apply(input);
            return true;
        } catch(NumberFormatException ignored) {
            return false;
        }
    }

    /**
     * The actual unparsed string should be the last element in the array after the server or whatever else is needed
     * to parse it correctly.
     */
    @IndirectCallers
    public <T> T parseArgAs(Object[] arg, Class<T> ignored) throws Exception {
        return this.type.parseArg(arg);
    }

    public abstract void prepareExceptionInfo();
    
    @SuppressWarnings("unchecked")
    public <E> E unwrapEntity(CommandSenderAPI<?> sender) {
        EntityAPI<?,?> entity = sender.getEntity();
        return Objects.nonNull(entity) ? (E)entity.getEntity() : null;
    }

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
        LITERAL(1,1,obj -> String.valueOf(obj[0])),
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
        STRING(1,1,obj -> String.valueOf(obj[0]));

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