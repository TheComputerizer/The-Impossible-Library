package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.blockentity;

import mods.thecomputerizer.theimpossiblelibrary.api.common.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.blockentity.BlockEntity1_12_2;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

@ParametersAreNonnullByDefault
public class TILTickableBlockEntity1_12_2 extends TILBasicBlockEntity1_12_2 implements ITickable {
    
    protected static final Map<ResourceLocation,Consumer<BlockEntityAPI<?,?>>> onTickMap = new HashMap<>();
    
    private ResourceLocation registryName;
    
    @SuppressWarnings("unused") public TILTickableBlockEntity1_12_2() {}
    
    public TILTickableBlockEntity1_12_2(ResourceLocationAPI<?> api, Consumer<BlockEntityAPI<?,?>> onTick) {
        ResourceLocation name = api.unwrap();
        onTickMap.put(name,onTick);
        this.registryName = name;
    }
    
    @Override public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        this.registryName = new ResourceLocation(tag.getString("id"));
    }
    
    @Override public void update() {
        if(Objects.nonNull(this.registryName) && onTickMap.containsKey(this.registryName))
            onTickMap.get(this.registryName).accept(WrapperHelper.wrapBlockEntity(this));
    }
}