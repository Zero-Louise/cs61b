package game2048;

import java.util.Formatter;
import java.util.Observable;


/**
 * The state of a game of 2048.
 *
 * @author TODO: YOUR NAME HERE
 */
public class Model extends Observable {
    /**
     * Current contents of the board.
     */
    private Board board;
    /**
     * Current score.
     */
    private int score;
    /**
     * Maximum score so far.  Updated when game ends.
     */
    private int maxScore;
    /**
     * True iff game is ended.
     */
    private boolean gameOver;

    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */

    /**
     * Largest piece value.
     */
    public static final int MAX_PIECE = 2048;

    /**
     * A new 2048 game on a board of size SIZE with no pieces
     * and score 0.
     */
    public Model(int size) {
        board = new Board(size);
        score = maxScore = 0;
        gameOver = false;
    }

    /**
     * A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes.
     */
    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {
        int size = rawValues.length;
        board = new Board(rawValues, score);
        this.score = score;
        this.maxScore = maxScore;
        this.gameOver = gameOver;
    }

    /**
     * Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     * 0 <= COL < size(). Returns null if there is no tile there.
     * Used for testing. Should be deprecated and removed.
     */
    public Tile tile(int col, int row) {
        return board.tile(col, row);
    }

    /**
     * Return the number of squares on one side of the board.
     * Used for testing. Should be deprecated and removed.
     */
    public int size() {
        return board.size();
    }

    /**
     * Return true iff the game is over (there are no moves, or
     * there is a tile with value 2048 on the board).
     */
    public boolean gameOver() {
        checkGameOver();
        if (gameOver) {
            maxScore = Math.max(score, maxScore);
        }
        return gameOver;
    }

    /**
     * Return the current score.
     */
    public int score() {
        return score;
    }

    /**
     * Return the current maximum game score (updated at end of game).
     */
    public int maxScore() {
        return maxScore;
    }

    /**
     * Clear the board to empty and reset the score.
     */
    public void clear() {
        score = 0;
        gameOver = false;
        board.clear();
        setChanged();
    }

    /**
     * Add TILE to the board. There must be no Tile currently at the
     * same position.
     */
    public void addTile(Tile tile) {
        board.addTile(tile);
        checkGameOver();
        setChanged();
    }

    /**
     * Tilt the board toward SIDE. Return true iff this changes the board.
     * <p>
     * 1. If two Tile objects are adjacent in the direction of motion and have
     * the same value, they are merged into one Tile of twice the original
     * value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     * tilt. So each move, every tile will only ever be part of at most one
     * merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     * value, then the leading two tiles in the direction of motion merge,
     * and the trailing tile does not.
     */


    public boolean tilt(Side side) {
        boolean changed;
        changed = false;

        // TODO: Modify this.board (and perhaps this.score) to account
        // for the tilt to the Side SIDE. If the board changed, set the
        // changed local variable to true.
        board.setViewingPerspective(side);
        changed = moveTile();
        board.setViewingPerspective(Side.NORTH);
        checkGameOver();
        if (changed) {
            setChanged();
        }
        return changed;
    }

    private boolean moveTile() {
        int size = board.size();
        boolean changed = false;
        for (int col = 0; col <= size - 1; col += 1) {
            if (mergeColTiles(col)) {
                changed = true;
            }
        }
        return changed;
    }

    private boolean mergeColTiles(int col) {
        int size = board.size();
        Tile[] colTiles = getColTile(col, size);
        return mergeColTiles(colTiles, col, size);
    }

