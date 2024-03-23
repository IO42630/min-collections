package com.olexyn;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.olexyn.min.entries.AEntry;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for simple App.
 */
public class TimeCubeIntegrityTest {

	private static final List<AEntry<Instant, String>> MANY_KEYS = new ArrayList<>();

	@BeforeClass
	public static void setUp() {

		var last = Instant.parse("2020-01-01T00:00:00Z");

		for (int i = 0; i < 480000; i++) {
			MANY_KEYS.add(new AEntry<>(last.minusSeconds(i), "value " + i));
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
	public void lowerKeyIntegrityTest() {
		Instant start;
		Instant end;
		int getLoops = 400000;

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

		var lastCube = timeCube.lastEntry();
		var lastTree = treeMap.lastEntry();
		for (var i = 0; i < getLoops; i++) {
			lastCube = timeCube.lowerEntry(lastCube.getKey());
			lastTree = treeMap.lowerEntry(lastTree.getKey());
			assertEquals(lastCube.getValue(), lastTree.getValue());
		}

	}

}
