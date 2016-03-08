import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class hillcipher {
	
	static int n;
	static int[][] Matrix = new int[10][10];
	
	
	public static String CipherofHill(String str)
	{
		
		int[] intArry = new int[n];	
		StringBuilder sb = new StringBuilder();
		String Alphabet = "abcdefghijklmnopqrstuvwxyz";
		for(int i= 0; i<str.length(); i++)
		{
			//utilize ASCII values
			//intArry[i] = ((int)str.charAt(i))-97;
			intArry[i] = Alphabet.indexOf(str.charAt(i));
		}
		//new array for holding values of matrix multiplications
		int[] newNums = new int[n];
		for(int i = 0; i<n; i++)
		{
			newNums[i] = 0;
			for(int j = 0; j<n; j++)
			{
				newNums[i] += intArry[j]*Matrix[i][j];
			}
			newNums[i] %= 26;
		}
		String newStr = "";
		for(int i=0; i<n; i++)
		{
			newStr+=(Alphabet.charAt(newNums[i]));
		}
		return newStr;
		}
	 static int[][] LoadData(String file) throws Exception
	 {
	    
	   File input = new File(file);
	   Scanner in = new Scanner(input);
	   
	  
	   //get size of matrix which will be nxn
	   n = in.nextInt();
	  
	   int[][] Matrix = new int[n][n];
	   //get matrix values
	   for(int i = 0; i<n; i++)
	   {
		    
		   for(int j = 0; j<n; j++)
		   {
			   Matrix[i][j] = in.nextInt();
		   }
	   }

	   return Matrix;
	 }
	 static String parseString(String str)
	 {
		 //base case where string does not need to be parsed because already less than 80 chars
		 if(str.length()<80)
		 {
			 return str;
		 }
		 //print the first 80 chars
		 for(int i =0; i<80; i++)
		 {
			 System.out.print(str.charAt(i));
		 }
		 System.out.println(" ");
		 
		 //recursively call method from the appropriate position in the string
		 return parseString(str.substring(80));
	 }
	

	public static void main(String[] args) throws FileNotFoundException {

	if(args.length!=2)
	{
		System.out.println("Improper input. Format : input_file text_file");
	}
	String key = args[0];
	String text = args[1];
	
	System.out.println("Key value: ");
	try
	 {
		Matrix = LoadData(key);
		
		System.out.println(" ");
		System.out.println(n);
		for(int i = 0; i<n; i++)
		{
			for(int j=0; j<n; j++)
			{
				System.out.print(Matrix[i][j]+ " ");
			}
			System.out.println(" ");
		}
	 }
	 catch(Exception ex)
	{
			 
	}
	System.out.println(" ");
	System.out.println("Plaintext input to program: ");
	
	File input = new File(text);
	Scanner sc = new Scanner(input);
	Scanner sc2 = new Scanner(input);
	
	//output unaltered plaintext file
	while(sc2.hasNextLine())
	{
		System.out.println(sc2.nextLine());
	}
	
	StringBuilder sb2 = new StringBuilder();
	//get plaintext ready to be encrypted
	while(sc.hasNext())
	{
		sb2.append(sc.next());
	}
	
	String tempStr = sb2.toString();
	//tempStr = tempStr.replaceAll("[^a-zAZ]", "");
	tempStr = tempStr.toLowerCase();
	sb2 = new StringBuilder(tempStr);
	
	//pad with x's if it is not divisible by the matrix size(matrix multiplication will not work otherwise)
	while(sb2.length()%n!=0)
	{
		sb2.append("x");
	}
	String encode = sb2.toString();
	StringBuilder sb3 = new StringBuilder();
	
	//chunk the word into substrings of length n
	for(int j =0; j<encode.length(); j+=n)
	{
		//call method for each substring as it is created
		String tmp2 = encode.substring(j, j+n);
		sb3.append(CipherofHill(tmp2));
	}
	
	System.out.println(" ");
	System.out.println("Ciphertext output: ");
	//call recursive method to parse the string into chunks of 80 characters
	System.out.println(parseString(sb3.toString()));
	

	}
}
	