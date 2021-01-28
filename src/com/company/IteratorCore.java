package com.company;

public interface IteratorCore {
    boolean hasIndex(int i);
    boolean hasNext();
    PaczkaCore getNext();
    void reset();
    PaczkaCore getIndex(int index);
    void add(Paczka co);
    int size();

}
