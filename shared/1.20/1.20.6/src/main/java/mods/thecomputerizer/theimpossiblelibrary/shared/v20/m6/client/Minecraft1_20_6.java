package mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.client.Minecraft1_20;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.client.render.Render1_20_6;
import net.minecraft.client.Minecraft;

public class Minecraft1_20_6 extends Minecraft1_20 {
    
    public static MinecraftAPI<?> getInstance() {
        return new Minecraft1_20_6(Minecraft.getInstance());
    }
    
    public Minecraft1_20_6(Minecraft mc) {
        super(mc,new Render1_20_6());
    }
}
