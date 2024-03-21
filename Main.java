import java.util.Random;
import java.util.Scanner;

// Superclass representing common properties of scavenging items and random events
abstract class GameObject {
    protected String name;

    public GameObject(String name) {
        this.name = name;
    }

    // Abstract method to get a description of the object
    public abstract String getDescription();
}

// Subclass representing scavenging items
class Item extends GameObject {
    public Item(String name) {
        super(name);
    }

    @Override
    public String getDescription() {
        return "You find some " + name + "!";
    }
}

// Subclass representing random events
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
    protected static final int MAX_HEALTH = 100;
    protected static final int MAX_DAYS = 10;
    protected static final int MAX_EVENT_CHANCE = 30;

    protected int health = MAX_HEALTH;
    protected int daysSurvived = 0;

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

    // Method to simulate health loss over multiple days using recursion
    protected void regenerateHealth(int days) {
        if (days <= 0) {
            return; // Base case: Stop recursion when there are no more days left
        }

        // Simulate health regeneration for the current day
        int regenAmount = 5; // Example: Regenerate 5 health points per day
        health = Math.min(health - regenAmount, MAX_HEALTH); // Ensure health doesn't exceed maximum

        System.out.println("You lost " + regenAmount + " health points. Health: " + health);

        // Recursive call for the next day
        regenerateHealth(days - 1);
    }


    // Method to handle random events during gameplay
    protected void handleRandomEvent(Random random) {
        if (random.nextInt(100) < MAX_EVENT_CHANCE) {
            int eventIndex = random.nextInt(randomEvents.length);
            GameObject event = randomEvents[eventIndex];
            System.out.println(event.getDescription());
            switch (eventIndex) {
                case 0:
                    int damage = random.nextInt(20) + 10;
                    health -= damage;
                    System.out.println("You lose " + damage + " health points.");
                    break;
                case 1:
                    int heal = random.nextInt(20) + 10;
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

    // Method to get user input
    protected int getUserInput(Scanner scanner) {
        return scanner.nextInt();
    }

    // Method to process user choice
    protected void processUserChoice(Random random, int choice) {
        switch (choice) {
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

    // Method to start the game
    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.println("Welcome to Survival Game!");

        while (health > 0 && daysSurvived < MAX_DAYS) {
            daysSurvived++;
            System.out.println("\nDay " + daysSurvived + ":");
            System.out.println("Health: " + health);

            handleRandomEvent(random);

            if (health <= 0) {
                System.out.println("\nGame over! You didn't survive.");
                break;
            }

            // Call regenerateHealth() method after each day
            regenerateHealth(1);

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

// Subclass representing easy difficulty level
class EasySurvivalGame extends SurvivalGame {
    public EasySurvivalGame() {
        super(); // Call the constructor of the superclass to initialize arrays
    }
}

// Subclass representing medium difficulty level
class MediumSurvivalGame extends SurvivalGame {
    public MediumSurvivalGame() {
        super(); // Call the constructor of the superclass to initialize arrays
    }
}

// Subclass representing hard difficulty level
class HardSurvivalGame extends SurvivalGame {
    public HardSurvivalGame() {
        super(); // Call the constructor of the superclass to initialize arrays
    }
}

// Main class to run the game
public class Main {
    public static void main(String[] args) {
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
                SurvivalGame easyGame = new EasySurvivalGame();
                easyGame.startGame();
                break;
            case 2:
                SurvivalGame mediumGame = new MediumSurvivalGame();
                mediumGame.startGame();
                break;
            case 3:
                SurvivalGame hardGame = new HardSurvivalGame();
                hardGame.startGame();
                break;
            default:
                System.out.println("Invalid choice. Exiting the game.");
        }
    }
}
