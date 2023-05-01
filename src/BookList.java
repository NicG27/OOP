import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

// ---------------------------------------------
// Assignment 4
// Written by: Nicholas Gallacher | ID 40248681
// --------------------------------------------

// circular singly linked list:
public class BookList {
	
	private Node head;
	
	// private inner class Node:
	private class Node {
		
		private Book b;
		private Node next;
		
		// parameterized constructor:
		public Node(Book b, Node next) {
			this.b = b;
			this.next = next;
		}
	}
	
	// head getter
	public Node getHead() {
		return head;
	}
	
	// 1. default constructor:
	public BookList() {
		head = null;
	}
	
	
	// 2. add a Book object to the start of the list:
	// -> the order in which we want to add these books to our list is the reverse of how we read them, so we add to end after the first addition
	public void addToStart(Book b) {
		if (head == null) {
			head = new Node(b, head);
			head.next = head;
		} else { 
			// after the first addition, it essentially executes an addToEnd() method
			Node tail = head;			// storing the head in tail
			while(tail.next != head) {	// finding the actual tail
				tail = tail.next;
			}
			Node newNode = new Node(b, head);
			tail.next = newNode;		// placing a new node after last node
		}
	}
		/*	TYPICAL ADDTOSTART() METHOD:
		} else {
			Node tail = head; 			// storing the current head
			while(tail.next != head) { 	// finding the tail
				tail = tail.next;
			}
			Node newNode = new Node(b, head);
			tail.next = newNode; 		// placing new node after last node
			head = newNode; 			// and making it the new head
		}
		*/
	
	
	// 3. store records by year in respective files:
	public void storeRecordsByYear(int yr) throws FileNotFoundException {
		
		String yrFile = Integer.toString(yr) + ".txt";
		File out = new File(yrFile); out.delete();	// deleting so that we don't get duplicates
		
		try {
		
		// array list to store books to be outputted
		ArrayList<Book> booksByYear = new ArrayList<>();
		
		if (head == null) return;				// if list is empty, exit
		
		Node current = head;					// start at head (start of list)
		do {									// do once before checking condition since current=head
			if (current.b.getYear() == yr) {	// if this book has yr
				booksByYear.add(current.b); 	// store current book in array list
			}
			current = current.next;				// then check the next book
		} while (current != head);				// until you reach head again (end of list)	
		
		if (booksByYear.isEmpty()) return;		// if array list is empty, exit without creating file
		
		// now we create 'yr.txt' and store the Book objects
		PrintWriter writer = new PrintWriter(yrFile);
		for (Book bk : booksByYear) { writer.println(bk.toString()); }
		writer.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return;
	}
	
	
	// 4. insert a new Node with passed Book 'b' before the calling BookList Node with passed 'isbn':
	public boolean insertBefore(long isbn, Book b) {
		
		if (head == null) return false;
		
		Node current = head;
		Node prev = null;
		Node newNode;
		
		do {
			if (current.b.getIsbn() == isbn) {			// when we find the node with isbn
				if (prev == null) {						// if it is head
					newNode = new Node(b, current);		// new node points to head
					head = newNode;						// new node becomes head
				} else {								// if it is not head
					prev.next = new Node(b, current);	// 
				}
				return true;
			}							// iteration finished
			prev = current;				// prev becomes current
			current = current.next;		// current becomes next
			
		} while (current != head);
		
		return false;	// if we never found the isbn
	}
	
	
	// 5. insert a new Node with passed Book 'b' between calling BookList Nodes with passed 'isbn1' and 'isbn2':
	public boolean insertBetween(long isbn1, long isbn2, Book b) {
		
		if (head == null) return false;
		
		Node current = head;
		Node newNode;
		
		do {
			if (current.b.getIsbn() == isbn1 && current.next.b.getIsbn() == isbn2) {
				newNode = new Node(b, current.next);
				current.next = newNode;
				return true;
			}
			current = current.next;
			
		} while (current != head);
		
		return false;
	}
	
	
	// 6. display records of calling BookList object:
	public void displayContent() {
		
		Node current = head;
		do {
			System.out.println(current.b.toString() + " ==>");
			current = current.next;
		} while (current != head);
		System.out.println("===> head");
	}
	
	
	// 7. delete all consecutive repeated records in calling BookList:
	public boolean delConsecutiveRepeatedRecords() {
		
		if (head == null || head.next == head) return false;
		
		boolean removedDuplicates = false;
		Node current = head;
		
		while (current.next != head) {	// loop until end of list
			
            if (current.b.equals(current.next.b)) {	// if duplicate is found
            	
                current.next = current.next.next;	// delete duplicate
                removedDuplicates = true;			// update return
                
            } else { // advance current
                current = current.next;
            }
        }
		
		// after loop, check for duplicate between last node and head
		if (current.b.equals(head.b)) {	
            removedDuplicates = true;
            current.next = head.next;
            head = current.next;
        }

        return removedDuplicates;
	}
	
	
	// 8. search all nodes in calling BookList for books with passed author value, store in a new BookList, and return it
	public BookList extractAuthList(String aut) {
		
		BookList autLst = new BookList();
		Node current = head;
		if (head == null) return autLst;
		
		do {
			if (current.b.getAuthors().equals(aut)) {	// if author matches
				autLst.addToStart(current.b);			// add the book to new list
			}	
			current = current.next;						// go to next node
		} while (current != head);						// until list is scanned
		
		return autLst;
	}
	
	
	// 9. swap the book values in the nodes with the 2 passed isbn values:
	public boolean swap(long isbn1, long isbn2) {
		
		if (head == null || isbn1 == isbn2) return false;
		
		Node current = head;
		Node node1 = null;
		Node node2 = null;
		
		// the first time we find a valid pair, we swap them
		// if the pair exists, eventually the current node will have each isbn, so each node will be populated
		do {
			if (current.b.getIsbn() == isbn1) node1 = current;
			if (current.b.getIsbn() == isbn2) node2 = current;
			current = current.next;
			
		} while (current != head && (node1 == null || node2 == null));
		
		// exiting loop, if either node 1 or 2 are null, the pair doesn't exist
		if (node1 == null || node2 == null) return false;
		
		// exiting loop, if nodes 1 & 2 are not empty, we swap them
		Book temp = node1.b;
		node1.b = node2.b;
		node2.b = temp;
		return true;
	}
	
	
	// 10. commit updates to calling BookList by writing books to file 'Update_Books.txt':
	public void commit() {
		try {
			// deleting so that we don't get duplicates
			File out = new File("Update_Books.txt"); out.delete();
			
			// outputting all book list objects
			PrintWriter update = new PrintWriter("Update_Books.txt");
			Node current = head;						// start at head (start of list)
			do {										// do once before checking condition since current=head
				update.println(current.b.toString()); 	// write current book in file
				current = current.next;					// then check the next book
			} while (current != head);					// until you reach head again (end of list)
			update.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
}
