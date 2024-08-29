package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Box;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.DimensionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.PosHelper;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.container.PlayerInventory1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.effect.EffectInstance1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.item.ItemStack1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.resource.ResourceLocation1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.text.Text1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.world.Dimension1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.world.World1_16_5;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class Player1_16_5<P extends PlayerEntity> extends PlayerAPI<P,EntityType<?>> {

    protected Player1_16_5(P player) {
        super(player,player.getType());
    }

    @Override
    public Collection<EffectInstance1_16_5> getActiveEffects() {
        return this.entity.getActiveEffects().stream().map(EffectInstance1_16_5::new).collect(Collectors.toList());
    }

    @Override
    public int getAir() {
        return this.entity.getAirSupply();
    }

    @Override
    public BlockPosAPI<?> getBedPos(DimensionAPI<?> dimension) {
        return null;
    }

    @Override
    public Box getBoundingBox() {
        return Objects.nonNull(this.entity) ? getBoundingBox(this.entity.getBoundingBox()) : null;
    }

    private Box getBoundingBox(AxisAlignedBB box) {
        return new Box(box.minX,box.minY,box.minZ,box.maxX,box.maxY,box.maxZ);
    }

    @Override
    public Dimension1_16_5 getDimension() {
        return new Dimension1_16_5(getWorld(),this.entity.level.dimensionType());
    }

    @Override
    public float getHealth() {
        return this.entity.getHealth();
    }

    @Override
    public PlayerInventory1_16_5 getInventory() {
        return new PlayerInventory1_16_5(this.entity.inventory);
    }

    @Override
    public ItemStack1_16_5 getMainHandStack() {
        return new ItemStack1_16_5(this.entity.getMainHandItem());
    }

    @Override
    public float getMaxHealth() {
        return this.entity.getMaxHealth();
    }

    @Override
    public String getName() {
        return this.entity.getName().getString();
    }

    @Override
    public ItemStack1_16_5 getOffHandStack() {
        return new ItemStack1_16_5(this.entity.getOffhandItem());
    }

    @Override
    public BlockPosAPI<?> getPos() {
        return PosHelper.getPos(this.entity.blockPosition());
    }

    @Override
    public BlockPosAPI<?> getPosRounded() {
        return PosHelper.getPos(new BlockPos((int)(Math.round(x()*2d)/2d),(int)(Math.round(y()*2d)/2d),
                (int)(Math.round(z()*2d)/2d)));
    }

    @Override
    public ResourceLocation1_16_5 getRegistryName() {
        return new ResourceLocation1_16_5(this.type.getRegistryName());
    }

    @Override
    public Entity1_16_5 getRootVehicle() {
        return new Entity1_16_5(this.entity.getRootVehicle());
    }

    @Override
    public UUID getUUID() {
        return this.entity.getUUID();
    }

    @Override
    public @Nullable EntityAPI<?,?> getVehicle() {
        Entity entity = this.entity.getVehicle();
        return Objects.nonNull(entity) ? new Entity1_16_5(entity) : null;
    }

    @Override
    public World1_16_5 getWorld() {
        return new World1_16_5(this.entity.level);
    }

    @Override
    public boolean isAlive() {
        return this.entity.isAlive();
    }

    @Override
    public boolean isAnimal() {
        return false;
    }

    @Override
    public boolean isFishing() {
        return Objects.nonNull(this.entity.fishing) && this.entity.fishing.isInWaterOrBubble();
    }

    @Override
    public boolean isFlying() {
        return this.entity.isFallFlying();
    }

    @Override
    public boolean isLiving() {
        return true;
    }

    @Override
    public boolean isPlayer() {
        return true;
    }

    @Override
    public boolean isOwnedBy(EntityAPI<?,?> owner) {
        return false;
    }

    @Override
    public void sendMessage(TextAPI<?> text, @Nullable UUID uuid) {
        if(Objects.nonNull(this.entity) && text instanceof Text1_16_5)
            this.entity.sendMessage(((Text1_16_5)text).getComponent(),Objects.nonNull(uuid) ? uuid : getUUID());
    }

    @Override
    public void sendStatusMessage(TextAPI<?> text, boolean actionBar) {
        if(Objects.nonNull(this.entity) && text instanceof Text1_16_5)
            this.entity.displayClientMessage(((Text1_16_5)text).getComponent(),actionBar);
    }
    
    @Override
    public void setPosition(double x, double y, double z) {
        this.entity.setPos(x,y,z);
    }

    @Override
    public double x() {
        return this.entity.position().x;
    }

    @Override
    public double y() {
        return this.entity.position().y;
    }

    @Override
    public double z() {
        return this.entity.position().z;
    }
}