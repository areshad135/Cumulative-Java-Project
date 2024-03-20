import java.util.Random;
import java.util.Scanner;

// Class representing scavenging items
class Item {
    private String name;

    public Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

// Class representing random events
class Event {
    private String name;

    public Event(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

public class SurvivalGame {
    private static final int MAX_HEALTH = 100;
    private static final int MAX_DAYS = 10;
    private static final int MAX_EVENT_CHANCE = 30;

    private static int health = MAX_HEALTH;
    private static int daysSurvived = 0;

    private static final Item[] scavengingResults = {
            new Item("food"),
            new Item("water"),
            new Item("medical supplies")
    };

    private static final Event[] randomEvents = {
            new Event("animal attack"),
            new Event("first aid kit"),
            new Event("stash of food")
    };

    public static void main(String[] args) {
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

    private static void handleRandomEvent(Random random) {
        if (random.nextInt(100) < MAX_EVENT_CHANCE) {
            int eventIndex = random.nextInt(randomEvents.length);
            Event event = randomEvents[eventIndex];
            switch (eventIndex) {
                case 0:
                    System.out.println("A wild " + event.getName() + "s you!");
                    int damage = random.nextInt(20) + 10;
                    health -= damage;
                    System.out.println("You lose " + damage + " health points.");
                    break;
                case 1:
                    System.out.println("You find a " + event.getName() + "!");
                    int heal = random.nextInt(20) + 10;
                    health = Math.min(health + heal, MAX_HEALTH);
                    System.out.println("You gain " + heal + " health points.");
                    break;
                case 2:
                    System.out.println("You stumble upon a " + event.getName() + "!");
                    int food = random.nextInt(30) + 10;
                    System.out.println("You collect " + food + " units of " + scavengingResults[eventIndex].getName() + ".");
                    break;
            }
        }
    }

    private static int getUserInput(Scanner scanner) {
        return scanner.nextInt();
    }

    private static void processUserChoice(Random random, int choice) {
        switch (choice) {
            case 1:
                System.out.println("You scavenge for supplies.");
                int scavengingResultIndex = random.nextInt(scavengingResults.length);
                Item scavengingResult = scavengingResults[scavengingResultIndex];
                System.out.println("You find some " + scavengingResult.getName() + "!");
                break;
            case 2:
                System.out.println("You rest in the shelter.");
                int restAmount = random.nextInt(20) + 10;
                health = Math.min(health + restAmount, MAX_HEALTH);
                System.out.println("You regain " + restAmount + " health points.");
                break;
            default:
                System.out.println("Invalid choice. Try again.");
                // Recursive call in case of invalid input
                processUserChoice(random, getUserInput(new Scanner(System.in)));
        }
    }
}
