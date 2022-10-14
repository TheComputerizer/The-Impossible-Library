package mods.thecomputerizer.theimpossiblelibrary.util;

import mods.thecomputerizer.theimpossiblelibrary.Constants;
import mods.thecomputerizer.theimpossiblelibrary.common.Files;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.Collection;
import java.util.function.Function;

@SuppressWarnings("unused")
public class LogUtil {

    public static void logInternal(Level level, String message, Object ... parameters) {
        Constants.LOGGER.log(level, message, parameters);
    }

    public static void logInternalCollection(Level level, String startingMessage, Collection<Object> collection,
                                             Function<Object, String> toMessage, int objectsPerLine) {
        Constants.LOGGER.log(level, collectionToString(startingMessage, collection, toMessage, objectsPerLine));
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
        if(startingMessage!=null) messageBuilder.append(startingMessage).append(": ");
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

    public static void logLevel(Logger LOG, Level level, String message, Object ... parameters) {
        LOG.log(level, message, parameters);
    }

    public static class ModLogger {
        private Writer writer;
        private String logName;

        private ModLogger(String modid) {
            try {
                File logFile = Files.generateNestedFile(injectParameters("./logs/mods/{}.log",modid),true);
                this.writer = new OutputStreamWriter(java.nio.file.Files.newOutputStream(Paths.get(logFile.toURI())), StandardCharsets.UTF_8);
                this.logName = getModName(modid);
            } catch (Exception e) {
                logInternal(Level.ERROR,"Could not create log file for {}",modid,e);
            }
        }

        /*
            Gets the name of a mod from a given modid or returns the modid if it cannot be found
         */
        private String getModName(String modid) {
            for(ModContainer mod : Loader.instance().getModList()) if(mod.getModId().matches(modid)) return mod.getName();
            return modid;
        }

        private String injectParameters(String message, Object ... parameters) {
            int index = 0;
            while(message.contains("{}")) {
                String replacement = "";
                if(index<parameters.length) replacement = parameters[index].toString();
                message = message.replaceFirst("\\{}",replacement);
                index++;
            }
            return message;
        }

        private String formattedTimeStamp(LocalDateTime time) {
            return injectParameters("[{}:{}:{}:{}]",time.getHour(),time.getMinute(),time.getSecond(), ChronoField.MICRO_OF_SECOND);
        }

        private String formattedLogLevel(Level level) {
            return injectParameters("[{}/{}]",this.logName,level.name());
        }

        private String finalizeMessage(Level level, String constructedMessage) {
            return formattedTimeStamp(LocalDateTime.now())+" "+formattedLogLevel(level)+": "+constructedMessage+"\n";
        }

        public void log(Level level, String message, Object ... parameters) {
            try {
                this.writer.write(finalizeMessage(level, injectParameters(message, parameters)));
                this.writer.flush();
            } catch (IOException e) {
                logInternal(level, "Failed to write message to external log for {}",this.logName,e);
            }
        }

        public void debug(String message, Object ... parameters) {
            log(Level.DEBUG, message, parameters);
        }

        public void info(String message, Object ... parameters) {
            log(Level.INFO, message, parameters);
        }

        public void warn(String message, Object ... parameters) {
            log(Level.WARN, message, parameters);
        }

        public void error(String message, Object ... parameters) {
            log(Level.ERROR, message, parameters);
        }

        public void fatal(String message, Object ... parameters) {
            log(Level.FATAL, message, parameters);
        }
    }
}
