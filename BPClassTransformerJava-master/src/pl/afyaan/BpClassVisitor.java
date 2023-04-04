package pl.afyaan;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class BpClassVisitor extends ClassVisitor {
    private final String className;
    public BpClassVisitor(ClassWriter classVisitor, String className) {
        super(Opcodes.ASM9, classVisitor);
        this.className = className;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);

        if(name.equalsIgnoreCase("hashCode") || name.equalsIgnoreCase("equals")) return mv;
        if((access & (Opcodes.ACC_NATIVE | Opcodes.ACC_ABSTRACT | Opcodes.ACC_STATIC)) != 0){
            return mv;
        }else {
            return new BpMethodVisitor(mv, className, name, descriptor, access);
        }
    }
}
