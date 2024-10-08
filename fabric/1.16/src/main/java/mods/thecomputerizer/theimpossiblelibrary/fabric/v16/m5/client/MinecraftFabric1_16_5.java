package mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.client;

import mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.client.font.FontFabric1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.client.Minecraft1_16_5;
import net.minecraft.client.Minecraft;

import java.util.Objects;

public class MinecraftFabric1_16_5 extends Minecraft1_16_5 {
    
    public static MinecraftFabric1_16_5 getInstance() {
        return new MinecraftFabric1_16_5(Minecraft.getInstance());
    }
    
    protected MinecraftFabric1_16_5(Minecraft mc) {
        super(mc,new FontFabric1_16_5());
    }
    
    
    @Override public boolean isLoading() { //TODO This might not catch some edge cases
        return Objects.isNull(this.mc) || (Objects.isNull(this.mc.level) && Objects.isNull(this.mc.screen));
    }
}