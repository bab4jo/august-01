/**
프로그래머스 최댓값과 최솟값

String으로 되어있는 문자열을 공백을 기준으로 잘라서 배열에 저장한다.
배열을 순회하면서 최솟값과 최대값을 찾는다.

@author 김내림
*/
class Solution {
    public String solution(String s) {
        String answer = "";
        String[] arr = s.split(" "); // 공백 기준으로 자르기
        
        String max = arr[0]; // 최대값 저장할 변수
        String min = arr[0]; // 최솟값 저장할 변수
        
        for(int i=1; i<arr.length; i++){
            if(Integer.parseInt(arr[i]) > Integer.parseInt(max)) { // int형으로 형변환한 후 값 비교, 최대값 찾기
                max = arr[i];
            }
            if(Integer.parseInt(arr[i]) < Integer.parseInt(min)) { // int형으로 형변환한 후 값 비교, 최솟값 찾기
                min = arr[i];
            }
        }
        
        answer = min + " " + max; // 정답 문자열에 최솟값, 최대값 더해주기
        
        return answer;
    }
}