package member;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class MemberUI extends JFrame{
	private Vector data;
	private Vector title;
	JTable table;
	JButton btnAdd, btnSerch, btnUpdate, btnDel, btnCler;
	JTextField txtName, txtAddr, txtUserid;
	JLabel lblName, lblAddr, lblUserid;
	DefaultTableModel model;
	public MemberUI(){
		//super("회원관리");
		init();
		pack();
		setVisible(true);
	}
	public void init(){
		this.setTitle("회원관리");
		data = new Vector<>(); // 테이블에 표시될 데이터 백터
		title = new Vector<>(); // 테이블 표시될 타이틀 백터
		title.add("아이디");
		title.add("이름");
		title.add("주소");
		Vector result = null; // MemberDAO가 selectAll()한 결과를 받는 컬렉션
	//	model.setDataVector(result, title);
		table = new JTable(model); // 모델을 통해 테이블 생성
		JScrollPane scroll = new JScrollPane(table); // 테이블 옆 스크롤바
		table.addMouseListener(new MouseAdapter() { // 이벤트를 단 하나의 컴포넌트 객체에 할당하는 방법
			@Override
			public void mouseClicked(MouseEvent e) {
				int  index = table.getSelectedRow();
				Vector rs = (Vector) data.get(index);
				txtUserid.setText((String) rs.get(0));
				txtName.setText((String) rs.get(1));
				txtAddr.setText((String) rs.get(2));
				// 사용자 ID는 변경할수 없다는 옵션 처리
				txtUserid.setEditable(false);
				txtName.setEditable(false);
			}
		});
		// 화면에 표시할 패널 생성
		JPanel panel = new JPanel();
		
		// 값을 입력받거나 표시라 텍스트 필드
		txtName = new JTextField(8); // 8글자 허용
		txtUserid = new JTextField(10); // 10글자 허용
		txtAddr = new JTextField(30); // 30글자 허용
		
		//레이블 생성
		lblUserid = new JLabel("ID");
		lblName = new JLabel("이름");
		lblAddr = new JLabel("주소");
		
		//버튼 생성
		btnAdd = new JButton("추가");
		btnSerch = new JButton("검색");
		btnUpdate = new JButton("수정");
		btnDel = new JButton("삭제");
		btnCler = new JButton("초기화");
		
		// 추가 버튼 클릭
		btnAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String userid = txtUserid.getText(); 
		//		String userid = scnner.naxt(); 와 동일한 기능
				String name = txtName.getText();
				String addr = txtAddr.getText();
				// service.insert(userid, name, addr)
				
		//		Vctor result = sellctAII();
		//		model.setDataVector(result,title);
			}
		});
		btnSerch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		btnUpdate.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		btnDel.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		
			});
		btnCler.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		
			});
			panel.add(lblUserid);
			panel.add(txtUserid);
			panel.add(lblName);
			panel.add(txtName);
			panel.add(lblAddr);
			panel.add(btnAdd);
			panel.add(btnSerch);
			panel.add(btnUpdate);
			panel.add(btnDel);
			panel.add(btnCler);
			Container container = this.getContentPane();
			container.add(new JLabel("테이블 데모 프로그램", JLabel.CENTER), "North");
			container.add(scroll,BorderLayout.CENTER);
			container.add(panel,BorderLayout.SOUTH);
			
			this.addWindowListener(new WindowAdapter(){
				
				@Override
				public void windowClosing(WindowEvent e){
					setVisible(false);
					System.exit(0);
				}
			});
	}
}
