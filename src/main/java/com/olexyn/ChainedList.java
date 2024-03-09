package com.olexyn;

import org.checkerframework.checker.nullness.qual.Nullable;

public class ChainedList<K,V> extends AbstractChainedList<K,V> {

    public @Nullable K getSameOrFirstBefore(K key) {
        return null;
    }

    public @Nullable K getSameOrFirstAfter(K key) {
        return null;
    }

    public @Nullable K higherKey(K key) {
        return null;
    }

    public @Nullable K lowerKey(K key) {
        return null;
    }
}
