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

public class DevConsole implements PostInitializeSubscriber, PostRenderSubscriber, PostUpdateSubscriber {    
    private static final float CONSOLE_X = 75.0f;
    private static final float CONSOLE_Y = 75.0f;
    private static final float CONSOLE_W = 800.0f;
    private static final float CONSOLE_H = 40.0f;
    private static final float CONSOLE_PAD_X = 15.0f;
    private static final int CONSOLE_TEXT_SIZE = 30;
    private static final int BACKSPACE_INTERVAL = 3;
    
    private static BitmapFont consoleFont = null;
    private static Color consoleColor = Color.BLACK;
    private static InputProcessor consoleInputProcessor; 
    
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
            tokens[i] = tokens[i].trim().toLowerCase();
        }
        
        switch (tokens[0]) {
            case "test": {
                cmdTest(tokens);
                break;
            }
        }
    }
    
    private static void cmdTest(String[] tokens) {
        currentText = "TEST TEST TEST";
    }
    
    public void receivePostInitialize() {
        consoleInputProcessor = new ConsoleInputProcessor();      
        
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/FantasqueSansMono/FantasqueSansMono-Regular.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = (int) (CONSOLE_TEXT_SIZE * Settings.scale);
        consoleFont = generator.generateFont(parameter);
        generator.dispose();
    }
    
    public void receivePostRender() {
        if (visible && consoleFont != null) {
            ShapeRenderer consoleBackground = new ShapeRenderer();
            consoleBackground.begin(ShapeType.Filled);
            consoleBackground.setColor(consoleColor);
            consoleBackground.rect(CONSOLE_X, CONSOLE_Y, (CONSOLE_W * Settings.scale), (CONSOLE_H * Settings.scale));
            consoleBackground.end();
            
            float x = (CONSOLE_X + (CONSOLE_PAD_X * Settings.scale));
            float y = (CONSOLE_Y + (float) Math.floor(CONSOLE_TEXT_SIZE * Settings.scale));
            
            SpriteBatch consoleTextBatch = new SpriteBatch();
            consoleTextBatch.begin();
            consoleFont.draw(consoleTextBatch, currentText, x, y);
            consoleTextBatch.end();
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
            if (visible) currentText = "";
            Gdx.input.setInputProcessor(visible ? null : consoleInputProcessor);
            visible = !visible;
        }
    }
}