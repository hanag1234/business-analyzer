//import needed packages
import java.util.ListIterator;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import list.ArrayList;
import list.LinkedList;
import java.io.File;
import java.io.FileNotFoundException;

class RegisteredBusinessLocation {
   //define variables
   String licCodeDescription, supervisorDistrict, neighborhoodsAnalysisBoundaries,businessCorridor,businessLocation,uniqueid,sfFindNeighborhoods,currentPoliceDistricts,currentSupervisorDistricts,analysisNeighborhoods,neighborhoods,locationId,businessAccountNumber,ownershipName,dbaName,streetAddress,city,state,sourceZipcode,businessStartDate,businessEndDate,locationStartDate,mailState, naicsCode, naicsCodeDescription, parkingTax,transientOccupancyTax,licCode,locationEndDate,mailAddress,mailCity,mailZipcode;
}

public class BusinessAnalyzer {
   private static final String validZipcode = "941[0-9]{2}";

   public static void main(String[] args) {
      //get input args 
      String route = args[0],key = args[1];
      Scanner sc = new Scanner(System.in);
      //Create a Hashtable to store RegisteredBusinessLocation objects
      Hashtable<String, ListIterator<RegisteredBusinessLocation>> locations = readRegisteredBusinessLocations(route, key);
      Queue<String> history = new java.util.LinkedList<String>();
      boolean done = false;
      // Loop until done flag is set to true
      while (!done) {
         System.out.print("Command: ");
         String input= sc.nextLine();
         done = executeCommand(input, locations, history, key);
      }

      sc.close();
   }
   // Function to read RegisteredBusinessLocations from a file
   private static Hashtable<String, ListIterator<RegisteredBusinessLocation>> readRegisteredBusinessLocations(String route, String key) {
      // Create a Hashtable to store RegisteredBusinessLocation objects
      Hashtable<String, ListIterator<RegisteredBusinessLocation>> locations = new Hashtable<String, ListIterator<RegisteredBusinessLocation>>();
      Scanner sc = tryToScanFile(route);
      sc.nextLine();

      while (sc.hasNextLine()) {
         // Loop through each line of the file
         RegisteredBusinessLocation location = readRegisteredBusinessLocation(sc.nextLine());
         putListInHashtableIfAbsent(locations, location.naicsCode, key);
         locations.get(location.naicsCode).add(location);
      }

      sc.close();
      return locations;
   }
   private static Scanner tryToScanFile(String route) {
      try {
         return new Scanner(new File(route));
      } catch (FileNotFoundException e) {
         throw new IllegalArgumentException("Pathname was not found");
      }
   }

/**This method takes in a String representing a line of data from a CSV file and returns a RegisteredBusinessLocation object.

@param line a String representing a line of data from a CSV file.

@return a RegisteredBusinessLocation object created from the data in the input line.
*/
   private static RegisteredBusinessLocation readRegisteredBusinessLocation(String line) {
    RegisteredBusinessLocation location = new RegisteredBusinessLocation();
    String[] fields = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

    for (int i = 0; i < fields.length; i++) {
        String value = fields[i].replaceAll("\"", "");
        switch (i) {
            case 0: location.locationId = value; break;
            case 1: location.businessAccountNumber = value; break;
            case 2: location.ownershipName = value; break;
            case 3: location.dbaName = value; break;
            case 4: location.streetAddress = value; break;
            case 5: location.city = value; break;
            case 6: location.state = value; break;
            case 7: location.sourceZipcode = value; break;
            case 8: location.businessStartDate = value; break;
            case 9: location.businessEndDate = value; break;
            case 10: location.locationStartDate = value; break;
            case 11: location.locationEndDate = value; break;
            case 12: location.mailAddress = value; break;
            case 13: location.mailCity = value; break;
            case 14: location.mailZipcode = value; break;
            case 15: location.mailState = value; break;
            case 16: location.naicsCode = value; break;
            case 17: location.naicsCodeDescription = value; break;
            case 18: location.parkingTax = value; break;
            case 19: location.transientOccupancyTax = value; break;
            case 20: location.licCode = value; break;
            case 21: location.licCodeDescription = value; break;
            case 22: location.supervisorDistrict = value; break;
            case 23: location.neighborhoodsAnalysisBoundaries = value; break;
            case 24: location.businessCorridor = value; break;
            case 25: location.businessLocation = value; break;
            case 26: location.uniqueid = value; break;
            case 27: location.sfFindNeighborhoods = value; break;
            case 28: location.currentPoliceDistricts = value; break;
            case 29: location.currentSupervisorDistricts = value; break;
            case 30: location.analysisNeighborhoods = value; break;
            case 31: location.neighborhoods = value; break;
        }
    }

    return location;
}

/*This method puts a new list in the locations Hashtable with the given naicsCode as the key
if the key is not already in the Hashtable.
The type of list to be created is determined by the value of the key parameter:
"AL": An ArrayList will be created
"LL": A LinkedList will be created
@param locations: A Hashtable where the lists will be added
@param naicsCode: A String representing the naicsCode which will be used as the key in the Hashtable
@param key: A String determining which type of list to create: "AL" for ArrayList, "LL" for LinkedList
@throws IllegalArgumentException: If the key parameter is neither "AL" nor "LL"
*/
   private static void putListInHashtableIfAbsent(Hashtable<String, ListIterator<RegisteredBusinessLocation>> locations, String naicsCode, String key) {
      switch (key) {
         case "AL": locations.putIfAbsent(naicsCode, new ArrayList<RegisteredBusinessLocation>()); break;
         case "LL": locations.putIfAbsent(naicsCode, new LinkedList<RegisteredBusinessLocation>()); break;
         default: throw new IllegalArgumentException("Flag is not LL or AL");
      }
   }

