package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client;

import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.font.FontForge1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.client.Minecraft1_16_5;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.client.ClientModLoader;

import java.util.Objects;

public class MinecraftForge1_16_5 extends Minecraft1_16_5 {
    
    public static MinecraftForge1_16_5 getInstance() {
        return new MinecraftForge1_16_5(Minecraft.getInstance());
    }
    
    private MinecraftForge1_16_5(Minecraft mc) {
        super(mc,new FontForge1_16_5());
    }
    
    @Override public boolean isLoading() {
        return Objects.isNull(this.mc) || ClientModLoader.isLoading();
    }
}