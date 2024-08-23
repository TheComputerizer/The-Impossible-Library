package mods.thecomputerizer.theimpossiblelibrary.api.core.loader;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.util.List;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.INSTANCE;

@Getter
public class MultiVersionModData {

    private final File root;
    private final MultiVersionModCandidate candidate;
    private final MultiVersionModInfo info;
    @Setter private String modClasspath;

    public MultiVersionModData(File root, MultiVersionModCandidate candidate, MultiVersionModInfo info) {
        this.root = root;
        this.candidate = candidate;
        this.info = info;
    }

    public File getSource() {
        return this.candidate.getFile();
    }

    public List<Pair<String,byte[]>> writeModClass(int javaVer) {
        return MultiVersionModWriter.buildModClass(javaVer,INSTANCE.getModLoader(),this.info);
    }
}