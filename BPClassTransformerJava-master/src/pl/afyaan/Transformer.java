package pl.afyaan;



import org.objectweb.asm.*;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;
import pl.afyaan.utils.BpStringBuilder;
import pl.afyaan.worker.BpConstructorVisitor;
import pl.afyaan.worker.BpWorker;

import javax.rmi.CORBA.Util;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Transformer {
    public static String entityPlayer = "^ , const return catch true 1 import | strictfp 7 } # ` & long finally % short abstract boolean else ! throw synchronized throw double float short ! catch implements 7 package ! for public this 3 else import protected ? ? short break new switch else strictfp";
    public static String entity = "` if static \" volatile = 5 native 8 static synchronized goto 7 switch null 2 * ^ * case finally package $ strictfp ` char int false 7 const while ] final throws if this * for throws ' case synchronized char class false private native * else return private double instanceof else";


    public static ClassLoader loader;
    public static int transformedMethods = 0;
    private static boolean isRun = false;
    private static int loadedClasses = 0;
    private static List<String> classQueue = new ArrayList<>();

    public byte[] retransformClass(ClassLoader loader, String className, byte[] bytecode) {
        Transformer.loader = loader;

        try {
            bytecode = retransformClass_(className, bytecode, ClassWriter.COMPUTE_MAXS);
        }catch (Exception e){
            return bytecode;
        }

        loadedClasses++;

        return bytecode;
    }

    public static byte[] retransformClass_(String className, byte[] bytecode, int flags) throws IOException {
        if(!isRun){
            isRun = true;
        }

        InputStream is = new ByteArrayInputStream(bytecode);
        ClassReader reader = new ClassReader(is);
        ClassWriter writer = new ClassWriter(reader, flags);
        ClassVisitor visitor = new BpClassVisitor(writer, className);
        reader.accept(visitor, 0);

        ClassNode clazz = new ClassNode();
        reader.accept(clazz, ClassReader.EXPAND_FRAMES);

        //findPlayers(clazz, writer, reader, className);
        addLoadedEntityListClass(clazz, writer, reader, className);

        noAsm(clazz, writer, reader, className);
        find_vclip_(clazz, writer, reader, className);
        //classQueue(clazz, writer, reader, className);
        findAttackPlayer(clazz, writer, reader, className);

        classMapping(clazz, writer, reader, className);

        return writer.toByteArray();
    }

    public static void classMapping(ClassNode clazz, ClassWriter writer, ClassReader reader, String className){
        String entityOtherPlayerMP = "continue instanceof try strictfp $ ` ` package strictfp continue 0 9 try throw catch throw final 5 $ 4 false return abstract for extends $ protected volatile 0 ~ import synchronized package , double instanceof while long 4 2 throw super 0 while , break else 2";
        String entityPlayerSP = "abstract default = & null do boolean false throws * 0 native import 3 = % switch public throws 9 | continue , int int short while true 3 void final class void final null case ? finally % + 3 9 null ? ] - return 2 void \" | ! * double 4 = short { , static interface goto";

        /*if(!className.contains("` if static \" volatile = 5 native 8 static synchronized goto 7 switch null 2 * ^ * case finally package $ strictfp ` char int false 7 const while ] final throws if this * for throws ' case synchronized char class false private native * else return private double instanceof else")){
            return;
        }*/

        /*if(!className.contains("continue instanceof try strictfp $ ` ` package strictfp continue 0 9 try throw catch throw final 5 $ 4 false return abstract for extends $ protected volatile 0 ~ import synchronized package , double instanceof while long 4 2 throw super 0 while , break else 2")){
            return;
        }*/

        int fields = clazz.fields.size();
        int constructors = Utils.getConstructors(clazz.methods).size();
        int methods = Utils.getMethodsWithoutConstructors(clazz.methods).size();

        int fields_ = 101;
        int constructors_ = 1;
        int methods_ = 218;

        if(fields_ == fields && constructors_ == constructors && methods_ == methods){
            System.out.println("Znaleziono Klasę Entity=================\n================================\n=================================");
        }

        BpStringBuilder sb = new BpStringBuilder();
        sb.appendln("Fields size: " + clazz.fields.size());
        sb.appendln("Constructors size: " + Utils.getConstructors(clazz.methods).size());
        sb.appendln("Methods size: " + Utils.getMethodsWithoutConstructors(clazz.methods).size());
      //  System.out.println(sb);


        int tc = loadedClasses;
        Thread thread = new Thread(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            while (true){
                if(loadedClasses > tc){
                    try{
                        Class<?> bpClass = Class.forName(className, false, Transformer.loader);
                        /*BpStringBuilder sb = new BpStringBuilder();
                        sb.appendln("Fields size: " + bpClass.getDeclaredFields().length);
                        sb.appendln("Constructors size: " + bpClass.getDeclaredConstructors().length);
                        sb.appendln("Methods size: " + bpClass.getDeclaredMethods().length);
                        System.out.println(sb);*/
                    }catch (ClassNotFoundException ignored){}
                    break;
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();


    }

    public static void noAsm(ClassNode clazz, ClassWriter writer, ClassReader reader, String className){
        int fieldsSize = 0;
        int intSize = 0;
        for(FieldNode field : clazz.fields){
            if(field.desc.equals("I")){
                intSize++;
            }
            if(field.desc.equals("F")){
                fieldsSize++;
            }
        }
        if(Utils.getConstructors(clazz.methods).size() > 0)
            if(fieldsSize > 0 && intSize > 0){
                int tc = loadedClasses;
                BpWorker bpWorker = new BpWorker(0);
                //if(bpWorker.bpWorkerId == 1) //timer
                if(bpWorker.bpWorkerId != 2) return; //timer
                //if(!(bpWorker.bpWorkerId >= 100 && bpWorker.bpWorkerId < 150)) return;//speedmine
                ClassVisitor constructorVisitor = new BpConstructorVisitor(writer, bpWorker);
                reader.accept(constructorVisitor, 0);
                Thread thread = new Thread(()->{
                    while (true){
                        if(loadedClasses > tc){
                            try{
                                Class<?> bpClass = Class.forName(className, false, Transformer.loader);
                                bpWorker.setClazz(bpClass);
                                //System.out.println("Znaleziono speedmine??");
                                Thread bpWorkerThread = new Thread(bpWorker);
                                bpWorkerThread.start();
                            }catch (ClassNotFoundException ignored){}
                            break;
                        }
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
            }
    }

    public static void findAttackPlayer(ClassNode clazz, ClassWriter writer, ClassReader reader, String className){
        int i = 0;

        for(MethodNode method : clazz.methods){
            String[] params = Utils.getParametersToArray(method.desc);
            if(params.length == 2){
                if(params[0].contains(entityPlayer) && params[1].contains(entity)){
                    i++;
                }
            }
        }

        if(i > 0){
            if(Utils.getConstructors(clazz.methods).size() > 0) {
                int tc = loadedClasses;
                BpWorker bpWorker = new BpWorker(11);
                ClassVisitor constructorVisitor = new BpConstructorVisitor(writer, bpWorker);
                reader.accept(constructorVisitor, 0);
                int finalI = i;
                Thread thread = new Thread(() -> {
                    while (true) {
                        if (loadedClasses > tc) {
                            try {
                                Class<?> bpClass = Class.forName(className, false, Transformer.loader);
                                bpWorker.setClazz(bpClass);
                                Thread bpWorkerThread = new Thread(bpWorker);
                                bpWorkerThread.start();
                                System.out.println("Attack Entity Finder Add Class: " + finalI);
                                System.out.println(bpClass.getName());
                            } catch (ClassNotFoundException ignored) {
                            }
                            break;
                        }
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
            }
        }
    }

    public static void addLoadedEntityListClass(ClassNode clazz, ClassWriter writer, ClassReader reader, String className){
        int i = 0;
        for(FieldNode field : clazz.fields){
            if(field.desc.equals("Ljava/util/ArrayList;")){
                i++;
            }
        }
        if(i == 8){
            if(Utils.getConstructors(clazz.methods).size() > 0) {
                int tc = loadedClasses;
                BpWorker bpWorker = new BpWorker(10);
                ClassVisitor constructorVisitor = new BpConstructorVisitor(writer, bpWorker);
                reader.accept(constructorVisitor, 0);
                int finalI = i;
                Thread thread = new Thread(() -> {
                    while (true) {
                        if (loadedClasses > tc) {
                            try {
                                Class<?> bpClass = Class.forName(className, false, Transformer.loader);
                                bpWorker.setClazz(bpClass);
                                Thread bpWorkerThread = new Thread(bpWorker);
                                bpWorkerThread.start();
                                System.out.println("Players Finder Add Class: " + finalI);
                                System.out.println(bpClass.getName());
                            } catch (ClassNotFoundException ignored) {
                            }
                            break;
                        }
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
            }
        }
    }

    public static void findPlayers(ClassNode clazz, ClassWriter writer, ClassReader reader, String className){
        int i = 0;
        for(FieldNode field : clazz.fields){
            if(field.desc.equals("Ljava/util/ArrayList;")){
                i++;
            }
        }
        if(i > 1){
            if(Utils.getConstructors(clazz.methods).size() > 0) {
                int tc = loadedClasses;
                BpWorker bpWorker = new BpWorker(3);
                ClassVisitor constructorVisitor = new BpConstructorVisitor(writer, bpWorker);
                reader.accept(constructorVisitor, 0);
                int finalI = i;
                Thread thread = new Thread(() -> {
                    while (true) {
                        if (loadedClasses > tc) {
                            try {
                                Class<?> bpClass = Class.forName(className, false, Transformer.loader);
                                bpWorker.setClazz(bpClass);
                                Thread bpWorkerThread = new Thread(bpWorker);
                                bpWorkerThread.start();
                                System.out.println("Players Finder Add Class: " + finalI);
                                System.out.println(bpClass.getName());
                            } catch (ClassNotFoundException ignored) {
                            }
                            break;
                        }
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
            }
        }
    }

    public static void find_vclip_(ClassNode clazz, ClassWriter writer, ClassReader reader, String className){
        if(!className.contains("` if static \" volatile = 5 native 8 static synchronized goto 7 switch null 2 * ^ * case finally package $ strictfp ` char int false 7 const while ] final throws if this * for throws ' case synchronized char class false private native * else return private double instanceof else")){
            return;
        }
        if(Utils.getConstructors(clazz.methods).size() > 0) {
            int tc = loadedClasses;
            BpWorker bpWorker = new BpWorker(1);
            ClassVisitor constructorVisitor = new BpConstructorVisitor(writer, bpWorker);
            reader.accept(constructorVisitor, 0);
            Thread thread = new Thread(() -> {
                while (true) {
                    if (loadedClasses > tc) {
                        try {
                            Class<?> bpClass = Class.forName(className, false, Transformer.loader);
                            bpWorker.setClazz(bpClass);
                            Thread bpWorkerThread = new Thread(bpWorker);
                            bpWorkerThread.start();
                        } catch (ClassNotFoundException ignored) {
                        }
                        break;
                    }
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }
    }

    /*public static void classQueue(ClassNode clazz, ClassWriter writer, ClassReader reader, String className){
        int tc = loadedClasses;
        if(clazz.superName != null){
            classQueue.add(clazz.superName);
        }

        for(String className_ : classQueue){
            if(className_.equals(className)){
                Thread thread = new Thread(()->{
                    while (true){
                        if(loadedClasses > tc){
                            find_vclip(clazz, writer, reader, className);
                            break;
                        }
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
            }
        }

    }*/

    public static void find_vclip(ClassNode clazz, ClassWriter writer, ClassReader reader, String className){
        int doubleSize = 0;
        //if(clazz.superName != null)
        try{
            Class<?> bpClass = Class.forName(className, false, Transformer.loader);
            System.out.println("XDDDDDDDDDDDD//");
            for(Field field : bpClass.getDeclaredFields()){
                if(field.getType().getName().equals("double")){
                    doubleSize++;
                }
            }

        }catch (ClassNotFoundException ignored){
            return;
        }
        if(Utils.getConstructors(clazz.methods).size() > 0)
            if(doubleSize >= 3){
                System.out.println("Added ");
                int tc = loadedClasses;
                BpWorker bpWorker = new BpWorker(2);
                //if(bpWorker.bpWorkerId == 1) //timer
                ClassVisitor constructorVisitor = new BpConstructorVisitor(writer, bpWorker);
                reader.accept(constructorVisitor, 0);
                Thread thread = new Thread(()->{
                    while (true){
                        if(loadedClasses > tc){
                            try{
                                Class<?> bpClass = Class.forName(className, false, Transformer.loader);
                                bpWorker.setClazz(bpClass);
                                Thread bpWorkerThread = new Thread(bpWorker);
                                bpWorkerThread.start();
                            }catch (ClassNotFoundException ignored){}
                            break;
                        }
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
            }
    }
}
