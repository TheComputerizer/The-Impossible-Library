package mods.thecomputerizer.theimpossiblelibrary.api.common.event;

public interface TickableEventWrapper {

    TickPhase getPhase();
    TickType getTickType();

    default boolean isPhase(TickPhase phase) {
        return getPhase()==phase;
    }

    default boolean isType(TickType type) {
        return getTickType()==type;
    }

    enum TickPhase { START, END, DEFAULT }
    enum TickType { CLIENT, CUSTOM, PLAYER, RENDER, SERVER, WORLD }
}