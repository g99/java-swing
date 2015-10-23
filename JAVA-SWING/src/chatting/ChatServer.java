package chatting;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class ChatServer extends JFrame implements ActionListener, Runnable {
	public static void main(String[] args) {
		ChatServer cs = new ChatServer();
		new Thread(cs).start();
	}
	private static final long serialVersionUID = 1L; // 직렬화
	/**
	 * 직렬화 : 데이터를 한줄로 정렬해서 스트림을 이용한 전송시에 더 빨리질 것이다.
	 */

	Vector<Service> vec; // 채팅방에 클라이언트들을 저장함
	JTextArea txt;
	JButton btn;

	public ChatServer() {
		super("서버");
		// 부품준비
		vec = new Vector<Service>();
		txt = new JTextArea();
		btn = new JButton("서버종료");
		btn.addActionListener(this);
		// 조립과정
		this.add(txt, "Center"); // BorderLayout.CENTER
		this.add(btn, "South"); // BorderLayout.SOUTH
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 프레임에 닫기버튼 장착
		this.pack();
		this.setBounds(50, 100, 300, 600); // x, y, 가로, 세로
		this.setVisible(true);
	}

	// "서버종료" 라는 버튼을 클릭했을 경우 이벤트 발생
	@Override
	public void actionPerformed(ActionEvent e) {
		// switch (e.getActionCommand()) {
		// case "서버종료":
		System.exit(0); // 프로그램을 종료시켜라
		// break;

		// default:
		// break;
		// }
	}

	@Override
	public void run() {
		ServerSocket ss = null;
		try {
			ss = new ServerSocket(5555); // 5555는 서버 접속포트 번호
		} catch (Exception e) {
			e.printStackTrace();
		}
		while (true) {
			txt.append("클라이언트 접속 대기중\n");
			try {
				Socket s = ss.accept(); // 클라이언트의 소켓은 서버소켓이 허용해야 생성된다.
				txt.append("클라이언트 접속 처리\n");
				Service cs = new Service(s);
				cs.start(); // 클라이언트의 스레드가 작동하는 것
				cs.nickName = cs.in.readLine();
				cs.sendMessageAll("/c" + cs.nickName); // 접속되어있는 다른 클라이언트에게 나를알림
				vec.add(cs); // 접속된 클라이언트를 벡터에 추가함
				for (int i = 0; i < vec.size(); i++) {
					Service service = vec.elementAt(i);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}

	}

	// 이너 클래스
	class Service extends Thread { // 접속자 객체를 생성하는 기능
		String nickName;
		BufferedReader in;
		// *Reader, *Writer 는 2바이트씩 처리하므로 문자에 최적화
		OutputStream out;
		// *Stream은 1바이트씩 처리하므로 이미지, 음악, 파일에 최적화
		Socket s; // 클라이언트 쪽 소켓
		Calendar now = Calendar.getInstance();

		// 디폴트 생성자 없이 소케을 무조건 필요로 함
		public Service(Socket s) {
			nickName = "guest";
			try {
				in = new BufferedReader(new InputStreamReader(s.getInputStream()));
				out = s.getOutputStream();
			} catch (Exception e) {
				e.printStackTrace();
			}
			this.s = s;
		}

		@Override
		public void run() {
			while (true) {
				try {
					String msg = in.readLine();
					txt.append("전송메시지 : " + msg + "\n");
					if (msg == null) {
						return;
					}
					if (msg.charAt(0) == '/') {
						char temp = msg.charAt(1);
						switch (temp) {
						case 'n': // n : 이름바꾸기
							if (msg.charAt(2)==' ') {
								// - 는 닉네임과 새이름을 분리하기 위해 임의로 설정한 기호
								sendMessageAll("\n" + nickName + "-" +msg.substring(3).trim());
								nickName = msg.substring(3).trim();
							}
							break;
						case 'q': // q : 클라이언트 퇴장
							for (int i = 0; i < vec.size(); i++) {
								Service svc = (Service) vec.get(i);
								if (nickName.equals(svc.nickName)) {
									vec.remove(i);
									break;
								}
							}
							sendMessage(">" + nickName + " 님이 퇴장하셨습니다.");
							in.close(); // 인풋 네트워크 해제
							out.close(); // 아웃풋 네트워크 해제
							s.close(); // 소켓 닫기
							return;
						case 's' : // s : 귓속말
							String name = msg.substring(2, msg.indexOf('-')).trim();
							for (int i = 0; i < vec.size(); i++) {
								Service cs3 = (Service) vec.elementAt(i);
								if (name.equals(cs3.nickName)) {
									cs3.sendMessage(nickName +">>(귓속말) " + msg.substring(msg.indexOf('-')+1));
									break;
								}
							}
						default:
							sendMessageAll(nickName + " >> " + msg); 
							break;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		public void sendMessageAll(String msg) {
			// 벡터의 데이터를 꺼내어 클라이언트에게 보내기
			for (int i = 0; i < vec.size(); i++) {
				Service cs = (Service) vec.elementAt(i);
				cs.sendMessage(msg);
			}
		}
		
		public void sendMessage(String msg) {
			try {
				out.write((msg + "\n").getBytes());
				txt.append("보냄 : " + msg + "\n");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
