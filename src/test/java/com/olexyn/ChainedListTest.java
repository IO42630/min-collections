package com.olexyn;

import com.olexyn.min.entries.Pair;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class ChainedListTest {


    private static final List<Pair<String, String>> LIST = new ArrayList<>();

    @BeforeClass
    public static void setUp() {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            LIST.add(new Pair<>("key" + i, "value" + i));
        }
    }

    @Test
    public void testApp()
    {
        Map<String, String> hashMap = new HashMap<>();
        LIST.forEach(pair -> hashMap.put(pair.getA(), pair.getB()));


        ChainedList<String, String> chainedList = new ChainedList<>();
        LIST.forEach(pair -> chainedList.add(new ChainLink<>()));


        assertTrue( true );
    }


}
