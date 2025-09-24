package mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.server;

import com.google.gson.JsonObject;
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
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.server.CommandAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.CommandSenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.MinecraftServerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.ServerHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.server.WrappedCommand1_20_6.CustomSuggesterInfo.CustomSuggesterTemplate;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.concurrent.CompletableFuture;

import static com.mojang.brigadier.exceptions.CommandSyntaxException.BUILT_IN_EXCEPTIONS;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks.CallStrategy.STATIC;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks.CallStrategy.STATIC_DIRECT;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev.DEV;

public class WrappedCommand1_20_6 {
    
    private static final Map<String,CommandAPI> BY_NAME = new HashMap<>();
    public static final CustomSuggesterInfo INFO = new CustomSuggesterInfo();

    public static int execute(CommandContext<CommandSourceStack> ctx, CommandAPI wrapped) throws CommandSyntaxException {
        wrapped.prepareExceptionInfo();
        String exKey = wrapped.getExceptionKey();
        exKey = Objects.nonNull(exKey) ? exKey : "";
        Object[] exArgs = wrapped.getExceptionArgs();
        try {
            String[] args = getArgStack(ctx);
            wrapped.execute(ctx,rebuildInput(args),args[args.length-1]);
            return 1;
        } catch(Exception ex) {
            TILRef.logError("Caught exception for command {}! Rethrowing as CommandException",wrapped.getRootName(),ex);
            throw BUILT_IN_EXCEPTIONS.dispatcherParseException().create(TextHelper.getTranslated(exKey,exArgs).getApplied());
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
            case BOOLEAN -> Commands.argument(name,BoolArgumentType.bool());
            case DOUBLE -> Commands.argument(name,DoubleArgumentType.doubleArg());
            case ENTITY -> Commands.argument(name,EntityArgument.entity());
            case FLOAT -> Commands.argument(name,FloatArgumentType.floatArg());
            case INTEGER -> Commands.argument(name,IntegerArgumentType.integer());
            case LITERAL -> Commands.literal(name);
            case LONG -> Commands.argument(name,LongArgumentType.longArg());
            case PLAYER -> Commands.argument(name,EntityArgument.player());
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
        BY_NAME.put(wrapped.getName(),wrapped);
    }
    
    public static void registerArgType() {
        if(CoreAPI.isForge() || CoreAPI.isNeoforge())
            STATIC.invoke(ArgumentTypeInfos.class,"registerByClass",CustomSuggester.class,INFO);
        else {
            String field = DEV ? "BY_CLASS" : "field_10921";
            Map<Class<?>,Object> byClass = STATIC_DIRECT.get(ArgumentTypeInfos.class,field);
            byClass.put(CustomSuggester.class,INFO);
        }
    }
    
    public record CustomSuggester(CommandAPI command) implements ArgumentType<String> {
        
        public <S> CompletableFuture<Suggestions> listSuggestions(final CommandContext<S> ctx,
                final SuggestionsBuilder builder) {
            MinecraftServerAPI<?> server = ServerHelper.getAPI();
            CommandSenderAPI<?> sender = WrapperHelper.wrapCommandSender(ctx);
            List<String> completions = Objects.nonNull(this.command) ? this.command.getTabCompletions(
                    server,sender,builder.getInput(),builder.getRemaining()) : List.of();
            return SharedSuggestionProvider.suggest(completions,builder);
        }
        
        @Override public String parse(StringReader reader) {
            return reader.readUnquotedString();
        }
    }
        
    public static class CustomSuggesterInfo implements ArgumentTypeInfo<CustomSuggester,CustomSuggesterTemplate> {
        
        @Override public void serializeToNetwork(CustomSuggesterTemplate template, FriendlyByteBuf buf) {
            buf.writeUtf(template.command.getName());
        }
        
        @Override public @NotNull CustomSuggesterTemplate deserializeFromNetwork(FriendlyByteBuf buf) {
            return new CustomSuggesterTemplate(BY_NAME.get(buf.readUtf()));
        }
        
        @Override public void serializeToJson(CustomSuggesterTemplate template, JsonObject json) {
            json.addProperty("commandapi",template.command.getName());
        }
        
        @Override public @NotNull CustomSuggesterTemplate unpack(CustomSuggester suggester) {
            return new CustomSuggesterTemplate(suggester.command);
        }
        
        public class CustomSuggesterTemplate implements Template<CustomSuggester> {
            
            final CommandAPI command;
            
            CustomSuggesterTemplate(CommandAPI command) {
                this.command = command;
            }
            
            @Override public @NotNull CustomSuggester instantiate(CommandBuildContext context) {
                return new CustomSuggester(this.command);
            }
            
            @Override public @NotNull CustomSuggesterInfo type() {
                return CustomSuggesterInfo.this;
            }
        }
    }
}