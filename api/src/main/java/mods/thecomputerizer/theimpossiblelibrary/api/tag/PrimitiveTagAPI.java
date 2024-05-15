package mods.thecomputerizer.theimpossiblelibrary.api.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.util.Wrapped;

@SuppressWarnings("unused")
public interface PrimitiveTagAPI<T> extends Wrapped<T> {

    boolean asBoolean();
    byte asByte();
    double asDouble();
    float asFloat();
    int asInt();
    long asLong();
    short asShort();
}