package inksaver;

import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

//import com.sun.tools.javac.util.StringUtils;

final class ColorTag
{
	/// use:
	/// ColorTag colorTag = new ColorTag(textInput, sep);
	/// String output = colorTag.output;
	/// int colorTagSpaces = colorTag.colorTagSpaces;
	/// List<String> embeddedColors = colorTag.colorTagList;
	public String output = "";
    public int colorTagSpaces;
    public List<String> colorTagList;
    public ColorTag(String input, String sep)
    {
    	colorTagSpaces = FormatColorTags(input, sep);
    }
    private int FormatColorTags(String text, String sep)
    {
        /// Checks supplied string has matching tags to define colour strings
        /// If string is split on ~ alone, no record of whether this was a colour tag
        colorTagList = new ArrayList<String>();
        // use Linq to check if even numbers of tags
        long sepCount = text.chars().filter(ch -> ch == sep.charAt(0)).count(); 	// account for embedded ~ or equivalent characters
        if (sepCount % 2 == 1) throw new RuntimeException("The supplied text "+ text +" does not have matching colour separators: " + sep);
        text = text.replace(sep, "¶" + sep + "¶");
        List<String> data = UI.SplitClean(text, "¶"); //"~red~some text~green~more text" -> "¶~¶red¶~¶some text¶~¶green¶~¶more text"
                                                   // {"~", "red", "~", "some text", "~","green", "~", "more text"}
        int colourTagSpaces = 0;
        int startTag = -1;
        int endTag = -1;
        int colorData = -1;
        for (int i = 0; i < data.size(); i++)
        {
            if (data.get(i).equals(sep)) // found ~
            {
                if (startTag == -1)
                {
                    startTag = i;
                    colorData = i + 1;
                    endTag = i + 2;
                }
                else if (i == endTag) //marked already, next data is normal text
                {
                    colorData = -1;
                }
                else // i not -1 and not endTag
                {
                    startTag = i;
                    colorData = i + 1;
                    endTag = i + 2;
                }
            }
            else
            {
                if (i == colorData)
                {
                    // convert "~red~" to "~RED~"
                    String textColour = data.get(i).toUpperCase();
                    if (UI.colors.get(textColour) != null)
                    {
                        data.set(i, textColour);
                        colourTagSpaces += data.get(i).length();
                        colorTagList.add(sep + textColour + sep);
                    }
                    colorData = -1;
                }
            }
        }
        // re-combine textLines

        for(String line : data)
        {
            //if (line != sep) output += line;
        	if (line.equals(sep)) colourTagSpaces ++;
        	output += line;
        }
        return colourTagSpaces;
    }
}
final class TryParse
{
	/// use:
	/// TryParse tryParse = new TryParse(text);
	/// if(tryParse.isEmpty) 	// test for empty string
	/// if(tryParse.success) 	// test for conversion of some sort
	/// if(tryParse.isBool) 	output = tryParse. bOut	// test for bool
	/// if(tryParse.isInt)		output = tryParse. iOut	// test for int
	///	if(tryParse.isDouble) 	output = tryParse. dOut	// test for real number

	public Boolean success = false;
	public int 		iOut = 0;
	public double 	dOut = 0;
	public Boolean 	bOut = false;
	public String 	sOut = "";
	public Boolean 	isBool = false;
	public Boolean 	isInt = false;
	public Boolean 	isDouble = false;
	public Boolean 	isEmpty = false;

	public TryParse(String input)
	{
		success = Tryparse(input);
	}
	private Boolean Tryparse(String input)
    {
    	Boolean retValue = false;
    	if (input.length() > 0) // ? empty string 
    	{
	    	try
	    	{
	    		bOut = Boolean.parseBoolean(input); // see if user entered true/false
	    		isBool = true;
	    		retValue = true;
		    }
	    	catch (RuntimeException ex) {}
	    	{
	    		isBool = false;
	    		retValue = false;
	    		try
	    		{
	    			iOut = Integer.parseInt(input); // see if user entered integer
	    			isInt = true;
	    			retValue = true;
	    		}
	    		catch (RuntimeException ex) {}
	    		{
	    			isInt = false;
	    			retValue = false;
	    			try
	    			{
	    				dOut = Double.parseDouble(input); // see if user entered real number
	    				isDouble = true;
	    				retValue = true;
	    			}
	    			catch (RuntimeException ex) {} // not bool/int/double must be string or empty string
	    			{
	    				sOut = input;
	    			}
	    		}
	    	}
    	}
    	else
    	{
    		isEmpty = true;
    	}
    	
    	return retValue;
    }
}
public class UI
{	
	private static Boolean isInitialised = false;
	private static Scanner input = new Scanner(System.in);  // Create a Scanner object
	public static Map<String, String> colors = new HashMap<String, String>();
	public static String sep = "~"; // change this value to any other char that you will NOT be using in any strings supplied to ColorPrint()
	private static final String escape 		= (char)27 + "[";
	private static final String BLACK 		= escape + "30m";
	private static final String WHITE 		= escape + "97m";
	private static final String GREY 		= escape + "37m";
	private static final String GRAY 		= escape + "37m";
	private static final String DGREY 		= escape + "90m";
	private static final String DGRAY 		= escape + "90m";
	private static final String BLUE 		= escape + "94m";
	private static final String GREEN 		= escape + "92m";
	private static final String CYAN 		= escape + "96m";
	private static final String RED 		= escape + "91m";
	private static final String MAGENTA 	= escape + "95m";
	private static final String YELLOW 		= escape + "93m";
	private static final String DBLUE 		= escape + "34m";
	private static final String DGREEN 		= escape + "32m";
	private static final String DCYAN 		= escape + "36m";
	private static final String DRED 		= escape + "31m";
	private static final String DMAGENTA 	= escape + "35m";
	private static final String DYELLOW 	= escape + "33m";
	
	private static final String BLACKBG 	= escape + "40m";
	private static final String WHITEBG 	= escape + "107m";
	private static final String GREYBG 		= escape + "47m";
	private static final String GRAYBG 		= escape + "47m";
	private static final String DGREYBG 	= escape + "100m";
	private static final String DGRAYBG 	= escape + "100m";
	private static final String BLUEBG 		= escape + "104m";
	private static final String GREENBG 	= escape + "102m";
	private static final String CYANBG 		= escape + "106m";
	private static final String REDBG 		= escape + "101m";
	private static final String MAGENTABG 	= escape + "105m";
	private static final String YELLOWBG 	= escape + "103m";
	private static final String DBLUEBG 	= escape + "44m";
	private static final String DGREENBG 	= escape + "42m";
	private static final String DCYANBG 	= escape + "46m";
	private static final String DREDBG 		= escape + "41m";
	private static final String DMAGENTABG 	= escape + "45m";
	private static final String DYELLOWBG 	= escape + "43m";
	
	private static final String RESET 		= escape + "0m";
	private static final String BRIGHT 		= escape + "1m";
	private static final String DIM 		= escape + "2m";
	private static final String UNDERLINE 	= escape + "4m";
	private static final String BLINK 		= escape + "5m";
	private static final String REVERSE 	= escape + "7m";
	private static final String HIDDEN 		= escape + "8m";
	
