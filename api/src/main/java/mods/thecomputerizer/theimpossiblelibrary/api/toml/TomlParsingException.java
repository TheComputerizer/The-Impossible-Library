package mods.thecomputerizer.theimpossiblelibrary.api.toml;

import java.text.ParseException;

public class TomlParsingException extends ParseException {
    
    /**
     Constructs a TomlParsingException with the specified detail message and offset. A detail message is a String
     that describes this particular exception.
     
     @param msg the detail message
     @param errorOffset the position where the error is found while parsing.
     */
    public TomlParsingException(String msg, int errorOffset) {
        super(msg,errorOffset);
    }
}
