package mods.thecomputerizer.theimpossiblelibrary.api.registry;

/**
 * Wrapper for registries
 */
public interface RegistryWrapper<R,V> {

    /**
     * Get the wrapped registry
     */
    R getRegistry();

    /**
     * Get a key from the wrapped registry using the input value. Returns default key of the registry if one is set
     */
    <K> K getKey(V val);

    /**
     * Get a key from the wrapped registry using the input value. Returns null if the key is not found
     */
    <K> K getKeyNullable(V val);

    /**
     * Get a value from the wrapped registry using the input key. Returns default value of the registry if one is set
     */
    <K> V getValue(K key);

    /**
     * Get a value from the wrapped registry using the input key. Returns null if the value is not found
     */
    <K> V getValueNullable(K key);

    /**
     * Checks if the wrapped registry has the input key
     */
    <K> boolean hasKey(K key);

    /**
     * Checks if the wrapped registry has the input key
     */
    boolean hasValue(V val);
}
