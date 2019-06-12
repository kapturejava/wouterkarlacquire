package be.kapture.tdd.wouterkarl.acquire;

import java.util.Arrays;
import java.util.List;

public class Coordinates {
    private static final List<String> NUMBER_TO_CHARACTER_LIST = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I");
    private static final int XY_OFFSET = 1;

    private final int x;
    private final int y;

    private Coordinates(int x, int y) {
        if (x <= 0 || x >= 10 || y <= 0 || y >= 13) {
            throw new IllegalArgumentException();
        }

        this.x = x;
        this.y = y;
    }

    static Coordinates create(int x, int y) {
        return new Coordinates(x, y);
    }

    public double distanceToOrigin(){
        return Math.sqrt(Math.pow(x - XY_OFFSET, 2) + Math.pow(y - XY_OFFSET, 2));
    }

    boolean isNextTo(Coordinates other){
        if((this.x == other.x)) {
            return Math.abs( this.y - other.y) == 1;
        }
        if((this.y == other.y)  ) {
            return Math.abs(this.x - other.x) == 1;
        }
        return false;
    }

    @Override
    public String toString() {
        return x + NUMBER_TO_CHARACTER_LIST.get(y - XY_OFFSET);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinates that = (Coordinates) o;

        if (x != that.x) return false;
        return y == that.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
