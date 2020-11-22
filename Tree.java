package com.company;

import java.util.Scanner;
import java.util.Stack;

public class Tree {
    private class Node {
        int data;
        Node left;
        Node right;
        boolean hasLeft;
        boolean hasRight;

        //конструктор
        public Node(int data, Node left, Node right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }
    }

    Node root = null;
    Node head;  //дополнительный элемент для прошивки(в ссылку на левого потомка запишем ук-ль на корень, а в правого - самого себя)
    Node y; //указатель на предыдущий элемент(будем использовать при прошивке)

    //метод вставки в непрошитое дерево
    //будем использовать в начале программы при создании дерева
    public void add(int data) {
        //создаем новый узел
        Node node = new Node(data, null, null);
        //если дерево пустое, то запишем новый узел в качестве корня
        if (root == null) {
            root = node;
            return;
        }
        Node current = root;
        //итеративно найдем место для нового элемента
        while (true) {
            //если новый элемент меньше текущего и у текущего есть потомок, то перейдем к этому потомку
            if (data < current.data && current.left != null) {
                current = current.left;
                continue;
            }
            //аналогично предыдущему
            if (data >= current.data && current.right != null) {
                current = current.right;
                continue;
            }
            //если новый элемент меньше текущего и у текущего нет левого потомка
            //(если б он был, мы бы не дошли до этого, итерация завершилась бы выше),
            //запишем новый элемент в качестве левого потомка для текущего
            if (data < current.data) {
                current.left = node;
                //аналогично, если больше либо равен
            } else current.right = node;
            return;
        }
    }

    //-------------симметричная прошивка-----------------
    //прошивка дерева
    public void SymmetricSew() {
        //в дополнительный элемент запишем ссылку на левого потомка адрес корня, а в правого - самого себя
        head = new Node(0, root, null);
        head.right = head;
        //инициализируем указатель на предыдущий элемент
        y = head;
        //выполняем прошивку справа
        rightSymSew(root);
        y.right = head;
        y.hasRight = false;
        //возвращаем в указатель ссылку на доп. элемент, чтобы заново произвести прошивку
        //но уже с левой стороны
        y = head;
        //прошивка слева
        leftSymSew(root);
        y.left = head;
        y.hasLeft = false;

    }

    //метод правосторонней прошивки
    private void rightSymSew(Node x) {
        if (x != null) {
            rightSymSew(x.left);
            rightSymSew1(x);
            rightSymSew(x.right);
        }
    }

    private void rightSymSew1(Node p) {
        if (y != null) {
            if (y.right == null) {
                y.hasRight = false;
                y.right = p;
            } else {
                y.hasRight = true;
            }
        }
        y = p;
    }

    //левосторонней
    private void leftSymSew(Node x) {
        if (x != null) {
            if (x.hasRight) leftSymSew(x.right);
            leftSymSew1(x);
            leftSymSew(x.left);
        }
    }

    private void leftSymSew1(Node p) {
        if (y != null) {
            if (y.left == null) {
                y.hasLeft = false;
                y.left = p;
            } else {
                y.hasLeft = true;
            }
        }
        y = p;
    }

    //симметричный обход симметрично прошитого дерева
    public void symmetricalPrint() {
        if (root==null){
            System.out.println("В дереве нет элементов.");
            return;
        }
        Node current = root;
        while (current != head) {
            while (current.left != null && current.hasLeft) current = current.left;
            System.out.print(current.data + " ");
            while (!current.hasRight && current.right != null) {
                current = current.right;
                if (current == head) {
                    System.out.println();
                    return;
                }
                System.out.print(current.data + " ");
            }
            current = current.right;
        }
        System.out.println();
    }

    //вставка в симметрично прошитое дерево
    public void insert() {
        System.out.println("Введите элемент.");
        Scanner scanner = new Scanner(System.in);
        while (!scanner.hasNextInt()) {
            System.out.println("Необходимо ввести целое число. Повторите ввод.");
            scanner.next();
        }
        insert1(scanner.nextInt());
    }

