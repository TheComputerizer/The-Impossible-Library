package mods.thecomputerizer.theimpossiblelibrary.common.toml;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Stores blank lines which normally do not get read in when parsing TOML files
 */
public final class BlankLine extends AbstractType {

    /**
     * The number of blank lines
     */
    private final int lines;
    public BlankLine(int absoluteIndex, @Nullable Table parentTable, int lines) {
        super(absoluteIndex,parentTable);
        this.lines = lines;
    }

    public int getLines() {
        return lines;
    }

    @Override
    public List<String> toLines() {
        return IntStream.range(0,this.lines).mapToObj(i -> "").collect(Collectors.toList());
    }
}
