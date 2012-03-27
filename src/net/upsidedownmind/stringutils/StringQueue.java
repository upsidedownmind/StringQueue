package net.upsidedownmind.stringutils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * This class helps to make easier to parse a string and to work whit 
 *  String related protocols definitions
 * 
 * @author upsaiddownmind
 *
 */
public class StringQueue implements Queue<String> {

	private StringBuilder src;
	
	/**
	 * create object.
	 */
	public StringQueue() {
		this.src = new StringBuilder();
	}
	
	/**
	 * create object whit the given source.
	 * @param src
	 */
	public StringQueue(CharSequence src) {
		this.src = new StringBuilder(src);
	}
	
	@Override
	public boolean addAll(Collection<? extends String> collection) {
		
		//all items must by valid
		StringBuilder tmp = new StringBuilder();
		
		for (Object object : collection) {
			if( !(object instanceof CharSequence) ) {
				return false;
			}
			
			tmp.append( (CharSequence)object );
		}
		
		//all ok
		this.src.append(tmp);
		
		return false;
	}

	@Override
	public boolean add(String str) { 
		//always add
		return addSequence( str );
	}
	
	/**
	 * Inserts the specified element into this queue 
	 * 
	 * @param str
	 * @return
	 */
	public boolean addSequence(CharSequence str) {
		this.src.append( str );
		
		return true;
	}


	@Override
	public void clear() {
		this.src.setLength(0);
	}

	@Override
	public boolean contains(Object object) {
		if(object != null && object instanceof CharSequence) {
			return this.src.indexOf( object.toString() ) != -1;
		}
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> collection) {
		
		for (Object object : collection) {
			if(!this.contains(object)) {
				return false;
			}
		}
		
		return true;
	}

	@Override
	public boolean isEmpty() {
		return this.src.length() == 0;
	}

	@Override
	public Iterator<String> iterator() {
		return this.iterator(1);
	}
	

	/**
     * Returns an iterator over the elements in this collection.<br/>
     * 
     * Same as StringQueue.terator(final int stepLength, <b>false</b>);
     * 
	 * @param stepLength the length of each element
     * @return an <tt>Iterator</tt> over the elements in this collection
     */
	public Iterator<String> iterator( final int stepLength ) {
		return this.iterator(stepLength, false);
	}
	 
	/**
     * Returns an iterator over the elements in this collection.
     * 
	 * @param stepLength the length of each element
	 * @param removeAfterRead  if true, the iterator removes the content after Iterator.next()
     * @return an <tt>Iterator</tt> over the elements in this collection
     */
	public Iterator<String> iterator(final int stepLength, final boolean removeAfterRead) {
		
		//invalid length
		if(stepLength<1) {
			return null;
		}
		
		return new Iterator<String>() {

			private int index = 0;
			
			@Override
			public boolean hasNext() {
				return index+stepLength <= size();
			}

			@Override
			public String next() { 
				String element = src.substring(index, (index+=stepLength));
				
				if(removeAfterRead) {
					index = 0;
					
					src.delete(0, stepLength);
				}
				
				return element;
			}

			@Override
			public void remove() {
				//the elements are removed at the end 
				if(removeAfterRead) {
					throw new UnsupportedOperationException("The element are removed automatically ");
				}
				
				src.delete(index-stepLength, index);
			}
			
		};
	}

	@Override
	public boolean remove(Object object) {
		//TODO: suport this metod
		throw new UnsupportedOperationException("Collection.remove is not suported");
	}

	@Override
	public boolean removeAll(Collection<?> collection) {
		
		StringBuilder original = new StringBuilder( this.src ); 
		
		for (Object object : collection) {
			if(!this.remove(object)) {
				
				this.src = original;
				
				return false;
			}
		}
		
		return true;
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
		throw new UnsupportedOperationException("Collection.retainAll is not suported");
	}

	@Override
	public int size() {
		return this.src.length();
	}

	@Override
	public Object[] toArray() { 
		//TODO improve
		
		String[] split = this.src.toString().split("");
		split =Arrays.copyOfRange(split, 1, split.length); 
		
		return split;
	}

	@Override
	public <T> T[] toArray(T[] arg0) {
		//TODO: support this method
		throw new UnsupportedOperationException("Collection.toArray(T[] arg0) is not suported");
	}

	@Override
	public String element() {
		if(this.isEmpty()) {
			throw new NoSuchElementException();
		}
		
		return peek();
	}

	@Override
	public boolean offer(String str) { 
		//this queue is not restricted 
		return add(str);
	}

	@Override
	public String peek() {
		return this.peek(1);
	}

