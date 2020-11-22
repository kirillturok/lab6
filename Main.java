package com.company;


import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Tree tree = new Tree();
        System.out.println("Введите элементы дерева.");
        Scanner scan = new Scanner(System.in);
        String str = scan.nextLine();
        String[] num = str.split("\\s+");
        for (int i=0; i<num.length;i++){
            if (num[i].matches("\\d+")) {
                tree.add(Integer.parseInt(num[i]));
                //System.out.println(Integer.parseInt(num[i])+" ");
            }
        }
        //System.out.println("Работа с бинарным деревом поиска с симметричной прошивкой.");
        tree.SymmetricSew();

        int select;
        Scanner scanner = new Scanner(System.in);
        do{
            System.out.println("Введите\n1 - добавить элемент в прошитое дерево\n2 - удалить элемент из прошитого дерева\n3 - симметричный обход дерева\n0 - exit");
            while (!scanner.hasNextInt()){
                System.out.println("Введена неверная операция.\nПовторите ввод.");
                scanner.next();
            }
            select=scanner.nextInt();
            switch (select){
                case 1:{
                    tree.insert();
                    break;
                }
                case 2:{
                    tree.delete();
                    break;
                }
                case 3:{
                    tree.symmetricalPrint();
                    break;
                }
                case 0: System.exit(0);
                default:{
                    System.out.println("Такой операции нет.");
                    continue;
                }
            }
        }while(true);
    }
}
