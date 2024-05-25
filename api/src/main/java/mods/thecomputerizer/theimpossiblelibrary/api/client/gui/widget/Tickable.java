package mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget;

public interface Tickable {
    
    boolean isActivelyTicking();
    void onTick();
}