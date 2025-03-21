package mods.thecomputerizer.theimpossiblelibrary.fabric.core.asm;

import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.BasicMutableWrapped;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.MutableWrapped;
import net.fabricmc.loader.impl.launch.FabricLauncherBase;
import org.objectweb.asm.tree.ClassNode;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

public class TILFabricASMTarget {
    
    private static TILFabricASMTarget INSTANCE;
    
    private static ClassLoader targetClassLoader() {
        return FabricLauncherBase.getLauncher().getTargetClassLoader();
    }
    
    private static TILFabricASMTarget getInstance() {
        return Objects.nonNull(INSTANCE) ? INSTANCE : new TILFabricASMTarget();
    }
    
    public static void loadDefinitions() {
        getInstance().load(targetClassLoader());
    }
    
    public static void registerDefinition(String target, byte[] byteCode) {
        TILFabricASMTarget instance = getInstance();
        instance.classDefinitions.put(target,byteCode);
        instance.checkLoadLate(targetClassLoader());
    }
    
    public static void registerEditor(String target, Consumer<ClassNode> editor) {
        TILFabricASMTarget instance = getInstance();
        instance.classEditors.putIfAbsent(target,new HashSet<>());
        instance.classEditors.get(target).add(editor);
    }
    
    /**
     * Runs all registered class editors
     */
    public static void runTransformers(Function<String,ClassNode> source, Consumer<ClassNode> emitter) {
        getInstance().transformFunctionally(source,emitter);
    }
    
    /**
     * Runs the specified class editor if it exists
     */
    @IndirectCallers
    private @Nullable byte[] transformSingleton(String className, byte[] byteCode) {
        return getInstance().transform(className,byteCode);
    }
    
    private final Map<String,byte[]> classDefinitions = new HashMap<>();
    private final Map<String,Set<Consumer<ClassNode>>> classEditors = new HashMap<>();
    private boolean loadedDefinitions;
    
    private TILFabricASMTarget() {
        INSTANCE = this;
    }
    
    /**
     * In case for some reason a class is defined after the initial loading for class definitions is run
     */
    private void checkLoadLate(ClassLoader target) {
        if(this.loadedDefinitions) load(target);
    }
    
    private void load(ClassLoader target) {
        for(Entry<String,byte[]> definition : this.classDefinitions.entrySet())
            ClassHelper.defineClass(target,definition.getKey(),definition.getValue());
        this.classDefinitions.clear();
        this.loadedDefinitions = true;
    }
    
    private @Nullable byte[] onReturnTransform(String className, @Nullable byte[] transformed) {
        this.classEditors.remove(className);
        return transformed;
    }
    
    private @Nullable byte[] transform(String className, byte[] byteCode) {
        final MutableWrapped<ClassNode> nodeWrapper = new BasicMutableWrapped<>();
        transformFunctionally(name -> ASMHelper.toClassNode(byteCode),nodeWrapper::setWrapped);
        return onReturnTransform(className,nodeWrapper.asOptional().map(ASMHelper::toBytes).orElse(null));
    }
    
    private void transformFunctionally(Function<String,ClassNode> source, Consumer<ClassNode> emitter) {
        for(Entry<String,Set<Consumer<ClassNode>>> entry : this.classEditors.entrySet())
            transformNode(entry.getKey(),source,emitter,entry.getValue());
        this.classEditors.clear();
    }
    
    private void transformNode(String className, Function<String,ClassNode> source, Consumer<ClassNode> emitter,
            Collection<Consumer<ClassNode>> editors) {
        ClassNode node = source.apply(className);
        if(Objects.isNull(node)) {
            TILRef.logError("ClassNode note found! Failed to transform {}",className);
            return;
        }
        if(Objects.isNull(editors) || editors.isEmpty()) return;
        TILRef.logInfo("Running transfomers for {}",className);
        for(Consumer<ClassNode> editor : editors) editor.accept(node);
        emitter.accept(node);
    }
}