	/**
     * Retrieves, but does not remove, the firsts <tt>len</tt> elements of this queue,
     * or returns <tt>null</tt> if this queue is empty.
     *
     * @return the head of this queue, or <tt>null</tt> if this queue is empty
     */
	public String peek(int len) {
		if(this.isEmpty()) {
			return null;
		}
		
		//the full string
		if(this.size() <= len) {
			return src.toString();
		}
		
		return src.substring(0, len);
		
	}

	@Override
	public String poll( ) {
		return this.poll(1);
	}
	
	/**
     * Retrieves and removes the entire queue,
     * or returns <tt>null</tt> if this queue is empty.
     *
     * @return this queue, or <tt>null</tt> if this queue is empty
     */
	public String pollAll( ) {
		return this.poll( this.size() ) ;
	}
	
	/**
	 * removes the firsts <tt>len</tt> elements of this queue
	 * 
	 * @param len
	 * @return true if success 
	 */
	public boolean discart(int len) {
		return this.poll(len) != null;
	}
	
	/**
     * Retrieves and removes the firsts <tt>len</tt> elements of this queue,
     * or returns <tt>null</tt> if this queue is empty.
     *
     * @return the head of this queue, or <tt>null</tt> if this queue is empty
     */
	public String poll(int len) {
		
		if(this.isEmpty() || len < 1) {
			return null;
		}
		
		//the rest
		if(this.size() <= len) {
			
			String string = src.toString();
			
			this.clear();
			
			return string;
		}
		
		//Retrieve the head elements
		String substring = src.substring(0, len);
		
		//clear
		src.delete(0, len);
		
		return substring;
	}
	
	/**
     * Retrieves and removes the firsts <tt>len</tt> elements of this queue,
     * or returns <tt>null</tt> if this queue is empty.<br />
     * 
     * This method parse the result string into a Double
     *
     * @return the head of this queue, or <tt>null</tt> if this queue is empty
     */
	public Double pollDouble(int len) throws NumberFormatException {

		if(!this.isEmpty()) {
			return Double.valueOf( this.poll(len) );
		}
		
		return null;
	}
	
	/**
     * Retrieves and removes the firsts <tt>len</tt> elements of this queue,
     * or returns <tt>null</tt> if this queue is empty.<br />
     * 
     * This method parse the result string into a Long
     *
     * @return the head of this queue, or <tt>null</tt> if this queue is empty
     */
	public Long pollLong(int len) throws NumberFormatException {

		if(!this.isEmpty()) {
			return Long.valueOf( this.poll(len) );
		}
		
		return null;
	}
	
	/**
     * Retrieves and removes the firsts <tt>len</tt> elements of this queue,
     * or returns <tt>null</tt> if this queue is empty.<br />
     * 
     * This method parse the result string into a Integer
     *
     * @return the head of this queue, or <tt>null</tt> if this queue is empty
     */
	public Integer pollInteger(int len) throws NumberFormatException {

		if(!this.isEmpty()) {
			return Integer.valueOf( this.poll(len) );
		}
		
		return null;
	}
	/**
     * Retrieves and removes the firsts elements of this queue,
     * or returns <tt>null</tt> if this queue is empty.<br />
     * 
     * This method parse the result string into a Date
     *
     * @return the head of this queue, or <tt>null</tt> if this queue is empty
     */
	public Date pollDate(SimpleDateFormat format) throws ParseException {
		if(format == null) {
			return null;
		}
		
		return format.parse( this.poll(format.toPattern().length()) );
		
	}
	/**
     * Retrieves and removes the firsts elements of this queue,
     * or returns <tt>null</tt> if this queue is empty.<br />
     * 
     * This method parse the result string into a Date
     *
     * @return the head of this queue, or <tt>null</tt> if this queue is empty
     */
	public Date pollDate(String pattern) throws ParseException {
		if(this.isEmpty() || pattern == null || pattern.isEmpty()) {
			return null;
		}
		
		return this.pollDate(new SimpleDateFormat(pattern));
	}
	
	@Override
	public String remove() {
		if(this.isEmpty()) {
			throw new NoSuchElementException();
		}
		
		return poll();
	}

	//Methods from StringBuilder
	
	public StringBuilder append(CharSequence str) {
		return src.append(str);
	}

	public boolean equals(Object obj) {
		if(obj == null) {
			return false;
		}
		return src.toString().equals(obj.toString());
	}

	public int hashCode() {
		return src.hashCode();
	}

	public StringBuilder reverse() {
		return src.reverse();
	}

	public String substring(int start, int end) {
		return src.substring(start, end);
	}

	public String substring(int start) {
		return src.substring(start);
	}

	public String toString() {
		return src.toString();
	}

}
