package mods.thecomputerizer.theimpossiblelibrary.api.server;

public abstract class MinecraftServerAPI<S> {

    protected MinecraftServerAPI() {}

    protected abstract void registerCommand(CommandAPI cmd);
    protected abstract void executeCommandLiteral(String command);
    protected abstract S getServer();
}