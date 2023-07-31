import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 백준 27501 RGB트리
 * @author youngeun
 * 397252 KB
 * 2340 ms
 *
 * DP + 트리 문제
 *
 * 1. DP로 가장 점수 높은 경우 구하기 -> 리프 노드부터
 * - 점화식
 * - r_dp[node] = max(g_dp[child_node], b_dp[child_node])
 * - g_dp[node] = max(r_dp[child_node], b_dp[child_node])
 * - b_dp[node] = max(g_dp[child_node], r_dp[child_node])
 *
 * 2. 가장 최적의 값이 나올 수 있는 경로 찾기 -> 루트 노드부터
 * - 루트 노드의 값 구하기 -> 1번 과정으로 도출된 dp[root] 결과 중 최대값이 루트 노드 결정값
 * - 루트의 자식들 -> 루트에서 결정된 색을 제외하고 두 색 중 더 큰 값이 결정값
 * - 이걸 DFS 돌면서 리프까지 구하면 됨
 */
public class RGBTree {
    static int N;
    static Map<Integer, List<Integer>> node_dict;
    static int[] r_dp, g_dp, b_dp; // dp 배열 -> 각 단계에서의 색깔 별 최대값 저장
    static boolean[] visited; // DFS 방문여부 체크용
    static char[] colors; // 결과 전구색 리스트 출력용

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        node_dict = new HashMap<>();

        for (int i = 0; i < N - 1; i++) { // 트리 map 형태로 저장
            String[] input = br.readLine().split(" ");
            int p = Integer.parseInt(input[0]);
            int c = Integer.parseInt(input[1]);

            node_dict.putIfAbsent(p, new ArrayList<>());
            node_dict.putIfAbsent(c, new ArrayList<>());
            // 트리의 루트를 지정해주지 않았으므로 양방향으로 저장
            node_dict.get(p).add(c);
            node_dict.get(c).add(p);
        }

        r_dp = new int[N + 1]; // 빨강 선택
        g_dp = new int[N + 1]; // 초록 선택
        b_dp = new int[N + 1]; // 파랑 선택
        visited = new boolean[N + 1];
        colors = new char[N + 1];

        for (int i = 1; i <= N; i++) { // 각 전구 당 색깔 별 cost
            String[] input = br.readLine().split(" ");
            r_dp[i] = Integer.parseInt(input[0]);
            g_dp[i] = Integer.parseInt(input[1]);
            b_dp[i] = Integer.parseInt(input[2]);
        }

        DFS(1); // 임의로 루트노드 하나 넣어버리기(뭘 넣어도 무관함..)
        int max_beauty = Math.max(r_dp[1], Math.max(g_dp[1], b_dp[1])); // 최종 최대 아름다움의 합
        System.out.println(max_beauty);

        if (max_beauty == r_dp[1]) { // 루트 노드가 빨강인 경우 최대 아름다움
            colors[1] = 'R';
        } else if (max_beauty == g_dp[1]) { // 루트 노드가 초록인 경우 최대 아름다움
            colors[1] = 'G';
        } else { // 루트 노드가 파랑인 경우 최대 아름다움
            colors[1] = 'B';
        }

        visited = new boolean[N + 1]; // 역으로 DFS 돌거임 -> 방문배열 다시 초기화
        getOrder(1, '0'); // 루트 노드는 어차피 위에서 저장했으니까 아무 값이나 넣어도 됨
        System.out.println(new String(colors, 1, N));
    }

    /**
     * 리프 노드부터 재귀 돌면서 최대 아름다움 구하는 메소드
     * @param node
     */
    static void DFS(int node) {
        visited[node] = true;
        if(node_dict.containsKey(node)){ // NPE 방지 (N=1인 경우 있음)
            for (int c_node : node_dict.get(node)) {
                if (!visited[c_node]) {
                    DFS(c_node); // 재귀로 DFS 구현함
                    // 점화식: 해당 색 제외하고 자식 노드 중 더 큰 값 더하기
                    r_dp[node] += Math.max(g_dp[c_node], b_dp[c_node]);
                    g_dp[node] += Math.max(r_dp[c_node], b_dp[c_node]);
                    b_dp[node] += Math.max(r_dp[c_node], g_dp[c_node]);
                }
            }
        }
    }

    /**
     * 루트 노드부터 최대 아름다움 결과 나오게 된 색깔 조합 저장
     * @param node
     * @param prev // 인접 노드와 색 겹치지 않으므로 prev 색은 제외한 최대값이 해당 노드의 색
     */
    static void getOrder(int node, char prev) {
        visited[node] = true;
        if (prev == 'R') { // 부모 노드가 R이면 자식은 G, B 중 최대값
            if (g_dp[node] > b_dp[node]) {
                colors[node] = 'G';
            } else {
                colors[node] = 'B';
            }
        } else if (prev == 'G') { // 부모 노드가 G이면 자식은 R, B 중 최대값
            if (r_dp[node] > b_dp[node]) {
                colors[node] = 'R';
            } else {
                colors[node] = 'B';
            }
        } else if (prev == 'B') { // 부모 노드가 B면 자식은 G, R 중 최대값
            if (r_dp[node] > g_dp[node]) {
                colors[node] = 'R';
            } else {
                colors[node] = 'G';
            }
        }
        if(node_dict.containsKey(node)) { // NPE 방지용
            for (int c_node : node_dict.get(node)) {
                if (!visited[c_node]) { // 자식 노드 넣어서 다시 재귀 돌리기
                    getOrder(c_node, colors[node]);
                }
            }
        }
    }
}
