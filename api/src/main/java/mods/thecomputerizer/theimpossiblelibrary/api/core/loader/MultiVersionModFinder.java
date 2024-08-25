package mods.thecomputerizer.theimpossiblelibrary.api.core.loader;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;

import javax.annotation.Nullable;
import java.io.File;
import java.util.*;
import java.util.jar.Attributes;
import java.util.jar.Attributes.Name;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev.CLASSPATH_COREMODS;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev.CLASSPATH_MODS;

public class MultiVersionModFinder {

    public static final Name MULTIVERSION_COREMODS = new Name("TILMultiversionCoreMods");
    public static final Name MULTIVERSION_MODS = new Name("TILMultiversionMods");

    public static Set<MultiVersionModCandidate> discover(MultiVersionLoaderAPI loader, File root, boolean isCore) {
        TILRef.logDebug("Attempting to find multiversion mod candidates from root `{}`",root);
        Set<MultiVersionModCandidate> candidates = new HashSet<>();
        Set<String> foundCoreMods = new HashSet<>();
        Set<String> foundMods = new HashSet<>();
        addClasspathMods(candidates,isCore,foundCoreMods,foundMods);
        for(File file : loader.gatherCandidateModFiles(root)) {
            MultiVersionModCandidate candidate = getCandidate(loader,file,isCore,foundCoreMods,foundMods);
            if(Objects.nonNull(candidate) && candidate.hasMods()) candidates.add(candidate);
        }
        return candidates;
    }

    private static @Nullable MultiVersionModCandidate getCandidate(MultiVersionLoaderAPI loader, File file,
            boolean isCore, Set<String> foundCoreMods, Set<String> foundMods) {
        TILRef.logDebug("Examining candidate file`{}`",file);
        Attributes attributes = loader.getFileAttributes(file);
        if(Objects.nonNull(attributes)) {
            MultiVersionModCandidate candidate = new MultiVersionModCandidate(file);
            if(isCore) candidate.addCoreClasses(foundCoreMods,parseClasses(attributes,MULTIVERSION_COREMODS));
            else candidate.addModClasses(foundMods,parseClasses(attributes,MULTIVERSION_MODS));
            return candidate;
        } else TILRef.logDebug("File did not contain any attributes to check");
        return null;
    }
    
    public static boolean hasMods(Attributes attributes) {
        return attributes.containsKey(MULTIVERSION_MODS) || attributes.containsKey(MULTIVERSION_COREMODS);
    }

    private static void addClasspathMods(Set<MultiVersionModCandidate> candidates, boolean isCore,
            Set<String> foundCoreMods, Set<String> foundMods) {
        if(isCore) {
            TILRef.logDebug("Adding {} classpath coremods", CLASSPATH_COREMODS.size());
            for(String core : CLASSPATH_COREMODS) {
                MultiVersionModCandidate candidate = new MultiVersionModCandidate(core);
                TILRef.logDebug("Adding classpath coremod `{}`", core);
                candidate.addCoreClasses(foundCoreMods,core);
                candidates.add(candidate);
            }
        }
        else {
            TILRef.logDebug("Adding {} classpath mods", CLASSPATH_MODS.size());
            for(String mod : CLASSPATH_MODS) {
                MultiVersionModCandidate candidate = new MultiVersionModCandidate(mod);
                TILRef.logDebug("Adding classpath mod `{}`", mod);
                candidate.addModClasses(foundMods,mod);
                candidates.add(candidate);
            }
        }
    }

    private static String[] parseClasses(Attributes attributes, Name name) {
        TILRef.logDebug("Found attribute `{}`",name);
        String unparsed = attributes.getValue(name);
        String[] split = Objects.nonNull(unparsed) ? unparsed.split(";") : null;
        return Objects.nonNull(split) ? split : new String[0];
    }
}