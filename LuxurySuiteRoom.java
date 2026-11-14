public class LuxurySuiteRoom extends SuiteRoom {
   private boolean butlerService;

   public LuxurySuiteRoom(int roomNumber, double pricePerNight, boolean hasJacuzzi, boolean butlerService) {
      super(roomNumber, pricePerNight, hasJacuzzi);
      this.butlerService = butlerService;
   }

   public double calculateCost(int nights){//Override
      double totalCost = super.calculateCost(nights);
      if (butlerService) {
         totalCost += 200 * nights;
      }
      return totalCost;
   }

   public String getRoomType(){//Override
      return "Luxury Suite Room ";
   }
   public boolean isButlerService() {
      return butlerService;
   }

   public void setButlerService(boolean butlerService) {
      this.butlerService = butlerService;
   }
   
   public void displayInfo(){
      super.displayInfo();
      System.out.println("Butler Service: "+ butlerService);
   }


}
