package com.richardroj.mower.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Mower {
    private Position position;
    private Integer valueMaxX;
    private Integer valueMaxY;

    @Override
    public String toString() {
        return position.getX()+" "+ position.getY()+" " +position.getDirection();
    }

}
