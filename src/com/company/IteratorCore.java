package com.company;

public interface IteratorCore {
    boolean hasIndex(int i);
    boolean hasNext();
    Object getNext();
    void reset();
    Object getIndex(int index);
    int size();

}
