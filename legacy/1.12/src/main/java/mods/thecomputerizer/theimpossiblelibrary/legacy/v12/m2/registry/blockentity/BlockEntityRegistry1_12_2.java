package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.blockentity;

import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistryNamespaced;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev.DEV;
import static org.burningwave.core.assembler.StaticComponentContainer.Fields;

public class BlockEntityRegistry1_12_2 extends RegistryAPI<Class<? extends TileEntity>> {
    
    private static final ResourceLocationAPI<?> REGISTRY_KEY = ResourceHelper.getResource("tile_entity");
    private static final String REGISTRY_FIELD_NAME = DEV ? "REGISTRY" : "field_190562_f";
    public static final RegistryNamespaced<ResourceLocation,Class<? extends TileEntity>> REGISTRY = findRegistry();
    
    public static RegistryNamespaced<ResourceLocation,Class<? extends TileEntity>> findRegistry() {
        return Fields.getStaticDirect(TileEntity.class,REGISTRY_FIELD_NAME);
    }

    public BlockEntityRegistry1_12_2() {
        super(REGISTRY,null,REGISTRY_KEY);
    }

    @Override public ResourceLocationAPI<?> getKey(Class<? extends TileEntity> value) {
        ResourceLocation key = TileEntity.getKey(value);
        return Objects.nonNull(key) ? WrapperHelper.wrapResourceLocation(key) : null;
    }
    
    @Override public Collection<ResourceLocationAPI<?>> getKeys() {
        return REGISTRY.getKeys().stream()
                .map(WrapperHelper::wrapResourceLocation)
                .collect(Collectors.toSet());
    }
    
    @Override public Class<? extends TileEntity> getValue(ResourceLocationAPI<?> key) {
        return REGISTRY.getObject(key.unwrap());
    }
    
    @Override public Collection<Class<? extends TileEntity>> getValues() {
        Set<Class<? extends TileEntity>> values = new HashSet<>();
        for(Class<? extends TileEntity> aClass : REGISTRY) values.add(aClass);
        return values;
    }
    
    @Override public boolean hasKey(ResourceLocationAPI<?> key) {
        return REGISTRY.containsKey(key.unwrap());
    }

    @Override public boolean hasValue(Class<? extends TileEntity> value) {
        for(Class<? extends TileEntity> clazz : REGISTRY)
            if(clazz==value) return true;
        return false;
    }
}