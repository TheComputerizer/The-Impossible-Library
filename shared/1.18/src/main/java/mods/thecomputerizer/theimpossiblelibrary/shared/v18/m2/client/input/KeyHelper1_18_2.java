package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.client.input;

import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI.Action;
import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI.AlphaNum;
import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI.FNKeys;
import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI.Modifier;
import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI.NumberPad;
import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI.Symbol;
import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyHelperAPI;
import net.minecraft.client.KeyMapping;

import static org.lwjgl.glfw.GLFW.*;

public class KeyHelper1_18_2 implements KeyHelperAPI {
    
    @Override public KeyAPI<?> create(String id, String category, int keyCode) {
        return new Key1_18_2(new KeyMapping(id,keyCode,category));
    }
    
    @Override public int getKeyCode(Action actionKey) {
        return switch(actionKey) {
            case BACKSPACE -> GLFW_KEY_BACKSLASH;
            case CAPS_LOCK -> GLFW_KEY_CAPS_LOCK;
            case DELETE -> GLFW_KEY_DELETE;
            case DOWN -> GLFW_KEY_DOWN;
            case END -> GLFW_KEY_END;
            case ENTER -> GLFW_KEY_ENTER;
            case HOME -> GLFW_KEY_HOME;
            case INSERT -> GLFW_KEY_INSERT;
            case LAST -> GLFW_KEY_LAST;
            case LEFT -> GLFW_KEY_LEFT;
            case LEFT_ALT -> GLFW_KEY_LEFT_ALT;
            case LEFT_CTRL -> GLFW_KEY_LEFT_CONTROL;
            case LEFT_SHIFT -> GLFW_KEY_LEFT_SHIFT;
            case LEFT_SUPER -> GLFW_KEY_LEFT_SUPER;
            case MENU -> GLFW_KEY_MENU;
            case NUM_LOCK -> GLFW_KEY_NUM_LOCK;
            case PAGE_DOWN -> GLFW_KEY_PAGE_DOWN;
            case PAGE_UP -> GLFW_KEY_PAGE_UP;
            case PAUSE -> GLFW_KEY_PAUSE;
            case PRINT_SCREEN -> GLFW_KEY_PRINT_SCREEN;
            case RIGHT -> GLFW_KEY_RIGHT;
            case RIGHT_ALT -> GLFW_KEY_RIGHT_ALT;
            case RIGHT_CTRL -> GLFW_KEY_RIGHT_CONTROL;
            case RIGHT_SHIFT -> GLFW_KEY_RIGHT_SHIFT;
            case RIGHT_SUPER -> GLFW_KEY_RIGHT_SUPER;
            case SCROLL_LOCK -> GLFW_KEY_SCROLL_LOCK;
            case TAB -> GLFW_KEY_TAB;
            case UP -> GLFW_KEY_UP;
        };
    }

    @Override public int getKeyCode(AlphaNum alphaNumKey) {
        return switch(alphaNumKey) {
            case N0 -> GLFW_KEY_0;
            case N1 -> GLFW_KEY_1;
            case N2 -> GLFW_KEY_2;
            case N3 -> GLFW_KEY_3;
            case N4 -> GLFW_KEY_4;
            case N5 -> GLFW_KEY_5;
            case N6 -> GLFW_KEY_6;
            case N7 -> GLFW_KEY_7;
            case N8 -> GLFW_KEY_8;
            case N9 -> GLFW_KEY_9;
            case A -> GLFW_KEY_A;
            case B -> GLFW_KEY_B;
            case C -> GLFW_KEY_C;
            case D -> GLFW_KEY_D;
            case E -> GLFW_KEY_E;
            case F -> GLFW_KEY_F;
            case G -> GLFW_KEY_G;
            case H -> GLFW_KEY_H;
            case I -> GLFW_KEY_I;
            case J -> GLFW_KEY_J;
            case K -> GLFW_KEY_K;
            case L -> GLFW_KEY_L;
            case M -> GLFW_KEY_M;
            case N -> GLFW_KEY_N;
            case O -> GLFW_KEY_O;
            case P -> GLFW_KEY_P;
            case Q -> GLFW_KEY_Q;
            case R -> GLFW_KEY_R;
            case S -> GLFW_KEY_S;
            case T -> GLFW_KEY_T;
            case U -> GLFW_KEY_U;
            case V -> GLFW_KEY_V;
            case W -> GLFW_KEY_W;
            case X -> GLFW_KEY_X;
            case Y -> GLFW_KEY_Y;
            case Z -> GLFW_KEY_Z;
        };
    }

