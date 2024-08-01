package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.core.loader;

import mods.thecomputerizer.theimpossiblelibrary.api.core.ReflectionHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLanguageProvider;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.moddiscovery.CoreModFile;
import net.minecraftforge.fml.loading.moddiscovery.ModFile;
import net.minecraftforge.fml.loading.moddiscovery.ModFileInfo;
import net.minecraftforge.forgespi.language.IConfigurable;
import net.minecraftforge.forgespi.language.IModFileInfo;
import net.minecraftforge.forgespi.language.IModInfo;
import net.minecraftforge.forgespi.language.IModLanguageProvider;
import net.minecraftforge.forgespi.language.ModFileScanData;
import net.minecraftforge.forgespi.locating.IModLocator;

import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static net.minecraftforge.forgespi.locating.IModFile.Type.MOD;

public class TILModFile1_16_5 extends ModFile {
    
    private final Collection<MultiVersionModInfo> infos;
    protected IModFileInfo fileInfo;
    protected IModLanguageProvider loader;
    protected Path accessTransformer;
    protected ModFileScanData scanData;
    
    public TILModFile1_16_5(Path path, IModLocator locator, Collection<MultiVersionModInfo> infos) {
        super(path,locator,null);
        this.infos = infos;
    }
    
    @Override public Optional<Path> getAccessTransformer() {
        return Optional.ofNullable(Files.exists(this.accessTransformer) ? this.accessTransformer : null);
    }
    
    @Override public List<CoreModFile> getCoreMods() {
        return Collections.emptyList();
    }
    
    @Override public IModLanguageProvider getLoader() {
        if(Objects.isNull(this.loader)) identifyLanguage();
        return super.getLoader();
    }
    
    @Override public ModFileScanData getScanResult() {
        if(Objects.isNull(this.scanData)) {
            this.scanData = new TILBetterModScan1_16_5(this.infos);
            new FMLJavaModLanguageProvider().getFileVisitor().accept(this.scanData);
        }
        return this.scanData;
    }
    
    @Override public Type getType() {
        return MOD;
    }
    
    @Override public List<IModInfo> getModInfos() {
        return getModFileInfo().getMods();
    }
    
    @Override public IModFileInfo getModFileInfo() {
        if(Objects.isNull(this.fileInfo)) {
            Constructor<?> infoConstructor = ReflectionHelper.findConstructor(ModFileInfo.class,ModFile.class,IConfigurable.class);
            if(Objects.nonNull(infoConstructor)) {
                if(!infoConstructor.isAccessible()) infoConstructor.setAccessible(true);
                try {
                    this.fileInfo = (ModFileInfo)infoConstructor.newInstance(this,new TILFileConfig1_16_5(this.infos));
                    TILRef.logDebug("Successfully captured multiversion mods for {}",getFileName());
                } catch(ReflectiveOperationException ex) {
                    TILRef.logFatal("Failed to capture mod file for {}! A crash may be imminent",getFileName(),ex);
                }
            } else TILRef.logFatal("Failed to find ModFileInfo constructor???");
        }
        return this.fileInfo;
    }
    
    @Override
    public void identifyLanguage() {
        IModFileInfo info = getModFileInfo();
        this.loader = FMLLoader.getLanguageLoadingProvider().findLanguage(this,info.getModLoader(),info.getModLoaderVersion());
    }
    
    @Override
    public boolean identifyMods() {
        TILRef.logDebug("Loading mod file {} with language {}",getFilePath(),getModFileInfo().getModLoader());
        //this.coreMods = ModFileParser.getCoreMods(this); //TODO Coremod support
        this.accessTransformer = getLocator().findPath(this,"META-INF","accesstransformer.cfg");
        return true;
    }
}
