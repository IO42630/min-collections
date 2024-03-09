package com.olexyn;

import org.checkerframework.checker.nullness.qual.Nullable;

public abstract class AbstractChainedList<K,V> {

    private final ChainLink<K,V> head = new ChainLink<>();
    private ChainLink<K,V> tail = head;
    private long size = 0;


    public void add(ChainLink<K,V> link) {
            tail.setNext(link);
            link.setPrev(tail);
            tail = link;
            size++;

    }

    public @Nullable K getFirstKey() {
        if (head.getNext() != null) {
            return head.getNext().getKey();
        }
        return null;
    }

    public @Nullable K getLastKey() {
        return tail.getKey();
    }

    public @Nullable V getLast() {
            return tail.getValue();
    }



    public long size() {
        return size;
    }

    void clear() {
        head.setNext(null);
        tail = head;
        size = 0;
    }


    public @Nullable V get(K key) {
        var link = head;
        while (link.getNext() != null) {
            link = link.getNext();
            if (link.getKey() != null && link.getKey().equals(key)) {
                return link.getValue();
            }
        }
        return null;
    }

}
