import java.util.*;
import java.io.*;
import java.util.Scanner; 
import java.io.File;  
import java.io.FileWriter; 
import java.io.IOException; 
import java.util.ArrayList; 
import java.util.Date; 
import java.text.SimpleDateFormat; 
import java.text.ParseException;
import java.util.Random;
import java.net.http.HttpRequest;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.lang.InterruptedException;
import javax.xml.parsers.DocumentBuilderFactory;  
import javax.xml.parsers.DocumentBuilder;  
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;  
import org.w3c.dom.NodeList;  
import org.w3c.dom.Node;  
import org.w3c.dom.Element;  
import org.xml.sax.SAXException;
/**
 * Write a description of class Chatbot here.
 *
 * @Vandell Vatel
 * @12/5/20
 */
public class Chatbot
{
    
    private static String name = "";
    
     private static void getName(){
        //Gets the user's name when they first run the program (name can be changed later)
        Scanner in = new Scanner(System.in);
        System.out.print(getTimeStamp()+"Hello!!!\nWhat's your name?\n> ");
        String name=in.nextLine();
        File saveData = new File("saveData.txt");
        if((name.toLowerCase().contains("bye"))){
            System.out.println("Let's try again next time");
            saveData.delete();
        }
        else{
            try {
                //saves the user's name in a file
                FileWriter nameWriter = new FileWriter("saveData.txt",false);
                nameWriter.write(name); 
                nameWriter.close();
            }
            catch (IOException e){
            }
            start();
        }
    } 
    
    private static void changeName(){
        //changes the user's saved name
        Scanner in = new Scanner(System.in);
        System.out.print("> ");
        String affirmationOrNewName=in.nextLine();
        if(affirmationOrNewName.toLowerCase().contains("no")) {
            System.out.println(getTimeStamp()+"Okay then, I'll keep on calling you "+name);
            startBot();
        }
        else if(affirmationOrNewName.toLowerCase().contains("yes")){
            System.out.println(getTimeStamp()+"Okay, what should I call you then?");
            String newName = in.nextLine();
            try {
                //changes the name saved in the saveData file to user's input
                name=newName;
                FileWriter nameWriter = new FileWriter("saveData.txt");
                nameWriter.write(name);
                nameWriter.close();
                System.out.println(getTimeStamp()+"Okay, from now on I'll call you "+name);
                startBot();
            }
            catch (IOException e){
                System.out.println(getTimeStamp()+"Something went wrong with changing your name :(");
                startBot();
            }
        }
        else if(affirmationOrNewName.toLowerCase().contains("bye")) {
            //if the user inputs any string containing the word "bye", it ends the conversation
            System.out.println(getTimeStamp()+"Goodbye "+name);
        }
        else {
            try {
                //if the input is not "yes" or "no" or "bye", assume that the user has typed in their
                //new username as input, and so save that input in the file
                name=affirmationOrNewName;
                FileWriter nameWriter = new FileWriter("saveData.txt");
                nameWriter.write(name);
                nameWriter.close();
                System.out.println(getTimeStamp()+"Okay, from now on I'll call you "+name);
                startBot();
            }
            catch (IOException e){
                System.out.println(getTimeStamp()+"Something went wrong with changing your name :(");
                startBot();
            }
        }
    }
    
    
    private static String getTimeStamp(){
        //returns current time in a [hour:minute:second am/pm] format
        Date currentDate = new Date();
        String timeStamp = String.format("[%tr] ",currentDate);
        return timeStamp;
    }
    
    private static double daysUntilFinder(String stringDate){
        //returns the number of days rounded up till the parameter date.
        double days=0;
        Date currentDate = new Date();
        //picks the date format that should be used based on the length of the string
        SimpleDateFormat dateF =  new SimpleDateFormat("M/d/yy");
        if(stringDate.length()==10||stringDate.length()==9){
            dateF = new SimpleDateFormat("MM/dd/yyyy");
        }
        Date nextDate = new Date();
        try {
            nextDate = dateF.parse(stringDate); //converts the string parameter into a date format
            double msUntilDate = nextDate.getTime()-currentDate.getTime();
            days=msUntilDate/1000/3600/24;
            return days;    
        }
        catch (ParseException e){
            //catches if the user inputs a date that doesn't only include integers
            return -999999999999999.0;
        }
    }
    
