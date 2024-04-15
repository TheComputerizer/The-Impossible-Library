package mods.thecomputerizer.theimpossiblelibrary.api.core.loader;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;

import javax.annotation.Nullable;
import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.jar.Attributes;
import java.util.jar.Attributes.Name;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

@Getter
public class MultiversionModFinder {

    private static final Name MULTIVERSION_COREMODS = new Name("TILMultiversionCoreMods");
    private static final Name MULTIVERSION_MODS = new Name("TILMultiversionMods");

    private final Set<MultiversionModCandidate> coreCandidates;
    private final Set<MultiversionModCandidate> modCandidates;

    protected MultiversionModFinder(File root) {
        Set<MultiversionModCandidate> candidates = discover(new File(root,"mods"));
        this.coreCandidates = addSet(candidates,MultiversionModCandidate::hasCoreMods);
        this.modCandidates = addSet(candidates,MultiversionModCandidate::hasMods);
    }

    private <M extends MultiversionModCandidate> Set<M> addSet(Set<M> candidates, Function<M,Boolean> filter) {
        Set<M> set = new HashSet<>();
        for(M candidate : candidates)
            if(filter.apply(candidate)) set.add(candidate);
        return Collections.unmodifiableSet(set);
    }

    private Set<MultiversionModCandidate> discover(File modsDir) {
        Set<MultiversionModCandidate> candidates = new HashSet<>();
        for(File file : gatherMods(new HashSet<>(),modsDir.listFiles())) {
            MultiversionModCandidate candidate = getCandidate(file);
            if(candidate.hasMods()) candidates.add(candidate);
        }
        return candidates;
    }

    private Set<File> gatherMods(Set<File> files, @Nullable File[] potentialFiles) {
        if(Objects.nonNull(potentialFiles)) {
            for(File file : potentialFiles) {
                if(Objects.nonNull(file) && file.exists() && file.canRead()) {
                    if(file.isDirectory()) files = gatherMods(files,file.listFiles());
                    else if(file.getName().endsWith(".jar")) files.add(file);
                }
            }
        }
        return files;
    }

    private MultiversionModCandidate getCandidate(File file) {
        MultiversionModCandidate candidate = new MultiversionModCandidate(file);
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
        String unparsed = attributes.getValue(name);
        String[] split = Objects.nonNull(unparsed) ? unparsed.split(";") : null;
        return Objects.nonNull(split) ? split : new String[0];
    }
}