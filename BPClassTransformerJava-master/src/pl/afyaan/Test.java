package pl.afyaan;

import com.google.gson.Gson;
import org.objectweb.asm.ClassWriter;
import pl.afyaan.module.impl.ModuleManager;
import pl.afyaan.module.impl.Position;
import pl.afyaan.module.impl.movement.Flight;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class Test {
    public void setFly(boolean aa){
        aa = ModuleManager.fly;
        System.out.println(aa);
    }

    public static void main(String[] args) throws IOException {
        double dou =  0.12147887323943662 * 1.7391304347826086 * 0.9642857142857143 * -0.0;
        System.out.println(dou);
        Position pos = new Position(22.1, 22.2, 23.2);
        Gson gson = new Gson();
        Position posit = gson.fromJson(new FileReader("C:\\Users\\afyaa\\IdeaProjects\\JJL-Encryptor\\out\\artifacts\\JJL_Encryptor_jar\\position.json"), Position.class);

        System.out.println(posit.x);
        /*try{
            Path path = Paths.get("C:\\Users\\afyaa\\IdeaProjects\\ASMTest\\out\\artifacts\\ASMTest_jar\\TestClass2.class");
            Path path2 = Paths.get("C:\\Users\\afyaa\\IdeaProjects\\ASMTest\\out\\artifacts\\ASMTest_jar\\TestClass2.class");
            byte[] bytecode = Files.readAllBytes(path);

            bytecode = Transformer.retransformClass_("TestClass", bytecode, ClassWriter.COMPUTE_MAXS);

              Files.write(path2, bytecode);
        }catch (Exception ex){
            ex.printStackTrace();
        }*/
    }
}
