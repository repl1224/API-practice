package algorithm;

import java.util.LinkedList;
import java.util.List;

public class Algorithm4 {

	public static void main(String[] args) {
		System.out.println(solution(6, 2));
	}
	
	public static int solution(int peopleCnt, int moveCnt) {
		List<Integer> round = new LinkedList<Integer>();
		
		for(int i = 0; i < peopleCnt; i++) {
			round.add(i + 1);
		}
		
		System.out.println(round.toString());
		
		//루프 돌면서 지워야할 순번체크용
		int index = 1;
		
		//링크드 리스트에서 지워야할 인덱스 위치
		int pos = 0;
		
		while(round.size() > 1) {
			if(index % peopleCnt == moveCnt) {
				//지워야할 위치를 착지위한 index는 1부터 시작이지만 실제 리스트의 인덱스는 0부터 시작이므로 -1
				pos = pos + (index - 1);
				
				if(round.size() == (pos + 1) % peopleCnt) {
					round.remove(pos % peopleCnt);
					pos = 0;
				} else if(round.size() < (pos + 1) % peopleCnt) {
					pos = pos - round.size();
					round.remove(pos % peopleCnt);
				} else {
					round.remove(pos % peopleCnt);
				}
				System.out.println(round.toString());
				
				index = 1;
			}
			
			index++;
		}
		
		return round.get(0);
	}

}
