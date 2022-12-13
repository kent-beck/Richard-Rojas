package com.richardroj.mower.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Position {
    private int x;
    private int y;
    private Direction direction;
}
