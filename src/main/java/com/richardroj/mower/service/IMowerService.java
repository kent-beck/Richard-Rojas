package com.richardroj.mower.service;

import com.richardroj.mower.model.Direction;
import com.richardroj.mower.model.Mower;

import java.util.List;
import java.util.Map;

public interface IMowerService {

    void move(Mower mower, String moves);


    default void moveToLeft(Mower mower){
        switch (mower.getPosition().getDirection()) {
            case N:
                mower.getPosition().setDirection(Direction.W);
                break;
            case S:
                mower.getPosition().setDirection(Direction.E);
                break;
            case E:
                mower.getPosition().setDirection(Direction.N);
                break;
            case W:
                mower.getPosition().setDirection(Direction.S);
                break;
            default:
                throw new IllegalArgumentException("Direction not accepted");
        }

    }

    default void moveToRight(Mower mower){
        switch (mower.getPosition().getDirection()) {
            case N:
                mower.getPosition().setDirection(Direction.E);
                break;
            case S:
                mower.getPosition().setDirection(Direction.W);
                break;
            case E:
                mower.getPosition().setDirection(Direction.S);
                break;
            case W:
                mower.getPosition().setDirection(Direction.N);
                break;
            default:
                throw new IllegalArgumentException("Direction not accepted");
        }
    }

    default void moveForward(Mower mower){
        switch (mower.getPosition().getDirection()){

            case N:
                mower.getPosition().setY(mower.getPosition().getY() + 1);
                break;
            case S:
                mower.getPosition().setY(mower.getPosition().getY() - 1);
                break;
            case E:
                mower.getPosition().setX(mower.getPosition().getX() + 1);
                break;
            case W:
                mower.getPosition().setX(mower.getPosition().getX() - 1);
                break;
        }
    }

}
