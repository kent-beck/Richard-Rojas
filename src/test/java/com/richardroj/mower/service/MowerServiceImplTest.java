package com.richardroj.mower.service;

import com.richardroj.mower.converter.MowerConverter;
import com.richardroj.mower.model.Direction;
import com.richardroj.mower.model.Mower;
import com.richardroj.mower.model.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MowerServiceImplTest {

    public static final Integer maxValueX = 5;
    public static final Integer maxValueY = 5;

    @Autowired
    IMowerService mowerService;

    @Autowired
    MowerConverter mowerConverter;

    @Test
    void shouldMoveWhenValidValues() {
        //given
        Position position = createPosition(1, 2, Direction.N);
        Mower mower = createMower(position);
        Position positionExpected = createPosition(1, 3, Direction.N);
        Mower mowerResult = createMower(positionExpected);
        //when
        mowerService.move(mower, "LMLMLMLMM");
        //then
        Assertions.assertEquals(mower, mowerResult);
    }

    @Test
    void shouldMoveYOneWhenDirectionIsNorth() {
        //given
        Position position = createPosition(0, 0, Direction.N);
        Mower mower = createMower(position);
        Position positionExpected = createPosition(0, 1, Direction.N);
        Mower mowerResult = createMower(positionExpected);
        //when
        mowerService.move(mower, "M");
        //then
        Assertions.assertEquals(mower, mowerResult);
    }

    @Test
    void shouldMoveXOneWhenDirectionIsEast() {
        //given
        Position position = createPosition(0, 0, Direction.E);
        Mower mower = createMower(position);
        Position positionExpected = createPosition(1, 0, Direction.E);
        Mower mowerResult = createMower(positionExpected);
        //when
        mowerService.move(mower, "M");
        //then
        Assertions.assertEquals(mower, mowerResult);
    }

    @Test
    void shouldReturnExceptionWhenXIsGreaterThanWidth() {
        //given
        var x = 8;
        Position position = createPosition(x, 3, Direction.S);
        var width = 5;
        Mower mower = createMower(position);
        mower.setValueMaxX(width);
        //when
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> mowerService.move(mower,"LMLMLMLMM"));
        //then
        assertEquals("Value X should be less than with", exception.getMessage());
    }

    @Test
    void shouldReturnExceptionWhenNotValidCommand() {
        //given
        Position position = createPosition(3, 3, Direction.S);
        Mower mower = createMower(position);
        //when
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> mowerService.move(mower,"MMXRMMRMRRM"));
        //then
        assertEquals("Command not accepted", exception.getMessage());

    }

    @Test
    void shouldProcessMowersMapFromFile() throws IOException {
        //Given
        Path path = Paths.get("src/test/resources/input.txt");
        var lines = Files.readAllLines(path);

        //When
        var mowersMap = mowerConverter.convertLinesToMowersMap(lines);
        Position positionExpected1 = createPosition(1,3,Direction.N);
        Position positionExpected2 = createPosition(5,1,Direction.E);
        Mower mowerExpected1 = createMower(positionExpected1);
        Mower mowerExpected2 = createMower(positionExpected2);
        mowersMap.forEach((mower, commands) -> mowerService.move(mower, commands));

        //Then
        Assertions.assertEquals(mowersMap.keySet().toArray()[0], mowerExpected1);
        Assertions.assertEquals(mowersMap.keySet().toArray()[1], mowerExpected2);

    }

    private Mower createMower(Position position) {
        return new Mower()
            .setPosition(position)
            .setValueMaxX(MowerServiceImplTest.maxValueX)
            .setValueMaxY(MowerServiceImplTest.maxValueY);
    }

    private Position createPosition(int x, int y, Direction direction) {
        return new Position()
            .setX(x)
            .setY(y)
            .setDirection(direction);
    }

}
