package pl.afyaan.module.impl;

import pl.afyaan.module.impl.movement.Speed;
import pl.afyaan.module.impl.movement.Vclip;

public class toggle {
    public static void flyOn(){
         ModuleManager.fly = true;
         //System.out.println("kurwa wlaczone");
    }
    public static void flyOff(){
        ModuleManager.fly = false;
        //System.out.println("kurwa wylaczone");
    }
    public static void killauraOn(){
        ModuleManager.killaura = true;
        //System.out.println("kurwa wlaczone");
    }
    public static void killauraOff(){
        ModuleManager.killaura = false;
        //System.out.println("kurwa wylaczone");
    }
    public static void motionXOn(){
        ModuleManager.motionX = true;
    }
    public static void motionYOn(){
        ModuleManager.motionY = true;
    }
    public static void motionZOn(){
        ModuleManager.motionZ = true;
    }
    public static void motionXOff(){
        ModuleManager.motionX = false;
    }
    public static void motionYOff(){
        ModuleManager.motionY = false;
    }
    public static void motionZOff(){
        ModuleManager.motionZ = false;
    }
    public static void setMotionX(double size){
        Speed.motionX = size;
    }
    public static void setMotionY(double size){
        Speed.motionY = size;
    }
    public static void setMotionZ(double size){
        Speed.motionZ = size;
    }
    public static void stepSize(float size){
        ModuleManager.stepSize = size;
    }
    public static void setSpeedmine(float speed){
        ModuleManager.speedmine = speed;
    }
    public static void stepToggle(){
        ModuleManager.step = !ModuleManager.step;
    }

    public static void setVclip(double vclip){
        ModuleManager.vclip = vclip;
    }
    public static void setNewX(double size){
        ModuleManager.newX = size;
    }
    public static void setNewY(double size){
        ModuleManager.newY = size;
    }
    public static void setNewZ(double size){
        ModuleManager.newZ = size;
    }
    public static void killauraReach(double size){
        ModuleManager.reach = size;
    }
}
