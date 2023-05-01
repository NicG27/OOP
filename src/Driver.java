import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

// ---------------------------------------------
// Assignment 4
// Written by: Nicholas Gallacher | ID 40248681
// ---------------------------------------------

public class Driver {

	public static void main(String[] args) {
		
		// create lists
		ArrayList<Book> arrLst = new ArrayList<>();
		BookList bkLst = new BookList();
		
		// read records and store them 
		try {
			
			// delete old files so that we don't duplicate
			File out1 = new File("YearErr.txt"); out1.delete();
			File out2 = new File("Updated_Books.txt"); out2.delete();
			
			
			BufferedReader reader = new BufferedReader(new FileReader("Books.txt")); // for reading records
			PrintWriter yearErr = new PrintWriter("YearErr.txt"); // for writing contents of arrLst
			
			// 
			String recordStr;
			Book recordBk = null;
			while((recordStr = reader.readLine()) != null) { // while there is another record in Books.txt
					
				// splitting records into fields
				String[] fields = recordStr.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);	// regular expression that ignores commas between ""
				// trimming string
		        for (int i = 0; i < fields.length; i++) {
		            fields[i] = fields[i].trim();
		        }
		        // initialize this record as a Book
		        double price = Double.parseDouble(fields[2]);
		        long isbn = Long.parseLong(fields[3]);
		        int year = Integer.parseInt(fields[5]);
		        recordBk = new Book(fields[0],fields[1],price,isbn,fields[4],year);
		        
		        // adding bad records to arrLst
		        if (year >= 2024) {
		        	arrLst.add(recordBk);
		        	continue;
		        }
		        // adding good records to bkLst
		        bkLst.addToStart(recordBk);
		        
			} // all records have now been read
			reader.close();
			
			// now we store all bad records in YearErr.txt
			for (Book error : arrLst) yearErr.println(error.toString());
			System.out.println("YearError File Created");
			yearErr.close();
			
			
			// ----------------
			// USER MENU PROMPT
			// ----------------
			
			System.out.println("Here are the contents of the list:");
			System.out.println("----------------------------------");
			bkLst.displayContent();
			System.out.println("Tell me what you want to do? Let's chat since this is trending now! Here are the options:");

			String menu = "\t1) Give me a year # and I will extract all records of that year and store them in a file for that year;\n"
					+ "\t2) Ask me to delete all consecutive repeated records;\n"
					+ "\t3) Give me an author name and I will create a new list with the records of this author and display them;\n"
					+ "\t4) Give me an ISBN number and a Book object, and I will insert a Node with the book before the record with this ISBN;\n"
					+ "\t5) Give me 2 ISBN numbers and a Book object, and I will insert a Node between them, if I find them!\n"
					+ "\t6) Give me 2 ISBN numbers and I will swap them in the list for rearrangement of records; of course only if they exist!\n"
					+ "\t7) Tell me to COMMIT! Your command is my wish. I will commit your list to a file called Updated_Books;\n"
					+ "\t8) Tell me to STOP TALKING. Remember, if you do not commit, I will not!\n"
					+ "Enter your selection: ";
			
			Scanner kb = new Scanner(System.in);
			boolean committed = false;
			int option;
			
			while (true) { // loops prompt with 8 options until return is executed in option 8
				
				System.out.print(menu);
				option = kb.nextInt();
				String bookChoice;
				String[] fields;
				double price;
				long isbn;
				int year;
				long isbnChoice1;
				long isbnChoice2;
				String junk;
				
				switch (option) {
				
				case 1:
					System.out.print("Please choose a year: ");
					int yearChoice = kb.nextInt();
					bkLst.storeRecordsByYear(yearChoice);
					System.out.println("Records have been extracted. If the file does not exist, no records were found for that year.");
					break;
					
				case 2:
					bkLst.delConsecutiveRepeatedRecords();
					System.out.println("Here are the contents of the list after removing consecutive duplicates");
					System.out.println("-----------------------------------------------------------------------");
					bkLst.displayContent();
					break;
					
				case 3:
					System.out.print("Please choose an author: ");
					junk = kb.nextLine();
					String authorChoice = kb.nextLine();
					authorChoice.trim();
					BookList autLst = bkLst.extractAuthList(authorChoice);
					if (autLst.getHead() == null) {
						System.out.println("No records for author \"" + authorChoice + "\" were found.");
					} else {
						System.out.println("Here are the contents of the author list for \"" + authorChoice + "\"");
						System.out.println("-----------------------------------------------------------------------");
						autLst.displayContent();
					}
					break;
					
				case 4:
					System.out.print("Please choose an ISBN: ");
					long isbnChoice = kb.nextLong();
					System.out.print("Please choose a Book: ");
					junk = kb.nextLine();
					bookChoice = kb.nextLine();
					// processing user book string:
					fields = bookChoice.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
			        for (int i = 0; i < fields.length; i++) { // trimming
			            fields[i] = fields[i].trim();
			        }
			        price = Double.parseDouble(fields[2]);
			        isbn = Long.parseLong(fields[3]);
			        year = Integer.parseInt(fields[5]);
			        recordBk = new Book(fields[0],fields[1],price,isbn,fields[4],year);
			        // executing method
			        if (bkLst.insertBefore(isbnChoice, recordBk)) {
						System.out.println("Here are the contents of the list after adding the book");
						System.out.println("-------------------------------------------------------");
						bkLst.displayContent();
			        } else {
						System.out.println("ISBN was not found. Book was not added to the list.");
			        }
					break;
					
				case 5:
					System.out.print("Please choose your first ISBN: ");
					isbnChoice1 = kb.nextLong();
					System.out.print("Please choose your second ISBN: ");
					isbnChoice2 = kb.nextLong();
					System.out.print("Please choose a Book: ");
					junk = kb.nextLine();
					bookChoice = kb.nextLine();
					// processing user book string:
					fields = bookChoice.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
			        for (int i = 0; i < fields.length; i++) { // trimming
			            fields[i] = fields[i].trim();
			        }
			        price = Double.parseDouble(fields[2]);
			        isbn = Long.parseLong(fields[3]);
			        year = Integer.parseInt(fields[5]);
			        recordBk = new Book(fields[0],fields[1],price,isbn,fields[4],year);
			        // executing method
			        if (bkLst.insertBetween(isbnChoice1, isbnChoice2, recordBk)) {
						System.out.println("Here are the contents of the list after adding the book");
						System.out.println("-------------------------------------------------------");
						bkLst.displayContent();
			        } else {
						System.out.println("ISBNs were not found. Book was not added to the list.");
			        }
					break;
					
				case 6:
					System.out.print("Please choose your first ISBN: ");
					isbnChoice1 = kb.nextLong();
					System.out.print("Please choose your second ISBN: ");
					isbnChoice2 = kb.nextLong();
					if (bkLst.swap(isbnChoice1, isbnChoice2)) {
						System.out.println("Here are the contents of the list after swapping the books");
						System.out.println("----------------------------------------------------------");
						bkLst.displayContent();
					} else {
						System.out.println("ISBNs were not found. List has not changed.");
					}
					break;
					
				case 7:
					bkLst.commit();
					System.out.println("List has been committed to file 'Update_Books.txt'");
					committed = true;
					break;
					
				case 8:
					if (committed) {
						System.out.println("Exiting now, Goodbye!");
						kb.close();
						return;
					} else {
						System.out.println("You haven't committed yet! Try again...");
					}
					break;
					
				default:
					System.out.println("Invalid choice. Please choose a number between 1 and 8.");
					break;
				}
			}
			
			
			/*
			// TESTS:
			
			New Book:
				"The Dark Road", Jimin S, 25.92, 1239009879, FCN, 2019 
				
			Insert Before:
				1887664688 -> inserts before "You're Not Old Enough Son" 
				
			Swap:
				1887664688, 1239009879 -> swaps "The Dark Road" and "You're Not Old Enough Son" 
				
			Insert Between:
				9780899509, 9780786400 -> inserts between "Lewis Jerry" and "Hammer Films" 
				
			Extract Author List:
				Roy Malan -> test before and after deleting duplicates
				
			 */
			
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
