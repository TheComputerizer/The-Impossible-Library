package mods.thecomputerizer.theimpossiblelibrary.api.toml;

import mods.thecomputerizer.theimpossiblelibrary.api.toml.Toml.TomlEntry;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

@SuppressWarnings("unused")
public abstract class TomlRemapper {
    
    /**
     Returns true if anything is remapped
     */
    public boolean remap(Toml toml) {
        boolean remapped = remapTables(toml);
        remapped = remapEntries(toml) || remapped;
        remapped = remapChildren(toml) || remapped;
        return remapped;
    }
    
    /**
     Returns true if any children are remapped
     */
    public boolean remapChildren(Toml toml) {
        boolean remapped = false;
        for(Toml table : toml.getAllTables()) {
            TomlRemapper next = getNextRemapper(table.getName());
            if(Objects.nonNull(next) && next.remap(table)) remapped = true;
        }
        return remapped;
    }
    
    /**
     Returns true if any entries are remapped
     */
    public boolean remapEntries(Toml toml) {
        boolean remapped = false;
        Map<TomlEntry<?>,TomlEntry<?>> mappedEntries = new HashMap<>();
        for(TomlEntry<?> entry : toml.getAllEntries()) mappedEntries.put(entry,remapEntry(toml,entry));
        for(Entry<TomlEntry<?>,TomlEntry<?>> entry : mappedEntries.entrySet()) {
            TomlEntry<?> original = entry.getKey();
            TomlEntry<?> mapped = entry.getValue();
            if(original==mapped) continue;
            remapped = true;
            toml.removeEntry(original.getKey());
            if(Objects.nonNull(original.getComments()) && Objects.nonNull(mapped))
                mapped.setComments(Arrays.asList(original.getComments()));
            toml.addEntry(mapped);
        }
        return remapped;
    }
    
    /**
     Returns true if any tables are remapped
     */
    public boolean remapTables(Toml toml) {
        boolean remapped = false;
        Map<String,String> mappedNames = new HashMap<>();
        for(Toml table : toml.getAllTables()) mappedNames.put(table.getName(),remapTable(table.getName()));
        for(Entry<String,String> table : mappedNames.entrySet()) {
            if(table.getKey().equals(table.getValue())) continue;
            remapped = true;
            toml.remapTables(table.getKey(),table.getValue());
        }
        return remapped;
    }
    
    /**
     This is applied after remapTable, so the new name should be checked for if applicable.
     */
    public abstract @Nullable TomlRemapper getNextRemapper(String name);
    
    /**
     Return null to remove
     */
    public abstract String remapTable(String name);
    
    /**
     Return null to remove
     */
    public abstract TomlEntry<?> remapEntry(Toml parent, TomlEntry<?> entry);
}