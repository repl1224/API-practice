package algorithm;

public class Algorithm1 {
	public static void main(String[] args) {
//		String startTime = "02:41";
//		String endTime = "04:41";
		String startTime = "04:41";
		String endTime = "00:42";
		
		System.out.println(solution(startTime, endTime));
	}
	
	public static int solution(String startTime, String endTime) {
		int startHour = Integer.parseInt(startTime.substring(0, 2));
		int endHour = Integer.parseInt(endTime.substring(0, 2));
		
		int startMin = Integer.parseInt(startTime.substring(3, 5));
		int endMin = Integer.parseInt(endTime.substring(3, 5));
		
		System.out.println(startHour);
		System.out.println(endHour);
		
		System.out.println(startMin);
		System.out.println(endMin);
		
		int resultMin = 0;
		
		if(startHour == endHour) {
			if(endMin >= startMin) {
				resultMin = endMin - startMin;
			} else {
				resultMin = 60 * 24 - (startMin - endMin);
			}
		} else if(startHour > endHour) {
			resultMin = (24 + endHour - startHour) * 60 + (endMin - startMin);
		} else {
			resultMin = (endHour - startHour) * 60 + (endMin - startMin);
		}
		
		return resultMin;
	}
}
