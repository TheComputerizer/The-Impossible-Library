package mods.thecomputerizer.theimpossiblelibrary.fabric.core.asm;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import net.fabricmc.loader.api.MappingResolver;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.fabricmc.loader.impl.game.GameProvider;
import net.fabricmc.loader.impl.game.patch.GamePatch;
import net.fabricmc.loader.impl.game.patch.GameTransformer;
import net.fabricmc.loader.impl.launch.FabricLauncher;
import net.fabricmc.loader.impl.launch.FabricLauncherBase;
import org.objectweb.asm.tree.ClassNode;

import javax.annotation.Nullable;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev.DEV;
import static org.burningwave.core.assembler.StaticComponentContainer.Fields;

public class TILFabricCoreModLoader extends GamePatch {
    
    static void announcePatches(Collection<String> classNames) {
        log(TILRef::logInfo,"Recalculated patch map for {}",classNames);
    }
    
    static String findMappedClass(MappingResolver mapper, String name) {
        return mapper.mapClassName("intermediary",name);
    }
    
    static void log(BiConsumer<String,Object[]> logger, String msg, Object ... args) {
        logger.accept("[Multiversion Transformer (Fabric)]: "+msg,args);
    }
    
    public static void patchTransformer(FabricLoaderImpl loader, CoreAPI core) {
        registerEditors(core,DEV ? null : loader.getMappingResolver());
        log(TILRef::logInfo,"Adding coremod transformer patch");
        final GameProvider provider = loader.getGameProvider();
        setupPatch(provider.getEntrypointTransformer(),Fields.getDirect(provider,"gameJars"));
        log(TILRef::logInfo,"Finished adding coremod transformer patch");
    }
    
    static void registerEditors(final CoreAPI core, final @Nullable MappingResolver mapper) {
        Set<CoreEntryPoint> entryPoints = core.getCoreInstances();
        log(TILRef::logInfo,"Initializing {} coremod(s)",entryPoints.size());
        for(CoreEntryPoint entryPoint : entryPoints) {
            String name = entryPoint.getCoreName();
            log(TILRef::logInfo,"Finding targets for {}",name);
            for(String target : entryPoint.classTargets()) {
                String mapped = Objects.nonNull(mapper) ? findMappedClass(mapper,target) : target;
                log(TILRef::logInfo,"[{}]: Adding class target {} (mapped {})",name,target,mapped);
                TILFabricASMTarget.registerEditor(mapped,entryPoint::editClass);
            }
        }
    }
    
    static void setupPatch(final GameTransformer transformer, final List<Path> gameJars) {
        List<GamePatch> patches = new ArrayList<>(Fields.getDirect(transformer,"patches"));
        patches.add(new TILFabricCoreModLoader());
        Fields.setDirect(transformer,"patches",Collections.unmodifiableList(patches));
        //Recalculate the patch map
        Fields.setDirect(transformer,"entrypointsLocated",false);
        transformer.locateEntrypoints(FabricLauncherBase.getLauncher(),gameJars);
        Map<String,byte[]> patchedClasses = Fields.getDirect(transformer,"patchedClasses");
        announcePatches(patchedClasses.keySet());
    }
    
    private TILFabricCoreModLoader() {}
    
    @Override public void process(FabricLauncher launcher, Function<String,ClassNode> source,
            Consumer<ClassNode> emitter) {
        TILFabricASMTarget.runTransformers(source,emitter);
    }
}