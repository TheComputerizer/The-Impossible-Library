package mods.thecomputerizer.theimpossiblelibrary.fabric.core.asm;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMHelper;
import net.fabricmc.loader.api.MappingResolver;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.fabricmc.loader.impl.game.GameProvider;
import net.fabricmc.loader.impl.game.patch.GamePatch;
import net.fabricmc.loader.impl.game.patch.GameTransformer;
import net.fabricmc.loader.impl.launch.FabricLauncher;
import net.fabricmc.loader.impl.util.SimpleClassPath;
import org.objectweb.asm.tree.ClassNode;

import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev.DEV;
import static org.burningwave.core.assembler.StaticComponentContainer.Fields;
import static org.burningwave.core.assembler.StaticComponentContainer.Methods;

public class TILFabricCoreModLoader extends GamePatch {
    
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
        patches.add(new TILFabricCoreModLoader(transformer,gameJars));
        Fields.setDirect(transformer,"patches",Collections.unmodifiableList(patches));
    }
    
    TILFabricCoreModLoader(final GameTransformer transformer, final List<Path> paths) {
        Map<String,byte[]> patchedClasses = Fields.getDirect(transformer,"patchedClasses");
        if(Objects.nonNull(patchedClasses)) patchedClasses.putAll(inject(transformer,paths));
    }
    
    @Nullable ClassNode findClass(final GameTransformer transformer, final List<Path> paths, final String className) {
        try(SimpleClassPath classPath = new SimpleClassPath(paths)) {
            return Methods.invokeDirect(transformer,"readClassNode",classPath,className);
        } catch(IOException ex) {
            TILRef.logError("Failed to read paths as SimpleClassPath {}",paths,ex);
        }
        return null;
    }
    
    Map<String,byte[]> inject(final GameTransformer transformer, final List<Path> paths) {
        Map<String,byte[]> transformed = new HashMap<>();
        Consumer<ClassNode> emitter = node -> {
            String className = node.name.replace('/','.');
            transformed.put(className,ASMHelper.toBytes(node));
        };
        TILFabricASMTarget.runTransformers(className -> findClass(transformer,paths,className),emitter);
        return transformed;
    }
    
    @Override public void process(FabricLauncher launcher, Function<String,ClassNode> source,
            Consumer<ClassNode> emitter) {
        TILFabricASMTarget.runTransformers(source,emitter);
    }
}