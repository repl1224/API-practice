package algorithm;

public class Algorithm3 {

	public static void main(String[] args) {
//		int x = 4;
//		int y = 4;
//		String paValues = "1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16";
		int x = 3;
		int y = 3;
		String paValues = "1,2,3,4,5,6,7,8,9";
		String command = "RRT";
		String printPos = "1,3";
		
		System.out.println(solution(x, y, paValues, command, printPos));
	}
	
	public static String solution(int x, int y, String paValues, String command, String printPos) {
		
		String[] splitValues = paValues.split(",");
		
		if(x * y != splitValues.length) return "error";
		
		String[][] initData = new String[x][y];
		
		for(int i = 0; i < x; i++) {
			for(int j = 0; j < y; j++) {
				initData[i][j] = splitValues[x * i + j];
				System.out.println(x * i + j);
			}
		}
		
		for(int i = 0; i < command.length(); i++) {
			if(command.charAt(i) == 'R') {
				initData = rightRotate(initData, x, y);
			} else if(command.charAt(i) == 'L') {
				initData = leftRotate(initData, x, y);
			} else if(command.charAt(i) == 'T') {
				initData = reverse(initData, x, y);
			}
		}
        
		String[] result = printPos.split(",");
		
		return initData[Integer.parseInt(result[0])][Integer.parseInt(result[1])];
	}
	
	/**
	 * 배열 왼쪽 회전
	 * @param nArray 회전할 배열
	 * @param x 배열 가로크기
	 * @param y 배열 새로크기
	 * @return 회전한 배열
	 */
	public static String[][] leftRotate(String[][] nArray, int x, int y) {
		String[][] temp = new String[nArray.length][nArray[0].length];
		
		for(int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
            	temp[i][j] = nArray[j][(y - 1) - i];
            }
        }
		
		return temp;
	}
	
	/**
	 * 배열 오른쪽 회전
	 * @param nArray 회전할 배열
	 * @param x 배열 가로크기
	 * @param y 배열 새로크기
	 * @return 회전한 배열
	 */
	public static String[][] rightRotate(String[][] nArray, int x, int y) {
		String[][] temp = new String[nArray.length][nArray[0].length];
		
		for(int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
            	temp[i][j] = nArray[(x - 1) - j][i];
            }
        }
		
		return temp;
	}
	
	/**
	 * 회전 가로 역전
	 * @param nArray 회전할 배열
	 * @param x 배열 가로크기
	 * @param y 배열 새로크기
	 * @return 역전된 배열
	 */
	public static String[][] reverse(String[][] nArray, int x, int y) {
		String[][] temp = new String[nArray.length][nArray[0].length];
		
		for(int i = 0; i < nArray.length; i++) {
            for (int j = 0; j < nArray[0].length; j++) {
            	temp[i][j] = nArray[i][(y - 1) - j];
            }
        }
		
		return temp;
	}
}
