import java.io.*;

// Hotel class managing room inventory and reservation linked list with file serialization support
public class Hotel implements Displayable {

   private String hotelName;
   private Node head;
   private Room[] rooms = new Room[50];
   private int roomCount;

   // Constructor to initialize hotel name and linked list head
   public Hotel(String hotelName) { 
      this.hotelName = hotelName;
      this.head = null;
   }

   // Add a room to the hotel room array
   public boolean addRoom(Room r) {
      if (r == null || roomCount >= rooms.length || searchRoom(r.getRoomNumber()) != null) {
         return false;
      }
      rooms[roomCount++] = r;
      return true;
   }

   // Search for a specific room by room number
   public Room searchRoom(int roomNumber) {
      for (int i = 0; i < roomCount; i++) {
         if (rooms[i].getRoomNumber() == roomNumber) {
            return rooms[i];
         }
      }
      return null;
   }

   // Display all currently available rooms in the hotel
   public void displayAvailableRooms() {
      boolean found = false;
      for (int i = 0; i < roomCount; i++) {
         if (rooms[i].isAvailable()) {
            rooms[i].displayInfo();
            found = true;
         }
      }
      if (!found) {
         System.out.println("No available rooms.");
      }
   }

   // Create a new reservation and append it to the linked list
   public boolean makeReservation(int id, Guest guest, Room room, int nights) {
      if (guest == null || room == null || nights <= 0 || !room.isAvailable() || searchReservation(id) != null) {
         return false;
      }
      
      Reservation newRes = new Reservation(id, guest, room, nights);
      Node newNode = new Node(newRes);

      if (head == null) {
         head = newNode;
      } else {
         Node current = head;
         while (current.getNext() != null) {
            current = current.getNext();
         }
         current.setNext(newNode);
      }
      return true;
   }

   // Remove a reservation from the linked list by reservation ID
   public boolean removeReservation(int id) {
      if (head == null) {
         return false;
      }

      if (head.getData().getReservationId() == id) {
         head.getData().cancelReservation();
         head = head.getNext();
         return true;
      }

      Node current = head;
      while (current.getNext() != null) {
         if (current.getNext().getData().getReservationId() == id) {
            current.getNext().getData().cancelReservation();
            current.setNext(current.getNext().getNext());
            return true;
         }
         current = current.getNext();
      }
      return false;
   }

   // Search for a reservation by ID in the linked list
   public Reservation searchReservation(int id) {
      Node current = head;
      while (current != null) {
         if (current.getData().getReservationId() == id) {
            return current.getData();
         }
         current = current.getNext();
      }
      return null;
   }

   // Public wrapper method for calculating total revenue recursively
   public double totalRevenue() {
      return totalRevenue(head);
   }

   // Recursive helper method to calculate total revenue from reservations linked list
   private double totalRevenue(Node current) {
      if (current == null) {
         return 0;
      }
      return current.getData().getTotalCost() + totalRevenue(current.getNext());
   }

   // Display hotel name and all active reservations
   public void displayInfo() {
      System.out.println("Hotel Name: " + hotelName);
      Node current = head;
      while (current != null) {
         current.getData().getGuest().displayInfo();
         System.out.println("Booking type: " + current.getData().getRoom().getRoomType());
         current = current.getNext();
      }
   }

   // Save reservations linked list to a file using object serialization
   public void saveReservationsToFile(String filename) {
      try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
         oos.writeObject(head);
         System.out.println("Reservations saved successfully.");
      } catch (IOException e) {
         System.out.println("Error saving file: " + e.getMessage());
      }
   }

   // Load reservations linked list from a file using object deserialization
   public void loadReservationsFromFile(String filename) {
      try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
         head = (Node) ois.readObject();
         System.out.println("Reservations loaded successfully.");
      } catch (IOException | ClassNotFoundException e) {
         System.out.println("Error loading file.");
      }
   }

   // Get hotel name
   public String getHotelName() {
      return hotelName;
   }

   // Get head node of reservations linked list
   public Node getHead() {
      return head;
   }
}
