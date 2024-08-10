package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.world;

import mods.thecomputerizer.theimpossiblelibrary.api.common.biome.BiomeAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.structure.StructureAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Box;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.DimensionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.biome.Biome1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.block.BlockState1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.blockentity.BlockEntity1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.entity.Entity1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.entity.Living1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.item.Item1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.item.ItemStack1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.structure.Structure1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.structure.StructureRef;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import org.joml.Vector3d;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Consumer;

public class World1_12_2 extends WorldAPI<World> {

    public World1_12_2(World world) {
        super(world);
    }

    @Override
    public boolean canSnowAt(BlockPosAPI<?> pos) {
        return this.world.canSnowAt(((BlockPos1_12_2)pos).getPos(),false);
    }

    @Override
    public BiomeAPI<?> getBiomeAt(BlockPosAPI<?> pos) {
        return new Biome1_12_2(this.world.getBiome(((BlockPos1_12_2)pos).getPos()));
    }

    @Override
    public Collection<BlockEntityAPI<?,?>> getBlockEntitiesInBox(Box box) {
        List<BlockEntityAPI<?,?>> entities = new ArrayList<>();
        if(this.world instanceof World) {
            Iterator<TileEntity> tiles = this.world.loadedTileEntityList.iterator();
            while(tiles.hasNext()) {
                synchronized(tiles) { //Double layer insurance against cmod stupidity
                    TileEntity tile = tiles.next();
                    BlockPos pos = tile.getPos();
                    if(box.isInside(pos.getX(),pos.getY(),pos.getZ())) entities.add(new BlockEntity1_12_2(tile));
                }
            }
        }
        return entities;
    }

    @Override
    public @Nullable BlockEntityAPI<?,?> getBlockEntityAt(BlockPosAPI<?> pos) {
        TileEntity tile = this.world.getTileEntity(((BlockPos1_12_2)pos).getPos());
        return Objects.nonNull(tile) ? new BlockEntity1_12_2(tile) : null;
    }

    @Override
    public int getDayNumber() {
        return (int)((double)getTimeTotal()/24000d);
    }

    @Override
    public int getDifficultyOrdinal() {
        if(this.world.getWorldInfo().isHardcoreModeEnabled()) return 4;
        switch(this.world.getDifficulty()) {
            case PEACEFUL: return 0;
            case EASY: return 1;
            case NORMAL: return 2;
            case HARD: return 3;
            default: return -1; //unreachable
        }
    }

    @Override
    public DimensionAPI<?> getDimension() {
        return new Dimension1_12_2(this);
    }

    @Override
    public List<EntityAPI<?,?>> getEntitiesInBox(Box box) {
        return getEntitiesInBox(new AxisAlignedBB(box.min.x,box.min.y,box.min.z,box.max.x,box.max.y,box.max.z));
    }

    public List<EntityAPI<?,?>> getEntitiesInBox(AxisAlignedBB box) {
        List<EntityAPI<?,?>> entities = new ArrayList<>();
        for(Entity entity : this.world.getEntitiesWithinAABB(Entity.class,box)) entities.add(new Entity1_12_2(entity));
        return entities;
    }

    @Override
    public int getLightBlock(BlockPosAPI<?> pos) {
        return this.world.getLightFor(EnumSkyBlock.BLOCK,((BlockPos1_12_2)pos).getPos());
    }

    @Override
    public int getLightSky(BlockPosAPI<?> pos) {
        return this.world.getLightFor(EnumSkyBlock.SKY,((BlockPos1_12_2)pos).getPos());
    }

    @Override
    public int getLightTotal(BlockPosAPI<?> pos) {
        return this.world.getLight(((BlockPos1_12_2)pos).getPos());
    }

    @Override
    public List<LivingEntityAPI<?,?>> getLivingInBox(Box box) {
        return getLivingInBox(new AxisAlignedBB(box.min.x,box.min.y,box.min.z,box.max.x,box.max.y,box.max.z));
    }

