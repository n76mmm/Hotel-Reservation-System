public class SuiteRoom extends Room {
   private boolean hasJacuzzi;

   public SuiteRoom(int roomNumber, double pricePerNight, boolean hasJacuzzi) {
      super(roomNumber, pricePerNight);
      this.hasJacuzzi = hasJacuzzi;
   }

   public double calculateCost(int nights) {//Override
      double totalCost = pricePerNight * nights;
      if (hasJacuzzi) {
         totalCost += 150 * nights;
      }
      return totalCost;
   }
    
   public String getRoomType(){//Override
      return "Suite Room ";
   }

   public boolean isHasJacuzzi() {
      return hasJacuzzi;
   }

   public void setHasJacuzzi(boolean hasJacuzzi) {
      this.hasJacuzzi = hasJacuzzi;
   }
    
   public void displayInfo(){
      super.displayInfo();
      System.out.println("Has Jacuzzi: "+hasJacuzzi);
   }
}




