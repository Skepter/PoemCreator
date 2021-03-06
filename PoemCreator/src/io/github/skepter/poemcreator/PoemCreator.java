package io.github.skepter.poemcreator;

import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JOptionPane;

/**
 * Creates poems. Stitches together the results of PoemBuilder to create 4
 * column poem results. Implements the secret.
 * 
 * @author Jorel
 *
 */
public class PoemCreator {

	private String result;
	

	/**
	 * Creates a poem. All sanity checks are produced during "production" (this
	 * allows ease of determining errors when it "is compiled")
	 * 
	 * @param title
	 * @param showTitle
	 * @param author
	 *            If null, author won't be generated
	 * @param body
	 * @param secret
	 * @param fourColumns
	 *            If using 4 column mode, it will generate 4 columns as opposed
	 *            to two columns. Sometimes, 4 columns is easier to view than 2
	 *            due to overlapping columns
	 */
	public PoemCreator(String title, boolean showTitle, String author, boolean showAuthor, String body, String[] secret, boolean fourColumnMode, boolean justified) {
		PoemBuilder builder = new PoemBuilder(justified);
		// Title
		if(title != null) {
			try {
				// showTitle is dealt with in this class
				builder.generateTitle(title, showTitle);
			} catch (PoemStringLengthException e) {
				JOptionPane.showMessageDialog(null, "Title '" + e.getErrorString() + "' is too long!", "Poem Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}

		// Author
		if (author != null) {
			try {
				builder.generateAuthor(author, showAuthor);
			} catch (PoemStringLengthException e) {
				JOptionPane.showMessageDialog(null, "Author name '" + e.getErrorString() + "' is too long!", "Poem Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		
		// Sanity check for secret embedding
		int lastIndex = 0;
		for (String str : secret) {
			if (body.indexOf(str, lastIndex) == -1) {
				JOptionPane.showMessageDialog(null, "3D word '" + str + "' wasn't detected in your poem!", "Poem Error", JOptionPane.ERROR_MESSAGE);
				return;
			} else {
				lastIndex = body.indexOf(str, lastIndex);
			}
		}
		
		/*
		 * Convert Secret[] into a Queue Basically, I plan to implement a queue
		 * system, where when the next available secret word is found in the
		 * body, it will be dequeued, so you don't have to mess with indices and
		 * stuff
		 */
		Queue<String> secretQueue = new LinkedList<>();
		for(String str : secret) {
			secretQueue.add(str);
		}
		
		//secretQueue.poll();
		
		// Implementing the body into the poem
		String[] paragraphs = body.split("\n");
		for (String str : paragraphs) {
			try {
				//Ensures that the queue continually updates when consumed
				secretQueue = builder.addParagraph(str, secretQueue);
			} catch (PoemStringLengthException e) {
				JOptionPane.showMessageDialog(null, "'" + e.getErrorString() + "' is too long for the poem!", "Poem Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}

		// Poem has been created, all checks dealt with.
		builder.build();
		
		result = builder.getResultingColumns(fourColumnMode);
		

		/*
		 * Basic idea of secret embedding:
		 * Add a single space before each secret in the left column.
		 * Add a single space after each secret in the right column.
		 * 
		 * Deal with "dual secrets" ('the world') later.
		 * When adding a space to each column, remove a space from * at the end of line.
		 */
		
	}
	
	public String getResult() {
		return result;
	}

}
