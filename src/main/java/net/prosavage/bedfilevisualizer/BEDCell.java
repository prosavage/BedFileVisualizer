package net.prosavage.bedfilevisualizer;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class BEDCell {
    private String chromosome;
    private int start;
    private int end;

    public BEDCell(String chromosome, int start, int end) {
        this.chromosome = chromosome;
        this.start = start;
        this.end = end;
    }

    public String getChromosome() {
        return chromosome;
    }

    public void setChromosome(String chromosome) {
        this.chromosome = chromosome;
    }


    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }


    public class BEDCellIterator<BEDCell> implements Iterator<BEDCell> {
        private BEDCell array[];
        private int pos = 0;

        public BEDCellIterator(BEDCell anArray[]) {
            array = anArray;
        }

        public boolean hasNext() {
            return pos < array.length;
        }

        public BEDCell next() throws NoSuchElementException {
            if (hasNext())
                return array[pos++];
            else
                throw new NoSuchElementException();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
