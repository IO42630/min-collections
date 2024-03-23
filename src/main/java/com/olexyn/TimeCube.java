package com.olexyn;

import java.time.Instant;
import java.util.Map.Entry;

import com.olexyn.min.entries.AEntry;
import lombok.Getter;
import org.checkerframework.checker.nullness.qual.Nullable;

public class TimeCube implements NavigableCube {

	@Getter
	private final Instant start;
	private final int size;
	private final int precision;
	private final AEntry<Instant, Object>[][] arr;
	private final int[] first = new int[2];
	private final int[] last = new int[2];

	/**
	 * Default values. Good for 2015-2043 with 10ms precision.
	 */
	@SuppressWarnings("unchecked")
	public TimeCube() {
		this.start = Instant.parse("2015-01-01T00:00:00Z");
		this.size = 300000;
		this.precision = 10;
		this.arr = (AEntry<Instant, Object>[][]) new AEntry[size][];
		this.first[0] = size;
		this.first[1] = size;
		this.last[0] = 0;
		this.last[1] = 0;
	}

	@SuppressWarnings("unchecked")
	public TimeCube(Instant start,  int size, int precision) {
		this.start = start;
		this.size = size;
		this.precision = precision;
		this.arr = (AEntry<Instant, Object>[][]) new AEntry[size][];
		this.first[0] = size;
		this.first[1] = size;
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
		if (first[0] > coords[0] || (first[0] == coords[0] && first[1] > coords[1])) {
				first[0] = coords[0];
				first[1] = coords[1];
		}
		if (last[0] < coords[0] || ( last[0] == coords[0] && last[1] < coords[1])) {
				last[0] = coords[0];
				last[1] = coords[1];

		}
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


	@Override
	public Instant lowerKey(Instant key) {
		return lowerEntry(key).getKey();
	}

	@Override
	public Entry<Instant, Object> lowerEntry(Instant key) {
		long idx = getIndex(key);
		var cords = getCoords(idx);
		while (true) {
			if (cords[0] == first[0] && cords[1] == first[1]) {
				return get(idx);
			}
			if (cords[0] == 0 && cords[1] == 0) {
				return get(idx);
			}
			if (cords[1] == 0) {
				var result = arr[cords[0] - 1][size -1];
				if (result != null) {
					return result;
				}
				cords[0] = cords[0] - 1;
				cords[1] = size -1;
			} else {
				var result = arr[cords[0]][cords[1] - 1];
				if (result != null) {
					return result;
				}
				cords[1] = cords[1] - 1;
			}
		}
	}

	@Override
	public Entry<Instant, Object> firstEntry() {
		return arr[first[0]][first[1]];
	}

	@Override
	public Entry<Instant, Object> lastEntry() {
		return arr[last[0]][last[1]];
	}


}
