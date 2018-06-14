import java.io.*;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

//Task Chkr Program by Xavier La Rosa 5/8/2017
public class runMe
{
//THIS IS THE MAIN METHOD WHICH AS YOU CAN SEE IF FAIRLY SIMPLE, ALL YOU DO IS CALL A FILE TO FOR THE LOG IN LIST TEXT FILE
//AFTER DOING THAT YOU READ THE LOG FILE TO TAKE IN THE INFORMATION
//THEN YOU SIMPLY RUN THE FUNCTION AND WATCH THE MAGIC HAPPEN
	public static void main(String[] args)
	{
		try
		{
		File logIn=new File("C:\\LOGIN.txt");
		boolean check=logIn.createNewFile();
		if(check)
		{
			System.out.println("New LogIn file created for first time");
		}
		else
		{
			System.out.println("File already exists");
		}
		ReadLogFile(logIn);
		run();
		}
		catch(IOException e)
		{
			System.out.println("Exception Occured");
			e.printStackTrace();
		}
		
	}

//THESE RIGHT HERE ARE ALL THE ADT LISTS, PROTECTED VARIABLES, STATIC VARIABLES THAT ARE NEEDED
//IN ORDER TO ACCOMPLISH EVERY FUNCTION/METHOD DOWN BELOW	
//I USE A DATE CLASS TO GET THE REAL TIME DATE OF TODAY
//THAT I CAN USE TO CHANGE FROM A FEW DAYS BEFORE AND AFTER TOO
//THERE ARE DEFAULT VALUES FOR STRING METHODS THAT CAN HELP WITH FUNCTIONS

static LinkedList<String> memberNames=new LinkedList<String>();
static LinkedList<Integer> memberCodes=new LinkedList<Integer>();
static Queue<LinkedList> tasks=new LinkedList<LinkedList>();

static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
static Date currentDate=new Date();

//OVER HERE WHAT IS PRETTY COOL IS THAT I FOUND A WAY TO CREATE A USER SPECIFIC FILE FOR EACH MEMBER
//I HAVE A TEMPLATE FOR THE HEAD OF THE FILE NAME, THE TAIL OF THE FILE NAME AND THE USER'S ACCOUNT NAME WILL BE ADDED IN THE MIDDLE
//YOU CAN CHANGE THIS IF YOU MOVE THE FILE
static protected String currentMember="Default";
static protected String taskTemplateHead="C:\\";
static protected String taskTemplateTail=".txt";
static protected int command;

	//THIS IS THE RUN METHOD WHERE EVEYRHTING BASICALLY STARTS
	//THERE IS A MAIN STATEMENT THAT ASK THE USER IF THEY WANT TO LOG IN OR SIGN UPDATE
	//BY DOING SO WITH ACCEPTABLE INTEGER NUMBERS FOUND IN THE MENU THEY ACTIVATE THEIR
	//WANTED FUNCTIONS. FROM HERE THEY CAN DO AN ASSORTMENT OF FUNCTIONS YOU WILL SEE AS YOU READ 
	//DOWN BELOW
	static void run()
	{
		
			Scanner i=new Scanner(System.in);
			System.out.println("###################################\nWelcome to TaskChkr\n###################################\n");
			System.out.println("To use functions, enter the associated number of a function provided\nMenu:\n-----------------------------------\n1. log in\n2. sign up\n");
			int function=i.nextInt();
			command=function;
		
			Scanner readS=new Scanner(System.in);
		try 
		{
			if(function==1)
			{
				System.out.println("Name: ");
				String name=readS.nextLine();
				System.out.println("Code: ");
				int code=i.nextInt();
				
				SignIn(name, code);
			}
			else if(function==2)
			{
				System.out.println("Please enter a name for signing up: ");
				String name=readS.nextLine();
				System.out.println("Please enter a personal positive integer code for signing up: ");
				int code=i.nextInt();
				
				SignUp(name, code);
			}
			else
			{
				System.out.println("Error: Input not a function.\n");
				run();
			}
		}
		finally
		{
			i.close();
			readS.close();
		}
	}
	