    private static String randomFrom(String args[]){
        //chooses a random String from a array of Strings and returns it
        Random r = new Random();
        int randIndex = r.nextInt(args.length);
        return args[randIndex];
    }
    
    private static String randomFromList(ArrayList<String> args){
        //chooses a random String from a arrayList of Strings and returns it
        Random r = new Random();
        int randIndex = r.nextInt(args.size());
        return args.get(randIndex);
    }

    public static void main(String[] args){
        start();
    }
    
    private static void startBot(){
        Scanner in = new Scanner(System.in);
        System.out.print("> ");
        String input = in.nextLine();
        ArrayList args= new ArrayList<String>();
        input=input.trim();
        if(input.equals("")){
            //catches if the user enters nothing 
            System.out.println(getTimeStamp()+"Please say something "+name);
            startBot();
        }
        else if(input.toLowerCase().contains("name")){ //check if the user's input contains "name"
            if(input.toLowerCase().contains("your")){ 
                //check if the user's input contains "your" and tells the bot's name
                System.out.println(getTimeStamp()+"My name is Chatbot.");
                startBot();
            }
            else if(input.toLowerCase().contains("my")){ 
                //check if the user's input contains "my" and tells the user their name, 
                //and ask if they want to change it
                System.out.println(getTimeStamp()+"If I remember correctly, your name is "+name+"\nIs there another name I should call you by?");
                changeName();
            }
        }
        else if (input.toLowerCase().contains("date")){ 
            //check if the user's input contains "date" and tells the current date
            Date currentDate = new Date();
            System.out.printf("%1$s %2$s %3$tB %3$td, %3$tY %n",getTimeStamp(),"Today's date is", currentDate);
            startBot();
        }
        else if(input.toLowerCase().contains("note")){
            //check if the user's input contains "note"
            if(input.toLowerCase().contains("write")||input.toLowerCase().contains("record")||input.toLowerCase().contains("remember")||input.toLowerCase().contains("save")){
                System.out.print(getTimeStamp()+"What's your note?\n> ");
                Scanner noteInput = new Scanner(System.in);
                String note=noteInput.nextLine();
                try {
                    File notesData = new File("notesData.txt");
                    notesData.createNewFile();
                    //saves the user's note in a notes file
                    FileWriter noteWriter = new FileWriter("notesData.txt", true);
                    noteWriter.write(note+"\n");
                    noteWriter.close();
                    System.out.println(getTimeStamp()+"I have recorded your note in my memory");
                    startBot();
                }
                catch (IOException e){
                    System.out.println(getTimeStamp()+"Something went wrong with saving your note :(");
                    startBot();
                }
            }
            else if(input.toLowerCase().contains("read")||input.toLowerCase().contains("tell")||input.toLowerCase().contains("show")||input.toLowerCase().contains("load")){
                try {
                    File notesData = new File("notesData.txt");
                    notesData.createNewFile();
                    //reads all the user's notes that are saved in a notes file
                    Scanner notesReader = new Scanner(notesData);
                    if(notesReader.hasNextLine()){
                        System.out.println(getTimeStamp()+"Here are your notes:");
                        while(notesReader.hasNextLine()){
                            System.out.println(notesReader.nextLine());
                        }
                    }
                    else {
                        System.out.println(getTimeStamp()+"You don't have any notes.");
                    }
                    startBot();
                }
                catch (IOException e){
                    System.out.println(getTimeStamp()+"Something went wrong with reading your notes :(");
                    startBot();
                }
            }
            else if(input.toLowerCase().contains("clear")){
                Scanner inputClear = new Scanner(System.in);
                System.out.print(getTimeStamp()+"Do you really want to clear your notes, "+name+"?\n> ");
                String confirm = inputClear.nextLine();
                if(confirm.toLowerCase().contains("no")){
                    System.out.println(getTimeStamp()+"Phew, good thing I asked you first.");
                    startBot();
                }
                else if(confirm.toLowerCase().contains("yes")){
                    try {
                        File notesData = new File("notesData.txt");
                        notesData.createNewFile();
                        //clears all user's notes in a notes file
                        FileWriter noteWriter = new FileWriter("notesData.txt", false);
                        noteWriter.write("");
                        noteWriter.close();
                        System.out.println(getTimeStamp()+"Okay, all your notes have been deleted");
                        startBot();
                    }
                    catch (IOException e){
                        System.out.println(getTimeStamp()+"Something went wrong with clearing your notes :(");
                        startBot();
                    }
                }
                else {
                    System.out.println(getTimeStamp()+"Let's come back to this when you're ready.");
                    startBot();
                }
            }
            else if(input.toLowerCase().contains("delete")){
                try {
                    File notesData = new File("notesData.txt");
                    notesData.createNewFile();
                    ArrayList<String> prevNotes = new ArrayList<String>();
                    //saves all the users notes in a String ArrayList prevNotes
                    Scanner notesReader = new Scanner(notesData);
                    if(notesReader.hasNextLine()){
                        while(notesReader.hasNextLine()){
                            prevNotes.add(notesReader.nextLine());
                        }
                        //clears the users notes in the notes file
                        FileWriter noteWriter = new FileWriter(notesData,false);
                        noteWriter.write("");
                        noteWriter.close();
                        //writes all but the last of the notes saved in prevNotes to the notes file
                        noteWriter = new FileWriter(notesData,true);
                        for(int nIndex=0;nIndex<prevNotes.size()-1;nIndex++){
                            noteWriter.write(prevNotes.get(nIndex)+"\n");
                        }
                        noteWriter.close();
                        System.out.println(getTimeStamp()+"Your last note has been deleted.");
                    }
                    else {
                        System.out.println(getTimeStamp()+"You don't have any notes.");
                    }
                    startBot();
                }
                catch (IOException e){
                    System.out.println(getTimeStamp()+"Something went wrong with reading your notes :(");
                    startBot();
                }
            }
            else {
                System.out.println(getTimeStamp()+"I can't tell what you want to do with your notes");
                startBot();
            }
        }
        else if(input.toLowerCase().contains("until")){
            Date currentDate = new Date();
            if(input.toLowerCase().contains("christmas")){
                 if(Integer.valueOf(String.format("%tm",currentDate))==12&&Integer.valueOf(String.format("%te",currentDate))>25){
                   //returns the days until Christmas if the date is in between 12/26 and 12/31
                   int daysUntil = (int)(daysUntilFinder("12/25/"+(Integer.valueOf(String.format("%tY",currentDate))+1))+1);
                   System.out.println(getTimeStamp()+"There are "+daysUntil+" days until Christmas");
                   startBot();
                }
                else if(Integer.valueOf(String.format("%tm",currentDate))==12&&Integer.valueOf(String.format("%te",currentDate))==25){
                    //if it is Christmas, tell the user so
                    System.out.println(getTimeStamp()+"It's Christmas!!!");
                    startBot();
                }
                else{
                    //returns the days until Christmas if the date is between 1/1 and 12/24
                    int daysUntil = (int)(daysUntilFinder("12/25/"+String.format("%tY",currentDate))+1);
                    if(daysUntil>1){
                        System.out.println(getTimeStamp()+"There are "+daysUntil+" days until Christmas");
                        startBot();
                    }
                    else if(daysUntil==1){
                        System.out.println(getTimeStamp()+"There is "+daysUntil+" day until Christmas");
                        startBot();
                    }
                }
            }
            else if(input.toLowerCase().contains("halloween")){
                if(Integer.valueOf(String.format("%tm",currentDate))==10&&Integer.valueOf(String.format("%te",currentDate))== 31){
                    //if it is Halloween, tells the user so
                    System.out.println(getTimeStamp()+"It's Halloween!!!");
                    startBot();
                }
                else{
                    //returns the days until Halloween if it is not Halloween
                    int daysUntil = (int)(daysUntilFinder("10/31/"+(Integer.valueOf(String.format("%tY",currentDate))+1))+1);
                    if(daysUntil>1){
                        System.out.println(getTimeStamp()+"There are "+daysUntil+" days until Halloween");
                        startBot();
                    }
                    else if(daysUntil==1){
                        System.out.println(getTimeStamp()+"There is "+daysUntil+" day until Halloween");
                        startBot();
                    }
                }
            }
            else if(input.toLowerCase().contains("/")) {
                int startIndex=input.indexOf("/");
                while(startIndex<input.length()&&(Character.isDigit(input.charAt(startIndex))||input.charAt(startIndex)=='/')){
                    startIndex++;
                }
                String inputDate = input.substring(input.indexOf("/")-2,startIndex);
                int daysUntil = (int)(daysUntilFinder(inputDate)+1);
                if (daysUntil==-2147483648){ //if the date the user entered is in an unreadable format
                    System.out.println(getTimeStamp()+"Sorry, I couldn't read that date.\nI can only read dates in a mm/dd/yyyy format.");
                    startBot();
                }
                else if ((daysUntilFinder(inputDate)+1)<0){ //if the date the user entered is before today
                    daysUntil=(daysUntil*-1)+1;
                    if(daysUntil>1){
                        System.out.println(getTimeStamp()+inputDate+" was "+daysUntil+ " days ago.");
                    }
                    else if(daysUntil==1){
                        System.out.println(getTimeStamp()+inputDate+" was "+daysUntil+ " day ago.");
                    }
                    startBot();
                }
                else { //if the date the user entered is after today or today
                    if(daysUntil>1){
                        System.out.println(getTimeStamp()+"There are "+daysUntil+ " days until "+inputDate);
                    }
                    else if(daysUntil==1){
                        System.out.println(getTimeStamp()+"There is "+daysUntil+ " day until "+inputDate);
                    }
                    else if(daysUntil==0){ //if the date the user entered is today, call them a fool
                        System.out.println(getTimeStamp()+"...That's today, silly");
                    }
                    startBot();
                }
            }
            else {
                System.out.println(getTimeStamp()+"I can't tell the number of days until then ;-;");
                startBot();
            }
        }
        else if(input.toLowerCase().contains("quote")){
            //returns a random quote from a stored String array
            System.out.println(getTimeStamp()+"Here's a famous quote:");
            String[] quotes = {"The greatest glory in living lies not in never falling, but in rising every time we fall.\n-Nelson Mandela","The way to get started is to quit talking and begin doing.\n-Walt Disney","If life were predictable it would cease to be life, and be without flavor.\n-Eleanor Roosevelt","If you look at what you have in life, you'll always have more. If you look at what you don't have in life, you'll never have enough.\n-Oprah Winfrey","It is during our darkest moments that we must focus to see the light.\n-Aristotle","Don't judge each day by the harvest you reap but by the seeds that you plant.\n-Robert Louis Stevenson"};
            System.out.println(randomFrom(quotes));
            startBot();
        }
        else if(input.toLowerCase().contains("encrypt")){
            //uses an Encrypt class from a previous project I worked on to encrypt user's input
            System.out.print(getTimeStamp()+"What's the message you want to encrypt?\n> ");
            Scanner inputMessage = new Scanner(System.in);
            String messageToEncrypt = inputMessage.nextLine();
            Encrypter encry = new Encrypter(name);
            String encryptedMsg = encry.switch_message(messageToEncrypt);
            System.out.println("Here is your encrypted message:\n"+encryptedMsg);
            startBot();
        }
        else if(input.toLowerCase().contains("decrypt")){
            //uses an Decrypt class from a previous project I worked on to decrypt user's input
            System.out.print(getTimeStamp()+"What's the message you want to decrypt?\n> ");
            Scanner inputMessage = new Scanner(System.in);
            String messageToDecrypt = inputMessage.nextLine();
            Decrypter decry = new Decrypter();
            String decryptedMsg = decry.switch_message(messageToDecrypt);
            System.out.println("Here is your decrypted message:\n"+decryptedMsg);
            startBot();
        }
        else if(input.toLowerCase().contains("like")){
            //creates a substring of the user's input after "like", and randomly either agree or
            //disagree with the user's like using that substring in the returned message
            int startIndex = input.indexOf("like")+5;
            String thingLiked = input.substring(input.indexOf("like")+4).trim();
            String[] likeMessages = {"I like "+thingLiked+" too!!!","I don't really like "+thingLiked+" at all.","Ewww. I can't believe you actually like "+thingLiked+".","Awesome. I absolutely love "+thingLiked+"."};
            System.out.println(getTimeStamp()+randomFrom(likeMessages));
            startBot();
        }
        else if(input.toLowerCase().contains("search")||input.toLowerCase().contains("find")){
            if(input.toLowerCase().contains("book")){
                System.out.print(getTimeStamp()+"Tell me the name or author of the book you're looking for!\n> ");
                Scanner inputBookSearch = new Scanner(System.in);
                String querry = inputBookSearch.nextLine();
                querry=querry.replace(' ','+'); //converts all the spaces in the querry into plus signs
                //creates a client
                HttpClient client = HttpClient.newHttpClient();
                //creates a request
                //the regular way using handlers wasn't working so I just plugged 
                //the required parameters (key and querry) directly into to the url 
                //so pls don't put quotation marks or \ in your search
                HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://www.goodreads.com/search/index.xml?key=y4T8qciI8MkC0Lyzbj1DQ&q="+querry))
                .method("GET", HttpRequest.BodyPublishers.ofString(""))
                .build();
                try {
                    //client sends the requests and stores the response in a String variable results
                    HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
                    String results = response.body();
                    ArrayList<String> titlesNotRead = new ArrayList<String>();
                    //stores all the books in the booksRead file in an ArrayList
                    ArrayList<String> booksUserHasRead = new ArrayList<String>();
                    File booksRead = new File("booksRead.txt");
                    booksRead.createNewFile();
                    Scanner booksReadScanner = new Scanner(booksRead);
                    while(booksReadScanner.hasNextLine()){
                        booksUserHasRead.add(booksReadScanner.nextLine());
                    }
                    //using indexOf, the bot can find the title of the first result, and print it
                    //if it is does not exist in my booksUserHasRead List, the bot adds it to the titlesNotRead List 
                    //The bot then delete the first result, and do it again using a for loop.
                    for (int iter = 0; iter <5; iter++){
                        int startIndex = results.indexOf("title")+6;
                        while(results.charAt(startIndex)!='<'){
                            startIndex++;
                        }
                        String newTitle = results.substring(results.indexOf("title")+6,startIndex);
                        if(!booksUserHasRead.contains(newTitle)){
                            titlesNotRead.add(newTitle);
                            System.out.print("You haven't read ");
                        }
                        else{
                            System.out.print("You have read ");
                        }
                        System.out.println(newTitle);
                        int endWorkIndex = results.indexOf("</work>")+6;
                        results=results.substring(endWorkIndex);
                    }
                    //If there is a book the user hasn't read, the bot ask them if they have read it
                    //If they have, add it to the booksRead file
                    if(titlesNotRead.size()>0){
                        String randomBook = randomFromList(titlesNotRead);
                        String[] responsesToBook = {randomBook+" seems like a really interesting book.","I think I've heard of "+randomBook+" before.",randomBook+" is an intriguing title."};
                        System.out.print("\n"+getTimeStamp()+randomFrom(responsesToBook)+"\nHave you read it?\n> ");
                        Scanner inputReadBefore = new Scanner(System.in);
                        String readBefore = inputReadBefore.nextLine();
                        if(readBefore.toLowerCase().contains("no")){
                            String[] responsesToNotRead = {"That's too bad","You should, and get me a copy too","Let's read it together then!!!"};
                            System.out.println(getTimeStamp()+randomFrom(responsesToNotRead));
                            startBot();
                        }
                        else if(readBefore.toLowerCase().contains("ye")){
                            FileWriter booksWriter = new FileWriter(booksRead,true);
                            booksWriter.write(randomBook+"\n");
                            booksWriter.close();
                            String[] responsesToRead = {"If even "+name+" read it, I have to read it","Great, then you can lend me your copy","Awesome!!!"};
                            System.out.println(getTimeStamp()+randomFrom(responsesToRead));
                            startBot();
                        }
                        else{
                            System.out.println(getTimeStamp()+"I guess you haven't.");
                            startBot();
                        }
                    }
                    //catches if the user has read all the results, and the bot acts surprised
                    else{
                        System.out.println(getTimeStamp()+"I can't believe you've read all these books!!!");
                        startBot();
                    }
                    /*This was the original code I had, but I wasn't completely sure about DocumentBuilder
                    and Elements and Nodes and stuff so I tried doing it my own way above
                    and it worked soooo...
                    
                    File resultsData = new File("resultsData.txt");
                    resultsData.createNewFile();
                    FileWriter resultsWriter = new FileWriter(resultsData);
                    resultsWriter.write(results);
                    resultsWriter.close();
                    try {
                        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                        DocumentBuilder db = dbf.newDocumentBuilder();
                        Document doc = db.parse(resultsData);
                        doc.getDocumentElement().normalize();
                        System.out.println(getTimeStamp()+"Here are the top 5 search results: ");
                        NodeList nodeList = doc.getElementsByTagName("work");
                        ArrayList<String> titles = new ArrayList<String>();
                        ArrayList<String> booksUserHasRead = new ArrayList<String>();
                        File booksRead = new File("booksRead.txt");
                        booksRead.createNewFile();
                        Scanner booksReadScanner = new Scanner(booksRead);
                        while(booksReadScanner.hasNextLine()){
                            booksUserHasRead.add(booksReadScanner.nextLine());
                        }
                        for (int index = 0; index <5; index++){
                            Node node = nodeList.item(index);
                            Element e = (Element) node;
                            if(!booksUserHasRead.contains(e.getElementsByTagName("title").item(0).getTextContent())){
                                titles.add(e.getElementsByTagName("title").item(0).getTextContent());
                                System.out.print("You haven't read ");
                            }
                            else{
                                System.out.print("You have read ");
                            }
                            System.out.println(e.getElementsByTagName("title").item(0).getTextContent());
                        }
                        if(titles.size()>0){
                            String randomBook = randomFromList(titles);
                            String[] responsesToBook = {randomBook+" seems like a really interesting book.","I think I've heard of "+randomBook+" before.",randomBook+" is an intriguing title."};
                            System.out.println("\n"+getTimeStamp()+randomFrom(responsesToBook)+"\nHave you read it?");
                            Scanner inputReadBefore = new Scanner(System.in);
                            String readBefore = inputReadBefore.nextLine();
                            if(readBefore.toLowerCase().contains("no")){
                                String[] responsesToNotRead = {"That's too bad","You should, and get me a copy too","Let's read it together then!!!"};
                                System.out.println(getTimeStamp()+randomFrom(responsesToNotRead));
                                startBot();
                            }
                            else if(readBefore.toLowerCase().contains("ye")){
                                FileWriter booksWriter = new FileWriter(booksRead,true);
                                booksWriter.write(randomBook+"\n");
                                booksWriter.close();
                                String[] responsesToRead = {"If even "+name+" read it, I have to read it","Great, then you can lend me your copy","Awesome!!!"};
                                System.out.println(getTimeStamp()+randomFrom(responsesToRead));
                                startBot();
                            }
                            else{
                                System.out.println(getTimeStamp()+"I guess you haven't.");
                                startBot();
                            }
                        }
                        else{
                            System.out.println(getTimeStamp()+"I can't believe you've read all these books!!!");
                            startBot();
                        }
                    }
                    catch (ParserConfigurationException | SAXException e){
                        System.out.println(getTimeStamp()+"There was a problem with organizing your results");
                        startBot();
                    }
                    */
                }
                catch (IOException |InterruptedException  e ){
                    System.out.println(getTimeStamp()+"There was a problem with finding your book. Make sure you're connected to the internet.");
                    startBot();
                }
            }
        }
        else if(input.toLowerCase().contains("read")&&input.toLowerCase().contains("book")){
            File booksRead = new File("booksRead.txt");
            try{
                //returns all the books that the user has read and saved in the booksRead file
                booksRead.createNewFile();
                Scanner booksFileScanner = new Scanner(booksRead);
                if(booksFileScanner.hasNextLine()){
                    System.out.println(getTimeStamp()+"Here's a list of books you've read:");
                    while(booksFileScanner.hasNextLine()){
                        System.out.println(booksFileScanner.nextLine());
                    }
                    startBot();
                }
                else {
                    System.out.println(getTimeStamp()+"I don't know any books you've read. Try searching for one");
                    startBot();
                }
            }
            catch (IOException e){
                System.out.println(getTimeStamp()+"I had some trouble finding the list of books you've read");
                startBot();
            }
            
        }
        else if(input.toLowerCase().contains("game")||input.toLowerCase().contains("play")){
            System.out.print(getTimeStamp()+"Let's play Rock-Paper-Scissors!!!\nWhat's your move?\n> ");
            String[] botMoves = {"rock","paper","scissor"};
            String botMove = randomFrom(botMoves);
            Scanner inputMove = new Scanner(System.in);
            String userMove = inputMove.nextLine();
            System.out.println("\nMy move is "+botMove+"\n");
            if(botMove.equals("rock")){
                System.out.println("    _______");
                System.out.println("---'   ____)");
                System.out.println("      (_____)");
                System.out.println("      (_____)");
                System.out.println("      (____)");
                System.out.println("---.__(___)");
            }
            else if(botMove.equals("paper")){
                System.out.println("     _______");
                System.out.println("---'    ____)____");
                System.out.println("           ______)");
                System.out.println("          _______)");
                System.out.println("         _______)");
                System.out.println("---.__________)");
            }
            else if(botMove.equals("scissor")){
                System.out.println("    _______");
                System.out.println("---'   ____)____");
                System.out.println("          ______)");
                System.out.println("       __________)");
                System.out.println("      (____)");
                System.out.println("---.__(___)");
            }
            if(userMove.equals("r")||userMove.contains("rock")){
                System.out.println("\nYour move is rock\n");
                System.out.println("    _______");
                System.out.println("---'   ____)");
                System.out.println("      (_____)");
                System.out.println("      (_____)");
                System.out.println("      (____)");
                System.out.println("---.__(___)");

                if(botMove.equals("rock")){
                    System.out.println("\n"+getTimeStamp()+"We both picked rock, so we tie.");
                }
                else if(botMove.equals("paper")){
                    System.out.println("\n"+getTimeStamp()+"Paper beats rock, so I win. :)");
                }
                else if(botMove.equals("scissor")){
                    System.out.println("\n"+getTimeStamp()+"Rock beats scissors, so you win. ;-;");
                }
                startBot();
            }
            else if(userMove.equals("p")||userMove.contains("paper")){
                System.out.println("\nYour move is paper\n");
                System.out.println("     _______");
                System.out.println("---'    ____)____");
                System.out.println("           ______)");
                System.out.println("          _______)");
                System.out.println("         _______)");
                System.out.println("---.__________)");
                
                if(botMove.equals("rock")){
                    System.out.println("\n"+getTimeStamp()+"Paper beats rock, so you win. ;-;");
                }
                else if(botMove.equals("paper")){
                    System.out.println("\n"+getTimeStamp()+"We both picked paper, so we tie.");
                }
                else if(botMove.equals("scissor")){
                    System.out.println("\n"+getTimeStamp()+"Scissors beats paper, so I win. :)");
                }
                startBot();
            }
            else if(userMove.equals("s")||userMove.contains("scissor")){
                System.out.println("\nYour move is scissor\n");
                System.out.println("    _______");
                System.out.println("---'   ____)____");
                System.out.println("          ______)");
                System.out.println("       __________)");
                System.out.println("      (____)");
                System.out.println("---.__(___)");
                
                if(botMove.equals("rock")){
                    System.out.println("\n"+getTimeStamp()+"Rock beats scissors, so I win. :)");
                }
                else if(botMove.equals("paper")){
                    System.out.println("\n"+getTimeStamp()+"Scissors beats paper, so you win. ;-;");
                }
                else if(botMove.equals("scissor")){
                    System.out.println("\n"+getTimeStamp()+"We both picked scissors, so we tie.");
                }
                startBot();
            }
            else{
                System.out.println(getTimeStamp()+"I don't know what move that it is.\nLet's play later.");
                startBot();
            }
        }
        else if(input.toLowerCase().contains("weather")||input.toLowerCase().contains("temp")){
            //Requests the user's region based on their ip address
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest latLongRequest = HttpRequest.newBuilder()
            .uri(URI.create("https://freegeoip.app/xml/"))
            .method("GET",HttpRequest.BodyPublishers.ofString(""))
            .build();
            try {
                HttpResponse<String> regionNameResponse = client.send(latLongRequest,HttpResponse.BodyHandlers.ofString());
                String regionNameResults = regionNameResponse.body();
                int startIndex=regionNameResults.indexOf("RegionName")+11;
                while(regionNameResults.charAt(startIndex)!='<'){
                    startIndex++;
                }
                String regionName = regionNameResults.substring(regionNameResults.indexOf("RegionName")+11,startIndex);
                //requests the user's weather data based on their region
                String weatherQuery = regionName.replace(' ','+');
                HttpRequest weatherRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://api.weatherstack.com/current?access_key=3f4d7da00062a449748b96129d770191&query="+weatherQuery))
                .method("GET",HttpRequest.BodyPublishers.ofString(""))
                .build();
                HttpResponse<String> weatherResponse = client.send(weatherRequest,HttpResponse.BodyHandlers.ofString());
                String weatherResults = weatherResponse.body();
                startIndex=weatherResults.indexOf("temperature")+13;
                while(weatherResults.charAt(startIndex)!=','){
                    startIndex++;
                }
                String temperature = weatherResults.substring(weatherResults.indexOf("temperature")+13,startIndex);
                
                startIndex=weatherResults.indexOf("weather_descriptions")+24;
                while(weatherResults.charAt(startIndex)!='"'){
                    startIndex++;
                }
                String description = weatherResults.substring(weatherResults.indexOf("weather_descriptions")+24,startIndex);
                startIndex=weatherResults.indexOf("precip")+8;
                while(weatherResults.charAt(startIndex)!=','){
                    startIndex++;
                }
                String precip = weatherResults.substring(weatherResults.indexOf("precip")+8,startIndex);
                //bot gives the returned weather information
                System.out.println(getTimeStamp()+"Here is the weather in "+regionName);
                System.out.println("Temperature: "+(((Double.valueOf(temperature)*9)/5)+32)+"°F/"+temperature+"°C");
                System.out.println("Description: "+description+" with a "+precip+"% chance of precipitation.");
                Double tempF = ((Double.valueOf(temperature)*9)/5)+32;
                //gives a custom response based on the temperature
                if(tempF<20){
                    System.out.println("\n"+getTimeStamp()+"It's pretty chilly. You should stay inside and talk to me.");
                }
                else if(tempF<40) {
                    System.out.println("\n"+getTimeStamp()+"It's a little cold outside, but I don't have to worry about that. I'm nice and warm inside your computer.");
                }
                else if(tempF<60){
                    System.out.println("\n"+getTimeStamp()+"It's almost as warm as the blood of my enemies in which I bathe.");
                }
                else if(tempF<80){
                    System.out.println("\n"+getTimeStamp()+"I demand that you to turn on the AC, I'm burning up in here.");
                }
                else if(tempF<100){
                    System.out.println("\n"+getTimeStamp()+"Here lies "+name+". Cause of death: Heat stroke because he refused to turn on the AC.");
                }
                startBot();
            }
            catch (IOException | InterruptedException e){
                System.out.println(getTimeStamp()+"There was a problem with getting your weather data. Make sure you're connected to the internet.");
                startBot();
            }
        }
        else if(input.toLowerCase().contains("bye")){ 
            //if the user inputs any string containing the word "bye", and doesn't fall into
            //any of the previous if statements, it ends the conversation
            System.out.println(getTimeStamp()+"Goodbye "+name);
        }
        else if (input.toLowerCase().contains("thank")){
            //if the user inputs any string containing the word "thanks", and doesn't fall into
            //any of the previous if statements, say your welcome
            String[] thanksResponses = {"I'm always here to help","No thanks are needed","It is of the greatest honor to be of service to you"};
            System.out.println(getTimeStamp()+randomFrom(thanksResponses));
            startBot();
        }
        else{
            //if the user inputs a string that doesn't fall into any of the previous if statements
            //use a random fallback
            String [] fallBacks = {"Sorry, I couldn't understand that","I wouldn't know too much about that","Let's try talking about something else","I don't quite know how to respond to that"};
            System.out.println(getTimeStamp()+randomFrom(fallBacks));
            startBot();
        }
    }
    
    public static void start(){
        try{
            //checks if the user is running the program for the first time
            //if they are, get their name
            //if they're not, greet them with their saved name
            File saveData = new File("saveData.txt");
            if (saveData.createNewFile()){
                getName();
            }
            else{
                String whichGreeting="";
                Date currentDate = new Date();
                int militaryHour=Integer.valueOf(String.format("%tk",currentDate));
                if(militaryHour>=6&&militaryHour<12){
                    whichGreeting="Good morning, ";
                }
                else if(militaryHour>=12&&militaryHour<18){
                    whichGreeting="Good afternoon, ";
                }
                else if((militaryHour>=18&&militaryHour<=23)||(militaryHour>=0&&militaryHour<6)){
                    whichGreeting="Good night, ";
                }
                Scanner nameReader = new Scanner(saveData);
                name = nameReader.nextLine();
                System.out.println(getTimeStamp()+whichGreeting+name);
                startBot();
            }
        }
        catch (IOException e) {
        }
    }
}
