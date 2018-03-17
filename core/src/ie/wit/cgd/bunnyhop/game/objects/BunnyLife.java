package ie.wit.cgd.bunnyhop.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ie.wit.cgd.bunnyhop.game.Assets;

public class BunnyLife extends AbstractGameObject {
    private TextureRegion   regBunnyLife;
    public boolean          collected;

    public BunnyLife() {
        init();
    }

    private void init() {
        dimension.set(0.5f, 0.5f);
        regBunnyLife = Assets.instance.bunnyLife.bunnyLife;
        // Set bounding box for collision detection
        bounds.set(0, 0, dimension.x, dimension.y);
        collected = false;
    }

    public void render(SpriteBatch batch) {
        if (collected) return;
        TextureRegion reg = null;
        reg = regBunnyLife;
        batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x,
                scale.y, rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(),
                false, false);
    }
}