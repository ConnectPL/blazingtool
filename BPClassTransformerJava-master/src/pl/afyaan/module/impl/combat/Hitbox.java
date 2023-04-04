package pl.afyaan.module.impl.combat;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import pl.afyaan.BpMethodVisitor;
import pl.afyaan.Hooks;
import pl.afyaan.Transformer;
import pl.afyaan.Utils;
import pl.afyaan.module.impl.movement.Flight;

import java.lang.reflect.Modifier;

public class Hitbox {
    private static int transformedMethods = 0;
    public static boolean isOn = false;
    public static void setHitboxSize(float size, int len, String[] params, String className, String methodName, String signature, int access, MethodVisitor mv) {
        /*if(len == 2 && !BpMethodVisitor.inne(params, "F")) {
            Hitbox.transformedMethods++;
            if (!(Hitbox.transformedMethods > 20 && Hitbox.transformedMethods < 50)) return;
            //System.out.println(signature);
            System.out.println(Transformer.transformedMethods + " " + Modifier.toString(access) + " " + signature);
            Hooks.methodHook(className, methodName, signature);
            int addIndex = 0;
            for (int i = 1; i < len + 1; i++) {
                if (i == 7 || i == 9) {
                    addIndex++;
                }
                String param = Utils.getParametersToArray(signature)[i - 1];
                i = i + addIndex;
                if (param.equals("F")) {
                    if(i == 1) {
                        mv.visitLdcInsn(size);
                        mv.visitVarInsn(Opcodes.FSTORE, i);
                    }
                }
                i = i - addIndex;
            }
        }*/

        if(len == 3 && !BpMethodVisitor.inne(params, "D")) {
            //Hitbox.transformedMethods++;
            //if (!(Hitbox.transformedMethods > 20 && Hitbox.transformedMethods < 50)) return;
            //System.out.println(signature);
            //System.out.println(Transformer.transformedMethods + " " + Modifier.toString(access) + " " + signature);
            //Hooks.methodHook(className, methodName, signature);
            int addIndex = 0;
            for (int i = 1; i < len + 1; i++) {
                if (i == 7 || i == 9) {
                    addIndex++;
                }
                String param = Utils.getParametersToArray(signature)[i - 1];
                i = i + addIndex;
                if (param.equals("D")) {
                    mv.visitLdcInsn(false);
                    mv.visitVarInsn(Opcodes.ISTORE, i);
                }
                i = i - addIndex;
            }
        }
    }
}
