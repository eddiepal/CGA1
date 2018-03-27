package ie.wit.cgd.bunnyhop.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import ie.wit.cgd.bunnyhop.game.objects.AbstractGameObject;
import ie.wit.cgd.bunnyhop.game.objects.BunnyHead;
import ie.wit.cgd.bunnyhop.game.objects.BunnyLife;
import ie.wit.cgd.bunnyhop.game.objects.Clouds;
import ie.wit.cgd.bunnyhop.game.objects.Feather;
import ie.wit.cgd.bunnyhop.game.objects.Goal;
import ie.wit.cgd.bunnyhop.game.objects.Bird;
import ie.wit.cgd.bunnyhop.game.objects.GoldCoin;
import ie.wit.cgd.bunnyhop.game.objects.Gun;
import ie.wit.cgd.bunnyhop.game.objects.Mountains;
import ie.wit.cgd.bunnyhop.game.objects.Rock;
import ie.wit.cgd.bunnyhop.game.objects.WaterOverlay;
import ie.wit.cgd.bunnyhop.util.Constants;

public class Level {

    public static final String  TAG = Level.class.getName();
    
    public BunnyHead bunnyHead;
    public Goal goal;
    public Gun gun;
    public Array<Bird> birds;
    public Array<GoldCoin> goldCoins;
    public Array<Feather> feathers;
    public Array<BunnyLife> bunnyLives;

    public enum BLOCK_TYPE {
        EMPTY(0, 0, 0),                   // black
        ROCK(0, 255, 0),                  // green
        PLAYER_SPAWNPOINT(255, 255, 255), // white
        ITEM_FEATHER(255, 0, 255),        // purple
        ITEM_GOLD_COIN(255, 255, 0),      // yellow
    	ITEM_GOAL(255, 0, 0),			  // red
    	ITEM_BUNNY_LIFE(0, 0, 255),		  // blue
    	Bird(0, 255, 255),				  // cyan blue
    	Gun(255, 140, 0);				  // orange

        private int color;

        private BLOCK_TYPE(int r, int g, int b) {
            color = r << 24 | g << 16 | b << 8 | 0xff;
        }

        public boolean sameColor(int color) {
            return this.color == color;
        }

        public int getColor() {
            return color;
        }
    }

    // objects
    public Array<Rock>  rocks;

    // decoration
    public Clouds       clouds;
    public Mountains    mountains;
    public WaterOverlay waterOverlay;

    public Level(String filename) {
        init(filename);
    }
    


