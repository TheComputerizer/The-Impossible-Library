package mods.thecomputerizer.theimpossiblelibrary.api.spawn;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonAPI;

import javax.annotation.Nullable;
import java.util.Objects;

public class SpawnHelper {

    public static @Nullable SpawnHelperAPI<?> getAPI() {
        return TILRef.getCommonSubAPI("SpawnHelperAPI",CommonAPI::getSpawnHelper);
    }

    @SuppressWarnings("unchecked")
    public static <E> @Nullable SpawnEntryAPI<E> getSpawnEntry(
            Class<? extends E> clazz, int weight, int maxGroup, int minGroup) {
        SpawnHelperAPI<?> api = getAPI();
        return Objects.nonNull(api) ? ((SpawnHelperAPI<E>)api).getEntry(clazz,weight,maxGroup,minGroup) : null;
    }
}