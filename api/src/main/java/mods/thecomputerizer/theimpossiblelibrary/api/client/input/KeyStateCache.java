package mods.thecomputerizer.theimpossiblelibrary.api.client.input;

import lombok.Getter;

@Getter
public class KeyStateCache {
    
    private final boolean holdingAlt;
    private final boolean holdingCtrl;
    private final boolean holdingShift;
    
    public KeyStateCache(boolean alt, boolean ctrl, boolean shift) {
        this.holdingAlt = alt;
        this.holdingCtrl = ctrl;
        this.holdingShift = shift;
    }
}