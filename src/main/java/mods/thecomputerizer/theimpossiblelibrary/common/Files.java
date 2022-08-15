package mods.thecomputerizer.theimpossiblelibrary.common;

import mods.thecomputerizer.theimpossiblelibrary.TheImpossibleLibrary;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class Files {

    public static void generateNestedFile(String path) {
        generateNestedFile(new File(path));
    }

    public static void generateNestedFile(File file) {
        boolean success = true;
        try {
            if(!file.getParentFile().exists()) success = file.getParentFile().mkdirs();
            if(success && !file.exists()) success = file.createNewFile();
        } catch (Exception e) {
            TheImpossibleLibrary.logError("Failed to create file at path "+file.getAbsolutePath(),e);
        }
        if(!success) TheImpossibleLibrary.logError("Failed to create file at path "+file.getAbsolutePath(),null);
    }

    public static void writeLineToFile(String path, String text) {
        writeLineToFile(new File(path),text);
    }

    public static void writeLineToFile(File file, String text) {
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(text);
            writer.close();
        } catch (Exception e) {
            TheImpossibleLibrary.logError("Failed to write line "+text+" to file "+file.getAbsolutePath(),e);
        }
    }

    public static void writeLinesToFile(String path, List<String> text) {
        writeLinesToFile(new File(path),text);
    }

    public static void writeLinesToFile(File file, List<String> text) {
        try {
            FileWriter writer = new FileWriter(file);
            for(String line : text) writer.write(line);
            writer.close();
        } catch (Exception e) {
            TheImpossibleLibrary.logError("Failed to write lines to file "+file.getAbsolutePath(),e);
        }
    }
}
