package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.server;

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
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreStateAccessor;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks;
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
import net.minecraft.commands.synchronization.ArgumentSerializer;
import net.minecraft.commands.synchronization.ArgumentTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.concurrent.CompletableFuture;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;

public class WrappedCommand1_18_2 implements CoreStateAccessor {
    
    private static final String ARGUMENT_TYPE_ENTRY = "net.minecraft.commands.synchronization.ArgumentTypes$Entry";
    private static final Map<String,CommandAPI> BY_NAME = new HashMap<>();
    private static final String FIELD_BY_CLASS = NAMED_ENV ? "BY_CLASS" : (SRG_ENV ? "f_121583_" : "field_10921");
    private static final String FIELD_BY_NAME = NAMED_ENV ? "BY_NAME" : (SRG_ENV ? "f_121584_" : "field_10922");
    private static final CustomSuggesterInfo SERIALIZER = new CustomSuggesterInfo();

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
        BY_NAME.put(wrapped.getName(),wrapped);
    }
    
    public static void registerArgType() {
        registerArgType(new ResourceLocation(MODID,"custom_suggestor"));
    }
    
    private static <E> void registerArgType(ResourceLocation name) {
        Map<Class<?>,E> byClass = Hacks.getFieldStaticDirect(ArgumentTypes.class,FIELD_BY_CLASS);
        Map<ResourceLocation,E> byName = Hacks.getFieldStaticDirect(ArgumentTypes.class,FIELD_BY_NAME);
        E entry = Hacks.construct(ARGUMENT_TYPE_ENTRY,SERIALIZER,name);
        if(Objects.nonNull(byClass)) byClass.put(CustomSuggester.class,entry);
        if(Objects.nonNull(byName)) byName.put(name,entry);
    }
    
    public record CustomSuggester(CommandAPI command) implements ArgumentType<String> {
        
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
    
    public static final class CustomSuggesterInfo implements ArgumentSerializer<CustomSuggester> {
        
        @Override public void serializeToNetwork(CustomSuggester type, FriendlyByteBuf buf) {
            buf.writeUtf(type.command.getName());
        }
        
        @Override public @NotNull CustomSuggester deserializeFromNetwork(FriendlyByteBuf buf) {
            return new CustomSuggester(BY_NAME.get(buf.readUtf()));
        }
        
        @Override public void serializeToJson(CustomSuggester type, JsonObject json) {
            json.addProperty("commandapi",type.command.getName());
        }
    }
}