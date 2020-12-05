package ua.edu.ucu.immutable;

import lombok.Getter;
import lombok.Setter;

public class ImmutableLinkedList implements ImmutableList {
    private Node head = new Node();
    private int size = 0;

    private static class Node {
        @Getter @Setter
        private Object data;
        @Getter @Setter
        private Node next;

        public Node() {
            this.setData(null);
            this.setNext(null);
        }

        public Node(Object data) {
            this.setData(data);
            this.setNext(null);
        }
    }

    public ImmutableLinkedList() { }

    public ImmutableLinkedList(Object[] c) {
        if (c.length > 0) {
            this.size = c.length;
            this.head.setData(c[0]);
            Node currentNode = this.head;

            for (int i = 1; i < c.length; i++) {
                Node newNode = new Node(c[i]);
                currentNode.setNext(newNode);
                currentNode = newNode;
            }
        }
    }

    public ImmutableLinkedList(Node head) {
        this.head = head;

        if (this.head.getData() != null) {
            this.size++;
            Node currentNode = this.head;

            while (currentNode.getNext() != null) {
                currentNode = currentNode.getNext();
                this.size++;
            }
        }
    }

    private Node copyLinkedList() {
        Node headCopy = new Node(this.head.getData());

        if (headCopy.getData() != null) {
            Node currentNode = this.head;
            Node currentNodeCopy = headCopy;

            while (currentNode.getNext() != null) {
                Node newNode = new Node(currentNode.getNext().getData());
                currentNodeCopy.setNext(newNode);

                currentNode = currentNode.getNext();
                currentNodeCopy = newNode;
            }
        }

        return headCopy;
    }

    @Override
    public ImmutableLinkedList add(Object e) {
        Node headCopy = this.copyLinkedList();

        if (this.size == 0) {
            headCopy.setData(e);
        } else {
            Node newNode = new Node(e);
            Node currentNode = headCopy;

            for (int i = 0; i < this.size - 1; i++) {
                currentNode = currentNode.getNext();
            }

            currentNode.setNext(newNode);
        }

        return new ImmutableLinkedList(headCopy);
    }

    @Override
    public ImmutableLinkedList add(int index, Object e) {
        if (0 <= index && index < this.size) {
            Node headCopy = this.copyLinkedList();
            Node newNode = new Node(e);
            Node currentNode = headCopy;

            if (index == 0) {
                newNode.setNext(headCopy);
                headCopy = newNode;
            } else {
                int i = index;

                while (i != 1) {
                    currentNode = currentNode.getNext();
                    i -= 1;
                }

                newNode.setNext(currentNode.getNext());
                currentNode.setNext(newNode);
            }

            return new ImmutableLinkedList(headCopy);
        }

        throw new IndexOutOfBoundsException("Index out of bounds");
    }

    @Override
    public ImmutableLinkedList addAll(Object[] c) {
        Node headCopy = this.copyLinkedList();
        ImmutableLinkedList immutableLinkedListCopy =
                new ImmutableLinkedList(headCopy);

        for (Object e: c) {
            immutableLinkedListCopy = immutableLinkedListCopy.add(e);
        }

        return immutableLinkedListCopy;
    }

    @Override
    public ImmutableLinkedList addAll(int index, Object[] c) {
        Node headCopy = this.copyLinkedList();
        ImmutableLinkedList immutableLinkedListCopy =
                new ImmutableLinkedList(headCopy);
        int i = index;

        for (Object e: c) {
            immutableLinkedListCopy = immutableLinkedListCopy.add(i, e);
            i += 1;
        }

        return immutableLinkedListCopy;
    }

    @Override
    public Object get(int index) {
        if (0 <= index && index < this.size) {
            Node currentNode = this.head;
            int i = index;

            while (i > 0) {
                currentNode = currentNode.getNext();
                i -= 1;
            }

            return currentNode.getData();
        }

        throw new IndexOutOfBoundsException("Index out of bounds");
    }

    @Override
    public ImmutableLinkedList remove(int index) {
        if (0 <= index && index < this.size) {
            Node headCopy = this.copyLinkedList();

            if (index == 0) {
                if (this.size == 1) {
                    headCopy = new Node();
                } else {
                    headCopy = headCopy.getNext();
                }
            } else {
                Node currentNode = headCopy;
                int i = index;

                while (i > 1) {
                    currentNode = currentNode.getNext();
                    i -= 1;
                }

                currentNode.setNext(currentNode.getNext().getNext());
            }

            return new ImmutableLinkedList(headCopy);
        }

        throw new IndexOutOfBoundsException("Index out of bounds");
    }

    @Override
    public ImmutableLinkedList set(int index, Object e) {
        if (0 <= index && index < this.size) {
            Node headCopy = this.copyLinkedList();
            Node currentNode = headCopy;
            int i = index;

            while (i > 0) {
                currentNode = currentNode.getNext();
                i -= 1;
            }

            currentNode.setData(e);

            return new ImmutableLinkedList(headCopy);
        }

        throw new IndexOutOfBoundsException("Index out of bounds");
    }

    @Override
    public int indexOf(Object e) {
        if (this.size > 0) {
            Node currentNode = this.head;

            for (int i = 0; i < this.size; i++) {
                if (currentNode.getData().equals(e)) {
                    return i;
                }

                currentNode = currentNode.getNext();
            }
        }

        return -1;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public ImmutableLinkedList clear() {
        return new ImmutableLinkedList();
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public Object[] toArray() {
        Object[] arr = new Object[this.size];
        Node currentNode = this.head;

        for (int i = 0; i < this.size; i++) {
            arr[i] = currentNode.getData();
            currentNode = currentNode.getNext();
        }

        return arr;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Object[] arr = this.toArray();

        for (int i = 0; i < this.size; i++) {
            sb.append(arr[i]);

            if (i != this.size - 1) {
                sb.append(", ");
            }
        }

        return sb.toString();
    }

    public ImmutableLinkedList addFirst(Object e) {
        if (this.size == 0) {
            return this.add(e);
        }

        return this.add(0, e);
    }

    public ImmutableLinkedList addLast(Object e) {
        return this.add(e);
    }

    public Object getFirst() {
        return this.get(0);
    }

    public Object getLast() {
        return this.get(this.size - 1);
    }

    public ImmutableLinkedList removeFirst() {
        return this.remove(0);
    }

    public ImmutableLinkedList removeLast() {
        return this.remove(this.size - 1);
    }
}