	//THIS RECURSE RUN METHOD IS USED TO HELP THE USER GO BACK TO THE MAIN MENU
	//FROM HERE THEY CAN CHOOSE TO LOG OUT, MAKE A TASK, SEE ALL TASKS
	//INPUTTING A NUMBER WILL CHANGE THE VARIABLE COMMAND 
	//DEPENDING ON THE COMMAND VARIABLE IT WILL CALL A METHOD TO USE
	static void recurseRun()
	{
		Scanner i=new Scanner(System.in);
		try 
		{
			System.out.println("-----------------------------------\nWould you like to:"
			+"\n1. log out\n2. make a task"
			+"\n3. see all my tasks");
			int function=i.nextInt();
			command=function;
			if(command==1)
			{
				LogOut();
			}
			else if(command==2)
			{
				makeATask();
			}
			else if(command==3)
			{
				seeAllTasks();
			}
			else
			{
				System.out.println("Error: Input not a function.");
				recurseRun();
			}
		}
		finally
		{
			i.close();
		}
		
	}
	
	//THE USER USES THIS METHOD WHEN HE OR SHE WANTS TO SIGN UP FOR AN ACCOUNT
	//THEY ACCOMPLISH THIS BY BRINGING IN A STIRNG NAME AND INT PASSWORD
	//IF IT PASSES THE IF AND ELSE STATEMENTS CORRECTLY THEN IT WILL ADD THESE VARIABLES
	//TO THE LINKED LISTS WE HAVE
	static void SignUp(String name, int c)
	{
		System.out.println("Trying to sign "+name+" up...");
		if(memberCodes.contains(c)==true)
		{
			System.out.println("Name or code is already taken.");
			run();
		}
		else if(memberCodes.contains(c)==false)
		{
			System.out.println("Success. thanks for signing up!");
			memberNames.add(name);
			memberCodes.add(c);
			currentMember=name;
			recurseRun();
		}
	}
	
	//THE USER CALLS THIS METHOD WHEN HE OR SHE WANTS TO SIGN IN TO THEIR ACCOUNT
	//BY DOING SO THEY CHECK A NAME AND A CODE PASSWORD INTO THE FILE OF MEMBERS
	static void SignIn(String name, int c)
	{
		int checker=0;
		System.out.println("\nAttempting login...");
		for(int k=0;k<memberCodes.size();k++)
		{
			if(c==memberCodes.get(k)&&memberNames.get(k).equals(name))
			{
				System.out.println("Hello, "+name+", welcome!");
				currentMember=name;
				recurseRun();
				checker++;
			}
			else if(checker==0)
			{
				System.out.println("Error: Wrong name or code.");
			}
		}
	}
	
	//THIS IS A SIMPLE LOG OUT METHOD WHICH WILL HELP THE USER EXIT THE METHOD WHEN NEEDED
	//BEFORE DOING SO IT PRINTS OUT A GOODBYE STATEMENT AND THEN CALLS THE VERFIYLOGFILE METHOD
	//IN ORDER TO UPDATE EVERYTHING BEFORE LEAVING
	static void LogOut()
	{
		System.out.println("###################################\nGoodbye!\n###################################\n");
		verifyLogFile(memberNames, memberCodes);
		return;
	}
	
