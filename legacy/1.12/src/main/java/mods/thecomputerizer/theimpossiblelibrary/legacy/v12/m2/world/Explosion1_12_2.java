package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.world;

import mods.thecomputerizer.theimpossiblelibrary.api.world.ExplosionAPI;
import net.minecraft.world.Explosion;

public class Explosion1_12_2 implements ExplosionAPI<Explosion> {

    private final Explosion explosion;

    public Explosion1_12_2(Explosion explosion) {
        this.explosion = explosion;
    }

    @Override
    public Explosion getExplosion() {
        return this.explosion;
    }
}
