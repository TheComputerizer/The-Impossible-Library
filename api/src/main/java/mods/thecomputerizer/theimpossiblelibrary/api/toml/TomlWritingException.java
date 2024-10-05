package mods.thecomputerizer.theimpossiblelibrary.api.toml;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;

public class TomlWritingException extends Exception {
    
    public TomlWritingException(String message) {
        super(message);
    }
    
    @IndirectCallers
    public TomlWritingException(String message, Throwable cause) {
        super(message, cause);
    }
    
    @IndirectCallers
    public TomlWritingException(Throwable cause) {
        super(cause);
    }
}