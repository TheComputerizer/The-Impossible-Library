package mods.thecomputerizer.theimpossiblelibrary.api.core.asm;

import mods.thecomputerizer.theimpossiblelibrary.api.core.ArrayHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelper;
import org.apache.commons.lang3.tuple.Pair;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.FieldVisitor;

import java.util.Collection;
import java.util.Objects;

public class FieldPrinter extends FieldVisitor implements BytecodePrinter {

    protected final ClassPrinter parent;
    protected final String access;
    protected final String name;
    protected final String typeName;
    protected final Object value;
    protected AnnotationPrinter[] annotations;

    public FieldPrinter(int api, ClassPrinter parent, int access, String name, String typeName, Object value) {
        super(api);
        this.parent = parent;
        this.access = ClassPrinter.parseAccess(access);
        this.name = name;
        this.typeName = ClassPrinter.getClassPath(typeName);
        this.value = value;
    }

    protected void getAnnotationLines(Collection<String> lines, int tabs) {
        if(ArrayHelper.isNotEmpty(this.annotations))
            for(AnnotationPrinter annotation : this.annotations)
                annotation.toLines(lines,tabs);
    }

    protected AnnotationPrinter parseAnnotation(String desc) {
        Pair<String,String> pkgPair = ClassPrinter.splitPackage(ClassPrinter.getClassPath(desc));
        this.parent.addImport(pkgPair.getLeft());
        AnnotationPrinter printer = new AnnotationPrinter(this.api,this.parent,pkgPair.getRight());
        this.annotations = ArrayHelper.append(this.annotations,printer,false);
        return printer;
    }

    @Override
    public void toLines(Collection<String> lines, int tabs) {
        getAnnotationLines(lines,tabs);
        lines.add(TextHelper.withTabs(this.access+" "+this.typeName+" "+this.name+
                (Objects.nonNull(this.value) ? " = "+this.value : "")+";",tabs));
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        return parseAnnotation(desc);
    }
}