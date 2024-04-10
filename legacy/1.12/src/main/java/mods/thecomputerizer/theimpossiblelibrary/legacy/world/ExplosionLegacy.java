package mods.thecomputerizer.theimpossiblelibrary.legacy.world;

import mods.thecomputerizer.theimpossiblelibrary.api.world.ExplosionAPI;
import net.minecraft.world.Explosion;

public class ExplosionLegacy implements ExplosionAPI<Explosion> {

    private final Explosion explosion;

    public ExplosionLegacy(Explosion explosion) {
        this.explosion = explosion;
    }

    @Override
    public Explosion getExplosion() {
        return this.explosion;
    }
}
