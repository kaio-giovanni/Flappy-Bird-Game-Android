package com.mygames.flappybird;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Color;

public class Assets
{
    public static BitmapFont font;
    public static Texture spriteSheet;

    public static TextureRegion backgroundDayRegion;
    public static TextureRegion backgroundNightRegion;

    public static TextureRegion foregroundRegion;

    public static TextureRegion scoreRegion;

    public static TextureRegion logo;
    public static TextureRegion highScoreRegion;
    public static TextureRegion playRegion;

    public static TextureRegion getReadyRegion;
    public static TextureRegion tapToInitRegion;
    public static TextureRegion gameOverRegion;
    public static TextureRegion menuMainRegion;

    public static TextureRegion medalGold;
    public static TextureRegion medalSilver;

    public static TextureRegion pipeNorthRegion;
    public static TextureRegion pipeSouthRegion;

    public static TextureRegion[] bird;

    public static Music music;
    public static Sound clickSound;
    public static Sound birdJump;
    public static Sound birdCollided;

    public static void playSound (Sound sound) {
        if (Settings.soundEnabled){

            sound.play(1);
        }
    }

    public static void load(){
        spriteSheet = new Texture("images/FlappyBird.png");

        backgroundDayRegion   = new TextureRegion(spriteSheet, 0   , 0  , 145, 257);
        backgroundNightRegion = new TextureRegion(spriteSheet, 146 , 0  , 145, 257);

        foregroundRegion      = new TextureRegion(spriteSheet, 292 , 0  , 168, 57);

        scoreRegion           = new TextureRegion(spriteSheet, 3   , 259, 112, 57);

        logo  				  = new TextureRegion(spriteSheet, 349 , 89 , 92 , 29);
        playRegion            = new TextureRegion(spriteSheet, 350 , 118, 58 , 29);
        highScoreRegion       = new TextureRegion(spriteSheet, 410 , 118, 58 , 29);

        getReadyRegion        = new TextureRegion(spriteSheet, 292 , 57 , 99 , 30);
        gameOverRegion        = new TextureRegion(spriteSheet, 395 , 57 , 96 , 26);
        tapToInitRegion       = new TextureRegion(spriteSheet, 291 , 86 , 59 , 55);
        menuMainRegion        = new TextureRegion(spriteSheet, 462 , 26 , 40 , 13);

        pipeSouthRegion       = new TextureRegion(spriteSheet, 84  , 322, 26 , 161);
        pipeNorthRegion       = new TextureRegion(spriteSheet, 56  , 322, 26 , 160);

        bird = new TextureRegion[3];

        bird[0]               = new TextureRegion(spriteSheet, 3   , 491, 18 , 13);
        bird[1]               = new TextureRegion(spriteSheet, 31  , 491, 18 , 13);
        bird[2]               = new TextureRegion(spriteSheet, 59  , 491, 18 , 13);

        medalGold             = new TextureRegion(spriteSheet, 120 , 281, 24 , 24);
        medalSilver           = new TextureRegion(spriteSheet, 120 , 258, 24 , 24);

        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(5,5);

        music = Gdx.audio.newMusic(Gdx.files.internal("audio/music.m4a"));
        music.setLooping(true);
        music.setVolume(0.5f);

        if (Settings.soundEnabled) music.play();

        clickSound = Gdx.audio.newSound(Gdx.files.internal("audio/ButtonClick.wav"));

    }
}