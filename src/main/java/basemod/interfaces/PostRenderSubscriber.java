package basemod.interfaces;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface PostRenderSubscriber {
    public void receivePostRender(SpriteBatch sb);
}