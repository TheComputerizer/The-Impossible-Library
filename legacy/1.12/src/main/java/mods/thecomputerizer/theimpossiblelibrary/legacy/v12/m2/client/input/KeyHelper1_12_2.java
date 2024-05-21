package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.input;

import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI.FNKeys;
import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI.Modifier;
import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyHelperAPI;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import static org.lwjgl.input.Keyboard.*;

public class KeyHelper1_12_2 implements KeyHelperAPI<KeyBinding> {

    @Override
    public KeyAPI<KeyBinding> create(String id, String category, int keyCode) {
        return new Key1_12_2(new KeyBinding(id,keyCode,category));
    }

    @Override
    public int getKeyCode(KeyAPI.Action actionKey) {
        switch(actionKey) {
            case BACKSPACE: return KEY_BACKSLASH;
            case CAPS_LOCK: return KEY_CAPITAL;
            case DELETE: return KEY_DELETE;
            case DOWN: return KEY_DOWN;
            case END: return KEY_END;
            case ENTER: return KEY_RETURN;
            case HOME: return KEY_HOME;
            case INSERT: return KEY_INSERT;
            //case LAST: return KEY_LAST;
            case LEFT: return KEY_LEFT;
            case LEFT_ALT: return KEY_LMENU;
            case LEFT_CTRL: return KEY_LCONTROL;
            case LEFT_SHIFT: return KEY_LSHIFT;
            //case LEFT_SUPER: return KEY_LSUPER; TODO
            //case MENU: return KEY_MENU; TODO
            case NUM_LOCK: return KEY_NUMLOCK;
            case PAGE_DOWN: return KEY_NEXT;
            case PAGE_UP: return KEY_PRIOR;
            case PAUSE: return KEY_PAUSE;
            //case PRINT_SCREEN: return KEY_PRINT_SCREEN; TODO
            case RIGHT: return KEY_RIGHT;
            case RIGHT_ALT: return KEY_RMENU;
            case RIGHT_CTRL: return KEY_RCONTROL;
            case RIGHT_SHIFT: return KEY_RSHIFT;
            //case RIGHT_SUPER: return KEY_RIGHT_SUPER; TODO
            case SCROLL_LOCK: return KEY_SCROLL;
            case TAB: return KEY_TAB;
            case UP: return KEY_UP;
            default: return KEY_NONE;
        }
    }

    @Override
    public int getKeyCode(KeyAPI.AlphaNum alphaNumKey) {
        switch(alphaNumKey) {
            case N0: return KEY_0;
            case N1: return KEY_1;
            case N2: return KEY_2;
            case N3: return KEY_3;
            case N4: return KEY_4;
            case N5: return KEY_5;
            case N6: return KEY_6;
            case N7: return KEY_7;
            case N8: return KEY_8;
            case N9: return KEY_9;
            case A: return KEY_A;
            case B: return KEY_B;
            case C: return KEY_C;
            case D: return KEY_D;
            case E: return KEY_E;
            case F: return KEY_F;
            case G: return KEY_G;
            case H: return KEY_H;
            case I: return KEY_I;
            case J: return KEY_J;
            case K: return KEY_K;
            case L: return KEY_L;
            case M: return KEY_M;
            case N: return KEY_N;
            case O: return KEY_O;
            case P: return KEY_P;
            case Q: return KEY_Q;
            case R: return KEY_R;
            case S: return KEY_S;
            case T: return KEY_T;
            case U: return KEY_U;
            case V: return KEY_V;
            case W: return KEY_W;
            case X: return KEY_X;
            case Y: return KEY_Y;
            case Z: return KEY_Z;
            default: return KEY_NONE;
        }
    }

    @Override
    public int getKeyCode(FNKeys fnKey) {
        switch(fnKey) {
            case F1: return KEY_F1;
            case F2: return KEY_F2;
            case F3: return KEY_F3;
            case F4: return KEY_F4;
            case F5: return KEY_F5;
            case F6: return KEY_F6;
            case F7: return KEY_F7;
            case F8: return KEY_F8;
            case F9: return KEY_F9;
            case F10: return KEY_F10;
            case F11: return KEY_F11;
            case F12: return KEY_F12;
            case F13: return KEY_F13;
            case F14: return KEY_F14;
            case F15: return KEY_F15;
            case F16: return KEY_F16;
            case F17: return KEY_F17;
            case F18: return KEY_F18;
            case F19: return KEY_F19;
            default: return KEY_NONE;
        }
    }

    @Override
    public int getKeyCode(Modifier modKey) {// TODO
        return KEY_NONE;
        /*
        switch(modKey) {
            case ALT: return KEY_MOD;
            case CTRL: return MOD_CONTROL;
            case SHIFT: return MOD_SHIFT;
            default: return KEY_NONE;
        }
         */
    }

    @Override
    public int getKeyCode(KeyAPI.NumberPad numPadKey) {
        switch(numPadKey) {
            case N0: return KEY_NUMPAD0;
            case N1: return KEY_NUMPAD1;
            case N2: return KEY_NUMPAD2;
            case N3: return KEY_NUMPAD3;
            case N4: return KEY_NUMPAD4;
            case N5: return KEY_NUMPAD5;
            case N6: return KEY_NUMPAD6;
            case N7: return KEY_NUMPAD7;
            case N8: return KEY_NUMPAD8;
            case N9: return KEY_NUMPAD9;
            case ADD: return KEY_ADD;
            case DECIMAL: return KEY_DECIMAL;
            case DIVIDE: return KEY_DIVIDE;
            case ENTER: return KEY_NUMPADENTER;
            case EQUAL: return KEY_EQUALS;
            case MULTIPLY: return KEY_MULTIPLY;
            case SUBTRACT: return KEY_SUBTRACT;
            default: return KEY_NONE;
        }
    }

    @Override
    public int getKeyCode(KeyAPI.Symbol symbolKey) {
        switch(symbolKey) {
            case APOSTROPHE: return KEY_APOSTROPHE;
            case BACKSLASH: return KEY_BACKSLASH;
            case COMMA: return KEY_COMMA;
            case EQUAL: return KEY_EQUALS;
            case GRAVE: return KEY_GRAVE;
            case LEFT_BRACKET: return KEY_LBRACKET;
            case MINUS: return KEY_MINUS;
            case PERIOD: return KEY_PERIOD;
            case RIGHT_BRACKET: return KEY_RBRACKET;
            case SEMICOLON: return KEY_SEMICOLON;
            case SLASH: return KEY_SLASH;
            case SPACE: return KEY_SPACE;
            //case WORLD_1: return KEY_WORLD_1; TODO
            //case WORLD_2: return KEY_WORLD_2; TODO
            default: return KEY_NONE;
        }
    }

    @Override
    public int applyModifier(int keyCode, Modifier modifier) { //TODO
        return keyCode;
    }

    @Override
    public void register(KeyAPI<KeyBinding> key) {
        ClientRegistry.registerKeyBinding(key.getKeybind());
    }
}
