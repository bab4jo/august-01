import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 백준 14500 테트로미노
 * 삼성 SW 역량평가 기출
 * @author youngeun
 *
 * 33724 KB
 * 740 ms
 *
 * DFS로 완전탐색
 * (4 ≤ N, M ≤ 500) 이므로 격자 완탐하면 500*500 -> 250,000
 * => 완탐으로 충분히 수행 가능한 문제
 * 
 * 4칸으로 된 블록 -> 4칸짜리 DFS 돌리는 것과 같다
 * 1 2 3 4      1 2         1 2         1 2 3
 *                3 4       4 3             4
 * 단 ㅜ 모양은  1 2 3 모양이라 2번에서 한번 더 분기해줘야 함
 *              3
 * 시간복잡도 O(N*M) -> 격자 하나씩 다 탐색
 */
public class Tetromino {

    static int max = Integer.MIN_VALUE;
    static int[][] numbers;
    static boolean[][] visited;
    static int N;
    static int M;

    // 상하좌우
    static int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        numbers = new int[N][M];
        visited = new boolean[N][M];

        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < M; j++) {
                numbers[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // DFS
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < M; j++) {
                visited[i][j] = true;
                DFS(i,j, numbers[i][j],1);
                visited[i][j] = false;
            }
        }

        System.out.println(max);
    }

    static void DFS(int row, int col, int sum, int cnt) {
        if(cnt == 4) { // 4칸짜리 테트로미노 완성!
            max = Math.max(max, sum);
            return;
        }
        // 상하좌우 탐색
        for(int i = 0; i < 4; i++) {
            int newRow = row + dirs[i][0];
            int newCol = col + dirs[i][1];
            // 배열 범위 내 + 방문 X
            if(newRow >= 0 && newRow < N && newCol >= 0 && newCol < M && !visited[newRow][newCol]) {
                if(cnt == 2) { // 보라색(ㅜ) 테트로미노 -> 얘는 2번째에서 분기해야하는 구조
                    // 1 2 3
                    //   3
                    visited[newRow][newCol] = true;
                    DFS(row, col, sum + numbers[newRow][newCol], cnt + 1);
                    visited[newRow][newCol] = false;
                }
                // 나머지 4개는 4칸까지 쭉 dfs
                // 1 2 3 4      1 2         1 2         1 2 3
                //                3 4       4 3             4
                visited[newRow][newCol] = true;
                DFS(newRow, newCol, sum + numbers[newRow][newCol], cnt + 1);
                visited[newRow][newCol] = false;
            }
        }
    }
}
