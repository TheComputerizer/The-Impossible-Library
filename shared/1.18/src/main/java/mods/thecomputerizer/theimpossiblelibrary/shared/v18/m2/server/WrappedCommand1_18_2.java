package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.server;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.server.CommandAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.CommandSenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.MinecraftServerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.ServerHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.minecraft.commands.CommandRuntimeException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.concurrent.CompletableFuture;

@ParametersAreNonnullByDefault
public class WrappedCommand1_18_2 {

    public static int execute(CommandContext<CommandSourceStack> ctx, CommandAPI wrapped) throws CommandRuntimeException {
        wrapped.prepareExceptionInfo();
        String exKey = wrapped.getExceptionKey();
        exKey = Objects.nonNull(exKey) ? exKey : "";
        Object[] exArgs = wrapped.getExceptionArgs();
        try {
            String[] args = getArgStack(ctx);
            wrapped.execute(ctx, rebuildInput(args), args[args.length-1]);
            return 1;
        } catch(Exception ex) {
            TILRef.logError("Caught exception for command {}! Rethrowing as CommandException",wrapped.getRootName(),ex);
            throw new CommandRuntimeException(TextHelper.getTranslated(exKey,exArgs).getAsComponent());
        }
    }

    private static ArgumentBuilder<CommandSourceStack,?> getArg(CommandAPI command) {
        ArgumentBuilder<CommandSourceStack,?> arg = getBuilder(command);
        for(CommandAPI subcmd : command.getSubCommands()) arg.then(getArg(subcmd));
        if(command.isExecutionNode()) arg.executes(ctx -> execute(ctx,command));
        return arg;
    }
    
    private static String[] getArgStack(CommandContext<?> ctx) {
        String input = ctx.getInput();
        if(!input.contains(" ")) return new String[]{};
        return input.substring(input.indexOf(" ")+1).split(" ");
    }

    private static ArgumentBuilder<CommandSourceStack,?> getBuilder(CommandAPI command) {
        String name = command.getName();
        return switch(command.getType()) {
            case BOOLEAN -> Commands.argument(name, BoolArgumentType.bool());
            case DOUBLE -> Commands.argument(name, DoubleArgumentType.doubleArg());
            case ENTITY -> Commands.argument(name, EntityArgument.entity());
            case FLOAT -> Commands.argument(name, FloatArgumentType.floatArg());
            case INTEGER -> Commands.argument(name, IntegerArgumentType.integer());
            case LITERAL -> Commands.literal(name);
            case LONG -> Commands.argument(name, LongArgumentType.longArg());
            case PLAYER -> Commands.argument(name, EntityArgument.player());
            default -> Commands.argument(name,new CustomSuggester(command));
        };
    }
    
    private static String rebuildInput(String ... args) {
        StringJoiner joiner = new StringJoiner(" ");
        for(int i=0;i<args.length-1;i++) joiner.add(args[i]);
        return joiner.toString();
    }
    
    @SuppressWarnings("unchecked")
    public static void register(Object dispatcherObj, CommandAPI wrapped) {
        CommandDispatcher<CommandSourceStack> dispatcher = (CommandDispatcher<CommandSourceStack>)dispatcherObj;
        LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal(wrapped.getName());
        for(CommandAPI subcmd : wrapped.getSubCommands()) root.then(getArg(subcmd));
        if(wrapped.isExecutionNode()) root.executes(ctx -> execute(ctx,wrapped));
        dispatcher.register(root);
    }
    
    record CustomSuggester(CommandAPI command) implements ArgumentType<String> {
        
        public <S> CompletableFuture<Suggestions> listSuggestions(
                final CommandContext<S> ctx, final SuggestionsBuilder builder) {
                MinecraftServerAPI<?> server = ServerHelper.getAPI();
                CommandSenderAPI<?> sender = WrapperHelper.wrapCommandSender(ctx);
                return SharedSuggestionProvider.suggest(
                        this.command.getTabCompletions(server,sender,builder.getInput(),builder.getRemaining()),
                        builder);
            }
            
            @Override public String parse(StringReader reader) {
                return reader.readUnquotedString();
            }
        }
}