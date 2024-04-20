package mods.thecomputerizer.theimpossiblelibrary.api.client.input;

import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI.Action;
import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI.AlphaNum;
import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI.Function;
import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI.Modifier;
import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI.NumberPad;
import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI.Symbol;

public interface KeyHelperAPI<K> {

    int applyModifier(int keyCode, Modifier modifier);

    default KeyAPI<K> create(String id, String category, Action action) {
        return create(id,category,getKeyCode(action));
    }

    default KeyAPI<K> create(String id, String category, AlphaNum actionKey) {
        return create(id,category,getKeyCode(actionKey));
    }

    default KeyAPI<K> create(String id, String category, Function fnKey) {
        return create(id,category,getKeyCode(fnKey));
    }

    default KeyAPI<K> create(String id, String category, NumberPad numPadKey) {
        return create(id,category,getKeyCode(numPadKey));
    }

    default KeyAPI<K> create(String id, String category, Symbol symbolKey) {
        return create(id,category,getKeyCode(symbolKey));
    }

    KeyAPI<K> create(String id, String category, int keyCode);
    int getKeyCode(Action actionKey);
    int getKeyCode(AlphaNum alphaNumKey);
    int getKeyCode(Function fnKey);
    int getKeyCode(Modifier modKey);
    int getKeyCode(NumberPad numPadKey);
    int getKeyCode(Symbol symbolKey);

    default KeyAPI<K> register(String id, String category, int keyCode) {
        KeyAPI<K> key = create(id,category,keyCode);
        register(key);
        return key;
    }

    void register(KeyAPI<K> key);
}