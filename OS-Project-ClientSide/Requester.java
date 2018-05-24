
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Requester {
	Socket requestSocket;
	ObjectOutputStream out;
	ObjectInputStream in;
	String message = "";
	String ipaddress;
	Scanner stdin;

	Requester() {
	}

	void run() {
		stdin = new Scanner(System.in);
		try {
			// 1. creating a socket to connect to the server
			System.out.println("Please Enter your IP Address");
			ipaddress = stdin.next();
			requestSocket = new Socket(ipaddress, 2004);
			System.out.println("Connected to " + ipaddress + " in port 2004");
			// 2. get Input and Output streams
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(requestSocket.getInputStream());
			System.out.println("Hello");
			// 3: Communicating with the server
			do {
				try {
					// send inital message to client
					// prompt for input
					message = (String) in.readObject();
					System.out.println(message);
					message = stdin.next();
					sendMessage(message);

					if (message.compareToIgnoreCase("1") == 0) {
						// register name
						message = (String) in.readObject();
						System.out.println(message);
						// This is a workaround for stopping .nextLine() skipping a user input.
						// as .next() only takes the first word and discounts any further words entered
						stdin.nextLine();
						message = stdin.nextLine();

						sendMessage(message);
						// register address
						message = (String) in.readObject();
						System.out.println(message);

						message = stdin.nextLine();
						sendMessage(message);
						// register pps number
						message = (String) in.readObject();
						System.out.println(message);
						message = stdin.next();
						sendMessage(message);
						// register age
						message = (String) in.readObject();
						System.out.println(message);
						message = stdin.next();
						sendMessage(message);
						// register weight
						message = (String) in.readObject();
						System.out.println(message);
						message = stdin.next();
						sendMessage(message);
						// register height
						message = (String) in.readObject();
						System.out.println(message);
						message = stdin.next();
						sendMessage(message);

					} else if (message.compareToIgnoreCase("2") == 0) {

						// Login Name
						message = (String) in.readObject();
						System.out.println(message);
						stdin.nextLine();
						message = stdin.nextLine();
						sendMessage(message);
						// Login PPS number
						message = (String) in.readObject();
						System.out.println(message);
						message = stdin.next();
						sendMessage(message);
						// do while loop to keep user at the logged in menu until
						// they decide to exit by pressing e.
						do {
							// logged in menu options
							message = (String) in.readObject();
							System.out.println(message);
							message = stdin.next();
							sendMessage(message);
							// add fitness record
							if (message.equalsIgnoreCase("1")) {
								// mode
								message = (String) in.readObject();
								System.out.println(message);
								stdin.nextLine();
								message = stdin.nextLine();
								sendMessage(message);
								// duration
								message = (String) in.readObject();
								System.out.println(message);
								message = stdin.next();
								sendMessage(message);

							}
							// Add meal record

							else if (message.equalsIgnoreCase("2")) {

								// meal type
								message = (String) in.readObject();
								System.out.println(message);
								stdin.nextLine();
								message = stdin.nextLine();
								sendMessage(message);
								// meal description
								message = (String) in.readObject();
								System.out.println(message);
								message = stdin.nextLine();
								sendMessage(message);
							}
							//List last ten fitness records
							else if (message.compareToIgnoreCase("3") == 0) {
								message = (String) in.readObject();
								System.out.println(message);
								// delete fitness item from list option yes or no (Y/N)
								message = (String) in.readObject();
								System.out.println(message);
								message = stdin.next();
								sendMessage(message);
								if (message.equalsIgnoreCase("y")) {
									//Read in the record the user wants to remove from the list
									message = (String) in.readObject();
									System.out.println(message);
									message = stdin.next();
									sendMessage(message);
								}
								//List last ten meal records
							} else if (message.equalsIgnoreCase("4")) {
								message = (String) in.readObject();
								System.out.println(message);
								// delete meal item from list option
								message = (String) in.readObject();
								System.out.println(message);
								message = stdin.next();
								sendMessage(message);
								if (message.equalsIgnoreCase("y")) {
									//Read in the record the user wants to remove from the list
									message = (String) in.readObject();
									System.out.println(message);
									message = stdin.next();
									sendMessage(message);
								}
							}
							//exit do while loop if "e" is pressed.
						} while (!message.equalsIgnoreCase("e"));
					}

				} catch (ClassNotFoundException classNot) {
					System.err.println("data received in unknown format");
				}

			} while (!message.equals("3"));
		} catch (UnknownHostException unknownHost) {
			System.err.println("You are trying to connect to an unknown host!");
		} catch (IOException ioException) {
			ioException.printStackTrace();
		} finally {
			// 4: Closing connection
			try {
				in.close();
				out.close();
				requestSocket.close();
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
		}
	}

	void sendMessage(String msg) {
		try {
			out.writeObject(msg);
			out.flush();
			System.out.println("client>" + msg);
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	public static void main(String args[]) {
		Requester client = new Requester();
		client.run();
	}
}