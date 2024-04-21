package mods.thecomputerizer.theimpossiblelibrary.api.core.loader;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;

import javax.annotation.Nullable;
import java.io.File;
import java.util.*;
import java.util.jar.Attributes;
import java.util.jar.Attributes.Name;

public class MultiVersionModFinder {

    private static final Name MULTIVERSION_COREMODS = new Name("TILMultiversionCoreMods");
    private static final Name MULTIVERSION_MODS = new Name("TILMultiversionMods");

    public static Set<MultiVersionModCandidate> discover(MultiVersionLoaderAPI loader, File root, boolean isCore) {
        TILRef.logDebug("Attempting to find multiversion mod candidates from root `{}`",root);
        Set<MultiVersionModCandidate> candidates = new HashSet<>();
        addClasspathMods(candidates,isCore);
        for(File file : loader.gatherCandidateModFiles(root)) {
            MultiVersionModCandidate candidate = getCandidate(loader,file,isCore);
            if(Objects.nonNull(candidate) && candidate.hasMods()) candidates.add(candidate);
        }
        return candidates;
    }

    private static @Nullable MultiVersionModCandidate getCandidate(MultiVersionLoaderAPI loader, File file, boolean isCore) {
        TILRef.logDebug("Examining candidate file`{}`",file);
        Attributes attributes = loader.getFileAttributes(file);
        if(Objects.nonNull(attributes)) {
            MultiVersionModCandidate candidate = new MultiVersionModCandidate(file);
            if(isCore) candidate.addCoreClasses(parseClasses(attributes,MULTIVERSION_COREMODS));
            else candidate.addModClasses(parseClasses(attributes,MULTIVERSION_MODS));
            return candidate;
        } else TILRef.logDebug("File did not contain any attributes to check");
        return null;
    }

    private static void addClasspathMods(Set<MultiVersionModCandidate> candidates, boolean isCore) {
        if(isCore) {
            TILRef.logDebug("Adding {} classpath coremods", TILDev.CLASSPATH_COREMODS.size());
            for(String core : TILDev.CLASSPATH_COREMODS) {
                MultiVersionModCandidate candidate = new MultiVersionModCandidate(core);
                TILRef.logDebug("Adding classpath coremod `{}`", core);
                candidate.addCoreClasses(core);
                candidates.add(candidate);
            }
        }
        else {
            TILRef.logDebug("Adding {} classpath mods", TILDev.CLASSPATH_MODS.size());
            for(String mod : TILDev.CLASSPATH_MODS) {
                MultiVersionModCandidate candidate = new MultiVersionModCandidate(mod);
                TILRef.logDebug("Adding classpath mod `{}`", mod);
                candidate.addModClasses(mod);
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