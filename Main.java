//Ahmed Reshad

//This is a program that when executed projects a game about survival. 
//The console prompts the user to type and enter numbers to choose a difficulty and
//progress in the game. 
//The game has random events that occur and the goal of the game is to survive for 10 days
//and health is lost each day by defualt so the choice to rest needs to be used wisely.
//If the program ends without the congragulations message, the user has died.

//This project allowed me to learn how time consuming it is to make a game in java. Simple errors can result in unnecessary debugging.
//I also learned how to put together everything that we have learned in the year and make a product that puts almost every unit into use.
//Lastly, I learned how to make a recursive method, since I did not really know how to before hand.

//import statements
import java.util.Random;
import java.util.Scanner;

// Superclass representing common properties of scavenging items and random events
abstract class GameObject {
    protected String name;

    public GameObject(String name) {
        this.name = name;
    }

    // Abstract method 1 to get a description of the object
    public abstract String getDescription();
}

// Subclass representing scavenging items and inherits name
class Item extends GameObject {
    public Item(String name) {
        super(name);
    }
//overrides method from parent class
    @Override
    public String getDescription() {
        return "You find some " + name + "!";
    }
}

// Subclass representing random events and inherits name
class Event extends GameObject {
    public Event(String name) {
        super(name);
    }

    @Override
    public String getDescription() {
        return "A wild " + name + "s you!";
    }
}

// Parent class representing the basic functionality of a survival game
abstract class SurvivalGame {
    protected static int MAX_HEALTH = 100;
    protected static int MAX_DAYS = 10;
    protected static int MAX_EVENT_CHANCE = 30;

    protected int health = MAX_HEALTH;
    protected int daysSurvived = 0;
  //Arrays that store outcomes and possibilities
    protected final GameObject[] scavengingResults;
    protected final GameObject[] randomEvents;

    // Constructor to initialize scavengingResults and randomEvents arrays
    public SurvivalGame() {
        scavengingResults = new GameObject[]{
                new Item("food"),
                new Item("water"),
                new Item("medical supplies")
        };

        randomEvents = new GameObject[]{
                new Event("animal attack"),
                new Event("first aid kit"),
                new Event("stash of food")
        };
    }

    //Recuruisve Method 2 to simulate health loss over multiple days using recursion
    protected void loseHealth(int days) {
        if (days <= 0) {
            return; // Base case: Stop recursion when there are no more days left
        }

        // Simulate health regeneration for the current day
        int loseAmount = 5; // Example: Regenerate 5 health points per day
        health = Math.min(health - loseAmount, MAX_HEALTH); // Ensure health doesn't exceed maximum

        System.out.println("You lost " + loseAmount + " health points. Health: " + health);

        // Recursive call for the next day
        loseHealth(days - 1);
    }


    // Method 3 to handle random events during gameplay
    protected void handleRandomEvent(Random random) {
        if (random.nextInt(100) < MAX_EVENT_CHANCE) {
            int eventIndex = random.nextInt(randomEvents.length);
            GameObject event = randomEvents[eventIndex];
            System.out.println(event.getDescription());
            switch (eventIndex) {
                //local variables in method
                case 0:
                    int damage = random.nextInt(20) + 10;
                    health -= damage;
                    System.out.println("You lose " + damage + " health points.");
                    break;
                case 1:
                    int heal = random.nextInt(10) + 5;
                    health = Math.min(health + heal, MAX_HEALTH);
                    System.out.println("You gain " + heal + " health points.");
                    break;
                case 2:
                    int food = random.nextInt(30) + 10;
                    System.out.println("You collect " + food + " units of " + scavengingResults[eventIndex].name + ".");
                    break;
            }
        }
    }

    // Method 4 to get user input
    protected int getUserInput(Scanner scanner) {
        return scanner.nextInt();
    }

    // Method 5 to process user choice
    protected void processUserChoice(Random random, int choice) {
        switch (choice) {
            //local variables in method
            case 1:
                int scavengingResultIndex = random.nextInt(scavengingResults.length);
                GameObject scavengingResult = scavengingResults[scavengingResultIndex];
                System.out.println(scavengingResult.getDescription());
                break;
            case 2:
                int restAmount = random.nextInt(20) + 10;
                health = Math.min(health + restAmount, MAX_HEALTH);
                System.out.println("You regain " + restAmount + " health points.");
                break;
            default:
                System.out.println("Invalid choice. Try again.");
                processUserChoice(random, getUserInput(new Scanner(System.in)));
        }
    }

    // Method 6 to start the game
    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        //random object intialization
        Random random = new Random();
        health = MAX_HEALTH;

        System.out.println("Welcome to Survival Game!");
    //Conditional statemetents to handle game mechanics
        while (health > 0 && daysSurvived < MAX_DAYS) {
            
            daysSurvived++;
            System.out.println("\nDay " + daysSurvived + ":");
            System.out.println("Health: " + health);

            handleRandomEvent(random);

            if (health <= 0) {
                System.out.println("\nGame over! You didn't survive.");
                break;
            }

            // Call Recursive regenerateHealth() method after each day
            loseHealth(1);

            System.out.println("\nWhat would you like to do?");
            System.out.println("1. Scavenge for supplies");
            System.out.println("2. Rest in the shelter");
            System.out.print("Enter your choice: ");

            int choice = getUserInput(scanner);
            processUserChoice(random, choice);
            
            
        }
        
        if (health > 0) {
            System.out.println("\nCongratulations! You survived for " + daysSurvived + " days.");
        }
    }
}

// Subclass representing easy difficulty level inherits parent class
class EasySurvivalGame extends SurvivalGame {
    public EasySurvivalGame() {
        super(); // Call the constructor of the superclass to initialize arrays
        
    }
}

// Subclass representing medium difficulty level inherits parent class
class MediumSurvivalGame extends SurvivalGame {
    public MediumSurvivalGame() {
        MAX_HEALTH = 50;
        
    }
}

// Subclass representing hard difficulty level inherits parent class
class HardSurvivalGame extends SurvivalGame {
    public HardSurvivalGame() {
        MAX_HEALTH = 20;
    }
}

// Main class to run the game
public class Main {
    public static void main(String[] args) {
        //makes a scanner object
        Scanner scanner = new Scanner(System.in);

        // Ask the player to choose a difficulty level
        System.out.println("Choose a difficulty level:");
        System.out.println("1. Easy");
        System.out.println("2. Medium");
        System.out.println("3. Hard");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();

        // Start the game based on the player's choice
        switch (choice) {
            case 1:
                //object
                SurvivalGame easyGame = new EasySurvivalGame();
                easyGame.startGame();
                break;
            case 2:
                //object
                SurvivalGame mediumGame = new MediumSurvivalGame();
                mediumGame.startGame();
                break;
            case 3:
                //object
                SurvivalGame hardGame = new HardSurvivalGame();
                hardGame.startGame();
                break;
            default:
                System.out.println("Invalid choice. Exiting the game.");
        }
    }
}
