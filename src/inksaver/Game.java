package inksaver;

public class Game
{
	 public static void GuessTheNumber()
     {
         /// The methods in this class use demonstration methods in the UI class
         /// This is an example of how the UI library should be used.
         /// Put the game logic in as many classes as you need, and pass
         /// all the user I/O to the UI class
         
         String description = "\n\n~yellow~Welcome to the most incredible\n\n" +
                              "~red~'Guess The Number Game'\n\n" +
                              "~green~You have ever seen!\n\n";
         String intro = "This is really annoying, but students like it...";
         GameShowIntro(description, intro);
         int secretNumber = GetRandomNumber();
         int attempts = GamePlayGame(secretNumber);
         String ending = "\n\n~red~Well done! Player of the year!\n\n" +
                         "~green~You smashed it!\n\n" +
                         "~yellow~You guessed the secret number: " + secretNumber + "\n\n" +
                         "~magenta~with just " + attempts + " attempts!\n\n";
         GameShowEnding(ending);
     }
     private static int GetRandomNumber()
     {
    	 return (int)(Math.random() * 99);  
     }
     public static int GamePlayGame(int secretNumber)
     {
         /// This function has some of the game logic in it, but is mostly user I/O
         int guess = 0;
         int attempts = 0;
         while (guess != secretNumber)
         {
             attempts++;
             UI.Clear();
             String userInput = UI.InputBox("s", "int", "~magenta~Guess The Number", "~cyan~See if you can guess the number", "Type your guess for the secret number,(1 to 100)", ">_", "green", "black", 60, 1, 100, false);
             guess = Integer.parseInt(userInput);
             if (guess > secretNumber)
             {
                 UI.DisplayMessage("Sorry, your guess " + guess + " was too high", false, true, "magenta", "black", 2000);
             }
             else if (guess < secretNumber)
             {
                 UI.DisplayMessage("Sorry, your guess " + guess + " was too low", false, true, "cyan", "black", 2000);
             }
         }
         return attempts;
     }
     public static void GameShowEnding(String description)
     {
         UI.Clear();
         int numLines = UI.DrawMultiLineBox("s", description, "yellow", "black", "white", "black", "centre", "centre", 0);
         numLines += UI.AddLines(2, "white", "blackbg");
         UI.AddLines(5, numLines, "white", "blackbg");
         UI.DrawLine("d", "white", "black", 0, "left");
         UI.DisplayMessage("", true, false, "white", "blackbg", 2000);
     }
     public static void GameShowIntro(String description, String intro)
     {
         UI.Clear();
         int numLines = UI.DrawMultiLineBox("s", description, "yellow", "black", "white", "black", "centre", "centre", 60);
         numLines += UI.AddLines(2, "white", "blackbg");
         intro = UI.PadLeft(intro, ((UI.windowWidth - intro.length()) / 2) + intro.length(), " ");
         intro = UI.PadRight(intro, UI.windowWidth, " ");
         numLines += UI.Teletype(intro, 20, "black", "red");
         UI.AddLines(5, numLines, "white", "blackbg");
         UI.DrawLine("d", "white", "black", 0 , "left");
         UI.DisplayMessage("", true, false, "white", "blackbg", 2000);
     }
}
