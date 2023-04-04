package pl.afyaan.worker;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class BpConstructorVisitor2 extends MethodVisitor{
    private final BpWorker bpWorker;

    public BpConstructorVisitor2(MethodVisitor mv, BpWorker bpWorker) {
        super(Opcodes.ASM9, mv);
        this.bpWorker = bpWorker;
    }

    @Override
    public void visitCode() {
        super.visitCode();
        mv.visitLdcInsn(bpWorker.bpWorkerId);
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "pl/afyaan/worker/BpConstructorVisitor2", "setInstance",
                "(ILjava/lang/Object;)V", false);
        super.visitEnd();
    }

    public static void setInstance(int bpWorkerId, Object instance){
        for(BpWorker bpWorker : BpWorker.bpWorkers){
            if(bpWorker.bpWorkerId == bpWorkerId){
                bpWorker.setInstance(instance);
            }
        }
    }
}
