package basemod;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.InputHelper;
import java.util.function.Consumer;

public class ModButton {
    private static final float HB_SHRINK = 14.0f;
    
    private Consumer<ModButton> click;
    private Hitbox hb;
    private String text;
    private Texture texture;
    private float x;
    private float y;
    private float w;
    private float h;

    public ModPanel parent;
    
    public ModButton(float xPos, float yPos, ModPanel p, Consumer<ModButton> c) {
        texture = new Texture(Gdx.files.internal("img/BlankButton.png"));
        x = xPos;
        y = yPos;
        w = texture.getWidth();
        h = texture.getHeight();
        hb = new Hitbox(x+HB_SHRINK, y+HB_SHRINK, w-(2*HB_SHRINK), h-(2*HB_SHRINK));
        
        parent = p;
        click = c;
    }
    
    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE); 
        sb.draw(texture, x, y);
        hb.render(sb);
    }
    
    public void update() { 
        hb.update();
        
        if (hb.justHovered) {
            CardCrawlGame.sound.playV("UI_HOVER", 0.75f);
        }
        
        if (hb.hovered) {
            if (InputHelper.justClickedLeft) {
                CardCrawlGame.sound.playA("UI_CLICK_1", -0.1f);
                hb.clickStarted = true;
            }
        }
        
        if (hb.clicked) {
            hb.clicked = false;
            onClick();
        }
    } 
    
    private void onClick() {
        BaseMod.logger.info("onClick");
        click.accept(this);
    }
}