package mods.thecomputerizer.theimpossiblelibrary.api.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;

import javax.annotation.Nullable;
import java.util.Objects;

@SuppressWarnings("unchecked")
public class RegistryHelper {

    public static <R extends RegistryEntryAPI<?>> @Nullable RegistryAPI<R> getBiomeRegistry() {
        RegistryHandlerAPI handler = getHandler();
        return Objects.nonNull(handler) ? (RegistryAPI<R>)handler.getBiomeRegistry() : null;
    }

    public static <R extends RegistryEntryAPI<?>> @Nullable RegistryAPI<R> getBlockRegistry() {
        RegistryHandlerAPI handler = getHandler();
        return Objects.nonNull(handler) ? (RegistryAPI<R>)handler.getBlockRegistry() : null;
    }

    public static <R extends RegistryEntryAPI<?>> @Nullable RegistryAPI<R> getBlockEntityRegistry() {
        RegistryHandlerAPI handler = getHandler();
        return Objects.nonNull(handler) ? (RegistryAPI<R>)handler.getBlockEntityRegistry() : null;
    }

    public static <R extends RegistryEntryAPI<?>> @Nullable RegistryAPI<R> getEffectRegistry() {
        RegistryHandlerAPI handler = getHandler();
        return Objects.nonNull(handler) ? (RegistryAPI<R>)handler.getEffectRegistry() : null;
    }

    public static <R extends RegistryEntryAPI<?>> @Nullable RegistryAPI<R> getEntityRegistry() {
        RegistryHandlerAPI handler = getHandler();
        return Objects.nonNull(handler) ? (RegistryAPI<R>)handler.getEntityRegistry() : null;
    }

    public static <E extends RegistryEntryAPI<?>> @Nullable E getEntryIfPresent(
            ResourceLocationAPI<?> registryKey, ResourceLocationAPI<?> entryKey) {
        RegistryHandlerAPI api = getHandler();
        return Objects.nonNull(api) ? api.getEntryIfPresent(registryKey,entryKey) : null;
    }

    public static @Nullable RegistryHandlerAPI getHandler() {
        return TILRef.getCommonSubAPI(CommonAPI::getRegistryHandler);
    }

    public static <R extends RegistryEntryAPI<?>> @Nullable RegistryAPI<R> getItemRegistry() {
        RegistryHandlerAPI handler = getHandler();
        return Objects.nonNull(handler) ? (RegistryAPI<R>)handler.getItemRegistry() : null;
    }

    public static <R extends RegistryEntryAPI<?>> @Nullable RegistryAPI<R> getPotionRegistry() {
        RegistryHandlerAPI handler = getHandler();
        return Objects.nonNull(handler) ? (RegistryAPI<R>)handler.getPotionRegistry() : null;
    }

    public static @Nullable RegistryAPI<?> getRegistry(ResourceLocationAPI<?> key) {
        RegistryHandlerAPI handler = getHandler();
        return Objects.nonNull(handler) ? handler.getRegistry(key) : null;
    }

    public static @Nullable RegistryAPI<?> getRegistry(Class<?> type) {
        RegistryHandlerAPI handler = getHandler();
        return Objects.nonNull(handler) ? handler.getRegistry(type) : null;
    }

    public static <R extends RegistryEntryAPI<?>> @Nullable RegistryAPI<R> getSoundRegistry() {
        RegistryHandlerAPI handler = getHandler();
        return Objects.nonNull(handler) ? (RegistryAPI<R>)handler.getSoundRegistry() : null;
    }
}
