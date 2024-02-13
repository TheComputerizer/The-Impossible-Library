package mods.thecomputerizer.theimpossiblelibrary.api.io;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.ModAPI;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;

@SuppressWarnings("unused")
public class LogHelper {

    public static ModLogger create(String modid) {
        return new ModLogger(modid);
    }

    public static void logCollection(ModLogger LOG, Level level, String startingMessage, Collection<Object> collection,
                                     Function<Object, String> toMessage) {
        LOG.log(level, collectionToString(startingMessage, collection, toMessage, 0));
    }

    public static void logCollection(ModLogger LOG, Level level, String startingMessage, Collection<Object> collection,
                                     Function<Object, String> toMessage, int objectsPerLine) {
        LOG.log(level, collectionToString(startingMessage, collection, toMessage, objectsPerLine));
    }

    public static String collectionToString(String startingMessage, Collection<Object> collection,
                                     Function<Object, String> toMessage, int objectsPerLine) {
        StringBuilder messageBuilder = new StringBuilder();
        if(Objects.nonNull(startingMessage)) messageBuilder.append(startingMessage).append(": ");
        int index = 0;
        for(Object object : collection) {
            messageBuilder.append(toMessage.apply(object));
            if(index!=0 && index%objectsPerLine==0) messageBuilder.append("\n");
            if(index<collection.size()-1) {
                messageBuilder.append(" ");
                index++;
            }
        }
        return messageBuilder.toString();
    }

    public static String injectParameters(String message, Object ... parameters) {
        if(Objects.isNull(message)) return "null";
        int index = 0;
        while(Objects.nonNull(parameters) && message.contains("{}")) {
            String replacement = "";
            if(index<parameters.length) {
                Object parameter = parameters[index];
                replacement = Objects.nonNull(parameter) ? parameter.toString() : "null";
            }
            message = message.replaceFirst("\\{}",replacement);
            index++;
        }
        return message;
    }

    public static void logLevel(Logger LOG, Level level, String message, Object ... parameters) {
        LOG.log(level, message, parameters);
    }

    public static class ModLogger {
        private Writer writer;
        private String logName;
        @Getter private int linesWritten;

        private ModLogger(String modid) {
            try {
                File logFile = FileHelper.get(injectParameters("./logs/mods/{}.log",modid),true);
                this.writer = new OutputStreamWriter(Files.newOutputStream(Paths.get(logFile.toURI())),StandardCharsets.UTF_8);
                this.logName = getModName(modid);
                this.linesWritten = 0;
            } catch(Exception e) {
                TILRef.logError("Could not create log file for {}",modid,e);
            }
        }

        /**
            Gets the name of a mod from a given modid or returns the modid if it cannot be found
         */
        private String getModName(String modid) {
            CommonAPI api = TILRef.getCommonAPI();
            ModAPI mod = Objects.nonNull(api) ? api.getModAPI() : null;
            String name = Objects.nonNull(mod) ? mod.getModName(modid) : null;
            return StringUtils.isNotBlank(name) ? name : modid;
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

        private String formattedLogLevel(Level level) {
            String name = Objects.nonNull(level) ? level.name() : "NULL";
            return injectParameters("[{}/{}]",this.logName, name.length()<5 ? name+" " : name);
        }

        private String finalizeMessage(Level level, String constructedMessage) {
            return formattedTimeStamp(LocalDateTime.now())+" "+formattedLogLevel(level)+": "+constructedMessage+"\n";
        }

        public void log(Level level, String message, Object ... parameters) {
            try {
                this.writer.write(finalizeMessage(level, injectParameters(message, parameters)));
                this.writer.flush();
                this.linesWritten++;
            } catch(IOException ex) {
                TILRef.log(level,"Failed to write message to external log for {}",this.logName,ex);
            }
        }

        public void all(String message, Object ... parameters) {
            log(Level.ALL,message,parameters);
        }

        public void debug(String message, Object ... parameters) {
            log(Level.DEBUG,message,parameters);
        }

        public void error(String message, Object ... parameters) {
            log(Level.ERROR,message,parameters);
        }

        public void fatal(String message, Object ... parameters) {
            log(Level.FATAL,message,parameters);
        }

        public void info(String message, Object ... parameters) {
            log(Level.INFO,message,parameters);
        }

        public void off(String message, Object ... parameters) {
            log(Level.OFF,message,parameters);
        }

        public void trace(String message, Object ... parameters) {
            log(Level.TRACE,message,parameters);
        }

        public void warn(String message, Object ... parameters) {
            log(Level.WARN,message,parameters);
        }
    }
}
