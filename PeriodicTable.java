//
//	Name:			Mark Barros
//	Course:			CS1400 - Intro to Programming and Problem Solving
//	Description:	This program reads information about elements in the periodic table and
//					prints out the periodic table sorted by atomic number or name.
//

import java.util.Scanner;
import java.io.*;

public class PeriodicTable
{
	public static void main(String[] args) throws IOException
	{
		// declarations ----------------------------------------------------------------------------------------------
		final int MAX_NUMBER_OF_ELEMENTS = 128;
		int[] atomicNumber = new int[MAX_NUMBER_OF_ELEMENTS];
		String[] atomicSymbol = new String[MAX_NUMBER_OF_ELEMENTS];
		float[] atomicMass = new float[MAX_NUMBER_OF_ELEMENTS];
		String[] atomicName = new String[MAX_NUMBER_OF_ELEMENTS];
		int currentElement = 0,
		    actualNumberOfElements = 0,
		    minimumIndex = 0,
		    smallerAtomicNumber = 0;
		double sumOfAtomicMasses = 0.0,
		       meanAtomicMass = 0.0;
		String sortBy = null,
		       smallerAtomicName = null,
		       smallerAtomicSymbol = null;
		float smallerAtomicMass = 0;

		// The following pathname must be set accordingly:
		File periodicTableFile = new File("F:/IdeaProjects/Periodic Table/src/periodictable.dat");
		Scanner fileReader = new Scanner(periodicTableFile);
		PrintWriter outputFile = null;

		// command line parameter evaluation  -----------------------------------------------------------------------
		switch(args.length)
		{
			case 0:
				sortBy = "no sort criterion";
				break;
			case 3:
				if(args.length == 3)
				{
					if(args[0].equals("print") && (args[1].equals("atomic") || args[1].equals("name")))
					{
						sortBy = args[1];
						outputFile = new PrintWriter(args[2]);
					} else
					{
						System.out.println("Unknown action.");
						System.exit(0);
					}
				}
				break;
			default:
				System.out.println("Unknown action.");
				System.exit(0);

		}

		// populate arrays, count # of elements, and compute average mass ----------------------------------------------
		while(fileReader.hasNext() && currentElement < MAX_NUMBER_OF_ELEMENTS)
		{
			atomicNumber[currentElement] = fileReader.nextInt();
			atomicSymbol[currentElement] = fileReader.next();
			atomicMass[currentElement] = fileReader.nextFloat();
			atomicName[currentElement] = fileReader.next();

			sumOfAtomicMasses += atomicMass[currentElement];
			currentElement++;
		}
		fileReader.close();

		actualNumberOfElements = currentElement;
		meanAtomicMass = sumOfAtomicMasses / actualNumberOfElements;

		// sort criterion not specified  ---------------------------------------------------------------------------------
		if(sortBy.equals("no sort criterion"))
		{
			System.out.println("Periodic Table by M. Barros\n");
			System.out.println(actualNumberOfElements + " elements\n");
			System.exit(0);
		}

		// sort criterion specified ---------------------------------------------------------------------------------------
		for(int currentLocation = 0; currentLocation < actualNumberOfElements - 1; currentLocation++)
		{
			minimumIndex = currentLocation;
			smallerAtomicNumber = atomicNumber[currentLocation];
			smallerAtomicName = atomicName[currentLocation];
			smallerAtomicSymbol = atomicSymbol[currentLocation];
			smallerAtomicMass = atomicMass[currentLocation];
			for(int movingIndex = currentLocation + 1; movingIndex < actualNumberOfElements; movingIndex++)
			{

				if(sortBy.equals("atomic") && atomicNumber[movingIndex] < smallerAtomicNumber 
				   || sortBy.equals("name") && atomicName[movingIndex].compareTo(smallerAtomicName) < 0)
				{
					minimumIndex = movingIndex;
					smallerAtomicNumber = atomicNumber[movingIndex];
					smallerAtomicName = atomicName[movingIndex];
					smallerAtomicSymbol = atomicSymbol[movingIndex];
					smallerAtomicMass = atomicMass[movingIndex];
				}
			}
			atomicNumber[minimumIndex] = atomicNumber[currentLocation];
			atomicNumber[currentLocation] = smallerAtomicNumber;
			atomicName[minimumIndex] = atomicName[currentLocation];
			atomicName[currentLocation] = smallerAtomicName;
			atomicSymbol[minimumIndex] = atomicSymbol[currentLocation];
			atomicSymbol[currentLocation] = smallerAtomicSymbol;
			atomicMass[minimumIndex] = atomicMass[currentLocation];
			atomicMass[currentLocation] = smallerAtomicMass;
		}

		// output -----------------------------------------------------------------------------------------------------------------
		System.out.println("Periodic Table by M. Barros\n");
		System.out.println(actualNumberOfElements + "  elements");
		outputFile.println();
		outputFile.println("Periodic Table (" + actualNumberOfElements + ")\n");
		outputFile.println("ANo. Name                 Abr    Mass");
		outputFile.println("---- -------------------- --- -------");

		for(int i = 0; i < actualNumberOfElements; i++)
		{
			outputFile.printf("%4d ", atomicNumber[i]);
			outputFile.printf("%-20s ", atomicName[i]);
			outputFile.printf("%-3s ", atomicSymbol[i]);
			outputFile.printf("%7.2f\n", atomicMass[i]);
		}

		outputFile.println();
		outputFile.printf("The average mass:             %5.2f\n", meanAtomicMass);

		outputFile.close();
		System.out.println("\nOutput file printed.");
	}
}
