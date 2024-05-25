package mods.thecomputerizer.theimpossiblelibrary.api.client.input;

import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI.Action;
import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI.AlphaNum;
import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI.FNKeys;
import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI.Modifier;
import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI.NumberPad;
import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI.Symbol;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Misc;

import javax.annotation.Nullable;
import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI.Action.DOWN;
import static mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI.Action.LEFT;
import static mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI.Action.RIGHT;
import static mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI.Action.UP;
import static mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI.AlphaNum.A;
import static mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI.AlphaNum.D;
import static mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI.AlphaNum.S;
import static mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI.AlphaNum.W;

@SuppressWarnings("unused")
public class KeyHelper {

    public static int applyModifier(int keyCode, Modifier modifier) {
        KeyHelperAPI<?> api = getAPI();
        return Objects.nonNull(api) ? api.applyModifier(keyCode,modifier) : -1;
    }

    public static KeyAPI<?> create(String id, String category, Action action) {
        KeyHelperAPI<?> api = getAPI();
        return Objects.nonNull(api) ? api.create(id,category,action) : null;
    }

    public static KeyAPI<?> create(String id, String category, AlphaNum actionKey) {
        KeyHelperAPI<?> api = getAPI();
        return Objects.nonNull(api) ? api.create(id,category,actionKey) : null;
    }

    public static KeyAPI<?> create(String id, String category, FNKeys fnKey) {
        KeyHelperAPI<?> api = getAPI();
        return Objects.nonNull(api) ? api.create(id,category,fnKey) : null;
    }

    public static KeyAPI<?> create(String id, String category, NumberPad numPadKey) {
        KeyHelperAPI<?> api = getAPI();
        return Objects.nonNull(api) ? api.create(id,category,numPadKey) : null;
    }

    public static KeyAPI<?> create(String id, String category, Symbol symbolKey) {
        KeyHelperAPI<?> api = getAPI();
        return Objects.nonNull(api) ? api.create(id,category,symbolKey) : null;
    }

    public static KeyAPI<?> create(String id, String category, int keyCode) {
        KeyHelperAPI<?> api = getAPI();
        return Objects.nonNull(api) ? api.create(id,category,keyCode) : null;
    }

    public static @Nullable KeyHelperAPI<?> getAPI() {
        return TILRef.getClientSubAPI(ClientAPI::getKeyHelper);
    }

    public static int getKeyCode(Action actionKey) {
        KeyHelperAPI<?> api = getAPI();
        return Objects.nonNull(api) ? api.getKeyCode(actionKey) : -1;
    }

    public static int getKeyCode(AlphaNum alphaNumKey) {
        KeyHelperAPI<?> api = getAPI();
        return Objects.nonNull(api) ? api.getKeyCode(alphaNumKey) : -1;
    }

    public static int getKeyCode(FNKeys fnKey) {
        KeyHelperAPI<?> api = getAPI();
        return Objects.nonNull(api) ? api.getKeyCode(fnKey) : -1;
    }

    public static int getKeyCode(Modifier modKey) {
        KeyHelperAPI<?> api = getAPI();
        return Objects.nonNull(api) ? api.getKeyCode(modKey) : -1;
    }

    public static int getKeyCode(NumberPad numPadKey) {
        KeyHelperAPI<?> api = getAPI();
        return Objects.nonNull(api) ? api.getKeyCode(numPadKey) : -1;
    }

    public static int getKeyCode(Symbol symbolKey) {
        KeyHelperAPI<?> api = getAPI();
        return Objects.nonNull(api) ? api.getKeyCode(symbolKey) : -1;
    }
    
    public static boolean isArrow(int keyCode) {
        return Misc.equalsAny(keyCode,getKeyCode(DOWN),getKeyCode(LEFT),getKeyCode(RIGHT),getKeyCode(UP));
    }
    
    public static boolean isArrowOrWASD(int keyCode) {
        return Misc.equalsAny(keyCode,getKeyCode(DOWN),getKeyCode(LEFT),getKeyCode(RIGHT),getKeyCode(UP),
                              getKeyCode(W),getKeyCode(A),getKeyCode(S),getKeyCode(D));
    }
    
    public static boolean isWASD(int keyCode) {
        return Misc.equalsAny(keyCode,getKeyCode(W),getKeyCode(A),getKeyCode(S),getKeyCode(D));
    }
    
    public static KeyAPI<?> register(String id, String category, int keyCode) {
        KeyHelperAPI<?> api = getAPI();
        if(Objects.isNull(api)) return null;
        return api.register(id,category,keyCode);
    }

    @SuppressWarnings("unchecked")
    public static <K> void register(KeyAPI<K> key) {
        KeyHelperAPI<?> api = getAPI();
        if(Objects.nonNull(api)) ((KeyHelperAPI<K>)api).register(key);
    }
}