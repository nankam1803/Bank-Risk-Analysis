/*
 * Name: Making Changes
 * R#: R01576061
 * 
 * Description:
 * This program analyzes risk factor of banks based on their total assets which should be above 
 * a limit for them to be safe. The total assets will be a sum of their balances and money lent to other banks. 
 * If any bank were to turn out to be Unsafe, other banks which might have lent money to this bank won't be
 * calculating the money lent in their assets (i.e. they cannot lend money since the bank is unsafe).
 * 
 * No of methods: 4(including main)
 * Indentation Style: Allman Style
 * Naming convention: Camelcase used
 * 
 * Start date: 02/19/24
 * End date: 03/13/24
 * */
package project1;

import java.util.Scanner;

public class Banks 
{

    static Scanner scan = new Scanner(System.in);		//"scan" object created from Scanner class to get input from user	

    public static void main(String[] args) 
    {
        int n = scan.nextInt();							// n = number of banks we need to calculate risk for.
        double limit = scan.nextDouble();				// limit under which bank is unsafe.

        double[][] borrowers = new double[n][n];    	// Array created to store pair of lender and borrower.
        double[] balances = new double[n];				// Array created to store balances of each bank.
        
        boolean[] isSafe = new boolean[n];				// Creating boolean array to store bank safety info: True-Safe & False-Unsafe
        
        

        readBankInfo(borrowers, n, balances);			// Method for getting input.
        identifySafety(n, limit, balances, borrowers, isSafe);	// Method for identifying Unsafe banks.
 
    }

    public static void readBankInfo(double[][] loans, int n, double[] balances) 
    {
        for (int i = 0; i < n; i++) 
        {
            balances[i] = scan.nextDouble();
            int numberOfBorrowers = scan.nextInt();
            for (int k = 0; k < numberOfBorrowers; k++) 
            {
                int j = scan.nextInt();					// 'j' stores the bankID to which the loan was given.
                loans[i][j] = scan.nextDouble();		// Stores amount borrowed from bank 'i' by bank 'j'
            }
        }
    }

    public static void identifySafety(int n, double limit, double[] balances, double[][] loans, boolean[] isSafe) 
    { 						
    	int numOfUnsafeBanks = 0;
    	
        for (int i = 0; i < n; i++) 
        {
            isSafe[i] = true;							// All banks are Safe initially.
        }

       boolean newUnsafeFound = true; 					// Indicate whether a new unsafe bank is discovered
        while (newUnsafeFound) 							
        {
           newUnsafeFound = false; 						// Assume no new unsafe banks are found
            for (int i = 0; i < n; i++) 
            {
                double totalAssets = calculateTotalAssets(i, loans, balances, n);

                if (isSafe[i] && totalAssets < limit) 	// If any one of the condition is not satisfied, the bank will be declared Unsafe. 
                {
                    isSafe[i] = false;
                    newUnsafeFound = true;
                    numOfUnsafeBanks++;

                    // Remove bank i from the loans of other banks
                    for (int k = 0; k < n; k++) 
                    {
                        loans[k][i] = 0; 				// Bank i's borrowed loans are gone
                    }
                }
            }
        }
        
        if(numOfUnsafeBanks == 0)
        {
        	//Nothing printed.	
        }
        
        else
        {
        	System.out.print("Unsafe banks are: ");
        	for(int j = 0; j < n; j++)
        	if(!isSafe[j])
        	{
        		System.out.print(j + " ");
        	}
        }
        	
        }

    public static double calculateTotalAssets(int bankID, double[][] loans, double[] balances, int n) 
    {
        double totalAssets = balances[bankID];			// We store balances first, in totalAssets.
        for (int j = 0; j < n; j++) 
        {
            totalAssets += loans[bankID][j];			// We add the loans given by the bank to the total assets.
        }
        return totalAssets;
    }
}
