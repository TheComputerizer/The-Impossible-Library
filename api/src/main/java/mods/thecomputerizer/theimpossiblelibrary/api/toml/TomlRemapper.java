package mods.thecomputerizer.theimpossiblelibrary.api.toml;

import mods.thecomputerizer.theimpossiblelibrary.api.toml.Toml.TomlEntry;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

@SuppressWarnings("unused")
public abstract class TomlRemapper {
    
    public void remap(Toml toml) {
        remapTables(toml);
        remapEntries(toml);
        remapChildren(toml);
    }
    
    public void remapChildren(Toml toml) {
        for(Toml table : toml.getAllTables()) {
            TomlRemapper next = getNextRemapper(table.getName());
            if(Objects.nonNull(next)) next.remap(table);
        }
    }
    
    public void remapEntries(Toml toml) {
        Map<TomlEntry<?>,TomlEntry<?>> mappedEntries = new HashMap<>();
        for(TomlEntry<?> entry : toml.getAllEntries()) mappedEntries.put(entry,remapEntry(entry));
        for(Entry<TomlEntry<?>,TomlEntry<?>> entry : mappedEntries.entrySet()) {
            toml.removeEntry(entry.getKey().getKey());
            toml.addEntry(entry.getValue().getKey(),entry.getValue().getValue());
        }
    }
    
    public void remapTables(Toml toml) {
        Map<String,String> mappedNames = new HashMap<>();
        for(Toml table : toml.getAllTables()) mappedNames.put(table.getName(),remapTable(table.getName()));
        for(Entry<String,String> table : mappedNames.entrySet()) toml.remapTables(table.getKey(),table.getValue());
    }
    
    /**
     This is applied after remapTable, so the new name should be checked for if applicable.
     */
    abstract @Nullable TomlRemapper getNextRemapper(String name);
    
    /**
     Return null to remove
     */
    abstract String remapTable(String name);
    
    /**
     Return null to remove
     */
    abstract TomlEntry<?> remapEntry(TomlEntry<?> entry);
}