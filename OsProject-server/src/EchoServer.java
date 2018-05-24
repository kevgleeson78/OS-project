import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;

public class EchoServer {
	private static ServerSocket m_ServerSocket;

	public static void main(String[] args) throws Exception {
		m_ServerSocket = new ServerSocket(2004, 10);
		int id = 0;
		while (true) {
			Socket clientSocket = m_ServerSocket.accept();
			ClientServiceThread cliThread = new ClientServiceThread(clientSocket, id++);
			cliThread.start();
		}
	}
}

class ClientServiceThread extends Thread {
	Socket clientSocket;
	String message;
	int clientID = -1;
	boolean running = true;
	ObjectOutputStream out;
	ObjectInputStream in;
	//The below array lists are used to store arrays of objects to add, delete and update the text file in this application.
	private ArrayList<User> aList = new ArrayList<>();
	private ArrayList<FitnessRecord> fList = new ArrayList<>();
	private ArrayList<MealRecord> mList = new ArrayList<>();

	ClientServiceThread(Socket s, int i) {
		clientSocket = s;
		clientID = i;
	}

	void sendMessage(String msg) {
		try {
			out.writeObject(msg);
			out.flush();

		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	@Override
	public void run() {
		System.out.println(
				"Accepted Client : ID - " + clientID + " : Address - " + clientSocket.getInetAddress().getHostName());
		try {
			out = new ObjectOutputStream(clientSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(clientSocket.getInputStream());
			System.out.println("Accepted Client : ID - " + clientID + " : Address - "
					+ clientSocket.getInetAddress().getHostName());
			//do while loop to keep the first options open unless the user selects 3.
			do {
				try {
					sendMessage("Press 1 to register\nPress 2 to login \nPress 3 to exit");
					//read user input
					message = (String) in.readObject();

					if (message.compareToIgnoreCase("1") == 0) {
						
						System.out.println("User wishes to register");
						sendMessage("Please enter your name.");
						//store user input
						String name = (String) in.readObject();
						sendMessage("Please enter your address.");
						//store user input
						String address = (String) in.readObject();
						sendMessage("Please enter your pps number");
						//store user input
						String ppsNum = (String) in.readObject();
						sendMessage("Please enter your age");
						//store user input
						String age = (String) in.readObject();
						sendMessage("Please enter your weight");
						//store user input
						String weight = (String) in.readObject();
						sendMessage("Please enter your height");
						//store user input
						String height = (String) in.readObject();

						try {
							//create new file to hold the list of users
							//Only created if it does not exist it is appended to otherwise
							File outFile = new File("user.txt");
							//True parameter passed to append to user.txt
							FileWriter fw = new FileWriter(outFile, true);
							BufferedWriter bw = new BufferedWriter(fw);
							//Write all user inputs to the text file with each entry added to a new line.
							bw.write(name);
							bw.newLine();
							bw.write(address);
							bw.newLine();
							bw.write(ppsNum);
							bw.newLine();
							bw.write(age);
							bw.newLine();
							bw.write(weight);
							bw.newLine();
							bw.write(height);
							bw.newLine();
							bw.close();
						} catch (IOException e) {

							e.printStackTrace();
						}

					}

					else if (message.compareToIgnoreCase("2") == 0) {
						System.out.println("User wishes to login");

						sendMessage("Please enter your name");
						//store user input
						String name = (String) in.readObject();

						sendMessage("Please enter your pps number");
						//store user input
						String ppsNum = (String) in.readObject();
						//Declare variables for reading in the user text file.
						File inFile;
						FileReader fr;
						BufferedReader br;
						
						try {
							//get the user.txt 
							inFile = new File("user.txt");
							fr = new FileReader(inFile);
							br = new BufferedReader(fr);
							//declare the list of user attributes in order of them being written to the text file.
							String name1 = br.readLine();
							String address1 = br.readLine();
							String ppsNum1 = br.readLine();
							String age1 = br.readLine();
							String weight1 = br.readLine();
							String height1 = br.readLine();
							//While loop to iterate over the text file user line by line.
							while (name1 != null) {
								//New instance of user.
								User u = new User();
								//Set all attributes of the user with each itteration.
								u.setName(name1);
								u.setAddress(address1);
								u.setPpsNum(ppsNum1);
								u.setAge(age1);
								u.setWeight(weight1);
								u.setHeight(height1);
								//add user object to the arrayListaList
								aList.add(u);
								//read each line of the text file
								name1 = br.readLine();
								address1 = br.readLine();
								ppsNum1 = br.readLine();
								age1 = br.readLine();
								weight1 = br.readLine();
								height1 = br.readLine();
							}
							//do while loop to keep the user logged in until he/she decides to exit by selecting "e".
							do {
								//For each loop to iterate over the array list aList
								for (User u : aList) {
									//Condition to check the user input matches the user name and pps number needed to login
									if (name.equalsIgnoreCase(u.getName()) && ppsNum.equalsIgnoreCase(u.getPpsNum())) {
									//message to display to the console if the user has logged in
										System.out.println("User: "+u.getName()+" Is logged in.");
										//Menu display if the user logs in successfully if not the user is sent back to the landing menu
										sendMessage("Press 1 to add a fitness record\n"
												+ "Press 2 to add a meal record\n"
												+ "Press 3 to view last ten fitness records entered\n"
												+ "Press 4 to view last ten meal records\n" + "Press e to log out.");
										//read in the user input of the menu option
										message = (String) in.readObject();
										if (message.equalsIgnoreCase("1")) {
											//console message of logged in user name
											System.out.println("User: "+ u.getName()+ " wishes to enter a fitness record");
											sendMessage("Please enter a mode of fitness activity");
											//read in maode to be written to text file
											String mode = (String) in.readObject();
											sendMessage("Please enter the duration of your activity in minutes");
											//read in time of activity
											String duration = (String) in.readObject();

											try {
												//create new file with the logged in  user pps number appended 
												File outFile = new File(u.getPpsNum() + "_fitness.txt");
												FileWriter fw = new FileWriter(outFile, true);
												BufferedWriter bw = new BufferedWriter(fw);
												bw.write(mode);
												bw.newLine();
												bw.write(duration);
												bw.newLine();
												bw.close();
											} catch (IOException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}

										} else if (message.equalsIgnoreCase("2")) {
											//console message of logged in user name
											System.out.println("User: "+u.getName() +" wishes to enter a meal record");
											sendMessage("Please enter the type of meal");
											//read in meal type
											String mealType = (String) in.readObject();
											sendMessage("Please describe the meal you have had");
											//read in meal description
											String description = (String) in.readObject();
											//limit the amount of characters to 100 if the user inputs over 100 characters the string is cut at this point.
											if (description.length()>100) {
											    String cutString = description.substring(0, 100);
											    description = cutString;
											}
											try {
												//create new file with the logged in  user pps number appended 
												File outFile = new File(u.getPpsNum() + "_meal.txt");
												FileWriter fw = new FileWriter(outFile, true);
												BufferedWriter bw = new BufferedWriter(fw);
												bw.write(mealType);
												bw.newLine();
												bw.write(description);
												bw.newLine();
												//close the buffered writer stream
												bw.close();
											} catch (IOException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}

										} else if (message.equalsIgnoreCase("3")) {
											System.out.println("User: "+u.getName()+" is viewing the last 10 fitness records");
											// Stringbuilder to send each numbered object to the console as a continuous string
											StringBuilder sb = new StringBuilder();
											try {
												//read the file with the same pps number as the logged in user.
												inFile = new File(u.getPpsNum() + "_fitness.txt");
												fr = new FileReader(inFile);
												br = new BufferedReader(fr);
												String mode = br.readLine();
												String duration = br.readLine();
												// loop over the text file
												while (mode != null) {
													// new instance of the FitnessRecord class
													FitnessRecord fitRec = new FitnessRecord();
													// set mode and duration of fitness record
													fitRec.setMode(mode);
													fitRec.setDuration(duration);
													// add the FitnessRecord object to the arraylist
													fList.add(fitRec);
													// read each line from text file.
													mode = br.readLine();
													duration = br.readLine();

												}
												// count variable to store the record number
												int count = 0;
												// reverse the order of the arraylist to get the last ten entries
												Collections.reverse(fList);
												// for each loop to iterate over the fitness record arraylist
												for (FitnessRecord f : fList) {
													// increment the counter for each record object
													count++;
													// only show the last ten entries
													if (count <= 10) {
														// write each object element to the string bulider
														sb.append("record number: " + count + "\nMode: " + f.getMode()
																+ "\nDuration: " + f.getDuration() + "\n\n");
													}
												}
												
												// set the res variable to to a string
												String res = sb.toString();
												// send the entire string to the console to be displayed to the user.
												sendMessage(res);
												// Option for deleting item
												// send to client
												sendMessage("Do you need to delete a record from the last ten entries?"
														+ "Y/N");
												// read in response from client
												String delete = (String) in.readObject();
												// conditional to check user input
												if (delete.equalsIgnoreCase("y")) {
													System.out.println("User: "+u.getName()+" has requested to delete a fitness record.");
													// message if "y" has been pressed
													sendMessage("please enter the record number you want to delete.");
													// get index value from client
													int index = Integer.parseInt((String) in.readObject());
													// remove the object from the arraylist according to the number of
													// the record entered.
													fList.remove(index - 1);
													// clear the text file for the new list to be written minus the
													// deleted item from the arraylist
													PrintWriter writer = new PrintWriter(
															u.getPpsNum() + "_fitness.txt");
													// set empty string to overwrite the existing text
													writer.print("");
													// close stream
													writer.close();
													//re-reverse the new entries of the text file
													Collections.reverse(fList);
													try {
														//recreate the fitness text file with the logged in users pps number appended to the beginning of the file
														File outFile = new File(u.getPpsNum() + "_fitness.txt");
														//for each loop to iterate over the array list of FitnessRecord objects
														for (FitnessRecord fRec : fList) {
															//Append to the text file.
															FileWriter fw = new FileWriter(outFile, true);
															BufferedWriter bw = new BufferedWriter(fw);

															bw.write(fRec.getMode());
															bw.newLine();
															bw.write(fRec.getDuration());
															bw.newLine();
															//close the buffered writer stream
															bw.close();
														}
													} catch (IOException e) {
														// TODO Auto-generated catch block
														e.printStackTrace();
													}
												}
												//Clear the arrayList so a new entry holds only the objects that are obtained from the for each loop.
												fList.removeAll(fList);
												//close the buffered reader stream
												br.close();
											} catch (IOException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}

										} else if (message.equalsIgnoreCase("4")) {
											//console message if the user has selected to view the meal records.
											System.out.println("User: "+u.getName()+" is viewing the last 10 meal records");
											try {
												// Stringbuilder to send each numbered object to the console as a continuous string
												StringBuilder sb = new StringBuilder();
												//read the file with the same pps number as the logged in user.
												inFile = new File(u.getPpsNum() + "_meal.txt");
												fr = new FileReader(inFile);
												br = new BufferedReader(fr);
												String mealType = br.readLine();
												String mealDescription = br.readLine();
												// loop over the text file
												while (mealDescription != null) {
													// new instance of the MealRecord class
													MealRecord mRec = new MealRecord();
													//set each line in the text file to the MealRecord classes variables
													mRec.setMealType(mealType);
													mRec.setMealDescription(mealDescription);
													//add the object to the arrayList with each iteration
													mList.add(mRec);
													mealType = br.readLine();
													mealDescription = br.readLine();

												}
												// count variable to store the record number
												int count = 0;
												//Reverse the order of teh arrayList so the last entry of the user is displayed as number 1 in the generated list
												Collections.reverse(mList);
												//iterate over the arraylist of objects
												for (MealRecord m : mList) {
													//incremant the counter to have this as the record number of user input
													count++;
													//limit the output to the last ten entries
													if (count <= 10) {
														// write each object element to the string bulider
														sb.append("record number: " + count + "\nMeal type: "
																+ m.getMealType() + "\nmeal description: "
																+ m.getMealDescription() + "\n\n");
													}
												}
												
												//Store the result as a string
												String res = sb.toString();
												//send the entire string to the user
												sendMessage(res);
												// Option for deleting item
												// send to client
												sendMessage("Do you need to delete a record from the last ten entries?"
														+ "Y/N");
												// read in response from client
												String delete = (String) in.readObject();
												// conditional to check user input
												if (delete.equalsIgnoreCase("y")) {
													System.out.println("User: "+u.getName()+" has requested to delete a meal record.");
													// message if "y" has been pressed
													sendMessage("please enter the record number you want to delete.");
													// get index value from client
													int index = Integer.parseInt((String) in.readObject());
													// remove the object from the arraylist according to the number of
													// the record entered.
													mList.remove(index - 1);
													// clear the text file for the new list to be written minus the
													// deleted item from the arraylist
													PrintWriter writer = new PrintWriter(u.getPpsNum() + "_meal.txt");
													// set empty string to overwrite the existing text
													writer.print("");
													// close stream
													writer.close();
													//re-reverse the new entries of the text file
													Collections.reverse(mList);
													try {
														//recreate the fitness text file with the logged in users pps number appended to the beginning of the file
														File outFile = new File(u.getPpsNum() + "_meal.txt");
														//for each loop to iterate over the array list of FitnessRecord objects
														for (MealRecord m : mList) {
															//Append to the text file.
															FileWriter fw = new FileWriter(outFile, true);
															BufferedWriter bw = new BufferedWriter(fw);

															bw.write(m.getMealType());
															bw.newLine();
															bw.write(m.getMealDescription());
															bw.newLine();
															bw.close();
														}
													} catch (IOException e) {
														// TODO Auto-generated catch block
														e.printStackTrace();
													}
												}
												//Clear the Array list mList for future use
												mList.removeAll(mList);
												//CLose the buffered reader
												br.close();
											} catch (IOException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
										}
										
										if(message.equalsIgnoreCase("e")) {
											//console message if the user has logged out
											System.out.println("User: "+u.getName()+" has logged out");
										}
									}
									
								}
								//End do while loop if user selects "e"
							} while (!message.equalsIgnoreCase("e"));
							//clear the array list aList for future use
							aList.removeAll(aList);
							//Close the buffered reader.
							br.close();

						} catch (IOException e) {

							e.printStackTrace();
						}

					}

				} catch (ClassNotFoundException classnot) {
					System.err.println("Data received in unknown format");
				}

			} while (!message.equals("3"));

			System.out.println(
					"Ending Client : ID - " + clientID + " : Address - " + clientSocket.getInetAddress().getHostName());
		} catch (

		Exception e) {
			e.printStackTrace();
		}
	}
}
