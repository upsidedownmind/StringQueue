package net.upsidedownmind.stringutils.test;

import java.text.ParseException;
import java.util.Iterator;

import net.upsidedownmind.stringutils.StringQueue;

/**
 * example usage
 *
 */
public class Example {

	public static void main(String[] args) throws ParseException {
		
		//some serialized stuff
		String textToParse ="001one20120301testcase";
		
		//create
		StringQueue queue = new StringQueue(textToParse);
		
		//consume
		System.out.println( queue.pollInteger(3) );
		System.out.println( queue.poll(3) );
		System.out.println( queue.pollDate("yyyyMMdd") );
		System.out.println( queue.pollAll() );
		
		//add some content
		queue.add("001002003");
		
		//walks by 3 without removing
		for (Iterator<String> iterator = queue.iterator(3); iterator.hasNext();) {
			System.out.println(iterator.next());
			
		}
		
		//walks by 3 and remove data
		for (Iterator<String> iterator = queue.iterator(3, true); iterator.hasNext();) {
			System.out.println(iterator.next());
			
		}
		

		//add some more
		queue.add("123456");
		
		//walks one by one without removing 
		for (String c : queue) {
			System.out.println(c);
		} 
		
		//delete content
		queue.clear();
	}
	
}
