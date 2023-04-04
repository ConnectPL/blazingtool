package pl.afyaan.worker;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import pl.afyaan.BpMethodVisitor;

public class BpConstructorVisitor extends ClassVisitor {
    private final BpWorker bpWorker;

    public BpConstructorVisitor(ClassWriter classWriter, BpWorker bpWorker) {
        super(Opcodes.ASM9, classWriter);
        this.bpWorker = bpWorker;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);

        if(name.equalsIgnoreCase("hashCode") || name.equalsIgnoreCase("equals")) return mv;
        if((access & (Opcodes.ACC_NATIVE | Opcodes.ACC_ABSTRACT | Opcodes.ACC_STATIC)) != 0){
            return mv;
        }else {
            return new BpConstructorVisitor2(mv, bpWorker);
        }
    }
}
