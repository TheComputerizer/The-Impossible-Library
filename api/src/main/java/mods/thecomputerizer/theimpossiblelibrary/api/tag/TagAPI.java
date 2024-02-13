package mods.thecomputerizer.theimpossiblelibrary.api.tag;

import java.io.File;
import java.io.IOException;

public interface TagAPI {

    CompoundTagAPI makeCompoundTag();
    ListTagAPI makeListTag();
    CompoundTagAPI readFromFile(File file) throws IOException;
    void writeToFile(CompoundTagAPI tag, File file) throws IOException;
}