    private boolean mergeColTiles(Tile[] colTiles, int cur_col, int size) {
        boolean changed = false;
        boolean moved = false;
        boolean merged = false;
        int move_tile_index = size;
        int move_tile_value = Integer.MAX_VALUE;
        for (int dis_row = size - 1; dis_row > 0; dis_row -= 1) {
            Tile dis_tile = colTiles[dis_row];
            if (dis_tile == null) {
                for (int cur_row = dis_row - 1; cur_row >= 0; cur_row -= 1) {
                    Tile cur_tile = colTiles[cur_row];
                    if (cur_tile != null) {
                        if (!board.move(cur_col, dis_row, cur_tile)) {
                            move_tile_index = cur_row;
                            move_tile_value = colTiles[move_tile_index].value();
                            colTiles[move_tile_index] = null;
                            moved = true;
                        }
                        changed = true;
                        break;
                    }
                }
            } else {
                int dis_tile_val = dis_tile.value();
                for (int cur_row = dis_row - 1; cur_row >= 0; cur_row -= 1) {
                    Tile cur_tile = colTiles[cur_row];
                    if (!merged && cur_tile != null) {
                        int cur_tile_val = cur_tile.value();
                        if (dis_tile_val == cur_tile_val) {
                            if (board.move(cur_col, dis_row, cur_tile)) {
                                score += cur_tile_val * 2;
                                colTiles[cur_row] = null;
                                merged = true;
                                changed = true;
                            }
                        }else {
                            break;
                        }
                    }
                }
            }
            if (moved) {
                for (int cur_row2 = move_tile_index - 1; cur_row2 >= 0; cur_row2 -= 1) {
                    Tile cur_tile2 = colTiles[cur_row2];
                    if (cur_tile2 != null){
                        int cur_tile2_val = cur_tile2.value();
                        if (cur_tile2_val != move_tile_value){
                            break;
                        } else {
                            board.move(cur_col, dis_row, cur_tile2);
                            score += cur_tile2.value() * 2;
                            colTiles[cur_row2] = null;
                            move_tile_value = Integer.MAX_VALUE;
                            moved = false;
                        }
                    }
                }
            }
            merged = false;
        }
        return changed;
    }

    private Tile[] getColTile(int col, int size) {
        Tile[] tiles = new Tile[size];
        for (int row = 0; row < size; row += 1) {
            tiles[row] = board.tile(col, row);
        }
        return tiles;
    }


    /*
    private void print_board(Board b) {
        int size = b.size();
        for (int i = 0; i <= size - 1; i++) {
            for (int j = 0; j <= size - 1; j++) {
                Tile tile = b.tile(i, j);
                if (tile != null) {
                    System.out.print(b.tile(i, j).value() + " ");
                } else {
                    System.out.print(0 + " ");
                }
            }
            System.out.println();
        }
    }
     */

    /**
     * Checks if the game is over and sets the gameOver variable
     * appropriately.
     */
    private void checkGameOver() {
        gameOver = checkGameOver(board);
    }

    /**
     * Determine whether game is over.
     */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /**
     * Returns true if at least one space on the Board is empty.
     * Empty spaces are stored as null.
     */
    public static boolean emptySpaceExists(Board b) {
        // TODO: Fill in this function.
        // tile(int col, int row) and size() methods of the Board class.
        int size = b.size();
        for (int col = 0; col < size; col += 1) {
            for (int row = 0; row < size; row += 1) {
                if (b.tile(col, row) == null) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public static boolean maxTileExists(Board b) {
        // TODO: Fill in this function.
        int size = b.size();
        for (int col = 0; col < size; col += 1) {
            for (int row = 0; row < size; row += 1) {
                Tile tile = b.tile(col, row);
                if (tile != null && tile.value() == MAX_PIECE) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public static boolean atLeastOneMoveExists(Board b) {
        // TODO: Fill in this function.
        if (emptySpaceExists(b) || maxTileExists(b)) {
            return true;
        }
        int size = b.size();
        for (int col = 0; col < size; col += 1) {
            for (int row = 0; row < size; row += 1) {
                if (adjacentSameVal(b, col, row)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean adjacentSameVal(Board b, int col, int row) {
        int val = b.tile(col, row).value();
        int size = b.size();
        boolean flag = false;

        if (!flag && row + 1 <= size - 1) {
            flag = checkSameVal(val, b.tile(col, row + 1).value());
        }
        if (!flag && col + 1 <= size - 1) {
            flag = checkSameVal(val, b.tile(col + 1, row).value());
        }
        if (!flag && col - 1 >= 0) {
            flag = checkSameVal(val, b.tile(col - 1, row).value());
        }
        if (!flag && row - 1 >= 0) {
            flag = checkSameVal(val, b.tile(col, row - 1).value());
        }
        return flag;
    }

    private static boolean checkSameVal(int val1, int val2) {
        if (val1 == val2) {
            return true;
        }
        return false;
    }


    @Override
    /** Returns the model as a string, used for debugging. */
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

    @Override
    /** Returns whether two models are equal. */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    @Override
    /** Returns hash code of Model’s string. */
    public int hashCode() {
        return toString().hashCode();
    }
}
