package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.*;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ClassPrinter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModData;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.core.asm.ModContainerWriter1_12_2;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.discovery.ModCandidate;
import net.minecraftforge.fml.common.discovery.asm.ASMModParser;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMRef.JAVA8;
import static net.minecraft.launchwrapper.Launch.classLoader;
import static net.minecraftforge.fml.common.discovery.ContainerType.JAR;

public class InjectedModCandidate1_12_2 extends ModCandidate {

    private static Map<String,MultiVersionModData> DATA_MAP;
    private static Map<File,InjectedModCandidate1_12_2> CANDIDATE_MAP;

    public static ModContainer findModContainer(String modid) {
        InjectedModCandidate1_12_2 candidate = getCandidate(modid);
        return Objects.nonNull(candidate) ? candidate.containerMap.get(modid).container : null;
    }

    public static File findSource(String modid) {
        MultiVersionModData data = getModData(modid);
        return Objects.nonNull(data) ? data.getSource() : null;
    }

    private static @Nullable MultiVersionModData getModData(String modid) {
        Map<String,MultiVersionModData> map = getModData();
        return Objects.nonNull(map) ? map.get(modid) : null;
    }

    private static Map<String,MultiVersionModData> getModData() {
        if(Objects.nonNull(DATA_MAP)) return DATA_MAP;
        File root = (File)ReflectionHelper.getFieldInstance(null,Loader.class,"minecraftDir");
        DATA_MAP = CoreAPI.INSTANCE.getModData(root);
        return DATA_MAP;
    }

    private static Collection<MultiVersionModData> getModDataValues() {
        Map<String,MultiVersionModData> map = getModData();
        return Objects.nonNull(map) ? map.values() : Collections.emptyList();
    }

    private static @Nullable InjectedModCandidate1_12_2 getCandidate(String modid) {
        Map<File,InjectedModCandidate1_12_2> map = getCandidateMap();
        MultiVersionModData data = getModData(modid);
        return Objects.nonNull(map) && Objects.nonNull(data) && Objects.nonNull(data.getSource()) ?
                map.get(data.getSource()) : null;
    }

    private static Map<File,InjectedModCandidate1_12_2> getCandidateMap() {
        if(Objects.nonNull(CANDIDATE_MAP)) return CANDIDATE_MAP;
        CANDIDATE_MAP = new HashMap<>();
        for(MultiVersionModData data : getModDataValues()) {
            CANDIDATE_MAP.putIfAbsent(data.getSource(),new InjectedModCandidate1_12_2(
                    data.getRoot(),data.getSource()));
            Pair<String,byte[]> classBytes = data.writeModClass(JAVA8);
            if(Objects.nonNull(classBytes)) {
                String classpath = classBytes.getLeft();
                byte[] bytes = classBytes.getRight();
                ASMHelper.writeDebugByteCode(classpath,bytes);
                Class<?> clazz = ClassHelper.defineClass(classLoader,classpath,bytes);
                ModContainerWriter1_12_2.cacheClass(classLoader,classpath,clazz);
                InjectedModCandidate1_12_2 candidate = CANDIDATE_MAP.get(data.getSource());
                candidate.injectMod(data.getInfo(),classpath,bytes);
                TILRef.logInfo("Successfully loaded mod class {}!",classpath);
            }
        }
        return CANDIDATE_MAP;
    }

    private static Collection<InjectedModCandidate1_12_2> getCandidateValues() {
        Map<File,InjectedModCandidate1_12_2> map = getCandidateMap();
        return Objects.nonNull(map) ? map.values() : Collections.emptyList();
    }

    public static boolean injectIntoTable(ModContainer container, String pkgName, ASMDataTable table) {
        Collection<InjectedModCandidate1_12_2> candidates = getCandidateValues();
        TILDev.logDebug("Checking injection for {} possible mod candidates",candidates.size());
        for(InjectedModCandidate1_12_2 candidate : candidates)
            if(candidate.appendToTable(container,pkgName,table)) return true;
        return false;
    }

    private final Map<String,ContainerData> containerMap;

    private InjectedModCandidate1_12_2(File root, File source) {
        super(root,source,JAR);
        this.containerMap = new HashMap<>();
    }

    @SuppressWarnings("unchecked")
    private void addContainer(ModContainer container) {
        Field field = ReflectionHelper.getField(ModCandidate.class,"mods");
        if(Objects.isNull(field)) return;
        Object mods = ReflectionHelper.getFieldInstance(this,field);
        if(Objects.nonNull(mods)) {
            if (mods instanceof List<?>)
                ((List<ModContainer>) mods).add(container);
        } else {
            List<ModContainer> list = new ArrayList<>();
            list.add(container);
            ReflectionHelper.setFieldValue(this,field,list);
        }
    }

    private boolean appendToTable(ModContainer container, String pkgName, ASMDataTable table) {
        TILDev.logDebug("Iterating over {} possible containers to check for type {}",
                this.containerMap.values().size(),container.getClass());
        for(ContainerData data : this.containerMap.values()) {
            TILDev.logDebug("Stored container is type {}",data.container.getClass());
            if(data.container==container) {
                TILDev.logDebug("Found container! Sending its data to the ASMDataTable.");
                data.parser.sendToTable(table,this);
                table.addContainer(container);
                table.registerPackage(this,pkgName);
                return true;
            }
        }
        return false;
    }

    public void injectMod(MultiVersionModInfo info, String classpath, byte[] bytes) {
        try {
            ASMModParser parser = new ASMModParser(new ByteArrayInputStream(bytes));
            parser.validate();
            ModContainer container = ModContainerFactory.instance().build(parser,getModContainer(),this);
            if(Objects.nonNull(container)) {
                Pair<String,String> pkgPair = ClassPrinter.splitPackage(classpath);
                getClassList().add(pkgPair.getRight());
                addContainer(container);
                getContainedPackages().add(pkgPair.getLeft());
                container.bindMetadata(new InjectedMetaDataCollection(info));
                container.setClassVersion(parser.getClassVersion());
                this.containerMap.put(info.getModID(),new ContainerData(container,parser));
            }
        } catch(IOException ex) {
            TILRef.logError("Failed to parse candidate mod class `{}`",classpath,ex);
        }
    }

    private static final class InjectedMetaDataCollection extends MetadataCollection {

        final MultiVersionModInfo info;

        InjectedMetaDataCollection(MultiVersionModInfo info) {
            this.info = info;
        }

        @Override
        public ModMetadata getMetadataForId(String modId, Map<String,Object> extraData) {
            ModMetadata meta = new ModMetadata();
            meta.modId = this.info.getModID();
            meta.name = this.info.getName();
            meta.version = this.info.getVersion();
            return meta;
        }
    }

    private static final class ContainerData {

        final ModContainer container;
        final ASMModParser parser;

        ContainerData(ModContainer container, ASMModParser parser) {
            this.container = container;
            this.parser = parser;
        }
    }
}