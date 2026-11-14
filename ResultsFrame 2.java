import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//Frame 2: displays reservation results and handels searches & cancellations;
public class ResultsFrame extends JFrame implements ActionListener{
private Hotel hotel;
private Room[] rooms;
private InputFrame inputFrame;

private JTextField reservationIdField = new JTextField(10);
private JTextArea resultsArea = new JTextArea(16, 55);

private JButton availableButton = new JButton("Available Rooms ");
private JButton allButton = new JButton("All Reservations ");
private JButton findButton = new JButton("Find Reservation ");
private JButton cancelButton = new JButton("Cancel Reservation ");
private JButton guestsButton = new JButton("All Guests ");
private JButton revenueButton = new JButton("Total Revenue ");
private JButton inputButton = new JButton("New Reservation ");


public ResultsFrame(Hotel hotel, Room[] rooms, InputFrame inputFrame){
super(hotel.getHotelName() + " -  Results");
this.hotel=hotel;
this.rooms=rooms;
this.inputFrame=inputFrame;


//Reservation input (id & title)
JLabel title = new JLabel("Hotel Reservation Results",JLabel.CENTER);
JPanel searchPanel = new JPanel(new FlowLayout());
searchPanel.add(new JLabel("Reservation ID: "));
searchPanel.add(reservationIdField);
searchPanel.add(findButton);
searchPanel.add(cancelButton);


JPanel topPanel = new JPanel(new BorderLayout());
topPanel.add(title,  BorderLayout.NORTH);
topPanel.add(searchPanel,  BorderLayout.SOUTH);


//display results
resultsArea.setEditable(false);
JScrollPane scrollPane = new JScrollPane(resultsArea);


// bouttons for other hotel services 
JPanel buttonsPanel = new JPanel(new GridLayout(0, 3, 8, 8));
buttonsPanel.add(availableButton);
buttonsPanel.add(allButton);
buttonsPanel.add(guestsButton);
buttonsPanel.add(revenueButton);
buttonsPanel.add(inputButton);
add(topPanel, BorderLayout.NORTH);
add(scrollPane, BorderLayout.CENTER);
add(buttonsPanel, BorderLayout.SOUTH);

//Register this frame to receive action events from all buttons
findButton.addActionListener(this);
cancelButton.addActionListener(this);
availableButton.addActionListener(this);
allButton.addActionListener(this);
guestsButton.addActionListener(this);
revenueButton.addActionListener(this);
inputButton.addActionListener(this);


setSize(760, 510);
setLocationRelativeTo(null);
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
showAllReservations();
}


//this method runs when any button is cliked
public void actionPerformed(ActionEvent e){
if(e.getSource()== findButton || e.getSource()== cancelButton){
try{
int id= Integer.parseInt(reservationIdField.getText().trim());
            if (e.getSource() == findButton)
               findReservation(id);
            else
               cancelReservation(id);
         } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
               "Please enter a valid numeric reservation ID.",
               "Invalid ID", JOptionPane.ERROR_MESSAGE);
         }
      } else if (e.getSource() == availableButton) {
         showAvailableRooms();
      } else if (e.getSource() == allButton ) {
         showAllReservations();
      } else if (e.getSource() == guestsButton) {
         showAllGuests();
      } else if (e.getSource() == revenueButton) {
         resultsArea.setText("Total revenue: " + hotel.totalRevenue() + " SAR");
      } else if (e.getSource() == inputButton) {
         inputFrame.setVisible(true);
         inputFrame.toFront();
      }
   }

//find a reservaion using the hotels linked list search methoud
private void findReservation(int id) {
   Reservation reservation = hotel.searchReservation(id);

   if (reservation == null)
      resultsArea.setText("Reservation " + id + " was not found.");
   else
      resultsArea.setText(reservationDetails(reservation));
}
private void cancelReservation(int id) {
   Reservation reservation = hotel.searchReservation(id);

   if (reservation == null) {
      JOptionPane.showMessageDialog(this, "Reservation not found");
      return;
   }

   // Keep the reservation in memory if the file cannot be updated.
   if (!FileManager.removeReservation(id)) {
      JOptionPane.showMessageDialog(this,
         "Could not update the reservations file.",
         "File Error", JOptionPane.ERROR_MESSAGE);
      return;
   }

   hotel.removeReservation(id);
   showAllReservations();
   JOptionPane.showMessageDialog(this, "Reservation canceled successfully!");
}

// Display reservations by visiting all nodes in the linked list
private void showAllReservations () {
resultsArea. setText ("ALL RESERVATIONS\n\n");
Node current = hotel.getHead();
if (current == null)
resultsArea.append("No reservations found.. \n" );
while (current != null) {
resultsArea.append(reservationDetails (current.getData ( )));
resultsArea.append("----------------------------------------\n");

current = current.getNext();
}
}
// Display the guests from the reservations linked list.
private void showAllGuests() {
resultsArea.setText("ALL GUESTS:\n\n");
Node current = hotel.getHead();
if (current == null)
resultsArea. append("No guests with active reservations. \n") ;
while (current!= null) {
Guest guest = current.getData().getGuest( ); 
resultsArea.append("Guest ID: " + guest.getGuestId() +"\n" );
resultsArea.append("Name:" + guest.getName() + "\n") ; 
resultsArea.append("Phone: " + guest.getPhoneNumber() +"\n" );
resultsArea.append("-----------------------------------\n");
current = current.getNext() ;
}
}
// Display the available rooms created in the main class.
private void showAvailableRooms () {
resultsArea.setText("AVAILABLE ROOMS\n\n");
int count = 0;
for (int i = 0; i < rooms. length; i++) {
if(rooms[i] != null && rooms[i].isAvailable()){
resultsArea.append("Room: "+ rooms[i].getRoomNumber()+"\n"); 
resultsArea.append("Type: "+ rooms[i].getRoomType()+"\n"); 
resultsArea.append("Price per night: "+ rooms[i].getPricePerNight()+" SAR\n"); 
resultsArea.append("-----------------------------------\n");
count++;

}
}
if(count==0)
resultsArea.append("No available rooms.. \n");
}
// Return the information of one reservation as text.
private String reservationDetails (Reservation reservation) {
return "Reservation ID: " + reservation.getReservationId() +"\n"
+ "Guest ID: " + reservation.getGuest().getGuestId() + "\n"
+ "Guest Name: " + reservation.getGuest().getName() + "\n"
+ "Phone:" + reservation.getGuest().getPhoneNumber() +"\n"
+ "Room: " + reservation.getRoom().getRoomNumber() + " (" + reservation.getRoom().getRoomType() + ") \n"
+ "Nights: " + reservation.getNumberOfNights() + "\n"
+ "Total Cost: " + reservation.getTotalCost() + " SAR\n";
}//end reservationDetails
}//end resultsFrame








