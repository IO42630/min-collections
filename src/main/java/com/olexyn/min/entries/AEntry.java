package com.olexyn.min.entries;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AEntry<K,V> implements Map.Entry<K,V> {

    private K key;
    private V value;

    @Override
    public V setValue(V value) {
        this.value = value;
        return value;
    }

}
