package mods.thecomputerizer.theimpossiblelibrary.api.core.asm;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ArrayHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;

import java.util.*;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMRef.*;

public class ClassPrinter extends ClassVisitor implements BytecodePrinter { //TODO Handle signatures

    /**
     * Converts an internal name or class descriptor into a classpath
     */
    public static String getClassPath(String name) {
        while(name.startsWith("[")) name = name.substring(1);
        if(name.startsWith("L") && name.endsWith(";")) name = name.substring(1,name.length()-1);
        return name.replace('/', '.');
    }

    public static String parseClassAccess(int access) {
        switch(access) {
            case PRIVATE_ABSTRACT_INTERFACE: return "private interface";
            case PROTECTED_ABSTRACT_INTERFACE: return "protected interface";
            case PUBLIC_ABSTRACT_INTERFACE: return "public interface";
            default: return parseAccess(access)+" class";
        }
    }

    public static String parseAccess(int access) {
        StringJoiner joiner = new StringJoiner(" ").setEmptyValue("(package-private)");
        for(int remaining=access,bit;remaining!=0;remaining-=bit) {
            bit = Integer.lowestOneBit(remaining);
            switch(bit) {
                case PUBLIC: joiner.add("public"); break;
                case PRIVATE: joiner.add("private"); break;
                case PROTECTED: joiner.add("protected"); break;
                case STATIC: joiner.add("static"); break;
                case FINAL: joiner.add("final"); break;
                case SYNCHRONIZED: joiner.add("synchronzied"); break;
                case BRIDGE: joiner.add("(bridge)"); break;
                case VARARGS: joiner.add("(varargs)"); break;
                case NATIVE: joiner.add("native"); break;
                case ABSTRACT: joiner.add("abstract"); break;
                case STRICT: joiner.add("strictfp"); break;
                case SYNTHETIC: joiner.add("synthetic"); break;
            }
        }
        return joiner.toString();
    }

    /**
     * Assumes the classpath has been parsed already.
     * If the package is null it will be an empty string.
     * If the class name is null or blank it will be substituted with '?'.
     * Returns a pair of the package & name of the class respectively.
     */
    public static Pair<String,String> splitPackage(String classpath) {
        int index = classpath.lastIndexOf('.');
        String pkgName = index==-1 ? "" : classpath.substring(0,index);
        String clsName = classpath.substring(index+1);
        return new ImmutablePair<>(pkgName,StringUtils.isNotBlank(clsName) ? clsName : "?");
    }

    protected final ClassPrinter parent;
    protected String pkgName;
    protected String[] imports;
    protected String access;
    @Getter protected String name;
    protected String superName;
    protected String[] interfaces;
    protected AnnotationPrinter[] annotations;
    protected FieldPrinter[] fields;
    protected MethodPrinter[] methods;
    protected ClassPrinter[] innerClasses;

    public ClassPrinter(int api) { // ASM4 | ASM5 | ASM6 | ASM7 | ASM8 | ASM9
        this(api,null);
    }

    public ClassPrinter(int api, ClassPrinter parent) {
        super(api);
        this.parent = parent;
    }

    public void addImport(String pkg) {
        if(StringUtils.isBlank(pkg) || pkg.equals(this.pkgName) || pkg.equals("java.lang")) return;
        if(Objects.nonNull(this.parent)) this.parent.addImport(pkg);
        else this.imports = ArrayHelper.append(this.imports,pkg,false);
    }

    protected void getAnnotationLines(Collection<String> lines, int tabs) {
        if(ArrayHelper.isNotEmpty(this.annotations))
            for(AnnotationPrinter annotation : this.annotations)
                annotation.toLines(lines,tabs);

    }

    protected void getFieldLines(Collection<String> lines, int tabs) {
        if(ArrayHelper.isNotEmpty(this.fields))
            for(FieldPrinter field : this.fields)
                field.toLines(lines,tabs+1);
        lines.add("");
    }

    protected void getHeaderLines(Collection<String> lines, int tabs) {
        StringJoiner headerJoiner = new StringJoiner(" ");
        headerJoiner.add(this.access).add(this.name);
        if(Objects.nonNull(this.superName)) headerJoiner.add("extends").add(this.superName);
        if(Objects.nonNull(this.interfaces) && this.interfaces.length>0) {
            StringJoiner interfaceJoiner = new StringJoiner(", ");
            for(String itf : this.interfaces) interfaceJoiner.add(itf);
            headerJoiner.add(interfaceJoiner.toString());
        }
        lines.add(TextHelper.withTabs(headerJoiner.add("{").toString(),tabs));
        lines.add("");
    }

    protected void getImportLines(Collection<String> lines) {
        if(Objects.nonNull(this.imports) && this.imports.length>0) {
            List<String> orderedImports = new ArrayList<>(Arrays.asList(this.imports));
            Collections.sort(orderedImports);
            for(String ordered : orderedImports) lines.add("import "+ordered+";");
            lines.add("");
        }
    }

