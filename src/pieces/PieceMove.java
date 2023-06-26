package pieces;

import java.util.Iterator;
import java.util.Objects;
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
    
    @Override
    public boolean equals(Object obj){
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PieceMove other = (PieceMove) obj;
        if (n != other.n) {
            return false;
        }
        return move.equals(other.move);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + this.n;
        hash = 83 * hash + Objects.hashCode(this.move);
        return hash;
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
