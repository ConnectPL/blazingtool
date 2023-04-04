package pl.afyaan;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import pl.afyaan.module.impl.combat.Hitbox;
import pl.afyaan.module.impl.movement.Flight;
import pl.afyaan.module.impl.movement.Step;
import pl.afyaan.module.impl.test.TestModule;

import java.lang.reflect.Modifier;
import java.util.Arrays;

public class BpMethodVisitor extends MethodVisitor {
    private String className;
    private String methodName;
    private String signature;
    private int access;

    public static boolean inne(String[] params, String param){
        for(String par : params){
            if(!par.equals(param)){
                return true;
            }
        }
        return false;
    }

    public BpMethodVisitor(MethodVisitor mv, String className, String methodName, String signature, int access) {
        super(Opcodes.ASM9, mv);
        this.className = className;
        this.methodName = methodName;
        this.signature = signature;
        this.access = access;
    }


    @Override
    public void visitCode() {
        super.visitCode();

        int len = Utils.getParametersLength(signature);
        String[] params = Utils.getParametersToArray(signature);
        Flight.setFly(true, len, params, className, methodName, signature, access, mv);
        Step.setStepSize(100.0F, len, params, className, methodName, signature, access, mv);

        /*if(params.length >= 2){
            if(params[0].contains(Transformer.entityPlayer) && params[1].contains(Transformer.entity)){
                mv.visitLdcInsn(className);
                mv.visitLdcInsn(methodName);
                mv.visitLdcInsn(signature);
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "pl/afyaan/Hooks", "methodHook", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V", false);
            }
        }*/

        //TestModule.testModule(len, params, className, methodName, signature, access, mv);
        /*if(len == 1 && !inne(params, "Z")){
            Transformer.transformedMethods++;
            if(!(Transformer.transformedMethods == 266)) return; //FLY
            //if(!(Transformer.transformedMethods == 164)) return; //AIRJUMP
            //if(!(Transformer.transformedMethods > 265 && Transformer.transformedMethods < 270)) return;

            //System.out.println(signature);
            System.out.println(Transformer.transformedMethods + " " + Modifier.toString(access) + " " + signature);
            Hooks.methodHook(className, methodName, signature);
            int addIndex = 0;
            for(int i = 1; i < len+1; i++){
                if(i == 7 || i == 9) {
                    addIndex++;
                }

                //Label label = new Label();
                String param = Utils.getParametersToArray(signature)[i-1];
                i = i + addIndex;
                //mv.visitLabel(label);
                //mv.visitLineNumber(0, label);

                //mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                if(param.equals("B") || param.equals("S")){
                    param = "I";
                }

                if(param.equals("Z") || param.equals("I") || param.equals("C")){
                    mv.visitLdcInsn(true);
                    mv.visitVarInsn(Opcodes.ISTORE, i);
                    //mv.visitVarInsn(Opcodes.ILOAD, i);
                }else if(param.equals("J")){
                    mv.visitVarInsn(Opcodes.LLOAD, i);
                }else if(param.equals("F")){
                    //mv.visitLdcInsn(0.0f);
                    //mv.visitVarInsn(Opcodes.FSTORE, i);
                    //mv.visitVarInsn(Opcodes.FLOAD, i);
                }else if(param.equals("D")){
                    mv.visitVarInsn(Opcodes.DLOAD, i);
                }else if(param.length() > 1){
                    mv.visitVarInsn(Opcodes.ALOAD, i);
                }
                //mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println",  "("+ param +")V", false);

                i = i - addIndex;
            }
        }*/
        super.visitEnd();
    }

    public void calcPush(MethodVisitor mv, int i){
        if(i == 0){
            mv.visitInsn(Opcodes.ICONST_0);
        }else if(i == 1){
            mv.visitInsn(Opcodes.ICONST_1);
        }else if(i == 2){
            mv.visitInsn(Opcodes.ICONST_2);
        }else if(i == 3){
            mv.visitInsn(Opcodes.ICONST_3);
        }else if(i == 4){
            mv.visitInsn(Opcodes.ICONST_4);
        }else if(i == 5){
            mv.visitInsn(Opcodes.ICONST_5);
        }else{
            mv.visitVarInsn(Opcodes.BIPUSH, i);
        }
    }
}