    protected void getInnerClassLines(Collection<String> lines, int tabs) {
        if(ArrayHelper.isNotEmpty(this.innerClasses))
            for(ClassPrinter clazz : this.innerClasses)
                clazz.toLines(lines,tabs+1);
    }

    protected void getMethodLines(Collection<String> lines, int tabs) {
        if(ArrayHelper.isNotEmpty(this.methods))
            for(MethodPrinter method : this.methods)
                method.toLines(lines,tabs+1);
    }

    protected void getPackageLines(Collection<String> lines) {
        if(StringUtils.isNotBlank(this.pkgName)) {
            lines.add("package "+this.pkgName+";");
            lines.add("");
        }
    }

    protected AnnotationPrinter parseAnnotation(String desc) {
        Pair<String,String> pkgPair = splitPackage(getClassPath(desc));
        addImport(pkgPair.getLeft());
        AnnotationPrinter printer = new AnnotationPrinter(this.api,this,pkgPair.getRight());
        this.annotations = ArrayHelper.append(this.annotations,printer,false);
        return printer;
    }

    protected FieldPrinter parseField(int access, String name, String typeName, Object value) {
        Pair<String,String> pkgPair = splitPackage(getClassPath(typeName));
        addImport(pkgPair.getLeft());
        FieldPrinter printer = new FieldPrinter(this.api,this,access,name,pkgPair.getRight(),value);
        this.fields = ArrayHelper.append(this.fields,printer,false);
        return printer;
    }

    protected void parseInnerClass(int access, String name, String desc) {
        ClassPrinter printer = new ClassPrinter(this.api,this);
        printer.access = parseClassAccess(access);
        printer.name = Objects.nonNull(name) ? name : splitPackage(getClassPath(desc)).getRight();
        this.innerClasses = ArrayHelper.append(this.innerClasses,printer,false);
    }

    protected void parseInterfaces(String[] interfaces) {
        if(Objects.isNull(interfaces) || interfaces.length==0) {
            this.interfaces = new String[]{};
            return;
        }
        ArrayHelper.deduplicate(interfaces);
        String[] classpaths = ArrayHelper.forEach(interfaces,(itf,i) -> interfaces[i] = getClassPath(itf));
        this.interfaces = new String[classpaths.length];
        for(int i=0;i<classpaths.length;i++) {
            Pair<String,String> pkgPair = splitPackage(classpaths[i]);
            addImport(pkgPair.getLeft());
            String name = pkgPair.getRight();
            this.interfaces[i] = name.equals("?") ? null : name;
        }
        this.interfaces = ArrayHelper.removeAllOccurrencesOf(this.interfaces, "?");
    }

    protected MethodPrinter parseMethod(int access, String name, String desc, String[] exceptions) {
        MethodPrinter printer = new MethodPrinter(this.api,this,access,name,desc,exceptions);
        this.methods = ArrayHelper.append(this.methods,printer,false);
        return printer;
    }

    protected void parseName(String classpath) {
        Pair<String,String> pkgPair = splitPackage(classpath);
        this.pkgName = pkgPair.getLeft();
        this.name = pkgPair.getRight();
    }

    protected void parseSuper(String classpath) {
        Pair<String,String> pkgPair = splitPackage(classpath);
        addImport(pkgPair.getLeft());
        String name = pkgPair.getRight();
        this.superName = name.equals("?") || (pkgPair.getLeft().equals("java.lang") && name.equals("Object")) ? null : name;
    }

    public List<String> toLines() {
        List<String> lines = new ArrayList<>();
        toLines(lines,0);
        return lines;
    }

    @Override public void toLines(Collection<String> lines, int tabs) {
        if(Objects.isNull(this.parent)) getPackageLines(lines);
        if(Objects.isNull(this.parent)) getImportLines(lines);
        getAnnotationLines(lines,tabs);
        getHeaderLines(lines,tabs);
        getFieldLines(lines,tabs);
        getMethodLines(lines,tabs);
        getInnerClassLines(lines,tabs);
        lines.add("}");
    }

    @Override public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        this.access = parseClassAccess(access);
        parseName(getClassPath(name));
        parseSuper(getClassPath(superName));
        parseInterfaces(interfaces);
    }

    @Override public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        return parseAnnotation(descriptor);
    }

    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        return parseField(access,name,descriptor,value);
    }

    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        return parseMethod(access,name,descriptor,exceptions);
    }

    @Override public void visitInnerClass(String name, String outerName, String innerName, int access) {
        parseInnerClass(access,innerName,name);
    }

    @Override public void visitEnd() {
        TILRef.logWarn("Printing written class with name `{}`",this.name);
        TILRef.logWarn("----------------------------------------------------------------------------------------------------");
        int lineNum = 1;
        for(String line : toLines()) {
            TILRef.logWarn("{}"+(lineNum>9 ? (lineNum>99 ? " " : "  ") : "   ")+"{}",lineNum,line);
            lineNum++;
        }
        TILRef.logWarn("----------------------------------------------------------------------------------------------------");
    }
}