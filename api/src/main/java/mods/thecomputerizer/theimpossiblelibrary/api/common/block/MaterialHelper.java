package mods.thecomputerizer.theimpossiblelibrary.api.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;

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

    public static <M> boolean isFlammable(M material) {
        return WrapperHelper.wrapMaterial(material).isFlammable();
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