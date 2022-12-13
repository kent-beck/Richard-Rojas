package com.richardroj.mower.service;

import com.richardroj.mower.converter.MowerConverter;
import com.richardroj.mower.model.Commands;
import com.richardroj.mower.model.Mower;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.logging.Logger;

@Component
public class MowerServiceImpl implements IMowerService {

    Logger logger = Logger.getLogger(MowerServiceImpl.class.getName());

    @Autowired
    MowerConverter mowerConverter;

    @Override
    public void move(Mower mower, String moves) {
        if (mower.getPosition().getX() > mower.getValueMaxX() )
            throw new IllegalArgumentException("Value X should be less than with");

        if (mower.getPosition().getY() > mower.getValueMaxY() )
            throw new IllegalArgumentException("Value Y should be less than height");

        List<Commands> commands = mowerConverter.convertMovesStringToMovesList(moves);
        commands.forEach(command -> executeCommand(mower, command));
        logger.info("Mower result: "+mower);
    }

    private void executeCommand(Mower mower, Commands command){
        switch (command) {
            case L:
                moveToLeft(mower);
                break;
            case R:
                moveToRight(mower);
                break;
            case M:
                moveForward(mower);
                break;
        }
    }
}
