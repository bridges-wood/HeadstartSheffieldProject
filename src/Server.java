import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.gson.Gson;

public class Server {

	public static UniRanker u;

	public static void main(String[] args) {
		new Server().startServer(); // Starts server to listen for connections.
		u = new UniRanker(args);
	}

	private void startServer() {
		final ExecutorService clientProcessingPool = Executors.newCachedThreadPool(); // Creates our pool of threads to
																						// process incoming text from
																						// clients.
		Runnable serverTask = new Runnable() {

			@Override
			public void run() {
				try {
					@SuppressWarnings("resource") // Because the server should always be up, the socket is never closed
													// programmatically.
					ServerSocket serverSocket = new ServerSocket(6000);
					System.out.println("Waiting for clients to connect...");
					while (true) {
						Socket clientSocket = serverSocket.accept();
						// If any task comes through, a thread is automatically assigned to it.
						clientProcessingPool.submit(new ClientTask(clientSocket));
					} // need host and port, we want to connect to the ServerSocket at port 8000
				} catch (IOException e) {
					System.err.println("Unable to process client request");
					e.printStackTrace();
				}
			}
		};
		Thread serverThread = new Thread(serverTask);
		serverThread.start();
	}

	private class ClientTask implements Runnable {
		private final Socket clientSocket;

		private ClientTask(Socket clientSocket) {
			this.clientSocket = clientSocket;
		}

		@Override
		public void run() {
			System.out.println("Got a client !");
			String recieved = receive(clientSocket);
			System.out.println("Data received from client: " + recieved);
			Gson g = new Gson();
			Preferences p = g.fromJson(recieved, Preferences.class);
			LinkedList<RankedCourse> curatedCourses = UniRanker.fetchCourses(p.subject, p.qualifications);
			String toSend = GenerateJSON.go(p, curatedCourses);
			System.out.println("Data to send: " + toSend);
			try {
				send(toSend, clientSocket);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/**
		 * Sends a string over a specified socket, back to the client that submitted the
		 * request.
		 *
		 * @param dataString The string to be sent.
		 * @param socket     The socket of the client.
		 * @throws IOException
		 */
		private void send(String dataString, Socket socket) throws IOException {
			OutputStream outputStream = socket.getOutputStream();
			DataOutputStream out = new DataOutputStream(outputStream); // Creates an output stream to the other end of
																		// the socket.
			System.out.println("Sending messages to the client");
			out.writeUTF(dataString + "aaaaaaaaaaaaaaaaaaaa" + "aaaaaaaaaaaaaaaaaaaa" + "aaaaaaaaaaaaaaaaaaaa"
					+ "aaaaaaaaaaaaaaaaaaaa" + "aaaaaaaaaaaaaaaaaaaa" + "aaaaaaaaaaaaaaaaaaaa" + "aaaaaaaaaaaaaaaaaaaa"
					+ "aaaaaaaaaaaaaaaaaaaa" + "aaaaaaaaaaaaaaaaaaaa" + "aaaaaaaaaaaaaaaaaaaa" + "aaaaaaaaaaaaaaaaaaaa"
					+ "aaaaaaaaaaaaaaaaaaaa" + "aaaaaaaaaaaaaaaaaaaa" + "aaaaaaaaaaaaaaaaaaaa" + "aaaaaaaaaaaaaaaaaaaa"
					+ "aaaaaaaaaaaaaaaaaaaa" + "aaaaaaaaaaaaaaaaaaaa" + "aaaaaaaaaaaaaaaaaaaa" + "aaaaaaaaaaaaaaaaaaaa"
					+ "aaaaaaaaaaaaaaaaaaaa" + "aaaaaaaaaaaaaaaaaaaa" + "aaaaaaaaaaaaaaaaaaaa" + "aaaaaaaaaaaaaaaaaaaa"
					+ "aaaaaaaaaaaaaaaaaaaa" + "aaaaaaaaaaaaaaaaaaaa"); // Writes data to the client (each block is 20
																		// a's)
			System.out.println("Closing socket and terminating thread.");
			out.flush(); // Cleans buffer.
		}

		/**
		 * Receives string data from the given client socket.
		 *
		 * @param socket The clients socket.
		 * @return The string sent by the client.
		 */
		private String receive(Socket socket) {
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String content = br.readLine();
				return content;
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
	}

}
