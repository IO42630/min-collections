package com.olexyn;

import java.time.Instant;

import com.olexyn.min.entries.Pair;

public class TimeCube {

	private final int subSize = 65536;

	@SuppressWarnings("unchecked")
	private final Pair<Instant, Object>[][] arr = (Pair<Instant, Object>[][]) new Pair[65536][];



	public Pair<Instant, Object> get(int index) {
		Pair<Instant, Object>[] subArr = arr[index / subSize];
		if (subArr == null) {
			arr[index / subSize] = (Pair<Instant, Object>[]) new Pair[subSize];
		}
		return arr[index / subSize][index % subSize];
	}


	public void put(int index,Pair<Instant, Object> value) {
		Pair<Instant, Object>[] subArr = arr[index / subSize];
		if (subArr == null) {
			arr[index / subSize] = (Pair<Instant, Object>[]) new Pair[subSize];
		}
		arr[index / subSize][index % subSize] = value;
	}


}
