import java.io.IOException;
import javax.swing.*;
public class HotelTester{
public static void main (String[] args){
 SwingUtilities.invokeLater (new Runnable(){
 public void run(){
 
 Hotel ritz = new Hotel("The Ritz");
 
 //room objects
 
Room[] rooms= new Room[6];
rooms[0]= new SingleRoom(101, 110.0 , true);
rooms[1]= new DoubleRoom(202, 220.0 , 2);
rooms[2]= new SuiteRoom(303, 330.0 , true);
rooms[3]= new SuiteRoom(404, 440.0 , false);
rooms[4]= new LuxurySuiteRoom(505, 550.0, true , false);
rooms[5]= new LuxurySuiteRoom(606, 660.0 , false, true);

for(int i=0; i<rooms.length;i++)
 ritz.addRoom(rooms[i]);
 
 try{
 FileManager.loadReservations(ritz);
 }catch(IOException e){
 JOptionPane.showMessageDialog(null, "could not load reservations: "+ e.getMessage(),
  "File error ",JOptionPane.ERROR_MESSAGE );
  }
   //let both frames use the same Hotel object and its linked list
   InputFrame inputFrame = new InputFrame(ritz);
   inputFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
   ResultsFrame resultsFrame = new ResultsFrame(ritz, rooms, inputFrame);
   
   resultsFrame.setVisible(true);
   inputFrame.setVisible(true);
    }//end run
 });
 
}//end main


}//end class