package com.olexyn;

import lombok.Getter;
import lombok.Setter;
import org.checkerframework.checker.nullness.qual.Nullable;

@Getter
@Setter
public class ChainLink<K,V> {



    @Nullable
    private ChainLink<K,V> next;
    @Nullable
    private ChainLink<K,V> prev;

    private K key;
    private V value;



}
