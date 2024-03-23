package com.olexyn;

import java.time.Instant;

import com.olexyn.min.entries.Pair;

public class TimeCube {

	public static final Instant START = Instant.parse("2015-01-01T00:00:00Z");

	// This TimeQube is meant to track 10 years with a precision of 10ms.
	// 10 years = 315569520000 ms
	// 10ms precision = 31556952000 slots
	// Root of 31556952000 = 177643 -> 200000

	private static final int SUB_SIZE = 300000;


	public TimeCube() {



	}


	@SuppressWarnings("unchecked")
	private final Pair<Instant, Object>[][] arr = (Pair<Instant, Object>[][]) new Pair[SUB_SIZE][];

	private static long getIndex(Instant instant) {
		return (instant.toEpochMilli() - START.toEpochMilli()) / 10;
	}

	public Pair<Instant, Object> get(Instant instant) {
		return get(getIndex(instant));
	}

	private Pair<Instant, Object> get(long index) {
		var coords = getCoords(index);
		return arr[coords[0]][coords[1]];
	}

	public void put(Instant instant, Object value) {
		put(getIndex(instant), new Pair<>(instant, value));
	}

	private void put(long index, Pair<Instant, Object> value) {
		var coords = getCoords(index);
		arr[coords[0]][coords[1]] = value;
	}

	public int[] getCoords(long index) {
		int aIdx = (int) (index / SUB_SIZE);
		int bIdx = (int) (index % SUB_SIZE);
		if (arr[aIdx] == null) {
			arr[aIdx] = (Pair<Instant, Object>[]) new Pair[SUB_SIZE];
		}
		return new int[] { aIdx, bIdx};
	}

}
