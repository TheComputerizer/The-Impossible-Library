package mods.thecomputerizer.theimpossiblelibrary.api.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;

import java.io.File;
import java.util.Objects;

public interface TagAPI {
    
    <T> BaseTagAPI<T> getWrapped(T tag);
    
    default CompoundTagAPI<?> makeCompoundTag() {
        return getWrapped(newCompoundTag()).asCompoundTag();
    }
    
    default ListTagAPI<?> makeListTag() {
        return getWrapped(newListTag()).asListTag();
    }
    
    default PrimitiveTagAPI<?> makePrimitiveTag(boolean b) {
        return getWrapped(newPrimitiveTag(b)).asPrimitiveTag();
    }
    
    default PrimitiveTagAPI<?> makePrimitiveTag(byte b) {
        return getWrapped(newPrimitiveTag(b)).asPrimitiveTag();
    }
    
    default PrimitiveTagAPI<?> makePrimitiveTag(double d) {
        return getWrapped(newPrimitiveTag(d)).asPrimitiveTag();
    }
    
    default PrimitiveTagAPI<?> makePrimitiveTag(float f) {
        return getWrapped(newPrimitiveTag(f)).asPrimitiveTag();
    }
    
    default PrimitiveTagAPI<?> makePrimitiveTag(int i) {
        return getWrapped(newPrimitiveTag(i)).asPrimitiveTag();
    }
    
    default PrimitiveTagAPI<?> makePrimitiveTag(long l) {
        return getWrapped(newPrimitiveTag(l)).asPrimitiveTag();
    }
    
    default PrimitiveTagAPI<?> makePrimitiveTag(short s) {
        return getWrapped(newPrimitiveTag(s)).asPrimitiveTag();
    }
    
    default StringTagAPI<?> makeStringTag(String value) {
        return getWrapped(newStringTag(value)).asStringTag();
    }
    
    Object newCompoundTag();
    Object newListTag();
    Object newPrimitiveTag(boolean b);
    Object newPrimitiveTag(byte b);
    Object newPrimitiveTag(double d);
    Object newPrimitiveTag(float f);
    Object newPrimitiveTag(int i);
    Object newPrimitiveTag(long l);
    Object newPrimitiveTag(short s);
    Object newStringTag(String value);
    
    default CompoundTagAPI<?> readFromFile(File file) {
        Object tag = null;
        if(Objects.isNull(file)) TILRef.logError("Tried to read NBT data from null file!");
        else {
            try {
                tag = readFromFileDirect(file);
            } catch(Exception ex) {
                TILRef.logWarn("Empty data file {}", file.toPath(), ex.getMessage());
            }
        }
        return getWrapped(Objects.nonNull(tag) ? tag : newCompoundTag()).asCompoundTag();
    }
    
    /**
     * Read directly without any error handling or null checking
     */
    Object readFromFileDirect(File file) throws Exception;
    
    default void writeToFile(CompoundTagAPI<?> tag, File file) {
        if(Objects.nonNull(tag) && !tag.isEmpty() && Objects.nonNull(file)) {
            try {
                writeToFileDirect(tag,file);
            } catch(Exception ex) {
                TILRef.logError("Failed to write tag to file {}",file,ex);
            }
        }
    }
    
    /**
     * Write directly without any error handling or null checking
     */
    void writeToFileDirect(CompoundTagAPI<?> tag, File file) throws Exception;
}