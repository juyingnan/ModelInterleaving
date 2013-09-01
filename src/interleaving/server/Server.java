package interleaving.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private int port;
	private ServerSocket server;
	private Socket socket;
	private BufferedReader bufferReader;
	private PrintWriter printWriter;
	
	public Server(int port){
		this.port = port;
		this.printWriter = null;
		this.bufferReader = null;
		this.socket = null;
		this.server = null;
	}
	
	public void openServerSocket(){
		try {
			this.server = new ServerSocket(port);
			this.socket = server.accept();
			this.bufferReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			this.printWriter = new PrintWriter(this.socket.getOutputStream());
		}catch(Exception e) {
			System.out.println("openServerSocket Error" + e);
		}
	}
	
	public void closeServerSocket(){
		try {
			this.printWriter.close();
			this.bufferReader.close();
			this.socket.close();
			this.server.close();
		}catch(Exception e) {
			System.out.println("closeServerSocket Error"+e);
		}
	}
	
	public String readLine(){
		String result = null;
		try {
			result = this.bufferReader.readLine();
		}catch(Exception e) {
			System.out.println("readLine Error"+e);
		}
		return result;
	}
	
	public void printLine(String line){
		try {
			this.printWriter.println(line);
		}catch(Exception e) {
			System.out.println("printLine Error"+e);
		}
	}
	
	public void flush(){
		try {
			this.printWriter.flush();
		}catch(Exception e) {
			System.out.println("flush Error"+e);
		}
	}
	
	public int getPort() {
		return port;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	
	public ServerSocket getServer() {
		return server;
	}
	
	public void setServer(ServerSocket server) {
		this.server = server;
	}
	
	public Socket getSocket() {
		return socket;
	}
	
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	
	public BufferedReader getBufferReader() {
		return bufferReader;
	}
	
	public void setBufferReader(BufferedReader bufferReader) {
		this.bufferReader = bufferReader;
	}
	
	public PrintWriter getPrintWriter() {
		return printWriter;
	}
	
	public void setPrintWriter(PrintWriter printWriter) {
		this.printWriter = printWriter;
	}
}
