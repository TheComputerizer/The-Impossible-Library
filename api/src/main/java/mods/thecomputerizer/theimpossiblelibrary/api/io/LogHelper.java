package mods.thecomputerizer.theimpossiblelibrary.api.io;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ArrayHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.channels.ClosedChannelException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.function.Function;

import static java.nio.charset.StandardCharsets.UTF_8;
import static mods.thecomputerizer.theimpossiblelibrary.api.util.Patterns.BRACKETS_LITERAL;
import static org.apache.logging.log4j.Level.*;

@SuppressWarnings("unused")
public class LogHelper {
    
    public static ModLogger create(String modid) {
        return create(modid,null);
    }

    public static ModLogger create(String modid, @Nullable String name) {
        return new ModLogger(modid,name);
    }
    
    public static void logCollection(
            ModLogger logger, Level level, String msg, Collection<Object> elements) {
        logger.log(level,collectionToString(msg,elements,Object::toString,0));
    }

    public static void logCollection(
            ModLogger logger, Level level, String msg, Collection<Object> elements,
            Function<Object,String> toString) {
        logger.log(level,collectionToString(msg,elements,toString,0));
    }

    public static void logCollection(
            ModLogger logger, Level level, String msg, Collection<Object> elements,
            Function<Object,String> toString, int perLine) {
        logger.log(level,collectionToString(msg,elements,toString,perLine));
    }

    public static String collectionToString(
            String msg, Collection<Object> elements, Function<Object, String> toString, int perLine) {
        StringJoiner joiner = new StringJoiner(" ");
        int count = 0;
        for(Object element : elements) {
            String elementStr = Objects.nonNull(element) ? (Objects.nonNull(toString) ?
                    toString.apply(element) : element.toString()) : "null";
            if(count>perLine) {
                joiner.add(elementStr+"\n");
                count = 0;
            } else {
                joiner.add(elementStr);
                count++;
            }
        }
        return StringUtils.isNotEmpty(msg) ? msg+" "+joiner : joiner.toString();
    }
    
    private static String fixRegex(@Nullable Object value) {
        return String.valueOf(value).replace("$","\\$");
    }

    public static String injectParameters(String message, Object ... parameters) {
        if(Objects.isNull(message)) return "null";
        if(ArrayHelper.isNotEmpty(parameters)) {
            for(Object parameter : parameters) {
                if(message.contains("{}")) message = message.replaceFirst(BRACKETS_LITERAL,fixRegex(parameter));
                else break;
            }
        }
        return message;
    }

    public static class ModLogger {
        
        private final File logFile;
        private final String logName;
        private Writer writer;
        @Getter private int linesWritten;

        private ModLogger(String modid, @Nullable String name) {
            this.logFile = FileHelper.get(injectParameters("./logs/mods/{}.log",modid),true);
            this.logName = Objects.nonNull(name) ? name : getModName(modid);
            this.linesWritten = 0;
            this.writer = initWriter();
        }
        
        public void all(String message, Object ... parameters) {
            log(ALL,message,parameters);
        }

        public void debug(String message, Object ... parameters) {
            log(DEBUG,message,parameters);
        }

        public void error(String message, Object ... parameters) {
            log(ERROR,message,parameters);
        }

        public void fatal(String message, Object ... parameters) {
            log(FATAL,message,parameters);
        }
        
        private String finalizeMessage(Level level, String constructedMessage) {
            return formattedTimeStamp(LocalDateTime.now())+" "+formattedLogLevel(level)+": "+constructedMessage+"\n";
        }
        
        private String formattedLogLevel(Level level) {
            String name = Objects.nonNull(level) ? level.name() : "NULL";
            return injectParameters("[{}/{}]",this.logName,name.length()<5 ? name+" " : name);
        }
        
        private String formattedMilli(int milli) {
            if(milli<10) return "00"+milli;
            if(milli<100) return "0"+milli;
            return String.valueOf(milli);
        }
        
        private String formattedTimeStamp(LocalDateTime time) {
            int hour = time.getHour();
            int minute = time.getMinute();
            int second = time.getSecond();
            return injectParameters("[{}:{}:{}:{}]",hour<10 ? "0"+hour : hour,minute<10 ? "0"+minute : minute,
                                    second<10 ? "0"+second : second, formattedMilli(time.getNano()/1000000));
        }
        
        /**
         Gets the name of a mod from a given modid or returns the modid if it cannot be found
         */
        private String getModName(String modid) {
            String name = ModHelper.getModName(modid);
            return StringUtils.isNotBlank(name) ? name : modid;
        }

        public void info(String message, Object ... parameters) {
            log(INFO,message,parameters);
        }
        
        Writer initWriter() {
            Path path = this.logFile.toPath();
            try {
                return new OutputStreamWriter(Files.newOutputStream(path),UTF_8);
            } catch(Exception ex) {
                TILRef.logError("Could not create log file for {}",this.logName,ex);
                return null;
            }
        }
        
        public void log(Level level, String msg, Object ... parameters) {
            String message = finalizeMessage(level,injectParameters(msg,parameters));
            if(Objects.isNull(this.writer)) {
                TILRef.logError("Tried to write the following message with a null writer: `{}`",message);
                this.writer = initWriter();
            } else writeMessage(message);
        }

        public void off(String message, Object ... parameters) {
            log(OFF,message,parameters);
        }

        public void trace(String message, Object ... parameters) {
            log(TRACE,message,parameters);
        }

        public void warn(String message, Object ... parameters) {
            log(WARN,message,parameters);
        }
        
        void writeMessage(String message) {
            try {
                this.writer.write(message);
                this.writer.flush();
                this.linesWritten++;
            } catch(IOException ex) {
                TILRef.logError("Failed to write message to {}: `{}`",this.logFile,message,ex);
                if(ex instanceof ClosedChannelException) {
                    TILRef.logInfo("Attempting to reinitialize closed logger");
                    this.writer = initWriter();
                }
            }
        }
    }
}