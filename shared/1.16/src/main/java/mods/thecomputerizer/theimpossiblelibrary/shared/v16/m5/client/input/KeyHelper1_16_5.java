package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.client.input;

import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI.Action;
import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI.AlphaNum;
import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI.FNKeys;
import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI.Modifier;
import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI.NumberPad;
import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI.Symbol;
import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyHelperAPI;
import net.minecraft.client.settings.KeyBinding;

import static org.lwjgl.glfw.GLFW.*;

public abstract class KeyHelper1_16_5 implements KeyHelperAPI {
    
    @Override public KeyAPI<?> create(String id, String category, int keyCode) {
        return new Key1_16_5(new KeyBinding(id,keyCode,category));
    }
    
    @Override public int getKeyCode(Action actionKey) {
        switch(actionKey) {
            case BACKSPACE: return GLFW_KEY_BACKSLASH;
            case CAPS_LOCK: return GLFW_KEY_CAPS_LOCK;
            case DELETE: return GLFW_KEY_DELETE;
            case DOWN: return GLFW_KEY_DOWN;
            case END: return GLFW_KEY_END;
            case ENTER: return GLFW_KEY_ENTER;
            case HOME: return GLFW_KEY_HOME;
            case INSERT: return GLFW_KEY_INSERT;
            case LAST: return GLFW_KEY_LAST;
            case LEFT: return GLFW_KEY_LEFT;
            case LEFT_ALT: return GLFW_KEY_LEFT_ALT;
            case LEFT_CTRL: return GLFW_KEY_LEFT_CONTROL;
            case LEFT_SHIFT: return GLFW_KEY_LEFT_SHIFT;
            case LEFT_SUPER: return GLFW_KEY_LEFT_SUPER;
            case MENU: return GLFW_KEY_MENU;
            case NUM_LOCK: return GLFW_KEY_NUM_LOCK;
            case PAGE_DOWN: return GLFW_KEY_PAGE_DOWN;
            case PAGE_UP: return GLFW_KEY_PAGE_UP;
            case PAUSE: return GLFW_KEY_PAUSE;
            case PRINT_SCREEN: return GLFW_KEY_PRINT_SCREEN;
            case RIGHT: return GLFW_KEY_RIGHT;
            case RIGHT_ALT: return GLFW_KEY_RIGHT_ALT;
            case RIGHT_CTRL: return GLFW_KEY_RIGHT_CONTROL;
            case RIGHT_SHIFT: return GLFW_KEY_RIGHT_SHIFT;
            case RIGHT_SUPER: return GLFW_KEY_RIGHT_SUPER;
            case SCROLL_LOCK: return GLFW_KEY_SCROLL_LOCK;
            case TAB: return GLFW_KEY_TAB;
            case UP: return GLFW_KEY_UP;
            default: return GLFW_KEY_UNKNOWN;
        }
    }

    @Override public int getKeyCode(AlphaNum alphaNumKey) {
        switch(alphaNumKey) {
            case N0: return GLFW_KEY_0;
            case N1: return GLFW_KEY_1;
            case N2: return GLFW_KEY_2;
            case N3: return GLFW_KEY_3;
            case N4: return GLFW_KEY_4;
            case N5: return GLFW_KEY_5;
            case N6: return GLFW_KEY_6;
            case N7: return GLFW_KEY_7;
            case N8: return GLFW_KEY_8;
            case N9: return GLFW_KEY_9;
            case A: return GLFW_KEY_A;
            case B: return GLFW_KEY_B;
            case C: return GLFW_KEY_C;
            case D: return GLFW_KEY_D;
            case E: return GLFW_KEY_E;
            case F: return GLFW_KEY_F;
            case G: return GLFW_KEY_G;
            case H: return GLFW_KEY_H;
            case I: return GLFW_KEY_I;
            case J: return GLFW_KEY_J;
            case K: return GLFW_KEY_K;
            case L: return GLFW_KEY_L;
            case M: return GLFW_KEY_M;
            case N: return GLFW_KEY_N;
            case O: return GLFW_KEY_O;
            case P: return GLFW_KEY_P;
            case Q: return GLFW_KEY_Q;
            case R: return GLFW_KEY_R;
            case S: return GLFW_KEY_S;
            case T: return GLFW_KEY_T;
            case U: return GLFW_KEY_U;
            case V: return GLFW_KEY_V;
            case W: return GLFW_KEY_W;
            case X: return GLFW_KEY_X;
            case Y: return GLFW_KEY_Y;
            case Z: return GLFW_KEY_Z;
            default: return GLFW_KEY_UNKNOWN;
        }
    }

