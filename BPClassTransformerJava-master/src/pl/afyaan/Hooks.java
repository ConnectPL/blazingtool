package pl.afyaan;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

public class Hooks {
    public static Set<BpMethod> bpMethods = new HashSet<>();

    public static void methodHookInstance(String className, String methodName, Object signature, Object instance){
        methodHook(className, methodName, signature);
        ClassLoader loader = Transformer.loader;
        try{
            if(loader != null || instance != null) {
                Class<?> clazz = instance.getClass();
                System.out.println(Modifier.toString(clazz.getModifiers()));
               /* for(Field field : clazz.getDeclaredFields()){
                    field.setAccessible(true);
                }*/
            }



        }catch (Exception e){
            //e.printStackTrace();
        }
    }

    public static void methodHook(String className, String methodName, Object signature){
        BpMethod method = new BpMethod(className,methodName, signature);

        if(!bpMethods.contains(method)){
            //bpMethods.add(method);
            StringBuilder sb = new StringBuilder();
            sb.append("===================\n");
            sb.append("ClassName: ").append(className).append("\n");
            sb.append("MethodName: ").append(methodName).append("\n");
            sb.append("Signature: ").append(signature).append("\n");
            sb.append("===================\n");

            System.out.println(sb);
        }
    }

    public static void methodHook_(String className, String methodName, Object signature, Object... obj){
        System.out.println("XD XD");

    }
}
