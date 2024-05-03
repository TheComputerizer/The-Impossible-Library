package mods.thecomputerizer.theimpossiblelibrary.api.core.asm;

import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelper;
import org.apache.commons.lang3.tuple.Pair;
import org.objectweb.asm.AnnotationVisitor;

import java.util.*;
import java.util.Map.Entry;

public class AnnotationPrinter extends AnnotationVisitor implements BytecodePrinter {

    protected final ClassPrinter parent;
    protected final String name;
    protected Map<String,Object> values;

    protected AnnotationPrinter(int api, ClassPrinter parent, String name) {
        super(api);
        this.parent = parent;
        this.name = name;
    }

    protected void addValue(String name, Object value) {
        if(Objects.isNull(this.values)) this.values = new HashMap<>();
        this.values.put(name,value.toString());
    }

    protected String getValueStr(Object value, int tabs) {
        if(value instanceof BytecodePrinter) {
            List<String> lines = new ArrayList<>();
            ((BytecodePrinter)value).toLines(lines,tabs);
            return TextHelper.fromIterable(lines, "");
        }
        return value instanceof String ? "\""+value+"\"" : value.toString();
    }

    protected AnnotationPrinter parseAnnotation(String desc) {
        Pair<String,String> pkgPair = ClassPrinter.splitPackage(ClassPrinter.getClassPath(desc));
        this.parent.addImport(pkgPair.getLeft());
        return new AnnotationPrinter(this.api,this.parent,pkgPair.getRight());
    }

    @Override
    public void toLines(Collection<String> lines, int tabs) {
        StringJoiner valueJoiner = new StringJoiner(", ");
        if(Objects.nonNull(this.values))
            for(Entry<String,Object> value : this.values.entrySet())
                valueJoiner.add(value.getKey()+" = "+getValueStr(value.getValue(),tabs));
        String values = valueJoiner.toString();
        lines.add(TextHelper.withTabs("@"+this.name+(values.isEmpty() ? "" : "("+values+")"),tabs));
    }

    @Override
    public void visit(String name, Object value) {
        addValue(name,value);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String name, String descriptor) {
        AnnotationPrinter printer = parseAnnotation(descriptor);
        addValue(name,printer);
        return printer;
    }
}