	private static Boolean isConsole 		= false; 		// assume running on a console/terminal until checked in initialise()
	private static Boolean isColoured 		= false; 	// assume not colour capable until checked in initialise()
	public static int windowWidth 			= 80;
    public static int windowHeight 			= 25;
    /* Character set used in old DOS programs to draw lines and boxes
    ┌───────┬───────┐   ╔═════╦═════╗ 
    │       │       │   ║     ║     ║
    ├───────┼───────┤   ╠═════╬═════╣ 
    │       │       │   ║     ║     ║
    └───────┴───────┘   ╚═════╩═════╝
   */
    private static List<String> constColors 	= new ArrayList<>();
    
    private static List<String> sSymbolsTop 	= new ArrayList<>(Arrays.asList( "┌", "─", "┐", "┬" ));
    private static List<String> sSymbolsBottom 	= new ArrayList<>(Arrays.asList( "└", "─", "┘", "┴" ));
    private static List<String> sSymbolsBody 	= new ArrayList<>(Arrays.asList(  "│", " ", "│", "│" ));
    private static List<String> sSymbolsMid 	= new ArrayList<>(Arrays.asList( "├", "─", "┤", "┼" ));
    
    private static List<String> dSymbolsTop 	= new ArrayList<>(Arrays.asList( "╔", "═", "╗", "╦" ));
    private static List<String> dSymbolsBottom 	= new ArrayList<>(Arrays.asList( "╚", "═", "╝", "╩" ));
    private static List<String> dSymbolsBody 	= new ArrayList<>(Arrays.asList( "║", " ", "║", "║" ));
    private static List<String> dSymbolsMid 	= new ArrayList<>(Arrays.asList( "╠", "═", "╣", "╬" ));
    
