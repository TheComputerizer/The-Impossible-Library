package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.server;

import mcp.MethodsReturnNonnullByDefault;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.server.CommandAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.ServerHelper;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Objects;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class WrappedCommand1_12_2 extends CommandBase {

    private final CommandAPI wrapped;

    public WrappedCommand1_12_2(CommandAPI wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public String getName() {
        return this.wrapped.getName();
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        this.wrapped.prepareExceptionInfo();
        String exKey = this.wrapped.getExceptionKey();
        exKey = Objects.nonNull(exKey) ? exKey : "";
        Object[] exArgs = this.wrapped.getExceptionArgs();
        try {
            this.wrapped.execute(new MinecraftServer1_12_2(),new CommandSender1_12_2(sender),args);
        } catch(Exception ex) {
            TILRef.logError("Caught exception for command {}! Rethrowing as CommandException",getName(),ex);
            throw new CommandException(exKey,exArgs);
        }
    }
    
    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args,
            @Nullable BlockPos pos) {
        return this.wrapped.getTabCompletions(ServerHelper.getAPI(),new CommandSender1_12_2(sender),args);
    }
}
