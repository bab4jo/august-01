import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 백준 1516 게임 개발
 * @author youngeun
 *
 * DP + TopologicalSort(BFS)
 * TopSort by BFS
 * 나보다 전에 지어져야 하는 건물이 없거나, 모두 지어져있으면(inDegree == 0) 건물 짓기 가능 -> enque
 * 내가 지어져야하는 건물들의 inDegree 1씩 줄이기
 *
 * DP
 * 나보다 전에 지어져야 하는 건물 중 가장 오래 걸리는 건물의 값 누적 더하기
 */
public class DevelopGame {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        int[] inDegree = new int[N+1];
        Map<Integer, List<Integer>> incomeEdges = new HashMap<>();
        Map<Integer, List<Integer>> outcomeEdges = new HashMap<>();

        int[] dp = new int[N+1];
        StringTokenizer st;

        int cost;
        int building;
        for(int i = 1; i <= N; i++){
            st = new StringTokenizer(br.readLine());
            cost = Integer.parseInt(st.nextToken()); // 나의 가격
            dp[i] = cost; // 내 가격은 누적 가격에 필수로 더해지니까 미리 저장해놓기

            int cnt = 0;
            while (true){ // 나보다 전에 지어저야 하는 건물
                building = Integer.parseInt(st.nextToken());
                if(building == -1){
                    inDegree[i] = cnt; // 나보다 전에 지어져야 하는 건물 갯수
                    break;
                }else {
                    cnt+=1;
                    // outcomeEdges -> 내가 지어져야 지을 수 있는 건물 리스트
                    outcomeEdges.putIfAbsent(building, new ArrayList<Integer>());
                    outcomeEdges.get(building).add(i);
                    // incomeEdges -> 나보다 전에 지어져야 하는 건물 리스트
                    incomeEdges.putIfAbsent(i, new ArrayList<Integer>());
                    incomeEdges.get(i).add(building);
                }
            }
        }
        // 시작 지점 찾기 -> 들어오는 엣지 0인 노드
        Queue<Integer> queue = new LinkedList<>();
        for(int i = 1; i <= N; i++){
            if(inDegree[i] == 0){ // 나보다 전에 지어야하는 건물이 0개인 경우부터 시작
                queue.offer(i);
            }
        }

        // BFS
        while (queue.size() > 0){ // queue에 들어왔다는 것은 indegree = 0이 충족된 경우임
            int node = queue.poll();
            // 나보다 전에 지어져야 하는 건물 소요시간 중 최대값 더하기
            if (incomeEdges.containsKey(node)) {
                int time = 0;
                for (int pNode : incomeEdges.get(node)) {
                    time = Math.max(time, dp[pNode]);
                }
                dp[node] += time;
            }
            // 내가 지어져야만 짓기 시작할 수 있는 건물들과 이어진 엣지 제거, inDegree 감소
            if(outcomeEdges.containsKey(node)) {
                for (int cNode : outcomeEdges.get(node)) {
                    inDegree[cNode]--;
                    if (inDegree[cNode] == 0) { // 전에 지어져야 하는 건물들 다 지어짐
                        queue.offer(cNode);
                    }
                }
            }
        }
        for(int i = 1; i < N+1; i++){
            System.out.println(dp[i]);
        }
    }
}
