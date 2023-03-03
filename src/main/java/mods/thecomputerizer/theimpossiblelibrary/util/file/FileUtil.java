package mods.thecomputerizer.theimpossiblelibrary.util.file;

import org.apache.logging.log4j.Level;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class FileUtil {

    public static File generateNestedFile(String path, boolean overrideExisting) {
        return generateNestedFile(new File(path), overrideExisting);
    }

    public static File generateNestedFile(File file, boolean overrideExisting) {
        boolean canProceed = !file.exists();
        if(!canProceed && overrideExisting) {
            canProceed = file.delete();
            if(!canProceed) LogUtil.logInternal(Level.ERROR,"Failed to delete existing file at path {}",file.getAbsolutePath());
        }
        if(canProceed) {
            boolean success = true;
            try {
                if (!file.getParentFile().exists()) success = file.getParentFile().mkdirs();
                if (success) success = file.createNewFile();
            } catch (Exception e) {
                LogUtil.logInternal(Level.ERROR,"Failed to create file at path {}",file.getAbsolutePath(), e);
                return null;
            }
            if (!success) {
                LogUtil.logInternal(Level.ERROR,"Failed to create file at path {}",file.getAbsolutePath());
                return null;
            }
        }
        return file;
    }

    public static void writeLineToFile(String path, String text, boolean append) {
        writeLineToFile(new File(path),text, append);
    }

    public static void writeLineToFile(File file, String text, boolean append) {
        try {
            if(!file.exists()) generateNestedFile(file,true);
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, append));
            writer.write(text);
            writer.close();
        } catch (Exception e) {
            LogUtil.logInternal(Level.ERROR,"Failed to write line {} to file {}",text,file.getAbsolutePath(),e);
        }
    }

    public static void writeLinesToFile(String path, List<String> text, boolean append) {
        writeLinesToFile(new File(path),text, append);
    }

    public static void writeLinesToFile(File file, List<String> text, boolean append) {
        try {
            if(!file.exists()) generateNestedFile(file,true);
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, append));
            for (String line : text) writeLineToFile(writer, file, line, append);
            writer.close();
        } catch (Exception e) {
            LogUtil.logInternal(Level.ERROR,"Failed to write lines {} to file {}",text,file.getAbsolutePath(),e);
        }
    }

    public static void writeLinesToFile(BufferedWriter writer, String path, List<String> text, boolean append) {
        writeLinesToFile(writer, new File(path),text, append);
    }

    public static void writeLinesToFile(BufferedWriter writer, File file, List<String> text, boolean append) {
        for (String line : text) writeLineToFile(writer, file, line, append);
    }

    public static void writeLineToFile(BufferedWriter writer, File file, String text, boolean append) {
        try {
            if(!file.exists()) generateNestedFile(file,true);
            if(append) writer.append(text);
            else {
                writer.write(text);
                writer.newLine();
            }
        } catch (Exception e) {
            LogUtil.logInternal(Level.ERROR,"Failed to write line {} to file {}",text,file.getAbsolutePath(),e);
        }
    }
}
