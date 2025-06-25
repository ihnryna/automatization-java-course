package org.example;

/**
 * MyLinkedList for Practice 13
 * File: MyLinkedList.java
 *
 * @author Iryna Hryshchenko
 */
public class MyLinkedList<T> {

    /**
     * Node
     * @param <A> - type
     */
    private class Node<A>{
        private Node<A> previous;
        private Node<A> next;
        private A me;

        /**
         * Node constructor
         * @param data - data
         */
        private Node(A data){
            this.me = data;
        }
    }

    private Node<T> first;
    private Node<T> last;
    public int size;

    /**
     * MyLinkedList constructor
     */
    MyLinkedList(){
        first = null;
        last = null;
        size = 0;
    }

    /**
     * Adding first node to list
     * @param nodeData - data
     */
    public void addFirst(T nodeData){
        if (first == null){
            first = new Node<T>(nodeData);
            last = first;
            first.next = null;
            first.previous = null;
        }
        else{
            Node<T> temp = new Node<T>(nodeData);
            first.previous = temp;
            temp.next = first;
            temp.previous = null;
            first = temp;
        }
        size++;
    }

    /**
     * Adding last node to list
     * @param nodeData - data
     */
    public void addLast(T nodeData){
        if (last == null){
            first = new Node<T>(nodeData);
            last = first;
            first.next = null;
            first.previous = null;
        }
        else{
            Node<T> temp = new Node<T>(nodeData);
            last.next = temp;
            temp.next = null;
            temp.previous = last;
            last = temp;
        }
        size++;
    }

    /**
     * Adding node to list by position
     * @param nodeData - data
     * @param pos - position
     */
    public void add(T nodeData,int pos){
        if (first == null){
            first = new Node<T>(nodeData);
            last = first;
            first.next = null;
            first.previous = null;
            size++;
        } else if (pos == size){
            addLast(nodeData);
        } else if (pos == 0){
            addFirst(nodeData);
        } else{
            Node<T> searcher = first;
            for(int i = 0; i<pos ; i++){
                searcher = searcher.next;
            }
            Node<T> temp = new Node<T>(nodeData);
            temp.previous = searcher.previous;
            searcher.previous = temp;
            temp.next = temp.previous.next;
            temp.previous.next = temp;
            size++;
        }
    }

    /**
     * Removing first node from list
     */
    public void removeFirst(){
        if (first != null){
            first = first.next;
            if (first != null){
                first.previous = null;
            }
            size--;
        }
    }

    /**
     * Removing last node from list
     */
    public void removeLast(){
        if (last != null){
            last = last.previous;
            if (last != null){
                last.next = null;
            }
            size--;
        }
    }

    /**
     * Removing node from list by position
     * @param pos - position
     */
    public void remove(int pos){
        if (first != null) {
            if (pos == size-1) {
                removeLast();
            } else if (pos == 0) {
                removeFirst();
            } else {
                Node<T> searcher = first;
                for (int i = 0; i < pos; i++) {
                    searcher = searcher.next;
                }
                searcher.previous.next = searcher.next;
                searcher.next.previous = searcher.previous;
                size--;
            }
        }
    }

    /**
     * Getting first node data
     * @return - first node data
     */
    public T getFirst(){
        return first.me;
    }

    /**
     * Getting last node data
     * @return - last node data
     */
    public T getLast(){
        return last.me;
    }

    /**
     * Getting node data by position
     * @param pos - position
     * @return - node data
     */
    public T get(int pos){
        Node<T> searcher = first;
        for (int i = 0; i < pos; i++) {
            searcher = searcher.next;
        }
        return searcher.me;
    }

    /**
     * Make a string from list
     * @return - stringed list
     */
    public String toString(){
        Node<T> temp = first;
        String res = "[ ";
        while (!(temp== null)){
            res = res + temp.me + " ";
            temp = temp.next;
        }
        return res+"]";
    }

}