package basemod.interfaces;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface RenderSubscriber {
    public void receiveRender(SpriteBatch sb);
}