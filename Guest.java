import java.io.Serializable;

// Guest class containing personal information for hotel guests
public class Guest implements Displayable, Serializable {
   private String id;
   private String name;
   private String phone;

   // Constructor to initialize guest attributes
   public Guest(String id, String name, String phone) {
      this.id = id;
      this.name = name;
      this.phone = phone;
   }

   // Display guest information
   public void displayInfo() {
      System.out.println("Guest ID: " + id);
      System.out.println("Name: " + name);
      System.out.println("Phone: " + phone);
   }

   // Get guest ID
   public String getGuestId() {
      return id;
   }

   // Get guest name
   public String getName() {
      return name;
   }

   // Get guest phone number
   public String getPhoneNumber() {
      return phone;
   }

   // Set guest ID
   public void setGuestId(String id) {
      this.id = id;
   }

   // Set guest name
   public void setName(String name) {
      this.name = name;
   }

   // Set guest phone number
   public void setPhoneNumber(String phone) {
      this.phone = phone;
   }
}
