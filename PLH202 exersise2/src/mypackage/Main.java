package mypackage;

import java.util.Random;
/**
 * 
 * @author christos ioannidis 2018030006
 * 
 * 
 * 
 * int averageCompares:average number of compares
 * long proccessTime:used to store the time length of a proccess
 * long startTime:used to calculate the time length of a proccess
 * long endTime:used to calculate the time length of a proccess
 * long programProccessTime:used to store the total runtime of the program
 * long programStartTime:used to calculate the total runtime of the program
 * long programEndTime:used to calculate the total runtime of the program
 */
public class Main {

	public static void main(String[] args) {
		
		int averageCompares=0;
		long proccessTime=0;
		long startTime=0;
		long endTime=0;
		long programProccessTime=0;
		long programStartTime=0;
		long programEndTime=0;
		
		
		programStartTime=System.nanoTime();
		
		
		/**
		 * 100 random int
		 */
		//Random random=new Random();
		int[] randomNumbers=new int[] {-1288967718,-1016388224,821284112,-1423973986,1189783758,-951906307,1512953614,-766639019,166198583,-2017757249,-1429511438,-1898844316,2017454084,-1911099375,1205047013,400459541,2049502692,632256592,485175056,1764924016,-1677908144,-1746541158,70027071,1709983255,-1066021682,-701385787,-1651606848,581906515,-950221966,-106401389,515135121,-1578503575,86764968,-1055704029,-1242719815,1022903090,1363821617,219938217,-172007434,1789179626,-854394005,1552114529,-2016980329,-1738135481,-976013100,-1007960384,-1469955157,-1126031667,1806638288,-1932245524,-1770072229,419350112,-1087953750,19097454,-962142316,241454106,-1962727208,1398154534,-1143976472,-537480097,-2131199102,-1437226369,-2043617354,-942768595,1622469445,710119906,241035399,-808998984,995143219,622890351,598990523,-618403787,1959098654,595093226,1184337508,-97111306,-1440135402,553224947,64820849,1910167478,1917818159,2100138874,2092897294,1759564854,1599870599,-529824400,412262475,1388194568,1459496070,-1431063102,-250936325,1781683114,1690070887,-1338996350,162153950,-1390995499,2049749751,-1173977753,-316016894,768700951};
		/*for(int i=0;i<randomNumbers.length;i++) {
			randomNumbers[i]=random.nextInt();
			System.out.print(randomNumbers[i]+",");
			//System.out.println(randomNumbers[i]);
		}*/
		
		
		
		
		/**
		 * input from file
		 */
		String fileDirectory=args[0];
		//FileInput fi=new FileInput("C:\\Users\\chris\\Desktop\\testnumbers_50_BE.bin");
		FileInput fi=new FileInput(fileDirectory);
		int[] fileInt=fi.getFileInt();
		
		
		
		
		/**
		 * bst initialisation
		 */
		
		int numberOfInt=fi.getNumberOfInt();
		BST bst=new BST(numberOfInt);
		
		startTime=System.nanoTime();
		for(int i=0;i<fi.getNumberOfInt();i++) {
			bst.add(fileInt[i]);
			averageCompares+=bst.getNumberOfCompares();
		}
		endTime=System.nanoTime();
		proccessTime=endTime-startTime;
		
				
		averageCompares/=numberOfInt;
		
		
		
		System.out.print("Average compares(input)|total time for "+numberOfInt+" inputs|");
		System.out.print("average compares(search)|total time for "+numberOfInt+" searches|");
		System.out.println("average compares(range k=100)|average compares(range k=1000)");
		if(proccessTime/1000000>=1) {
			proccessTime/=1000000;
			System.out.print("array tree|"+averageCompares+"|"+proccessTime+" ms|");
		}
		else {
			System.out.print("array tree|"+averageCompares+"|"+proccessTime+" ns|");
		}
		
		
		/**
		 * search for 100 random int
		 */
		startTime=System.nanoTime();
		averageCompares=0;
		for(int i=0;i<100;i++) {
			bst.find(randomNumbers[i]);
			averageCompares+=bst.getNumberOfCompares();
		}
		endTime=System.nanoTime();
		proccessTime=endTime-startTime;
		
				
		averageCompares/=100;
		if(proccessTime/1000000>=1) {
			proccessTime/=1000000;
			System.out.print(averageCompares+"|"+proccessTime+" ms|");
		}
		else {
			System.out.print(averageCompares+"|"+proccessTime+" ns|");
		}
		
		
		/**
		 * range search
		 */
		averageCompares=0;
		for(int i=0;i<100;i++) {
			bst.range(bst.getRoot(), randomNumbers[i], randomNumbers[i]+100);
			averageCompares+=bst.getNumberOfCompares();
		}
				
		averageCompares/=100;
		
		System.out.print(averageCompares+"|");
		
		averageCompares=0;
		for(int i=0;i<100;i++) {
			bst.range(bst.getRoot(), randomNumbers[i], randomNumbers[i]+1000);
			averageCompares+=bst.getNumberOfCompares();
		}
				
		averageCompares/=100;
		
		System.out.println(averageCompares+"|");
		
		
		
		
		
		
		
		
		
		
		
		/**
		 * binary search algorithm
		 */
		
		
		System.out.print("\n Average compares(search)|total time for "+numberOfInt+" searches|");
		System.out.println("Average compares(range k=100)|average compares(range k=1000)");
		int[] inOrderInt=bst.inOrder(bst.getRoot());
		BinarySearchAlgorithm algorithm=new BinarySearchAlgorithm(inOrderInt);
		
		startTime=System.nanoTime();
		averageCompares=0;
		for(int i=0;i<100;i++) {
			algorithm.find(randomNumbers[i]);
			averageCompares+=algorithm.getNumberOfCompares();
		}
		
		
		
		endTime=System.nanoTime();
		proccessTime=endTime-startTime;
		
				
		averageCompares/=100;
		if(proccessTime/1000000>=1) {
			proccessTime/=1000000;
			System.out.print("bianry search algorithm|"+averageCompares+"|"+proccessTime+" ms|");
		}
		else {
			System.out.print("bianry search algorithm|"+averageCompares+"|"+proccessTime+" ns|");
		}
		
		
		
		/**
		 * range search
		 */
		
		averageCompares=0;
		for(int i=0;i<100;i++) {
			algorithm.range(randomNumbers[i], randomNumbers[i]+100);
			averageCompares+=algorithm.getNumberOfCompares();
		}
				
		averageCompares/=100;
		
		System.out.print(averageCompares+"|");
		
		averageCompares=0;
		for(int i=0;i<100;i++) {
			algorithm.range(randomNumbers[i], randomNumbers[i]+1000);
			averageCompares+=algorithm.getNumberOfCompares();
		}
				
		averageCompares/=100;
		
		System.out.println(averageCompares+"|");
		
		
		
		
		
		
		
		/**
		 * dynamic bst initialisation
		 */
		System.out.print("\n Average compares(input)|total time for "+numberOfInt+" inputs|");
		System.out.print("average compares(search)|total time for "+numberOfInt+" searches|");
		System.out.println("average compares(range k=100)|average compares(range k=1000)");
		
		
		BinarySearchTreeDynamic bstd=new BinarySearchTreeDynamic();
		averageCompares=0;
		startTime=System.nanoTime();
		for(int i=0;i<fi.getNumberOfInt();i++) {
			bstd.add(fileInt[i]);
			averageCompares+=bstd.getNumberOfCompares();
		}
		endTime=System.nanoTime();
		proccessTime=endTime-startTime;
		
		
		averageCompares/=numberOfInt;
		if(proccessTime/1000000>=1) {
			proccessTime/=1000000;
			System.out.print("binary search tree dynamic|"+averageCompares+"|"+proccessTime+" ms|");
		}
		else {
			System.out.print("binary search tree dynamic|"+averageCompares+"|"+proccessTime+" ns|");
		}
		
		
		
		
		
		
		/**
		 * search for 100 random int
		 */
		
		startTime=System.nanoTime();
		averageCompares=0;
		Node root=bstd.getRoot();
		for(int i=0;i<100;i++) {
			bstd.find(randomNumbers[i]);
			averageCompares+=bstd.getNumberOfCompares();
		}
		endTime=System.nanoTime();
		proccessTime=endTime-startTime;
		
				
		averageCompares/=100;
		
		if(proccessTime/1000000>=1) {
			proccessTime/=1000000;
			System.out.print(averageCompares+"|"+proccessTime+" ms|");
		}
		else {
			System.out.print(+averageCompares+"|"+proccessTime+" ns|");
		}
		
		
		/**
		 * range search
		 */
		
		averageCompares=0;
		for(int i=0;i<100;i++) {
			bstd.range(root, randomNumbers[i], randomNumbers[i]+100);
			averageCompares+=bstd.getNumberOfCompares();
		}
				
		averageCompares/=100;
		
		System.out.print(averageCompares+"|");
		
		averageCompares=0;
		for(int i=0;i<100;i++) {
			bstd.range(root, randomNumbers[i], randomNumbers[i]+1000);
			averageCompares+=bstd.getNumberOfCompares();
		}
				
		averageCompares/=100;
		
		System.out.println(averageCompares+"|");
		
		
		programEndTime=System.nanoTime();
		programProccessTime=programEndTime-programStartTime;
		
		if(programProccessTime/1000000>=1) {
			programProccessTime/=1000000;
			System.out.print("\nTotal run time:"+programProccessTime+" ms");
		}
		else {
			System.out.print("\nTotal run time:"+programProccessTime+" ns");
		}
		
		
		
		
		

	}

}
