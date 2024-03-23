package com.olexyn;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.olexyn.min.entries.Pair;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class TimeCubeTest {


    private static final List<Pair<String, String>> LIST = new ArrayList<>();

    @BeforeClass
    public static void setUp() {



    }

    @Test
    public void testApp()
    {

        var arrList = new TimeCube();

        List<Instant> keys = List.of(
            Instant.now(),
            Instant.now().plusSeconds(1)
        );

        for ( var instant : keys ) {
            int index = new Random().nextInt(Integer.MAX_VALUE);
            String value = "value " + index;
            arrList.put(index, new Pair<>(instant, value));
			assertEquals(arrList.get(index).getB(), value);
        }

    }


}
