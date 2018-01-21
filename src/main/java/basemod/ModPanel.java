package basemod;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.InputHelper;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;

public class ModPanel {
    private static Texture texture;
    
    private boolean waitingOnKey;
    private InputProcessor oldInputProcessor = null;
    private ModButton consoleKeyButton;
    public String consoleKeyLabel;
    
    public boolean isUp = false;
    
    public ModPanel() {
        texture = new Texture(Gdx.files.internal("img/ModPanelBg.png"));
        consoleKeyLabel = "Change console hotkey (" + Keys.toString(DevConsole.toggleKey) + ")";
        
        consoleKeyButton = new ModButton(350.0f*Settings.scale, 650.0f*Settings.scale, this, (me) -> {       
            waitingOnKey = true;
            oldInputProcessor = Gdx.input.getInputProcessor();
            Gdx.input.setInputProcessor(new InputAdapter() {
                @Override
                public boolean keyUp(int keycode) {
                    DevConsole.toggleKey = keycode;
                    waitingOnKey = false;
                    Gdx.input.setInputProcessor(oldInputProcessor);
                    return true;
                }
            });
        });
    }
    
    public void render(SpriteBatch sb) {
        renderBg(sb);
        renderText(sb);
        renderButtons(sb);
    }
    
    private void renderBg(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        sb.draw(texture, (float)Settings.WIDTH / 2.0f - 682.0f, Settings.OPTION_Y - 376.0f, 682.0f, 376.0f, 1364.0f, 752.0f, Settings.scale, Settings.scale, 0.0f, 0, 0, 1364, 752, false, false);
    }
    
    private void renderText(SpriteBatch sb) {
        FontHelper.renderFontLeftDownAligned(sb, FontHelper.buttonLabelFont, consoleKeyLabel, 475.0f*Settings.scale, 700.0f*Settings.scale, Color.WHITE);
    }
    
    private void renderButtons(SpriteBatch sb) {
        consoleKeyButton.render(sb);
    }
    
    public void update() {
        updateText();
        updateButtons();
        
        if (InputHelper.pressedEscape) {
            InputHelper.pressedEscape = false;
            BaseMod.modSettingsUp = false;
        }
        
        if (!BaseMod.modSettingsUp) {
            waitingOnKey = false;
            Gdx.input.setInputProcessor(oldInputProcessor);
            CardCrawlGame.mainMenuScreen.lighten();
            CardCrawlGame.mainMenuScreen.screen = MainMenuScreen.CurScreen.MAIN_MENU;
            CardCrawlGame.cancelButton.hideInstantly();
            isUp = false;
        }
    }
    
    private void updateText() {
        consoleKeyLabel = waitingOnKey ? "Press key" : "Change console hotkey (" + Keys.toString(DevConsole.toggleKey) + ")";
    }
    
    private void updateButtons() {
        consoleKeyButton.update();
    }
}