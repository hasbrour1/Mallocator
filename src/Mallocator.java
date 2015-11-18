import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;


public class Mallocator {

	public static int testing = 1;
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		MemoryBlock[] memorySlots; //array of memory block objects
		int[] processes; //array of processes
		int numOfBlocks = 0;
		int numOfProc = 0;
		
		
		try{
			File file = new File("Mainput.data");
			Scanner input = new Scanner(file);
			String str;
			PrintWriter FFwriter = new PrintWriter("FFoutput.data", "UTF-8");
			PrintWriter BFwriter = new PrintWriter("BFoutput.data", "UTF-8");
			PrintWriter WFwriter = new PrintWriter("WFoutput.data", "UTF-8");
			
			//get number of memory blocks
			str = input.nextLine();
			numOfBlocks = Integer.parseInt(str);
			memorySlots = new MemoryBlock[numOfBlocks];
			
			//Get Memory Allocations
			for(int i = 0; i < numOfBlocks; i++){
				memorySlots[i] = new MemoryBlock();
				//lower
				str = input.next();
				memorySlots[i].setLow(Integer.parseInt(str));
				
				//upper
				str = input.next();
				memorySlots[i].setHigh(Integer.parseInt(str));			
			}
			
			//Get Processes
			str = input.next();
			numOfProc = Integer.parseInt(str);
			processes = new int[numOfProc];
			
			
			for(int i = 0; i < numOfProc; i++){
				//lower
				
				str = input.next();
				processes[i] = (Integer.parseInt(str));	
			}
				
			//if in test phase print all variables
			if(testing == 1){
				System.out.println("Memory: ");
				for(int i = 0; i < numOfBlocks; i++){
					System.out.println(i + ":" + memorySlots[i].getLow() + " " + memorySlots[i].getHigh());		
				}
				
				System.out.println("Processes: ");
				for(int i = 0; i < numOfProc; i++){
					System.out.println(i + ":" + processes[i]);
				}
				
			}
			
			//Allocate FF
			//FFoutput.data
			if(testing == 1)
				System.out.println("First Fit");
			
			for(int i = 0; i < numOfProc; i++){			
				for(int j = 0; j < numOfBlocks; j++){
					//if process can fit then allocate, else move to next block
					if(memorySlots[j].checkFit(processes[i]) >= 0){
						
						if(testing == 1){
							System.out.println("Process " + i + " allocated to block " + 
									j + " from address " + memorySlots[j].getCurrentBound() + 
									" to " + (memorySlots[j].getCurrentBound() + processes[i]));
						}
						
						FFwriter.println(memorySlots[j].getCurrentBound() + 
									" " + (memorySlots[j].getCurrentBound() + processes[i]) + " "+ i);
						
						//process fits so allocate it
						memorySlots[j].setCurrentBound(processes[i]);					
						
						j = numOfBlocks;
					}else if(j == (numOfBlocks - 1)){
						if(testing == 1)
							System.out.println("Couldnt allocate process " + i);
						
						FFwriter.println("-" + i);
					}
				}
			}
			
			//reset bounds
			for(int i = 0; i < numOfBlocks; i++){
				memorySlots[i].reset();
			}
			
			//Allocate BF
			//BFoutput.data
			if(testing == 1)
				System.out.println("Best Fit");
			int best = -1;
			for(int i = 0; i < numOfProc; i++){			
				for(int j = 0; j < numOfBlocks; j++){
					//if process can fit then allocate, else move to next block
					if(memorySlots[j].checkFit(processes[i]) >= 0){
						if(best == -1)
							best = j;
						else{
							if(memorySlots[best].checkFit(processes[i]) > memorySlots[j].checkFit(processes[i])){
								best = j;
							}
						}
					}
				}
				//allocate best fit
				if(best == -1){
					if(testing == 1)
						System.out.println("Couldnt Allocate process " + i);
					
					BFwriter.println("-"+ i);
				}else{
					if(testing == 1){
						System.out.println("Process " + i + " allocated to block " + 
								best + " from address " + memorySlots[best].getCurrentBound() + 
								" to " + (memorySlots[best].getCurrentBound() + processes[i]));
					}
					
					BFwriter.println(memorySlots[best].getCurrentBound() + 
							" " + (memorySlots[best].getCurrentBound() + processes[i]) + " "+ i);
					
					//process fits so allocate it
					memorySlots[best].setCurrentBound(processes[i]);	
									
				}
				best = -1;
			}
			
			//reset bounds
			for(int i = 0; i < numOfBlocks; i++){
				memorySlots[i].reset();
			}
			
			//Allocate WF
			//WFoutput.data
			if(testing == 1)
				System.out.println("Best Fit");
			int worst = -1;
			for(int i = 0; i < numOfProc; i++){			
				for(int j = 0; j < numOfBlocks; j++){
					//if process can fit then allocate, else move to next block
					if(memorySlots[j].checkFit(processes[i]) >= 0){
						if(worst == -1)
							worst = j;
						else{
							if(memorySlots[worst].checkFit(processes[i]) < memorySlots[j].checkFit(processes[i])){
								worst = j;
							}
						}
					}
				}
				//allocate best fit
				if(worst == -1){
					if(testing == 1)
						System.out.println("Couldnt Allocate process " + i);
					
					WFwriter.println("-" + i);
				}else{
					if(testing == 1){
						System.out.println("Process " + i + " allocated to block " + 
								worst + " from address " + memorySlots[worst].getCurrentBound() + 
								" to " + (memorySlots[worst].getCurrentBound() + processes[i]));
					}
					
					WFwriter.println(memorySlots[worst].getCurrentBound() + 
							" " + (memorySlots[worst].getCurrentBound() + processes[i]) + " "+ i);
					
					//process fits so allocate it
					memorySlots[worst].setCurrentBound(processes[i]);	
									
				}
				worst = -1;
			}
			
			FFwriter.close();
			BFwriter.close();
			WFwriter.close();
			input.close();
		}catch(FileNotFoundException e){
			System.out.println("ERROR: " + e.getMessage());
		}		
	}
}
