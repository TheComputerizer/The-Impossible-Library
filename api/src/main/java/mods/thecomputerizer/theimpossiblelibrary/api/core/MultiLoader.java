package mods.thecomputerizer.theimpossiblelibrary.api.core;

public class MultiLoader {

    public static void test(Class<?> clazz) {
        if(clazz.isAnnotationPresent(MultiversionMod.class)) {
            MultiversionMod mod = clazz.getAnnotation(MultiversionMod.class);
        }
    }
}