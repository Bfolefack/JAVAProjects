class Word {
	String characters;
	float fitness = 0;
	float truFitness = 0;
	float  score;
	
	Word(String s_){
		characters = s_;
	}
	
	void calcFitness(String target){
		int score = 0;
		for( int i = 0; i < target.length() ; i++ ){
			if(target.charAt(i) == characters.charAt(i)){
				score += 1f;
			}
		}
		
		float a = score;
		float b = target.length();
		
		fitness = 0f + (a/b);
		
		
	}
}                                                        