package list;

import java.util.Iterator;

public interface ListIterator<E> extends Iterator<E> {
    /*
     * Inserts the specified element into the list (optional operation).
     *
     * @param e the element to be inserted
     */
    public void add(E e);

    public boolean hasNext();

    public boolean hasPrevious();

    public E next();
    /*
     * Returns the index of the element that would be returned by a subsequent call to next().
     *
     * @return the index of the next element
     */
    public int nextIndex();

    public E previous();
    /*
     * Returns the index of the element that would be returned by a subsequent call to previous().
     *
     * @return the index of the previous element
     */
    public int previousIndex();

    public void remove();

    public void set(E e);
}