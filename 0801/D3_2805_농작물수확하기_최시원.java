package Algo_08_01;

/**
 * 
 * <pre>
 * 농장의 크기는 홀수뿐이다. 또한 n의 크기를 가지는 농장은 n-2 크기 농장을 가운데 품고 있다.
 * 이 구조가 반복되므로, n+2크기의 농장에서 수확물 양은 (n농장 수확물) + (n+2에서의 마름모꼴 가장자리)
 * 이 될 것이다. 이를 재귀로 구현하여 풀어볼 것이다.
 * </pre>
 * @author 최시원
 *
 */


import java.util.Scanner;
public class D3_2805 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		//총합을 구할 변수
		int sum = 0;
		//테스트케이스
		int T = sc.nextInt();
		//2차원배열
		int[][] arr2d;
		for(int test_case = 1; test_case<T+1; test_case++) {
			int N = sc.nextInt();
			//N만큼의 크기를 가진 2차원배열을 만든다
			arr2d = new int[N][N];
			
			
			String tmp;
			for(int i=0; i<N; i++) {
				tmp = sc.next();
				for(int j=0; j<N; j++) {
					//2차원배열 초기화
					arr2d[i][j] = (int) tmp.charAt(j)-'0';
				}	
			}
			
			//함수호출
			System.out.println("#"+test_case+" "+farm(0, arr2d));
		}
	}
	
	public static int farm(int sum, int[][] arr2d) {
		//만약 배열 크기가 1개면, 마지막남은 요소 1개를 더하고 값을 반환한다
		if(arr2d.length == 1) {
			return sum+arr2d[0][0];
		}
		
		//배열의 중앙값 기준으로 양 옆을 따라가며 마름모꼴이 되게끔 숫자를 더한다
		//여기는 마름모꼴의 상단 부분의 합을 구한다
		int mid = (arr2d.length)/2;
		for(int i=0; i<=arr2d.length/2;i++) {
			//마름모꼴의 위아래 꼭짓점은 한번만 더해준다
			if(i==0) {
				sum += arr2d[i][mid];
				continue;
			}
			//그 이외는 i의 값에 맞추어 더해준다
			sum += arr2d[i][mid-i];
			sum += arr2d[i][mid+i];
		}

		//여기는 마름모꼴의 하단 부분의 합을 구한다
		//위와 마찬가지로 구해 더해준다
		for(int i=arr2d.length/2+1; i<arr2d.length; i++) {
			if(i==arr2d.length-1) {
				sum += arr2d[i][mid];
				continue;
			}
			sum += arr2d[i][(i-mid)];
			sum += arr2d[i][arr2d.length-1-(i-mid)];
		}
		
		//현재 배열보다 크기가 2 작은, 가운데 포함되어있는 새로운 배열을 만든다
		int[][] newArr2d = new int[arr2d.length-2][arr2d.length-2];
		//해당하는 값을 초기화시킨 후
		for(int i=1; i<arr2d.length-1; i++) {
			for(int j=1; j<arr2d.length-1; j++) {
				newArr2d[i-1][j-1] = arr2d[i][j];
			}
		}
		
		// 작아진 새 배열 기준으로 합을 구하도록 새로이 함수를 호출한다
		return farm(sum, newArr2d);
	}

}
