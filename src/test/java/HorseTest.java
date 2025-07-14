import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

public class HorseTest {
    @Test
    public void constructorShouldThrowExceptionWhenNameIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(null, 10, 10));
    }

    @Test
    public void exceptionShouldHaveMessageWhenNameIsNull() {
        try {
            new Horse(null, 1, 1);
            fail();
        }
        catch (IllegalArgumentException e) {
            assertEquals("Name cannot be null.", e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t"})
    public void constructorShouldThrowExceptionWhenNameIsBlank(String name) {
        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class, () -> new Horse(name, 43, 2));
        assertEquals("Name cannot be blank.", e.getMessage());
    }

    @Test
    public void constructorShouldThrowExceptionWhenSpeedIsNegative() {
        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class, () -> new Horse("name", -32, 1));
        assertEquals("Speed cannot be negative.", e.getMessage());
    }

    @Test
    public void constructorShouldThrowExceptionWhenDistanceIsNegative() {
        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class, () -> new Horse("name", 0, -1));
        assertEquals("Distance cannot be negative.", e.getMessage());
    }

    @Test
    public void gettersShouldReturnValidValues() {
        String name = "Steve";
        int speed = 16;
        int distance = 32;
        Horse horse = new Horse(name, speed, distance);
        assertEquals(name, horse.getName());
        assertEquals(speed, horse.getSpeed());
        assertEquals(distance, horse.getDistance());
    }

    @Test
    public void distanceGetterShouldReturnZeroWhenItsNotGiven() {
        Horse horse = new Horse("Bob", 10);
        assertEquals(0, horse.getDistance());
    }

    @Test
    public void moveShouldCallGetRandomDouble() {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            new Horse("Dave", 10).move();
            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @Test
    public void moveShouldSetValidDistance() {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            double fakeDouble = 0.85;
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(fakeDouble);
            double speed = 13.3;
            double distance = 15.5;
            Horse horse = new Horse("Horse", speed, distance);
            horse.move();
            assertEquals(distance + speed * fakeDouble, horse.getDistance());
        }
    }
}
