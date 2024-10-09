package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.server;

import mcp.MethodsReturnNonnullByDefault;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.server.CommandAPI;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

@ParametersAreNonnullByDefault @MethodsReturnNonnullByDefault
public class WrappedCommand1_12_2 extends CommandBase {

    private final CommandAPI wrapped;
    private final List<List<CommandAPI>> executionStacks;

    public WrappedCommand1_12_2(CommandAPI wrapped) {
        this.wrapped = wrapped;
        this.executionStacks = collectExecutionStacks();
    }
    
    List<List<CommandAPI>> collectExecutionStacks() {
        List<List<CommandAPI>> stacks = new ArrayList<>();
        for(CommandAPI node : findExecutionNodes(this.wrapped)) stacks.add(node.getStackReverse());
        return stacks;
    }
    
    List<CommandAPI> computeCommandStack(String[] args) {
        for(List<CommandAPI> stack : this.executionStacks)
            if(isValidStack(stack,args)) return stack;
        return Collections.singletonList(this.wrapped);
    }
    
    @Override public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        List<CommandAPI> stack = computeCommandStack(args);
        executeAt(sender,stack.get(stack.size()-1),rebuildInput(args),args.length>0 ? args[args.length-1] : "");
    }
    
    void executeAt(ICommandSender sender, CommandAPI executionNode, String input, String remaining) throws CommandException {
        executionNode.prepareExceptionInfo();
        String exKey = executionNode.getExceptionKey();
        exKey = Objects.nonNull(exKey) ? exKey : "";
        Object[] exArgs = executionNode.getExceptionArgs();
        try {
            executionNode.execute(sender,input,remaining);
        } catch(Exception ex) {
            TILRef.logError("Caught exception for command {}! Rethrowing as CommandException",getName(),ex);
            throw new CommandException(exKey,exArgs);
        }
    }
    
    List<CommandAPI> findExecutionNodes(CommandAPI command) {
        List<CommandAPI> nodes = new ArrayList<>();
        findExecutionNodes(command,nodes);
        return nodes;
    }
    
    void findExecutionNodes(CommandAPI command, List<CommandAPI> nodes) {
        if(command.isExecutionNode()) nodes.add(command);
        for(CommandAPI subCmd : command.getSubCommands()) findExecutionNodes(subCmd,nodes);
    }

    @Override public String getName() {
        return this.wrapped.getName();
    }
    
    @Override public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args,
            @Nullable BlockPos pos) {
        List<CommandAPI> stack = computeCommandStack(args);
        String remaining = args.length>0 ? args[args.length-1] : "";
        return stack.get(stack.size()-1).getTabCompletions(sender,rebuildInput(args),remaining);
    }
    
    @Override public String getUsage(ICommandSender sender) {
        return "";
    }
    
    boolean isValidStack(List<CommandAPI> stack, String[] args) {
        if(stack.size()!=args.length+1) return false;
        for(int i=0;i<args.length;i++)
            if(!stack.get(i+1).isValidInput(args[i])) return false;
        return true;
    }
    
    private String rebuildInput(String ... args) {
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add(this.wrapped.getRootName());
        for(int i=0;i<args.length-1;i++) joiner.add(args[i]);
        return joiner.toString();
    }
}