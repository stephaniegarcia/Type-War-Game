package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * The Server's GUI.
 * @author Stephanie Garcia Ribot
 *
 */
public class GameServer {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) throws IOException {
		@SuppressWarnings("resource")
		ServerSocket servSoc = new ServerSocket(7889); 
		
		try {
			ServerFrame frame = new ServerFrame();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		while(true) {
			System.out.println("Listening...");
			Socket socket1 = servSoc.accept();
			Socket socket2 = servSoc.accept();
			Thread t = new ServerThread(socket1, socket2);
			t.start();
		}
	}
}
