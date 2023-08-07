import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

/**
 * 백준 16120 PPAP
 * @author youngeun
 * 24832 KB
 * 236 ms
 *
 * stack
 * 처음에는 문자열 replace로 해결하려 하였으나 시간초과 이슈 발생
 * PPAP의 규칙을 찾아봄
 *  - P = PPAP
 *  - PPAP = PPAP
 *  - (PPAP)PAP = (P)PAP = PPAP
 *  - P(PPAP)AP = P(P)AP = PPAP
 *  - PPA(PPAP) = PPA(P) = PPAP
 * => 모든 경우에 A 뒤에는 P가 나와야 함
 * => 모든 경우에 A 1개 당 자신보다 앞에는 P가 2개 있어야 함 (연속적이지 않아도 됨)
 *
 * 풀이법
 * Stack에 P 쌓아두기
 * 만약 A 만나면
 *  1. 다음에 P(AP): stack에서 P 2개 pop 가능한지
 *      i. stack 길이가 2 이상 -> P 2개 pop + PPAP를 P로 치환해서 push => P 1개 pop
 *          이 때 A 다음에 나오는 P는 이미 검증했으므로 인덱스 하나 더 넘기기
 *      ii. 2 이하 -> NP
 *  2. 다음에 A(AA): 무조건 불가능! NP
 *  문자열 끝까지 순회한 후 stack에 P가 2개 이상 남아있으면 NP
 *
 *  상기 모든 조건 통과 시 PPAP
 */
public class PPAP {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = br.readLine();

        Stack<Character> stack = new Stack<>();

        for(int i = 0; i < str.length(); i++){
            if(str.charAt(i) == 'P'){ // P인 경우 -> stack에 넣기
                stack.push('P');
            }else { // A인 경우
                // A로 끝나거나, A 다음에 A가 오거나, pop할 P가 2개 이하면 자동 탈락
                if(i < str.length()-1 && str.charAt(i+1) == 'P' && stack.size() >= 2){
                    stack.pop(); // 하나만 pop 하면 됨 -> 어차피 PPAP -> P로 치환되어 들어감
                    i++; // A 다음 P는 이미 검증했으니까 건너
                }else {
                    System.out.println("NP");
                    return;
                }
            }
        }
        if (stack.size() > 1){ // stack에 P가 2개 이상 있는 경우
            System.out.println("NP");
        }else{
            System.out.println("PPAP");
        }
    }
}
