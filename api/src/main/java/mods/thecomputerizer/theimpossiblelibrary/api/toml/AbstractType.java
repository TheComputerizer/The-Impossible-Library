package mods.thecomputerizer.theimpossiblelibrary.api.toml;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelper;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

/**
 * Underlying methods and fields used by all TOML object types in the toml package
 * Use the {@link Holder} class for accessing and modifying an indexed TOML file
 */
public abstract class AbstractType {

    /**
     * Used for direct indexing of an entire toml object from top to bottom.
     * -- SETTER --
     *  This should only be called by the holder class to when removing an element

     */
    @Setter @Getter private int absoluteIndex;

    /**
     * The parent table this type is under or null if it is top-level.
     */
    protected final Table parentTable;

    protected AbstractType(CompoundTagAPI tag, @Nullable Table parentTable) {
        this.absoluteIndex = tag.getPrimitiveTag("absoluteIndex").asInt();
        this.parentTable = parentTable;
    }

    protected AbstractType(ByteBuf buf, @Nullable Table parentTable) {
        this.absoluteIndex = buf.readInt();
        this.parentTable = parentTable;
    }

    protected AbstractType(int absoluteIndex, @Nullable Table parentTable) {
        this.absoluteIndex = absoluteIndex;
        this.parentTable = parentTable;
    }

    /**
     * Used for decrementing when a type gets removed from a holder
     */
    protected AbstractType(AbstractType from) {
        this.absoluteIndex = from.absoluteIndex-1;
        this.parentTable = from.parentTable;
    }

    public void incrementAbsoluteIndex() {
        this.absoluteIndex++;
    }

    public Table getParent() {
        return this.parentTable;
    }

    public boolean isTopLevel() {
        return Objects.isNull(this.parentTable);
    }

    public String getSpacing() {
        return TextHelper.withTabs("",Objects.isNull(this.parentTable) ? 0 : this.parentTable.getLevel());
    }

    /**
     * Converts the type into a list of strings for writing to a file or sending over a packet
     * The ignore value is passed in so types can be ignored when getting written if that is desired
     */
    public abstract List<String> toLines();

    public void write(ByteBuf buf) {
        NetworkHelper.writeString(buf,TomlPart.getByClass(this.getClass()).getID());
        buf.writeInt(this.absoluteIndex);
    }
}
