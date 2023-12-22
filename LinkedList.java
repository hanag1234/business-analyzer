package list;

import java.util.ListIterator;
import java.util.NoSuchElementException;
import list.Node;

public class LinkedList<E> implements ListIterator<E> {
    private Node<E> place;
    private Node<E> end; 
    private Node<E> beginningNode;
    private Node<E> finalNode;
    private Node<E> previous; 
    private int index;

    public LinkedList() {
        index = 0;
        beginningNode = null;
        finalNode = null;
        place = null;
        end = null;
        previous = null;
    }
    public void set(E e) {
        if (end == null) {
            throw new IllegalStateException();
        } else {
            end.setData(e);
        }
    }
    public boolean hasNext() {
        return place != null;
    }

    public boolean hasPrevious() {
        return previous != null;
    }
    public int nextIndex() {
        return index;
    }
    public int previousIndex() {
        return index - 1;
    }

    public void add(E e) {
        Node<E> node = new Node<E>(e);
        if (place == null) {
            if (beginningNode == null && finalNode == null) {
                beginningNode = node;
            } else {
                finalNode.setNext(node);
                node.setPrev(finalNode);
            }
            finalNode = node;
        } else {
            if (beginningNode == place) {
                beginningNode = node;
            } else {
                place.getPrev().setNext(node);
                node.setPrev(place.getPrev());
            }
            node.setNext(place);
            place.setPrev(node);
        }
        end = null;
        previous = node;
        index++;
    }

    public E next() {
        if (hasNext()) {
            E element = place.getData();
            previous = place;
            end = place;
            place = place.getNext();
            index++;
            return element;
        } else {
            throw new NoSuchElementException();
        }
    }

    public E previous() {
        if (hasPrevious()) {
            place = previous;
            end = previous;
            previous = previous.getPrev();
            index--;
            return place.getData();
        } else {
            throw new NoSuchElementException();
        }
    }

    public void remove() {
        if (end == null) {
            throw new IllegalStateException();
        } else {
            if (beginningNode == end) {
                beginningNode = end.getNext();
            } else {
                end.getPrev().setNext(end.getNext());
            }
            if (finalNode == end) {
                finalNode = end.getPrev();
            } else {
                end.getNext().setPrev(end.getPrev());
            }
            end = null;
            if (end != place) {
                index--;
            }
        }
    }
}
