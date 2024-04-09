package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.world;

import mods.thecomputerizer.theimpossiblelibrary.api.common.world.ExplosionAPI;
import net.minecraft.world.Explosion;

public class ExplosionForge implements ExplosionAPI<Explosion> {

    private final Explosion explosion;

    public ExplosionForge(Explosion explosion) {
        this.explosion = explosion;
    }

    @Override
    public Explosion getExplosion() {
        return this.explosion;
    }
}
