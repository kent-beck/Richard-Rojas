package com.richardroj.mower.converter;

import com.richardroj.mower.model.Commands;
import com.richardroj.mower.model.Direction;
import com.richardroj.mower.model.Mower;
import com.richardroj.mower.model.Position;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MowerConverter {

    public Map<Mower, String> convertLinesToMowersMap(List<String> lines) {
        if (lines.size() == 0)
            throw new IllegalArgumentException("Not Lines to process");

        var mowersMap = new HashMap<Mower, String>();
        var valuesXY = lines.get(0).split(" ");
        var valueMaxX = getValueMax(valuesXY, 0);
        var valueMaxY = getValueMax(valuesXY, 1);

        var index = 1;
        //From line 2 to get values for each mower
        while(index < lines.size()){
            var position = getPositionFromLine(lines.get(index));
            index++;
            var commands = lines.get(index);
            var mower = new Mower()
                .setValueMaxY(valueMaxY)
                .setValueMaxX(valueMaxX)
                .setPosition(position);

            mowersMap.put(mower, commands);
            index++;
        }

        return mowersMap;
    }

    private Position getPositionFromLine(String line) {
        var positions = line.split(" ");
        if (positions.length < 3)
            throw new IllegalArgumentException("Not valid position, Expected 2 numbers and 1 Letter for direction");

        return new Position()
            .setX(Integer.parseInt(positions[0]))
            .setY(Integer.parseInt(positions[1]))
            .setDirection(convertCharToDirection(positions[2].charAt(0)));
    }

    private Integer getValueMax(String[] valuesXY, int x) {
        return Integer.valueOf(valuesXY[x]);
    }

    public static List<Commands> convertMovesStringToMovesList(String moves) {
        var listMoves = moves.toCharArray();
        var commandsList = new ArrayList<Commands>();
        for (char listMove : listMoves) {
            switch (listMove) {
                case 'L':
                    commandsList.add(Commands.L);
                    break;
                case 'R':
                    commandsList.add(Commands.R);
                    break;
                case 'M':
                    commandsList.add(Commands.M);
                    break;
                default:
                    throw new IllegalArgumentException("Command not accepted");
            }

        }
        return commandsList;
    }

    public static Direction convertCharToDirection(char charValue) {

        Direction direction;
        switch (charValue){
            case 'S':
                direction = Direction.S;
                break;
            case 'N':
                direction = Direction.N;
                break;
            case 'E':
                direction = Direction.E;
                break;
            case 'W':
                direction = Direction.W;
                break;
            default:
                throw new IllegalArgumentException("Direction value not valid");
        }

        return direction;
    }
}
