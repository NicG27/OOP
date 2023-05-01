// ---------------------------------------------
// Assignment 4
// Written by: Nicholas Gallacher | ID 40248681
// ---------------------------------------------

import java.io.Serializable;

public class Book implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String title;
	private String authors;
	private double price;
	private long isbn;
	private String genre;
	private int year;

	// default constructor
	public Book() {
		this.title = "";
		this.authors = "";
		this.price = 0.0;
		this.isbn = 0;
		this.genre = "";
		this.year = 0;
	}
	
	// parameterized constructor
	public Book(String title, String authors, double price, long isbn, String genre, int year) {
		this.title = title;
		this.authors = authors;
		this.price = price;
		this.isbn = isbn;
		this.genre = genre;
		this.year = year;
	}
	
	// copy constructor
	public Book(Book b) {
		this.title = b.title;
		this.authors = b.authors;
		this.price = b.price;
		this.isbn = b.isbn;
		this.genre = b.genre;
		this.year = b.year;
	}
	
	// accessors
	public String getTitle() {
		return this.title;
	}
	public String getAuthors() {
		return this.authors;
	}
	public double getPrice() {
		return this.price;
	}
	public long getIsbn() {
		return this.isbn;
	}
	public String getGenre() {
		return this.genre;
	}
	public int getYear() {
		return this.year;
	}
	
	// mutators
	public void setTitle(String title) {
		this.title = title;
	}
	public void setAuthors(String authors) {
		this.authors = authors;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public void setIsbn(long isbn) {
		this.isbn = isbn;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public void setYear(int year) {
		this.year = year;
	}
	
	@Override
	public String toString() {
		return title + "," + authors + "," + price + "," + isbn + "," + genre + "," + year;
	}
	
	@Override
	public boolean equals(Object x) { 
		if (this == x) return true;
		if (x == null || !(x instanceof Book)) return false;
		Book other = (Book)x;
		return ((this.title.equals(other.title)) && (this.authors.equals(other.authors)) && (this.price == other.price) && (this.isbn == other.isbn) && (this.genre.equals(other.genre)) && (this.year == other.year));
	}

}
