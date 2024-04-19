package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.core;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Reference;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ReflectionHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionLoaderAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.Client1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.TILClientEntry1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.Common1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.TILCommonEntry1_12_2;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;

import javax.annotation.Nullable;
import java.io.File;
import java.util.*;
import java.util.Map.Entry;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion.V12;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader.LEGACY;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side.DEDICATED_CLIENT;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side.DEDICATED_SERVER;

public class TILCore1_12_2 extends CoreAPI {

    public static final Reference LEGACY_REF = TILRef.instance(FMLLaunchHandler.side()::isClient,"");
    private static final Map<ModMetadata,Class<? extends CommonEntryPoint>> CLIENT_MODS = new HashMap<>();
    private static final Map<ModMetadata,Class<? extends CommonEntryPoint>> SERVER_MODS = new HashMap<>();

    @SuppressWarnings("unchecked")
    private static @Nullable List<ModContainer> getActiveModContainers() {
        LoadController controller = (LoadController)ReflectionHelper.getFieldInstance(Loader.instance(),Loader.class,"modController");
        if(Objects.isNull(controller)) {
            TILRef.logError("Could not retrieve LoadController instance!");
            return null;
        }
        return (List<ModContainer>)ReflectionHelper.getFieldInstance(controller,LoadController.class,"activeModList");
    }

    public static void getRegisteredMods(List<MultiversionModContainer1_12_2<?>> containers, boolean client) {
        List<ModContainer> activeMods = getActiveModContainers();
        if(Objects.nonNull(activeMods)) {
            for(Entry<ModMetadata,Class<? extends CommonEntryPoint>> entry : (client ? CLIENT_MODS : SERVER_MODS).entrySet()) {
                MultiversionModContainer1_12_2<?> container = setContainer(entry.getKey(),entry.getValue());
                activeMods.add(container);
                containers.add(container);
            }
        } else TILRef.logError("Could not retrieve ModContainer list!");
    }

    private static <E extends CommonEntryPoint> MultiversionModContainer1_12_2<E> setContainer(ModMetadata meta, Class<E> modClass) {
        return new MultiversionModContainer1_12_2<>(meta,modClass);
    }

    private final MultiVersionLoader1_12_2 loader;

    public TILCore1_12_2(Collection<File> mods) {
        super(V12,LEGACY,LEGACY_REF.isClient() ? DEDICATED_CLIENT : DEDICATED_SERVER);
        this.loader = new MultiVersionLoader1_12_2(this,mods);
    }

    @Override
    public void initAPI() {
        TILRef.setAPI(getSide().isClient() ? new Client1_12_2() : new Common1_12_2());
    }

    @Override
    public MultiVersionLoaderAPI getLoader() {
        return this.loader;
    }

    @Override
    public CommonEntryPoint getClientVersionHandler() {
        return this.side.isClient() ? new TILClientEntry1_12_2() : null;
    }

    @Override
    public CommonEntryPoint getCommonVersionHandler() {
        return new TILCommonEntry1_12_2();
    }
}