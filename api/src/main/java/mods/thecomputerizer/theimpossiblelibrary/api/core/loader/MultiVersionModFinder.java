package mods.thecomputerizer.theimpossiblelibrary.api.core.loader;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;

import java.io.File;
import java.util.*;
import java.util.function.Function;
import java.util.jar.Attributes;
import java.util.jar.Attributes.Name;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

@Getter
public class MultiVersionModFinder {

    private static final Name MULTIVERSION_COREMODS = new Name("TILMultiversionCoreMods");
    private static final Name MULTIVERSION_MODS = new Name("TILMultiversionMods");

    private final Set<MultiVersionModCandidate> coreCandidates;
    private final Set<MultiVersionModCandidate> modCandidates;

    protected MultiVersionModFinder(Collection<File> files) {
        Set<MultiVersionModCandidate> candidates = discover(files);
        this.coreCandidates = addSet(candidates, MultiVersionModCandidate::hasCoreMods);
        this.modCandidates = addSet(candidates, MultiVersionModCandidate::hasMods);
    }

    private <M extends MultiVersionModCandidate> Set<M> addSet(Set<M> candidates, Function<M,Boolean> filter) {
        Set<M> set = new HashSet<>();
        for(M candidate : candidates)
            if(filter.apply(candidate)) set.add(candidate);
        return Collections.unmodifiableSet(set);
    }

    private Set<MultiVersionModCandidate> discover(Collection<File> files) {
        TILRef.logDebug("Attempting to find multiversion mod candidates with {} files",files.size());
        Set<MultiVersionModCandidate> candidates = new HashSet<>();
        for(File file : files) {
            MultiVersionModCandidate candidate = getCandidate(file);
            if(candidate.hasMods()) candidates.add(candidate);
        }
        return candidates;
    }

    private MultiVersionModCandidate getCandidate(File file) {
        TILRef.logDebug("Examining candidate `{}`",file);
        MultiVersionModCandidate candidate = new MultiVersionModCandidate(file);
        try(JarFile jar = new JarFile(file)) {
            Manifest manifest = jar.getManifest();
            if(Objects.nonNull(manifest)) {
                Attributes attributes =  manifest.getMainAttributes();
                if(Objects.nonNull(attributes)) {
                    candidate.addCoreClasses(parseClasses(attributes,MULTIVERSION_COREMODS));
                    candidate.addModClasses(parseClasses(attributes,MULTIVERSION_MODS));
                }
            }
        } catch(Exception ex) {
            TILRef.logError("Failed to read jar for multiversion mod candidate at `{}`!",file.getPath(),ex);
        }
        return candidate;
    }

    private String[] parseClasses(Attributes attributes, Name name) {
        TILRef.logDebug("Found attribute `{}`",name);
        String unparsed = attributes.getValue(name);
        String[] split = Objects.nonNull(unparsed) ? unparsed.split(";") : null;
        return Objects.nonNull(split) ? split : new String[0];
    }
}