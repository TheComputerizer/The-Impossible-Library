package mods.thecomputerizer.theimpossiblelibrary.api.common.event.tick;

public interface TickableEventWrapper {

    TickPhase getPhase();
    TickType getTickType();

    enum TickPhase { START, END, DEFAULT }
    enum TickType { CLIENT, PLAYER, RENDER, SERVER, WORLD }
}