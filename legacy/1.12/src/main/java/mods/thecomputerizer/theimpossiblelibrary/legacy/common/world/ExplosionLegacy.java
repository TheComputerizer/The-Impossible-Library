package mods.thecomputerizer.theimpossiblelibrary.legacy.common.world;

import mods.thecomputerizer.theimpossiblelibrary.api.common.world.ExplosionAPI;
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
