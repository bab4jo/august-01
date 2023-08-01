package ssafy.hw0801;

import java.io.*;
import java.util.*;

/**
 * @author 이하늬
 * 
 *         <pre>
 *         문제 - BJ 1244 스위치 켜고 끄기
 *         아이디어
 *         - 남학생일 경우 자기가 받은 수의 배수들의 스위치 상태를 바꿈
 *             -> 반복문을 돌며 스위치 번호 % 남학생이 받은 수 == 0 일 경우 상태 바꿈
 *         - 여학생일 경우 자기가 받은 수의 스위치를 기준으로 가장 긴 좌우대칭인 구간을 찾아 스위치 상태를 바꿈
 *             -> 여학생 스위치 번호 -1이 0보다 크거나 같을 경우 && 여학생 스위치 번호 +1이 스위치 배열 길이보다 적을 경우에만
 *                 비교 하여 같을 시에 상태 바꿈
 * 
 *         - 메모리: 14196KB, 시간: 124ms
 *         </pre>
 */

public class Main {
    public static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine()); // 스위치 개수
        int[] arr = new int[N]; // 스위치 상태를 저장하는 배열
        StringTokenizer st = new StringTokenizer(br.readLine()); // 스위치 string 받음
        for (int i = 0; i < N; i++)
            arr[i] = Integer.parseInt(st.nextToken());
        int S = Integer.parseInt(br.readLine()); // 학생수
        int[][] switchArr = new int[S][2];
        for (int i = 0; i < S; i++) {
            st = new StringTokenizer(br.readLine());
            switchArr[i][0] = Integer.parseInt(st.nextToken());
            switchArr[i][1] = Integer.parseInt(st.nextToken());
        }
        solution(arr, switchArr, 0);
        System.out.println(sb);
    }

    private static void solution(int[] arr, int[][] switchArr, int cnt) {
        if (cnt == switchArr.length) {
            for (int i = 0; i < arr.length; i++) {
                sb.append(arr[i]);
                sb.append(" ");
                if ((i + 1) % 20 == 0) {
                    sb.append("\n");
                }
            }
            return;
        }

        if (switchArr[cnt][0] == 1) { // 남학생일 경우
            int num = switchArr[cnt][1]; // 남학생이 받은 번호
            for (int i = 1; i * num - 1 < arr.length; i++) {
                switchStatus(arr, i * num - 1);
            }
        }
        if (switchArr[cnt][0] == 2) { // 여학생일 경우
            int num = switchArr[cnt][1] - 1; // 여학생이 받은 번호 (기준점)
            switchStatus(arr, num);
            // 여학생이받은번호-i 이 0보다 같거나 클 경우/여학생이받은번호+i가 스위치 배열의 길이보다 적은 경우까지만 수행
            for (int i = 1; num - i >= 0 && num + i < arr.length; i++) {
                if (arr[num - i] == arr[num + i]) { // 여학생이 받은 번호 기준으로 좌 우 스위치의 번호 비교했을 때 같을 경우
                    switchStatus(arr, num - i);
                    switchStatus(arr, num + i);
                } else {
                    break;
                }
            }
        }
        solution(arr, switchArr, cnt + 1);

    }

    private static void switchStatus(int[] arr, int idx) {
        arr[idx] = arr[idx] == 1 ? 0 : 1; // 1일 경우 0, 0일 경우 1으로 바꿔줌
    }
}
