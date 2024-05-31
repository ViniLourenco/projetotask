package com.example;

import java.util.Scanner;

/**
 * Task here is to write a list. Each element must know the element before and
 * after it. Print out your list and them remove the element in the middle of
 * the list. Print out again.
 *
 */

class DoublyLinkedList {
    static class Node {
        int data;
        Node previous;
        Node next;

        Node(int data) {
            this.data = data;
            this.previous = null;
            this.next = null;
        }
    }

    private Node head;
    private Node tail;
    private int size;

    public DoublyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public void add(int data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.previous = tail;
            tail = newNode;
        }
        size++;
    }

    public void printList() {
        Node current = head;
        while (current != null) {
            System.out.print(current.data + " ");
            current = current.next;
        }
        System.out.println();
    }

    public void removeMiddle() {
        if (size == 0) return;

        Node middle = head;
        for (int i = 0; i < size / 2; i++) {
            middle = middle.next;
        }

        if (middle.previous != null) {
            middle.previous.next = middle.next;
        } else {
            head = middle.next;
        }

        if (middle.next != null) {
            middle.next.previous = middle.previous;
        } else {
            tail = middle.previous;
        }

        size--;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DoublyLinkedList list = new DoublyLinkedList();

        System.out.print("Enter the number of elements in the list: ");
        int n = sc.nextInt();
        System.out.print("Enter the elements of the list: ");

        for (int i = 0; i < n; i++) {
            int element = sc.nextInt();
            list.add(element);
        }

        // Print the original list
        System.out.print("\nOriginal list: ");
        list.printList();

        // Remove the middle element
        list.removeMiddle();

        // Print the modified list
        System.out.print("List after removing the middle element: ");
        list.printList();

        sc.close();
    }
}