package mods.thecomputerizer.theimpossiblelibrary.api.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing.UP;

@SuppressWarnings("unused")
public class MaterialHelper {
    
    /**
     Returns air if nothing is found
     */
    public static MaterialAPI<?> getByName(String name) {
        return BlockHelper.getAPI().getMaterialByName(name);
    }
    
    /**
     Returns grass if nothing is found
     */
    public static MaterialColorAPI<?> getColorByName(String name) {
        return BlockHelper.getAPI().getMaterialColorByName(name);
    }

    public static <M> boolean hasCollider(M material) {
        return WrapperHelper.wrapMaterial(material).hasCollider();
    }

    public static <M> boolean isAir(M material) {
        return WrapperHelper.wrapMaterial(material).isAir();
    }

    public static <M> boolean isDestroyedByPiston(M material) {
        return WrapperHelper.wrapMaterial(material).isDestroyedByPiston();
    }
    
    public static <M> boolean isFlammable(M material, WorldAPI<?> world, BlockPosAPI<?> pos) {
        return isFlammable(material,world,pos,UP);
    }

    public static <M> boolean isFlammable(M material, WorldAPI<?> world, BlockPosAPI<?> pos, Facing side) {
        return WrapperHelper.wrapMaterial(material).isFlammable(world,pos,side);
    }

    public static <M> boolean isLiquid(M material) {
        return WrapperHelper.wrapMaterial(material).isLiquid();
    }

    public static <M> boolean isPushable(M material) {
        return WrapperHelper.wrapMaterial(material).isPushable();
    }

    public static <M> boolean isReplaceable(M material) {
        return WrapperHelper.wrapMaterial(material).isReplaceable();
    }

    public static <M> boolean isSolid(M material) {
        return WrapperHelper.wrapMaterial(material).isSolid();
    }

    public static <M> boolean isUnderwater(M material) {
        return WrapperHelper.wrapMaterial(material).isUnderwater();
    }
}