    private void init (String filename) {

        // player character
        bunnyHead = null;
        
        // goal
        goal = null;

        // objects
        birds = new Array<Bird>();
        rocks = new Array<Rock>();
        goldCoins = new Array<GoldCoin>();
        feathers = new Array<Feather>();
        bunnyLives = new Array<BunnyLife>();

        // load image file that represents the level data
        Pixmap pixmap = new Pixmap(Gdx.files.internal(filename));
        // scan pixels from top-left to bottom-right
        @SuppressWarnings("unused")
		int lastPixel = -1;
        
  
        for (int pixelY = 0; pixelY < pixmap.getHeight(); pixelY++) {
            for (int pixelX = 0; pixelX < pixmap.getWidth(); pixelX++) {
                AbstractGameObject obj = null;
                float offsetHeight = 0;
                // height grows from bottom to top
                float baseHeight = pixmap.getHeight() - pixelY;
                // get color of current pixel as 32-bit RGBA value
                int currentPixel = pixmap.getPixel(pixelX, pixelY);
                // find matching color value to identify block type at (x,y)
                // point and create the corresponding game object if there is
                // a match
                
                if(Constants.currLevel == Constants.LEVEL_01) {
                if (BLOCK_TYPE.EMPTY.sameColor(currentPixel)) {                             // empty space
                    // do nothing
                } else if (BLOCK_TYPE.ROCK.sameColor(currentPixel)) {                       // rock
                    obj = new Rock();
                    offsetHeight = -3.9f;
                    obj.position.set(pixelX,baseHeight * obj.dimension.y+ offsetHeight);
                    rocks.add((Rock)obj);
                } else if (BLOCK_TYPE.PLAYER_SPAWNPOINT.sameColor(currentPixel)) {          // player spawn point
                    obj = new BunnyHead();
                    offsetHeight = -0f;
                    obj.position.set(pixelX,baseHeight * obj.dimension.y + offsetHeight);
                    bunnyHead = (BunnyHead)obj;
                } else if(BLOCK_TYPE.ITEM_FEATHER.sameColor(currentPixel)) {                // feather
                    obj = new Feather();
                    offsetHeight = -0.6f;
                    obj.position.set(pixelX,baseHeight * obj.dimension.y+ offsetHeight);
                    feathers.add((Feather)obj);
                } else if (BLOCK_TYPE.ITEM_GOLD_COIN.sameColor(currentPixel)) {             // gold coin
                    obj = new GoldCoin();
                    offsetHeight = 0f;
                    obj.position.set(pixelX,baseHeight * obj.dimension.y + offsetHeight);
                    goldCoins.add((GoldCoin)obj);
                }else if (BLOCK_TYPE.ITEM_GOAL.sameColor(currentPixel)) {          // goal
                    obj = new Goal();
                    offsetHeight = 0f;
                    obj.position.set(pixelX,baseHeight * obj.dimension.y + offsetHeight);
                    goal = (Goal)obj;
                }else if (BLOCK_TYPE.ITEM_BUNNY_LIFE.sameColor(currentPixel)) {          // bunny Life
                    obj = new BunnyLife();
                    offsetHeight = -2f;
                    obj.position.set(pixelX,baseHeight * obj.dimension.y + offsetHeight);
                    bunnyLives.add((BunnyLife)obj);
                }else if (BLOCK_TYPE.Gun.sameColor(currentPixel)) {          // gun
                    obj = new Gun();
                    offsetHeight = -8f;
                    obj.position.set(pixelX,baseHeight * obj.dimension.y + offsetHeight);
                    gun = (Gun)obj;
                }else if (BLOCK_TYPE.Bird.sameColor(currentPixel)) {          // bird
                    obj = new Bird();
                    offsetHeight = -1f;
                    obj.position.set(pixelX,baseHeight * obj.dimension.y + offsetHeight);
                    birds.add((Bird)obj);
                } else {                                                                    // unknown object/pixel color
                   // ...
                }
                lastPixel = currentPixel;
				}
                
                else if(Constants.currLevel == Constants.LEVEL_02) {
                if (BLOCK_TYPE.EMPTY.sameColor(currentPixel)) {                             // empty space
                    // do nothing
                } else if (BLOCK_TYPE.ROCK.sameColor(currentPixel)) {                       // rock
                    obj = new Rock();
                    offsetHeight = -12f;
                    obj.position.set(pixelX,baseHeight * obj.dimension.y+ offsetHeight);
                    rocks.add((Rock)obj);
                } else if (BLOCK_TYPE.PLAYER_SPAWNPOINT.sameColor(currentPixel)) {          // player spawn point
                    obj = new BunnyHead();
                    offsetHeight = -8f;
                    obj.position.set(pixelX,baseHeight * obj.dimension.y + offsetHeight);
                    bunnyHead = (BunnyHead)obj;
                } else if(BLOCK_TYPE.ITEM_FEATHER.sameColor(currentPixel)) {                // feather
                    obj = new Feather();
                    offsetHeight = 0f;
                    obj.position.set(pixelX,baseHeight * obj.dimension.y+ offsetHeight);
                    feathers.add((Feather)obj);
                } else if (BLOCK_TYPE.ITEM_GOLD_COIN.sameColor(currentPixel)) {             // gold coin
                    obj = new GoldCoin();
                    offsetHeight = -3;
                    obj.position.set(pixelX,baseHeight * obj.dimension.y + offsetHeight);
                    goldCoins.add((GoldCoin)obj);
                }else if (BLOCK_TYPE.ITEM_GOAL.sameColor(currentPixel)) {          // goal
                    obj = new Goal();
                    offsetHeight = -8f;
                    obj.position.set(pixelX,baseHeight * obj.dimension.y + offsetHeight);
                    goal = (Goal)obj;
                }else if (BLOCK_TYPE.ITEM_BUNNY_LIFE.sameColor(currentPixel)) {        // bunny Life
                    obj = new BunnyLife();
                    offsetHeight = -6f;
                    obj.position.set(pixelX,baseHeight * obj.dimension.y + offsetHeight);
                    bunnyLives.add((BunnyLife)obj);
                }else if (BLOCK_TYPE.Gun.sameColor(currentPixel)) {          // gun
                    obj = new Gun();
                    offsetHeight = -8f;
                    obj.position.set(pixelX,baseHeight * obj.dimension.y + offsetHeight);
                    gun = (Gun)obj;
                } else {                                                                    // unknown object/pixel color
                   // ...
                }
                lastPixel = currentPixel;
				}
                
                else if(Constants.currLevel == Constants.LEVEL_03) {
                    if (BLOCK_TYPE.EMPTY.sameColor(currentPixel)) {                             // empty space
                        // do nothing
                    } else if (BLOCK_TYPE.ROCK.sameColor(currentPixel)) {                       // rock
                        obj = new Rock();
                        offsetHeight = -12f;
                        obj.position.set(pixelX,baseHeight * obj.dimension.y+ offsetHeight);
                        rocks.add((Rock)obj);
                    } else if (BLOCK_TYPE.PLAYER_SPAWNPOINT.sameColor(currentPixel)) {          // player spawn point
                        obj = new BunnyHead();
                        offsetHeight = -8f;
                        obj.position.set(pixelX,baseHeight * obj.dimension.y + offsetHeight);
                        bunnyHead = (BunnyHead)obj;
                    } else if(BLOCK_TYPE.ITEM_FEATHER.sameColor(currentPixel)) {                // feather
                        obj = new Feather();
                        offsetHeight = 0f;
                        obj.position.set(pixelX,baseHeight * obj.dimension.y+ offsetHeight);
                        feathers.add((Feather)obj);
                    } else if (BLOCK_TYPE.ITEM_GOLD_COIN.sameColor(currentPixel)) {             // gold coin
                        obj = new GoldCoin();
                        offsetHeight = -3;
                        obj.position.set(pixelX,baseHeight * obj.dimension.y + offsetHeight);
                        goldCoins.add((GoldCoin)obj);
                    }else if (BLOCK_TYPE.ITEM_GOAL.sameColor(currentPixel)) {          // goal
                        obj = new Goal();
                        offsetHeight = -8f;
                        obj.position.set(pixelX,baseHeight * obj.dimension.y + offsetHeight);
                        goal = (Goal)obj;
                    }else if (BLOCK_TYPE.ITEM_BUNNY_LIFE.sameColor(currentPixel)) {          // bunny Life
                        obj = new BunnyLife();
                        offsetHeight = -6f;
                        obj.position.set(pixelX,baseHeight * obj.dimension.y + offsetHeight);
                        bunnyLives.add((BunnyLife)obj);
                    }else if (BLOCK_TYPE.Gun.sameColor(currentPixel)) {          // goal
                        obj = new Gun();
                        offsetHeight = -8f;
                        obj.position.set(pixelX,baseHeight * obj.dimension.y + offsetHeight);
                        gun = (Gun)obj;
                    } else {                                                                    // unknown object/pixel color
                       // ...
                    }
                    lastPixel = currentPixel;
    				}
			}
		}
        // decoration
        clouds = new Clouds(pixmap.getWidth());
        clouds.position.set(0, 2);
        mountains = new Mountains(pixmap.getWidth());
        mountains.position.set(-1, -1);
        waterOverlay = new WaterOverlay(pixmap.getWidth());
        waterOverlay.position.set(0, -3.75f);

        // free memory
        pixmap.dispose();
        Gdx.app.debug(TAG, "level '" + filename + "' loaded");
    }
    
