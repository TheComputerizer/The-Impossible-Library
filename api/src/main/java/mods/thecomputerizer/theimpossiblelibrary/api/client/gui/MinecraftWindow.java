package mods.thecomputerizer.theimpossiblelibrary.api.client.gui;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientHelper;

@SuppressWarnings("unused") @Getter
public class MinecraftWindow {

    private final double displayWidth;
    private final double displayHeight;
    private final double width;
    private final double height;
    private final int scale;

    public MinecraftWindow(double width, double height, int scale) {
        this.displayWidth = ClientHelper.getDisplayWidth();
        this.displayHeight = ClientHelper.getDisplayHeight();
        this.width = width;
        this.height = height;
        this.scale = scale;
    }

    public float getHeightF() {
        return (float)this.height;
    }
    
    public double getHeightScale() {
        return this.height/this.width;
    }

    public float getWidthF() {
        return (float)this.width;
    }
    
    public double getSmallerScale() {
        return isWide() ? getHeightScale() : this.width/this.height;
    }
    
    public boolean isWide() {
        return this.width>=this.height;
    }
}