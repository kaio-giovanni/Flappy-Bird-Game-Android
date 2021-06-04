package com.mygames.flappybird.config;

public class Settings
{
    public static boolean soundEnabled = true;

    public static final float VIRTUAL_WIDTH = 768;
    public static final float VIRTUAL_HEIGHT = 1024;
    public static final float SCORE_RECT_WIDTH = 400;
    public static final float SCORE_RECT_HEIGHT = 300;
    public static final float SCORE_RECT_POS_X = VIRTUAL_WIDTH/2 - 200;
    public static final float SCORE_REACT_POS_Y = VIRTUAL_HEIGHT/2 - 150;
    public static final float BUTTON_MENU_WIDTH = 100;
    public static final float BUTTON_MENU_HEIGHT = 50;
    public static final float BUTTON_MENU_POS_X = VIRTUAL_WIDTH/2 - 50;
    public static final float BUTTON_MENU_POS_Y = VIRTUAL_HEIGHT/2 - 300;

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