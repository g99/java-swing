package lotto;

import java.util.Arrays;

public class Lotto {
	public static void main(String[] args) {

	}
	int[] lotto = new int[6]; // SBS에서는 하나의 로또번호만 출력
	public Lotto() {
		this.setLotto();
	}
	public int[] getLotto(){
		Arrays.sort(lotto);
		return lotto;
	}
	public void setLotto() {
		for (int i = 0; i < lotto.length; i++) {
			int randomNum = (int) (Math.random()*45+1);
			boolean exist = false; 
			for (int j = 0; j < lotto.length; j++) {
				if (randomNum == lotto[j]) {
					exist = true;
					break;
				}
			}
			if (exist) {
				i--; // 중복된 값이 출력되면 카운트 숫자를 줄여준다.
			}else{
				lotto[i] = randomNum; // if문을 타지 않은 경우
			}
		}
		Arrays.sort(lotto); // 오름차순 정렬
	}

}


