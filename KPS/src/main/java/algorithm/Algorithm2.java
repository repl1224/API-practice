package algorithm;

public class Algorithm2 {

	public static void main(String[] args) {
		int x = 4;
		int y = 4;
		String paValues = "1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16";
		
		System.out.println(solution(x, y, paValues));

	}
	
	public static String solution(int x, int y, String paValues) {
		String[] splitValues = paValues.split(",");
		
		if(x * y != splitValues.length) return "Error";
		
		String[][] initData = new String[x][y];
		
		for(int i = 0; i < y; i++) {
			for(int j = 0; j < x; j++) {
				initData[j][i] = splitValues[x * i + j];
			}
		}
		
		for(int i = 0; i < y; i++) {
			for(int j = 0; j < x; j++) {
				System.out.print(splitValues[x * i + j]);
			}
			System.out.println();
		}
		
		String result = "";
		
		for( int k = 0 ; k <= x + y - 2; k++ ) {
	        for( int j = 0 ; j <= k ; j++ ) {
	            int i = k - j;
	            if( i < y && j < x ) {
	                result = result + initData[i][j] + ",";
	            }
	        }
	    }
		
		return result.substring(0, result.length() - 1);
	}

}
