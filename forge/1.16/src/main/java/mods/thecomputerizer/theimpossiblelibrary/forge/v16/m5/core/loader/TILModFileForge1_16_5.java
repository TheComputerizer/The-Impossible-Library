package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.core.loader;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModData;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.loader.ForgeModLoading;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.loader.TILFileConfigForge;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.LanguageLoadingProvider;
import net.minecraftforge.fml.loading.moddiscovery.CoreModFile;
import net.minecraftforge.fml.loading.moddiscovery.ModFile;
import net.minecraftforge.fml.loading.moddiscovery.ModFileInfo;
import net.minecraftforge.fml.loading.moddiscovery.ModFileParser;
import net.minecraftforge.forgespi.language.IConfigurable;
import net.minecraftforge.forgespi.language.IModFileInfo;
import net.minecraftforge.forgespi.language.IModInfo;
import net.minecraftforge.forgespi.language.IModLanguageProvider;
import net.minecraftforge.forgespi.language.ModFileScanData;
import net.minecraftforge.forgespi.locating.IModLocator;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static org.burningwave.core.assembler.StaticComponentContainer.Constructors;
import static org.burningwave.core.assembler.StaticComponentContainer.Methods;

@Getter //TODO See if this can be made consistent with the other mod file implementations due to the lang provider standardization
public class TILModFileForge1_16_5 extends ModFile {
    
    static boolean fixedCoreMods;
    static boolean loadedProvider;
    
    private final Map<MultiVersionModInfo,MultiVersionModData> infos;
    protected IModFileInfo fileInfo;
    protected IModLanguageProvider loader;
    protected Path accessTransformer;
    protected List<CoreModFile> coreMods;
    
    public TILModFileForge1_16_5(Path path, IModLocator locator, Collection<?> infos) {
        super(path,locator,null);
        this.infos = ForgeModLoading.initFileInfo("16_5",infos);
    }
    
    @Override public ModFileScanData compileContent() {
        return ForgeModLoading.writeMods(this);
    }
    
    private List<CoreModFile> findCoreMods() {
        return Methods.invokeStatic(ModFileParser.class,"getCoreMods",this);
    }
    
    @Override public Optional<Path> getAccessTransformer() {
        return Optional.ofNullable(Files.exists(this.accessTransformer) ? this.accessTransformer : null);
    }
    
    @Override public List<CoreModFile> getCoreMods() {
        if(Objects.isNull(this.coreMods)) {
            this.coreMods = findCoreMods();
            if(!this.coreMods.isEmpty() && !fixedCoreMods) {
                ForgeModLoading.fixCoreModPackages();
                fixedCoreMods = true;
            }
        }
        TILRef.logInfo("Found coremods {}",this.coreMods);
        return this.coreMods;
    }
    
    @Override public IModLanguageProvider getLoader() {
        if(Objects.isNull(this.loader)) identifyLanguage();
        return this.loader;
    }
    
    @Override public List<IModInfo> getModInfos() {
        return getModFileInfo().getMods();
    }
    
    @Override public IModFileInfo getModFileInfo() {
        if(Objects.isNull(this.fileInfo)) {
            if(!loadedProvider) {
                TILRef.logWarn("Invalid? We'll see about that, Forge");
                TILRef.logInfo("Loading multiversion language provider");
                LanguageLoadingProvider provider =  FMLLoader.getLanguageLoadingProvider();
                ModFile langFile = ForgeModLoading.langProviderModFile(this);
                provider.addAdditionalLanguages(Collections.singletonList(langFile));
                loadedProvider = true;
            }
            TILRef.logInfo("Building IModFileInfo");
            IConfigurable config = new TILFileConfigForge(this.infos.keySet());
            this.fileInfo = Constructors.newInstanceOf(ModFileInfo.class,this,config);
            if(Objects.isNull(this.fileInfo)) TILRef.logFatal("Failed to find construct ModFileInfo???");
        }
        return this.fileInfo;
    }
    
    @Override public void identifyLanguage() {
        IModFileInfo info = getModFileInfo();
        LanguageLoadingProvider provider =  FMLLoader.getLanguageLoadingProvider();
        this.loader = provider.findLanguage(this,info.getModLoader(),info.getModLoaderVersion());
    }
    
    @Override public boolean identifyMods() {
        TILRef.logInfo("Loading mod file {} with language {}",getFilePath(),getModFileInfo().getModLoader());
        this.accessTransformer = getLocator().findPath(this,"META-INF","accesstransformer.cfg");
        return true;
    }
}