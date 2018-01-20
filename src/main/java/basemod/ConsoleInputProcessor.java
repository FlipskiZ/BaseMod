package basemod;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

public class ConsoleInputProcessor implements InputProcessor {
    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Keys.ENTER: {
                DevConsole.execute();
                return true;
            }
            case Keys.BACKSPACE: {
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
        if (character == '`') return false; 
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