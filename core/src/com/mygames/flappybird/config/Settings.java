package com.mygames.flappybird.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Settings {
    public static boolean soundEnabled = true;

    public static final float VIRTUAL_WIDTH = 768;
    public static final float VIRTUAL_HEIGHT = 1024;
    public static final float SCORE_RECT_WIDTH = 400;
    public static final float SCORE_RECT_HEIGHT = 300;
    public static final float SCORE_RECT_POS_X = VIRTUAL_WIDTH / 2 - 200;
    public static final float SCORE_REACT_POS_Y = VIRTUAL_HEIGHT / 2 - 150;
    public static final float BUTTON_MENU_WIDTH = 120;
    public static final float BUTTON_MENU_HEIGHT = 60;
    public static final float BUTTON_MENU_POS_X = VIRTUAL_WIDTH / 2 - 50;
    public static final float BUTTON_MENU_POS_Y = VIRTUAL_HEIGHT / 2 - 300;

    private final Preferences preferences;
    private long[] topRecords;

    public Settings() {
        preferences = Gdx.app.getPreferences("gameData");
        topRecords = load();
    }

    public long[] load() {
        long record1 = preferences.getLong("record1");
        long record2 = preferences.getLong("record2");
        long record3 = preferences.getLong("record3");

        return new long[]{record1, record2, record3};
    }

    public void saveRecord(Long record) {
        for(int index = 0; index < topRecords.length; index++) {
            if(record > topRecords[index]) {
                String key = String.format("record%s", index + 1);
                preferences.putLong(key, record);
                break;
            }
        }

        preferences.flush();
    }
}