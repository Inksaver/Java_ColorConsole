package inksaver;
import java.io.IOException;
import java.util.*;

//import inksaver.AutoRunFromConsole.OSType;

public class Program
{
	private static Scanner input = new Scanner(System.in);  // Create a Scanner object
	public static void main(String[] args)
	{
		AutoRunFromConsole.runYourselfInConsole(false);
		UI.SetConsole(80, 25, "white", "black", true); //set to default size 80 x 25, white text on black bg 
		Boolean quit = false;
		while (!quit) //present a menu of possible choices, which redraws after input errors and completing a task
        {
            String title = "This is a demo of 'UI.Menu': Choose another demo from the following";
            List<String> options = new ArrayList<String>();
         // Every menu choice has a colour string: ~colour~
            options.add("~green~Show a mix of different colours");
            options.add("~dgreen~Show colour mix NOT using UI (DemoWithoutUI())");
            options.add("~green~Show 'UI.Print' method");
            options.add("~dgreen~Show 'Print' NOT using UI (PrintWithoutUI())");
            options.add("~green~Show 'UI.DrawLine' method");
            options.add("~green~Show 'UI.DrawBox..' methods");
            options.add("~green~Show 'UI.MultiBox..' methods");
            options.add("~green~Show 'UI.Grid..' methods");
            options.add("~green~Show 'UI.InputBox' method");
            options.add("~green~Show 'UI.DisplayMessage' method");
            options.add("~green~Show 'UI.User Input' methods");
            options.add("~red~Play the awesome 'Guess The Number' Game!");
            options.add("~magenta~Quit");
            /// Menu(String style,  String title, String promptChar, List<String> textLines, String foreColor, String backColor, String align, int width) ///
            
            int choice = UI.Menu("d", title, ">_", options, "white", "blackbg", "left", 0);
            if (choice == 0)        ColorPrintDemo();
            else if (choice == 1)   DemoWithoutUI();
            else if (choice == 2)   PrintDemo();
            else if (choice == 3)   PrintWithoutUI();
            else if (choice == 4)   LineDemo();
            else if (choice == 5)   BoxDemo();
            else if (choice == 6)   MultiBoxDemo();
            else if (choice == 7)   GridDemo();
            else if (choice == 8)   InputBoxDemo();
            else if (choice == 9)   DisplayMessageDemo();
            else if (choice == 10)  UserInputDemo();
            else if (choice == 11)  Game.GuessTheNumber();
            else if (choice == 12)  quit = true;
        }
		input.close();
		UI.Quit(false);
	}
	private static void BoxDemo()
    {
        /// All user i/o handled by UI class
        /// DrawBoxOutline(string style, string part, string foreColor, string backColor,
        ///                string boxAlign = "left", int width = 0, bool midMargin = false)
        /// DrawBoxBody(string style, string text, string boxAlign, string foreColor, string backColor,
        ///             string textColor = "WHITE", string textBackColor = "BLACKBG",
        ///             string textAlign = "left", int width = 0)
        
        UI.Clear();
        int numLines = UI.DrawBoxOutline("d", "top", "yellow", "black", "left", 0, false);
        numLines += UI.DrawBoxBody("d","This is ~blue~blue and ~green~green text in a ~yellow~yellow box!",
                                   "centre","yellow", "black", "white", "black", "centre", 0);
        numLines += UI.DrawBoxOutline("d", "mid", "yellow", "black", "left", 0, false);
        numLines += UI.DrawBoxBody("d", "", "centre", "yellow", "black", "white", "black", "centre", 0);
        numLines += UI.DrawBoxBody("d", "", "centre", "yellow", "black", "white", "black", "centre", 0);
        numLines += UI.DrawBoxBody("d", "", "centre", "yellow", "black", "white", "black", "centre", 0);
        numLines += UI.DrawBoxBody("d", "", "centre", "yellow", "black", "white", "black", "centre", 0);
        numLines += UI.DrawBoxBody("d", "", "centre", "yellow", "black", "white", "black", "centre", 0);
        numLines += UI.DrawBoxOutline("d", "bottom", "yellow", "black", "left", 0, false);
        numLines += UI.AddLines(5, numLines, "WHITE", "BLACKBG");
        numLines += UI.DrawLine("D", "WHITE", "black", 0, "left");
        UI.DisplayMessage("", true, false, "WHITE", "BLACKBG", 2000);
    }
	private static void ColorPrintDemo()
    {
		UI.Clear();
		int numLines = UI.ColorPrint("~WHITE~" + "WHITE + This line is white on black.", true, true);
		numLines = numLines + UI.ColorPrint("~GREY~" + "GREY + This line is grey on black.", true, true);
		numLines = numLines + UI.ColorPrint("~DGREY~" + "DGREY + This line is dark grey on black.", true, true);
		numLines = numLines + UI.ColorPrint("~BLUE~" + "BLUE + This line is blue on black.", true, true);
		numLines = numLines + UI.ColorPrint("~GREEN~" + "GREEN + This line is green on black.", true, true);
		numLines = numLines + UI.ColorPrint("~CYAN~" + "CYAN + This line is cyan on black.", true, true);
		numLines = numLines + UI.ColorPrint("~RED~" + "RED + This line is red on black.", true, true);
		numLines = numLines + UI.ColorPrint("~MAGENTA~" + "MAGENTA + This line is magenta on black.", true, true);
		numLines = numLines + UI.ColorPrint("~YELLOW~" + "YELLOW + This line is yellow on black.", true, true);
		numLines = numLines + UI.ColorPrint("~DBLUE~" + "DBLUE + This line is dark blue on black.", true, true);
		numLines = numLines + UI.ColorPrint("~DGREEN~" + "DGREEN + This line is dark green on black", true, true);
		numLines = numLines + UI.ColorPrint("~DCYAN~" + "DCYAN + This line is dark cyan on black.", true, true);
		numLines = numLines + UI.ColorPrint("~DRED~" + "DRED + This line is dark red on black.", true, true);
		numLines = numLines + UI.ColorPrint("~DMAGENTA~" + "DMAGENTA + This line is dark magenta on black.", true, true);
		numLines = numLines + UI.ColorPrint("~DYELLOW~" + "DYELLOW + This line is dark yellow on black.", true, true);
		numLines = numLines + UI.ColorPrint("~BLACK~" + "~WHITEBG~" + "BLACK + WHITEBG + This line is black on white.", true, true);
		numLines = numLines + UI.ColorPrint("~WHITE~" + "WHITE + This line is white, and now " + "~RED~" + " + RED + red on black.", true, true);
		numLines = numLines + UI.ColorPrint("~GREEN~" + "~REDBG~" + "GREEN + REDBG + This line is green " + "~RED~" + "~GREENBG~" + "RED + GREENBG +  on red.", true, true);
		numLines = numLines + UI.ColorPrint("~RED~" + "~GREENBG~" + "RED + GREENBG + This line is red" + "~GREEN~" + "~REDBG~" + " + GREEN + REDBG +  on green.", true, true);
		numLines = numLines + UI.AddLines(5, 19, "WHITE", "BLACKBG");
		numLines = numLines + UI.DrawLine("d", "WHITE", "BLACKBG", 0, "left");
		UI.DisplayMessage("Press Enter to continue...", true, false, "WHITE", "BLACKBG", 2000);
    }
	private static void DemoWithoutUI()
	{
		try
        {
            if (System.getProperty("os.name").contains("Windows"))
            {
            	Runtime.getRuntime().exec((char)44 + "mode 80;25" + (char)44);
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else
            {
            	String cmd = "\\e[8;25;80t";
				Runtime.getRuntime().exec(cmd);
            	Runtime.getRuntime().exec("clear");
            }
        }
        catch (IOException | InterruptedException ex) {}
		
		String spaces = "                                            ";
		if(System.console() == null)
        {
			System.out.println("[0m"); //reset
			System.out.println("[100m(char)27 + '[100m'  " + spaces);
			System.out.println("[101m(char)27 + '[101m'  " + spaces);
			System.out.println("[102m(char)27 + '[102m'  " + spaces);
			System.out.println("[103m(char)27 + '[103m'  " + spaces);
			System.out.println("[104m'[104m'  " + spaces);
			System.out.println("[105m'[105m'  " + spaces);
			System.out.println("[106m'[106m'  " + spaces);
			System.out.println("[107m'[107m'  " + spaces);
			System.out.println("[40m'[40m'  " + spaces);
			System.out.println("[41m'[41m'  " + spaces);
			System.out.println("[42m'[42m'  " + spaces);
			System.out.println("[43m'[43m'  " + spaces);
			System.out.println("[44m'[44m'  " + spaces);
			System.out.println("[45m'[45m'  " + spaces);
			System.out.println("[46m'[46m'  " + spaces);
			System.out.println("[47m'[47m'  " + spaces);
			System.out.println("[0m"); //reset
			System.out.println("[30m'[30m'  " + spaces);
			System.out.println("[31m'[31m'  " + spaces);
			System.out.println("[32m'[32m'  " + spaces);
			System.out.println("[33m'[33m'  " + spaces);
			System.out.println("[34m'[34m'  " + spaces);
			System.out.println("[35m'[35m'  " + spaces);
			System.out.println("[36m'[36m'  " + spaces);
			System.out.println("[37m'[37m'  " + spaces);
			System.out.println("[90m'[90m'  " + spaces);
			System.out.println("[91m'[91m'  " + spaces);
			System.out.println("[92m'[92m'  " + spaces);
			System.out.println("[93m'[93m'  " + spaces);
			System.out.println("[94m'[94m'  " + spaces);
			System.out.println("[95m'[95m'  " + spaces);
			System.out.println("[96m'[96m'  " + spaces);
			System.out.println("[97m'[97m'  " + spaces);
			
			System.out.print("Press Enter to continue...");
        }
		else
		{
			System.out.println((char)27 + "[0m"); //reset
			System.out.println((char)27 + "[100m(char)27 + '[100m'  " + spaces);
			System.out.println((char)27 + "[101m(char)27 + '[101m'  " + spaces);
			System.out.println((char)27 + "[102m(char)27 + '[102m'  " + spaces);
			System.out.println((char)27 + "[103m(char)27 + '[103m'  " + spaces);
			System.out.println((char)27 + "[104m(char)27 + '[104m'  " + spaces);
			System.out.println((char)27 + "[105m(char)27 + '[105m'  " + spaces);
			System.out.println((char)27 + "[106m(char)27 + '[106m'  " + spaces);
			System.out.println((char)27 + "[107m(char)27 + '[107m'  " + spaces);
			System.out.println((char)27 + "[40m(char)27 + '[40m'  " + spaces);
			System.out.println((char)27 + "[41m(char)27 + '[41m'  " + spaces);
			System.out.println((char)27 + "[42m(char)27 + '[42m'  " + spaces);
			System.out.println((char)27 + "[43m(char)27 + '[43m'  " + spaces);
			System.out.println((char)27 + "[44m(char)27 + '[44m'  " + spaces);
			System.out.println((char)27 + "[45m(char)27 + '[45m'  " + spaces);
			System.out.println((char)27 + "[46m(char)27 + '[46m'  " + spaces);
			System.out.println((char)27 + "[47m(char)27 + '[47m'  " + spaces);
			System.out.println((char)27 + "[0m"); //reset
			System.out.println((char)27 + "[30m(char)27 + '[30m'  " + spaces);
			System.out.println((char)27 + "[31m(char)27 + '[31m'  " + spaces);
			System.out.println((char)27 + "[32m(char)27 + '[32m'  " + spaces);
			System.out.println((char)27 + "[33m(char)27 + '[33m'  " + spaces);
			System.out.println((char)27 + "[34m(char)27 + '[34m'  " + spaces);
			System.out.println((char)27 + "[35m(char)27 + '[35m'  " + spaces);
			System.out.println((char)27 + "[36m(char)27 + '[36m'  " + spaces);
			System.out.println((char)27 + "[37m(char)27 + '[37m'  " + spaces);
			System.out.println((char)27 + "[90m(char)27 + '[90m'  " + spaces);
			System.out.println((char)27 + "[91m(char)27 + '[91m'  " + spaces);
			System.out.println((char)27 + "[92m(char)27 + '[92m'  " + spaces);
			System.out.println((char)27 + "[93m(char)27 + '[93m'  " + spaces);
			System.out.println((char)27 + "[94m(char)27 + '[94m'  " + spaces);
			System.out.println((char)27 + "[95m(char)27 + '[95m'  " + spaces);
			System.out.println((char)27 + "[96m(char)27 + '[96m'  " + spaces);
			System.out.println((char)27 + "[97m(char)27 + '[97m'  " + spaces);
			
			System.out.print((char)27 + "[0mPress Enter to continue...");
		}
		
		try
        {
            if (System.getProperty("os.name").contains("Windows"))
            {
            	Runtime.getRuntime().exec((char)44 + "mode 80;25" + (char)44);
            }
            else
            {
            	String cmd = "\\e[8;25;80t";
				Runtime.getRuntime().exec(cmd);
            }		

        }
        catch (IOException ex) {}
		
		input.nextLine();
		
	}
	private static void GridDemo()
    {
        /// All user I/O handled by UI class
        /// DrawBoxOutline(string style, string part, string foreColor, string backColor,
        ///                string boxAlign = "left", int width = 0, bool midMargin = false)
        /// DrawBoxBody(string style, string text, string boxAlign, string foreColor, string backColor,
        ///             string textColor = "WHITE", string textBackColor = "BLACKBG",
        ///             string textAlign = "left", int width = 0)
        /// DrawGridOutline(string style, string part, List<int> columns, string foreColor, string backColor, bool midMargin = false)
        /// DrawGridBody(string style, string part, List<int> columns, string boxColor, string boxBackColor,
        ///              string textColor, string textBackColor, List< string > textLines, List<string> alignments)
        
        UI.Clear();
        List<Integer> columns = new ArrayList<Integer>(Arrays.asList( 10, 30, 20, 20 ));
        List<String> alignments = new ArrayList<String>(Arrays.asList( "left", "centre", "centre", "centre" ));
        // DrawBoxOutline(String style, String part, String foreColor, String backColor, String boxAlign, int width, Boolean midMargin)
        UI.DrawBoxOutline("s", "top", "yellow", "black", "left", 0, false); // draw top of single box and title
        UI.DrawBoxBody("s", "This is ~magenta~Dos~red~Excel!", "centre", "yellow", "black", "white", "black","centre", 0);
        // draw column headers
        List<String> textLines = new ArrayList<String>(Arrays.asList( "", "~green~Column A", "~green~Column B", "~green~Column C" ));
        UI.DrawGridOutline("s", "top", columns, "yellow", "black", true);
        UI.DrawGridBody("s", "body", columns, "yellow", "black", "green", "black", textLines, alignments);
        // draw main columns
        UI.DrawGridOutline("s", "mid", columns, "yellow", "black", false);
        alignments =  new ArrayList<String>(Arrays.asList( "right", "left", "centre", "right" ));
        textLines =  new ArrayList<String>(Arrays.asList( "~green~Row 1: ", "~white~Cell(1,1)", "~white~Cell(1,2)", "~white~Cell(1,3)" ));
        UI.DrawGridBody("s", "body", columns, "yellow", "black", "green", "black", textLines, alignments);
        UI.DrawGridOutline("s", "mid", columns, "yellow", "black", false);
        textLines = new ArrayList<String>(Arrays.asList( "~green~Row 2: ", "~grey~Cell(2,1)", "~grey~Cell(2,2)", "~grey~Cell(2,3)" ));
        UI.DrawGridBody("s", "body", columns, "yellow", "black", "green", "black", textLines, alignments);
        UI.DrawGridOutline("s", "mid", columns, "yellow", "black", false);
        textLines = new ArrayList<String>(Arrays.asList( "~green~Row 3: ", "~dgrey~Cell(3,1)", "~dgrey~Cell(3,2)", "~dgrey~Cell(3,3)" ));
        UI.DrawGridBody("s", "body", columns, "yellow", "black", "green", "black", textLines, alignments);
        UI.DrawGridOutline("s", "mid", columns, "yellow", "black", false);
        textLines = new ArrayList<String>(Arrays.asList( "~green~Row 4: ", "~dgrey~align=\"left=\"", "~dgrey~align=\"centre\"", "~dgrey~align=\"right\"" ));
        UI.DrawGridBody("s", "body", columns, "yellow", "black", "green", "black", textLines, alignments);
        UI.DrawGridOutline("s", "bottom", columns, "yellow", "black", false);
        UI.AddLines(5, 13, "WHITE", "BLACKBG");
        UI.DrawLine("d", "white", "black", 0, "left");
        UI.DisplayMessage("", true, false, "WHITE", "BLACKBG", 2000);
    }
	private static void LineDemo()
    {
        /// All user I/O handled by UI class
        /// DrawLine(string style, string foreColor, string backColor, int width = 0, string align = "left")
        int numLines = 0;
        UI.Clear();
        numLines += UI.DrawLine("s", "red", "blackbg", 5, "left");
        numLines += UI.DrawLine("s", "red", "blackbg", 10, "left");
        numLines += UI.DrawLine("s", "red", "black", 20, "left");
        numLines += UI.DrawLine("s", "red", "black", 40, "left");
        numLines += UI.DrawLine("s", "red", "black", 60, "left");
        numLines += UI.DrawLine("s", "red", "black", 0, "left");
        numLines += UI.DrawLine("s", "red", "black", 60, "right");
        numLines += UI.DrawLine("s", "red", "black", 40, "right");
        numLines += UI.DrawLine("s", "red", "black", 20, "right");
        numLines += UI.DrawLine("s", "red", "black", 10, "right");
        numLines += UI.DrawLine("s", "red", "black", 5, "right");
        numLines += UI.DrawLine("d", "white", "black", 0, "left");
        numLines += UI.DrawLine("d", "grey", "black", 0, "left");
        numLines += UI.DrawLine("d", "dGrey", "black", 0, "left");
        numLines += UI.DrawLine("d", "green", "black", 0, "left");
        numLines += UI.DrawLine("d", "black", "green", 0, "left");
        numLines += UI.DrawLine("d", "red", "white", 0, "left");
        numLines += UI.DrawLine("d", "blue", "black", 40, "centre");
        numLines += UI.DrawLine("d", "cyan", "black", 50, "centre");
        UI.AddLines(5, numLines, "WHITE", "BLACKBG");
        UI.DrawLine("d", "white", "black", 0, "left");
        UI.DisplayMessage("", true, false, "WHITE", "BLACKBG", 2000);
    }
	private static void MultiBoxDemo()
    {
        /// All user I/O handled by UI class
        /// DrawMultiBoxOutline(List<string> styles, string part, List<int> sizes, List<string> foreColors,
        ///                     List<string> backColors, int padding = 0)
        /// DrawMultiBoxBody(List<string> styles, List<int> sizes, List<string> foreColors, List<string> backColors,
        ///                  List<string> textLines, List<string> alignments, int padding = 0)
        
        UI.Clear();
        List<String> styles     = new ArrayList<String>(Arrays.asList( "d","s","d" ));
        List<Integer> boxSizes  = new ArrayList<Integer>(Arrays.asList( 15, 40, 25 ));
        List<String> foreColors = new ArrayList<String>(Arrays.asList( "red", "green", "blue" ));
        List<String> backColors = new ArrayList<String>(Arrays.asList( "black", "black", "grey" ));
        List<String> textLines  = new ArrayList<String>(Arrays.asList( "~blue~blue 14 chars", "~red~red text 40 chars", "~dyellow~dark yellow 25 chars" ));
        List<String> emptyLines = new ArrayList<String>(Arrays.asList( "", "", "" ));
        List<String> alignments = new ArrayList<String>(Arrays.asList( "left", "centre", "right" ));
        int padding = 0;
        UI.DrawMultiBoxOutline(styles, "top", boxSizes , foreColors, backColors, padding);
        UI.DrawMultiBoxBody   (styles, boxSizes, foreColors, backColors, textLines, alignments, padding);
        UI.DrawMultiBoxOutline(styles, "mid", boxSizes, foreColors, backColors, padding);
        UI.DrawMultiBoxBody   (styles, boxSizes, foreColors, backColors, emptyLines, alignments, padding);
        UI.DrawMultiBoxBody   (styles, boxSizes, foreColors, backColors, emptyLines, alignments, padding);
        textLines = new ArrayList<String>(Arrays.asList( "~yellow~align=\"left\"", "~yellow~align=\"centre\"", "~red~align=\"right\"" ));
        UI.DrawMultiBoxBody   (styles, boxSizes, foreColors, backColors, textLines, alignments, padding);
        UI.DrawMultiBoxBody   (styles, boxSizes, foreColors, backColors, emptyLines, alignments, padding);
        UI.DrawMultiBoxBody   (styles, boxSizes, foreColors, backColors, emptyLines, alignments, padding);
        UI.DrawMultiBoxOutline(styles, "bottom", boxSizes, foreColors, backColors, padding);
        UI.AddLines(5, 9, "WHITE", "BLACKBG");
        UI.DrawLine("d", "white", "black", 0, "left");
        UI.DisplayMessage("", true, false, "WHITE", "BLACKBG", 2000);
    }
	private static void PrintDemo()
    {
        /// All user I/O handled by UI class
        /// ColorPrint(string value, bool newline = true, bool reset = true)
        
        UI.Clear();
        UI.ColorPrint("1. UI.Clear();", true, true);
        UI.ColorPrint("2. UI.Print(~yellow~~blackbg~\"This demo uses 10 lines of code\");", true, true);
        UI.ColorPrint("3. UI.Print(~green~\"This line is green on black\");", true, true);
        UI.ColorPrint("4. UI.AddLines(5, 7);", true, true);
        UI.ColorPrint("5. UI.DrawLine(\"d\", \"white\", \"black\");", true, true);
        UI.ColorPrint("6. UI.DisplayMessage(\"\", true, false);", true, true);
        UI.AddLines(5, 7, "WHITE", "BLACKBG");
        UI.DrawLine("d", "white", "black", 0, "left");
        UI.DisplayMessage("", true, false, "WHITE", "BLACKBG", 2000);
    }
	private static void PrintWithoutUI()
	{
		try
        {
            if (System.getProperty("os.name").contains("Windows"))
            {
            	Runtime.getRuntime().exec((char)44 + "color 0E" + (char)44); // back, fore 0E = yellow on black hard coded
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else
            {
            	String bc = (char)44 + "\\e[40m" + (char)44;
    			Runtime.getRuntime().exec(bc);
    			Runtime.getRuntime().exec("\\e[1m");
    			String fc = (char)44 + "\\e[93m" + (char)44;
    			Runtime.getRuntime().exec(fc);
    			Runtime.getRuntime().exec("clear");
            }
        }
        catch (IOException | InterruptedException ex) {}
		System.out.println("1.  if (System.getProperty(\"os.name\").contains(\"Windows\")){");
		System.out.println("2.     Runtime.getRuntime().exec((char)44 + \"color 0E\" + (char)44);");
		System.out.println("3.     new ProcessBuilder(\"cmd\", \"/c\", \"cls\").inheritIO().start().waitFor();");
		System.out.println("4.  else");
		System.out.println("5.     String bc = (char)44 + \"\\\\e[40m\" + (char)44;");
		System.out.println("6.     Runtime.getRuntime().exec(bc);");
		System.out.println("7.     Runtime.getRuntime().exec(\"\\\\e[1m\");");
		System.out.println("8.     String fc = (char)44 + \"\\\\e[93m\" + (char)44;");
		System.out.println("9.     Runtime.getRuntime().exec(fc);");
		System.out.println("10.    Runtime.getRuntime().exec(\"clear\");");
		System.out.println("11. if(System.console() == null)");
		if(System.console() == null)
		{
			System.out.println("12.    (char)27 + [92m + (char)27 + [40mThis line could be green on black + (char)27 + [0m");
		}
		else
		{
			System.out.println((char)27 + "[92m" + (char)27 + "[40m12.    This line is green on black" + (char)27 + "[0m");
		}
		for(int i = 0; i < 3; i++)
		{
			System.out.println();
		}
		System.out.println("13. for(int i = 0; i < 3; i++)");
		System.out.println("14.     System.out.println();");
		System.out.println("15. System.out.println(\"═\".repeat(79));");
		System.out.println("16. System.out.print(\"Press Enter to continue\");");
		System.out.println("17. input.nextLine();");
		System.out.println("═".repeat(79));
		System.out.print("Press Enter to continue");
		input.nextLine();
	}
	private static void DisplayMessageDemo()
    {
        /// All user I/O handled by UI class
        /// DisplayMessage(string message, bool useInput, bool useTimer,
        ///                string foreColor = "WHITE", string backColor = "BLACKBG", int delay = 2000)
        /// +1 overload  DisplayMessage(List<string> messages ...
        UI.Clear();
        UI.DisplayMessage("This shows a message, timed for 2 secs", false, true, "cyan", "dgrey", 2000);
        UI.DisplayMessage("Same method, but requiring Enter to continue", true, false, "green","black", 2000);
    }
	private static String GetInputDemo(String demoType, String description)
    {
        /// Most UI operations should be done within this UI class  ///
        /// Try to keep all I/O operations out of other classes     ///
        /// This will make transfer of code to a GUI much easier    ///
        String userInput = "";
        UI.Clear();
        int numLines = UI.DrawMultiLineBox("s", description, "yellow", "black", "white", "black", "left", "left", 0);
        numLines += UI.AddLines(5, numLines, "white", "black"); // pad Console to leave 5 empty lines
        numLines += UI.DrawLine("d", "white", "black", 0, "left"); // now leaves 4 empty lines
        switch (demoType)
        {
            case "string":
                userInput = UI.GetString(numLines, "UI.GetString: Type your name (1-10 chars)", ">_", "green", "black", true, 1, 10);
                break;
            case "int":
                userInput = Integer.toString(UI.GetInteger(numLines, "UI.GetInteger: Type your age (5-100)", ">_", "cyan", "black", 5, 100));
                break;

            case "real":
                userInput = Double.toString(UI.GetRealNumber(numLines, "UI.GetRealNumber: Type your height in metres (0.5 to 2.0)", ">_", "magenta", "black", 0.5, 2));
                break;

            case "bool":
                userInput = UI.GetBoolean(numLines, "UI.GetBoolean: Is this library useful (y/n)?", ">_", "blue", "black").toString();
                break;
        }
        return userInput;
    }
	private static void InputBoxDemo()
    {
        /// All user I/O handled by UI class
        /// InputBox(String style, String returnType, String title, String boxMessage, String inputPrompt, String promptEnd, String foreColor, 
		/// 		  String backColor, int width, double minReturnLen, double maxReturnLen, Boolean withTitle)
        UI.Clear();
        String userInput = UI.InputBox("s", "string", "Input Box Demo",
                                        "~white~What is your opinion of this Input Box demonstration",
                                        "Type your comment", ">_", "red","black", 60, 1, 50, false);
        UI.ColorPrint("Your opinion was:\n" + userInput, true, true);
        UI.DisplayMessage("", true, false, "WHITE", "BLACKBG", 2000);
    }
	private static void UserInputDemo()
    {
        /// All user I/O handled by UI class
        /// UI.GetInputDemo specifically written for this procedure. This is how the library was designed
        /// to be used. Do the logic in Program.cs or similar, pass all the UI stuff to UI.cs
        
        String description = "The user input methods are:\n\n" +
                            "~green~UI.GetString ~white~to get a string typed by the user. " +
                            "There is an option to set minimum and maximum length," +
                            " and to convert to TitleCase.\n\n" +
                            "~cyan~UI.GetInteger ~white~to get an integer value. Minimum and max values can be specified.\n\n" +
                            "~magenta~UI. GetRealNumber ~white~similar to UI.GetInteger, allows for real numbers to be entered.\n\n" +
                            "~blue~UI.GetBoolean ~white~requires the user to type 'y' or 'n' and returns a boolean value.\n\n"+
                            "~green~Test ~white~each one out with ~blue~Enter ~white~only, or wrong numbers, too many characters etc.\n\n" +
                            "~red~Try and break it!";
        String retValue = GetInputDemo("string", description);
        UI.DisplayMessage("You entered " + retValue, false, true, "WHITE", "BLACKBG", 2000);
        retValue = GetInputDemo("int", description);
        UI.DisplayMessage("You entered " + retValue, false, true, "WHITE", "BLACKBG", 2000);
        retValue = GetInputDemo("real", description);
        UI.DisplayMessage("You entered " + retValue, false, true, "WHITE", "BLACKBG", 2000);
        retValue = GetInputDemo("bool", description);
        UI.DisplayMessage("You entered " + retValue + ". Enter to continue", true, false, "WHITE", "BLACKBG", 2000);
    }
}
