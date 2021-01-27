package com.company;

public interface IteratorCore {
    boolean hasIndex(int i);
    boolean hasNext();
    PaczkaCore getNext();
    void reset();

}