    public static int AddLines(int numLines, String foreColor, String backColor) //default 25 lines
    {
        /// Adds numLines blank lines in specified or default colour ///
    	String[] check = ValidateColors(foreColor, backColor);
        foreColor = check[0];
        backColor = check[1];
        String blank = PadRight("", windowWidth, " "); //string of spaces across entire width of Console
        if (numLines > 0)
        {
            for (int i = 0; i < numLines; i++)
            {
                ColorPrint(foreColor + backColor + blank, true, true);
            }
        }
        return numLines;
    }
    public static int AddLines(int leaveLines, int currentLines, String foreColor, String backColor) 
    {
        /// overload 1: Adds blank lines until windowHeight - leaveLines in specified or default colour ///
    	String[] check = ValidateColors(foreColor, backColor);
        foreColor = check[0];
        backColor = check[1];
        String blank = PadRight("", windowWidth, " "); //string of spaces across entire width of Console
        int numLines = windowHeight - currentLines - leaveLines;
        if (numLines > 0)
        {
            for (int i = 0; i < numLines; i++)
            {
                ColorPrint(foreColor + backColor + blank, true, true);
            }
        }
        return numLines;
    }
    public static void ChangeSeparator(String value)
    {
        /// Allows use of different character to separate colour tags. Default is ~ ///
        sep = value.substring(0, 1);    // if user supplies more than 1 character, use the first only. Stored as string
        Initialise(true);               //re-initialise to make use of new character
    }
    public static void Clear()
    {
    	//Clear console / terminal
    	if (isConsole)
    	{
	        try
	        {
	            if (System.getProperty("os.name").contains("Windows"))
	                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
	            else
	                Runtime.getRuntime().exec("clear");
	        }
	        catch (IOException | InterruptedException ex) {}
    	}
    	else
    	{
    		AddLines(windowHeight, WHITE, BLACKBG);
    	}
    }
    public static int ColorPrint(String value, Boolean newline, Boolean reset)
    {
        ///  This uses the default char ~ to separate colour strings                ///
        ///  change the line:  static string sep = "~"; as required                 ///
        ///  other possibles are ` (backtick) ¬ (?) any character you will not use  ///
        //  example value = "~RED~This is a line of red text"                      
        int numLines = 0;
        numLines += value.chars().filter(ch -> ch == '\n').count(); 	// account for embedded newline characters
        // SplitClean returns a List<string> and removes empty items
        if(value.indexOf(sep) >= 0) // embedded colours found
        {
	        List<String> lineParts = SplitClean(value, sep); // = {'RED', 'This is a line of red text'} 
	        for (String part : lineParts)
	        {
	        	if (colors.get(part.toUpperCase()) != null) // is 'RED' in the colors dictionary?
	            {
	                if(isColoured)
	                	System.out.print(colors.get(part.toUpperCase()));
	            }
	            else // not a colour command so print it out without newline
	            {
	                System.out.print(part);
	            }
	        }
        }
        else if (value.indexOf((char)27) >= 0) //string is in form of "Hello..RED..World"
        {
        	if (!isColoured || !isConsole)
        	{
        		value = RemoveANSI(value);	
        	}
        	System.out.print(value);
        }
        else // no colour tags in the text
        {
        	System.out.print(value);
        }
        if (newline)
        {
        	System.out.print("\n"); // Add newline to complete the print command
            numLines++;
        }
        if (reset)
        {
        	if(isColoured)	System.out.print(RESET);
        }

        return numLines;
    }
    public static int DisplayMessage(String message, Boolean useInput, Boolean useTimer, String foreColor, String backColor, int delay) // string version
    {
        /// show user message or default, either wait for 2 secs, or ask user to press enter ///
    	long numLines = message.chars().filter(ch -> ch == '\n').count(); 	// account for embedded newline characters
        String[] check = ValidateColors(foreColor, backColor);
        foreColor = check[0];
        backColor = check[1];
        if (useTimer) //pause for delay mS
        {
            if (message != "")
            {
                ColorPrint(foreColor + backColor + message, true, true);
                Sleep(delay);  
            }
        }
        if (useInput) // wait for Enter key
        {
            if (message.equals("")) message = "Press Enter to continue";
            else message = foreColor + backColor + message;
            Input(message, "...", foreColor, backColor);
        }
        numLines += 1;
        return (int)numLines;
    }
    public static int DisplayMessage(List<String> messages, Boolean useInput, Boolean useTimer, String foreColor, String backColor, int delay) //list vesion
    {
        /// show user message or default, either wait for 2 secs, or ask user to press enter ///
    	String[] check = ValidateColors(foreColor, backColor);
        foreColor = check[0];
        backColor = check[1];
        int numLines = 0;
        for (String message : messages)
        {
        	numLines+= ColorPrint(foreColor + backColor + message, true, true); 
        }
        if (useTimer) Sleep(delay); //pause for delay mS
        if (useInput)
        {
            Input("Press Enter to continue", "...", WHITE, BLACK);
            numLines++;
        }
        return numLines;
    }
    private static void DisplaySetup(int cols, int rows, String foreColor, String backColor)
    {
    	int numLines = 0;
    	
    	if(isConsole)
    	{
    		isColoured = true;
    		Clear();
    		numLines += print("This program uses coloured text and formatted menus");
    		numLines += print("This can only be seen when run in terminal or IDEs with ansi console:");
    		numLines += print("(Example Visual Studio Code)");			
    		numLines += print("");
    		numLines += print("The terminal has been set to " + cols + " x " + rows + " with " + foreColor + " text on " + backColor + " background.");
    		if(GetOS().equals("nt"))
    		{
    			numLines += print("");
    			numLines += print("If the font size is too small:");
    			numLines += print("");
    			numLines += print("\t1) Right-click on the title bar and select 'Properties'");
    			numLines += print("\t2) Select the 'Font' tab");
    			numLines += print("\t3) Change the size to suit");
    			numLines += print("\t4) Click 'Close' (The window will resize)");
    			numLines += print("");
    		}
    		else
    		{
    			numLines += print("");
    			numLines += print("If the font size is too small:");
    			numLines += print("");
    			numLines += print("\t1) Menu -> Edit -> Preferences");
    			numLines += print("\t2) Select the 'Text' tab");
    			numLines += print("\t3) Change the 'Size' to suit");
    			numLines += print("\t4) Click 'OK' (The window will resize)");
    			numLines += print("\t5) Repeat steps 1 and 4 if you change window position");
    		}
    		numLines += print("");
    		numLines += ColorPrint("Do ~red~NOT~white~ resize this window as it will mess up the menus!", true, true);
    		numLines += DrawLine("d", RED, BLACKBG, 0, "left");
    		GetBoolean(numLines, "Is the line above red on a black background? (y/n)", ">_", "white", "blackbg");
    	}
    	else
    	{
    		numLines += print(" 1 This program uses coloured text and formatted menus\n 2");
    		numLines += print(" 3 This can only be seen when run in terminal or IDEs with ANSI console:");
    		numLines += print(" 4 (Visual Studio Code or Wing Personal with I/O set to external console)");			
    		numLines += print(" 5\n 6 Important!");
    		numLines += print(" 6\n 7 A line of characters has been drawn on line 24 below");
    		numLines += print(" 8 Resize the output window so you can see lines 1 to 25 only");
    		numLines += print(" 9 Follow the instruction on line 25");
    		for(int i = 10; i < 25;i++)
    		{
    			numLines += print(String.valueOf(i));
    		}
    		numLines += DrawLine("d", RED, BLACKBG, 0, "left");
    		Input("25 Enter to continue", ">_", WHITE, BLACKBG);
    	}
    	Clear();
    	if(isConsole)
    	{
    		if (!isColoured)
    		{
    			numLines += print("You are running from a non-compliant IDE\n");
    			numLines += print("Coloured text will NOT be displayed\n");
    			numLines += print("The game will still run!\n");
    			if(GetOS().equals("nt")) // Windows cmd
    			{
    				numLines += print("Try double-clicking 'Program.jar' in Windows Explorer");
    				numLines += print("Or running 'java -jar <program name>.jar' in cmd or powershell");
    			}
    			else
    			{
    				numLines += print("Try typing 'java -jar <program name>.jar' in terminal\n");
    			}
    			print("(make sure you are in the correct directory!)");
    			AddLines(14, WHITE, BLACKBG);
    			DrawLine("d", WHITE, BLACKBG, 0, "left"); //line 21
    			Input("Press Enter to continue", ">_", WHITE, BLACKBG);
    		}
    		Clear();
    	}
    }
    public static int DrawBoxBody(String style, String text, String boxAlign, String foreColor, String backColor, String textColor, String textBackColor, String textAlign, int width)
	{
		/// print out single line body section of a box with text and/or spaces: "║  text  ║"
		style = FixStyle(style);
		String[] check = ValidateColors(foreColor, backColor);
        foreColor = check[0];
        backColor = check[1];
        check = ValidateColors(textColor, textBackColor);
        textColor = check[0];
        textBackColor = check[1];
        boxAlign = ValidateAlignment(boxAlign);                            // check alignments are in permitted list
        textAlign = ValidateAlignment(textAlign);
		List<String> s = SelectCharacterList("body", style);        // { "║", " ", "║", "║" };
		if (width > windowWidth || width <= 0) width = windowWidth;
		String output = PadString(text, width - 2, " ", textAlign);  // "  text  " -2 to allow for start/end margin chars
		String[] sides = {s.get(0), s.get(2) };
		sides = PadBoxSides(sides, output, boxAlign);
		// foreColor border on backColor -> colour formatted text -> foreColor border on backColor
		ColorPrint(foreColor + backColor + sides[0] + textColor + textBackColor + output + foreColor + backColor + sides[1], true, true);
		
		return 1; // single line used
	}
    public static int DrawBoxOutline(String style, String part, String foreColor, String backColor, String boxAlign, int width, Boolean midMargin)
    {
        /// Draw the top, mid or bottom of a box to width ///
        style = FixStyle(style);
        String[] check = ValidateColors(foreColor, backColor);
        foreColor = check[0];
        backColor = check[1];
        boxAlign = ValidateAlignment(boxAlign);
        part = ValidateBoxPart(part);
        List<String> s = SelectCharacterList(part, style);
        if (width > windowWidth || width <= 0) width = windowWidth;
        String[] sides = {s.get(0), s.get(2) };
        if (midMargin)
        {
            sides[0] = SelectCharacterList("mid", style).get(0);        // "├"
            sides[1] = SelectCharacterList("mid", style).get(2);        // "┤"
        }
        String output = PadString("", width - 2, s.get(1), boxAlign);   // -2 to allow for start/end margin chars
        sides = PadBoxSides(sides, output, boxAlign);
        // line padded, now do same for spaces around the line
        output = foreColor + backColor + sides[0] + output + foreColor + backColor + sides[1];
        ColorPrint(output, true, true);

        return 1; // single line used
    }
    public static int DrawMultiBoxBody(List<String> styles, List<Integer> sizes, List<String> foreColors, List<String> backColors, List<String> textLines, List<String> alignments, int padding)
    {
        /// print out single line mid section of multiple boxes with or without text
        /// ║  box 0  ║ ║   box 1   ║ │  box 2  │
        if (styles.size() != sizes.size() && styles.size() != foreColors.size() && styles.size() != backColors.size() && styles.size() != alignments.size())
        {
            throw new RuntimeException("All supplied parameter lists must have the same number of items");
        }
        for (int i = 0; i < styles.size(); i++)
        {
            styles.set(1, FixStyle(styles.get(i)));
        }
        if (foreColors.size() != backColors.size()) throw new RuntimeException("DrawMultiBoxOutline: Number of foreColours must equal number of backColours");
        for (int i = 0; i < foreColors.size(); i++) //validate supplied colours
        {
            String foreColor = foreColors.get(i);
            String backColor = backColors.get(i);
            String[] check = ValidateColors(foreColor, backColor);
            foreColors.set(i, check[0]);
            backColors.set(i, check[1]);
        }
        for (int i = 0; i < alignments.size(); i++)
        {
            alignments.set(1, ValidateAlignment(alignments.get(i)));
        }
        List<String> s;
        List<String> outputs = new ArrayList<String>();
        int boxLength;
        String output;
        for (int i = 0; i < sizes.size(); i++)
        {
            // size examples {15, 40, 25} = 80 cols
            s = SelectCharacterList("body", styles.get(i));
            if (i < sizes.size() - 1) boxLength = sizes.get(i) - padding - 2;   // -2 as is box edge, and characters will be added both sides
            else boxLength = sizes.get(i) - 2;
            String lSide = s.get(0);                                            // start with left side char
            String rSide = s.get(3);                                            // end with right side char
            //check length of String
            output = textLines.get(i);
            ColorTag colorTag = new ColorTag(output, sep);
        	output = colorTag.output;
        	int colorTagSpaces = colorTag.colorTagSpaces;
        	/// List<String> embeddedColors = colorTag.colorTagList;
            if (output.length() > sizes.get(i) - 2 + colorTagSpaces) output = output.substring(0, sizes.get(i) - 2 + colorTagSpaces);
            output = PadString(output, boxLength, " ", alignments.get(i));
            output = foreColors.get(i) + backColors.get(i) + lSide + output + foreColors.get(i) + backColors.get(i) + rSide;
            outputs.add(output);                                            	// create new list item with completed String
        }
        output = "";
        for (String line : outputs)                                    			// concatenate all box outlines in outputs
        {
            output += line;
        }
        ColorPrint(output, true, true);

        return 1; // single line used
    }
    public static int DrawMultiBoxOutline(List<String> styles, String part, List<Integer> sizes, List<String> foreColors, List<String> backColors, int padding)
    {
        /// Draw the top/bottoms of sizes.Count boxes, width in absolute values
        /// ╔══════════╗ ┌────────┐ ╔════════════════════╗ 
        if (styles.size() != sizes.size() && styles.size() != foreColors.size() && styles.size() != backColors.size())
        {
            throw new RuntimeException("All supplied parameter lists must have the same number of items");
        }
        for (int i = 0; i < styles.size(); i++)
        {
            styles.set(i, FixStyle(styles.get(i)));
        }
        for (int i = 0; i < foreColors.size(); i++)
        {
            String foreColor = foreColors.get(i);
            String backColor = backColors.get(i);
            String[] check = ValidateColors(foreColor, backColor);
            foreColors.set(i, check[0]);
            backColors.set(i, check[1]);
        }
        List<String> s;
        List<String> outputs = new ArrayList<String>();
        //int numBoxes;
        //int width;
        int boxLength;
        String output;
        for (int i = 0; i < sizes.size(); i++)
        {
            // size examples {15, 40, 25} = 80 cols
            s = SelectCharacterList(part, styles.get(i));
            if (i < sizes.size() - 1) boxLength = sizes.get(i) - padding - 2;     // -2 as is box edge, and characters will be added both sides
            else boxLength = sizes.get(i) - 2;
            String lSide = s.get(0);                                                // start with left corner
            String rSide = s.get(2);                                                // end with right corner
            output = PadString("", boxLength, s.get(1), "left");
            output = foreColors.get(i) + backColors.get(i) + lSide + output + foreColors.get(i) + backColors.get(i) + rSide;
            outputs.add(output);        // create new list item with completed String
        }

        output = "";
        for (String line : outputs) // concatenate all box outlines in outputs
        {
            output += line;
        }
        ColorPrint(output, true, true);

        return 1; //single line
    }
    public static int DrawGridBody( String style, String part, List<Integer> columns, String boxColor, String boxBackColor,
            						String textColor, String textBackColor, List<String> textLines, List<String> alignments)
	{
		/// Draw the body of a grid to width
		/// ║  box 0  ║    box 1   ║         box 2        ║
		style = FixStyle(style);
		String[] check = ValidateColors(boxColor, boxBackColor);
        boxColor = check[0];
        boxBackColor = check[1];
        check = ValidateColors(textColor, textBackColor);
        textColor = check[0];
        textBackColor = check[1];
		List<String> s = SelectCharacterList(part, style);              //  "│", " ", "│", "│"
		List<String> outputs = new ArrayList<String>();
		String output;
		for (int col = 0; col < columns.size(); col++)
		{
			int colWidth = columns.get(col);                                // eg {10, 20, 20, 30 } = 80 cols
			String lSide;
			String rSide;
			if (col == 0)
			{
				lSide = s.get(0);                                           // "│"
				rSide = s.get(3);                                           // "│"
			}
			else if (col == columns.size() - 1)
			{
				lSide = s.get(1);                                           // "│"
				rSide = s.get(2);                                           // "│"
			}
			else
			{
				lSide = s.get(1);                                           // " "
				rSide = s.get(3);                                           // "│"
			}
			String text = "";
			if (col < textLines.size()) text = textLines.get(col);
			output = PadString(text, colWidth - 2, " ", alignments.get(col));          // -2 to allow for start/end margin chars
			output = boxColor + boxBackColor + lSide + textColor + textBackColor + output + boxColor + boxBackColor + rSide;
			outputs.add(output);                                        // create new list item with completed String
		}
		output = "";
		for (String line : outputs) // concatenate all box outlines in outputs
		{
			output += line;
		}
		ColorPrint(output, true, true);
		
		return 1; // single line used
	}
    public static int DrawGridOutline(String style, String part, List<Integer> columns, String foreColor, String backColor, Boolean midMargin)
    {
        /// Draw the top, mid or bottom of a grid to width 
        /// ┌───────────┬───────────┬───────────┬───────────┬──────────┐
        style = FixStyle(style);
        String[] check = ValidateColors(foreColor, backColor);
        foreColor = check[0];
        backColor = check[1];
        List<String> s = SelectCharacterList(part, style);              			// "┌", "─", "┐", "┬"
        List<String> outputs = new ArrayList<String>();
        String output;
        for (int col = 0; col < columns.size(); col++)
        {
            int colWidth = columns.get(col);                            			// eg {10, 20, 20, 30 } = 80 cols
            String lSide;                                               			// "┌"
            String rSide;                                               			// "┐"
            if (col == 0)
            {
                lSide = s.get(0);                                           		// "┌"
                if (midMargin) lSide = SelectCharacterList("mid", style).get(0); 	// "├"
                rSide = s.get(3);                                           		// "┬"
            }
            else if (col == columns.size() - 1)
            {
                lSide = s.get(1);                                           		// "─"
                rSide = s.get(2);                                           		// "┐"
                if (midMargin) rSide = SelectCharacterList("mid", style).get(2); 	// "├"
            }
            else
            {
                lSide = s.get(1);                                           		// "─"
                rSide = s.get(3);                                           		// "┬"
            }
            output = PadString("", colWidth - 2, s.get(1), "left");         		// -2 to allow for start/end margin chars
            output = foreColor + backColor + lSide + output + rSide;
            outputs.add(output);                                        			// create new list item with completed String
        }
        output = "";
        for (String line : outputs)                                					// concatenate all box outlines in outputs
        {
            output += line;
        }
        ColorPrint(output, true, true);

        return 1; // single line used
    } 
    public static int DrawLine(String style, String foreColor, String backColor, int width, String align)
    {
        ///  Draw a single or double line across the console with fore and back colours set  ///
        style = FixStyle(style);
        String[] check = ValidateColors(foreColor, backColor);
        foreColor = check[0];
        backColor = check[1];
        align = ValidateAlignment(align);
        // width has to be 1 less than windowWidth or spills over to next line: Odd behaviour of C# Console?
        if (width > windowWidth || width <= 0) width = windowWidth;
        List<String> s = SelectCharacterList("mid", style);
        String output;
        if (width == windowWidth)
        {
            output = PadString("", width, s.get(1), "left");        // -2 to allow for start/end margin chars
            output = foreColor + backColor + output;           		//eg "~WHITE~~BLACKBG~════════════════════"
        }
        else
        {
            output = PadString("", width, s.get(1), "left");
            output = PadString(output, windowWidth, " ", align);
            output = foreColor + backColor + output;           		//eg "~WHITE~~BLACKBG~════════════════════"
        }
        ColorPrint(output, true, true);

        return 1; // single line used
    }
    public static int DrawMultiLineBox(String style, String text, String foreColor, String backColor, String textColor, String textBackColor, String boxAlign, String textAlign, int width)
    {
        /// Draw a single box containing many lines of text
        /// Convert the supplied string 'text' into a list and pass to same function with over-ride
        List<String> textLines = SplitClean(text, "\n");
        return DrawMultiLineBox(style, textLines, foreColor, backColor, textColor, textBackColor, boxAlign, textAlign, width);
    }
    public static int DrawMultiLineBox(String style, List<String> textLines, String foreColor, String backColor, String textColor, String textBackColor, String boxAlign, String textAlign, int width)
    {
        ///  Draw a single box containing many lines of text, split at whole words
        int numLines = 0;
        style = FixStyle(style);
        String[] check = ValidateColors(foreColor, backColor);
        foreColor = check[0];
        backColor = check[1];
        if (width > windowWidth || width <= 0) width = windowWidth;
        numLines += DrawBoxOutline(style, "top", foreColor, backColor, boxAlign, width, false);
        textLines = GetFormattedLines(textLines, width, false);
        for (String line : textLines)
        {
            numLines += DrawBoxBody(style, line, boxAlign, foreColor, backColor, textColor, textBackColor, textAlign, width);
        }
        numLines += DrawBoxOutline(style, "bottom", foreColor, backColor, boxAlign, width, false);

        return numLines;
    }
    private static String FixStyle(String style)
    {
        /// format given style to either "s" or "d"
        style = style.trim().toLowerCase();
        if (style != "s" && style != "d") style = "s";
        return style;
    }
    public static Boolean GetBoolean(int row, String prompt, String promptChar, String textColor, String backColor)
    {
        /// gets a boolean (yes/no) type entry from the user
        String userInput = ProcessInput(row, prompt, promptChar, textColor, backColor, 1, 3, "bool");
        return Boolean.parseBoolean(userInput);
    }
    public static List<String> GetFormattedLines(String text, int maxLength, Boolean noBorder)
    {
        /// format single text string into  fixed length lines ///

        /*example input string: (could contain \n characters, or continuous line)
        "This is the first line\n\nThis is the third line (follows blank due to \\n\\n).\nThis is the fourth line which is very long and therefore exceeds the standard console
        or terminal width of eighty characters" */

        List<String> textLines = SplitClean(text, "\n");
        /* textLines = {"This is the first line", "", "This is the third line (follows blank due to \\n\\n)",
           "This is the fourth line which is very long and therefore exceeds the standard console or terminal width of eighty characters"} */
        return GetFormattedLines(textLines, maxLength, noBorder);
    }
    public static List<String> GetFormattedLines(List<String> textLines, int maxLength, Boolean noBorder)
    {
        ///takes a list of lines , checks length of each and adds additional lines if required, returns list
        // check length of each line. max = windowWidth
        if (maxLength > windowWidth || maxLength <= 0) maxLength = windowWidth;
        if (!noBorder) maxLength -= 2; //default  80-2 = 78 chars  
        List<String> newLines = new ArrayList<String>();
        for (int index = 0; index < textLines.size(); index++) 
        {
            String line = textLines.get(index).trim();                  // remove leading/trailing spaces
            int colorTagSpaces = 0;
            // now check the length of each line and cut into shorter sections if required
            if (line.length() == 0) newLines.add(line);               // add empty
            else
            {
                while (line.length() >= maxLength + colorTagSpaces)   // line length > 77 in 80 col terminal
                {
                    //String text = GetMaxLengthString(line, maxLength, out colorTagSpaces);
                    String[] data = GetMaxLengthString(line, maxLength);
                    String text = data[0];
                    colorTagSpaces = Integer.parseInt(data[1]);
                    newLines.add(text);
                    line = line.substring(text.length()).trim();
                }
                if (line.length() > 0) newLines.add(line);            // partial line left over from the while loop ( <maxLength) or short line
            }
        }
        return newLines;
    }
    private static String[] GetMaxLengthString(String text, int maxLength)
    {
    	//int colorTagSpacesCount = FormatColorTags(ref text, out List<string> colorTagList);
        ColorTag colorTag = new ColorTag(text, sep);
    	text = colorTag.output;
    	int colorTagSpaces = colorTag.colorTagSpaces;
        if (text.length() > maxLength + colorTagSpaces)
        {
            text = text.substring(0, maxLength + colorTagSpaces);
            int ending = text.lastIndexOf(" ");
            text = text.substring(0, ending);
        }
        return new String[] {text, Integer.toString(colorTagSpaces)};
    }
    public static int GetInteger(int row, String prompt, String promptChar, String foreColor, String backColor, double min, double max)
    {
        /// Public Method: gets an integer from the user ///
        String userInput = ProcessInput(row, prompt, promptChar, foreColor, backColor, min, max, "int");
        return Integer.parseInt(userInput);
    }
    public static double GetRealNumber(int row, String prompt, String promptChar, String textColor, String backColor, double min, double max)
    {
        /// gets a float / double from the user
        String userInput = ProcessInput(row, prompt, promptChar, textColor, backColor, min, max, "real");
        return Double.parseDouble(userInput);
    }
    public static String GetString(int row, String prompt, String ending, String foreColor, String backColor, Boolean withTitle, double min, double max)
    {
        /// Public Method: gets a string from the user ///
        String userInput = ProcessInput(row, prompt, ending, foreColor, backColor, min, max, "string");
        if (withTitle)
        {
            userInput = toTitleCase(userInput);
        }
        return userInput;
    }
    private static String GetOS()
    {
        String osName = System.getProperty("os.name", "").toLowerCase();
        if (osName.startsWith("windows")) {
            return "nt";
        } else if (osName.startsWith("linux")) {
            return "posix";
        } else if (osName.startsWith("mac os") || osName.startsWith("macos") || osName.startsWith("darwin")) {
            return "mac";
        }
        return "";
    }
    public static void Initialise(Boolean reset)
    {
        isInitialised = true;
        colors.clear();
    	colors.put("BLACK", BLACK);
        colors.put("WHITE", WHITE);
        colors.put("GREY", GREY);
        colors.put("GRAY", GRAY);
        colors.put("DGREY", DGREY);
        colors.put("DGRAY", DGRAY);
        colors.put("BLUE", BLUE);
        colors.put("GREEN", GREEN);
        colors.put("CYAN", CYAN);
        colors.put("RED", RED);
        colors.put("MAGENTA", MAGENTA);
        colors.put("YELLOW", YELLOW);
        colors.put("DBLUE", DBLUE);
        colors.put("DGREEN", DGREEN);
        colors.put("DCYAN", DCYAN);
        colors.put("DRED", DRED);
        colors.put("DMAGENTA", DMAGENTA);
        colors.put("DYELLOW", DYELLOW);
        
        colors.put("BLACKBG", BLACKBG);
        colors.put("WHITEBG", WHITEBG);
        colors.put("GREYBG", GREYBG);
        colors.put("GRAYBG", GRAYBG);
        colors.put("DGREYBG", DGREYBG);
        colors.put("DGRAYBG", DGRAYBG);
        colors.put("BLUEBG", BLUEBG);
        colors.put("GREENBG", GREENBG);
        colors.put("CYANBG", CYANBG);
        colors.put("REDBG", REDBG);
        colors.put("MAGENTABG", MAGENTABG);
        colors.put("YELLOWBG", YELLOWBG);
        colors.put("DBLUEBG", DBLUEBG);
        colors.put("DGREENBG", DGREENBG);
        colors.put("DCYANBG", DCYANBG);
        colors.put("DREDBG", DREDBG);
        colors.put("DMAGENTABG", DMAGENTABG);
        colors.put("DYELLOWBG", DYELLOWBG);
        
        colors.put("RESET", RESET);
        colors.put("BRIGHT", BRIGHT);
        colors.put("DIM", DIM);
        colors.put("UNDERLINE", UNDERLINE);
        colors.put("BLINK", BLINK);
        colors.put("REVERSE", REVERSE);
        colors.put("HIDDEN", HIDDEN);
        
        // make a list of color constants: {'\27[30m','\27[97m'}
        for (Map.Entry<String, String> entry : colors.entrySet())
        {
            constColors.add(entry.getValue());
        }
        isConsole = false;
        isColoured = false;
        if(System.console() != null)
        {
        	isConsole = true;
        	isColoured = true;
        } 
    }
    public static String Input(String prompt, String ending, String foreColor, String backColor)
    {
        /// Get keyboard input from user (requires Enter )
        /// check if prompt contains embedded colours
    	ColorTag colorTag = new ColorTag(prompt, sep);
    	//int colourTagSpaces = colorTag.colorTagSpaces; // not used in this method
    	prompt = colorTag.output;
    	List<String> embeddedColors = colorTag.colorTagList;
        //FormatColorTags(ref prompt, out List<string> embeddedColors); //extract any colour info from prompt eg ~red~
        for(String color : embeddedColors)
        {
            if (color.contains("BG")) backColor = color;
            else foreColor = color;
        }
        String[] check = ValidateColors(foreColor, backColor);
        foreColor = check[0];
        backColor = check[1];
        ColorPrint(foreColor + backColor + prompt + ending, false, true); //do not use newline
        String retValue = "";
    	//if(input.hasNextLine())
    	//{
    		retValue = input.nextLine();
    	//}
        return retValue;
    }
    public static String InputBox(String style, String returnType, String title, String boxMessage, String inputPrompt, String promptEnd, String foreColor, 
            					  String backColor, int width, double minReturnLen, double maxReturnLen, Boolean withTitle)
	{
		/// Draw an inputBox with title, message, input area
		/// Example "bool", "File Exists Warning", "Are you sure you want to over-write?", "Confirm over-write (y/n)_"
		style = FixStyle(style);
		String[] check = ValidateColors(foreColor, backColor);
        foreColor = check[0];
        backColor = check[1];
		if (width > windowWidth || width <= 0) width = windowWidth;
		Clear();
		String userInput = "";
		List<String> lines = GetFormattedLines(boxMessage, 0, false); // returns alist of lines max length of any line Console.Windowwidth - 3
		int numLines = DrawBoxOutline(style, "top", foreColor, backColor, "centre", width, false); // draw top of box double line, yellow
		numLines += DrawBoxBody(style, title, "centre", foreColor, backColor, foreColor, backColor, "centre", width); // draw title
		numLines += DrawBoxOutline(style, "mid", foreColor, backColor, "centre", width, false); // draw single line
		for (String line : lines)
		{
		numLines += DrawBoxBody(style,  line, "centre", foreColor, backColor, foreColor, backColor, "left", width); // draw each line of text
		}
		numLines += DrawBoxBody(style, "", "centre", foreColor, backColor, foreColor, backColor, "centre", width); // draw empty line
		numLines += DrawBoxOutline(style, "bottom", foreColor, backColor, "centre", width, false); // draw bottom of box double line, yellow
		numLines += AddLines(5, numLines, WHITE, BLACKBG);
		numLines += DrawLine("d", WHITE, BLACKBG, 0, "left");
		
		if (returnType.equals("str") || returnType.equals("string"))
		{
			userInput = GetString(numLines, inputPrompt, promptEnd, foreColor, backColor, withTitle, minReturnLen, maxReturnLen);
		}
		else if (returnType.equals("int"))
		{
			userInput = Integer.toString(GetInteger(numLines, inputPrompt, promptEnd, foreColor, backColor, minReturnLen, maxReturnLen));
		}
		else if (returnType.equals("real") || returnType.equals("float") || returnType.equals("double"))
		{
			userInput = Double.toString(GetRealNumber(numLines, inputPrompt, promptEnd, foreColor, backColor, minReturnLen, maxReturnLen));
		}
		else if (returnType.equals("bool") || returnType.equals("boolean"))
		{
			userInput = GetBoolean(numLines, inputPrompt, promptEnd, "", "").toString();
		}
		return userInput; // string eg filename typed in, || bool
	}

