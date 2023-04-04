package pl.afyaan.module.impl.movement;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import pl.afyaan.BpMethodVisitor;
import pl.afyaan.Hooks;
import pl.afyaan.Transformer;
import pl.afyaan.Utils;

import java.lang.reflect.Modifier;

public class Flight{
    private static int transformedMethods = 0;
    public static void setFly(boolean fly, int len, String[] params, String className, String methodName, String signature, int access, MethodVisitor mv) {
        if(len == 1 && !BpMethodVisitor.inne(params, "Z")) {
            Flight.transformedMethods++;
            if (!(Flight.transformedMethods == 266)) return; //FLY
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
                if (param.equals("B") || param.equals("S")) {
                    param = "I";
                }
                if (param.equals("Z") || param.equals("I") || param.equals("C")) {
                    //mv.visitLdcInsn(fly);
                    mv.visitFieldInsn(Opcodes.GETSTATIC, "pl/afyaan/module/impl/ModuleManager", "fly", "Z");
                    mv.visitVarInsn(Opcodes.ISTORE, i);
                }
                i = i - addIndex;
            }
        }
    }
}
