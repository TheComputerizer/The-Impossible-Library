package mods.thecomputerizer.theimpossiblelibrary.api.core.asm;

import java.util.Collection;

public interface BytecodePrinter {

    void toLines(Collection<String> lines, int tabs);
}