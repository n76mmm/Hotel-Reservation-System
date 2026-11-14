//our own exception (it is checked because it extends Exception)
// we throw it when the reservation data is not valid (empty name, wrong phone, room booked ...)

 public class InvalidReservationException extends Exception {
// takes the message that tells what is wrong
public InvalidReservationException(String message) {
super(message); 
}
}
