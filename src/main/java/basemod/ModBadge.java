package basemod;

import basemod.interfaces.RenderSubscriber;
import basemod.interfaces.PreUpdateSubscriber;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.InputHelper;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.screens.mainMenu.EarlyAccessPopup;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import java.util.ArrayList;

public class ModBadge implements RenderSubscriber, PreUpdateSubscriber {
    private Texture texture;
    private String modName;
    private String author;
    private String description;
    private String tip;
    
    private float x;
    private float y;
    private float w;
    private float h;
    
    private Hitbox hb;
    private ModPanel modPanel;
    
    public ModBadge(Texture t, float xPos, float yPos, String name, String auth, String desc) {
        modName = name;
        author = auth;
        description = desc;
        tip = author + " NL " + description;
        
        texture = t;
        x = xPos;
        y = yPos;
        w = t.getWidth();
        h = t.getHeight();
        
        hb = new Hitbox(x, y, w, h);
        modPanel = new ModPanel();
        
        BaseMod.subscribeToRender(this);
        BaseMod.subscribeToPreUpdate(this);
    }
    
    public void receiveRender(SpriteBatch sb) {
        if (CardCrawlGame.mainMenuScreen != null && CardCrawlGame.mainMenuScreen.screen == MainMenuScreen.CurScreen.MAIN_MENU && !EarlyAccessPopup.isUp && !BaseMod.modSettingsUp) { 
            sb.setColor(Color.WHITE); 
            sb.draw(texture, x, y);
            hb.render(sb);
        } else if (modPanel.isUp) {
            modPanel.render(sb);
        }
    }
    
    public void receivePreUpdate() {
        if (CardCrawlGame.mainMenuScreen != null && CardCrawlGame.mainMenuScreen.screen == MainMenuScreen.CurScreen.MAIN_MENU && !EarlyAccessPopup.isUp && !BaseMod.modSettingsUp) {  
            hb.update();
            
            if (hb.justHovered) {
                CardCrawlGame.sound.playV("UI_HOVER", 0.75f);
            }
            
            if (hb.hovered) {
                TipHelper.renderGenericTip(x+(2*w), y+h, modName, tip);
                
                if (InputHelper.justClickedLeft) {
                    CardCrawlGame.sound.playA("UI_CLICK_1", -0.1f);
                    hb.clickStarted = true;
                }
            }
            
            if (hb.clicked) {
                hb.clicked = false;
                onClick();
            }
        } else if (modPanel.isUp) {
            modPanel.update();
        }        
    }
    
    private void onClick() {
        BaseMod.modSettingsUp = true;
        modPanel.isUp = true;
        
        CardCrawlGame.mainMenuScreen.darken();
        CardCrawlGame.mainMenuScreen.hideMenuButtons();
        CardCrawlGame.mainMenuScreen.screen = MainMenuScreen.CurScreen.SETTINGS;
        CardCrawlGame.cancelButton.show("Close");
    }
}