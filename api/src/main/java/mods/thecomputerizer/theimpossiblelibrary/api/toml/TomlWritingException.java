package mods.thecomputerizer.theimpossiblelibrary.api.toml;

public class TomlWritingException extends Exception {
    
    public TomlWritingException(String message) {
        super(message);
    }
    
    public TomlWritingException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public TomlWritingException(Throwable cause) {
        super(cause);
    }
}