   /*

Executes a command based on the user input and updates the history.
@param input the user input command
@param locations a Hashtable containing RegisteredBusinessLocation objects
@param history a Queue containing the history of user input commands
@param key a String indicating the type of ListIterator to be used for the locations Hashtable
@return a boolean indicating if the program should continue running or not
*/

   private static boolean executeCommand(String input, Hashtable<String, ListIterator<RegisteredBusinessLocation>> locations, Queue<String> history, String key) {
      Scanner sc = new Scanner(input).useDelimiter(" ");
      boolean done;
      done = false;

      switch (sc.next().toLowerCase()) {
         case "summary": printTheSummary(locations, key); break;
         case "history": printTheHistory(history); break;
         case "zip": tryZip(sc, locations, key); break;
         case "naics": tryNAICS(sc, locations, key); break;
         case "quit": done = true; break;
         default: System.out.println("IllegalStateException: Command is not naics, zip, summary, history, or quit");
      }

      history.add(input);
      sc.close();
      return done;
   }

   private static void tryZip(Scanner sc, Hashtable<String, ListIterator<RegisteredBusinessLocation>> locations, String key) {
   String zip = sc.hasNext() ? sc.next() : "";
   printZip(locations, zip, key);
}

private static void printZip(Hashtable<String, ListIterator<RegisteredBusinessLocation>> businessLocations, String sourceZipcode, String key) {
    //create 3 sets

    Set<String> numbers = new HashSet<>();
    Set<String> codes = new HashSet<>();
    Set<String> neighborhoods = new HashSet<>();
    // Loop through all the locations in the hashtable and add the relevant data to the sets
    businessLocations.values().forEach(locations -> {
        resetList(locations);
        locations.forEachRemaining(location -> {
            if (sourceZipcode.equals(location.sourceZipcode)) {
                numbers.add(location.locationId);
                codes.add(location.naicsCodeDescription);
                neighborhoods.add(location.neighborhoodsAnalysisBoundaries);
            }
        });
    });

    System.out.printf("%s Business Summary\n", sourceZipcode);
    System.out.printf("Total Businesses: %d\n", numbers.size());
    System.out.printf("Business Types: %d\n", codes.size());
    System.out.printf("Neighborhood: %d\n", neighborhoods.size());
}
   private static void tryNAICS(Scanner sc, Hashtable<String, ListIterator<RegisteredBusinessLocation>> locations, String key) {
      int naicsCode = sc.nextInt();
      if (naicsCode > 0) {
         printNAICS(locations, naicsCode, key);
      } else {
      System.out.println("NAICS Code is empty or invalid");
      }
   }

