package task2;

public class Tractor {

    Position position = new Position(0, 0);
    Field field = new Field(5, 5);
    Orientation orientation = Orientation.NORTH;

    public void move(final String command) {
        if (command == "F") {
            moveForwards();
        } else if (command == "T") {
            turnClockwise();
        }
    }

    public void moveForwards() {
        if (orientation == Orientation.NORTH) {
            position.y++;
        } else if (orientation == Orientation.EAST) {
            position.x++;
        } else if (orientation == Orientation.SOUTH) {
            position.y--;
        } else if (orientation == Orientation.WEST) {
            position.x--;
        }

        if (position.x > field.width ||
                position.y > field.height ||
                position.x < 0 ||
                position.y < 0) {
            throw new TractorInDitchException();
        }
    }

    public void turnClockwise() {
        int nextOrientation = orientation.ordinal() + 1;
        if (nextOrientation == Orientation.values().length) nextOrientation = 0;
        orientation = Orientation.values()[nextOrientation];
    }

    public int getPositionX() {
        return position.x;
    }

    public int getPositionY() {
        return position.y;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    private class Position {
        public int x;
        public int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private class Field {
        public final int width;
        public final int height;

        public Field(final int width, final int height) {
            this.width = width;
            this.height = height;
        }
    }
}