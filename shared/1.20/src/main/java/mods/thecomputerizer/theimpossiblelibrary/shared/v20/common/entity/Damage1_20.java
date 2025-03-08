package mods.thecomputerizer.theimpossiblelibrary.shared.v20.common.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.DamageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.BasicWrapped;
import net.minecraft.world.damagesource.DamageSource;

public class Damage1_20 extends DamageAPI {
    
    private final DamageSource source;

    public Damage1_20(Object source, float amount) {
        super(WrapperHelper.wrapEntity(((DamageSource)source).getEntity()),amount);
        this.source = BasicWrapped.cast(source);
    }
    
    @Override public String getName() {
        return this.source.getMsgId();
    }
    
    @SuppressWarnings("unchecked")
    @Override public <S> S getSourceObject() {
        return (S)this.source;
    }
}