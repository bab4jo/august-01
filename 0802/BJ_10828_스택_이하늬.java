package baekjoon.problem10828;

import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < n; i++) {
            String str = br.readLine();
            solution(str, stack);
        }
    }

    private static void solution(String str, Stack stack) {
        StringTokenizer st = new StringTokenizer(str);
        String command = st.nextToken();
        switch (command) {
            case "push":
                int num = Integer.parseInt(st.nextToken());
                stack.push(num);
                break;

            case "pop":
                if (stack.isEmpty()) {
                    System.out.println(-1);
                } else {
                    System.out.println(stack.pop());
                }
                break;

            case "size":
                System.out.println(stack.size());
                break;

            case "empty":
                if (stack.isEmpty()) {
                    System.out.println(1);
                } else {
                    System.out.println(0);
                }
                break;

            case "top":
                if (stack.isEmpty()) {
                    System.out.println(-1);
                } else {
                    System.out.println(stack.peek());
                }
                break;

        }
    }
}
