

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        Scanner in = new Scanner(System.in);
        Topology topology= new Topology();
        Topologies usef = new Topologies(topology);
        String num;
        do {
            System.out.println("Enter [1] to Read a topology from a given JSON file.                         ");
            System.out.println("Enter [2] to Write a topology to a JSON file.                                ");
            System.out.println("Enter [3] to Query about all topologies in Memory.                           ");
            System.out.println("Enter [4] to Delete a topology from Memory.                                  ");
            System.out.println("Enter [5] to Query about all devices in a topology.                          ");
            System.out.println("Enter [6] to Query about components connected to netlist node in a topology. ");
            System.out.println("Enter [0] to Exit.                                                           ");
            System.out.println("Enter number : ");
            num = in.nextLine();
            if(num.equals("1")) {
                System.out.println("path to read");
                String path = in.nextLine();
                usef.readJson(path);
            }
            else if (num.equals("2")) {
                System.out.println("Enter topology id");
                String id = in.nextLine();
                System.out.println("path:");
                String path = in.nextLine();
                if(topology.hasId(id))
                    usef.writeJson(path,id);
                else
                    System.out.println("Id not found.");
            }
            else if(num.equals("3")){
                usef.queryTopology();
            }
            else if(num.equals("4")) {
                System.out.println("Enter topology id");
                String id = in.nextLine();
                usef.deleteTopology(id);
            }
            else if(num.equals("5")) {
                System.out.println("Enter topology id:");
                String id = in.nextLine();
                if(topology.hasId(id))
                    usef.queryDevices(id);
                else
                    System.out.println("Id not found.");
            }
            else if(num.equals("6")) {
                System.out.println("Enter topology id:");
                String id = in.nextLine();
                System.out.println("Enter Net list node:");
                String NetlistNode = in.nextLine();
                if(topology.hasId(id))
                    usef.queryDevicesWithNetlistNode(id,NetlistNode);
                else
                    System.out.println("Id not found.");
            }

        }while(!num.equals("0"));


    }
}