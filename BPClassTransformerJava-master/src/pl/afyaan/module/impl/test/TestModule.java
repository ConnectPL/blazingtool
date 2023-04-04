package pl.afyaan.module.impl.test;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import pl.afyaan.BpMethodVisitor;
import pl.afyaan.Hooks;
import pl.afyaan.Transformer;
import pl.afyaan.Utils;
import pl.afyaan.module.impl.movement.Flight;

import java.lang.reflect.Modifier;

public class TestModule {
    private static int transformedMethods = 0;
    public static void testModule(int len, String[] params, String className, String methodName, String signature, int access, MethodVisitor mv) {
        if(len == 1 && !BpMethodVisitor.inne(params, "Z")) {
            //TestModule.transformedMethods++;
            //if (!(TestModule.transformedMethods > 260 & TestModule.transformedMethods < 350)) return; //FLY
            //if(!(Transformer.transformedMethods == 164)) return; //AIRJUMP
            //if(!(Transformer.transformedMethods > 265 && Transformer.transformedMethods < 270)) return;

            //System.out.println(signature);
            //System.out.println(Transformer.transformedMethods + " " + Modifier.toString(access) + " " + signature);
            Hooks.methodHook(className, methodName, signature);
            int addIndex = 0;
            for (int i = 1; i < len + 1; i++) {
                if (i == 7 || i == 9) {
                    addIndex++;
                }
                String param = Utils.getParametersToArray(signature)[i - 1];
                i = i + addIndex;

                //mv.visitLdcInsn(1.0F);
                if (param.equals("B") || param.equals("S")) {
                    param = "I";
                }
                if (param.equals("Z") || param.equals("I") || param.equals("C")) {
                    mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                    mv.visitVarInsn(Opcodes.ALOAD, 0);
                    mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println",  "("+ param +" BOOOOOOOL", false);
                }
                if (param.equals("F")) {
                    mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                    mv.visitVarInsn(Opcodes.ALOAD, 0);
                    mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println",  "("+ param +") FLOATTTTTTTTTTT", false);
                }
                i = i - addIndex;
            }
        }
    }
}
