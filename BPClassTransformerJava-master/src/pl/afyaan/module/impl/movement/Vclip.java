package pl.afyaan.module.impl.movement;

import com.google.gson.Gson;
import pl.afyaan.module.impl.ModuleManager;
import pl.afyaan.module.impl.Position;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Vclip {
    public static Object instance;

    public static Field x;
    public static Field y;
    public static Field z;
    public static Field pitch;
    public static Field yaw;

    public static Field motionX;
    public static Field motionY;
    public static Field motionZ;

    public static List<Method> setPositionAndRotation = new ArrayList<>();

    public static void onEnable(){
        new Thread(()->{
            System.out.println("Vclip 1");
            if(instance != null) {
                System.out.println("Vclip 2");
                try {
                    double x_ = ModuleManager.newX;
                    double y_ = ModuleManager.newY;
                    double z_ = ModuleManager.newZ;
                    for (Method method : setPositionAndRotation) {
                        method.invoke(instance, x_, y_, z_, 0.0f, 0.0f);
                    }
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
