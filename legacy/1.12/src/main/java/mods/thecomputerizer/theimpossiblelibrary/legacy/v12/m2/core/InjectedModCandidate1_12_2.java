package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ReflectionHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ClassPrinter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModData;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Misc;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.core.asm.ModContainerWriter1_12_2;
import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.discovery.ModCandidate;
import net.minecraftforge.fml.common.discovery.asm.ASMModParser;
import org.apache.commons.lang3.tuple.Pair;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMRef.JAVA8;
import static net.minecraft.launchwrapper.Launch.classLoader;
import static net.minecraftforge.fml.common.discovery.ContainerType.JAR;

public class InjectedModCandidate1_12_2 extends ModCandidate {

    private static Map<String,MultiVersionModData> DATA_MAP;
    private static Map<File,InjectedModCandidate1_12_2> CANDIDATE_MAP;

    public static ModContainer findModContainer(String modid) {
        MultiVersionModData data = getModData().get(modid);
        if(Objects.isNull(data)) return null;
        InjectedModCandidate1_12_2 candidate = getCandidateMap().get(data.getSource());
        return Objects.nonNull(candidate) ? candidate.containerMap.get(modid) : null;
    }

    public static File findSource(String modid) {
        MultiVersionModData data = getModData().get(modid);
        return Objects.nonNull(data) ? data.getSource() : null;
    }

    private static Map<String,MultiVersionModData> getModData() {
        if(Objects.nonNull(DATA_MAP)) return DATA_MAP;
        File root = (File)ReflectionHelper.getFieldInstance(null,Loader.class,"minecraftDir");
        DATA_MAP = CoreAPI.INSTANCE.getModData(root);
        return DATA_MAP;
    }

    private static Map<File,InjectedModCandidate1_12_2> getCandidateMap() {
        if(Objects.nonNull(CANDIDATE_MAP)) return CANDIDATE_MAP;
        CANDIDATE_MAP = new HashMap<>();
        for(MultiVersionModData data : getModData().values()) {
            CANDIDATE_MAP.putIfAbsent(data.getSource(),new InjectedModCandidate1_12_2(
                    data.getRoot(),data.getSource(), new ASMDataTable()));
            Pair<String,byte[]> classBytes = data.writeModClass(JAVA8);
            if(Objects.nonNull(classBytes)) {
                String classpath = classBytes.getLeft();
                byte[] bytes = classBytes.getRight();
                ASMHelper.writeDebugByteCode(classpath,bytes);
                Class<?> clazz = ASMHelper.defineClass(classLoader,classpath,bytes);
                ModContainerWriter1_12_2.cacheClass(classLoader,classpath,clazz);
                InjectedModCandidate1_12_2 candidate = CANDIDATE_MAP.get(data.getSource());
                candidate.injectMod(data.getInfo(),classpath,bytes);
                TILRef.logInfo("Successfully loaded mod class {}!",classpath);
            }
        }
        return CANDIDATE_MAP;
    }

    private final Map<String,ModContainer> containerMap;
    private final ASMDataTable table;

    private InjectedModCandidate1_12_2(File root, File source, ASMDataTable table) {
        super(root,source,JAR);
        this.table = table;
        this.containerMap = new HashMap<>();
    }

    public void injectMod(MultiVersionModInfo info, String classpath, byte[] bytes) {
        try {
            ASMModParser parser = new ASMModParser(new ByteArrayInputStream(bytes));
            parser.validate();
            parser.sendToTable(this.table,this);
            ModContainer container = ModContainerFactory.instance().build(parser,getModContainer(),this);
            if(Objects.nonNull(container)) {
                Pair<String,String> pkgPair = ClassPrinter.splitPackage(classpath);
                getClassList().add(pkgPair.getRight());
                getContainedPackages().add(pkgPair.getLeft());
                this.table.addContainer(container);
                container.bindMetadata(new InjectedMetaDataCollection(info));
                container.setClassVersion(parser.getClassVersion());
                this.containerMap.put(info.getModID(),container);
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
}