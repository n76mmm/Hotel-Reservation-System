import java.io.*;
import java.util.Scanner;

// this class saves the reservations in a text file and reads them back
// each reservation is one line: reservationId,guestId,name,phone,roomNumber,nights
 
public class FileManager {
public static final String FILE_NAME = "reservations.txt";

// saves one reservation at the end of the file
// the checked exception IOException is handled here with try-catch
// returns true if it was saved and false if there was an error
  
public static boolean saveReservation(Reservation r) {
try {
PrintWriter out = new PrintWriter(new FileWriter(FILE_NAME, true)); // true = append
out.println(r.getReservationId() + "," + r.getGuest().getGuestId() + ","
+ r.getGuest().getName() + "," + r.getGuest().getPhoneNumber() + ","
+ r.getRoom().getRoomNumber() + "," + r.getNumberOfNights());
// checkError tells us if the writing failed
boolean ok = !out.checkError();                   
// close the file so the data is saved
out.close();
return ok;
} catch (IOException e) {
System.out.println("Error while saving the reservation: " + e.getMessage());
return false;
}
}

// removes one reservation from the file (used when a reservation is cancelled)
// we read all the lines, keep every line except the removed one, then write the file again

public static boolean removeReservation(int reservationId) {
try {
File file = new File(FILE_NAME);
// if there is no file, there is nothing to remove
if (!file.exists())
return true;
String kept = "";
Scanner in = new Scanner(file);
while (in.hasNextLine()) {
String line = in.nextLine();
if (!line.startsWith(reservationId + ",")) // keep every line except the removed reservation
kept = kept + line + "\n";
}
in.close();
PrintWriter out = new PrintWriter(new FileWriter(FILE_NAME)); // overwrite the file with the kept lines
out.print(kept);
boolean ok = !out.checkError(); 
out.close();
return ok;
} catch (IOException e) {
System.out.println("Error while updating the file: " + e.getMessage());
return false;
}
}

// reads the file and rebuilds the reservations inside the hotel
// returns how many reservations were loaded
public static int loadReservations(Hotel hotel) throws IOException {
File file = new File(FILE_NAME);
// first run: no file yet
if (!file.exists()) 
return 0;
// counts the reservations rebuilt successfully
int loaded = 0;
Scanner in = new Scanner(file);
// read the file line by line
while (in.hasNextLine()) { 
String line = in.nextLine().trim();
if (line.isEmpty())
continue;
String[] p = line.split(","); 
try {
int resId = Integer.parseInt(p[0]);
Guest guest = new Guest(p[1], p[2], p[3]);
Room room = hotel.searchRoom(Integer.parseInt(p[4]));
int nights = Integer.parseInt(p[5]);
if (room == null)
System.out.println("Skipped (room " + p[4] + " does not exist): " + line);
else if (hotel.makeReservation(resId, guest, room, nights))
loaded++;
else
System.out.println("Skipped (reservation could not be rebuilt): " + line);
} catch (NumberFormatException e) {
System.out.println("Skipped a damaged line in the file: " + line);
} catch (ArrayIndexOutOfBoundsException e) {
System.out.println("Skipped a damaged line in the file: " + line);
}
}
in.close();
return loaded;
}
}
