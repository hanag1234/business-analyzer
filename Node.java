package list;

public class Node<E> {
    private E data;
    private Node<E> next;
    private Node<E> prev;

    public Node() {
        this.data = null;
        this.next = null;
        this.prev = null;
    }

    public Node(E data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }

    public Node(E data, Node<E> next) {
        this.data = data;
        this.next = next;
        this.prev = null;
    }

    public Node(E data, Node<E> next, Node<E> prev) {
        this.data = data;
        this.next = next;
        this.prev = prev;
    }

    public void setData(E data) {
        this.data = data;
    }

    public E getData() {
        return this.data;
    }

    public void setNext(Node<E> next) {
        this.next = next;
    }

    public Node<E> getNext() {
        return this.next;
    }

    public void setPrev(Node<E> prev) {
        this.prev = prev;
    }

    public Node<E> getPrev() {
        return this.prev;
    }
}