    @Override public int getKeyCode(FNKeys fnKey) {
        return switch(fnKey) {
            case F1 -> GLFW_KEY_F1;
            case F2 -> GLFW_KEY_F2;
            case F3 -> GLFW_KEY_F3;
            case F4 -> GLFW_KEY_F4;
            case F5 -> GLFW_KEY_F5;
            case F6 -> GLFW_KEY_F6;
            case F7 -> GLFW_KEY_F7;
            case F8 -> GLFW_KEY_F8;
            case F9 -> GLFW_KEY_F9;
            case F10 -> GLFW_KEY_F10;
            case F11 -> GLFW_KEY_F11;
            case F12 -> GLFW_KEY_F12;
            case F13 -> GLFW_KEY_F13;
            case F14 -> GLFW_KEY_F14;
            case F15 -> GLFW_KEY_F15;
            case F16 -> GLFW_KEY_F16;
            case F17 -> GLFW_KEY_F17;
            case F18 -> GLFW_KEY_F18;
            case F19 -> GLFW_KEY_F19;
            case F20 -> GLFW_KEY_F20;
            case F21 -> GLFW_KEY_F21;
            case F22 -> GLFW_KEY_F22;
            case F23 -> GLFW_KEY_F23;
            case F24 -> GLFW_KEY_F24;
            case F25 -> GLFW_KEY_F25;
        };
    }

    @Override public int getKeyCode(Modifier modKey) {
        return switch(modKey) {
            case ALT -> GLFW_MOD_ALT;
            case CTRL -> GLFW_MOD_CONTROL;
            case SHIFT -> GLFW_MOD_SHIFT;
            default -> GLFW_KEY_UNKNOWN;
        };
    }

    @Override public int getKeyCode(NumberPad numPadKey) {
        return switch(numPadKey) {
            case N0 -> GLFW_KEY_KP_0;
            case N1 -> GLFW_KEY_KP_1;
            case N2 -> GLFW_KEY_KP_2;
            case N3 -> GLFW_KEY_KP_3;
            case N4 -> GLFW_KEY_KP_4;
            case N5 -> GLFW_KEY_KP_5;
            case N6 -> GLFW_KEY_KP_6;
            case N7 -> GLFW_KEY_KP_7;
            case N8 -> GLFW_KEY_KP_8;
            case N9 -> GLFW_KEY_KP_9;
            case ADD -> GLFW_KEY_KP_ADD;
            case DECIMAL -> GLFW_KEY_KP_DECIMAL;
            case DIVIDE -> GLFW_KEY_KP_DIVIDE;
            case ENTER -> GLFW_KEY_KP_ENTER;
            case EQUAL -> GLFW_KEY_KP_EQUAL;
            case MULTIPLY -> GLFW_KEY_KP_MULTIPLY;
            case SUBTRACT -> GLFW_KEY_KP_SUBTRACT;
        };
    }

    @Override public int getKeyCode(Symbol symbolKey) {
        return switch(symbolKey) {
            case APOSTROPHE -> GLFW_KEY_APOSTROPHE;
            case BACKSLASH -> GLFW_KEY_BACKSLASH;
            case COMMA -> GLFW_KEY_COMMA;
            case EQUAL -> GLFW_KEY_EQUAL;
            case GRAVE -> GLFW_KEY_GRAVE_ACCENT;
            case LEFT_BRACKET -> GLFW_KEY_LEFT_BRACKET;
            case MINUS -> GLFW_KEY_MINUS;
            case PERIOD -> GLFW_KEY_PERIOD;
            case RIGHT_BRACKET -> GLFW_KEY_RIGHT_BRACKET;
            case SEMICOLON -> GLFW_KEY_SEMICOLON;
            case SLASH -> GLFW_KEY_SLASH;
            case SPACE -> GLFW_KEY_SPACE;
            case WORLD_1 -> GLFW_KEY_WORLD_1;
            case WORLD_2 -> GLFW_KEY_WORLD_2;
        };
    }

    @Override public int applyModifier(int keyCode, Modifier modifier) { //TODO
        return keyCode;
    }
}