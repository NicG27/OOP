# OOP

Object Oriented Programming 'List Manipulation' Assignment
--

In this assignment, I created a Linked List class of Book objects in which various methods allow the user to manipulate the contents of these lists.

Book Class:
Each Book object contains 6 fields: title, author, isbn, price, isbn, genre, year.

BookList Class:
Each BookList object has only one head Node which contains a single Book and a Node referencing to the next Book in the list.
This class contains several methods to manipulate and edit the contents of the list:
  - addToStart: adds a passed Book object to the start of the list 
  - storeRecordsByYear: stores all Book object descriptions from a specific given year in a text file
  - insertBefore: stores a passed Book object before an existing one with the passed isbn
  - insertBetween: stores a passed Book object between the 2 passed isbn values if they exist in the list and are adjacent
  - displayContent: displays the current content of the list for the user
  - delConsecutiveRepeatedRecords: deletes any repeated records of identical Book objects
  - extractAuthList: creates a new BookList object containing all Book objects with the passed author value
  - swap: swaps the 2 Book objects with the 2 passed isbn values if they exist in the list
  - commit: commits the current content of the list to a text file named 'Update_Books.txt'
  
Driver Class:
1. Reads the sample book records from the 'Books.txt' file and stores each record as a Book object in a temporary array.
2. Filters through the Book objects to remove any faulty Book objects that have incorrect year values (> 2023) and stores the valid books in a BookList object.
3. Creates an interactive display for the user in which they can choose between various options that utilize the aforementioned methods to manipulate the BookList.
