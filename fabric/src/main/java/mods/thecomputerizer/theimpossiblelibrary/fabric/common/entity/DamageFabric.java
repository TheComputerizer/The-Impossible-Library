package mods.thecomputerizer.theimpossiblelibrary.fabric.common.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.DamageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import net.minecraft.world.damagesource.DamageSource;
import org.jetbrains.annotations.Nullable;

public class DamageFabric extends DamageAPI {
    
    private final DamageSource source;
    
    public DamageFabric(DamageSource source, @Nullable EntityAPI<?,?> entity, float amount) {
        super(entity,amount);
        this.source = source;
    }
    
    @Override
    public String getName() {
        return this.source.msgId;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <S> S getSourceObject() {
        return (S)this.source;
    }
}
