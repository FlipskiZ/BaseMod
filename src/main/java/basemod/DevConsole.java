package basemod;

import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.actions.common.*;
import java.util.Arrays;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class DevConsole implements PostInitializeSubscriber, PostRenderSubscriber, PostUpdateSubscriber {    
    public static final Logger logger = LogManager.getLogger(BaseMod.class.getName());
    private static final float CONSOLE_X = 75.0f;
    private static final float CONSOLE_Y = 75.0f;
    private static final float CONSOLE_W = 800.0f;
    private static final float CONSOLE_H = 40.0f;
    private static final float CONSOLE_PAD_X = 15.0f;
    private static final int CONSOLE_TEXT_SIZE = 30;
    private static final int BACKSPACE_INTERVAL = 4;
    
    private static BitmapFont consoleFont = null;
    private static Color consoleColor = Color.BLACK;
    private static InputProcessor consoleInputProcessor; 
    private static InputProcessor otherInputProcessor = null;
    
    public static boolean backspace = false;
    public static boolean visible = false;
    public static int backspaceWait = 0;
    public static int toggleKey = Keys.GRAVE;
    public static String currentText = "";
    
    public DevConsole() {
        BaseMod.subscribeToPostInitialize(this);
        BaseMod.subscribeToPostRender(this);
        BaseMod.subscribeToPostUpdate(this);
    }
    
    public static void execute() {
        String[] tokens = currentText.split(" ");
        currentText = "";
        
        if (tokens.length < 1) return;
        for (int i = 0; i < tokens.length; i++) {
            tokens[i] = tokens[i].trim();
        }
        
        switch (tokens[0].toLowerCase()) {
            case "relic": {
                cmdRelic(tokens);
                break;
            }
            case "card": {
                cmdCard(tokens);
                break;
            }
            case "info": {
                Settings.isInfo = !Settings.isInfo;
                break;
            }
            default: {
                // TODO: Implement command hook
                break;
            }
        }
    }
    
    private static void cmdRelic(String[] tokens) {
        if (AbstractDungeon.player != null) {
            if (tokens.length < 3) {
                return;
            }
            
            if (tokens[1].equals("r") && tokens.length > 2) {
                String[] relicNameArray = Arrays.copyOfRange(tokens, 2, tokens.length);
                String relicName = String.join(" ", relicNameArray);
                AbstractDungeon.player.loseRelic(relicName);
            } else if (tokens[1].equals("add") && tokens.length > 2) {
                String[] relicNameArray = Arrays.copyOfRange(tokens, 2, tokens.length);
                String relicName = String.join(" ", relicNameArray);
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, RelicLibrary.getRelic(relicName).makeCopy());
            }
        }
    }
    
    private static void cmdCard(String[] tokens) {
        if (AbstractDungeon.player != null) {
            if (tokens.length < 3) {
                return;
            }
            logger.info("" + String.join(" ", tokens));
            logger.info("" + tokens[1]);
            
            if (tokens[1].equals("add") && tokens.length > 2) {
                String[] cardNameArray = Arrays.copyOfRange(tokens, 2, tokens.length);
                String cardName = String.join(" ", cardNameArray);
                logger.info("" + cardName);
                AbstractCard c = CardLibrary.getCard(cardName);
                if (c != null) {
                    c = c.makeCopy();
                    AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c, true));
                }
            } else if (tokens[1].equals("r") && tokens.length > 2){
                String[] cardNameArray = Arrays.copyOfRange(tokens, 2, tokens.length);
                String cardName = String.join(" ", cardNameArray);
                logger.info("" + cardName);
                
                boolean removed = false;
                AbstractCard toRemove = null;
                for (AbstractCard c : AbstractDungeon.player.hand.group) {
                    logger.info("" + c.cardID);
                    if(removed) break;
                    if(c.cardID.equals(cardName)){
                        toRemove = c;
                        removed = true;
                    }
                }
                if(removed) AbstractDungeon.player.hand.moveToExhaustPile(toRemove);
            }
        }
    }
    
    public void receivePostInitialize() {
        consoleInputProcessor = new ConsoleInputProcessor();      
        
        // Console font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/FantasqueSansMono/FantasqueSansMono-Regular.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = (int) (CONSOLE_TEXT_SIZE * Settings.scale);
        consoleFont = generator.generateFont(parameter);
        generator.dispose();
    }
    
    public void receivePostRender(SpriteBatch sb) {
        if (visible && consoleFont != null) {       
            // Since we need a shape renderer, need to end then restart the SpriteBatch
            // Should probably just make a background texture for the console so this doesn't need to be done
            sb.end();
            
            ShapeRenderer consoleBackground = new ShapeRenderer();
            consoleBackground.begin(ShapeType.Filled);
            consoleBackground.setColor(consoleColor);
            consoleBackground.rect(CONSOLE_X, CONSOLE_Y, (CONSOLE_W * Settings.scale), (CONSOLE_H * Settings.scale));
            consoleBackground.end();
            
            sb.begin();
            
            float x = (CONSOLE_X + (CONSOLE_PAD_X * Settings.scale));
            float y = (CONSOLE_Y + (float) Math.floor(CONSOLE_TEXT_SIZE * Settings.scale));
            consoleFont.draw(sb, currentText, x, y);
        }
    }
    
    public void receivePostUpdate() {
        --backspaceWait;
        
        if (backspace && currentText.length() > 0) {
            if (backspaceWait <= 0) {
                currentText = currentText.substring(0, currentText.length()-1);
                backspaceWait = BACKSPACE_INTERVAL;
            }
        }
        
        if (Gdx.input.isKeyJustPressed(toggleKey)) {
            if (visible) {
                currentText = "";
            } else {
                otherInputProcessor = Gdx.input.getInputProcessor();
            }
            
            Gdx.input.setInputProcessor(visible ? otherInputProcessor : consoleInputProcessor);
            visible = !visible;
        }
    }
}