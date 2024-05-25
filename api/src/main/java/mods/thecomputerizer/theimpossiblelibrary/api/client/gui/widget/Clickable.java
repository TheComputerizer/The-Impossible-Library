package mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget;

public interface Clickable {
    
    boolean onLeftClick(double x, double y);
    boolean onRightClick(double x, double y);
    void playLeftClickSound();
    void playRightClickSound();
}