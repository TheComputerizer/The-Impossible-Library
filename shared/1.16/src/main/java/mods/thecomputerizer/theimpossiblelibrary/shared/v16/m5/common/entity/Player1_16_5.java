package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.common.container.PlayerInventoryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.effect.EffectInstanceAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Box;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.DimensionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.text.Text1_16_5;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class Player1_16_5<P extends PlayerEntity> extends PlayerAPI<P,EntityType<?>> {

    @SuppressWarnings("unchecked")
    protected Player1_16_5(Object player) {
        super((P)player,(((P)player).getType()));
    }
    
    @Override public Collection<EffectInstanceAPI<?>> getActiveEffects() {
        return this.entity.getActiveEffects().stream().map(WrapperHelper::wrapEffectInstance).collect(Collectors.toList());
    }
    
    @Override public int getAir() {
        return this.entity.getAirSupply();
    }
    
    @Override public BlockPosAPI<?> getBedPos(DimensionAPI<?> dimension) { //TODO 1.16.5 dimension specific respawn pos
        return null;
    }
    
    @Override public Box getBoundingBox() {
        return Objects.nonNull(this.entity) ? getBoundingBox(this.entity.getBoundingBox()) : null;
    }
    
    protected Box getBoundingBox(AxisAlignedBB box) {
        return new Box(box.minX,box.minY,box.minZ,box.maxX,box.maxY,box.maxZ);
    }

    @Override public DimensionAPI<?> getDimension() {
        return WrapperHelper.wrapDimension(getWorld(),this.entity.level.dimensionType());
    }
    
    @Override public float getHealth() {
        return this.entity.getHealth();
    }
    
    @Override public PlayerInventoryAPI<?> getInventory() {
        return WrapperHelper.wrapPlayerInventory(this.entity.inventory);
    }
    
    @Override public ItemStackAPI<?> getMainHandStack() {
        return WrapperHelper.wrapItemStack(this.entity.getMainHandItem());
    }
    
    @Override public float getMaxHealth() {
        return this.entity.getMaxHealth();
    }
    
    @Override public String getName() {
        return this.entity.getName().getString();
    }
    
    @Override public ItemStackAPI<?> getOffHandStack() {
        return WrapperHelper.wrapItemStack(this.entity.getOffhandItem());
    }
    
    @Override public BlockPosAPI<?> getPos() {
        return WrapperHelper.wrapPosition(this.entity.blockPosition());
    }
    
    @Override public EntityAPI<?,?> getRootVehicle() {
        return WrapperHelper.wrapEntity(this.entity.getRootVehicle());
    }
    
    @Override public UUID getUUID() {
        return this.entity.getUUID();
    }
    
    @Override public @Nullable EntityAPI<?,?> getVehicle() {
        Entity entity = this.entity.getVehicle();
        return Objects.nonNull(entity) ? WrapperHelper.wrapEntity(entity) : null;
    }
    
    @Override public WorldAPI<?> getWorld() {
        return WrapperHelper.wrapWorld(this.entity.level);
    }
    
    @Override public boolean isAlive() {
        return this.entity.isAlive();
    }

    @Override public boolean isAnimal() {
        return false;
    }
    
    @Override public boolean isFishing() {
        return Objects.nonNull(this.entity.fishing) && this.entity.fishing.isInWaterOrBubble();
    }
    
    @Override public boolean isFlying() {
        return this.entity.isFallFlying();
    }

    @Override public boolean isLiving() {
        return true;
    }

    @Override public boolean isPlayer() {
        return true;
    }

    @Override public boolean isOwnedBy(EntityAPI<?,?> owner) {
        return false;
    }
    
    @Override public void sendMessage(TextAPI<?> text, @Nullable UUID uuid) {
        if(Objects.nonNull(this.entity) && text instanceof Text1_16_5)
            this.entity.sendMessage(text.getAsComponent(),Objects.nonNull(uuid) ? uuid : getUUID());
    }
    
    @Override public void sendStatusMessage(TextAPI<?> text, boolean actionBar) {
        if(Objects.nonNull(this.entity) && text instanceof Text1_16_5)
            this.entity.displayClientMessage(text.getAsComponent(),actionBar);
    }
    
    @Override public void setPosition(double x, double y, double z) {
        this.entity.setPos(x,y,z);
    }
    
    @Override public double x() {
        return this.entity.position().x;
    }
    
    @Override public double y() {
        return this.entity.position().y;
    }
    
    @Override public double z() {
        return this.entity.position().z;
    }
}