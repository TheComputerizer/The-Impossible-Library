package mods.thecomputerizer.theimpossiblelibrary.api.client.gui;

import lombok.Getter;

@Getter
public class MinecraftWindow {

    private final double width;
    private final double height;
    private final int scale;

    public MinecraftWindow(double width, double height, int scale) {
        this.width = width;
        this.height = height;
        this.scale = scale;
    }

    public float getHeightF() {
        return (float)this.height;
    }

    public float getWidthF() {
        return (float)this.width;
    }
}
