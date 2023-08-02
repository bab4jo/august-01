import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * BJ 12865 평범한 배낭
 * 0-1 knapsack 문제
 * @author youngeun
 *
 * 53824 KB
 * 200 ms
 *
 * 매우 대표적인 DP 문제 유형 (물건을 쪼갤 수 없는  0-1 knapsack)
 * 배낭 무게를 1~k까지 점점 늘려가면서 해당 무게에서 담을 수 있는 최적의 값 구하기
 * 이때 물건 여러개에 대한 계산을 해야하므로 물건 순회하면서 가방 크기 1~k 늘려서 넣기
 *
 * 점화식
 * - 너무 무거워서 배낭(j)에 안들어가..
 *   - 이전 물건(i-1) 텀에서 계산한 결과 복붙
 * - 물건 배낭(j)에 넣을 수 있어!
 *   - 이전 물건(i-1)까지 계산한 최대 가격 vs 현재 물건(i) + 현재 물건의 무게만큼 제외한 배낭의 최대 가격
 * - DP[i][j] = Math.max(DP[i-1][j], DP[i-1][j - weight[i]] + cost[i]);
 *
 */
public class Knapsack {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken()); // 물건 개수
        int K = Integer.parseInt(st.nextToken()); // 배낭 용량

        int[][] DP = new int[N+1][K+1]; // 각 배낭 크기마다 담을 수 있는 최고 가격 저장할 dp배열

        int[] weight = new int[N+1];
        int[] cost = new int[N+1];
        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            weight[i] = Integer.parseInt(st.nextToken());
            cost[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 1; i <= N; i++) { // 물건
            for (int j = 1; j <= K; j++) { // 배낭 용량 늘려나가면서 각 용량마다 최적값 구하기(1 ~ K)
                if (weight[i] > j){ // 물건 i가 배낭(j)보다 무거우면
                    DP[i][j] = DP[i-1][j]; // 더이상 담지 말고 이전 물건(i-1) Loop 때 값 들고오기
                }
                else { // 가방 j에 물건 i 담을 수 있어
                    // 이전 물건(i-1)까지 계산한 최대 가격 vs 현재 물건(i) + 현재 물건의 무게만큼 제외한 배낭의 최대 가격
                    DP[i][j] = Math.max(DP[i-1][j], DP[i-1][j - weight[i]] + cost[i]);
                }
            }
        }
        System.out.println(DP[N][K]); // N개의 물건 다 돌았을 때 용량 K인 배낭의 최적값 출력
    }
}

