import java.util.Scanner;
import java.io.*;
import java.util.Random;
import java.io.PrintStream;

class MonsteryMachine {
	
	public static void main(String [] args) throws FileNotFoundException  {
		
		boolean printToFile;
		
		Scanner scan = new Scanner(System.in);
		System.out.println("How many monsters would you like to make?");
		int monsterNum = scan.nextInt();
		
		System.out.println("Would you like to print to an output file?");
		printToFile = scan.nextBoolean();
		
		PrintStream stream = new PrintStream ("output.txt");
		
		for( int j = 0; j < monsterNum; j++ ){
			File t1 = new File("MonsterTypes.txt");
			Generator monsterTypes  = new Generator(t1);
			String[] monsterList = monsterTypes.getArray();
			
			File t2 = new File("Elements.txt");
			Generator elementTypes  = new Generator(t2);
			String[] elementList = elementTypes.getArray();
			
			File t3 = new File("Abilities.txt");
			Generator abilityTypes  = new Generator(t3);
			String[] abilityList = abilityTypes.getArray();
			
			File t4 = new File("Attacks.txt");
			Generator attackTypes  = new Generator(t4);
			String[] attackList = attackTypes.getArray();
			
			File t5 = new File("Sizes.txt");
			Generator sizeTypes  = new Generator(t5);
			String[] sizeList = sizeTypes.getArray();
			
			File t6 = new File("Animals.txt");
			Generator animalTypes  = new Generator(t6);
			String[] animalList = animalTypes.getArray();
			
			File t7 = new File("AncientAnimals.txt");
			Generator ancientAnimals = new Generator(t7);
			String[] ancientList = ancientAnimals.getArray();
			
			Random rand = new Random();
			
			//**********************************************************************
			
			
			//Generates MonsterType
			String monsterType = monsterList[rand.nextInt(monsterList.length)];
			if(monsterType.equals("Chimera")){
				int coinFlip = rand.nextInt(2);
				if (coinFlip == 0){
					monsterType = animalList[rand.nextInt(animalList.length)] + "-" + animalList[rand.nextInt(animalList.length)];
				} else {
					monsterType = animalList[rand.nextInt(animalList.length)] + "-" + animalList[rand.nextInt(animalList.length)] + "-" + animalList[rand.nextInt(animalList.length)];
				}
			} else if (monsterType.equals("Modern Animal")){
				monsterType = animalList[rand.nextInt(animalList.length)];
			} else if (monsterType.equals("Undead")){
				monsterType = "Undead-" + monsterList[rand.nextInt(monsterList.length)];	
			} else if (monsterType.equals("Ancient Animal")){
				monsterType = ancientList[rand.nextInt(ancientList.length)];
			}
			
			
			//Generates Element
			String elementType = elementList[rand.nextInt(elementList.length)];
			if(elementType.equals("Dual-Element")){
				elementType = elementList[rand.nextInt(elementList.length)] + "-" + elementList[rand.nextInt(elementList.length)];
			}
			
			//Generates Attacks
			String attackType1 = attackList[rand.nextInt(attackList.length)];
			String attackType2 = attackList[rand.nextInt(attackList.length)];
			String attackType3 = attackList[rand.nextInt(attackList.length)];
			
			//Generates Abilities
			String abilityType1 = abilityList[rand.nextInt(abilityList.length)];
			String abilityType2 = abilityList[rand.nextInt(abilityList.length)];
			String abilityType3 = abilityList[rand.nextInt(abilityList.length)];
			String abilityType4 = abilityList[rand.nextInt(abilityList.length)];
			String abilityType5 = abilityList[rand.nextInt(abilityList.length)];
			
			//Generate Size
			String sizeType = sizeList[rand.nextInt(sizeList.length)];
			
			//Generates Monster
			int realMonstNum = j + 1;
			
			if(printToFile){
				
				stream.println();
				stream.println();
				stream.println("Your Monster #" + realMonstNum +":");
				stream.println();
				stream.println("	" + sizeType + " " + elementType + " " + monsterType);
				stream.println();
				stream.println("	Abilities:");
				stream.println("		" + abilityType1);
				stream.println("		" + abilityType2);
				stream.println("		" + abilityType3);
				stream.println("		" + abilityType4);
				stream.println("		" + abilityType5);
				stream.println();
				stream.println("	Attacks:");
				stream.println("		" + attackType1);
				stream.println("		" + attackType2);
				stream.println("		" + attackType3);
				stream.println();
				stream.println();
				stream.println();
				
			} else {
				System.out.println();
				System.out.println();
				System.out.println("Your Monster #" + realMonstNum +":");
				System.out.println();
				System.out.println("	" + sizeType + " " + elementType + " " + monsterType);
				System.out.println();
				System.out.println("	Abilities:");
				System.out.println("		" + abilityType1);
				System.out.println("		" + abilityType2);
				System.out.println("		" + abilityType3);
				System.out.println("		" + abilityType4);
				System.out.println("		" + abilityType5);
				System.out.println();
				System.out.println("	Attacks:");
				System.out.println("		" + attackType1);
				System.out.println("		" + attackType2);
				System.out.println("		" + attackType3);
				System.out.println();
				System.out.println();
				System.out.println();
			}
		}
		
		stream.close();
		scan.close();
		
	}
}