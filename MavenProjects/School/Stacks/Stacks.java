import java.util.Scanner;
import java.util.Stack;

/**
 * Overflow
 */
public class Stacks {
    public static void main(String[] args) {
        lab1("a b c d e f g h i");
        lab1("1 2 3 4 5 6 7 8 9 10");
        lab1("# $ % ^ * ( ) ) _");

        lab2("(abc(*def)");
        lab2("[{}]");
        lab2("[");
        lab2("[{<()>}]");
        lab2("{<html[value=4]*(12)>{$x}}");
        lab2("[one]<two>{three}(four)");
        lab2("car(cdr(a)(b)))");
        lab2("car(cdr(a)(b))");
        System.out.println("\n\n");
        lab3("2 7 + 1 2 + +");
        lab3("1 2 3 4 + + +");
        lab3("9 3 * 8 / 4 +");
        lab3("3 3 + 7 * 9 2 / +");
        lab3("9 3 / 2 * 7 9 * + 4 –");
        lab3("5 5 + 2 * 4 / 9 +");
        System.out.println("\n\n");
        MyStack ms = new MyStack();
        ms.push(5);
        ms.push(7);
        ms.push(9);
        System.out.println(ms.isEmpty());
        System.out.println(ms.pop());
        System.out.println(ms.peek());
        System.out.println(ms.pop());
        System.out.println(ms.pop());
        System.out.println(ms.isEmpty());

    }

    private static void lab3(String in) {
        Scanner scan = new Scanner(in);
        Stack<Float> stack = new Stack<>();
        while (scan.hasNext()) {
            String s = scan.next();
            switch (s) {
                case "+":
                    stack.push(stack.pop() + stack.pop());
                    break;
                case "–":
                    float two = stack.pop();
                    float one = stack.pop();
                    stack.push(one - two);
                    break;
                case "*":
                    stack.push(stack.pop() * stack.pop());
                    break;
                case "/":
                    float twoo = stack.pop();
                    float onne = stack.pop();
                    stack.push(onne / twoo);
                    break;
                case "%":
                    stack.push(stack.pop() % stack.pop());
                    break;
                default:
                    float i = Float.parseFloat(s);
                    stack.push(i);
                }
        }
        System.out.println(in + " = " + stack.pop());
    }

    private static void lab2(String in) {
        Scanner scan = new Scanner(in);
        Stack<String> stack = new Stack<>();
        scan.useDelimiter("");
        boolean correct = true;
        while (scan.hasNext()) {
            char c = scan.next().charAt(0);
            if (c == '(' || c == '{' || c == '[' || c == '<') {
                stack.push(c + "");
            } else if (c == ')' || c == '}' || c == ']' || c == '>') {
                if (stack.size() < 1) {
                    correct = false;
                    break;
                }
                switch (c) {
                case ')':
                    correct = stack.pop().equals("(");
                    break;
                case '}':
                    correct = stack.pop().equals("{");
                    break;
                case ']':
                    correct = stack.pop().equals("[");
                    break;
                case '>':
                    correct = stack.pop().equals("<");
                    break;
                }
                if (!correct) {
                    break;
                }
            }
        }
        if (stack.size() > 0) {
            correct = false;
        }
        if (correct) {
            System.out.println(in + " is correct.");
        } else {
            System.out.println(in + " is incorrect.");
        }
    }

    public static void lab1(String in) {
        Stack<String> stack = new Stack<>();
        for (String s : in.split(" ")) {
            stack.push(s);
        }
        System.out.println(stack);
        System.out.println("popping all items from the stack");
        while (!stack.isEmpty()) {
            System.out.print(stack.pop() + " ");
        }
        System.out.println("\n\n");
    }
}