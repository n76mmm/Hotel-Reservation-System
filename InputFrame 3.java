import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


// this is the first frame: the user enters the reservation details here
// it implements ActionListener so it can respond to the buttons
public class InputFrame extends JFrame implements ActionListener {

private Hotel hotel;

// text fields for the user input
private JTextField resIdField = new JTextField(15);
private JTextField guestIdField = new JTextField(15);
private JTextField nameField = new JTextField(15);
private JTextField phoneField = new JTextField(15);
private JTextField roomField = new JTextField(15);
// spinner for the number of nights (start 1, min 1, max 365, step 1)
private JSpinner nightsSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 365, 1));

// buttons
private JButton addButton = new JButton("Add Reservation");
private JButton clearButton = new JButton("Clear");

// constructor: builds the window
public InputFrame(Hotel hotel) {
// the title of the window
super(hotel.getHotelName() + " - New Reservation"); 
this.hotel = hotel;

// title label at the top
JLabel title = new JLabel("Make a Reservation", JLabel.CENTER);
title.setFont(new Font("Arial", Font.BOLD, 20));
title.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));

 // panel with a grid: 6 rows and 2 columns (label and field)
JPanel form = new JPanel(new GridLayout(6, 2, 8, 8));
form.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
form.add(new JLabel("Reservation ID:"));
form.add(resIdField);
form.add(new JLabel("Guest ID:"));
form.add(guestIdField);
form.add(new JLabel("Guest Name:"));
form.add(nameField);
form.add(new JLabel("Phone Number:"));
form.add(phoneField);
form.add(new JLabel("Room Number:"));
form.add(roomField);
form.add(new JLabel("Number of Nights:"));
form.add(nightsSpinner);

// panel for the buttons
JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
buttons.add(addButton);
buttons.add(clearButton);

// put the panels in the frame: top, center, bottom
add(title, BorderLayout.NORTH);
add(form, BorderLayout.CENTER);
add(buttons, BorderLayout.SOUTH);

// register the frame as the listener of the buttons
addButton.addActionListener(this);
clearButton.addActionListener(this);
// size of the window
setSize(400, 360);
setLocationRelativeTo(null); // center the window on the screen
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // stop the program when the window is closed
}

// this method runs when a button is clicked
// it calls addReservation, so the NumberFormatException is handled here
 public void actionPerformed(ActionEvent e) {
// Clear button was clicked
if (e.getSource() == clearButton) { 
clearFields();
} else if (e.getSource() == addButton) {             
try {
// try to add the reservation
boolean saved = addReservation();
// show a message: saved or not saved
if (saved)
JOptionPane.showMessageDialog(this, "Reservation added and saved successfully!");
else
JOptionPane.showMessageDialog(this, "Reservation added, but it could not be saved to the file.","File error", JOptionPane.WARNING_MESSAGE);
// empty the fields after adding                                   
clearFields();
} catch (InvalidReservationException ex) {  
// our own exception: show its message
JOptionPane.showMessageDialog(this, ex.getMessage(), "Invalid reservation", JOptionPane.WARNING_MESSAGE);
} catch (NumberFormatException ex) {             
// unchecked exception: the user wrote letters instead of numbers
JOptionPane.showMessageDialog(this, "Reservation ID and Room Number must be numbers.","Invalid input", JOptionPane.ERROR_MESSAGE);
}
}
}

// reads and checks the input, then adds the reservation
// returns true if the reservation was saved in the file
private boolean addReservation() throws InvalidReservationException {
// read the input (parseInt throws NumberFormatException if the text is not a number)
int resId = Integer.parseInt(resIdField.getText().trim());
String guestId = guestIdField.getText().trim();
String name = nameField.getText().trim();
String phone = phoneField.getText().trim();
int roomNumber = Integer.parseInt(roomField.getText().trim());
int nights = (Integer) nightsSpinner.getValue();

// check the name
if (name.isEmpty())
throw new InvalidReservationException("Guest name cannot be empty.");
if (name.contains(",")) // a comma would break the line format in the file
throw new InvalidReservationException("Guest name cannot contain a comma.");

// check the phone: 10 characters and all of them digits
if (phone.length() != 10)
throw new InvalidReservationException("Phone number must be exactly 10 digits.");
for (int i = 0; i < phone.length(); i++) {
if (!Character.isDigit(phone.charAt(i)))
throw new InvalidReservationException("Phone number must contain digits only.");
}

// the reservation ID must be new
if (hotel.searchReservation(resId) != null)
throw new InvalidReservationException("Reservation ID " + resId + " already exists.");

// the room must exist and be available
Room room = hotel.searchRoom(roomNumber);
if (room == null)
throw new InvalidReservationException("Room " + roomNumber + " does not exist.");
if (!room.isAvailable())
throw new InvalidReservationException("Room " + roomNumber + " is already booked.");

// add the reservation to the hotel
if (!hotel.makeReservation(resId, new Guest(guestId, name, phone), room, nights))
throw new InvalidReservationException("Could not add the reservation.");

// save the new reservation to the file (true = saved, false = file error)
return FileManager.saveReservation(hotel.searchReservation(resId));
}

//Empties all the input fields
private void clearFields() {
resIdField.setText("");
guestIdField.setText("");
nameField.setText("");
phoneField.setText("");
roomField.setText("");
nightsSpinner.setValue(1); // back to the default number of nights
}
}
