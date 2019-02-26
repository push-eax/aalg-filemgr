import java.io.*;
import java.net.*;
import java.util.Scanner;

/*
   Kiernan Roche
   CSCI 347
   Professor David Keil
   Competency 0.0e: File Management Program

   This is an implementation of the file management program described in competency 0.0e. It can add and remove objects from an in-memory data structure, and optionally write and read data from disk. This is achieved using Java object serialization.

   Detailed documentation, including command usage and an example session, can be found in the README.

   The command parsing code in the FileMgr class was reused from an earlier project (https://github.com/push-eax/coffirc).
*/

public class FileMgr {
    private static NodeCollection collection;

    public static void main(String[] args) throws Exception {
        String VERSION = "0.1";
        // store user input
        String rawInput;

        System.out.println("filemgr v"+VERSION);
        
        try (Scanner scanner = new Scanner(System.in)) {
            // input loop
            while (true) {
                // prompt
                System.out.print("$> ");
                rawInput = scanner.nextLine();

                if (rawInput.length() > 0) {
                    String[] commArr = rawInput.split(" ");
                    String command = commArr[0];

                    String commands[] = {"add", "delete", "list", "flush", "init", "searchf", "searchs", "sortf", "sorts"};
                    if (command.equals("exit")) {
                        // exit the program
                        return;
                    }  else {
                        int cindex = -1;
                        for(int i = 0; i < commands.length; i++){
                            if(command.equals(commands[i])) cindex = i;
                        }
                        if (collection == null && !command.equals("init")) {
                            // don't do anything if NodeCollection hasn't been initialized yet
                            System.out.println("NodeCollection has not been initialized yet. Use the \"init\" command to initialize a new NodeCollection.");
                            continue;
                        }
                        switch(cindex){
                            case 0: // add object
                                System.out.println("Added " + collection.addNode(Integer.parseInt(commArr[1]), commArr[2]) + "\n{\n\t\"id\": " + commArr[1] + "\n\t\"name\": \"" + commArr[2] + "\"\n}");
                                break;
                            case 1: // delete object
                                System.out.println("Deleted " + collection.delNode(Integer.parseInt(commArr[1])));
                                break;
                            case 2: // list objects
                                Node[] nodelist = collection.listNodes(new Node[collection.getSize()]);
                                System.out.println(collection);
                                System.out.println("{");
                                for (Node node : nodelist) {
                                    System.out.println("\t{");
                                    System.out.println("\t\t\"id\": " + Integer.toString(node.id));
                                    System.out.println("\t\t\"name\": \"" + node.name + "\"");
                                    System.out.println("\t}");
                                }
                                System.out.println("}");

                                break;
                            case 3: // flush objects to file
                                collection.writeNodes();
                                System.out.println(collection + " flushed to " + collection.file.getPath());
                                break;
                            case 4: // initialize new NodeCollection
                                collection = new NodeCollection(commArr[1]);
                                collection.readNodes();
                                System.out.println("New " + collection + " using file \"" + commArr[1] + "\""); 
                                break;
                            case 5: // search fast
                                Node result_fast = collection.searchFast(Integer.parseInt(commArr[1]));
                                System.out.println("Searched " + collection + " in " + Double.toString(collection.runtime) + " seconds");
                                if (result_fast == null) {
                                    System.out.println("Node " + commArr[1] + " not found.");
                                } else {
                                    System.out.println("{");
                                    System.out.println("\t{");
                                    System.out.println("\t\t\"id\": " + Integer.toString(result_fast.id));
                                    System.out.println("\t\t\"name\": \"" + result_fast.name + "\"");
                                    System.out.println("\t}");
                                    System.out.println("}");
                                }
                                break;
                            case 6: // search slow
                                // It seems that Java does not respect scope within switch:case statements.
                                // I couldn't declare variables with the same name in two case statements without throwing compiler errors.
                                Node result_slow = collection.searchSlow(Integer.parseInt(commArr[1]));
                                System.out.println("Searched " + collection + " in " + Double.toString(collection.runtime) + " seconds");
                                if (result_slow == null) {
                                    System.out.println("Node " + commArr[1] + " not found.");
                                } else {
                                    System.out.println("{");
                                    System.out.println("\t{");
                                    System.out.println("\t\t\"id\": " + Integer.toString(result_slow.id));
                                    System.out.println("\t\t\"name\": \"" + result_slow.name + "\"");
                                    System.out.println("\t}");
                                    System.out.println("}");
                                }
                                break;
                            case 7: // sort fast
                                collection.sortFast();
                                System.out.println("Sorted " + collection + " in " + Double.toString(collection.runtime) + " seconds");
                                break;
                            case 8: // sort slow
                                collection.sortSlow();
                                System.out.println("Sorted " + collection + " in " + Double.toString(collection.runtime) + " seconds");
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        }
    }
}

