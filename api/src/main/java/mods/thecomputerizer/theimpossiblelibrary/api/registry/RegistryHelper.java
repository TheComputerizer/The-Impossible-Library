package mods.thecomputerizer.theimpossiblelibrary.api.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;

import javax.annotation.Nullable;
import java.util.Objects;

@SuppressWarnings("unchecked")
public class RegistryHelper {

    public static <R extends RegistryEntryAPI<?>> @Nullable RegistryAPI<R> getBiomeRegistry() {
        RegistryHandlerAPI<?> handler = getHandler();
        return Objects.nonNull(handler) ? (RegistryAPI<R>)handler.getBiomeRegistry() : null;
    }

    public static <R extends RegistryEntryAPI<?>> @Nullable RegistryAPI<R> getBlockRegistry() {
        RegistryHandlerAPI<?> handler = getHandler();
        return Objects.nonNull(handler) ? (RegistryAPI<R>)handler.getBlockRegistry() : null;
    }

    public static <R extends RegistryEntryAPI<?>> @Nullable RegistryAPI<R> getBlockEntityRegistry() {
        RegistryHandlerAPI<?> handler = getHandler();
        return Objects.nonNull(handler) ? (RegistryAPI<R>)handler.getBlockEntityRegistry() : null;
    }

    public static <R extends RegistryEntryAPI<?>> @Nullable RegistryAPI<R> getEntityRegistry() {
        RegistryHandlerAPI<?> handler = getHandler();
        return Objects.nonNull(handler) ? (RegistryAPI<R>)handler.getEntityRegistry() : null;
    }

    public static @Nullable RegistryEntryAPI<?> getEntryIfPresent(
            ResourceLocationAPI<?> registryKey, ResourceLocationAPI<?> entryKey) {
        RegistryHandlerAPI<?> api = getHandler();
        return Objects.nonNull(api) ? api.getEntryIfPresent(registryKey,entryKey) : null;
    }

    public static @Nullable RegistryHandlerAPI<?> getHandler() {
        return TILRef.getCommonSubAPI("RegistryHandlerAPI",CommonAPI::getRegistryHandlerAPI);
    }

    public static <R extends RegistryEntryAPI<?>> @Nullable RegistryAPI<R> getItemRegistry() {
        RegistryHandlerAPI<?> handler = getHandler();
        return Objects.nonNull(handler) ? (RegistryAPI<R>)handler.getItemRegistry() : null;
    }

    public static <R extends RegistryEntryAPI<?>> @Nullable RegistryAPI<R> getSoundRegistry() {
        RegistryHandlerAPI<?> handler = getHandler();
        return Objects.nonNull(handler) ? (RegistryAPI<R>)handler.getSoundRegistry() : null;
    }
}
