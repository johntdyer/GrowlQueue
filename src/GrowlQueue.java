import java.io.*;
import com.growl.*;

//import org.apache.commons.codec.StringDecoder;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.*;

public class GrowlQueue {
	/*
	 * alarm.wav alarm2.wav bunnyTroubles.wav eightHours.wav gameOver.wav
	 * inconceivable.wav newTicket.wav redAlert.wav
	 */
	static String NO_NOTIFICTION = "All Good", NEW_TICKET = "New Ticket Alert",
			TICKET_WARNING = "Initial Ticket Warning", TICKET_ALERT = "Ticket Stalage Warning",
			TICKET_STALE = "Ticket Stale Alert", CONNECTION_ISSUE = "Connectivity Alert";

	// *************************************************
	// Messages
	// *************************************************
	final static String ticketWarningMessage = "Ticket has passed a warning mark, please check queue",
			newTicketAlertMessage = "Heads up, new ticket",
			ticketWentStaleMessage = "A ticket has gone stale, way to go. A Cardboard box for your belongings can be picked up in the Human Resources department.",
			ticketStaleageWarningMessage = "A ticket is about to go stale, please check the queue immediately!!!",
			allGoodMessage = "Status Zero Queue Empty",
			connectonAlertMessage = "Connectivity lost with Evolution";

	static String url = "http://evolution.voxeo.com/community.bizblog.ticketq.TicketQ/ticketQStatus";

	static String oldValue, newValue;
	// static String newValue;

	static boolean alertedAboutConnectivity = false;

	static GrowlWrapper gw = new GrowlWrapper("Voxeo Support Queue", "Finder", new String[] {
			NEW_TICKET, TICKET_WARNING, TICKET_ALERT, TICKET_STALE, NO_NOTIFICTION,
			CONNECTION_ISSUE }, new String[] { NO_NOTIFICTION });

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		MainWindow myInterface = new MainWindow();

		// drawWindow();

		while (true) {
			newValue = fetchStuff();
			if (newValue == "error") {
				if (alertedAboutConnectivity == false) {
					gw.notify(CONNECTION_ISSUE, CONNECTION_ISSUE, connectonAlertMessage);
					alertedAboutConnectivity = true;
				}
				sleep(20000);// We have alerted, retry every 20 seconds
			} else {
				if (newValue.equals(oldValue)) {
					// System.out.println("nothing to see here");
				} else {
					// System.out.println("new event");
					alert(newValue);
				}
				alertedAboutConnectivity = false; // Reset alert for
				// connectivity
				sleep(15000);
				oldValue = newValue;
			}
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
				gw.notify(TICKET_ALERT, TICKET_ALERT, "ALERT ALERT ALERT ALERT");
				gw.notify(TICKET_ALERT, TICKET_ALERT, ticketStaleageWarningMessage);
				new AePlayWave("Audio/gameOver.wav").start();
				sleep(3900);
				new AePlayWave("Audio/alarm.wav").start();
			} else if (inputArray[2].equalsIgnoreCase("s")) {
				// RED SOLID
				gw.notify(TICKET_STALE, TICKET_STALE, ticketWentStaleMessage);
				new AePlayWave("../audio/redAlert.wav").start();
				sleep(2100);
				new AePlayWave("../audio/bunnyTroubles.wav").start();
			}
		}

		// WARNING FUNCTIONS (YELLOW)
		if (inputArray[3].equalsIgnoreCase("Y")) {
			if (inputArray[4].equalsIgnoreCase("o")) {
				// YELLOW OFF *NOT USED*
			} else if (inputArray[4].equalsIgnoreCase("b")) {
				// YELLOW FLASH
				gw.notify(TICKET_WARNING, TICKET_WARNING, ticketWarningMessage);
				new AePlayWave("/Audio/alarm2.wav").start();
				sleep(2000);
				new AePlayWave("/Audio/alarm2.wav").start();
			} else if (inputArray[4].equalsIgnoreCase("s")) {
				// YELLOW SOLID *NOT USED*
			}
		}
		// NEW TICKET FUNCTIONS (GREEN)
		if (inputArray[5].equalsIgnoreCase("G")) {
			if (	   inputArray[6].equalsIgnoreCase("o")   // Green 
					&& inputArray[4].equalsIgnoreCase("o")   // Yellow
					&& inputArray[2].equalsIgnoreCase("o"))	 // Red 
			{
				gw.notify(NO_NOTIFICTION, NO_NOTIFICTION, allGoodMessage);
				new AePlayWave("ohYeah.wav").start();
				// GREEN OFF
			} else if (inputArray[6].equalsIgnoreCase("b")) {
				gw.notify(NEW_TICKET, NEW_TICKET, newTicketAlertMessage);
				new AePlayWave("/audio/newTicket.wav").start();
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
				br = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream()));
				String readLine;
				while (((readLine = br.readLine()) != null)) {
					returnedString = readLine;
				}
			}
		} catch (Exception e) {
			System.err.println("No Connection");
			// e.printStackTrace();
			returnedString = "error";
		}
		return returnedString;
	}

}
