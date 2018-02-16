package io.github.skepter.poemcreator;

/**
 * Creates poems. Stitches together the results of PoemBuilder to create 4
 * column poem results. Implements the secret.
 * 
 * @author Jorel
 *
 */
public class PoemCreator {

	//TODO GUI?
	
	public static void main(String[] a) {
		
		//Testing the raw PoemBuilder class
		PoemBuilder builder = new PoemBuilder();
		try {
			builder.generateTitle("title", false);
			// builder.addLine(new PoemString("this is simple"));

			builder.addParagraph("This is an insanely super long abnormal string which has length of over "
					+ "twenty nine characters just to test the functionality of my paragraph function");

			
			builder.generateAuthor("Jorel Ali");
			builder.build();
			builder.printBuilder();
		} catch (PoemStringLengthException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @param title
	 * @param showTitle
	 * @param author
	 *            If null, author won't be generated
	 * @param body
	 * @param secret
	 */
	public PoemCreator(String title, boolean showTitle, String author, String body, String[] secret) {
		PoemBuilder builder = new PoemBuilder();
			// Title
			try {
				builder.generateTitle(title, showTitle);
			} catch (PoemStringLengthException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Author
			if (author != null) {
				try {
					builder.generateAuthor(author);
				} catch (PoemStringLengthException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			String[] paragraphs = body.split("\n");
			for (String str : paragraphs) {
				try {
					builder.addParagraph(str);
				} catch (PoemStringLengthException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		
		// Requirements for a poem:
		/*
		 * Title
		 * 
		 * TitleShown (optional - depends if title should be shown)
		 * 
		 * Author (optional)
		 * 
		 * String containing "body" of the text
		 * 
		 * String[] or List<String> containing the secret
		 */

	}

}
