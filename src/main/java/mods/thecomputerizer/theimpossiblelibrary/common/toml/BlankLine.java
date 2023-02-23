package mods.thecomputerizer.theimpossiblelibrary.common.toml;

import io.netty.buffer.ByteBuf;

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

    /**
     * For decoding from a packet
     */
    public BlankLine(ByteBuf buf) {
        super(buf,null);
        this.lines = buf.readInt();
    }

    /**
     * Blank lines do not need to be tied to specific tables so the parent will always be null
     */
    public BlankLine(int absoluteIndex, int lines) {
        super(absoluteIndex,null);
        this.lines = lines;
    }

    public int getLines() {
        return this.lines;
    }

    @Override
    public List<String> toLines() {
        return IntStream.range(0,this.lines).mapToObj(i -> "").collect(Collectors.toList());
    }

    @Override
    public void write(ByteBuf buf) {
        super.write(buf);
        buf.writeInt(this.lines);
    }
}
