package mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.server;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.server.CommandAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.CommandAPI.ArgType;
import mods.thecomputerizer.theimpossiblelibrary.api.server.ServerHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelper;
import net.minecraft.commands.CommandRuntimeException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@ParametersAreNonnullByDefault
public class WrappedCommandFabric1_16_5 {
    
    @SuppressWarnings("unchecked")
    public static void register(Object dispatcherObj, CommandAPI wrapped) {
        CommandDispatcher<CommandSourceStack> dispatcher = (CommandDispatcher<CommandSourceStack>)dispatcherObj;
        LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal(wrapped.getName());
        for(CommandAPI subcmd : wrapped.getSubCommands()) {
            RequiredArgumentBuilder<CommandSourceStack,?> arg = getArg(subcmd);
            if(Objects.nonNull(arg)) root.then(arg);
        }
        if(wrapped.isExecutionNode()) root.executes(ctx -> execute(ctx,wrapped));
        dispatcher.register(root);
    }

    public static int execute(CommandContext<CommandSourceStack> ctx, CommandAPI wrapped) throws CommandRuntimeException {
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
            wrapped.execute(ServerHelper.getAPI(), new CommandSenderFabric1_16_5(ctx),args);
            return 1;
        } catch(Exception ex) {
            TILRef.logError("Caught exception for command {}! Rethrowing as CommandException",baseName,ex);
            throw new CommandRuntimeException(TextHelper.getTranslated(exKey,exArgs).getAsComponent());
        }
    }

    private static @Nullable RequiredArgumentBuilder<CommandSourceStack,?> getArg(CommandAPI command) {
        ArgumentType<?> type = getType(command.getType());
        if(Objects.isNull(type)) return null;
        RequiredArgumentBuilder<CommandSourceStack,?> arg = Commands.argument(command.getName(),type);
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