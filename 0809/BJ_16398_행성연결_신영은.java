import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 백준 16398 행성연결
 * @author youngeun
 *
 * 184,184 KB
 * 1,000 ms
 *
 * <MST>
 * 1. 사이클이 생기지 않으면서(Tree)
 * 2. 최소 cost 간선으로 이루어져야 함
 * 구현 -> Prim's Algo 써서 MST 구현
 */
public class ConnectPlanet {
    static  int N;
    static boolean[] visited;
    static Map<Integer, List<Node>> graph = new HashMap<>();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        N = Integer.parseInt(br.readLine());
        // Map 자료구조로 그래프 저장
        for(int i = 1; i <= N; i++){
            st = new StringTokenizer(br.readLine());
            List<Node> edges = new ArrayList<>();
            for(int j = 1; j <= N; j++) {
                if(i == j){ // 나 자신으로 가는 엣지는 없음(입력 0으로 들어옴)
                    st.nextToken();
                    continue;
                }
                edges.add(new Node(j, Integer.parseInt(st.nextToken())));
            }
            graph.put(i, edges);
        }
        visited = new boolean[N+1];
        // Prim's Algo
        // Node 1번부터 시작해서 우선순위 queue에 넣으면서 현재 방문한 모든 노드에서 최소 엣지 찾기
        long answer = 0;
        PriorityQueue<Node> queue = new PriorityQueue<>(); // 최소 cost 간선 고르기 위해 내부적으로 minHeap 구현되어있는 priorityQueue 사용
        queue.add(new Node(1, 0));
        Node node;
        int cost, to;
        while (!queue.isEmpty()){
            node = queue.poll(); // 우선순위 큐라서 가장 cost 작은 엣지 나온다.
            to = node.to;
            cost = node.cost;
            if(visited[to]){
                continue;
            }
            visited[to] = true; // 방문처리
            answer+=cost; // 엣지 추가됨
            if(graph.containsKey(to)){ // NPE 방지
                for(Node n : graph.get(to)) {
                    if (!visited[n.to]) { // 방문하지 않은 노드로 가는 엣지면 큐에 추가
                        queue.add(n);
                    }
                }
            }
        }

        System.out.println(answer);
    }
}

/**
 * 우선순위 큐 정렬 기준을 위해 Comparable 구현함
 */
class Node implements Comparable<Node>{
    int to;
    int cost;

    public Node(int to, int cost) {
        this.to = to;
        this.cost = cost;
    }

    @Override
    public int compareTo(Node o) {
        return this.cost > o.cost ? 1 : -1;
    }
}

