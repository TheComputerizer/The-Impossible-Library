package mods.thecomputerizer.theimpossiblelibrary.api.client.input;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.util.AbstractWrapped;

@Getter
public abstract class KeyAPI<K> extends AbstractWrapped<K> {


    protected KeyAPI(K keybind) {
        super(keybind);
    }

    public abstract boolean isDown();

    public enum Action {
        BACKSPACE, CAPS_LOCK, DELETE, DOWN, END,
        ENTER, HOME, INSERT, LAST, LEFT,
        LEFT_ALT, LEFT_CTRL, LEFT_SHIFT, LEFT_SUPER, MENU,
        NUM_LOCK, PAGE_DOWN, PAGE_UP, PAUSE, PRINT_SCREEN,
        RIGHT, RIGHT_ALT, RIGHT_CTRL, RIGHT_SHIFT, RIGHT_SUPER,
        SCROLL_LOCK, TAB, UP
    }
    public enum AlphaNum {
        N0, N1, N2, N3, N4, N5, N6, N7, N8, N9,
        A, B, C, D, E, F, G, H, I, J,
        K, L, M, N, O, P, Q, R, S, T,
        U, V, W, X, Y, Z }

    public enum FNKeys {
        F1, F2, F3, F4, F5, F6, F7, F8, F9, F10,
        F11, F12, F13, F14, F15, F16, F17, F18, F19, F20,
        F21, F22, F23, F24, F25
    }

    public enum NumberPad {
        N0, N1, N2, N3, N4,
        N5, N6, N7, N8, N9,
        ADD, DECIMAL, DIVIDE, ENTER, EQUAL,
        MULTIPLY, SUBTRACT
    }

    public enum Modifier {
        ALT, CTRL, SHIFT, NONE
    }

    public enum Symbol {
        APOSTROPHE, BACKSLASH, COMMA, EQUAL, GRAVE,
        LEFT_BRACKET, MINUS, PERIOD, RIGHT_BRACKET, SEMICOLON,
        SLASH, SPACE, WORLD_1, WORLD_2
    }
}