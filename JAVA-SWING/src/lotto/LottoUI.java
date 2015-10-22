package lotto;


import java.awt.BorderLayout;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;


public class LottoUI extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	Lotto lotto;
	// 컴포넌트(스윙에서 화면을 나타내느 클래스)는 연관관계로 부모*자식 관계를 맺는다.
	JButton btn;
	JPanel panelNorth, panelSouth; // 보더 레이아웃 개념으로 볼때 동서남북으로 위치값을 줌.
	ImageIcon icon;
	List<JButton> btns;
	public LottoUI() {
		// 부품 준비 - 큰것에서 작은것 순으로
		this.setTitle("SBS 로또추첨");
		lotto = new Lotto();
		btns = new ArrayList<JButton>();
		panelNorth = new JPanel();
		panelSouth = new JPanel();
		btn = new JButton("로또 추첨 번호");
		// 조립 단계 - 작은것부터 큰것 순으로
		btn.addActionListener(this); // 이 클래스에서 구현한 관련 메소드를 할당한다.
		panelNorth.add(btn); // 북쪽 패널에 버튼을 장착
		this.add(panelNorth, BorderLayout.NORTH);
		this.add(panelSouth, BorderLayout.SOUTH);
		this.setBounds(300, 400, 1200, 300);
		// 300, 400 은 x, y 좌표값
		// 1200, 300은 픽셀로 크기
		this.setVisible(true);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (btns.size() == 0) {
			JButton temp = null;
			for (int i = 0; i < 6; i++) {
				temp = new JButton(); // 번호가 붙지않은 로또공 객체를 만들어라
				btns.add(temp); // 6회전이므로  로또볼  6개를 만들어 담아라
				panelSouth.add(temp); // 리스트에 담고, 또 그 로또볼을 패널에 붙여라
			}
		}
		lotto.setLotto(); // 로또볼 추첨에 들어갑니다.
		int[] temp = lotto.getLotto();
		for (int i = 0; i < temp.length; i++) {
			btns.get(i).setIcon(new ImageIcon(""));
		}
	}
}
