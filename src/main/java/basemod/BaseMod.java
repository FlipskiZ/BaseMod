package basemod;

import basemod.interfaces.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import java.util.ArrayList;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class BaseMod {
    private static final Logger logger = LogManager.getLogger(BaseMod.class.getName());
    
    public static DevConsole console;
    
    private static ArrayList<PostInitializeSubscriber> postInitializeSubscribers;
    private static ArrayList<PostRenderSubscriber> postRenderSubscribers;
    private static ArrayList<PreUpdateSubscriber> preUpdateSubscribers;
    private static ArrayList<PostUpdateSubscriber> postUpdateSubscribers;  
    
    // initialize -
    public static void initialize() {
        logger.info("========================= BASEMOD INIT =========================");
        logger.info("isModded: " + Settings.isModded);
        
        postInitializeSubscribers = new ArrayList<PostInitializeSubscriber>();
        postRenderSubscribers = new ArrayList<PostRenderSubscriber>();
        preUpdateSubscribers = new ArrayList<PreUpdateSubscriber>();
        postUpdateSubscribers = new ArrayList<PostUpdateSubscriber>();
           
        console = new DevConsole();
        
        logger.info("================================================================");
    }
    
    //
    // Publishers
    //
    
    // publishPostInitialize -
    public static void publishPostInitialize() {
        for (PostInitializeSubscriber sub : postInitializeSubscribers) {
            sub.receivePostInitialize();
        }
    }
    
    // publishPostRender -
    public static void publishPostRender() {
        for (PostRenderSubscriber sub : postRenderSubscribers) {
            sub.receivePostRender();
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