package pl.afyaan.module.impl.movement;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import pl.afyaan.BpMethodVisitor;
import pl.afyaan.Hooks;
import pl.afyaan.Transformer;
import pl.afyaan.Utils;

import java.lang.reflect.Modifier;

public class Step {
    private static int transformedMethods = 0;
    public static void setStepSize(float size, int len, String[] params, String className, String methodName, String signature, int access, MethodVisitor mv) {
        if(len == 1 && !BpMethodVisitor.inne(params, "F")) {
            Step.transformedMethods++;
            if (!(Step.transformedMethods == 115)) return; //step
            //System.out.println(signature);
            //System.out.println(Step.transformedMethods + " " + Modifier.toString(access) + " " + signature);
            Hooks.methodHook(className, methodName, signature);
            int addIndex = 0;
            for (int i = 1; i < len + 1; i++) {
                if (i == 7 || i == 9) {
                    addIndex++;
                }
                String param = Utils.getParametersToArray(signature)[i - 1];
                i = i + addIndex;
                if (param.equals("F")) {
                    mv.visitLdcInsn(size);
                    mv.visitVarInsn(Opcodes.FSTORE, i);
                    mv.visitLdcInsn(Opcodes.FLOAD);
                }
                i = i - addIndex;
            }
        }
    }
}
