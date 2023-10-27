import java.util.Scanner;

public class BankersAlgorithm {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter no. of processes: ");
        int np = input.nextInt();
        System.out.print("Enter no. of resources: ");
        int nr = input.nextInt();

        int[][] allocationMatrix = new int[np][nr];
        int[][] maxDemandMatrix = new int[np][nr];
        int[] availableVector = new int[nr];
        
        input.nextLine();
        
        System.out.println("Enter the allocation matrix:");
        for (int i = 0; i < np; i++) {
            String[] tokens = input.nextLine().split("");
            for (int j = 0; j < nr; j++) {
                allocationMatrix[i][j] = Integer.parseInt(tokens[j]);
            }
        }

        System.out.println("Enter the maximum demand matrix:");
        for (int i = 0; i < np; i++) {
            String[] tokens = input.nextLine().split("");
            for (int j = 0; j < nr; j++) {
                maxDemandMatrix[i][j] = Integer.parseInt(tokens[j]);
            }
        }

        System.out.println("Enter the available matrix:");
        String[] tokens = input.nextLine().split("");
        for (int i = 0; i < nr; i++) {
            availableVector[i] = Integer.parseInt(tokens[i]);
        }

        int[] safeSequence = findSafeSequence(np, nr, allocationMatrix, maxDemandMatrix, availableVector);

        if (safeSequence != null) {
            System.out.print("\nSafe sequence: ");
            for (int i = 0; i < np; i++) {
                System.out.print("P" + safeSequence[i]);
                if (i < np - 1) {
                    System.out.print(", ");
                }
            }
        } else {
            System.out.println("Could not find a safe sequence");
        }
        input.close();
    }
    

    public static int[] findSafeSequence(int np, int nr, int[][] alloc, int[][] max, int[] avail) {
        int[] work = avail.clone();
        boolean[] finish = new boolean[np];
        int[] safeSequence = new int[np];
        int count = 0;

        while (count < np) {
            boolean found = false;
            for (int i = 0; i < np; i++) {
                if (!finish[i] && canFinish(np, nr, alloc, max, work, i)) {
                    for (int j = 0; j < nr; j++) {
                        work[j] += alloc[i][j];
                    }
                    safeSequence[count] = i;
                    finish[i] = true;
                    count++;
                    found = true;
                }
            }

            if (!found) {
                return null; // No safe sequence found
            }
        }

        return safeSequence;
    }

    public static boolean canFinish(int np, int nr, int[][] alloc, int[][] max, int[] work, int process) {
        for (int j = 0; j < nr; j++) {
            if (max[process][j] - alloc[process][j] > work[j]) {
                return false;
            }
        }
        return true;
    }
}
