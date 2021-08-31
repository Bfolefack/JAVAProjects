import java.util.Scanner;
import java.io.*;

class Generator{ 
	File dataSet; 
	boolean e;
	int dataSetNum = 0;
	Scanner getNumber;
	Scanner makeArray;
	
	Generator(File f) throws FileNotFoundException {
		dataSet = f;
		getNumber = new Scanner(dataSet);
		makeArray = new Scanner(dataSet);
	}
	
	public void getNum()  throws FileNotFoundException  {
		while (getNumber.hasNext()){
			dataSetNum++;
			getNumber.nextLine();
		}	
	}
	
	public String[] getArray()  throws FileNotFoundException  {
		getNum();
		String[] dataPoints = new String[dataSetNum];
		for( int i = 0; i < dataSetNum; i++ ){
				dataPoints[i] = makeArray.nextLine();
		}
		return dataPoints;
	}
}