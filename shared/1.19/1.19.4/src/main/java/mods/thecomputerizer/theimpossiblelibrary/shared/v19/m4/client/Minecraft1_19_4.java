package mods.thecomputerizer.theimpossiblelibrary.shared.v19.m4.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.client.Minecraft1_19;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.m4.client.render.Render1_19_4;
import net.minecraft.client.Minecraft;

public class Minecraft1_19_4 extends Minecraft1_19 {
    
    public static MinecraftAPI<?> getInstance() {
        return new Minecraft1_19_4(Minecraft.getInstance());
    }
    
    public Minecraft1_19_4(Minecraft mc) {
        super(mc,new Render1_19_4());
    }
}