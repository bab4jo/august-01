/**
 * BJ 1244 스위치켜고끄기
 *
 * 남자일때랑 여자일때를 나눠서 수행한다.
 * 남자일때는 for문을 돌면서 배열의 인덱스가 스위치 번호로 나눴을 때 나머지가 0인 경우에만 스위치 상태를 바꿔준다.
 * 이때 배열 인덱스는 0으로 시작함에 주의한다.
 * 여자일때는 우선 현재 인덱스의 스위치 상태를 바꾼 후
 * while문을 돌면서 왼쪽,오른쪽으로 비교할 갯수(diff)를 늘리면서 비교해준다.
 * 비교할때 숫자가 다르다면 while문을 종료하고 같다면 스위치 상태를 바꿔준다.
 * 이때 현재인덱스+비교할 갯수가 배열의 범위를 넘어간다면 while문을 종료한다.
 *
 * 메모리 14568 KB
 * 시간 140 ms
 *
 * @author 김내림
 */

package com.ssafy.algo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class bj_1244_스위치켜고끄기 {
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int S = Integer.parseInt(br.readLine()); // 스위치 개수

        int[] arr = new int[S]; // 스위치 상태 배열
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i=0; i<S; i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }

        int N = Integer.parseInt(br.readLine()); // 학생 수

        for(int index=1; index<=N; index++){ // 학생 수 만큼 반복
            st = new StringTokenizer(br.readLine());
            int gender = Integer.parseInt(st.nextToken()); // 학생 성별
            int num  = Integer.parseInt(st.nextToken()); // 스위치 번호

            if(gender==1){ // 남자일때
                for(int i=0; i<S; i++) { // 스위치 번호의 배수라면
                    if((i+1)%num == 0) { // 배열의 인덱스는 0부터 시작하므로
                        arr[i] = switchNum(arr[i]); // 숫자 반대로 바꿔줌
                    }
                }
            } else if(gender==2) { // 여자일때
                int temp = num-1; // 기준 인덱스, 배열의 인덱스는 1부터 시작하므로 -1해줌
                int diff = 1; // 비교할 길이
                arr[temp] = switchNum(arr[temp]); // 현재 스위치 바꿔줌
                while(true){
                    if(temp-diff<0 || temp+diff>=S) { // 비교할 길이가 0보다 작거나 배열의 크기보다 커지면 반복문 종료
                        break;
                    }
                    if(arr[temp-diff] == arr[temp+diff]) { // 같으면 값 바꿔줌
                        arr[temp - diff] = switchNum(arr[temp - diff]);
                        arr[temp + diff] = switchNum(arr[temp + diff]);
                        diff++; // 비교할 길이 늘려줌
                    } else { // 다르면 반복문 종료
                        break;
                    }
                }
            }
        }
        for(int i=0; i<S; i++) { // 출력
            System.out.print(arr[i] + " ");
            if((i+1)%20==0){ // 20개가 넘을 경우 줄바꿈
                System.out.println();
            }
        }
    }

    public static int switchNum(int n) { // 숫자 반대로 바꿔주는 함수
        if (n == 1) { // 1이면 0반환
            return 0;
        }
        return 1; // 아닌 경우 1반환
    }
}
