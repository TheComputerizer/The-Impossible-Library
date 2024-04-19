package mods.thecomputerizer.theimpossiblelibrary.api.core.asm;

import mods.thecomputerizer.theimpossiblelibrary.api.core.ArrayHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import java.util.Collection;
import java.util.Objects;
import java.util.StringJoiner;

import static org.objectweb.asm.Type.VOID;

public class MethodPrinter extends MethodVisitor implements BytecodePrinter {

    protected final ClassPrinter parent;
    protected final String access;
    protected final String name;
    protected final String[] exceptions;
    protected String returnType;
    protected String parameterTypes;
    protected AnnotationPrinter[] annotations;

    protected MethodPrinter(int api, ClassPrinter parent, int access, String name, String desc, String[] exceptions) {
        super(api);
        this.parent = parent;
        this.access = ClassPrinter.parseAccess(access);
        this.name = name;
        this.exceptions = parseExcpections(exceptions);
        computeTypes(desc);
    }

    protected void computeTypes(String desc) {
        Type methodType = Type.getMethodType(desc);
        Pair<String,String> pair = ClassPrinter.splitPackage(methodType.getReturnType().getClassName());
        this.parent.addImport(pair.getLeft());
        this.returnType = pair.getRight();
        StringBuilder builder = new StringBuilder();
        StringJoiner joiner = new StringJoiner(", ");
        for(Type type : methodType.getArgumentTypes()) {
            pair = ClassPrinter.splitPackage(type.getClassName());
            this.parent.addImport(pair.getLeft());
            String name = pair.getRight();
            if(StringUtils.isBlank(name)) joiner.add(name+" ?");
            else {
                char varChar = name.toLowerCase().charAt(0);
                int charCount = 0;
                for(char c : builder.toString().toCharArray())
                    if(c==varChar) charCount++;
                builder.append(varChar);
                joiner.add(name+" "+varChar+(charCount==0 ? "" : charCount));
            }
        }
        this.parameterTypes = joiner.toString();
    }

    protected void getAnnotationLines(Collection<String> lines, int tabs) {
        if(ArrayHelper.isNotEmpty(this.annotations))
            for(AnnotationPrinter annotation : this.annotations)
                annotation.toLines(lines,tabs);
    }

    protected String getMethodHeader() {
        switch(this.name) {
            case "<clinit>": return "static";
            case "<init>": return this.access+" "+this.parent.getName()+"("+this.parameterTypes+")";
            default: return this.access+" "+this.returnType+" "+this.name+"("+this.parameterTypes+")";
        }
    }

    protected AnnotationPrinter parseAnnotation(String desc) {
        Pair<String,String> pkgPair = ClassPrinter.splitPackage(ClassPrinter.getClassPath(desc));
        this.parent.addImport(pkgPair.getLeft());
        AnnotationPrinter printer = new AnnotationPrinter(this.api,this.parent,pkgPair.getRight());
        this.annotations = ArrayHelper.append(this.annotations,printer,false);
        return printer;
    }

    protected String[] parseExcpections(String[] exceptions) {
        if(Objects.nonNull(exceptions)) {
            for(int i=0;i<exceptions.length;i++) {
                Pair<String,String> pkgPair = ClassPrinter.splitPackage(ClassPrinter.getClassPath(exceptions[i]));
                this.parent.addImport(pkgPair.getLeft());
                exceptions[i] = pkgPair.getRight();
            }
            return exceptions;
        }
        return new String[]{};
    }

    @Override
    public void toLines(Collection<String> lines, int tabs) { //TODO Print code? Might be too much effort
        getAnnotationLines(lines,tabs);
        lines.add(TextHelper.withTabs(getMethodHeader()+" {}",tabs));
        lines.add("");
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        return parseAnnotation(desc);
    }
}