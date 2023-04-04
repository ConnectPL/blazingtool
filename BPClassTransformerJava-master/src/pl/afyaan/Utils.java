package pl.afyaan;

import org.objectweb.asm.tree.MethodNode;
import pl.afyaan.module.impl.BpPlayer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {
    public static int findNextChar(String string, int startIn, char key){
        for(int i = startIn; i < string.length(); i++){
            if(string.charAt(i) == key){
                return i;
            }
        }
        return 0;
    }

    public static String[] getParametersToArray(String signature){
        int start = signature.indexOf("(") + 1;
        int end = signature.indexOf(")");
        signature = signature.substring(start, end);

        List<String> signatures = new ArrayList<>();

        for (int i = 0; i < signature.length(); i++){
            if(signature.charAt(i) == 'L'){
                int skip = findNextChar(signature, i, ';');
                String newSign = signature.substring(i, skip + 1);
                i = skip;
                signatures.add(newSign);
            }else if(signature.charAt(i) == '['){
                String newSign = signature.substring(i, i + 2);
                i++;
                signatures.add(newSign);
            }
            else{
                signatures.add(String.valueOf(signature.charAt(i)));
            }
        }

        return signatures.toArray(new String[0]);
    }

    public static String getReturnType(String desc){
        int end = desc.indexOf(")");
        desc = desc.substring(end + 1, desc.length());

        return desc;
    }

    public static int getParametersLength(String signature){
        return getParametersToArray(signature).length;
    }

    public static boolean methodIsConstructor(MethodNode method){
        if(method.name.equals("<init>")) {
            return true;
        }else {
            return false;
        }
    }

    public static List<MethodNode> getConstructors(List<MethodNode> methods){
        List<MethodNode> methodNodes = new ArrayList<>();
        for(MethodNode methodNode : methods){
            if(methodIsConstructor(methodNode)){
                methodNodes.add(methodNode);
            }
        }
        return methodNodes;
    }

    public static List<MethodNode> getMethodsWithoutConstructors(List<MethodNode> methods){
        List<MethodNode> methodNodes = new ArrayList<>();
        for(MethodNode methodNode : methods){
            if(!methodIsConstructor(methodNode)){
                methodNodes.add(methodNode);
            }
        }
        return methodNodes;
    }

    public static String removeColors(String value){
        StringBuilder sb = new StringBuilder();
        int nextIndex = 0;
        int colorsCount = 0;
        int colorsCount2 = 0;
        for(int i = 0; i < value.length(); i++){
            if(value.charAt(i) == '§'){
                colorsCount++;
            }
        }

        if(colorsCount == 0){
            return value;
        }

        for(int i = 0; i < value.length(); i++){
            if(value.charAt(i) == '§'){
                colorsCount2++;
                sb.append(value, nextIndex, i);
                if(colorsCount2 == colorsCount) sb.append(value.substring(i+2));
                nextIndex = i+2;
            }
        }

        return sb.toString();
    }

    public static BpPlayer getBpPlayer(String className){
        BpPlayer player = new BpPlayer();
        className = className.substring(className.indexOf("[") + 1, className.indexOf("]"));
        for(int i = 0; i < className.length(); i++){
            if(className.charAt(i) == '\''){
                int skip = findNextChar(className, i+1, '\'');
                String param = className.substring(i+1, skip);
                player.setUserName(removeColors(param));
                break;
            }
        }

        for(int i = 0; i < className.length(); i++){
            if(className.charAt(i) == 'x'){
                if(className.charAt(i+1) == '=') {
                    int skip = findNextChar(className, i, 'y');
                    String param = className.substring(i + 2, skip - 2).replace(",", ".");
                    player.setX(Double.parseDouble(param));
                }
            }
            if(className.charAt(i) == 'y'){
                if(className.charAt(i+1) == '=') {
                    int skip = findNextChar(className, i, 'z');
                    String param = className.substring(i + 2, skip - 2).replace(",", ".");
                    player.setY(Double.parseDouble(param));
                }
            }
            if(className.charAt(i) == 'z'){
                if(className.charAt(i+1) == '=') {
                    String param = className.substring(i + 2).replace(",", ".");
                    player.setZ(Double.parseDouble(param));
                }
            }
        }
        return player;
    }

}
