package com.olexyn;

import java.time.Instant;

import com.olexyn.min.entries.Pair;
import lombok.Getter;

public class TimeCube {

	@Getter
	private final Instant start;
	private final int size;
	private final int precision;
	private final Pair<Instant, Object>[][] arr;

	/**
	 * Default values. Good for 2015-2043 with 10ms precision.
	 */
	@SuppressWarnings("unchecked")
	public TimeCube() {
		this.start = Instant.parse("2015-01-01T00:00:00Z");
		this.size = 300000;
		this.precision = 10;
		this.arr = (Pair<Instant, Object>[][]) new Pair[size][];
	}

	@SuppressWarnings("unchecked")
	public TimeCube(Instant start,  int size, int precision) {
		this.start = start;
		this.size = size;
		this.precision = precision;
		this.arr = (Pair<Instant, Object>[][]) new Pair[size][];
	}

	private long getIndex(Instant instant) {
		return (instant.toEpochMilli() - start.toEpochMilli()) / precision;
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

	@SuppressWarnings("unchecked")
	public int[] getCoords(long index) {
		int aIdx = (int) (index / size);
		int bIdx = (int) (index % size);
		if (arr[aIdx] == null) {
			arr[aIdx] = new Pair[size];
		}
		return new int[] { aIdx, bIdx };
	}

}
