import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{
	
	public static ArrayList<ClientHandler> ClientHandlers = new ArrayList<>();
	private Socket socket;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
	private String clientUserName;
	
	public ClientHandler(Socket socket) {
		try {
			this.socket = socket;
			this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.clientUserName = bufferedReader.readLine();
			ClientHandlers.add(this);
			broadcastMessage("SERVER: "+ clientUserName + "has antred the chat !");
		} catch (IOException e) {
			closeEveryThing(socket, bufferedReader, bufferedWriter);
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		String messageFromClient;
		while(socket.isConnected()) {
			try {
				messageFromClient = bufferedReader.readLine();
				broadcastMessage(messageFromClient);
			} catch (IOException e) {
				closeEveryThing(socket, bufferedReader, bufferedWriter);
				break;
			}
			
		}
	}
	
	public void broadcastMessage(String messageToSend) {
		for (ClientHandler clientHandler : ClientHandlers) {
			try {
				if (!clientHandler.clientUserName.equals(clientUserName)) {
					clientHandler.bufferedWriter.write(messageToSend);
					clientHandler.bufferedWriter.newLine();
					clientHandler.bufferedWriter.flush();
				}
			} catch (IOException e) {
				closeEveryThing(socket, bufferedReader, bufferedWriter);
			}
		}
		
	}
	
	public void removeClientHandler() {
		ClientHandlers.remove(this);
		broadcastMessage("server: " + clientUserName + "has left the chat");
		
	}
	
	public void closeEveryThing(Socket socket, BufferedReader bufferedReader, BufferedWriter buffredWriter) {
		try {
			if (bufferedReader != null ) {
				bufferedReader.close();
			}
			if (bufferedWriter != null ) {
				bufferedWriter.close();
			}
			if (socket != null) {
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
