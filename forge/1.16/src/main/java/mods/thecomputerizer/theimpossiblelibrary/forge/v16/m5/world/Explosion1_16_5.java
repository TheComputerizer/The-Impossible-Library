package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.world;

import mods.thecomputerizer.theimpossiblelibrary.api.world.ExplosionAPI;
import net.minecraft.world.Explosion;

public class Explosion1_16_5 implements ExplosionAPI<Explosion> {

    private final Explosion explosion;

    public Explosion1_16_5(Explosion explosion) {
        this.explosion = explosion;
    }

    @Override
    public Explosion getExplosion() {
        return this.explosion;
    }
}
