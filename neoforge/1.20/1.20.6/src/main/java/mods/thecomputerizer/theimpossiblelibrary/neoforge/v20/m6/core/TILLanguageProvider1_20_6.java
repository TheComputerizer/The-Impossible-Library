package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m6.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.core.NeoForgeCoreLoader;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.core.loader.TILNeoForgeLanguageProvider;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.core.loader.TILModFileNeoForge1_20;
import net.neoforged.fml.ModLoadingIssue;
import net.neoforged.fml.loading.LoadingModList;
import net.neoforged.neoforgespi.language.IModLanguageLoader;
import net.neoforged.neoforgespi.language.ModFileScanData;
import org.objectweb.asm.Type;

import java.util.List;
import java.util.function.Consumer;

import static org.burningwave.core.assembler.StaticComponentContainer.Classes;
import static org.burningwave.core.assembler.StaticComponentContainer.Driver;
import static org.burningwave.core.assembler.StaticComponentContainer.Fields;

@IndirectCallers //TODO Fix for 1.20.6
public class TILLanguageProvider1_20_6 implements TILNeoForgeLanguageProvider<IModLanguageLoader> {
    
    @Override public void fixMods() {
        try {
            TILRef.logError("Starting to try and fix multiversion mods");
            LoadingModList list = LoadingModList.get();
            List<ModLoadingIssue> errors = list.getModLoadingIssues();
            TILRef.logError("Maybe fixing {} loading errors",errors.size());
            errors.removeIf(ex -> ex.affectedModFile() instanceof TILModFileNeoForge1_20);
        } catch(Throwable t) {
            TILRef.logError("Failed to fix mods",t);
        }
    }
    
    @Override public Consumer<ModFileScanData> getFileVisitor(CoreAPI core, IModLanguageLoader loader) {
        return scan -> {
            String className = "net.minecraftforge.fml.javafmlmod.FMLJavaModLanguageProvider";
            ClassLoader pluginLoader = NeoForgeCoreLoader.layerClassLoader("PLUGIN");
            ClassHelper.checkBurningWaveInit();
            Class<?> jlp = Driver.getClassByName(className,false,pluginLoader,Classes.getClass());
            Type modAnnotation = Fields.getStatic(jlp,"MODANNOTATION");
            //Map<String,TILLanguageLoader1_20> map = scan.getAnnotations().stream()
            //            .filter(ad -> ad.annotationType().equals(modAnnotation))
            //            .peek(ad -> TILRef.logDebug("Found @Mod class {} with id {}",ad.clazz().getClassName(),ad.annotationData().get("value")))
            //            .map(ad -> new TILLanguageLoader1_20(core, ad.clazz().getClassName(), (String)ad.annotationData().get("value"), scan))
            //            .collect(Collectors.toMap(TILLanguageLoader1_20::getModid,Function.identity(),(a,b)->a));
        };
    }
}