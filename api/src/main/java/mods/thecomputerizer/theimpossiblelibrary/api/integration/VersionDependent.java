package mods.thecomputerizer.theimpossiblelibrary.api.integration;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

public interface VersionDependent {

    boolean isCompatible(ModLoader loader, Side side, GameVersion version);
    
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    default <T> @Nullable T orElseNull(@Nullable Optional<T> optional) {
        return Objects.nonNull(optional) ? optional.orElse(null) : null;
    }
}