   private static void printNAICS(Hashtable<String, ListIterator<RegisteredBusinessLocation>> businessLocations, int naicsCode, String key) {
      // Initialize sets to store unique values
      Set<String> numbers = new HashSet<>();
      Set<String> zipcodes = new HashSet<>();
      Set<String> neighborhoods = new HashSet<>();
      // Iterate through all naics code ranges in the hashtable
      for (Enumeration<String> e = businessLocations.keys(); e.hasMoreElements();) {
         String naicsCodeRange = e.nextElement();
         Scanner sc = new Scanner(naicsCodeRange).useDelimiter("-");
         
         if (sc.hasNextInt()) {
            int smallest = sc.nextInt();
            int largest = sc.hasNextInt() ? sc.nextInt() : smallest;

            if (smallest <= naicsCode && naicsCode <= largest) {
               ListIterator<RegisteredBusinessLocation> locations = businessLocations.get(naicsCodeRange);
               resetList(locations);

               locations.forEachRemaining(location -> { 
                  numbers.add(location.locationId);
                  zipcodes.add(zipWriter(location.mailZipcode));
                  neighborhoods.add(location.neighborhoodsAnalysisBoundaries);
               });
            }
         }

         sc.close();
      }

      System.out.printf("Total Businesses: %d\n", numbers.size());
      System.out.printf("Zip Codes: %d\n", zipcodes.size());
      System.out.printf("Neighborhood: %d\n", neighborhoods.size());
   }

   private static String zipWriter(String zipcode){
      Scanner sc = new Scanner(zipcode).useDelimiter("-| ");
      zipcode = sc.hasNext() ? sc.next() : "";
      zipcode = zipcode.matches(validZipcode) ? zipcode : "";
      sc.close();
      return zipcode;
   }
   /*
Prints a summary of the businesses in the given hashtable.
@param businessLocations Hashtable containing the business locations
@param key the type of ListIterator to use for iterating through the list of locations
*/
   private static void printTheSummary(Hashtable<String, ListIterator<RegisteredBusinessLocation>> businessLocations, String key) {
    Set<String> numbers = new HashSet<>();
    int closedBusinesses = 0;
    int newBusinessInLastYear = 0;
    int total = 0;
    
    for (ListIterator<RegisteredBusinessLocation> locations : businessLocations.values()) {
        resetList(locations);

        while (locations.hasNext()) {
            RegisteredBusinessLocation location = locations.next();
            total++;

            if (numbers.add(location.businessAccountNumber)) {
                closedBusinesses += location.mailAddress.equals("***Administratively Closed") ? 1 : 0;
                newBusinessInLastYear += isNewBusinessInLastYear(location.businessStartDate) ? 1 : 0;
            }
        }
    }

    System.out.printf("Total Businesses: %d\n", total);
    System.out.printf("Closed Businesses: %d\n", closedBusinesses);
    System.out.printf("New Business in last year: %d\n", newBusinessInLastYear);
}

   private static void resetList(ListIterator<RegisteredBusinessLocation> locations) {
      while (locations.hasPrevious()) {
         locations.previous();
      }
   }

   private static boolean isNewBusinessInLastYear(String businessStartDate) {
      try (Scanner sc = new Scanner(businessStartDate).useDelimiter("/")) {
         String year = null;
         while (sc.hasNext()) year = sc.next();
         return year.equals("2022");
      }
   }

   private static void printTheHistory(Queue<String> history) {
    for (String input : history) {
        System.out.println(input);
    }
}
}