package mods.thecomputerizer.theimpossiblelibrary.api.tag;

import java.io.File;
import java.io.IOException;

public interface TagAPI {
    
    <T> BaseTagAPI<T> getWrapped(T tag);
    CompoundTagAPI<?> makeCompoundTag();
    ListTagAPI<?> makeListTag();
    PrimitiveTagAPI<?> makePrimitiveTag(boolean b);
    PrimitiveTagAPI<?> makePrimitiveTag(byte b);
    PrimitiveTagAPI<?> makePrimitiveTag(double d);
    PrimitiveTagAPI<?> makePrimitiveTag(float f);
    PrimitiveTagAPI<?> makePrimitiveTag(int i);
    PrimitiveTagAPI<?> makePrimitiveTag(long l);
    PrimitiveTagAPI<?> makePrimitiveTag(short s);
    StringTagAPI<?> makeStringTag(String value);
    CompoundTagAPI<?> readFromFile(File file) throws IOException;
    void writeToFile(CompoundTagAPI<?> tag, File file) throws IOException;
}