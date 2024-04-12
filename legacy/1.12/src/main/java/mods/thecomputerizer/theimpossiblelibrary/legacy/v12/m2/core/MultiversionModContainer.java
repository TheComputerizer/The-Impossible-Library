package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.core;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;

public class MultiversionModContainer<E extends CommonEntryPoint> extends DummyModContainer {

    private final Class<E> modClass;
    private E instance;

    public MultiversionModContainer(ModMetadata meta, Class<E> modClass) {
        super(meta);
        this.modClass = modClass;
    }

    public void construct(FMLConstructionEvent event) {
        try {
            this.instance = this.modClass.newInstance();
        } catch(InstantiationException | IllegalAccessException ex) {
            TILCore1_12_2.LEGACY_REF.logError("Failed to construct instance of mod `{}`!",this.getMod());
        }
    }

    @Override
    public Object getMod() {
        return this.instance;
    }

    @Override
    public boolean matches(Object mod) {
        return mod==this.instance;
    }
}
