import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 백준 13325 이진 트리
 * @author youngeun
 * 228052 KB
 * 708 ms
 *
 * <세그먼트 트리 + DFS>
 * 1. 가능한 최대 가중치 합 루트를 세그먼트 트리로 구함
 *  - 세그먼트 트리를 사용한 이유는 데이터 구간합에 용이하기 때문
 *  - 루트노드 뿐만 아니라 각 노드에 대한 최대 가중치를 저장해 둠
 *  - sumTree에 자식의 값 중 최대값 + 자신의 값 저장함
 *
 * 2. DFS로 sumTree 순회하면서, 각 노드 별 필요한 추가 가중치를 업데이트
 *  - [최대값 - (자신의 가중치 + 조상 노드의 가중치)] 만큼 추가해야함.
 *  - 이 때 부모 노드의 값이 변화하면 자식 노드는 부모가 변화한 만큼은 가중치 추가할 필요가 없다
 *  - 그러므로, 자신의 부모 ~ 루트노드 까지의 조상 노드의 가중치를 넘겨줌
 *  - 재귀 대신 Node 클래스를 통해 넘겨주었다.
 */
public class BinaryTree {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int H = Integer.parseInt(br.readLine());
        int nodes = (int) Math.pow(2, H+1) - 1; // 노드 개수 = 2^(H+1)-1
        int[] tree = new int[nodes + 1]; // 노드 개수 + 1 만큼 트리 배열 만들기 (인덱스 1부터 시작)

        StringTokenizer st = new StringTokenizer(br.readLine());
        int answer = 0;
        for(int i = 2; i < nodes+1; i++){ // 루트노드(1) 제외해야하므로 2부터 담기
            tree[i] = Integer.parseInt(st.nextToken());
            answer += tree[i]; // 기존 엣지 총합 적어두기 -> 나중에 엣지 가중치 추가된 만큼만 더하면 됨
        }

        // 리프부터 올라오면서, 각 노드까지 도달하는데 최대 간선 합 구하기
        // 세그먼트 트리 사용함
        int[] sumTree = new int[nodes + 1];
        for(int i = nodes; i > 0; i--){
            if(i >= (nodes+1)/2){ // 리프노드면 그냥 넣기
                sumTree[i] = tree[i];
                continue;
            }
            sumTree[i] = Math.max(sumTree[i*2], sumTree[i*2+1]) + tree[i]; // 자식 중 최대값 + 자신
        }
        int maxLeaf = sumTree[1]; // root node에 있는 값이 최대값임

        // maxLeaf 충족하기 위해 필요한 만큼 더하기
        // DFS 씀
        Stack<Node> stack = new Stack<>();
        int add;
        stack.push(new Node(1, 0, 0)); // root 노드부터 시작 (노드 번호, 깊이, 조상 노드들의 엣지 가중치 합)
        while (stack.size() > 0){
            Node node = stack.pop();
            add = maxLeaf - (node.cost + sumTree[node.n]); // 최대 간선 합 - (조상 노드 합 + 현재 노드 값)
//            System.out.println(node.n + ": " + add);
            answer += add; // 이미 초기 간선 값을 저장해뒀기 때문에 변화한 만큼만 추가하면 됨
            if(node.height < H){
                int child = node.n * 2;
                stack.push(new Node(child,node.height + 1,  node.cost + tree[node.n] + add)); // 조상 노드 합을 Node.cost로 넘겨줌
                stack.push(new Node(child+1,node.height + 1, node.cost + tree[node.n] + add)); // Node 클래스를 써서 필드를 일종의 파라미터 역할로 썼다.
            }
        }
        System.out.println(answer);

    }
    static class Node {
        int n;
        int height;
        int cost;
        public Node(int n, int height, int cost){
            this.n = n;
            this.height = height;
            this.cost = cost;
        }
    }
}

