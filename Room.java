import java.io.Serializable;

// Abstract room class serving as a base template for various room types
public abstract class Room implements Displayable, Serializable {
   protected int roomNumber;
   protected double pricePerNight;
   protected boolean isAvailable;

   // Constructor to initialize room number and pricing
   public Room(int roomNumber, double pricePerNight) {
      this.roomNumber = roomNumber;
      this.pricePerNight = pricePerNight;
      this.isAvailable = true;
   }
   
   // Abstract method to calculate cost based on number of nights
   public abstract double calculateCost(int nights);
   
   // Abstract method to return the specific type of room
   public abstract String getRoomType(); 

   // Mark the room as booked
   public void bookRoom() {
      this.isAvailable = false;
   }

   // Mark the room as available again
   public void releaseRoom() {
      this.isAvailable = true;
   }

   // Get room number
   public int getRoomNumber() {
      return roomNumber;
   }

   // Get price per night
   public double getPricePerNight() {
      return pricePerNight;
   }

   // Check room availability status
   public boolean isAvailable() {
      return isAvailable;
   }
   
   // Display room details
   public void displayInfo() {
      System.out.println("Room Number: " + roomNumber);
      System.out.println("Price Per Night: " + pricePerNight);
      System.out.println("Available: " + isAvailable);
   }
}
