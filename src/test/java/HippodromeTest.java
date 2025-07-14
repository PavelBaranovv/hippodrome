import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class HippodromeTest {
    @Test
    public void constructorShouldThrowWhenNullIsGiven() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
        assertEquals("Horses cannot be null.", e.getMessage());
    }

    @Test
    public void constructorShouldThrowWhenEmptyListIsGiven() {
        List<Horse> list = new ArrayList<>();
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(list));
        assertEquals("Horses cannot be empty.", e.getMessage());
    }

    @Test
    public void getterShouldReturnValidListOfHorses() {
        List<Horse> originalHorses = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            originalHorses.add(new Horse("Horse-" + i, i, i*2));
        }
        Hippodrome hippodrome = new Hippodrome(originalHorses);
        assertEquals(originalHorses, hippodrome.getHorses());
    }

    @Test
    public void moveShouldCallHorseMove() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horses.add(mock(Horse.class));
        }
        new Hippodrome(horses).move();

        for (Horse horse : horses) {
            verify(horse).move();
        }
    }

    @Test
    public void getWinnerShouldReturnHorseWithBiggestDistance() {
        List<Horse> horses = new ArrayList<>();
        Horse winner = new Horse("Winner", 5, 55.4);
        horses.add(new Horse("1", 4, 30));
        horses.add(winner);
        horses.add(new Horse("2", 4, 55.35));
        horses.add(new Horse("3", 4, 9));

        Hippodrome hippodrome = new Hippodrome(horses);

        assertEquals(winner, hippodrome.getWinner());
    }
}
