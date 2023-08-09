import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * 백준 2042 구간 합 구하기
 * @author youngeun
 * 170,544 KB	
 * 1,232 ms
 * 
 * 세그먼트 트리
 * 구간합인데 변경이 빈번 -> 세그먼트 트리를 쓰자
 * 일반 구간합은 변경 시 O(N)... 그러나 세그먼트 트리는 조상 노드만 변경하기 때문에 O(lgN)
 * 
 */
public class BJ_2042_구간합구하기_신영은 {
	
	static int N;
	static int M;
	static int K;
	
	static long[] tree;
	static int[] leftIdx;
	static int[] rightIdx;
	static int[] leafIdx;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		tree = new long[N*2]; // 리프노드 갯수를 기준으로 트리 전체 노드 갯수 구하기 -> N*2 - 1
		leftIdx = new int[N*2]; // 해당 노드가 포함하고 있는 구간
		rightIdx = new int[N*2]; // 해당 노드가 포함하고 있는 구간
		leafIdx = new int[N+1]; // 숫자 바꿀 때 변경해야하는 위치의 배열 상 인덱스 저장
		
		// 리프노드 저장
		// DFS로 트리 타고 들어가면서 리프노드인 경우에 입력 들어온 값 넣기
        Stack<Integer> stack = new Stack<>();
        stack.add(1); // root
        int node;
        int cnt = 1; // 몇번째 인덱스인지 알려줌
        while(!stack.empty()) {
        	node = stack.pop();
        	if(node >= N) { // 리프노드 도착
        		tree[node] = Long.parseLong(br.readLine());
    			// 리프 노드는 구간 시작~끝 동일
        		leafIdx[cnt] = node;
    			leftIdx[node] = cnt; 
    			rightIdx[node] = cnt++; 
        	}else {
        		if(node*2+1 < N*2) {
            		stack.add(node*2+1);
        		}
        		if(node*2 < N*2) {
            		stack.add(node*2);
        		}
        	}
        }
		// 세그먼트 트리로 초기 누적값 구하기
		// 부모 노드에 양 리프의 누적합 저장 -> 루트까지 반복
		for(int i = N-1; i > 0; i--) {
			tree[i] = tree[i*2] + tree[i*2+1];
			leftIdx[i] = leftIdx[i*2]; // 부모 노드는 왼쪽 자식 시작 ~
			rightIdx[i] = rightIdx[i*2+1]; // ~ 오른쪽 자식 끝
		}
		
		// 명령어 처리
		int op1, op2;
		long n, change;
		for(int i = 0; i < M + K; i++) {
			st = new StringTokenizer(br.readLine());
			switch (st.nextToken()) {
			case "1": // 숫자 바꾼 후 세그먼트 트리 다시 구현
				op1 = leafIdx[Integer.parseInt(st.nextToken())]; // 리프노드 위치로 인덱스 수정
				n = Long.parseLong(st.nextToken());
				change = n - tree[op1];
				tree[op1] = n;
				// 부모 노드에 바뀐 값 반영 -> 전체 바꿀 필요 없이 바뀐 노드의 조상들만 바꾸면 된다
				for(int j = op1/2; j > 0; j/=2) {
					tree[j]+=change;
				}
				break;
			case "2": // 구간 합 출력
				op1 = Integer.parseInt(st.nextToken()); // 구간 시작
				op2 = Integer.parseInt(st.nextToken()); // 구간 끝
				System.out.println(query(1, op1, op2)); // root부터 쿼리 날리기
				break;
			}
		}
	}
	static long query(int node, int start, int end) { // start ~ end: 목표 누적 합 범위
		if(node > N*2) {
			return 0;
		}
		// 해당 노드가 가지고 있는 범위
		int left = leftIdx[node];
		int right = rightIdx[node];
		if(right < start || end < left) { // 노드가 지정된 범위 밖임
			return 0;
		}
		if(left >= start && right <= end) { // 노드가 지정된 범위 안에 있음
			return tree[node];
		}
		// 노드가 지정된 범위에 걸쳐져있음 -> 재귀로 범위 쪼개기
		return query(node*2, start, end) + query(node*2+1, start, end); // 양 자식들에게 넘기기
	}
}
