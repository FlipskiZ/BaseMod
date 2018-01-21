package basemod;

import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import java.util.ArrayList;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class BaseMod {
    public static final Logger logger = LogManager.getLogger(BaseMod.class.getName());
    
    private static final int BADGES_PER_ROW = 16;
    private static final float BADGES_X = 640.0f;
    private static final float BADGES_Y = 250.0f;
    public static final float BADGE_W = 40.0f;
    public static final float BADGE_H = 40.0f;
    
    private static ArrayList<ModBadge> modBadges;
    private static ArrayList<PostInitializeSubscriber> postInitializeSubscribers;
    private static ArrayList<RenderSubscriber> renderSubscribers;
    private static ArrayList<PostRenderSubscriber> postRenderSubscribers;
    private static ArrayList<PreUpdateSubscriber> preUpdateSubscribers;
    private static ArrayList<PostUpdateSubscriber> postUpdateSubscribers;  
    
    public static DevConsole console;
    public static boolean modSettingsUp = false;
    
    // initialize -
    public static void initialize() {
        logger.info("========================= BASEMOD INIT =========================");
        logger.info("isModded: " + Settings.isModded);
        
        modBadges = new ArrayList<ModBadge>();        
        postInitializeSubscribers = new ArrayList<PostInitializeSubscriber>();
        renderSubscribers = new ArrayList<RenderSubscriber>();
        postRenderSubscribers = new ArrayList<PostRenderSubscriber>();
        preUpdateSubscribers = new ArrayList<PreUpdateSubscriber>();
        postUpdateSubscribers = new ArrayList<PostUpdateSubscriber>();
           
        console = new DevConsole();
        
        logger.info("================================================================");
    }
    
    //
    // Mod badges
    //
    public static void registerModBadge(Texture t, String name, String author, String desc) {
        int modBadgeCount = modBadges.size();
        int col = (modBadgeCount%BADGES_PER_ROW);
        int row = (modBadgeCount/BADGES_PER_ROW);
        float x = (BADGES_X*Settings.scale) + (col*BADGE_W*Settings.scale);
        float y = (BADGES_Y*Settings.scale) - (row*BADGE_H*Settings.scale);
        
        ModBadge badge = new ModBadge(t, x, y, name, author, desc);
        modBadges.add(badge);
    }
    
    //
    // Publishers
    //
    
    // publishPostInitialize -
    public static void publishPostInitialize() {
        // BaseMod post initialize handling
        Texture badgeTexture = new Texture(Gdx.files.internal("img/badge.png"));
        registerModBadge(badgeTexture, "BaseMod", "t-larson", "v1.1.1 NL Provides hooks and a console");
        
        // Publish
        for (PostInitializeSubscriber sub : postInitializeSubscribers) {
            sub.receivePostInitialize();
        }
    }
    
    // publishRender -
    public static void publishRender(SpriteBatch sb) {
        for (RenderSubscriber sub : renderSubscribers) {
            sub.receiveRender(sb);
        }
    }
    
    // publishPostRender -
    public static void publishPostRender(SpriteBatch sb) {
        for (PostRenderSubscriber sub : postRenderSubscribers) {
            sub.receivePostRender(sb);
        }
    }
    
    // publishPreUpdate -
    public static void publishPreUpdate() {
        for (PreUpdateSubscriber sub : preUpdateSubscribers) {
            sub.receivePreUpdate();
        }
    }
    
    // publishPostUpdate -
    public static void publishPostUpdate() {
        for (PostUpdateSubscriber sub : postUpdateSubscribers) {
            sub.receivePostUpdate();
        }
    }
    
    //
    // Subsciption handlers
    //
    
    // subscribeToPostInitialize -
    public static void subscribeToPostInitialize(PostInitializeSubscriber sub) {
        postInitializeSubscribers.add(sub);
    }
    
    // unsubscribeFromPostRender -
    public static void unsubscribeFromPostInitialize(PostInitializeSubscriber sub) {
        postInitializeSubscribers.remove(sub);
    }
    
    // subscribeToRender -
    public static void subscribeToRender(RenderSubscriber sub) {
        renderSubscribers.add(sub);
    }
    
    // unsubscribeFromRender -
    public static void unsubscribeFromRender(RenderSubscriber sub) {
        renderSubscribers.remove(sub);
    }
    
    // subscribeToPostRender -
    public static void subscribeToPostRender(PostRenderSubscriber sub) {
        postRenderSubscribers.add(sub);
    }
    
    // unsubscribeFromPostRender -
    public static void unsubscribeFromPostRender(PostRenderSubscriber sub) {
        postRenderSubscribers.remove(sub);
    }
    
    // subscribeToPreUpdate -
    public static void subscribeToPreUpdate(PreUpdateSubscriber sub) {
        preUpdateSubscribers.add(sub);
    }
    
    // unsubscribeFromPreUpdate -
    public static void unsubscribeFromPreUpdate(PreUpdateSubscriber sub) {
        preUpdateSubscribers.remove(sub);
    }
    
    // subscribeToPostUpdate -
    public static void subscribeToPostUpdate(PostUpdateSubscriber sub) {
        postUpdateSubscribers.add(sub);
    }
    
    // unsubscribeFromUpdate -
    public static void unsubscribeFromPostUpdate(PostUpdateSubscriber sub) {
        postUpdateSubscribers.remove(sub);
    }
}