    public void insert1(int data) {
        Node current = root;
        Node prev = null;
        //ищем место для нового элемента
        while (current != null) {
            prev = current;
            if (data < current.data) {
                if (current.hasLeft)
                    current = current.left;
                else
                    break;
            } else {
                if (current.hasRight)
                    current = current.right;
                else
                    break;
            }
        }

        Node newNode = new Node(data, null, null);
        newNode.hasLeft = false;
        newNode.hasRight = false;
        //вставка
        if (prev == null) {
            root = newNode;
            newNode.left = null;
            newNode.right = null;
        } else if (data < (prev.data)) {
            newNode.left = prev.left;
            newNode.right = prev;
            prev.hasLeft = true;
            prev.left = newNode;
        } else {
            newNode.left = prev;
            newNode.right = prev.right;
            prev.hasRight = true;
            prev.right = newNode;
        }
    }

    //удаление из симметрично прошитого
    public void delete() {
        System.out.println("Введите элемент.");
        Scanner scanner = new Scanner(System.in);
        while (!scanner.hasNextInt()) {
            System.out.println("Необходимо ввести целое число. Повторите ввод.");
            scanner.next();
        }
        int data = scanner.nextInt();
        Node prev = null;
        Node curr = root;
        //проверим, есть ли такой элемент
        boolean exists = false;
        while (curr != null) {
            if (data == curr.data) {
                exists = true;
                break;
            }
            prev = curr;
            if (data < curr.data) {
                if (curr.hasLeft)
                    curr = curr.left;
                else
                    break;
            } else {
                if (curr.hasRight)
                    curr = curr.right;
                else
                    break;
            }
        }
        if (!exists) System.out.println("В дереве нет элемента " + data);
        else if (curr.hasLeft && curr.hasRight) delete2(curr);
        else if (curr.hasLeft || curr.hasRight) delete1(prev, curr);
        else delete0(prev, curr);
    }

    //удаление листа
    private void delete0(Node prev, Node curr) {
        //если в дереве 1 элемент, и этот элемент удаляют
        if (prev == null) root = null;
            //если удаляемый элемент - левый потомок для prev
        else if (curr == prev.left) {
            prev.hasLeft = false;
            prev.left = curr.left;
            //если удаляемый элемент - правый потомок для prev
        } else {
            prev.hasRight = false;
            prev.right = curr.right;
        }
    }

    //удаление элемента с одним потомком
    private void delete1(Node prev, Node curr) {
        Node child;
        //запишем потомка удаляемого элемента
        if (curr.hasLeft) child = curr.left;
        else child = curr.right;
        //если удаляем корень
        if (prev == null) root = child;
            //если удаляемый элемент - левый потомок для предыдущего, то запишем в левый потомок потомка удаляемого
        else if (curr == prev.left) prev.left = child;
            //аналогично, если правый
        else prev.right = child;

        //перезапишем нити с удаленного элемента
        Node curr2 = curr;
        Node s;
        if (!curr.hasRight) s = curr.right;
        else {
            curr = curr.right;
            while (curr.hasLeft) curr = curr.left;
            s = curr;
        }
        curr=curr2;
        Node p;
        if (!curr2.hasLeft) p = curr2.left;
        else {
            curr2 = curr2.left;
            while (curr2.hasRight) curr2 = curr2.right;
            p = curr2;
        }

        if (curr.hasLeft) p.right = s;
        else if (curr.hasRight) s.left = p;

    }

    //удаление элемента с двумя потомками
    private void delete2(Node curr) {
        Node prevSucc = curr;
        Node succ = curr.right;

        while (succ.hasLeft) {
            prevSucc = succ;
            succ = succ.left;
        }
        curr.data = succ.data;
        if (!succ.hasLeft && !succ.hasRight)
            delete0(prevSucc, succ);
        else
            delete1(prevSucc, succ);
    }
}
