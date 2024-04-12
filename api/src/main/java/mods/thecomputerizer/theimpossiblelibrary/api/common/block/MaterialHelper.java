package mods.thecomputerizer.theimpossiblelibrary.api.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.WrapperHelper;

import java.util.Objects;

public class MaterialHelper {

    public static <M> boolean hasCollider(M material) {
        MaterialAPI<M> api = WrapperHelper.wrapMaterial(material);
        return Objects.nonNull(api) && api.hasCollider();
    }

    public static <M> boolean isAir(M material) {
        MaterialAPI<M> api = WrapperHelper.wrapMaterial(material);
        return Objects.nonNull(api) && api.isAir();
    }

    public static <M> boolean isDestroyedByPiston(M material) {
        MaterialAPI<M> api = WrapperHelper.wrapMaterial(material);
        return Objects.nonNull(api) && api.isDestroyedByPiston();
    }

    public static <M> boolean isFlammable(M material) {
        MaterialAPI<M> api = WrapperHelper.wrapMaterial(material);
        return Objects.nonNull(api) && api.isFlammable();
    }

    public static <M> boolean isLiquid(M material) {
        MaterialAPI<M> api = WrapperHelper.wrapMaterial(material);
        return Objects.nonNull(api) && api.isLiquid();
    }

    public static <M> boolean isPushable(M material) {
        MaterialAPI<M> api = WrapperHelper.wrapMaterial(material);
        return Objects.nonNull(api) && api.isPushable();
    }

    public static <M> boolean isReplaceable(M material) {
        MaterialAPI<M> api = WrapperHelper.wrapMaterial(material);
        return Objects.nonNull(api) && api.isReplaceable();
    }

    public static <M> boolean isSolid(M material) {
        MaterialAPI<M> api = WrapperHelper.wrapMaterial(material);
        return Objects.nonNull(api) && api.isSolid();
    }

    public static <M> boolean isUnderwater(M material) {
        MaterialAPI<M> api = WrapperHelper.wrapMaterial(material);
        return Objects.nonNull(api) && api.isUnderwater();
    }
}