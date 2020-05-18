package com.mygames.flappybird;
import com.badlogic.gdx.Gdx;

public class Settings
{
    public static boolean soundEnabled = true;

    public static final float VIRTUAL_WIDTH = 768;
    public static final float VIRTUAL_HEIGHT = 1024;

    public static int[] highscores = {5,4,3,2,1};

    public static void load(){

    }

    public static void save(){

    }

    public static void addScore(int score){
        for(int i = 0; i < 5; i++){
            if(score > highscores[i]){
                highscores[i] = score;
                break;
            }
        }
    }

    public static void orderList(int[] list){
        int aux = 0;
        for(int i = 0; i < list.length; i++){
            for(int j = i + 1; j < list.length; j++){
                if(list[j] > list[i]){
                    aux = list[i];
                    list[i] = list[j];
                    list[j] = aux;
                }
            }
        }
    }
}