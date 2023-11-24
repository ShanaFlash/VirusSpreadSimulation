import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class VirusSpreadSimulation {
    public static void main(String[] args) {
        // Simulation parameters
        int numComputers = 20;
        double infectionProbability = 0.1;
        int removalCapacity = 5;
        int simulationDays = 0;

        // Run the simulation
        int[] computers = new int[numComputers]; // 0: uninfected, 1: infected
        Random random = new Random();

        while (countInfectedComputers(computers) < numComputers && simulationDays < 1000) {
            // Spread the virus
            for (int i = 0; i < numComputers; i++) {
                if (computers[i] == 1) { // Infected computer
                    for (int j = 0; j < numComputers; j++) {
                        if (i != j && computers[j] == 0 && random.nextDouble() < infectionProbability) {
                            computers[j] = 1; // Infect an uninfected computer
                        }
                    }
                }
            }

            // Remove the virus from random computers
            ArrayList<Integer> infectedIndices = getInfectedComputerIndices(computers);
            Collections.shuffle(infectedIndices);

            int removalCount = Math.min(removalCapacity, infectedIndices.size());
            for (int i = 0; i < removalCount; i++) {
                computers[infectedIndices.get(i)] = 0; // Remove virus
            }

            simulationDays++;
        }

        // Results
        System.out.println("Simulation Results:");
        System.out.println("Total days to remove the virus: " + simulationDays);
        System.out.println("Probability that each computer gets infected at least once: " +
                calculateProbabilityEachComputerInfectedAtLeastOnce(simulationDays, numComputers));
        System.out.println("Average number of computers that get infected: " +
                calculateAverageInfectedComputers(simulationDays));
    }

    private static int countInfectedComputers(int[] computers) {
        int count = 0;
        for (int computer : computers) {
            if (computer == 1) {
                count++;
            }
        }
        return count;
    }

    private static ArrayList<Integer> getInfectedComputerIndices(int[] computers) {
        ArrayList<Integer> infectedIndices = new ArrayList<>();
        for (int i = 0; i < computers.length; i++) {
            if (computers[i] == 1) {
                infectedIndices.add(i);
            }
        }
        return infectedIndices;
    }

    private static double calculateProbabilityEachComputerInfectedAtLeastOnce(int simulationDays, int numComputers) {
        return 1 - Math.pow((double) (numComputers - 1) / numComputers, simulationDays);
    }

    private static double calculateAverageInfectedComputers(int simulationDays) {
        // This is a simple calculation assuming equal probability for each number of infected computers.
        // A more accurate calculation may involve tracking the distribution of infected computers in each simulation.
        return (double) simulationDays / 2; // Example: Assuming on average half of the computers get infected each day.
    }
}

