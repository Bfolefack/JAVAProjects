class Population {

	int popSize;
	float mutRate;
	boolean answerFound = false;
	String target;
	Word[] genePool;

	Population(int p_, float m_, String t_){
		popSize = p_;
		mutRate = m_;
		target = t_;
		mutRate *= 10000;
		System.out.println(mutRate);
		
		genePool = new Word[popSize];
		
		for ( int i = 0; i < popSize; i++){
			genePool[i] = new Word(generateString());
		}
	}
	
	String generateString(){
		String temp = "";
							
		for(int i = 0; i < target.length(); i++){
			temp += GATG.alphabet.charAt(GATG.rand.nextInt(27));
		}
		return temp;
	}
	
	 void calcFitness(){
		for ( int i = 0; i < popSize; i++ ){
			genePool[i].calcFitness(target);
		}
	}
	
	void newGeneration() {
		calcFitness();
		Word temp = new Word(" ");
		temp.fitness = 0;
		for (int i = 0; i < popSize; i++){
			if(genePool[i].fitness >= temp.fitness){
				temp = genePool[i];
			}
		}
		
		float tempFitness = temp.fitness;
		System.out.println("Max Fitness: " + temp.fitness * 100);
		System.out.println("Best Candidate: " + temp.characters);
		answerFound = temp.fitness * 100 == 100;
		
		for (int i = 0; i < popSize; i++){
			
				map(genePool[i].fitness, 0, tempFitness, 0, 1);
				genePool[i].fitness *= 100;
				genePool[i].fitness++;
			
		}
		
		
		int matingPoolLength = 0;
		for (int i = 0; i < popSize; i++){
				matingPoolLength  += Math.floor((double)genePool[i].fitness);
		}
		Word[] matingPool = new Word[matingPoolLength];
		int counter = 0;
		for (int i = 0; i < popSize; i++){
			for(int j = 0; j < Math.floor((double)genePool[i].fitness); j++){
				matingPool[counter] = genePool[i];
				counter++;
			}
		}
		
		
		
		Word[] nextGen = new Word[popSize];
		
		for (int i = 0; i < popSize; i++){
			nextGen[i] = mate(matingPool[GATG.rand.nextInt(matingPool.length)], matingPool[GATG.rand.nextInt(matingPool.length)]);
		}
		genePool = nextGen;
	}
	
	Word mate(Word a, Word b){
		String child;
		child = "";
		
		for(int i = 0; i < target.length(); i++){
			int coinToss = GATG.rand.nextInt(2);
			if(coinToss == 0){    
				child += a.characters.charAt(i);
			} else {
				child += b.characters.charAt(i);
			}
		}
		
		child = mutate(child);
		return new Word(child);
	}
	
	String mutate(String child){
		for(int i = 0; i < child.length(); i++){
			if(GATG.rand.nextInt(10000) < mutRate){
				char[] childChars = child.toCharArray();
				childChars[i] = GATG.alphabet.charAt(GATG.rand.nextInt(27));
				child = String.valueOf(childChars);
			}
		}
		
		return child;
	}
	
	float map (float s, float a1, float a2, float b1, float b2){
			return b1 + (s-a1)*(b2-b1)/(a2-a1);
	}
}