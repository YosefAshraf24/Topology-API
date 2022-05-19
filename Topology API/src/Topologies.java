
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Topologies {
   private Topology top;
    public Topologies(Topology topology){
        this.top = topology;
    }
    // read file json and save data in string
    private static String readJsonFile(String path) throws IOException {
        String jsTxt = "";
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        String str = bufferedReader.readLine();
        while (str!=null){
            jsTxt += str;
            str = bufferedReader.readLine();
        }
        bufferedReader.close();
        return jsTxt;
    }
    // store topology in the memory
    public  void readJson(String fileName) throws IOException, ParseException {
        ArrayList<Component> components = new ArrayList<>();
        String path = readJsonFile(fileName);
        JSONParser parser = new JSONParser();
        Object object = parser.parse(path);
        JSONObject topology = (JSONObject) object;
        // set ID
        String topologyID  = (String) topology.get("id");
        top.setId(topologyID);
        JSONArray arrComponent = (JSONArray) topology.get("components");
        for(int i = 0;i<arrComponent.size();i++){
            JSONObject objectComponent = (JSONObject) arrComponent.get(i);
            String type = (String) objectComponent.get("type");
            String id = (String) objectComponent.get("id");
            if(type.equals("resistor")) // if type is resistor
            {
                JSONObject resistance = (JSONObject) objectComponent.get("resistance");
                JSONObject netlist = (JSONObject) objectComponent.get("netlist");
                 HashMap<String,Object> resistanceRes = new HashMap<>();
                 HashMap<String,String> netlistRes = new HashMap<>();
                 resistanceRes.put("default",(Object) resistance.get("default"));
                resistanceRes.put("min",(Object) resistance.get("min"));
                resistanceRes.put("max",(Object) resistance.get("max"));
                netlistRes.put("t1",(String) netlist.get("t1"));
                netlistRes.put("t2",(String) netlist.get("t2"));
                Component c = new Component(type,id,resistanceRes,netlistRes);
                components.add(c);
            }
            else if(type.equals("nmos")) // if type is nmos
            {
                JSONObject ml = (JSONObject) objectComponent.get("m(l)");
                JSONObject netlist = (JSONObject) objectComponent.get("netlist");
                HashMap<String,Object> mlRes = new HashMap<>();
                HashMap<String,String> netlistRes = new HashMap<>();
                mlRes.put("default",(Object) ml.get("default"));
                mlRes.put("min",(Object) ml.get("min"));
                mlRes.put("max",(Object) ml.get("max"));
                netlistRes.put("source",(String) netlist.get("source"));
                netlistRes.put("gate",(String) netlist.get("gate"));
                netlistRes.put("drain",(String) netlist.get("drain"));
                Component c = new Component(type,id,mlRes,netlistRes);
                components.add(c);
            }
        }

        top.setComponents(components);


    }
    //Write a given topology from the memory to a JSON file.
    public void writeJson(String fileName,String topologyID) throws IOException {

        JSONObject topology = new JSONObject();
        topology.put("id",topologyID);
        int index = top.getIndex(topologyID);
        JSONArray components = new JSONArray();
       topology.put("components",components);
       // System.out.println(top.getComponents(index).size());
        for(int i = 0;i<top.getComponents(index).size();i++){
            Component c = top.getComponents(index).get(i);
            if(c.getType().equals("resistor")){
                JSONObject resistor = new JSONObject();
                resistor.put("type", "resistor");
                resistor.put("id", c.getId());
                JSONObject resistance = new JSONObject();
                JSONObject netlist = new JSONObject();
                HashMap<String,Object> r = c.getResistance();
                HashMap<String,String> n = c.getNetList();
                resistance.put("default", r.get("default"));
                resistance.put("min", r.get("min"));
                resistance.put("max", r.get("max"));
                netlist.put("t1",n.get("t1"));
                netlist.put("t2",n.get("t2"));
                resistor.put("resistance", resistance);
                resistor.put("netlist", netlist);
                components.add(i,resistor);
            }
            else{
                JSONObject resistor = new JSONObject();
                resistor.put("type", "nmos");
                resistor.put("id", c.getId());
                JSONObject resistance = new JSONObject();
                JSONObject netlist = new JSONObject();
                HashMap<String,Object> r = c.getResistance();
                HashMap<String,String> n = c.getNetList();
                resistance.put("default", r.get("default"));
                resistance.put("min", r.get("min"));
                resistance.put("max", r.get("max"));
                netlist.put("source",n.get("source"));
                netlist.put("gate",n.get("gate"));
                netlist.put("drain",n.get("drain"));
                resistor.put("resistance", resistance);
                resistor.put("netlist", netlist);
                components.add(i,resistor);
            }

        }

        FileWriter fileWriter = new FileWriter(fileName);
        fileWriter.write(topology.toJSONString());
        fileWriter.flush();

    }
    public void queryTopology(){
        int size = top.size();
        System.out.println("Number of topologies are : "+size);
        for(int i=0;i<size;i++)
            System.out.println(top.getId(i));
    }
    public void deleteTopology(String id){
        if(top.hasId(id)){
            int index = top.getIndex(id);

           top.removeElement(index);
        }
        else
            System.out.println("Id not found.");
    }
    public void queryDevices(String id){
        int index = top.getIndex(id);
        int res = 0;
        int nmos = 0;
        for(int i=0;i<top.getComponents(index).size();i++)
        {
            if(top.getComponents(index).get(i).getType().equals("nmos"))
                nmos++;
            else
                res++;
        }
        if(nmos!=0){
            if(res!=0)
                System.out.println("This Topology Contains Resistors and nmos.");
            else
                System.out.println("This Topology Contains nmos only.");
        }
        else
            System.out.println("This Topology Contains Resistors only.");
    }
    public void queryDevicesWithNetlistNode(String ID, String NetlistNode){
        int index = top.getIndex(ID);
        boolean found = false;
        for(int i=0;i<top.getComponents(index).size();i++){
            for(HashMap.Entry<String,String> set : top.getComponents(index).get(i).getNetList().entrySet()) {
                if (set.getValue().equals(NetlistNode)) {
                    found = true;
                    System.out.println("Device " + top.getComponents(index).get(i).getType() + " with id " +
                            top.getComponents(index).get(i).getId() + " is connected to this netlist(" + NetlistNode + ")");
                }
            }
        }
        if(!found)
            System.out.println("There is no devices are connected to this netlist.");
    }
}