    public List<LivingEntityAPI<?,?>> getLivingInBox(AxisAlignedBB box) {
        List<LivingEntityAPI<?,?>> entities = new ArrayList<>();
        for(EntityLivingBase entity : this.world.getEntitiesWithinAABB(EntityLivingBase.class,box))
            entities.add(new Living1_12_2(entity));
        return entities;
    }

    @Override
    public int getMoonPhase() {
        return this.world.getMoonPhase();
    }

    @Override
    public BlockStateAPI<?> getStateAt(BlockPosAPI<?> pos) {
        return new BlockState1_12_2(this.world.getBlockState(((BlockPos1_12_2)pos).getPos()));
    }

    @Override
    public StructureAPI<?> getStructureAt(BlockPosAPI<?> pos) {
        if(this.world instanceof WorldServer) {
            StructureRef ref = StructureRef.getStructureAt((WorldServer)this.world,((BlockPos1_12_2)pos).getPos());
            return Objects.nonNull(ref) ? new Structure1_12_2(ref) : null;
        }
        return null;
    }

    @Override
    public long getTimeDay() {
        return getTimeTotal()%24000L;
    }

    @Override
    public long getTimeTotal() {
        return this.world.getWorldTime();
    }

    @Override
    public boolean isClient() {
        return this.world.isRemote;
    }

    @Override
    public boolean isDaytime() {
        return getTimeDay()<13000L;
    }

    @Override
    public boolean isRaining() {
        return this.world.isRaining();
    }

    @Override
    public boolean isSkyVisible(BlockPosAPI<?> pos) {
        return this.world.canBlockSeeSky(((BlockPos1_12_2)pos).getPos());
    }

    @Override
    public boolean isStorming() {
        return this.world.isThundering();
    }

    @Override
    public boolean isSunrise() {
        return getTimeDay()>=23000L;
    }

    @Override
    public boolean isSunset() {
        long time = getTimeDay();
        return time>=12000L && time<13000L;
    }
    
    @Override public void setState(BlockPosAPI<?> pos, BlockStateAPI<?> state) {
        this.world.setBlockState(((BlockPos1_12_2)pos).getPos(),((BlockState1_12_2)state).getState());
    }
    
    @Override public void spawnEntity(EntityAPI<?,?> entity, @Nullable Consumer<EntityAPI<?,?>> onSpawn) {
        if(!this.world.isRemote) {
            TILDev.logInfo("Spawning entity {} of type {}",entity.getEntity(),entity.getType());
            this.world.spawnEntity(((Entity1_12_2)entity).getEntity());
            if(Objects.nonNull(onSpawn)) {
                TILDev.logInfo("Accepting onSpawn consumer");
                onSpawn.accept(entity);
            }
        }
    }
    
    @Override public void spawnItem(ItemStackAPI<?> api, Vector3d pos, @Nullable Consumer<EntityAPI<?,?>> onSpawn) {
        if(!this.world.isRemote) {
            ItemStack stack = ((ItemStack1_12_2)api).getStack();
            TILDev.logInfo("Spawning ItemStack at {} from {} with count {} and tag {}",pos,stack.getItem().getRegistryName(),stack.getCount(),stack.getTagCompound());
            EntityItem item = new EntityItem(this.world,pos.x,pos.y,pos.z,stack);
            item.setDefaultPickupDelay();
            spawnEntity(new Entity1_12_2(item),onSpawn);
        }
    }
    
    @Override public void spawnItem(ItemAPI<?> api, Vector3d pos, @Nullable Consumer<ItemStackAPI<?>> beforeSpawn,
            @Nullable Consumer<EntityAPI<?,?>> onSpawn) {
        if(!this.world.isRemote) {
            TILDev.logInfo("Spawning item {}",api.getRegistryName());
            ItemStack stack = new ItemStack(((Item1_12_2)api).getValue());
            ItemStack1_12_2 stackAPI = new ItemStack1_12_2(stack);
            if(Objects.nonNull(beforeSpawn)) {
                TILDev.logInfo("Accepting beforeSpawn consumer");
                beforeSpawn.accept(stackAPI);
            }
            spawnItem(stackAPI,pos,onSpawn);
        }
    }
}
