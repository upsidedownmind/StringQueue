package net.upsidedownmind.stringutils.test;

import java.text.SimpleDateFormat;
import java.util.Iterator;

import net.upsidedownmind.stringutils.StringQueue;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Test suite for <tt>StringQueue</tt> using JUnit 4
 *
 */
public class StringQueueTest {

	@Test
	public void testEquals() throws Exception {
		String test = "Equals";
		StringQueue queue = new StringQueue(test);
		
		assertTrue(queue.equals(test));
		
	}

	@Test
	public void testLong() throws Exception {
		String test = "001error";
		StringQueue queue = new StringQueue(test);
		
		assertTrue(queue.pollLong(3) == 1);
	
	}
	
	@Test
	public void testInteger() throws Exception {
		String test = "001error";
		StringQueue queue = new StringQueue(test);
		
		assertTrue(queue.pollInteger(3) == 1);
	
	}
	
	@Test
	public void testDouble() throws Exception {
		String test = "001error";
		StringQueue queue = new StringQueue(test);
		
		assertTrue(queue.pollDouble(3) == 1);
	
	}
	
	@Test
	public void testIteratorRemove() throws Exception {
		String test = "Equals";
		StringQueue queue = new StringQueue(test);
		
		for (Iterator<String> iterator = queue.iterator(); iterator.hasNext();) {
			String string = iterator.next();
			if(string.equals("a")) {
				iterator.remove();
			}
			
		}
		
		assertEquals("Equls", queue.toString());
	}

	@Test
	public void testIterator2Steps() throws Exception {

		String test = "Equals";
		StringQueue queue = new StringQueue(test);
		
		StringBuilder output = new StringBuilder();
		for (Iterator<String> iterator = queue.iterator(2); iterator.hasNext();) {
			String string = iterator.next();
			output.append(string);
		}
		
		assertEquals(test, output.toString());
	}
	
	@Test
	public void testIterator() throws Exception {

		String test = "Equals";
		StringQueue queue = new StringQueue(test);
		
		StringBuilder output = new StringBuilder();
		for (String string : queue) {
			output.append(string);
		}
		
		assertEquals(test, output.toString());
	}
	
	@Test
	public void testPoll() throws Exception {
		String test = "Equals";
		StringQueue queue = new StringQueue(test);
		
		assertTrue( "Eq".equals(queue.poll(2)) && queue.equals("uals"));
	}
	
	@Test
	public void testPollAll() throws Exception {
		String test = "Equals";
		StringQueue queue = new StringQueue(test);
		
		assertEquals( test, queue.pollAll() );
	}
	
	@Test
	public void testDate() throws Exception {
		String test = "aa20120301notThis";
		String pattern = "yyyyMMdd";
		StringQueue queue = new StringQueue(test);
		
		queue.discart(2);
		
		assertEquals( new SimpleDateFormat(pattern).parse("20120301"), queue.pollDate(pattern));
	}
	
	@Test
	public void testPollNegetive() throws Exception {
		String test = "Equals";
		StringQueue queue = new StringQueue(test);
		
		assertTrue( "Eq".equals(queue.poll(2)) && queue.equals("uals"));
	}
	
	@Test
	public void testPollBigger() throws Exception {
		String test = "Equals";
		StringQueue queue = new StringQueue(test);
		
		assertEquals(test, queue.poll(200));
	}
	
	@Test
	public void testPollEmpty() throws Exception {
		StringQueue queue = new StringQueue();
		
		assertNull(queue.poll());
	}

	@Test
	public void testPeek() throws Exception {
		
		String test = "Equals";
		StringQueue queue = new StringQueue(test);
		
		assertTrue( "Eq".equals(queue.peek(2)) && queue.equals(test));
	}
}
