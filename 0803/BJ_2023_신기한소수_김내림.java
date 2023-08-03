/**
 * BJ 2023 신기한소수
 *
 * [힌트] 소수 위키 펼쳐놓고 아이디어를 떠올려라
 * 영은언니의 도움을 받아 완성할 수 있었다.
 * 먼저 큐에 1자리수 소수를 넣어놓고 시작하면 된다.
 * 한자리수씩 늘려가면서 소수인지 아닌지 판별해주고 만들어야하는 자리수가 되면 출력해준다.
 *
 * 메모리 14296 KB
 * 시간 132 ms
 *
 * @author 김내림
 */
package com.ssafy.algo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class BJ_2023_신기한소수_김내림 {
    public static int N;
    public static Queue<Integer> queue;
    public static void main(String[] args) throws Exception {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(bf.readLine());

        queue = new LinkedList<>();
        // 큐에 소수 넣기
        queue.add(2);
        queue.add(3);
        queue.add(5);
        queue.add(7);

        if(N==1) { // 1자리수일때 그냥 출력
            System.out.printf("%d%n%d%n%d%n%d", 2,3,5,7);
            return;
        }

        int range = (int)Math.pow(10, N-1); // 만들어야 하는 자릿수의 최솟값

        int[] numbers = {1,3,7,9}; // 큐에 들어갈 수 있는 수

        while (queue.size()>0) { // 큐의 사이즈가 0이 아닐때까지 반복
            int p = queue.poll(); // 큐에서 하나 뽑아줌
            int num = 0; // 만든 숫자를 저장할 변수
            for(int i=0; i<4; i++) {
                num = p*10 + numbers[i]; // 숫자 만들어줌
                if(isPrime(num)) { // 소수일때
                    if(num < range) { // 만들어야하는 자릿수가 아니라면 다시 큐에 넣어줌
                        queue.add(num);
                    } else { // 만들어야하는 자릿수라면 출력
                        System.out.println(num);
                    }
                }
            }
        }
    }

    public static boolean isPrime(int num) { // 소수인지 확인하는 함수
        if (num < 2) return false;
        int n = (int)Math.sqrt(num); // 소수인지 확인할 숫자 제곱근값, int로 형변환
        for(int i=2; i<=n; i++) {
            if (num % i == 0) { // 나머지가 0이면 소수 아님
                return false;
            }
        }
        return true;
    }
}
