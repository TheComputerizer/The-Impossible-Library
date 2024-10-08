package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.structure;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.structure.StructureRef;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.resource.ResourceLocation1_12_2;
import net.minecraft.util.ResourceLocation;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public class StructureRegistry1_12_2 extends RegistryAPI<StructureRef> {
    
    private static final ResourceLocation1_12_2 REGISTRY_KEY = new ResourceLocation1_12_2(new ResourceLocation("structure"));
    
    public StructureRegistry1_12_2() {
        super(null, StructureRef.class, REGISTRY_KEY);
    }
    
    @Override public ResourceLocationAPI<?> getKey(StructureRef value) {
        return WrapperHelper.wrapResourceLocation(value.getId());
    }
    
    @Override public Collection<ResourceLocationAPI<?>> getKeys() {
        return getValues().stream()
                .map(StructureRef::getId)
                .map(WrapperHelper::wrapResourceLocation)
                .collect(Collectors.toSet());
    }
    
    @Override public StructureRef getValue(ResourceLocationAPI<?> key) {
        return StructureRef.getStructure(key.unwrap());
    }
    
    @Override public Collection<StructureRef> getValues() {
        return StructureRef.getRegisteredStructures();
    }
    
    @Override public boolean hasKey(ResourceLocationAPI<?> key) {
        return Objects.nonNull(getValue(key));
    }
    
    @Override public boolean hasValue(StructureRef value) {
        return StructureRef.isRegistered(value);
    }
}