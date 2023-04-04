package pl.afyaan.worker;

import pl.afyaan.MathHelper;
import pl.afyaan.Transformer;
import pl.afyaan.Utils;
import pl.afyaan.module.impl.BpPlayer;
import pl.afyaan.module.impl.ModuleManager;
import pl.afyaan.module.impl.combat.Killaura;
import pl.afyaan.module.impl.movement.Vclip;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

public class BpWorker implements Runnable{
    public static List<BpWorker> bpWorkers = new ArrayList<>();
    private static int id = 0;
    private Class<?> clazz;
    private Object instance;
    public int bpWorkerId;
    public int type = 0;

    public BpWorker(int type) {
        this.bpWorkerId = BpWorker.id++;
        this.type = type;
        BpWorker.bpWorkers.add(this);
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    private static String otherPlayerClassName = "continue instanceof try strictfp $ ` ` package strictfp continue 0 9 try throw catch throw final 5 $ 4 false return abstract for extends $ protected volatile 0 ~ import synchronized package , double instanceof while long 4 2 throw super 0 while , break else 2";
    private boolean killaura = false;
    @Override
    public void run() {
        System.out.println("BpWorkerID " + bpWorkerId);

        while (true){
            //Vclip.onEnable();
            if(instance != null) {
                int i = 0;
                int fieldIndex = 0;

                if(type == 11){
                    if(!killaura) {
                        for (Method method : clazz.getDeclaredMethods()) {
                            if (method.getParameterTypes().length == 3)
                                if (method.getParameterTypes()[0].getName().contains(Transformer.entityPlayer)
                                        && method.getParameterTypes()[1].getName().contains(Transformer.entity)
                                        && method.getParameterTypes()[2].getName().contains("Vec3")) {
                                    //System.out.println("Method attackPlayer added: " + method.getReturnType().getName());
                                    for (Class<?> param : method.getParameterTypes()) {
                                        //System.out.println(" >> " + param.getName());
                                    }
                                    Killaura.methods.add(method);
                                    Killaura.instance = instance;
                                }
                        }
                        killaura = true;
                    }
                }

                if(type == 10){// players list
                    Field otherX = null;
                    Field otherY = null;
                    Field otherZ = null;
                    for(Field field : clazz.getDeclaredFields()){
                        if(field.getType().getName().equals("java.util.ArrayList")){
                            fieldIndex++;
                            try {
                                field.setAccessible(true);
                                //if(fieldIndex != 4) continue; //Players?
                                //if(fieldIndex != 7) continue; //All Entities?
                                List<Object> objects_ = (List<Object>) field.get(instance);


                                if(objects_ != null) {
                                    List<Object> objects = new ArrayList<>(objects_);
                                    List<BpPlayer> bpPlayers = new ArrayList<>();
                                    for (Iterator<Object> it = objects.iterator(); it.hasNext(); ) {
                                        Object entity = it.next();
                                        if (entity != null) {
                                            String objStr = entity.toString();
                                            if (objStr.contains("MpServer")) {
                                                if(objStr.contains(otherPlayerClassName) && !objStr.contains("WitoMajnkrafs")){
                                                    //if(Killaura.methods.size() ?)
                                                    //Killaura.methods.get(0).invoke(Killaura.instance, Vclip.instance, entity);
                                                    for(Method method : Killaura.methods){
                                                        System.out.println("Attack :");
                                                        Class<?> vec3 = Class.forName("net.minecraft.util.Vec3", true, Transformer.loader);
                                                        //Random random = new Random();
                                                        if(ModuleManager.killaura) {
                                                            BpPlayer player = Utils.getBpPlayer(entity.toString());
                                                            float f = (float)(Vclip.x.getDouble(Vclip.instance) - player.getX());
                                                            float f1 = (float)(Vclip.y.getDouble(Vclip.instance) - player.getY());
                                                            float f2 = (float)(Vclip.z.getDouble(Vclip.instance) - player.getZ());
                                                            Object vec3Obj = vec3.getConstructor(double.class, double.class, double.class).newInstance(player.getX(), player.getY() + 1.4, player.getZ());
                                                            double currentDist = MathHelper.sqrt_float(f * f + f1 * f1 + f2 * f2);
                                                            System.out.println("distanceXD: " + currentDist);
                                                            System.out.println("Twoje X: " + Vclip.x.getDouble(Vclip.instance));
                                                            System.out.println("Enemy X: " + player.getX());
                                                            if (currentDist <= 6.0) {
                                                                method.invoke(Killaura.instance, Vclip.instance, entity, vec3Obj);
                                                                /*System.out.println("mamy to");
                                                                System.out.println("X " + player.getX());
                                                                System.out.println("y " + player.getY());
                                                                System.out.println("z " + player.getZ());
                                                                System.out.println("distance: " + currentDist);*/
                                                                try{
                                                                    Thread.sleep(300);
                                                                } catch (InterruptedException e){
                                                                    e.printStackTrace();
                                                                }
                                                            }
                                                            //method.invoke(Killaura.instance, Vclip.instance, entity, vec3Obj);
                                                            //System.out.println("NAME: " + entity.toString());
                                                            //System.out.println("Vec3: " + vec3);
                                                            //System.out.println("Vec3Obj: " + vec3Obj);
                                                        }
                                                    }
                                                    Class<?> entityClass = entity.getClass().getSuperclass().getSuperclass()
                                                            .getSuperclass().getSuperclass();
                                                    //System.out.println("EntityPlayer: " + entity.getClass().getSuperclass().getSuperclass().getName());
                                                    //System.out.println("Entity: " + entityClass.getName());
                                                    //System.out.println("ClassName: " + entity.toString());
                                                    //System.out.println("ClassName: " + Utils.getBpPlayer(entity.toString()).toString());
                                                    bpPlayers.add(Utils.getBpPlayer(entity.toString()));
                                                    int fieldIndex2 = 0;
                                                    for(Field field1 : entityClass.getDeclaredFields()){
                                                        field1.setAccessible(true);
                                                        if (field.getType().getName().equals("double")) {
                                                            double val = field1.getDouble(entity);
                                                            System.out.println(val);
                                                            if(fieldIndex2 == 1) otherX = field1;
                                                            if(fieldIndex2 == 2) otherY = field1;
                                                            if(fieldIndex2 == 3) otherZ = field1;
                                                            //System.out.println(fieldIndex2 + " >> double >> " + val);
                                                            fieldIndex2++;
                                                        }
                                                    }
                                                    for(Method method : entityClass.getDeclaredMethods()){
                                                        if(method.getParameterCount() == 5) {
                                                            if (method.getParameterTypes()[0].getName().equals("double") &&
                                                                    method.getParameterTypes()[1].getName().equals("double") &&
                                                                    method.getParameterTypes()[2].getName().equals("double") &&
                                                                    method.getParameterTypes()[3].getName().equals("float") &&
                                                                    method.getParameterTypes()[4].getName().equals("float")) {
                                                                //System.out.println("XDDDDDDDDDDDDDDDDD");
                                                                if(otherX != null) otherX.setAccessible(true);
                                                                if(otherY != null) otherY.setAccessible(true);
                                                                if(otherZ != null) otherZ.setAccessible(true);
                                                                if(Vclip.instance != null
                                                                    && Vclip.x != null
                                                                    && Vclip.y != null
                                                                    && Vclip.z != null) {
                                                                    try{
                                                                        double x_ = Vclip.x.getDouble(Vclip.instance);
                                                                        double y_ = Vclip.y.getDouble(Vclip.instance);
                                                                        double z_ = Vclip.z.getDouble(Vclip.instance);
                                                                        //method.invoke(entity, x_, y_, z_, 0.0f, 0.0f);
                                                                        //System.out.println(">>TP>>");
                                                                    }catch (NullPointerException ex){
                                                                        System.out.println("NULL");
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                //System.out.println(fieldIndex + " Entity: " + entity.toString());
                                                //System.out.println("ClassName|" + clazz.getName() + "|END");
                                            }
                                        }
                                    }
                                    if(bpPlayers.size() != 0) {
                                        BpPlayer.bpPlayers = new ArrayList<>(bpPlayers);
                                        System.out.println("LIsta: " + BpPlayer.bpPlayers.size());
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            //System.out.println("ClassType: " + field.getType().getName());
                        }

                    }
                }

                /*if(type == 3){// find players list class
                    for(Field field : clazz.getDeclaredFields()){
                        if(field.getType().getName().equals("java.util.ArrayList")){
                            field.setAccessible(true);
                            try {
                                List<Object> objects = (List<Object>) field.get(instance);
                                if(objects != null)
                                for(Object o : objects){
                                    if(o == null) continue;
                                    String objStr = o.toString();
                                    if(objStr.contains("MpServer")) {
                                        System.out.println("Object: " + o.toString());
                                        System.out.println("ClassName|" + clazz.getName() + "|END");
                                    }
                                }
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                            //System.out.println("ClassType: " + field.getType().getName());
                        }
                    }
                }*/

                /*if(type == 2){
                    int doubleIndex = 0;
                    int floatIndex = 0;
                    for(Field field : clazz.getDeclaredFields()){
                        try {
                            field.setAccessible(true);
                            System.out.println(field.get(instance));
                            if (field.getType().getName().equals("double")) {
                                double val = field.getDouble(instance);
                                if(val > 60 && val < 70) {
                                    System.out.println(doubleIndex + " >> double >> " + val);
                                }
                                doubleIndex++;
                            }
                            if (field.getType().getName().equals("float")) {
                                //System.out.println(floatIndex + " >> float >> " + field.getFloat(instance));
                                floatIndex++;
                            }
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }*/

                if(type == 1){
                    Vclip.instance = null;
                    int doubleIndex = 0;
                    int floatIndex = 0;
                    for(Field field : clazz.getDeclaredFields()){
                        try {
                            field.setAccessible(true);
                            if (field.getType().getName().equals("double")) {
                                double val = field.getDouble(instance);
                                if(doubleIndex == 1) Vclip.y = field;
                                if(doubleIndex == 2) Vclip.x = field;
                                if(doubleIndex == 3) Vclip.z = field;

                                if(doubleIndex == 9) Vclip.motionX = field;
                                if(doubleIndex == 8) Vclip.motionY = field;
                                if(doubleIndex == 3) Vclip.motionZ = field;

                                /*if(Vclip.motionX != null){
                                    double motionX = Vclip.motionX.getDouble(instance);
                                    double newMotionX = 0;

                                    if(motionX > 0){
                                        newMotionX = 0.05;
                                    }else {
                                        newMotionX = -0.05;
                                    }

                                    Vclip.motionX.setDouble(instance, motionX);
                                }

                                if(Vclip.motionZ != null){
                                    double motionZ = Vclip.motionZ.getDouble(instance);

                                    double newMotionZ = 0;


                                    if(motionZ > 0){
                                        newMotionZ = 0.05;
                                    }else {
                                        newMotionZ = -0.05;
                                    }



                                    Vclip.motionZ.setDouble(instance, motionZ);
                                }

                                if(Vclip.motionY != null) {
                                    double motionY = Vclip.motionY.getDouble(instance);

                                    //Vclip.motionY.setDouble(instance, motionY);
                                }
                                        //Vclip.motionY.setDouble(instance, 5);
                                }*/

                                //System.out.println(doubleIndex + " >> double >> " + val);
                                doubleIndex++;
                            }
                            if (field.getType().getName().equals("float")) {
                                //System.out.println(floatIndex + " >> float >> " + field.getFloat(instance));
                                floatIndex++;
                            }
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                    Vclip.setPositionAndRotation.clear();
                    for(Method method : clazz.getDeclaredMethods()){
                        if(method.getParameterCount() == 5)
                            if(method.getParameterTypes()[0].getName().equals("double") &&
                                    method.getParameterTypes()[1].getName().equals("double") &&
                                    method.getParameterTypes()[2].getName().equals("double") &&
                                    method.getParameterTypes()[3].getName().equals("float") &&
                                    method.getParameterTypes()[4].getName().equals("float")){

                                Vclip.setPositionAndRotation.add(method);
                                //System.out.println("XD");


                                /*try {
                                    System.out.println("NAME|" + clazz.getName() + "|END NAME");
                                    method.invoke(instance, 243, val - 3.0, 102, 0, 0);
                                } catch (InvocationTargetException e) {
                                    e.printStackTrace();
                                }*/
                            }
                    }
                    Vclip.instance = instance;
                   /* try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                }

                for (Field field : clazz.getDeclaredFields()) {
                    /*if (field.getType().getName().equals("double")) {
                        field.setAccessible(true);
                        try {
                            double val = field.getDouble(instance);
                            if(val > 60.0 && val < 80.0){
                                System.out.println("double >> " + val);
                                for(Method method : clazz.getDeclaredMethods()){
                                    if(method.getParameterCount() == 5)
                                    if(method.getParameterTypes()[0].getName().equals("double") &&
                                            method.getParameterTypes()[1].getName().equals("double") &&
                                            method.getParameterTypes()[2].getName().equals("double") &&
                                            method.getParameterTypes()[3].getName().equals("float") &&
                                            method.getParameterTypes()[4].getName().equals("float")){
                                        System.out.println("XD");
                                        try {
                                            System.out.println("NAME|" + clazz.getName() + "|END NAME");
                                            method.invoke(instance, 243, val - 3.0, 102, 0, 0);
                                        } catch (InvocationTargetException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                                for (Field field2 : clazz.getDeclaredFields()) {
                                    field2.setAccessible(true);
                                    //System.out.println(field2.get(instance));
                                }


                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }


                                //field.setDouble(instance, val - 3.0);
                            }
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }*/
                    i++;
                }
            }
            try {
                Thread.sleep(1); //13 cps chyba
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setInstance(Object instance) {
        this.instance = instance;
    }
}
