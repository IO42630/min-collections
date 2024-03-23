package com.olexyn;

import java.time.Instant;
import java.util.Map.Entry;

public interface NavigableCube {


	Instant lowerKey(Instant key);

	Entry<Instant,Object> lowerEntry(Instant key);

}
