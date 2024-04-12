package mods.thecomputerizer.theimpossiblelibrary.api.io;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class FileHelper {

    public static File get(String path, boolean overrideExisting) {
        return get(new File(path), overrideExisting);
    }

    public static File get(File file, boolean overrideExisting) {
        boolean canProceed = !file.exists();
        if(!canProceed && overrideExisting) {
            canProceed = file.delete();
            if(!canProceed) TILRef.logError("Failed to delete existing file at path {}",file.getAbsolutePath());
        }
        if(canProceed) {
            boolean success = true;
            try {
                if(!file.getParentFile().exists()) success = file.getParentFile().mkdirs();
                if(success) success = file.createNewFile();
            } catch(Exception ex) {
                TILRef.logError("Failed to create file at path {}",file.getAbsolutePath(),ex);
                return null;
            }
            if(!success) {
                TILRef.logError("Failed to create file at path {}",file.getAbsolutePath());
                return null;
            }
        }
        return file;
    }

    public static void writeLine(String path, String text, boolean append) {
        writeLine(new File(path),text,append);
    }

    public static void writeLine(File file, String text, boolean append) {
        try {
            if(!file.exists()) get(file,true);
            BufferedWriter writer = new BufferedWriter(new FileWriter(file,append));
            writer.write(text);
            writer.close();
        } catch (Exception ex) {
            TILRef.logError("Failed to write line {} to file {}",text,file.getAbsolutePath(),ex);
        }
    }

    public static void writeLines(String path, List<String> text, boolean append) {
        writeLines(new File(path),text,append);
    }

    public static void writeLines(File file, List<String> text, boolean append) {
        try {
            if(!file.exists()) get(file,true);
            BufferedWriter writer = new BufferedWriter(new FileWriter(file,append));
            for(String line : text) writeLine(writer,file,line,append);
            writer.close();
        } catch(Exception ex) {
            TILRef.logError("Failed to write lines {} to file {}",text,file.getAbsolutePath(),ex);
        }
    }

    public static void writeLines(BufferedWriter writer, String path, List<String> text, boolean append) {
        writeLines(writer,new File(path),text,append);
    }

    public static void writeLines(BufferedWriter writer, File file, List<String> text, boolean append) {
        for(String line : text) writeLine(writer,file,line,append);
    }

    public static void writeLine(BufferedWriter writer, File file, String text, boolean append) {
        try {
            if(!file.exists()) get(file,true);
            if(append) writer.append(text);
            else {
                writer.write(text);
                writer.newLine();
            }
        } catch(Exception ex) {
            TILRef.logError("Failed to write line {} to file {}",text,file.getAbsolutePath(),ex);
        }
    }
}
