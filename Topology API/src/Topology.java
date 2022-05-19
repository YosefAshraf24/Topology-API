
import java.util.ArrayList;

public class Topology {
    private ArrayList<String> id; // store IDs of topologies
    private ArrayList<ArrayList<Component>> components; // store components of topologies

    public Topology(){
        id = new ArrayList<>();
        this.components = new ArrayList<>();
    }

    public int getIndex(String Id){

        return id.indexOf(Id);
    }
    public String getId(int i) {
        return id.get(i);
    }

    public void setId(String i) {
        this.id.add(i);
    }

    public ArrayList<Component> getComponents(int i) {
        return components.get(i);
    }

    public void setComponents(ArrayList<Component> components) {
        this.components.add(components);
    }
    public boolean hasId(String id)
    {
        return this.id.contains(id);
    }
    public void removeElement(int index){
        components.remove(index);
        id.remove(index);
    }
    public int size(){
        return id.size();
    }

}