    public void update(float deltaTime) {
        bunnyHead.update(deltaTime);
        clouds.update(deltaTime);
        goal.update(deltaTime);
        mountains.update(deltaTime);
        gun.update(deltaTime);
        for (Bird bird: birds)
        	bird.update(deltaTime);
        for (Rock rock : rocks)
            rock.update(deltaTime);
        for (GoldCoin goldCoin : goldCoins)
            goldCoin.update(deltaTime);
        for (Feather feather : feathers)
            feather.update(deltaTime);
        for (BunnyLife bunnyLife : bunnyLives)
        	bunnyLife.update(deltaTime);
    }



	public void render(SpriteBatch batch) {
		mountains.render(batch); // Draw Mountains
		for (Rock rock : rocks) // Draw Rocks
			rock.render(batch);
		for (GoldCoin goldCoin : goldCoins) // Draw Gold Coins
			goldCoin.render(batch);
		for (Feather feather : feathers) // Draw Feathers
			feather.render(batch);
		for (BunnyLife bunnyLife : bunnyLives) // Draw Bunny Lives
			bunnyLife.render(batch);
		for (Bird bird : birds) // Draw Feathers
			bird.render(batch);
		bunnyHead.render(batch); // Draw Player Character
		goal.render(batch); // Draw goal item
		waterOverlay.render(batch); // Draw Water Overlay
		clouds.render(batch); // Draw Clouds
		gun.render(batch); // Draw Gun
	}
}