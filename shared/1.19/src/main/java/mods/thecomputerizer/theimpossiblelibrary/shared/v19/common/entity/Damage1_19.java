package mods.thecomputerizer.theimpossiblelibrary.shared.v19.common.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.util.GenericUtils;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.DamageAPI;
import net.minecraft.world.damagesource.DamageSource;

public class Damage1_19 extends DamageAPI {
    
    private final DamageSource source;

    public Damage1_19(Object source, float amount) {
        super(WrapperHelper.wrapEntity(((DamageSource)source).getEntity()),amount);
        this.source = GenericUtils.cast(source);
    }
    
    @Override public String getName() {
        return this.source.msgId;
    }
    
    @SuppressWarnings("unchecked")
    @Override public <S> S getSourceObject() {
        return (S)this.source;
    }
}