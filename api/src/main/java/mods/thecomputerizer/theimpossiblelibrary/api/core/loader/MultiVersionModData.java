package mods.thecomputerizer.theimpossiblelibrary.api.core.loader;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ModWriter;

import java.io.File;
import java.util.List;
import java.util.Map.Entry;

@Getter
public class MultiVersionModData {

    private final File root;
    private final MultiVersionModCandidate candidate;
    private final MultiVersionModInfo info;
    private final ModWriter writer;
    @Setter private String modClasspath;

    public MultiVersionModData(File root, MultiVersionModCandidate candidate, ModWriter writer) {
        this.root = root;
        this.candidate = candidate;
        this.info = writer.getInfo();
        this.writer = writer;
    }

    public File getSource() {
        return this.candidate.getFile();
    }
    
    public List<Entry<String,byte[]>> writeModClass() {
        return this.writer.buildModClass();
    }
}