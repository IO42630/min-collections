package com.olexyn;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.olexyn.min.entries.AEntry;
import com.olexyn.min.entries.Pair;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class TimeCubeTest {

	private static final List<AEntry<Instant, String>> MANY_KEYS = new ArrayList<>();

	@BeforeClass
	public static void setUp() {

		for (int i = 0; i < 480000; i++) {
			MANY_KEYS.add(new AEntry<>(Instant.now().minusSeconds(i), "value " + i));
		}

	}

	@Test
	public void rangeTest() {

		var timeCube = new TimeCube();
		timeCube.put(timeCube.getStart(), "START");
		assertEquals(timeCube.get(timeCube.getStart()).getValue(), "START");

		var now = Instant.now();
		timeCube.put(now, "NOW");
		assertEquals(timeCube.get(now).getValue(), "NOW");

		var inFiveYears = Instant.now().plus(Duration.ofDays(365 * 5));
		timeCube.put(inFiveYears, "inFiveYears");
		assertEquals(timeCube.get(inFiveYears).getValue(), "inFiveYears");

		var inTenYears = Instant.now().plus(Duration.ofDays(365 * 10));
		timeCube.put(inTenYears, "inTenYears");
		assertEquals(timeCube.get(inTenYears).getValue(), "inTenYears");
	}

	@Test
	public void putPerformanceTest() {
        Instant start;
        Instant end;
		int putLoops = 10;

        start = Instant.now();
		var timeCube = new TimeCube();
		for (var i = 0; i < putLoops; i++) {
			for (var pair : MANY_KEYS) {
                timeCube.put(pair.getKey(), pair.getValue());
			}
		}
		end = Instant.now();
		System.out.println("TimeCube x" + putLoops + " : " + (end.toEpochMilli() - start.toEpochMilli()) + " ms");

		start = Instant.now();
		var treeMap = new TreeMap<Instant, String>();
		for (var i = 0; i < putLoops; i++) {
			for (var pair : MANY_KEYS) {
                treeMap.put(pair.getKey(), pair.getValue());
			}
		}
		end = Instant.now();
		System.out.println("TreeMap x" + putLoops + " : " + (end.toEpochMilli() - start.toEpochMilli()) + " ms");

	}

	@Test
	public void getPerformanceTest() {
        Instant start;
        Instant end;
		int getLoops = 10;

		// Prepare TimeCube
		var timeCube = new TimeCube();
		for (var pair : MANY_KEYS) {
			timeCube.put(pair.getKey(), pair.getValue());
		}
		// Prepare TreeMap
		var treeMap = new TreeMap<Instant, String>();

		for (var pair : MANY_KEYS) {
			treeMap.put(pair.getKey(), pair.getValue());
		}
		// Test TimeCube X10
		start = Instant.now();
		for (var i = 0; i < getLoops; i++) {
			for (var pair : MANY_KEYS) {
				assertEquals(timeCube.get(pair.getKey()).getValue(), pair.getValue());
			}
		}
		end = Instant.now();
		System.out.println("TimeCube get x" + getLoops + " : " + (end.toEpochMilli() - start.toEpochMilli()) + " ms");

		// Test TreeMap X10
		start = Instant.now();
		for (var i = 0; i < getLoops; i++) {
			for (var pair : MANY_KEYS) {
				assertEquals(treeMap.get(pair.getKey()), pair.getValue());
			}
		}
		end = Instant.now();
		System.out.println("TreeMap get x" + getLoops + " : " + (end.toEpochMilli() - start.toEpochMilli()) + " ms");

	}

}
