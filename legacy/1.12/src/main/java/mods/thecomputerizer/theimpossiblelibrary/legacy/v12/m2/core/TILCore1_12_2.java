package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.core;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.MultiLoaderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Reference;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Misc;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

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
        Field instanceField = Misc.getField(Loader.class,"instance");
        if(Objects.isNull(instanceField)) {
            TILRef.logError("Could not locate Loader instance field!");
            return null;
        }
        try {
            instanceField.setAccessible(true);
        } catch(Exception ex) {
            TILRef.logError("Could not make Loader instance field accessible!",ex);
            return null;
        }
        Loader loader = (Loader)Misc.getFieldInstance(null,instanceField);
        if(Objects.isNull(loader)) {
            TILRef.logError("Could not retrieve Loader instance!");
            return null;
        }
        Field controllerField = Misc.getField(Loader.class,"modController");
        if(Objects.isNull(controllerField)) {
            TILRef.logError("Could not locate Loader modController field!");
            return null;
        }
        try {
            controllerField.setAccessible(true);
        } catch(Exception ex) {
            TILRef.logError("Could not make Loader modController field accessible!",ex);
            return null;
        }
        LoadController controller = (LoadController)Misc.getFieldInstance(loader,controllerField);
        if(Objects.isNull(controller)) {
            TILRef.logError("Could not retrieve LoadController instance!");
            return null;
        }
        Field containersField = Misc.getField(LoadController.class,"activeModList");
        if(Objects.isNull(containersField)) {
            TILRef.logError("Could not locate LoadController activeModList field!");
            return null;
        }
        try {
            containersField.setAccessible(true);
        } catch(Exception ex) {
            TILRef.logError("Could not make LoadController activeModList field accessible!",ex);
        }
        return (List<ModContainer>)Misc.getFieldInstance(controller,containersField);
    }

    public static void getRegisteredMods(List<MultiversionModContainer<?>> containers, boolean client) {
        List<ModContainer> activeMods = getActiveModContainers();
        if(Objects.nonNull(activeMods)) {
            for(Entry<ModMetadata,Class<? extends CommonEntryPoint>> entry : (client ? CLIENT_MODS : SERVER_MODS).entrySet()) {
                MultiversionModContainer<?> container = setContainer(entry.getKey(),entry.getValue());
                activeMods.add(container);
                containers.add(container);
            }
        }
    }

    public static void registerMultiversionMod(
            ModMetadata meta, Class<? extends CommonEntryPoint> modClass, boolean client, boolean server) {
        if(client) CLIENT_MODS.put(meta,modClass);
        if(server) SERVER_MODS.put(meta,modClass);
    }

    private static <E extends CommonEntryPoint> MultiversionModContainer<E> setContainer(ModMetadata meta, Class<E> modClass) {
        return new MultiversionModContainer<>(meta,modClass);
    }

    private final MultiLoader1_12_2 loader;

    public TILCore1_12_2() {
        super(V12,LEGACY,LEGACY_REF.isClient() ? DEDICATED_CLIENT : DEDICATED_SERVER);
        this.loader = new MultiLoader1_12_2(getSide());
    }

    @Override
    public MultiLoaderAPI getLoader() {
        return this.loader;
    }
}