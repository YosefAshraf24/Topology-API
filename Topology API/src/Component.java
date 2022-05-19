import java.util.HashMap;

public class Component {
   private String type;
   private String id;
   private  HashMap<String,Object> resistance;
   private HashMap<String,String> netList;

   public Component(String type,String id,HashMap<String, Object> resistance, HashMap<String, String> netList){
       this.type = type;
       this.id = id;
       this.resistance = resistance;
       this.netList = netList;
   }
    /* Getter and Setter */

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public HashMap<String, String> getNetList() {
        return netList;
    }

    public void setNetList(HashMap<String, String> netList) {
        this.netList = netList;
    }


    public HashMap<String, Object> getResistance() {
        return resistance;
    }

    public void setResistance(HashMap<String, Object> resistance) {
        this.resistance = resistance;
    }
}
