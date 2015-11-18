
public class MemoryBlock {

	int lowBound;
	int upperBound;
	int currentLowBound;
	
	public MemoryBlock(){
		lowBound = 0;
		upperBound = 0;
		currentLowBound = 0;
	}
	
	public void setLow(int l){
		lowBound = l;
		currentLowBound = lowBound;
	}
	
	public void setHigh(int h){
		upperBound = h;
	}
	
	public int getLow(){
		return lowBound;
	}
	
	public int getHigh(){
		return upperBound;
	}
	
	public int getCurrentBound(){
		return currentLowBound;
	}
	
	public void setCurrentBound(int c){
		currentLowBound += c;
	}
	
	public int checkFit(int num){
		if(currentLowBound == upperBound){
			return -1;
		}
		
		return (upperBound - currentLowBound) - num;
	}
	
	public void reset(){
		currentLowBound = lowBound;
	}
}
