package interleaving.client;

import java.io.*;
import java.net.*;

public class Client {
	private String host;
	private int port;
	private Socket socket;
	private PrintWriter sout;
	private BufferedReader is;

	public Client(String host, int port){
		this.host = host;
		this.port = port;
	}
	
	public void openSocket(){
		try {
			this.socket = new Socket(this.host, this.port);
			this.sout = new PrintWriter(socket.getOutputStream());
			this.is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		}catch(Exception e) {
			System.out.println("Error"+e);
		}
	}
	
	public void closeSocket(){
		try {
			this.sout.close();
			this.is.close();
			this.socket.close();
		}catch(Exception e) {
			System.out.println("Error"+e);
		}
	}
	
	public void communicate(InputStream ins) {
		try{
			BufferedReader sin = new BufferedReader(new InputStreamReader(ins));

			String readline;
			readline = sin.readLine();
			this.sout.println(readline);
			while(!readline.equals("end")){			
				this.sout.flush();
				System.out.println("Client:" + readline);
				System.out.println("Server:" + this.is.readLine());
				readline = sin.readLine();
				this.sout.println(readline);
			}
		}catch(Exception e) {
			System.out.println("Error"+e);
		}
	}
	
	public String communicate(String line){
		this.sout.println(line);
		this.sout.flush();
		if(!line.equals("end")){
			try {
				return this.is.readLine();
			}catch(Exception e) {
				System.out.println("communicate Error"+e);
			}
		}
		return null;
	}
	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	
	public PrintWriter getSout() {
		return sout;
	}

	public void setSout(PrintWriter sout) {
		this.sout = sout;
	}

	public BufferedReader getIs() {
		return is;
	}

	public void setIs(BufferedReader is) {
		this.is = is;
	}
}
