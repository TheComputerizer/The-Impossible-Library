package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.resource.ResourceLocation1_12_2;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import java.util.Objects;

public class TileEntityRegistry1_12_2 extends RegistryAPI<Class<? extends TileEntity>> {

    private static final ResourceLocation1_12_2 REGISTRY_KEY = new ResourceLocation1_12_2(new ResourceLocation("tile_entity"));

    public TileEntityRegistry1_12_2() {
        super(null,REGISTRY_KEY);
    }

    @Override
    public ResourceLocationAPI<?> getKey(Class<? extends TileEntity> value) {
        ResourceLocation key = TileEntity.getKey(value);
        return Objects.nonNull(key) ? new ResourceLocation1_12_2(key) : null;
    }

    @Override
    public Class<? extends TileEntity> getValue(ResourceLocationAPI<?> key) {
        return TileEntity.REGISTRY.getObject(((ResourceLocation1_12_2)key).getInstance());
    }

    @Override
    public boolean hasKey(ResourceLocationAPI<?> key) {
        return TileEntity.REGISTRY.containsKey(((ResourceLocation1_12_2)key).getInstance());
    }

    @Override
    public boolean hasValue(Class<? extends TileEntity> value) {
        for(Class<? extends TileEntity> clazz : TileEntity.REGISTRY)
            if(clazz==value) return true;
        return false;
    }
}
