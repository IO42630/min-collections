package com.olexyn;

import java.time.Instant;

import com.olexyn.min.entries.AEntry;
import lombok.Getter;

public class TimeCube {

	@Getter
	private final Instant start;
	private final int size;
	private final int precision;
	private final AEntry<Instant, Object>[][] arr;

	/**
	 * Default values. Good for 2015-2043 with 10ms precision.
	 */
	@SuppressWarnings("unchecked")
	public TimeCube() {
		this.start = Instant.parse("2015-01-01T00:00:00Z");
		this.size = 300000;
		this.precision = 10;
		this.arr = (AEntry<Instant, Object>[][]) new AEntry[size][];
	}

	@SuppressWarnings("unchecked")
	public TimeCube(Instant start,  int size, int precision) {
		this.start = start;
		this.size = size;
		this.precision = precision;
		this.arr = (AEntry<Instant, Object>[][]) new AEntry[size][];
	}

	private long getIndex(Instant instant) {
		return (instant.toEpochMilli() - start.toEpochMilli()) / precision;
	}

	public AEntry<Instant, Object> get(Instant instant) {
		return get(getIndex(instant));
	}

	private AEntry<Instant, Object> get(long index) {
		var coords = getCoords(index);
		return arr[coords[0]][coords[1]];
	}

	public void put(Instant instant, Object value) {
		put(getIndex(instant), new AEntry<>(instant, value));
	}

	private void put(long index, AEntry<Instant, Object> value) {
		var coords = getCoords(index);
		arr[coords[0]][coords[1]] = value;
	}

	@SuppressWarnings("unchecked")
	public int[] getCoords(long index) {
		int aIdx = (int) (index / size);
		int bIdx = (int) (index % size);
		if (arr[aIdx] == null) {
			arr[aIdx] = new AEntry[size];
		}
		return new int[] { aIdx, bIdx };
	}

}
