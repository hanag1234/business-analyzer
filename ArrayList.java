package list;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class ArrayList<E> implements ListIterator<E>{
    private E[] list;
    private int length,position,last;

    public ArrayList() {
        list = (E[]) new Object[10];
        length = 0;
        last = -1;
        position = 0;
    }

    public void add(E e) {
        if (length == list.length) {
            E[] newList = (E[]) new Object[length * 3 / 2];
            for (int i = 0; i < length; i++) {
                newList[i] = list[i];
            }
            list = newList;
        }
        for (int i = length++; i > position; i--) {
            list[i] = list[i - 1];
        }
        list[position++] = e;
        last = -1;
    }

    public boolean hasNext() {
        return position < length;
    }

    public boolean hasPrevious() {
        return position > 0;
    }

    public E next() {
        if (hasNext()) {
            last = position;
            return list[position++];
        } else {
            throw new NoSuchElementException();
        }
    }

    public int nextIndex() {
        return position;
    }

    public E previous() {
        if (hasPrevious()) {
            last = --position;
            return list[position];
        } else {
            throw new NoSuchElementException();
        }
    }

    public int previousIndex() {
        return position - 1;
    }

    public void remove() {
        if (last >= 0) {
            for (int i = last; i < length - 1; i++) {
                list[i] = list[i + 1];
            }
            length--;
            if (last < position) {
                position--;
            }
            last = -1;
        } else {
            throw new IllegalStateException();
        }
    }

    public void set(E e) {
        if (last >= 0) {
            list[last] = e;
        } else {
            throw new IllegalStateException();
        }
    }
}