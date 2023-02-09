package mods.thecomputerizer.theimpossiblelibrary.common.toml;

import mods.thecomputerizer.theimpossiblelibrary.util.TextUtil;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

/**
 * A basic variable type line handling anything the TOML format accepts.
 * Accepted types are string, boolean, long, double, Date, and list, where list can have a subset of any accepted type
 * Assumes the entire variable and its values are contained within a single line
 */
public class Variable extends AbstractType {

    /**
     * The name the variable is assigned to
     */
    private final String name;

    /**
     * The value of the variable
     */
    private final Object value;

    public Variable(int absoluteIndex, @Nullable Table parentTable, String name, Object value) {
        super(absoluteIndex, parentTable);
        this.name = name;
        this.value = value;
    }

    public String getValueType() {
        return this.value.getClass().getName();
    }

    @Override
    public List<String> toLines() {
        return Collections.singletonList(getSpacing()+this.name+" = "+
                (!(this.value instanceof List<?>) ? !(this.value instanceof String) ?
                        this.value : "\""+this.value+"\"" : TextUtil.compileCollection((List<?>)this.value)));
    }
}
