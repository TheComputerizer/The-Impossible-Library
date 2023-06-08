package mods.thecomputerizer.theimpossiblelibrary.common.toml;

import io.netty.buffer.ByteBuf;
import mods.thecomputerizer.theimpossiblelibrary.util.NetworkUtil;
import mods.thecomputerizer.theimpossiblelibrary.util.TextUtil;
import net.minecraft.nbt.NBTTagCompound;

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
     */
    private int absoluteIndex;

    /**
     * The parent table this type is under or null if it is top-level.
     */
    protected final Table parentTable;

    protected AbstractType(NBTTagCompound tag, @Nullable Table parentTable) {
        this.absoluteIndex = tag.getInteger("absoluteIndex");
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

    public int getAbsoluteIndex() {
        return this.absoluteIndex;
    }

    /**
     * This should only be called by the holder class to when removing an element
     */
    public void setAbsoluteIndex(int correctIndex) {
        this.absoluteIndex = correctIndex;
    }

    /**
     * This should only be called by the holder class to when add or moving an element
     */
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
        return TextUtil.withTabs("",Objects.isNull(this.parentTable) ? 0 : this.parentTable.getLevel());
    }

    /**
     * Converts the type into a list of strings for writing to a file or sending over a packet
     * The ignore value is passed in so types can be ignored when getting written if that is desired
     */
    public abstract List<String> toLines();

    public void write(ByteBuf buf) {
        NetworkUtil.writeString(buf,TomlPart.getByClass(this.getClass()).getID());
        buf.writeInt(this.absoluteIndex);
    }
}