    @Override public int getKeyCode(FNKeys fnKey) {
        switch(fnKey) {
            case F1: return GLFW_KEY_F1;
            case F2: return GLFW_KEY_F2;
            case F3: return GLFW_KEY_F3;
            case F4: return GLFW_KEY_F4;
            case F5: return GLFW_KEY_F5;
            case F6: return GLFW_KEY_F6;
            case F7: return GLFW_KEY_F7;
            case F8: return GLFW_KEY_F8;
            case F9: return GLFW_KEY_F9;
            case F10: return GLFW_KEY_F10;
            case F11: return GLFW_KEY_F11;
            case F12: return GLFW_KEY_F12;
            case F13: return GLFW_KEY_F13;
            case F14: return GLFW_KEY_F14;
            case F15: return GLFW_KEY_F15;
            case F16: return GLFW_KEY_F16;
            case F17: return GLFW_KEY_F17;
            case F18: return GLFW_KEY_F18;
            case F19: return GLFW_KEY_F19;
            case F20: return GLFW_KEY_F20;
            case F21: return GLFW_KEY_F21;
            case F22: return GLFW_KEY_F22;
            case F23: return GLFW_KEY_F23;
            case F24: return GLFW_KEY_F24;
            case F25: return GLFW_KEY_F25;
            default: return GLFW_KEY_UNKNOWN;
        }
    }

    @Override public int getKeyCode(Modifier modKey) {
        switch(modKey) {
            case ALT: return GLFW_MOD_ALT;
            case CTRL: return GLFW_MOD_CONTROL;
            case SHIFT: return GLFW_MOD_SHIFT;
            default: return GLFW_KEY_UNKNOWN;
        }
    }

    @Override public int getKeyCode(NumberPad numPadKey) {
        switch(numPadKey) {
            case N0: return GLFW_KEY_KP_0;
            case N1: return GLFW_KEY_KP_1;
            case N2: return GLFW_KEY_KP_2;
            case N3: return GLFW_KEY_KP_3;
            case N4: return GLFW_KEY_KP_4;
            case N5: return GLFW_KEY_KP_5;
            case N6: return GLFW_KEY_KP_6;
            case N7: return GLFW_KEY_KP_7;
            case N8: return GLFW_KEY_KP_8;
            case N9: return GLFW_KEY_KP_9;
            case ADD: return GLFW_KEY_KP_ADD;
            case DECIMAL: return GLFW_KEY_KP_DECIMAL;
            case DIVIDE: return GLFW_KEY_KP_DIVIDE;
            case ENTER: return GLFW_KEY_KP_ENTER;
            case EQUAL: return GLFW_KEY_KP_EQUAL;
            case MULTIPLY: return GLFW_KEY_KP_MULTIPLY;
            case SUBTRACT: return GLFW_KEY_KP_SUBTRACT;
            default: return GLFW_KEY_UNKNOWN;
        }
    }

    @Override public int getKeyCode(Symbol symbolKey) {
        switch(symbolKey) {
            case APOSTROPHE: return GLFW_KEY_APOSTROPHE;
            case BACKSLASH: return GLFW_KEY_BACKSLASH;
            case COMMA: return GLFW_KEY_COMMA;
            case EQUAL: return GLFW_KEY_EQUAL;
            case GRAVE: return GLFW_KEY_GRAVE_ACCENT;
            case LEFT_BRACKET: return GLFW_KEY_LEFT_BRACKET;
            case MINUS: return GLFW_KEY_MINUS;
            case PERIOD: return GLFW_KEY_PERIOD;
            case RIGHT_BRACKET: return GLFW_KEY_RIGHT_BRACKET;
            case SEMICOLON: return GLFW_KEY_SEMICOLON;
            case SLASH: return GLFW_KEY_SLASH;
            case SPACE: return GLFW_KEY_SPACE;
            case WORLD_1: return GLFW_KEY_WORLD_1;
            case WORLD_2: return GLFW_KEY_WORLD_2;
            default: return GLFW_KEY_UNKNOWN;
        }
    }

    @Override public int applyModifier(int keyCode, Modifier modifier) { //TODO
        return keyCode;
    }
}