	//THIS METHOD IS USED TO READ THE FILE OF AN EXISTING USER
	//IT WILL PRINT OUT THE NAME AND CODE OF EACH MEMBER IN THE ORDER:
	//NAME FIRST, CODE NEXT
	//IT NEEDS A FILE TO READ FOR THE PARAMETER
	//I COMMENTED OUT THE PRINT STATEMENTS BUT YOU CAN USE IT TO SEE 
	//THE OUTPUT
	static void ReadLogFile(File x)
	{
		int k=0;
		
		try (BufferedReader br=new BufferedReader(new FileReader(x))) 
		{
			String line;
			while ((line=br.readLine())!= null) 
			{
				String[] lineArray=line.split(" ");   
				for(String str:lineArray) 
				{
					if(k%2==0)
					{
					//System.out.println("name: "+str);
					memberNames.add(str);
					k++;
					}
					else if(k%2!=0)
					{
					int code=Integer.parseInt(str);
					//System.out.println("code: "+code);
					memberCodes.add(code);
					k++;
					}
				}	
			}
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	//THIS METHOD SIMPLY RETURNS THE DATE WHICH CAN BE USED FOR A PRINTLN METHOD
	//THE FORMAT IS REPRESENTED WITH YYYY/MM/DD
	static String getDate()
	{
		return dateFormat.format(currentDate);
	}
	
	//THIS METHOD BASICALLY OBTAINS AN INTEGER VALUE
	//ONCE IT OBTAINS THE INTEGER VALUE IT PASSES IT THROUGH THE METHOD TO OBTAIN A DAY BEFORE
	//THE PRESENT DAY. THE INTEGER MUST BE A -1, -2, -3 IN ORDER TO PASS THE METHOD CORRECTLY
	//THIS THEN RETURNS A DATE 
	static String getDateBefore(int c)
	{
		///////String date=getDate();
		String newDate=getDate();
		//////char[] dateArray=date.toCharArray();
		
		if(c==(-1))
		{
			Calendar wholeCalendar=Calendar.getInstance();
			wholeCalendar.add(Calendar.DATE, -1);
			Date dateBefore= wholeCalendar.getTime();
			newDate=dateFormat.format(dateBefore);
			System.out.println(dateFormat.format(dateBefore));
		}
		else if(c==(-2))
		{
			Calendar wholeCalendar=Calendar.getInstance();
			wholeCalendar.add(Calendar.DATE, -2);
			Date dateBefore= wholeCalendar.getTime();
			newDate=dateFormat.format(dateBefore);
			System.out.println(dateFormat.format(dateBefore));
		}
		else if(c==(-3))
		{
			Calendar wholeCalendar=Calendar.getInstance();
			wholeCalendar.add(Calendar.DATE, -3);
			Date dateBefore= wholeCalendar.getTime();
			newDate=dateFormat.format(dateBefore);
			System.out.println(dateFormat.format(dateBefore));
		}
		else
			System.out.println("Error. Wrong input: "+c);
		return newDate;
	}
	
	//THIS METHOD BASICALLY OBTAINS AN INTEGER VALUE
	//ONCE IT OBTAINS THE INTEGER VALUE IT PASSES IT THROUGH THE METHOD TO OBTAIN A DAY AFTER
	//THE PRESENT DAY. THE INTEGER MUST BE A 1, 2, 3 IN ORDER TO PASS THE METHOD CORRECTLY
	//THIS THEN RETURNS A DATE
	static String getDateAfter(int c)
	{
		///////String date=getDate();
		String newDate=getDate();
		//////char[] dateArray=date.toCharArray();
		
		if(c==1)
		{
			Calendar wholeCalendar=Calendar.getInstance();
			wholeCalendar.add(Calendar.DATE, +1);
			Date dateBefore= wholeCalendar.getTime();
			newDate=dateFormat.format(dateBefore);
			System.out.println(dateFormat.format(dateBefore));
		}
		else if(c==2)
		{
			Calendar wholeCalendar=Calendar.getInstance();
			wholeCalendar.add(Calendar.DATE, +2);
			Date dateBefore= wholeCalendar.getTime();
			newDate=dateFormat.format(dateBefore);
			System.out.println(dateFormat.format(dateBefore));
		}
		else if(c==3)
		{
			Calendar wholeCalendar=Calendar.getInstance();
			wholeCalendar.add(Calendar.DATE, +3);
			Date dateBefore= wholeCalendar.getTime();
			newDate=dateFormat.format(dateBefore);
			System.out.println(dateFormat.format(dateBefore));
		}
		else
			System.out.println("Error. Wrong input: "+c);
		return newDate;
	}
	//JUST A SUPPORT METHOD TO HELP DECODE A STRING INTO CHAR ARRAY
	static char[] decodeDate(String d)
	{
		char[] defaultC={'d', 'e', 'f'};	
		////////String[] DateChar=d.split("/");
		return defaultC;
	}
static String path;
static boolean booleanForFile=false;

	//THIS IS A SUPPORT METHOD/ METHOD FOR THE USER TO SEE ALL TASKS
	//IT CAN HELP WITH THE PROGRAMMER TO SEE IF THE LIST IS ACCEPTABLE
	//THE USER CAN USE THIS TO CHECK IF HIS METHODS ARE CORRECT AND THAT
	//HIS TASKS ARE ACTUALLY IN THE LIST
	static void seeAllTasks()
	{
		//////String dateMatch=getDate();
		File tasks=new File((taskTemplateHead+currentMember+taskTemplateTail));
		////int k=0;
		
		try (BufferedReader br=new BufferedReader(new FileReader(tasks))) 
		{
			String line;
			System.out.println("###################################\n"+currentMember+"'s tasks\n###################################\n");
			while ((line=br.readLine())!= null) 
			{
				System.out.println(line);	
			}
		}
		catch (FileNotFoundException e) 
		{
			System.out.println("File does not exist. Which could mean, the user has not yet made any tasks. Making your first task will create a file for the user.");
			e.printStackTrace();
		}
		catch(IOException e)
		{
		}
		recurseRun();
	}
	
	//THIS METHOD WILL ACTUALLY OUTPUT A SCANNER FOR THE USER TO MAKE A TASK
	//IT WILL HAVE A MENU SYSTEM ASKING WHICH DAY THEY WANT TO ADD THEIR TASK INTO
	//THE INTEGERS ARE SENT OVER TO THE MEMBERTASKS METHOD TO SEE IF IT IS ACCEPTABLE
	static void makeATask()
	{
		Scanner s=new Scanner(System.in);
		Scanner i=new Scanner(System.in);
		try 
		{
			System.out.println("What day? \n-3=3 days before, -2=2 days before, -1=1 day before\n0=0 days before, 1=1day after, 2=2 days after, 3=3 days after\n");
			int setDay=i.nextInt();
			System.out.println("... add a task: ");
			String task=s.nextLine();
			
			memberTasks((taskTemplateHead+currentMember+taskTemplateTail), task, setDay);
		}
		finally
		{
			s.close();
			i.close();
		}
	}
	
	//THIS METHOD TAKES IN A STRING OF FILE NAME, A TEXT TO ADD AND AN INTEGER TO SET THE DAY OF THE TASK
	//IT WILL HAVE A SERIES OF TRY AND CATCHES TO SET THE DAY, AND ADD THE DATE INTO THE FILE
	//THERE IS ALSO A RECURSIVE METHOD TO RUN THE MAIN MENU AGAIN SO THAT THE USER CAN USE OTHER FUNCTIONS
	static void memberTasks(String fileName, String textToAdd, int setDay)
	{
		FileEditor data=new FileEditor(fileName, true);
		try
		{
			System.out.println("set day: "+setDay);
			if(setDay==0)
			{
			data.writingToFile(getDate());
			}
			else if(setDay>0)
			{
			data.writingToFile(getDateAfter(setDay));
			}
			else if(setDay<0)
			{
				
			data.writingToFile(getDateBefore(setDay));
			}
		}
		catch(IOException e)
		{
			System.out.println("Sorry, no DATE can do!");
		}
		try
		{
		data.writingToFile(textToAdd);
		}
		catch(IOException e)
		{
			System.out.println("Sorry, no can do!");
		}
		recurseRun();
	}	
	
	
	
	//THIS METHOD IS THE END POINT FROM LOGGING OFF AND VERIFYLOGFILE METHOD
	//THIS WILL ADD DATA TO THE TEXT FILE DOWNWARDS AS IT KEEPS GOING
	static void updateLogFile(String textToAdd)
	{
		FileEditor data=new FileEditor("C:\\LOGIN.txt", true);
		try
		{
		data.writingToFile(textToAdd);
		}
		catch(IOException e)
		{
			System.out.println("Sorry, no can do!");
		}
	}
	
	//THIS METHOD IS TO BASICALLY HELP RUN THE UPDATE LOG FILE, THIS METHOD
	//IS CALLED RIGHT BEFORE LOGGING OFF SO THAT ALL INFORMATION IS THEN UPDATED INTO THE FILE
	//THIS METHOD TAKES IN A LINKED LIST OF NAME AND CODES AND TRIES TO VERIFY IF THESE 
	//NAMES AND CODES ARE IN THE FILE, IF NOT THEN THEY ARE ADDED ON.
	static void verifyLogFile(LinkedList<String> names, LinkedList<Integer> codes)
	{
		int k=0;
		LinkedList<String> everyLine=new LinkedList<String>();
		try (BufferedReader br=new BufferedReader(new FileReader("C:\\LOGIN.txt"))) 
		{
			String line;
			while ((line=br.readLine())!= null) 
			{
				everyLine.add(line);
			}
			for(k=0;k<memberNames.size();k++)
			{
				
					if(everyLine.contains(memberNames.get(k))==false)
					{
						updateLogFile(memberNames.get(k));
						String codeString=Integer.toString(memberCodes.get(k));
						updateLogFile(codeString);
					}
			}
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	//THIS IS A SUPPORT METHOD TO HELP WITH PRINTING OUT THE MEMBER NAMES LIST IN THIS CLASS
	//THIS CAN BE USED TO CHECK HOW EVERYTHING IS BEING UPDATED EFFICIENTLY AND CORRECTLY
	//BETWEEN THE LISTS AND THEIR RESPECTIVE FILES
	static void printList()
	{		
		int k;
		for(k=0;k<memberNames.size();k++)
		{
			System.out.println(memberNames.get(k)+"\n"+memberCodes.get(k));
		}
	}
	
	//THIS IS A SUPPORT METHOD TO HELP WITH PRINTING OUT THE TEXT IN THE MEMBER FILE IN CASE IT IS NEEDED
	//THIS IS DONE BY CALLING THE FILE AND USING A TRY AND CATCH WITH A WHILE LOOP TO PRINT OUT EVERY LINE
	//THAT IS STORED IN THE STRING LINE
	static void printMemberFile()
	{
		File members=new File("C:\\LOGIN.txt");
		
		try (BufferedReader br=new BufferedReader(new FileReader(members))) 
		{
			String line;
			while ((line=br.readLine())!= null) 
			{
				System.out.println(line);	
			}
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
		}

	}	
}

/*
AUTHOR'S NOTES:

1. This program is a log in database that holds specific tasks for every user.
The user can input a task for present day, days before, or days after. The data
will be saved in their own personal text file. Signing up for the program will
also save user name and user code into a database text file. Once the user simply
uses the run method, all the methods work in the background as the user inputs data.
Logging out will say goodbye, then update any data that is freshly made or removed.
The updated data will be put into their respective text files.

2. To run functions within the program you must type it EXACTLY as displayed
	log in
	sign up
	make a task
	see all my tasks
	log out
	
3. When you want to see tasks in a specific day, keep in mind you are limited
to seeing tasks from three days in the past, present day, and three days in
the future. This is to prevent useless data from slowing down the program.

3. ERROR: I know that if you try to make a task within a different day other 
than the present, it will still print out as the present day. I do not know
how to fix this issue so far.

4. ERROR: I tried making the getDate method not only return the date but also 
run the method setValidDayRange so it can create a linked list with a total
of seven days. However, if I were to uncomment the statement that runs the
setValidDayRange method, I will get a stack overflow error which I do not know
how to fix.
*/


/////////OUTPUTS////////////////////////////////

////////////////////////////////SCENARIO 1////////////////////////////////////////////
/*
###################################
Welcome to TaskChkr
###################################

To use functions, enter the associated number of a function provided
Menu:
-----------------------------------
1. log in
2. sign up

2
Please enter a name for signing up:
Xavier
Please enter a personal positive integer code for signing up:
23
Trying to sign Xavier up...
Success. thanks for signing up!
-----------------------------------
Would you like to:
1. log out
2. make a task
3. see all my tasks
2
What day?
-3=3 days before, -2=2 days before, -1=1 day before
0=0 days before, 1=1day after, 2=2 days after, 3=3 days after

-1
... add a task:
Do the dishes
set day: -1
2017/05/07
-----------------------------------
Would you like to:
1. log out
2. make a task
3. see all my tasks
2
What day?
-3=3 days before, -2=2 days before, -1=1 day before
0=0 days before, 1=1day after, 2=2 days after, 3=3 days after

0
... add a task:
Wash the car
set day: 0
-----------------------------------
Would you like to:
1. log out
2. make a task
3. see all my tasks
2
What day?
-3=3 days before, -2=2 days before, -1=1 day before
0=0 days before, 1=1day after, 2=2 days after, 3=3 days after

3
... add a task:
Hand in assignments for class
set day: 3
2017/05/11
-----------------------------------
Would you like to:
1. log out
2. make a task
3. see all my tasks
3
###################################
Xavier's tasks
###################################

2017/05/07
Do the dishes
2017/05/08
Wash the car
2017/05/11
Hand in assignments for class
-----------------------------------
Would you like to:
1. log out
2. make a task
3. see all my tasks
1
###################################
Goodbye!
###################################
*/

////////////////////////////////SCENARIO 2////////////////////////////////////////////
/*
###################################
Welcome to TaskChkr
###################################

To use functions, enter the associated number of a function provided
Menu:
-----------------------------------
1. log in
2. sign up

2
Please enter a name for signing up:
Xavier
Please enter a personal positive integer code for signing up:
23
Trying to sign Xavier up...
Name or code is already taken.
###################################
Welcome to TaskChkr
###################################

To use functions, enter the associated number of a function provided
Menu:
-----------------------------------
1. log in
2. sign up

1
Name:
Xavier
Code:
23

Attempting login...
Hello, Xavier, welcome!
-----------------------------------
Would you like to:
1. log out
2. make a task
3. see all my tasks
3
###################################
Xavier's tasks
###################################

2017/05/07
Do the dishes
2017/05/08
Wash the car
2017/05/11
Hand in assignments for class
-----------------------------------
Would you like to:
1. log out
2. make a task
3. see all my tasks
1
###################################
Goodbye!
###################################
*/

////////////////////////////////SCENARIO 3////////////////////////////////////////////
/*
###################################
Welcome to TaskChkr
###################################

To use functions, enter the associated number of a function provided
Menu:
-----------------------------------
1. log in
2. sign up

2
Please enter a name for signing up:
Michelle
Please enter a personal positive integer code for signing up:
12345
Trying to sign Michelle up...
Success. thanks for signing up!
-----------------------------------
Would you like to:
1. log out
2. make a task
3. see all my tasks
3
File does not exist. Which could mean, the user has not yet made any tasks. Making your first task will create a file for the user.
java.io.FileNotFoundException: d:\ds\Michelle.txt (The system cannot find the file specified)
        at java.io.FileInputStream.open0(Native Method)
        at java.io.FileInputStream.open(Unknown Source)
        at java.io.FileInputStream.<init>(Unknown Source)
        at java.io.FileReader.<init>(Unknown Source)
        at runMe.seeAllTasks(runMe.java:290)
        at runMe.recurseRun(runMe.java:85)
        at runMe.SignUp(runMe.java:107)
        at runMe.run(runMe.java:59)
        at runMe.main(runMe.java:17)
-----------------------------------
Would you like to:
1. log out
2. make a task
3. see all my tasks
2
What day?
-3=3 days before, -2=2 days before, -1=1 day before
0=0 days before, 1=1day after, 2=2 days after, 3=3 days after

0
... add a task:
Go take out trash
set day: 0
-----------------------------------
Would you like to:
1. log out
2. make a task
3. see all my tasks
1
###################################
Goodbye!
###################################
*/
