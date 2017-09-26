public class BuildWithStatus {

    private String status;
    private int number;

    public BuildWithStatus(String status, int number){
        this.status = status;
        this.number = number;
    }

    public String GetName(){
        return status+" Build:"+String.valueOf(number);
    }


}
