package mods.thecomputerizer.theimpossiblelibrary.common.toml;

import mods.thecomputerizer.theimpossiblelibrary.util.NetworkUtil;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Stores comments which normally do not get read in when parsing TOML files
 */
public final class Comment extends AbstractType {

    /**
     * This is a list so comments that span multiple lines do not have to be stored separately
     */
    private final List<String> comments;

    public Comment(FriendlyByteBuf buf, @Nullable Table parentTable) {
        super(buf,parentTable);
        this.comments = NetworkUtil.readGenericList(buf,NetworkUtil::readString);
    }

    public Comment(int absoluteIndex, @Nullable Table parentTable, List<String> orderedCommentLines) {
        super(absoluteIndex,parentTable);
        this.comments = orderedCommentLines;
    }

    public List<String> getOrderedCommentLines() {
        return this.comments;
    }

    @Override
    public List<String> toLines() {
        return this.comments.stream().map(comment -> getSpacing()+"#"+comment).collect(Collectors.toList());
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        super.write(buf);
        NetworkUtil.writeGenericList(buf,this.comments,NetworkUtil::writeString);
    }
}
