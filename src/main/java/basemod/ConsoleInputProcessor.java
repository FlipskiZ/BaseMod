package basemod;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import java.awt.event.KeyEvent;

public class ConsoleInputProcessor implements InputProcessor {
    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Keys.ENTER: {
                DevConsole.execute();
                return true;
            }
            case Keys.BACKSPACE: {
                DevConsole.backspaceWait = 0;
                DevConsole.backspace = true;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Keys.BACKSPACE: {
                DevConsole.backspace = false;
                DevConsole.backspaceWait = 0;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        Character.UnicodeBlock block = Character.UnicodeBlock.of(character);
        
        boolean badBlock = (block == null || block == Character.UnicodeBlock.SPECIALS);
        boolean isToggle = (character == DevConsole.toggleKey);
        boolean isControl = (Character.isISOControl(character) || character == KeyEvent.CHAR_UNDEFINED);
        if (badBlock || isToggle || isControl) {
            return false; 
        }
        
        DevConsole.currentText += character;
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}