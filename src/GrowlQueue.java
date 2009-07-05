import java.io.*;
import com.growl.*;
import javax.swing.*;

//import org.apache.commons.codec.StringDecoder;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.*;

public class GrowlQueue {
 /*
	alarm.wav alarm2.wav bunnyTroubles.wav eightHours.wav
	gameOver.wav inconceivable.wav newTicket.wav redAlert.wav
  */
	static String No_Notifcation = "All Good", New_Ticket = "New Ticket",
			Ticket_Warning = "Ticket Warning",
			Ticket_Alert = "Ticket Stalage Imminate",
			Ticket_Stale = "Ticket has gone stale";

	static GrowlWrapper gw = new GrowlWrapper("The Queue", "Finder",

	new String[] { New_Ticket, Ticket_Warning, Ticket_Alert, Ticket_Stale,
			No_Notifcation }, new String[] { No_Notifcation });
	static String url = "http://evolution.voxeo.com/community.bizblog.ticketq.TicketQ/ticketQStatus";

	static String oldValue = null;
	static String newValue = null;

	public static void main(String[] args) {
		
		drawWindow();
		
		while (true) {
			newValue = fetchStuff();
			if (newValue.equals(oldValue)) {
				//System.out.println("nothing to see here");
			} else {
				//System.out.println("new event");
				alert(newValue);
			}
			sleep(15000);
			oldValue = newValue;
		}
	}

	public static void alert(String input) {
		String[] inputArray = new String[7];
		inputArray = input.split("");

		if (inputArray[1].equalsIgnoreCase("R")) {
			if (inputArray[2].equalsIgnoreCase("o")) {
				// RED OFF * NOT USED *
			} else if (inputArray[2].equalsIgnoreCase("b")) {
				// RED FLASH
				gw.notify(Ticket_Alert, "Ticket Alert", "ALERT ALERT ALERT ALERT");
				gw.notify(Ticket_Alert, "Ticket Alert", "A ticket is about to go stale, please check the queue immediately!!!");
				new AePlayWave("Audio/gameOver.wav").start();
				sleep(3900);
				new AePlayWave("Audio/alarm.wav").start();			
			} else if (inputArray[2].equalsIgnoreCase("s")) {
				// RED SOLID
				gw.notify(Ticket_Stale, "Ticket Alarm",
				"A ticket has gone stale, way to go. A Cardboard box for your belongings can be picked up in the Human Resources department.");
				new AePlayWave("Audio/redAlert.wav").start();	
				sleep(2100);
				new AePlayWave("Audio/bunnyTroubles.wav").start();
			}
		}

// WARNING FUNCTIONS (YELLOW)
		if (inputArray[3].equalsIgnoreCase("Y")) {
			if (inputArray[4].equalsIgnoreCase("o")) {
				// YELLOW OFF  *NOT USED*
			} else if (inputArray[4].equalsIgnoreCase("b")) {
				// YELLOW FLASH
				gw.notify(Ticket_Warning, "Ticket Warning", "Ticket has passed a warning mark, please check queue");
				new AePlayWave("/Audio/alarm2.wav").start();	
				sleep(2000);
				new AePlayWave("/Audio/alarm2.wav").start();			
			} else if (inputArray[4].equalsIgnoreCase("s")) {
				// YELLOW SOLID *NOT USED*
			}
		}

// NEW TICKET FUNCTIONS (GREEN)
		if (inputArray[5].equalsIgnoreCase("G")) {
			if (inputArray[6].equalsIgnoreCase("o")) {
				gw.notify(No_Notifcation, "All Good","Status Zero Queue Empty");
				new AePlayWave("/Audio/ohYeah.wav").start();			
				// GREEN OFF
			} else if (inputArray[6].equalsIgnoreCase("b")) {
				gw.notify(New_Ticket, "New Ticket", "Heads up, new ticket");
				new AePlayWave("/Audio/newTicket.wav").start();			
				// GREEN FLASH
			} else if (inputArray[6].equalsIgnoreCase("s")) {
				// GREEN SOLID
			}
		}
	}
	public static void sleep(int duration) {
		try {
			Thread.sleep(duration);
		} catch (InterruptedException ie) {
			System.out.println("Sleep Exception");
		}
	}

	public static String fetchStuff() {
		BufferedReader br = null;
		String returnedString = null;
		try {
			HttpClient client = new HttpClient();
			GetMethod method = new GetMethod(url);
			method.setFollowRedirects(true);
			// Execute the GET method
			int statusCode = client.executeMethod(method);
			if (statusCode != -1) {
				br = new BufferedReader(new InputStreamReader(method
						.getResponseBodyAsStream()));
				String readLine;
				while (((readLine = br.readLine()) != null)) {
					returnedString = readLine;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnedString;
	}
	
	public static void drawWindow(){
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setTitle("Voxeo Queue");
		window.pack();
		window.setSize(200, 0);
		window.setVisible(true);
	}
}