    public static int Menu(String style,  String title, String promptChar, List<String> textLines, String foreColor, String backColor, String align, int width)
	{
		/// displays a menu using the text in 'title', and a list of menu items (string)
		/// This menu will re-draw until user enters correct data
		style = FixStyle(style);
		String[] check = ValidateColors(foreColor, backColor);
        foreColor = check[0];
        backColor = check[1];
		if (width > windowWidth || width <= 0) width = windowWidth;            
		for (int i = 0; i < textLines.size(); i++)
		{
			if ( i < 9)     textLines.set(i, "     " + (i + 1) + " " + textLines.get(i));
			else            textLines.set(i, "    " + (i + 1) + " " + textLines.get(i));
		}
		Clear();
		int numLines = 0;
		numLines += DrawBoxOutline(style, "top", foreColor, backColor, "left", 0, false);           // draw top of box double line
		numLines += DrawBoxBody(style, "", "centre", foreColor, backColor, foreColor, backColor, "left", 0);       // draw empty line
		numLines += DrawBoxBody(style, title, "left", foreColor, backColor, foreColor, backColor, "left", 0); // draw title
		numLines += DrawBoxBody(style, "", "centre", foreColor, backColor, foreColor, backColor, "left", 0);       // draw empty line
		for (int i = 0; i < textLines.size(); i++)
		{
			numLines += DrawBoxBody(style, textLines.get(i), "left", foreColor, backColor, foreColor, backColor, "left", 0); // draw menu options
		}
		numLines += DrawBoxBody(style, "", "centre", foreColor, backColor, foreColor, backColor, "left", 0);       // draw empty line
		numLines += DrawBoxOutline(style, "bottom", foreColor, backColor, "left", 0, false);        // draw top of box double line, yellow
		// get multiple return variables using Tuple
		numLines += AddLines(5, numLines, WHITE, BLACKBG);
		numLines += DrawLine("d", WHITE, BLACKBG, 0, "left");
		int userInput = GetInteger(numLines, "Type the number of your choice (1 to " + textLines.size() + ")", promptChar, WHITE, BLACKBG, 1, textLines.size());
		return userInput - 1;
	}
    private static String[] PadBoxSides(String[] sides, String text, String boxAlign)
    {
        /// Takes exising sides: ║║, text: some~red~coloured text, and pads to width of console using alignment
        if (boxAlign.equals("left")) //pad rSide 
        {
            int length = windowWidth - text.length() - 2; // 80 - 78 - 2 = 0
            if (length > 0)    sides[1] = PadString(sides[1], length, " ", "right");    // "║    "
        }
        else if (boxAlign.equals("right")) //pad lSide 
        {
            int length = windowWidth - text.length() - 2; // 80 - 78 - 1 = 1
            if (length > 0) sides[0] = PadString(sides[0], windowWidth - text.length() - 1, " ", "left");     // "    ║"
        }
        else // centre
        {
        	int colorTagSpaces = 0;
        	ColorTag colorTag = new ColorTag(text, sep);
        	colorTagSpaces = colorTag.colorTagSpaces;
        	text = colorTag.output;

            int length = windowWidth - text.length() - 2 + colorTagSpaces; // 80 - 78 - 2 = 0
            if (length >= 2)
            {
                sides[0] = PadString(sides[0], length / 2, " ", "right");
                length = windowWidth - text.length() - sides[0].length() + colorTagSpaces;
                if (length > 0)    sides[1] = PadString(sides[1], length, " ", "left");
            }
        }
        return sides;
    }
    public static String PadRight(String s, int n, String padChar)
    {
        return s + padChar.repeat(n - s.length());
    }
    public static String PadLeft(String s, int n, String padChar)
    {
    	return(padChar.repeat(n - s.length()) + s);
    }
    private static String PadString(String text, int width, String padChar, String align)
    {
        /// width should be width of the part to be padded, so excludes left/right margin characters
        String output = text;
        int colorTagSpaces = 0;
        if (text.contains(sep)) // colour tag(s) present
        {
            /* C#: colourTagSpaces = FormatColorTags(ref output, out List<string> embeddedColors);
             extract any colour info from output eg ~red~ returns additional spaces required and formats output by ref
             Java does not have 'out' or byRef
             Use a private class to get this information
             */
        	ColorTag colorTag = new ColorTag(output, sep);
        	colorTagSpaces = colorTag.colorTagSpaces;
        	output = colorTag.output;
        	//List<String> embeddedColors = colorTag.colorTagList; //not used in this method
        }
        if (align.equals("centre") || align.equals("center"))
        {
            output = PadLeft(output, ((width - output.length() + colorTagSpaces) / 2) + output.length(), padChar); //eg output 10 chars long, pad to half of width - 2 and allow for chars used by colour tag
            output = PadRight(output, width + colorTagSpaces, padChar);
        }
        else if (align.equals("right"))
        {
            output = PadLeft(output, width + colorTagSpaces, padChar); // right align = pad left
        }
        else //left align = PadRight
        {
            output = PadRight(output, width + colorTagSpaces, padChar);  //eg output 10 chars long, pad to width - 2 and allow for chars used by colour tag
        }

        return output;
    }
    private static int print(String text)
    {
    	int numLines = 0;
        numLines += text.chars().filter(ch -> ch == '\n').count(); 	// account for embedded newline characters
        numLines++;
    	System.out.println(text);
    	return numLines;
    }
    private static String ProcessInput(int row, String prompt, String promptChar, String textColor, String backColor, double min, double max, String dataType)
    {
        Boolean valid = false;
        if (dataType.toLowerCase().substring(0, 3).equals("str")) dataType = "string";
        if (dataType.toLowerCase().substring(0, 3).equals("int")) dataType = "int";
        String userInput = "";
        while (!valid)
        {
            String message = "";
            userInput = Input(prompt, promptChar, textColor, backColor);
            TryParse tryParse = new TryParse(userInput);
            if (dataType.equals("string"))
            { 
            	if (tryParse.isEmpty && min > 0) message = "Just pressing the Enter key doesn't work...";
                else if (userInput.length() > max) message = "Try entering between " + min + " and " + max + " characters...";
                else if(tryParse.success) message = "You just entered a number";
                else valid = true;
            }
            else //integer, float, bool
            {
                if (userInput.length() == 0)
                {
                    message = "Just pressing the Enter key doesn't work...";
                }
                else
                {
                    if (dataType.equals("bool"))
                    {
                        if (userInput.substring(0, 1).toLowerCase().equals("y"))
                        {
                            userInput = "true";
                            valid = true;
                        }
                        else if (userInput.substring(0, 1).toLowerCase().equals("n"))
                        {
                            userInput = "false";
                            valid = true;
                        }
                        else message = "Only anything starting with y or n is accepted...";
                    }
                    else
                    {
                        if (dataType.equals("int"))
                        {
                        	if (tryParse.success)
                            {
                                int conversion = tryParse.iOut;
                        		if (conversion >= min && conversion <= max) valid = true;
                                else message = "Try a number from " + min + " to " + max + "...";
                            }
                        }
                        else if (dataType.equals("real"))
                        {
                        	if (tryParse.success)
                            {
                                double conversion = tryParse.dOut;
                        		if (conversion >= min && conversion <= max) valid = true;
                                else message = "Try a number from " + min + " to " + max + "...";
                            }
                        }
                        if (!valid && message.equals(""))
                        {
                            if (dataType.equals("int")) message = "Try entering a whole number: " + userInput + " cannot be converted...";
                            else message = "Try entering a decimal number: " + userInput + " cannot be converted...";
                        }
                    }
                }
            }
            if (!valid)
            {
                ColorPrint(RED + message + MAGENTA + " retry in 2 secs...", true, true);
                Sleep(2000);
                if(isConsole)
                {
	                for (int i = row + 1; i < row + 4; i++)
	                {
	                    SetCursorPos(i, 1);
	                    System.out.print(PadRight("", windowWidth, " "));
	                }
	                SetCursorPos(row + 1, 1);
                }
                else
                {
                	System.out.print("\r\r");
                }
            }
        }
        return  userInput; //string can be converted to bool, int or float
    }
    private static String RemoveANSI(String text)
    {
    	///remove ANSI strings from text
		/// text = "Hello " + RED + "World" = "Hello`[91m World" (where ` represents Char(27))
    	/// text1 = RED + "********" = "`[91m******" (where ` represents Char(27))
		String retValue = "";
    	List<String> parts = UI.SplitClean(text, String.valueOf((char)27)); //"Hello", "[91m world"
    	for(String part : parts)
    	{
    		if(part.startsWith("[")) // ANSI code found
    		{
    			int start = text.indexOf("m");
    			if(start < part.length() - 1)
    				part = part.substring(start);
    			else
    				part = "";
    		}
    		retValue += part;
    	}
		
		return retValue;
    }
    public static void Resize(int windowWidth, int windowHeight)
    {
    	if (isConsole)
    	{
    		try
    		{
	    		if(GetOS().equals("nt"))
	    		{
	    			//new ProcessBuilder("cmd", "/c", "mode" + windowWidth + ";" + windowHeight).inheritIO().start().waitFor();
	    			Runtime.getRuntime().exec((char)44 + "mode " + windowWidth + ";" + windowHeight + (char)44);
	    		}
	    		else
	    		{
	    			String cmd = "\\e[8;" + windowHeight + ";" + windowWidth + "t";
	    			Runtime.getRuntime().exec(cmd);
	    		}
    		}
    		catch (IOException ex) {}
    	}
    }
    public static void SetCursorPos(int row, int col)
    {
    	System.out.print((char)27 + "[" + row + ";" + col + "H");
    }
    private static void Sleep(int delay)
    {
    	try
        {
			Thread.sleep(delay);
		}
        catch (InterruptedException e)
        {
			e.printStackTrace();
		}
    }
    public static List<String> SplitClean(String stringToSplit, String sepChar)
    {
        /// .Split often gives empty array elements. This returns a list of non-empty strings ///
        List<String> retValue = new ArrayList<String>();
        String[] temp = stringToSplit.split(sepChar);
        for (String part : temp)
        {
            if (part.length() > 0 || sepChar.equals("\n")) // allow empty elements if \n\n supplied to create blank lines
            {
                retValue.add(part);
            }
        }

        return retValue;
    }
    public static void Quit(Boolean withConfirm)
    {
        if (withConfirm)
        {
        	System.out.print("Enter to quit");
            input.nextLine();
        }
    }
    private static List<String> SelectCharacterList(String part, String style)
    {
        /// select correct character list depending on style and part ///
        List<String> s = sSymbolsTop;
        switch (part)
        {
            case "top":                                 // 0 1 2 3 <- index
                s = sSymbolsTop;                        // ┌ ─ ┐ ┬
                if (style.equals("d")) s = dSymbolsTop;      // ╔ ═ ╗ ╦
                break;
            case "mid":
                s = sSymbolsMid;                        // ├ ─ ┤ ┼
                if (style.equals("d")) s = dSymbolsMid;      // ╠ ═ ╣ ╬
                break;
            case "bottom":
                s = sSymbolsBottom;                     // └ ─ ┘ ┴ 
                if (style.equals("d")) s = dSymbolsBottom;   // ╚ ═ ╝ ╩
                break;
            case "body":
                s = sSymbolsBody;                       // └ ─ ┘ ┴ 
                if (style.equals("d")) s = dSymbolsBody;     // ╚ ═ ╝ ╩
                break;
        }
        return s;
    }
    public static void SetConsole(int cols, int rows, String foreColor, String backColor, Boolean initialise)
    {
    	final Map<String, String> windows = Map.ofEntries
    			(Map.entry("black", "0"), Map.entry("dblue", "1"), Map.entry("dgreen", "2"), Map.entry("dcyan", "3"),
    			 Map.entry("dred", "4"), Map.entry("dmagenta", "5"), Map.entry("dyellow", "6"), Map.entry("grey", "7"),
    			 Map.entry("dgrey", "8"), Map.entry("blue", "9"), Map.entry("green", "A"), Map.entry("cyan", "B"),
    			 Map.entry("red", "C"), Map.entry("magenta", "D"), Map.entry("yellow", "E"), Map.entry("white", "F"));
    	
    	final Map<String, String> posixFg = Map.ofEntries
    			(Map.entry("black", "30"), Map.entry("dblue", "34"), Map.entry("dgreen", "32"), Map.entry("dcyan", "36"),
    			 Map.entry("dred", "31"), Map.entry("dmagenta", "35"), Map.entry("dyellow", "33"), Map.entry("grey", "37"),
    			 Map.entry("dgrey", "90"), Map.entry("blue", "94"), Map.entry("green", "92"), Map.entry("cyan", "96"),
    			 Map.entry("red", "91"), Map.entry("magenta", "95"), Map.entry("yellow", "93"), Map.entry("white", "97"));
    	
    	final Map<String, String> posixBg = Map.ofEntries
    			(Map.entry("black", "40"), Map.entry("dblue", "44"), Map.entry("dgreen", "42"), Map.entry("dcyan", "46"),
    			 Map.entry("dred", "41"), Map.entry("dmagenta", "45"), Map.entry("dyellow", "43"), Map.entry("grey", "47"),
    			 Map.entry("dgrey", "100"), Map.entry("blue", "104"), Map.entry("green", "102"), Map.entry("cyan", "106"),
    			 Map.entry("red", "101"), Map.entry("magenta", "105"), Map.entry("yellow", "103"), Map.entry("white", "107"));
    	
    	// in case developer forgot to Initialise!
    	if(!isInitialised)	Initialise(false);
    	windowWidth = cols;
    	windowHeight = rows;
    	Resize(windowWidth, windowHeight);
    	if(isConsole)
    	{
    		try
    		{
	    		if(GetOS().equals("nt"))
	    		{
	    			//new ProcessBuilder("cmd", "/c", "mode" + windowWidth + ";" + windowHeight).inheritIO().start().waitFor();
	    			Runtime.getRuntime().exec((char)44 + "color " + windows.get(backColor) + ";" + windows.get(foreColor) + (char) 44);
	    		}
	    		else
	    		{
	    			String bc = (char)44 + "\\e[" + posixBg.get(backColor) + "m" + (char)44;
	    			Runtime.getRuntime().exec(bc);
	    			Runtime.getRuntime().exec("\\e[1m");
	    			String fc = (char)44 + "\\e[" + posixFg.get(foreColor) + "m" + (char)44;
	    			Runtime.getRuntime().exec(fc);
	    		}
    		}
    		catch (IOException ex) {}
    	}
    	if (initialise)
    	{
    		DisplaySetup(cols, rows, foreColor, backColor);
    	}			
    }
    public static int Teletype(String text, int delay, String foreColor, String backColor)
    {
        /// Visual effect of slow character printout
    	String[] check = ValidateColors(foreColor, backColor);
        foreColor = check[0];
        backColor = check[1];
        int numLines = 1; //default 1 line
        numLines += ColorPrint(foreColor + backColor, false, false);
        numLines += text.chars().filter(ch -> ch == '\n').count(); 	// account for embedded newline characters
        for (int i = 0; i < text.length(); i++)
        {
            System.out.print(text.substring(i, i+1));
            Sleep(delay);
        }
        numLines += ColorPrint(RESET, false, false);
        return numLines;
    }
    private static String toTitleCase(String inputString) 
    {
        if (inputString.equals("")) return "";
        if (inputString.length() == 1)	return inputString.toUpperCase();
 
        StringBuffer resultPlaceHolder = new StringBuffer(inputString.length());
 
        Stream.of(inputString.split(" ")).forEach(stringPart -> {
            char[] charArray = stringPart.toLowerCase().toCharArray();
            charArray[0] = Character.toUpperCase(charArray[0]);
            resultPlaceHolder.append(new String(charArray)).append(" ");
        });
 
        return resultPlaceHolder.toString().trim();
    }
    private static String ValidateBoxPart(String part)
    {
        /// check and correct box part strings, else error
        part = part.trim().toLowerCase();
        List<String> check = new ArrayList<String>(Arrays.asList("top", "mid", "bottom", "body" ));
        if (!check.contains(part)) throw new RuntimeException("Part parameter must be: 'top', 'mid', 'bottom', 'body'");
        return part;
    }
    private static String[] ValidateColors(String foreColor, String backColor)
    {
    	// check if ansi code supplied
    	if(!constColors.contains(foreColor))
    	{	
    		foreColor = foreColor.toUpperCase(); 		//"red" -> "RED" "~red~" -> "~RED~"
    		foreColor = foreColor.replace(sep,""); 		//remove separators
    		if (colors.get(foreColor).equals(null)) throw new RuntimeException("foreground colour must match name in any format: 'white' or 'WhiTE' not " + foreColor);
    		foreColor = sep + foreColor + sep;
    	}
    	if(!constColors.contains(backColor))
    	{
	    	backColor = backColor.toUpperCase(); 		//"red" -> "RED" "~redbg~" -> "~REDBG~"
	        backColor = backColor.replace(sep, ""); 	//remove separators
	        if (colors.get(backColor).equals(null)) throw new RuntimeException("background colour must match name in any format: 'black' or 'bLACk' not " + backColor);
	        if(backColor.endsWith("BG")) backColor = sep + backColor + sep;
	        else backColor = sep + backColor + "BG" + sep;
    	}
        String[] retValue = {foreColor, backColor};
        return retValue; //retrurn string array, so will need to be separated by calling code
    }
    private static String ValidateAlignment(String align)
    {
        /// check and correct align strings, else error
        align = align.trim().toLowerCase();
        List<String> check = new ArrayList<String>(Arrays.asList( "left", "centre", "center", "right" ));
        if (!check.contains(align)) throw new RuntimeException("Align parameter must be: 'left', 'centre', 'center', 'right'");
        return align;
    }
}