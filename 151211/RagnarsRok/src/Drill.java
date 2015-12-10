/**
 * Created by Linus on 2015-12-07.
 */
import org.newdawn.slick.*;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Drill {

    /** Sprites **/
    private SpriteSheet drillTipSprite;
    private SpriteSheet drillSprite;
    private SpriteSheet drillBaseSprite;

    /** Animations **/
    private Animation drillTipAnimation;
    private Animation drillAnimation;
    private Animation drillBaseAnimation;

    /** Sounds **/
    private Audio wawWallHit;
    private Audio wawDrillDown;

    private ArrayList drill;

    private int x;
    private int y;
    private int position[];
    private  int direction;


    public Drill(int direction, int x, int y){

        //Initialize drill
        position = new int[]{9,0};
        this.x = x;
        this.y = y;

        try {
            drillTipSprite = new SpriteSheet("/img/drill_tip.png", 32, 32);
            drillSprite = new SpriteSheet("/img/drill_part.png", 32, 32);
            drillBaseSprite = new SpriteSheet("/img/drill_base.png", 32, 32);


            this.direction = direction;

            drillTipAnimation = new Animation(drillTipSprite, 10);
            drillAnimation = new Animation(drillSprite, 10);
            drillBaseAnimation = new Animation(drillBaseSprite,50);
            drillBaseAnimation.setLooping(false);

            drill = new ArrayList<Animation>();
            drill.add(drillTipAnimation);
            drill.add(drillBaseAnimation);

            wawWallHit = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("/sfx/drill_wall_hit.wav"));
            wawDrillDown = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("/sfx/drilldown.wav"));



        }catch(SlickException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void drawDrill(Graphics g){
        int offset = 0;
        for(int i = drill.size()-1; i >=0 ; i--){
            if(direction == GameStatics.PLAYER_ORIENTATION_N) {
                g.drawAnimation((Animation)drill.get(i), x, y + offset);
                offset += drillAnimation.getHeight();
            }
            else if(direction == GameStatics.PLAYER_ORIENTATION_S) {
                ((Animation)drill.get(i)).getCurrentFrame().setRotation(180);
                g.drawAnimation((Animation)drill.get(i), x, y + offset);
                offset -= drillAnimation.getHeight();
            }
            else if(direction == GameStatics.PLAYER_ORIENTATION_E) {
                ((Animation)drill.get(i)).getCurrentFrame().setRotation(270);
                g.drawAnimation((Animation)drill.get(i), x + offset, y);
                offset += drillAnimation.getWidth();
            }
            else if(direction == GameStatics.PLAYER_ORIENTATION_W) {
                ((Animation)drill.get(i)).getCurrentFrame().setRotation(90);
                g.drawAnimation((Animation)drill.get(i), x + offset, y);
                offset -= drillAnimation.getWidth();
            }
            else
            {
                System.out.println("It's not right! It's wrong!");
            }
        }
    }

    public int getDrillPositionX(){
        return position[0];
    }

    public int getDrillPositionY(){
        return position[1];
    }

    public boolean canDrillDown(){
        if(drill.size() < 11){
            return true;
        }
        else{
            return false;
        }
    }

    public void drillDown(){
        if (drill.size() < 12) {
            wawDrillDown.playAsSoundEffect(1f, 1f, false);
            drill.add(1, drillAnimation);
            position[1]++;
        }
    }

    public void drillUp() {
        if (drill.size() > 2) {
            drill.remove(drill.size() - 2);
            position[1] --;
        }
    }

    public void drillLeft(){
        if(drill.size() > 2 || position[0] == 4) {
            wawWallHit.playAsSoundEffect(1f, 1f, false);
            return;
        }
        position[0] --;

        if(direction == GameStatics.PLAYER_ORIENTATION_N) {
            x -= drillAnimation.getWidth();
        }
        else if(direction == GameStatics.PLAYER_ORIENTATION_S) {
            x += drillAnimation.getWidth();
        }
        else if(direction == GameStatics.PLAYER_ORIENTATION_E) {
            y -= drillAnimation.getHeight();
        }
        else if(direction == GameStatics.PLAYER_ORIENTATION_W) {
            y += drillAnimation.getHeight();
        }
        else
        {
            System.out.println("It's not right! It's wrong!");
        }
    }

    public void drillRight(){
        if(drill.size() > 2 || position[0] == 15) {
            wawWallHit.playAsSoundEffect(1f, 1f, false);
            return;
        }
        position[0] ++;
        drillBaseAnimation.start();

        if(direction == GameStatics.PLAYER_ORIENTATION_N) {
            x += drillAnimation.getWidth();
        }
        else if(direction == GameStatics.PLAYER_ORIENTATION_S) {
            x -= drillAnimation.getWidth();
        }
        else if(direction == GameStatics.PLAYER_ORIENTATION_E) {
            y += drillAnimation.getHeight();
        }
        else if(direction == GameStatics.PLAYER_ORIENTATION_W) {
            y -= drillAnimation.getHeight();
        }
        else
        {
            System.out.println("It's not right! It's wrong!");
        }
    }
}

