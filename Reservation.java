import java.io.Serializable;

// Reservation class to manage booking details between guest and room
public class Reservation implements Displayable, Serializable {
   private int reservationId;
   private Guest guest;
   private Room room;
   private int numberOfNights;

   // Constructor to create a reservation and book the associated room
   public Reservation(int id, Guest g, Room r, int nights) {
      this.reservationId = id;
      this.guest = g;
      this.room = r;
      this.numberOfNights = nights;
      this.room.bookRoom();
   }

   // Calculate total cost for the stay
   public double getTotalCost() {
      return room.calculateCost(numberOfNights);
   }

   // Cancel the reservation and release the room
   public void cancelReservation() {
      room.releaseRoom();
   }

   // Display reservation details
   public void displayInfo() {
      System.out.println("Reservation ID: " + reservationId);
      guest.displayInfo();
      room.displayInfo();
      System.out.println("Number Of Nights: " + numberOfNights);
      System.out.println("Total cost: " + getTotalCost());
   }

   // Get reservation ID
   public int getReservationId() {
      return reservationId;
   }

   // Get guest object
   public Guest getGuest() {
      return guest;
   }

   // Get room object
   public Room getRoom() {
      return room;
   }

   // Get number of nights
   public int getNumberOfNights() {
      return numberOfNights;
   }
}
