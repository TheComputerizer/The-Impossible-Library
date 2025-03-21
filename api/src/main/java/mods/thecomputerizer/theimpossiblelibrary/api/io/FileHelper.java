package mods.thecomputerizer.theimpossiblelibrary.api.io;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;

import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FileHelper {
    
    /**
     * Assumes the input file has already been confirmed to not exist
     */
    public static boolean create(File file) {
        try {
            File parent = file.getParentFile();
            if(Objects.nonNull(parent) && !parent.exists()) {
                if(parent.mkdirs())
                    TILRef.logDebug("[FileHelper]: Successfully created parent directories for {}",file);
                else {
                    TILRef.logError("[FileHelper]: Failed to create directories for {} ({})",file,parent);
                    return false;
                }
            }
            if(file.createNewFile()) {
                TILRef.logDebug("[FileHelper]: Successfully created new file {}",file);
                return true;
            }
            TILRef.logError("[FileHelper]: Failed to create file {}",file);
        } catch(Exception ex) {
            TILRef.logError("[FileHelper]: Failed to create file at path {}",file.getAbsolutePath(),ex);
        }
        return false;
    }
    
    public static Function<Path,URL> defaultPathToURL() {
        return path -> {
            try {
                return path.toAbsolutePath().toUri().toURL();
            } catch(Exception ex) {
                TILRef.logError("[FileHelper]: Failed to get URL from path {}",path);
            }
            return null;
        };
    }
    
    public static Function<URL,URI> defaultURLToURI() {
        return url -> {
            try {
                return url.toURI();
            } catch(Exception ex) {
                TILRef.logError("[FileHelper]: Failed to get URI from URL {}",url);
            }
            return null;
        };
    }
    
    public static Function<URI,URL> defaultURIToURL() {
        return uri -> {
            try {
                return uri.toURL();
            } catch(Exception ex) {
                TILRef.logError("[FileHelper]: Failed to get URL from URI {}",uri);
            }
            return null;
        };
    }
    
    public static Function<URL,Path> defaultURLToPath() {
        return url -> {
            try {
                return Paths.get(url.toURI()).toAbsolutePath();
            } catch(Exception ex) {
                TILRef.logError("[FileHelper]: Failed to get path from URL {}",url);
            }
            return null;
        };
    }
    
    public static File get(URL url) {
        return get(url,false,defaultURLToPath());
    }
    
    public static File get(URL url, Function<URL,Path> toPath) {
        return get(url,false,toPath);
    }
    
    public static File get(URL url, boolean createIfAbsent) {
        return get(url,createIfAbsent,defaultURLToPath());
    }
    
    public static File get(URL url, boolean createIfAbsent, Function<URL,Path> toPath) {
        return get(toPath.apply(url),createIfAbsent);
    }
    
    public static File get(URI uri) {
        return get(uri,false,Paths::get);
    }
    
    public static File get(URI uri, Function<URI,Path> toPath) {
        return get(uri,false,toPath);
    }
    
    public static File get(URI uri, boolean createIfAbsent) {
        return get(uri,createIfAbsent,Paths::get);
    }
    
    public static File get(URI uri, boolean createIfAbsent, Function<URI,Path> toPath) {
        return get(toPath.apply(uri),createIfAbsent);
    }
    
    public static File get(Path path) {
        return get(path,false);
    }
    
    public static File get(Path path, boolean createIfAbsent) {
        File file = path.toFile();
        if(!file.exists() && createIfAbsent) create(file);
        return file;
    }
    
    public static File get(String path) {
        return get(new File(path),false);
    }

    public static File get(String path, boolean overrideExisting) {
        return get(new File(path),overrideExisting);
    }
    
    public static File get(File parent, String path) {
        return get(parent,path,false);
    }
    
    public static File get(File parent, String path, boolean overrideExisting) {
        return get(parent.toPath()+"/"+path,overrideExisting);
    }

    public static File get(File file, boolean overrideExisting) {
        boolean canProceed = !file.exists();
        if(!canProceed) {
            if(overrideExisting) {
                canProceed = file.delete();
                if(!canProceed) TILRef.logError("[FileHelper]: Failed to delete existing file at path {}",
                                                file.getAbsolutePath());
            }
        }
        return canProceed ? (create(file) ? file : null) : file;
    }
    
    public static @Nullable BufferedReader getCheckedLineReader(File file) {
        if(!file.exists() && !create(file)) {
            TILRef.logError("[FileHelper]: Cannot read from a file that does not exist: Failed to create file {}",file);
            return null;
        }
        try {
            return getLineReader(file);
        } catch(IOException ex) {
            TILRef.logError("[FileHelper]: Failed to open BufferedReader for file {}",file.getAbsolutePath(),ex);
        }
        return null;
    }
    
    /**
     * Get a BufferedWriter for a file after it is verified that the file exists.
     * Returns null if the input file is null, the input file does not exist, or the BufferedWriter throws an exception.
     */
    public static @Nullable BufferedWriter getCheckedLineWriter(File file, boolean append) {
        if(!file.exists() && !create(file)) {
            TILRef.logError("[FileHelper]: Cannot write to file that does not exist: Failed to create file {}",file);
            return null;
        }
        try {
            return getLineWriter(file, append);
        } catch(IOException ex) {
            TILRef.logError("[FileHelper]: Failed to open BufferedWriter for file {}",file.getAbsolutePath(),ex);
        }
        return null;
    }
    
    public static BufferedReader getLineReader(File file) throws FileNotFoundException {
        return new BufferedReader(new FileReader(file));
    }
    
    /**
     * Get a BufferedWriter for a file without checking if the file exists
     */
    public static BufferedWriter getLineWriter(File file, boolean append) throws IOException {
        return new BufferedWriter(new FileWriter(file,append));
    }
    
    public static File[] list(File root) {
        return list(root,file -> true);
    }
    
    public static File[] list(File root, Predicate<File> filter) {
        File[] files = Objects.nonNull(root) ? root.listFiles(filter::test) : null;
        return Objects.nonNull(files) ? files : new File[]{};
    }
    
    @IndirectCallers
    public static List<String> toLines(URL url) {
        return toLines(get(url));
    }
    
    @IndirectCallers
    public static List<String> toLines(URI uri) {
        return toLines(get(uri));
    }
    
    @IndirectCallers
    public static List<String> toLines(Path path) {
        return toLines(get(path));
    }
    
    public static List<String> toLines(String filePath) {
        return toLines(filePath,false);
    }
    
    public static List<String> toLines(String filePath, boolean createNewFile) {
        File file = new File(filePath);
        if(!file.exists() && !createNewFile) {
            TILRef.logError("Cannot read lines from nonexistant file {}",filePath);
            return Collections.emptyList();
        }
        return toLines(get(filePath));
    }
    
    public static List<String> toLines(File file) {
        List<String> lines = Collections.emptyList();
        try(BufferedReader reader = getCheckedLineReader(file)) {
            if(Objects.nonNull(reader)) lines = reader.lines().collect(Collectors.toList());
        } catch(Exception ex) {
            TILRef.logError("Failed to read lines from file {}",file,ex);
        }
        return lines;
    }
    
    public static Path toPath(File file) {
        return toPath(file,f -> f.toPath().toAbsolutePath());
    }
    
    public static Path toPath(File file, Function<File,Path> toPath) {
        if(Objects.isNull(file)) TILRef.logError("[FileHelper]: Cannot convert null file to path!");
        else return toPath.apply(file);
        return null;
    }
    
    public static Path toPath(String filePath) {
        return toPath(filePath,s ->toPath(new File(s)));
    }
    
    public static Path toPath(String filePath, Function<String,Path> toPath) {
        if(Objects.isNull(filePath) || filePath.isEmpty())
            TILRef.logError("[FileHelper]: Cannot convert null or empty filepath String to URI!");
        else return toPath.apply(filePath);
        return null;
    }
    
    public static Path toPath(URI uri) {
        return toPath(uri,u -> Paths.get(u).toAbsolutePath());
    }
    
    public static Path toPath(URI uri, Function<URI,Path> toPath) {
        if(Objects.isNull(uri)) TILRef.logError("[FileHelper]: Cannot convert null URI to path!");
        else return toPath.apply(uri);
        return null;
    }
    
    public static Path toPath(URL url) {
        return toPath(url,defaultURLToPath());
    }
    
    public static Path toPath(URL url, Function<URL,Path> toPath) {
        if(Objects.isNull(url)) TILRef.logError("[FileHelper]: Cannot convert null URL to path!");
        else return toPath.apply(url);
        return null;
    }
    
    @IndirectCallers
    public static URI toURI(File file) {
        return toURI(file,File::toURI);
    }
    
    public static URI toURI(File file, Function<File,URI> toURI) {
        if(Objects.isNull(file))
            TILRef.logError("[FileHelper]: Cannot convert null file to URI!");
        else if(!file.exists())
            TILRef.logError("[FileHelper]: Cannot convert file that does not exist to URI! {}",file);
        else return toURI.apply(file);
        return null;
    }
    
    @IndirectCallers
    public static URI toURI(Path path) {
        return toURI(path,p -> p.toAbsolutePath().toUri());
    }
    
    public static URI toURI(Path path, Function<Path,URI> toURI) {
        if(Objects.isNull(path)) TILRef.logError("[FileHelper]: Cannot convert null path to URI!");
        else return toURI.apply(path);
        return null;
    }
    
    @IndirectCallers
    public static URI toURI(String filePath) {
        return toURI(filePath,s ->toURI(new File(s)));
    }
    
    public static URI toURI(String filePath, Function<String,URI> toURI) {
        if(Objects.isNull(filePath) || filePath.isEmpty())
            TILRef.logError("[FileHelper]: Cannot convert null or empty filepath String to URI!");
        else return toURI.apply(filePath);
        return null;
    }
    
    @IndirectCallers
    public static URI toURI(URL url) {
        return toURI(url,defaultURLToURI());
    }
    
    public static URI toURI(URL url, Function<URL,URI> toURI) {
        if(Objects.isNull(url)) TILRef.logError("[FileHelper]: Cannot convert null URL to URI!");
        else return toURI.apply(url);
        return null;
    }
    
    @IndirectCallers
    public static URL toURL(File file) {
        if(Objects.isNull(file))
            TILRef.logError("[FileHelper]: Cannot convert null file to URL!");
        else if(!file.exists())
            TILRef.logError("[FileHelper]: Cannot convert file that does not exist to URL! {}",file);
        else return toURL(file.toPath().toAbsolutePath());
        return null;
    }
    
    public static URL toURL(Path path) {
        return toURL(path,defaultPathToURL());
    }
    
    public static URL toURL(Path path, Function<Path,URL> toURL) {
        if(Objects.isNull(path)) TILRef.logError("[FileHelper]: Cannot convert null path to URL!");
        else return toURL.apply(path);
        return null;
    }
    
    @IndirectCallers
    public static URL toURL(String filePath) {
        return toURL(filePath,s -> toURL(new File(s)));
    }
    
    public static URL toURL(String filePath, Function<String,URL> toURL) {
        if(Objects.isNull(filePath) || filePath.isEmpty())
            TILRef.logError("[FileHelper]: Cannot convert null or empty filepath String to URI!");
        else return toURL.apply(filePath);
        return null;
    }
    
    @IndirectCallers
    public static URL toURL(URI uri) {
        return toURL(uri,defaultURIToURL());
    }
    
    public static URL toURL(URI uri, Function<URI,URL> toURL) {
        if(Objects.isNull(uri)) TILRef.logError("[FileHelper]: Cannot convert null URI to URL!");
        else return toURL.apply(uri);
        return null;
    }
    
    public static void tryCloseWriter(Writer writer) {
        if(Objects.isNull(writer)) {
            TILRef.logWarn("[FileHelper]: Tried to close a null writer!");
        }
        try {
            writer.close();
        } catch(IOException ex) {
            TILRef.logError("[FileHelper]: Failed to close writer {}",writer,ex);
        }
    }

    @IndirectCallers
    public static void writeLine(String path, String text, boolean append) {
        writeLine(new File(path),text,append);
    }

    public static void writeLine(File file, String text, boolean append) {
        BufferedWriter writer = getCheckedLineWriter(file, append);
        if(Objects.nonNull(writer)) {
            writeLine(file,text,writer);
            tryCloseWriter(writer);
        } else TILRef.logError("[FileHelper]: Failed to write line ({}) to file ({})",text,file);
    }
    
    /**
     * This is a separate method in the
     */
    public static void writeLine(File file, String text, BufferedWriter writer) {
        try {
            writer.write(text);
            writer.newLine();
        } catch (Exception ex) {
            TILRef.logError("[FileHelper]: Failed to write line {} to file {}",text,file.getAbsolutePath(),ex);
        }
    }
    
    public static BufferedWriter writeLine(File file, String text, boolean append, BufferedWriter writer) {
        if(Objects.isNull(writer)) writer = writeLineAndReturnWriter(file,text,append);
        else writeLine(file,text,writer);
        return writer;
    }
    
    public static BufferedWriter writeLineAndReturnWriter(File file, String text, boolean append) {
        BufferedWriter writer = getCheckedLineWriter(file,append);
        if(Objects.nonNull(writer)) writeLine(file,text,writer);
        else TILRef.logError("[FileHelper]: Failed to write line ({}) to file ({})",text,file);
        return writer;
    }
    
    @IndirectCallers
    public static void writeLines(String path, List<String> text, boolean append) {
        writeLines(new File(path),text,append);
    }

    public static void writeLines(File file, List<String> text, boolean append) {
        BufferedWriter writer = null;
        for(String line : text) writer = writeLine(file,line,append,writer);
        if(Objects.nonNull(writer)) tryCloseWriter(writer);
    }
    
    @IndirectCallers
    public static BufferedWriter writeLinesAndReturnWriter(File file, List<String> text, boolean append) {
        return writeLines(file,text,append,null);
    }
    
    public static BufferedWriter writeLines(File file, List<String> text, boolean append, BufferedWriter writer) {
        for(String line : text) writer = writeLine(file,line,append,writer);
        return writer;
    }
}
