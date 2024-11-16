package AdvanceCode;



import java.util.Scanner;

public class dungeonGame {
    static int dungeonSize;
    static int[][] dungeon;
    static int[] adventurer = new int[2];
    static int[] monster = new int[2];
    static int[] treasure = new int[2];
    static int[][] pits;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Set up dungeon size
        System.out.print("Enter dungeon size (n x n): ");
        dungeonSize = scanner.nextInt();
        dungeon = new int[dungeonSize][dungeonSize];

        // Read adventurer, monster, and treasure positions
        System.out.print("Enter adventurer position (row col): ");
        adventurer[0] = scanner.nextInt();
        adventurer[1] = scanner.nextInt();

        System.out.print("Enter monster position (row col): ");
        monster[0] = scanner.nextInt();
        monster[1] = scanner.nextInt();

        System.out.print("Enter treasure position (row col): ");
        treasure[0] = scanner.nextInt();
        treasure[1] = scanner.nextInt();

        // Read pits
        System.out.print("Enter number of pits: ");
        int pitCount = scanner.nextInt();
        pits = new int[pitCount][2];
        for (int i = 0; i < pitCount; i++) {
            System.out.print("Enter pit " + (i + 1) + " position (row col): ");
            pits[i][0] = scanner.nextInt();
            pits[i][1] = scanner.nextInt();
            dungeon[pits[i][0]][pits[i][1]] = -1; // Mark pit locations
        }
        scanner.close();

        // Determine if the adventurer can reach the treasure before the monster
        if (reachTreasureBeforeMonster()) {
            System.out.println("Success! The adventurer reached the treasure first.");
        } else {
            System.out.println("Game over! The monster reached the adventurer.");
        }
    }

    // Check if any pit lies in the direct path between adventurer and treasure
    // Check if any pit lies in the direct path between adventurer and treasure
private static boolean isPitInPath() {
    int x1 = adventurer[0], y1 = adventurer[1];
    int x2 = treasure[0], y2 = treasure[1];

    int maxX = Math.max(x1, x2), maxY = Math.max(y1, y2);
    int minX = Math.min(x1, x2), minY = Math.min(y1, y2);

    for (int[] pit : pits) {
        // Check if the pit is within the rectangle bounds
        if (pit[0] >= minX && pit[0] <= maxX && pit[1] >= minY && pit[1] <= maxY) {
            // Ensure the pit is not in line with only one axis (i.e., not in direct path)
            if (!(pit[0] == x1 && pit[1] == y2) && !(pit[1] == y1 && pit[0] == x2)) {
                return true; // Pit blocks the path
            }
        }
    }
    return false; // No pit blocks the path
}


    // Calculate Manhattan distance between two points
    private static int calculateDistance(int[] pos1, int[] pos2) {
        return Math.abs(pos1[0] - pos2[0]) + Math.abs(pos1[1] - pos2[1]);
    }

    // Main function to check who reaches the treasure first
    private static boolean reachTreasureBeforeMonster() {
        // If a pit is in the direct path of the adventurer, they can't reach the treasure
        if (isPitInPath()) {
            System.out.println("A pit is blocking the adventurer's path!");
            return false;
        }

        int adventurerDistance = calculateDistance(adventurer, treasure);
        int monsterDistance = calculateDistance(monster, treasure);

        // Adventurer wins if they reach the treasure before the monster
        return adventurerDistance < monsterDistance;
    }
}
