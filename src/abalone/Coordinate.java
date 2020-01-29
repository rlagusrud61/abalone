package abalone;

import java.util.Objects;

public class Coordinate {

    // initially named just int x and y, but due to checkstyle had to change the names.
    public int xxCord;
    public int yyCord;

    public Coordinate(int xxCord, int yyCord) {
        this.xxCord = xxCord;
        this.yyCord = yyCord;
    }

    public Coordinate(Coordinate coord) {
        this.xxCord = coord.xxCord;
        this.yyCord = coord.yyCord;
    }

    /**
     * Check if the step that the coordinate is making is valid.
     *
     * @param direction direction of the movement
     * @return true if step != null
     * @invariant x >=0 && y >=0
     * @requires direction != null
     */
    public boolean isValidStep(Move.Direction direction) {
        int x = this.xxCord;
        int y = this.yyCord;

        switch (direction) {
            case NE:
                if (y == 0) {
                    return false;
                }
                return x != Board.ROW_SIZES[y] - 1;
            case E:
                return x != Board.ROW_SIZES[y] - 1;
            case SE:
                if (y == Board.ROW_SIZES.length - 1) {
                    return false;
                }
                return x != Board.ROW_SIZES[y] - 1;
            case SW:
                if (y == Board.ROW_SIZES.length - 1) {
                    return false;
                }
                return x != 0;
            case W:
                return x != 0;
            case NW:
                if (y == 0) {
                    return false;
                }
                return x != 0;
            default:
                return false;
        }
    }

    /**Steps the coordinate to a given direction.
     * @invariant x >=0 && y >=0
     * @param direction direction of the movement.
     * @return Coordinate of this coordinate after moving with the direction.
     */
    public Coordinate step(Move.Direction direction) {
        int x = this.xxCord;
        int y = this.yyCord;

        switch (direction) {
            case NE:
                if (y == 0) {
                    return null;
                }
                if (x == Board.ROW_SIZES[y] - 1 && y <= 4) {
                    return null;
                }

                if (Board.ROW_SIZES[y - 1] > Board.ROW_SIZES[y]) {
                    x += 1;
                }
                y -= 1;
                break;
            case E:
                if (x == Board.ROW_SIZES[y] - 1) {
                    return null;
                }

                x += 1;
                break;
            case SE:
                if (y == Board.ROW_SIZES.length - 1) {
                    return null;
                }
                if (x == Board.ROW_SIZES[y] - 1 && y >= 4) {
                    return null;
                }

                if (Board.ROW_SIZES[y + 1] > Board.ROW_SIZES[y]) {
                    x += 1;
                    y += 1;
                }
                break;
            case SW:
                if (y == Board.ROW_SIZES.length - 1) {
                    return null;
                }
                if (x == 0 && y >= 4) {
                    return null;
                }

                if (Board.ROW_SIZES[y + 1] < Board.ROW_SIZES[y]) {
                    x -= 1;
                    y += 1;
                }
                break;
            case W:
                if (x == 0) {
                    return null;
                }

                x -= 1;
                break;
            case NW:
                if (y == 0) {
                    return null;
                }
                if (x == 0 && y <= 4) {
                    return null;
                }

                if (Board.ROW_SIZES[y - 1] < Board.ROW_SIZES[y]) {
                    x -= 1;
                    y -= 1;
                }
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

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Coordinate that = (Coordinate) o;
        return xxCord == that.xxCord && yyCord == that.yyCord;
    }

    @Override
    public int hashCode() {
        return Objects.hash(xxCord, yyCord);
    }

}
