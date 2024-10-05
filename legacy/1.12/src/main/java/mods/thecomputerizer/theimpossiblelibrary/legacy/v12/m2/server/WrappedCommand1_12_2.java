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
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

@ParametersAreNonnullByDefault @MethodsReturnNonnullByDefault
public class WrappedCommand1_12_2 extends CommandBase {

    private final CommandAPI wrapped;

    public WrappedCommand1_12_2(CommandAPI wrapped) {
        this.wrapped = wrapped;
    }

    @Override public String getName() {
        return this.wrapped.getName();
    }

    @Override public String getUsage(ICommandSender sender) {
        return "";
    }

    @Override public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        this.wrapped.prepareExceptionInfo();
        String exKey = this.wrapped.getExceptionKey();
        exKey = Objects.nonNull(exKey) ? exKey : "";
        Object[] exArgs = this.wrapped.getExceptionArgs();
        try {
            String remaining = args.length>0 ? args[args.length-1] : "";
            this.wrapped.execute(sender,rebuildInput(args),remaining);
        } catch(Exception ex) {
            TILRef.logError("Caught exception for command {}! Rethrowing as CommandException",getName(),ex);
            throw new CommandException(exKey,exArgs);
        }
    }
    
    @Override public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args,
            @Nullable BlockPos pos) {
        String remaining = args.length>0 ? args[args.length-1] : "";
        return this.wrapped.getTabCompletions(sender,rebuildInput(args),remaining);
    }
    
    private String rebuildInput(String ... args) {
        StringJoiner joiner = new StringJoiner(" ");
        for(int i=0;i<args.length-1;i++) joiner.add(args[i]);
        return joiner.toString();
    }
}