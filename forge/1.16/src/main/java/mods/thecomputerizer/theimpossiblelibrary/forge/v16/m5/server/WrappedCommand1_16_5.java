package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.server;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.*;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import mcp.MethodsReturnNonnullByDefault;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.server.CommandAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.CommandAPI.ArgType;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelper;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.text.Text1_16_5;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class WrappedCommand1_16_5 {

    public static void register(CommandDispatcher<CommandSource> dispatcher, CommandAPI wrapped) {
        LiteralArgumentBuilder<CommandSource> root = Commands.literal(wrapped.getName());
        for(CommandAPI subcmd : wrapped.getSubCommands()) {
            RequiredArgumentBuilder<CommandSource,?> arg = getArg(subcmd);
            if(Objects.nonNull(arg)) root.then(arg);
        }
        if(wrapped.isExecutionNode()) root.executes(ctx -> execute(ctx,wrapped));
        dispatcher.register(root);
    }

    public static int execute(CommandContext<CommandSource> ctx, CommandAPI wrapped) throws CommandException {
        List<CommandAPI> cmdStack = new ArrayList<>();
        cmdStack.add(wrapped);
        wrapped = wrapped.getParent();
        int argCount = 0;
        while(Objects.nonNull(wrapped)) {
            argCount++;
            cmdStack.add(wrapped);
            wrapped = wrapped.getParent();
        }
        Collections.reverse(cmdStack);
        String[] args = new String[argCount];
        for(int i=0;i<cmdStack.size();i++) args[i] = cmdStack.get(i).getName();
        wrapped = cmdStack.get(0);
        String baseName = wrapped.getName();
        wrapped.prepareExceptionInfo();
        String exKey = wrapped.getExceptionKey();
        exKey = Objects.nonNull(exKey) ? exKey : "";
        Object[] exArgs = wrapped.getExceptionArgs();
        try {
            wrapped.execute(new MinecraftServer1_16_5(),new CommandSender1_16_5(ctx),args);
            return 1;
        } catch(Exception ex) {
            TILRef.logError("Caught exception for command {}! Rethrowing as CommandException",baseName,ex);
            throw new CommandException(((Text1_16_5)TextHelper.getTranslated(exKey,exArgs)).getComponent());
        }
    }

    private static @Nullable RequiredArgumentBuilder<CommandSource,?> getArg(CommandAPI command) {
        ArgumentType<?> type = getType(command.getType());
        if(Objects.isNull(type)) return null;
        RequiredArgumentBuilder<CommandSource,?> arg = Commands.argument(command.getName(),type);
        for(CommandAPI subcmd : command.getSubCommands()) arg.then(getArg(subcmd));
        if(command.isExecutionNode()) arg.executes(ctx -> execute(ctx,command));
        return arg;
    }

    private static @Nullable ArgumentType<?> getType(ArgType type) {
        switch(type) {
            case BOOLEAN: return BoolArgumentType.bool();
            case DOUBLE: return DoubleArgumentType.doubleArg();
            case ENTITY: return EntityArgument.entity();
            case FLOAT: return FloatArgumentType.floatArg();
            case INTEGER: return IntegerArgumentType.integer();
            case LONG: return LongArgumentType.longArg();
            case PLAYER: return EntityArgument.player();
            case STRING: return StringArgumentType.word();
            default: return null;
        }
    }
}