package com.richardroj.mower.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class MowerConverterTest {

    @Autowired
    MowerConverter mowerConverter;

    @Test
    void shouldReturnAMapWithMowersAndCommands() {
        //given
        var lines = new ArrayList<String>();
        lines.add("5 5");
        lines.add("1 2 N");
        lines.add("LMLM");
        //when
        var result = mowerConverter.convertLinesToMowersMap(lines);
        //then
        Assertions.assertInstanceOf(HashMap.class, result);
    }

    @Test
    void shouldReturnAnExceptionWhenNotAValidPosition() {
        //given
        var lines = new ArrayList<String>();
        lines.add("5 5");
        lines.add("1 2");//only 2 values
        lines.add("LMLM");
        //when
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> mowerConverter.convertLinesToMowersMap(lines));
        //then
        assertEquals("Not valid position, Expected 2 numbers and 1 Letter for direction", exception.getMessage());
    }

    @Test
    void shouldReturnAnExceptionWhenNotAValidDirection() {
        //given
        var lines = new ArrayList<String>();
        lines.add("5 5");
        lines.add("1 2 3");
        lines.add("LMLM");
        //when
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> mowerConverter.convertLinesToMowersMap(lines));
        //then
        assertEquals("Direction value not valid", exception.getMessage());
    }

    @Test
    void shouldReturnAnExceptionWhenNotLineToProcess() {
        //given
        var lines = new ArrayList<String>();
        //when
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> mowerConverter.convertLinesToMowersMap(lines));
        //then
        assertEquals("Not Lines to process", exception.getMessage());
    }



}
