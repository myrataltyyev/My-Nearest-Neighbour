import java.util.Random;

public class MyNearestNeighbour {
	
	
	static Random rand = new Random();
	
	public static void main(String[] args){
		
		int[][] trainingSet = createTrainingSet();
		int[][] distanceMatrixForTrainingSet = createDistanceMatrixForTrainingSet(trainingSet);

		System.out.println("\nDistance matrix:");
		displayDistanceMatrix(distanceMatrixForTrainingSet);
		
		char[] className = changeClassName(distanceMatrixForTrainingSet);
		System.out.println("\nTraining set matrix:\n");
		showTrainingSetWithClassName(trainingSet,className);
		
		int[][] testData = createTestData();
		System.out.println("\nTest Data:");
		displayMatrix(testData);
		
		int[][] distanceMatrix = createDistanceMatrix(testData,trainingSet);
		findClass(distanceMatrix,className);
		
	}
	
	
	public static void fillMatrix(int[][] matrix){	
		
		for (int row = 0; row < matrix.length; row++)
		{
			for (int column = 0; column < matrix[0].length; column++)
			{
				matrix[row][column] = rand.nextInt(256);
			}
		}
		
	}
	
	
	public static void displayMatrix(int matrix[][]){
		
		System.out.println();
		for (int row = 0; row < matrix.length; row++)
		{
			for (int column = 0; column < matrix[0].length; column++) 
			{
				System.out.print("\t" + matrix[row][column] + "  ");
			}
				System.out.println();
		}
	}
	
	
	public static void displayDistanceMatrix(int distanceMatrix[][]){
		
		String[] records = {"R0","R1","R2","R3","R4"};
		
		System.out.print("\n\t");
		for (int i = 0; i < records.length; i++) {
			System.out.printf("%-5s",records[i]);
		}
		System.out.println();
		
		
		for (int row = 0; row < distanceMatrix.length; row++)
		{
			System.out.print(records[row]+"  ");
			for (int i = row; i >= 0; i--) 
			{
				System.out.print("     ");
			}
			for (int column = row; column < distanceMatrix[0].length; column++) 
			{
				System.out.print(distanceMatrix[row][column] + "  ");
			}
				System.out.println();
		}
	}
	
	
	public static void applyMaxFilter(int[][] matrix){
		
		int max;
		int[][] maxMatrix = new int[5][5];
		
		for (int i = 0; i < 3; i++) 
		{
			for (int j = 0; j < 3; j++) 
			{
				max = matrix[i][j];
				for (int column = j; column < j+3; column++) 
				{
					for (int row = i; row < i+3; row++) 
					{
						if (matrix[row][column] >= max)
						{
							max = matrix[row][column];
							maxMatrix[i+1][j+1] = max;
						}
					}
				}
			}
		}
		
		
		for (int row = 0; row < maxMatrix.length; row++) 
		{
			for (int column = 0; column < maxMatrix[0].length; column++) 
			{
				matrix[row][column] = maxMatrix[row][column];
			}
		}
			
	}
	
	
	public static void applyMinFilter(int[][] matrix){
		
		int min;
		int[][] minMatrix = new int[5][5];
		
		for (int i = 0; i < 3; i++) 
		{
			for (int j = 0; j < 3; j++) 
			{
				min = matrix[i][j];
				for (int column = j; column < j+3; column++) 
				{
					for (int row = i; row < i+3; row++) 
					{
						if (matrix[row][column] <= min)
						{
							min = matrix[row][column];
							minMatrix[i+1][j+1] = min;
						}
					}
				}
			}
		}
		
		for (int row = 0; row < minMatrix.length; row++) 
		{
			for (int column = 0; column < minMatrix[0].length; column++) 
			{
				matrix[row][column] = minMatrix[row][column];
			}
		}
			
		
	}
	
	
	public static void applyAverageFilter(int[][] matrix){
		
		int sum;
		int[][] sumMatrix = new int[5][5];
		
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 3; j++) 
			{
				sum = 0;
				for (int column = j; column < j+3; column++) 
				{
					for (int row = i; row < i+3; row++) 
						{
							sum += matrix[row][column];
						}
				}
				sumMatrix[i+1][j+1] = sum/9;
			}
		}
		
		for (int row = 0; row < sumMatrix.length; row++) 
		{
			for (int column = 0; column < sumMatrix[0].length; column++) 
			{
				matrix[row][column] = sumMatrix[row][column];
			}
		}

	}
	
	
	public static void applyMedianFilter(int[][] matrix){
		
		int[] sort = new int[9];
		int[][] medianMatrix = new int[5][5];
		int hold = 0;
		int count;
		
		for (int i = 0; i < 3; i++) 
		{
			for (int j = 0; j < 3; j++) 
			{
				count = 0;
				for (int column = j; column < j+3; column++) 
				{
					for (int row = i; row < i+3; row++) 
					{
						sort[count++] = matrix[row][column];
					}
				}
							
				for (int l = 0; l < sort.length; l++)
				{
					for (int m = l+1; m < sort.length; m++)
						if (sort[m] < sort[l])
						{
							hold = sort[l];
							sort[l] = sort[m];
							sort[m] = hold;
						}
				}
				medianMatrix[i+1][j+1] = sort[4];
			}
		}
		
		for (int row = 0; row < medianMatrix.length; row++)
		{
			for (int column = 0; column < medianMatrix.length; column++)
			{
				matrix[row][column] = medianMatrix[row][column];
			}
		}

	}
	
	
	public static int[][] convertToColumnMatrix(int[][] matrix){
		
		int[][] convertToColumn = new int[25][1];
		int count = 0;
		
		for (int row = 0; row < matrix.length; row++) 
		{
			for (int column = 0; column < matrix[0].length; column++)
			{
					convertToColumn[count++][0] = matrix[row][column];
			}
		}
		
		return convertToColumn;
	}
	
	
	public static int[][] takenTranspose(int[][] matrix){

		int[][] transpose = new int[1][25];
		
		for (int i = 0; i < transpose[0].length; i++)
		{
			transpose[0][i] = matrix[i][0];
		}
		
		return transpose;
	}
	
	
	public static int[][] createTrainingSet(){

		int[][][] matrix = new int[5][5][5];
		
		for (int i = 0; i < matrix.length; i++)
		{
			fillMatrix(matrix[i]);
		}
		
		System.out.println("\nMatrixes are generated:");
		for (int i = 0; i < matrix.length; i++) {
			displayMatrix(matrix[i]);
		}
		
		int randNum;
		
		for (int i = 0; i < matrix.length; i++)
		{
			switch (randNum = rand.nextInt(4))
			{
				case 0: applyMaxFilter(matrix[i]); break;
				case 1: applyMinFilter(matrix[i]); break;
				case 2: applyAverageFilter(matrix[i]); break;
				case 3: applyMedianFilter(matrix[i]); break;
			}
		}
		
		System.out.println("\nFilters are applied randomly:");
		for (int i = 0; i < matrix.length; i++) {
			displayMatrix(matrix[i]);
		}
		
		// converted to column and taken transpose in one step
		for (int i = 0; i < matrix.length; i++) {
			matrix[i] = takenTranspose(convertToColumnMatrix(matrix[i]));
		}
		
		System.out.println("\nConverted to column matrix and taken transpose:");
		for (int i = 0; i < matrix.length; i++) {
			displayMatrix(matrix[i]);
		}
		
		// all matrixes are initialized to training matrix
		int[][] trainingMatrix = new int[5][25];
		
		for (int row = 0; row < trainingMatrix.length; row++)
		{
			for (int column = 0; column < trainingMatrix[0].length; column++)
			{
				trainingMatrix[row][column] = matrix[row][0][column];
			}
		}
		
		return trainingMatrix;
	}
	
	
	public static int findManhattanDistance(int[] x, int[] y){
		
		int[] subtractionMatrix = new int[25];
		int sum = 0;
		
		for (int i = 0; i < subtractionMatrix.length; i++)
		{
			subtractionMatrix[i] = Math.abs(x[i] - y[i]);
			sum += subtractionMatrix[i];
		}
		return sum;
	}
	
	
	public static int[][] createDistanceMatrixForTrainingSet(int[][] trainingSet){
		
		int[][] distanceMatrix = new int[5][5];
		
		for (int row = 0; row < distanceMatrix.length; row++)
		{
			for (int column = row; column < distanceMatrix[0].length; column++)
			{
				distanceMatrix[row][column] = findManhattanDistance(trainingSet[row],trainingSet[column]);
			}
		}
		
		return distanceMatrix;
	}
	
	
	public static int[][] createDistanceMatrix(int[][] testData, int[][] trainingSet){
		
		int[][] distanceMatrix = new int[1][5];
		
		for (int row = 0; row < distanceMatrix[0].length; row++)
		{
			distanceMatrix[0][row] = findManhattanDistance(trainingSet[row],testData[0]);
		}
		
		return distanceMatrix;
	}
	
	
	public static char[] createClassName(){
		char[] className = {'A','A','A','A','A'};
		return className;
	}
	
	
	public static char[] changeClassName(int[][] distanceMatrix){
		
		int min = distanceMatrix[0][1];
		int index = 0;
		
		for (int column = 1; column < distanceMatrix.length; column++) 
		{
			if (distanceMatrix[0][column] <= min)
			{
				min = distanceMatrix[0][column];
				index = column;
			}
		}
		
		char[] className = createClassName();
		className[0] = 'B';
		className[index] = 'B';
		
		return className;
	}
	
	
	 public static void showTrainingSetWithClassName(int trainingSet[][], char[] className){
		
		String[] records = {"Record 1","Record 2","Record 3","Record 4","Record 5"};
		
		System.out.print("\t");
		for (int i = 0; i < trainingSet[0].length; i++) 
		{
			System.out.print("\t"+(i+1)+" ");
		}
		System.out.println("  Class\t");
		
		
		for (int row = 0; row < trainingSet.length; row++)
		{
			System.out.print(records[row]+" ");
			for (int column = 0; column < trainingSet[0].length; column++) 
			{
				System.out.print("\t" + trainingSet[row][column] + " ");
			}
				System.out.println("\t"+className[row]+"\t");
		}
	}
	
	 
	public static int[][] createTestData(){
		
		int[][] testData = new int[5][5];
		
		fillMatrix(testData);
		System.out.println("\nTest matrix is created:");
		displayMatrix(testData);
		
		switch (rand.nextInt(4)){
			case 0: applyMaxFilter(testData); break;
			case 1: applyMinFilter(testData); break;
			case 2: applyAverageFilter(testData); break;
			case 3: applyMedianFilter(testData); break;
		}
		
		System.out.println("\nFilter is applied randomly:");
		displayMatrix(testData);
		
		int[][] testMatrix = takenTranspose(convertToColumnMatrix(testData));
		return testMatrix;
	}

	
	public static void findClass(int[][] distanceMatrix, char[] className){
		
		System.out.println();
		for (int i = 0; i < distanceMatrix[0].length; i++) 
		{
			System.out.println("Distance between Record "+(i+1)+" and test data is "+distanceMatrix[0][i]);
		}
		
		int min = distanceMatrix[0][0];
		int index = 0;
		
		for (int i = 0; i < distanceMatrix[0].length; i++)
		{
			if (distanceMatrix[0][i] <= min)
			{
				min = distanceMatrix[0][i];
				index = i;
			}
		}
		
		System.out.println("\nRecord "+(index+1)+" is in "+className[index]+" class. So test data is also in same class.");
		
	}
}


