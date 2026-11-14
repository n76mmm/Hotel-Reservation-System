public class DoubleRoom extends Room{
   private int numberOfBeds;
   public DoubleRoom (int roomNumber,double pricePerNight,int numberOfBeds){
      super(roomNumber,pricePerNight);
      this.numberOfBeds=numberOfBeds;
   }

   public double calculateCost(int nights){//Override
      return pricePerNight*nights;
   }
   public int getNumberOfBeds(){
      return numberOfBeds;
   }

   public String getRoomType(){//Override
      return "Double Room ";
   }
   
   public void displayInfo(){//Override
      super.displayInfo();
      System.out.println("Number of beds: "+ numberOfBeds);
   }


   public void setNumberOfBeds(int numberOfBeds){
      this.numberOfBeds=numberOfBeds;
   }

}