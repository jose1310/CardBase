package com.fba.cardboard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;

public class Card extends Actor implements Comparable<Card>{
    final Actor actor;
    private Rectangle bounds;
    private CardBoard parent;
    private float offsetX=0;
    private float offsetY=0;

    public Card(CardBoard parent, final Actor actor, final String name){
        this.parent = parent;
        this.actor=actor;
        this.setName(name);
        bounds = new Rectangle(0, 0, actor.getWidth(), actor.getHeight());
        setTouchable(Touchable.enabled);
        parent.multiplexer.addProcessor(new InputAdapter(){

            @Override
            public boolean touchDown(int x, int y, int pointer, int button) {
                if(contains(new Vector2(x,(float)Gdx.graphics.getHeight()-y))) {
                    Gdx.app.log("LOG", "TAPPED " + name);
                    return super.touchDown(x, y, pointer, button);
                }
                return false;
            }
        });
    }

    public void update(float x,float y){
        update(new Vector2(x,y));
    }
    public void update(Vector2 pos){
        if(offsetX==0 && offsetY==0) {
            offsetX = pos.x - actor.getX();
            offsetY = pos.y - actor.getY();
        }
        actor.setX(pos.x-offsetX);
        actor.setY(pos.y-offsetY);

        updateBounds();
    }

    public void drop(){
        offsetX=0;
        offsetY=0;
    }

    public boolean contains(Vector2 pos) {
        //System.out.println("Valido "+getName()+" en la posicion"+pos);
        //System.out.println("Su bounds es "+bounds);
        return bounds.contains(pos);
    }

    public Rectangle getBounds(){
        return bounds;
    }

    public void updateBounds(){
        bounds = new Rectangle(actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight());
    }

    @Override
    public int compareTo(Card o) {
        if (this.getZIndex() < o.getZIndex()) {
            return -1;
        } else if (this.getZIndex() == o.getZIndex()) {
            return 0;
        } else {
            return 1;
        }
    }
}
