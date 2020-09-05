/*
Joseph McDonough
CMPT 435
Professor Labouseur
18 September 2020
 */

public class Assignment1 {
    public static void main(String[] args) {
        System.out.println(1 + 1);
        Stack s = new Stack();
        Node n1 = new Node("alpha");

        Node n2 = new Node("bravo");

        Node n3 = new Node("charlie");

        n1.nextNode(n2);
        n2.nextNode(n3);

        s.push("n1");
        System.out.println(s.pop().data);
        s.push("n2");
        System.out.println(s.pop().data);
        s.push("n3");
        System.out.println(s.pop());System.out.println(s.pop());System.out.println(s.pop());
    }
}

