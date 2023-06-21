package pieces;

import java.util.Iterator;
import utilities.Coordinates;

/**
 *
 * @author Ángel Marqués García
 */
public class PieceMove implements Iterable<Coordinates> {

    public int n;
    public Coordinates move;

    public PieceMove(int n, Coordinates move) {
        this.n = n;
        this.move = move;
    }
    public PieceMove(int n, int x, int y) {
        this.n = n;
        this.move = new Coordinates(x,y);
    }

    @Override
    public Iterator<Coordinates> iterator() {
        return new PieceMoveIterator(n); //pasarle this es una opción atractiva
    }
    
    class PieceMoveIterator implements Iterator<Coordinates> {

        private int counter;
        private int goal;

        public PieceMoveIterator(int n) {
            goal = n;
            counter = 0;
        }

        @Override
        public boolean hasNext() {
            return counter < goal;
        }

        @Override
        public Coordinates next() {
            counter += 1;
            return move.clone();
        }
    }

}
