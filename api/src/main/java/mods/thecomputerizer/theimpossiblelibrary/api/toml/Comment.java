package mods.thecomputerizer.theimpossiblelibrary.api.toml;

import io.netty.buffer.ByteBuf;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkHelper;

import javax.annotation.Nullable;
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

    public Comment(ByteBuf buf, @Nullable Table parentTable) {
        super(buf,parentTable);
        this.comments = NetworkHelper.readGenericList(buf, NetworkHelper::readString);
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
    public void write(ByteBuf buf) {
        super.write(buf);
        NetworkHelper.writeGenericList(buf,this.comments, NetworkHelper::writeString);
    }
}
