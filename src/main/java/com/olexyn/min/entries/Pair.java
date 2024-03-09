package com.olexyn.min.entries;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Pair<A,B> {

    private A a;
    private B b;

}
