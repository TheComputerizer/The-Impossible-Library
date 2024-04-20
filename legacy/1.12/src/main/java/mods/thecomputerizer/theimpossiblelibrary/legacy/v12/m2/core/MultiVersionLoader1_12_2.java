package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ReflectionHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionLoaderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.core.asm.ModContainerWriter1_12_2;
import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.relauncher.CoreModManager;
import net.minecraftforge.fml.relauncher.FMLInjectionData;
import net.minecraftforge.fml.relauncher.libraries.Artifact;
import net.minecraftforge.fml.relauncher.libraries.LibraryManager;
import net.minecraftforge.fml.relauncher.libraries.ModList;
import net.minecraftforge.fml.relauncher.libraries.Repository;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class MultiVersionLoader1_12_2 extends MultiVersionLoaderAPI {


    public MultiVersionLoader1_12_2(CoreAPI parent) {
        super(parent);
    }

    @Override
    protected File findCoreModRoot() { //TODO Figure out how to get around classpath injection
        //return (File)ReflectionHelper.getFieldInstance(null,CoreModManager.class,"mcDir");
        return new File("mods");
    }

    @Override
    protected File findModRoot() {//TODO Figure out how to get around classpath injection
        //return (File)ReflectionHelper.getFieldInstance(null,Loader.class,"minecraftDir");
        return new File("mods");
    }

    @Override
    protected List<File> gatherCandidateModFiles(File root) { //TODO support dev env :(
        TILRef.logDebug("Gathering mod candidates from `{}`",root);
        File[] files = root.listFiles(file -> file.isFile() && file.getName().endsWith(".jar"));
        return Objects.nonNull(files) ? Arrays.asList(files) : Collections.emptyList();
    }

    @Override
    protected @Nullable Attributes getFileAttributes(File file) {
        if(Objects.isNull(file) || !file.exists()) return null;
        File manifest = new File(file.getAbsolutePath() + ".meta");
        if(LibraryManager.DISABLE_EXTERNAL_MANIFEST || !manifest.exists()) {
            try(JarFile jar = new JarFile(file)) {
                return Objects.isNull(jar.getManifest()) ? null : jar.getManifest().getMainAttributes();
            } catch(IOException ex) {
                TILRef.logError("Failed to get attributes from jar `{}`",file,ex);
                return null;
            }
        }
        try(FileInputStream stream = new FileInputStream(manifest)) {
            return new Manifest(stream).getMainAttributes();
        } catch(IOException ex) {
            TILRef.logError("Failed to get attributes from file `{}`",file,ex);
            return null;
        }
    }

    @Override
    protected MultiVersionModInfo loadModInfo(ClassLoader classLoader, File root, MultiVersionModInfo info) {
        FMLInjectionData.containers.add(ModContainerWriter1_12_2.writeModContainer((LaunchClassLoader)classLoader,info.getModID(),
                info.getContainerClasspath()));
        return info;
    }
}