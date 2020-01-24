package abalone;

import java.util.Objects;

public class Coordinate {

    public int x;
    public int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate(Coordinate coord) {
        this.x = coord.x;
        this.y = coord.y;
    }

    public Coordinate step(Move.Direction direction) {
        int x = this.x;
        int y = this.y;

        switch (direction) {
            case NE:
                if (y == 0)
                    return null;
                if (x == Board.ROW_SIZES[y] - 1)
                    return null;

                if (Board.ROW_SIZES[y - 1] > Board.ROW_SIZES[y])
                    x += 1;
                y -= 1;
                break;
            case E:
                if (x == Board.ROW_SIZES[y] - 1)
                    return null;

                x += 1;
                break;
            case SE:
                if (y == Board.ROW_SIZES.length - 1)
                    return null;
                if (x == Board.ROW_SIZES[y] - 1)
                    return null;

                if (Board.ROW_SIZES[y + 1] > Board.ROW_SIZES[y])
                    x += 1;
                y += 1;
                break;
            case SW:
                if (y == Board.ROW_SIZES.length - 1)
                    return null;
                if (x == 0)
                    return null;

                if (Board.ROW_SIZES[y + 1] > Board.ROW_SIZES[y])
                    x -= 1;
                y += 1;
                break;
            case W:
                if (x == 0)
                    return null;

                x -= 1;
                break;
            case NW:
                if (y == 0)
                    return null;
                if (x == 0)
                    return null;

                if (Board.ROW_SIZES[y - 1] > Board.ROW_SIZES[y])
                    x -= 1;
                y -= 1;
                break;
            default:
                break;
        }

        return new Coordinate(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) return false;

        Coordinate that = (Coordinate) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

}
