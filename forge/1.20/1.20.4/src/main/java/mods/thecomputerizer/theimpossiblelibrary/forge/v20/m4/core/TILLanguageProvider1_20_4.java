package mods.thecomputerizer.theimpossiblelibrary.forge.v20.m4.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.ForgeCoreLoader;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.loader.TILForgeLanguageProvider;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.core.loader.TILLanguageLoader1_20;
import net.minecraftforge.fml.loading.EarlyLoadingException;
import net.minecraftforge.fml.loading.EarlyLoadingException.ExceptionData;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.forgespi.language.IModLanguageProvider;
import net.minecraftforge.forgespi.language.ModFileScanData;
import org.objectweb.asm.Type;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.burningwave.core.assembler.StaticComponentContainer.Classes;
import static org.burningwave.core.assembler.StaticComponentContainer.Driver;
import static org.burningwave.core.assembler.StaticComponentContainer.Fields;

@IndirectCallers
public class TILLanguageProvider1_20_4 implements TILForgeLanguageProvider {
    
    public TILLanguageProvider1_20_4() {
        TILRef.logInfo("Instantiated 1.20.4 language provider (Forge edition)");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            List<EarlyLoadingException> errors = FMLLoader.getLoadingModList().getErrors();
            TILRef.logInfo("There are {} loading errors",errors.size());
            for(EarlyLoadingException exception : errors) {
                List<ExceptionData> datas = exception.getAllData();
                TILRef.logError("LOADING EXCEPTION FOUND WITH {} DATA INSTANCES",datas.size(),exception);
                for(ExceptionData data : datas)
                    TILRef.logError("EXCEPTION DATA: MESSAGE = {} | ARGS = {} | INFO = {}",data.getI18message(),
                                    data.getArgs(),data.getModInfo());
                TILRef.logError("CAUSED BY",exception.getCause());
            }
        }));
    }
    
    @Override public Consumer<ModFileScanData> getFileVisitor(CoreAPI core, IModLanguageProvider provider) {
        return scan -> {
            String className = "net.minecraftforge.fml.javafmlmod.FMLJavaModLanguageProvider";
            ClassLoader pluginLoader = ForgeCoreLoader.layerClassLoader("PLUGIN");
            ClassHelper.checkBurningWaveInit();
            Class<?> jlp = Driver.getClassByName(className,false,pluginLoader,Classes.getClass());
            Type modAnnotation = Fields.getStatic(jlp,"MODANNOTATION");
            scan.addLanguageLoader(scan.getAnnotations().stream()
                        .filter(ad -> ad.annotationType().equals(modAnnotation))
                        .peek(ad -> TILRef.logDebug("Found @Mod class {} with id {}",ad.clazz().getClassName(),ad.annotationData().get("value")))
                        .map(ad -> new TILLanguageLoader1_20(core,ad.clazz().getClassName(),(String)ad.annotationData().get("value"),scan))
                        .collect(Collectors.toMap(TILLanguageLoader1_20::getModid,Function.identity(),(a,b)->a)));
        };
    }
}