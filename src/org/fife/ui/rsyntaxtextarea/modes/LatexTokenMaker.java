/* The following code was generated by JFlex 1.4.1 on 8/20/16 5:08 PM */
/*
 * 04/24/2012
 * LatexTokenMaker.java - Scanner for LaTeX.
 * This library is distributed under a modified BSD license. See the included
 * RSyntaxTextArea.License.txt file for details.
 */
package org.fife.ui.rsyntaxtextarea.modes;

import java.io.IOException;

import javax.swing.text.Segment;

import org.fife.ui.rsyntaxtextarea.AbstractJFlexTokenMaker;
import org.fife.ui.rsyntaxtextarea.Token;
import org.fife.ui.rsyntaxtextarea.TokenImpl;

/**
 * Scanner for the LaTeX.
 * <p>
 *
 * This implementation was created using
 * <a href="http://www.jflex.de/">JFlex</a> 1.4.1; however, the generated file
 * was modified for performance. Memory allocation needs to be almost completely
 * removed to be competitive with the handwritten lexers (subclasses of
 * <code>AbstractTokenMaker</code>, so this class has been modified so that
 * Strings are never allocated (via yytext()), and the scanner never has to
 * worry about refilling its buffer (needlessly copying chars around). We can
 * achieve this because RSTA always scans exactly 1 line of tokens at a time,
 * and hands the scanner this line as an array of characters (a Segment really).
 * Since tokens contain pointers to char arrays instead of Strings holding their
 * contents, there is no need for allocating new memory for Strings.
 * <p>
 *
 * The actual algorithm generated for scanning has, of course, not been
 * modified.
 * <p>
 *
 * If you wish to regenerate this file yourself, keep in mind the following:
 * <ul>
 * <li>The generated <code>LatexTokenMaker.java</code> file will contain two
 * definitions of both <code>zzRefill</code> and <code>yyreset</code>. You
 * should hand-delete the second of each definition (the ones generated by the
 * lexer), as these generated methods modify the input buffer, which we'll never
 * have to do.</li>
 * <li>You should also change the declaration/definition of zzBuffer to NOT be
 * initialized. This is a needless memory allocation for us since we will be
 * pointing the array somewhere else anyway.</li>
 * <li>You should NOT call <code>yylex()</code> on the generated scanner
 * directly; rather, you should use <code>getTokenList</code> as you would with
 * any other <code>TokenMaker</code> instance.</li>
 * </ul>
 *
 * @author Robert Futrell
 * @version 0.5
 *
 */
public class LatexTokenMaker extends AbstractJFlexTokenMaker {

	/** This character denotes the end of file */
	public static final int YYEOF = -1;
	/** lexical states */
	public static final int EOL_COMMENT = 1;
	public static final int YYINITIAL = 0;
	/**
	 * Translates characters to character classes
	 */
	private static final String ZZ_CMAP_PACKED = "\11\0\1\3\1\32\1\0\1\3\23\0\1\3\1\5\1\0\1\5"
			+ "\1\7\1\4\7\5\1\2\1\22\1\6\12\1\1\20\1\5\1\0" + "\1\5\1\0\2\5\32\1\1\5\1\23\1\5\1\0\1\2\1\0"
			+ "\1\1\1\25\1\1\1\31\1\17\1\14\1\26\1\10\1\15\2\1" + "\1\16\1\1\1\27\1\1\1\12\2\1\1\13\1\11\2\1\1\21"
			+ "\3\1\1\30\1\0\1\24\1\5\uff81\0";
	/**
	 * Translates characters to character classes
	 */
	private static final char[] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);
	/**
	 * Translates DFA states to action switch labels.
	 */
	private static final int[] ZZ_ACTION = zzUnpackAction();
	private static final String ZZ_ACTION_PACKED_0 = "\2\0\2\1\1\2\1\3\1\1\1\4\1\5\4\6"
			+ "\1\7\1\10\1\11\2\10\4\0\2\10\4\0\2\10" + "\2\0\1\12\1\0\1\10\3\0\1\10\1\13\2\0" + "\1\14";

	private static int[] zzUnpackAction() {
		int[] result = new int[43];
		int offset = 0;
		offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
		return result;
	}

	private static int zzUnpackAction(String packed, int offset, int[] result) {
		int i = 0; /* index in packed string */
		int j = offset; /* index in unpacked array */
		int l = packed.length();
		while (i < l) {
			int count = packed.charAt(i++);
			int value = packed.charAt(i++);
			do
				result[j++] = value;
			while (--count > 0);
		}
		return j;
	}

	/**
	 * Translates a state to a row index in the transition table
	 */
	private static final int[] ZZ_ROWMAP = zzUnpackRowMap();
	private static final String ZZ_ROWMAP_PACKED_0 = "\0\0\0\33\0\66\0\121\0\66\0\66\0\154\0\66"
			+ "\0\66\0\207\0\242\0\275\0\330\0\66\0\363\0\66"
			+ "\0\u010e\0\u0129\0\u0144\0\u015f\0\u017a\0\u0195\0\u01b0\0\u01cb"
			+ "\0\u01e6\0\u0201\0\u021c\0\u0237\0\u0252\0\u026d\0\u0288\0\u02a3"
			+ "\0\u02be\0\u02d9\0\u02f4\0\u030f\0\u02be\0\u032a\0\u0345\0\66" + "\0\u0360\0\u037b\0\66";

	private static int[] zzUnpackRowMap() {
		int[] result = new int[43];
		int offset = 0;
		offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
		return result;
	}

	private static int zzUnpackRowMap(String packed, int offset, int[] result) {
		int i = 0; /* index in packed string */
		int j = offset; /* index in unpacked array */
		int l = packed.length();
		while (i < l) {
			int high = packed.charAt(i++) << 16;
			result[j++] = high | packed.charAt(i++);
		}
		return j;
	}

	/**
	 * The transition table of the DFA
	 */
	private static final int[] ZZ_TRANS = zzUnpackTrans();
	private static final String ZZ_TRANS_PACKED_0 = "\1\3\2\4\1\5\1\6\3\3\10\4\1\3\1\4"
			+ "\1\3\1\7\1\10\3\4\1\10\1\4\1\11\10\12" + "\1\13\3\12\1\14\4\12\1\15\10\12\1\16\34\0"
			+ "\2\4\5\0\10\4\1\0\1\4\3\0\3\4\1\0" + "\1\4\2\0\2\17\1\0\1\20\3\0\7\17\1\21"
			+ "\1\0\1\17\3\0\1\22\2\17\1\0\1\17\1\0" + "\10\12\1\0\3\12\1\0\4\12\1\0\10\12\12\0"
			+ "\1\23\32\0\1\24\3\0\1\25\36\0\1\26\12\0" + "\2\17\5\0\10\17\1\0\1\17\3\0\3\17\1\0"
			+ "\1\17\2\0\2\17\5\0\10\17\1\0\1\17\3\0" + "\2\17\1\27\1\0\1\17\2\0\2\17\5\0\7\17"
			+ "\1\30\1\0\1\17\3\0\3\17\1\0\1\17\12\0" + "\1\31\33\0\1\32\36\0\1\33\35\0\1\34\12\0"
			+ "\2\17\5\0\10\17\1\0\1\17\3\0\3\17\1\0" + "\1\35\2\0\2\17\5\0\10\17\1\0\1\17\3\0"
			+ "\1\17\1\36\1\17\1\0\1\17\13\0\1\37\40\0" + "\1\40\31\0\1\32\35\0\1\41\11\0\2\17\5\0"
			+ "\10\17\1\0\1\17\3\0\3\17\1\42\1\17\2\0" + "\2\17\5\0\5\17\1\43\2\17\1\0\1\17\3\0"
			+ "\3\17\1\0\1\17\14\0\1\32\4\0\1\40\20\0" + "\1\44\25\0\1\41\1\45\1\0\2\45\12\41\1\45"
			+ "\1\41\1\45\2\0\3\41\1\0\1\41\2\0\2\46" + "\5\0\10\46\1\0\1\46\3\0\3\46\1\0\1\46"
			+ "\2\0\2\17\5\0\10\17\1\0\1\17\3\0\2\17" + "\1\47\1\0\1\17\7\0\1\41\25\0\2\46\5\0"
			+ "\10\46\1\0\1\46\2\0\1\50\3\46\1\0\1\46" + "\2\0\2\17\5\0\10\17\1\0\1\17\3\0\3\17"
			+ "\1\51\1\17\2\0\2\52\5\0\10\52\1\0\1\52" + "\3\0\3\52\1\0\1\52\2\0\2\52\5\0\10\52"
			+ "\1\0\1\52\2\0\1\53\3\52\1\0\1\52\1\0";

	private static int[] zzUnpackTrans() {
		int[] result = new int[918];
		int offset = 0;
		offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
		return result;
	}

	private static int zzUnpackTrans(String packed, int offset, int[] result) {
		int i = 0; /* index in packed string */
		int j = offset; /* index in unpacked array */
		int l = packed.length();
		while (i < l) {
			int count = packed.charAt(i++);
			int value = packed.charAt(i++);
			value--;
			do
				result[j++] = value;
			while (--count > 0);
		}
		return j;
	}

	/* error codes */
	private static final int ZZ_UNKNOWN_ERROR = 0;
	private static final int ZZ_NO_MATCH = 1;
	private static final int ZZ_PUSHBACK_2BIG = 2;
	/* error messages for the codes above */
	private static final String ZZ_ERROR_MSG[] = { "Unkown internal scanner error", "Error: could not match input",
			"Error: pushback value was too large" };
	/**
	 * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
	 */
	private static final int[] ZZ_ATTRIBUTE = zzUnpackAttribute();
	private static final String ZZ_ATTRIBUTE_PACKED_0 = "\2\0\1\11\1\1\2\11\1\1\2\11\4\1\1\11"
			+ "\1\1\1\11\2\1\4\0\2\1\4\0\2\1\2\0" + "\1\1\1\0\1\1\3\0\1\1\1\11\2\0\1\11";

	private static int[] zzUnpackAttribute() {
		int[] result = new int[43];
		int offset = 0;
		offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
		return result;
	}

	private static int zzUnpackAttribute(String packed, int offset, int[] result) {
		int i = 0; /* index in packed string */
		int j = offset; /* index in unpacked array */
		int l = packed.length();
		while (i < l) {
			int count = packed.charAt(i++);
			int value = packed.charAt(i++);
			do
				result[j++] = value;
			while (--count > 0);
		}
		return j;
	}

	/** the input device */
	private java.io.Reader zzReader;
	/** the current state of the DFA */
	private int zzState;
	/** the current lexical state */
	private int zzLexicalState = YYINITIAL;
	/**
	 * this buffer contains the current text to be matched and is the source of the
	 * yytext() string
	 */
	private char zzBuffer[];
	/** the textposition at the last accepting state */
	private int zzMarkedPos;
	/** the current text position in the buffer */
	private int zzCurrentPos;
	/** startRead marks the beginning of the yytext() string in the buffer */
	private int zzStartRead;
	/**
	 * endRead marks the last character in the buffer, that has been read from input
	 */
	private int zzEndRead;
	/** zzAtEOF == true <=> the scanner is at the EOF */
	private boolean zzAtEOF;
	/* user code: */

	/**
	 * Constructor. This must be here because JFlex does not generate a no-parameter
	 * constructor.
	 */
	public LatexTokenMaker() {
	}

	/**
	 * Adds the token specified to the current linked list of tokens.
	 *
	 * @param tokenType The token's type.
	 * @see #addToken(int, int, int)
	 */
	private void addHyperlinkToken(int start, int end, int tokenType) {
		int so = start + offsetShift;
		addToken(zzBuffer, start, end, tokenType, so, true);
	}

	/**
	 * Adds the token specified to the current linked list of tokens.
	 *
	 * @param tokenType The token's type.
	 */
	private void addToken(int tokenType) {
		addToken(zzStartRead, zzMarkedPos - 1, tokenType);
	}

	/**
	 * Adds the token specified to the current linked list of tokens.
	 *
	 * @param tokenType The token's type.
	 * @see #addHyperlinkToken(int, int, int)
	 */
	private void addToken(int start, int end, int tokenType) {
		int so = start + offsetShift;
		addToken(zzBuffer, start, end, tokenType, so, false);
	}

	/**
	 * Adds the token specified to the current linked list of tokens.
	 *
	 * @param array       The character array.
	 * @param start       The starting offset in the array.
	 * @param end         The ending offset in the array.
	 * @param tokenType   The token's type.
	 * @param startOffset The offset in the document at which this token occurs.
	 * @param hyperlink   Whether this token is a hyperlink.
	 */
	@Override
	public void addToken(char[] array, int start, int end, int tokenType, int startOffset, boolean hyperlink) {
		super.addToken(array, start, end, tokenType, startOffset, hyperlink);
		zzStartRead = zzMarkedPos;
	}

	/**
	 * ${inheritDoc}
	 */
	@Override
	public String[] getLineCommentStartAndEnd(int languageIndex) {
		return new String[] { "%", null };
	}

	/**
	 * Returns the first token in the linked list of tokens generated from
	 * <code>text</code>. This method must be implemented by subclasses so they can
	 * correctly implement syntax highlighting.
	 *
	 * @param text             The text from which to get tokens.
	 * @param initialTokenType The token type we should start with.
	 * @param startOffset      The offset into the document at which
	 *                         <code>text</code> starts.
	 * @return The first <code>Token</code> in a linked list representing the syntax
	 *         highlighted text.
	 */
	@Override
	public Token getTokenList(Segment text, int initialTokenType, int startOffset) {
		resetTokenList();
		this.offsetShift = -text.offset + startOffset;
		// Start off in the proper state.
		int state = Token.NULL;
		s = text;
		try {
			yyreset(zzReader);
			yybegin(state);
			return yylex();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return new TokenImpl();
		}
	}

	/**
	 * Refills the input buffer.
	 *
	 * @return <code>true</code> if EOF was reached, otherwise <code>false</code>.
	 * @exception IOException if any I/O-Error occurs.
	 */
	private boolean zzRefill() {
		return zzCurrentPos >= s.offset + s.count;
	}

	/**
	 * Resets the scanner to read from a new input stream. Does not close the old
	 * reader.
	 *
	 * All internal variables are reset, the old input stream <b>cannot</b> be
	 * reused (internal buffer is discarded and lost). Lexical state is set to
	 * <tt>YY_INITIAL</tt>.
	 *
	 * @param reader the new input stream
	 */
	public final void yyreset(java.io.Reader reader) {
		// 's' has been updated.
		zzBuffer = s.array;
		/*
		 * We replaced the line below with the two below it because zzRefill no longer
		 * "refills" the buffer (since the way we do it, it's always "full" the first
		 * time through, since it points to the segment's array). So, we assign
		 * zzEndRead here.
		 */
		// zzStartRead = zzEndRead = s.offset;
		zzStartRead = s.offset;
		zzEndRead = zzStartRead + s.count - 1;
		zzCurrentPos = zzMarkedPos = s.offset;
		zzLexicalState = YYINITIAL;
		zzReader = reader;
		zzAtEOF = false;
	}

	/**
	 * Creates a new scanner There is also a java.io.InputStream version of this
	 * constructor.
	 *
	 * @param in the java.io.Reader to read input from.
	 */
	public LatexTokenMaker(java.io.Reader in) {
		this.zzReader = in;
	}

	/**
	 * Creates a new scanner. There is also java.io.Reader version of this
	 * constructor.
	 *
	 * @param in the java.io.Inputstream to read input from.
	 */
	public LatexTokenMaker(java.io.InputStream in) {
		this(new java.io.InputStreamReader(in));
	}

	/**
	 * Unpacks the compressed character translation table.
	 *
	 * @param packed the packed character translation table
	 * @return the unpacked character translation table
	 */
	private static char[] zzUnpackCMap(String packed) {
		char[] map = new char[0x10000];
		int i = 0; /* index in packed string */
		int j = 0; /* index in unpacked array */
		while (i < 112) {
			int count = packed.charAt(i++);
			char value = packed.charAt(i++);
			do
				map[j++] = value;
			while (--count > 0);
		}
		return map;
	}

	/**
	 * Closes the input stream.
	 */
	public final void yyclose() throws java.io.IOException {
		zzAtEOF = true; /* indicate end of file */
		zzEndRead = zzStartRead; /* invalidate buffer */
		if (zzReader != null)
			zzReader.close();
	}

	/**
	 * Returns the current lexical state.
	 */
	public final int yystate() {
		return zzLexicalState;
	}

	/**
	 * Enters a new lexical state
	 *
	 * @param newState the new lexical state
	 */
	@Override
	public final void yybegin(int newState) {
		zzLexicalState = newState;
	}

	/**
	 * Returns the text matched by the current regular expression.
	 */
	public final String yytext() {
		return new String(zzBuffer, zzStartRead, zzMarkedPos - zzStartRead);
	}

	/**
	 * Returns the character at position <tt>pos</tt> from the matched text.
	 *
	 * It is equivalent to yytext().charAt(pos), but faster
	 *
	 * @param pos the position of the character to fetch. A value from 0 to
	 *            yylength()-1.
	 *
	 * @return the character at position pos
	 */
	public final char yycharat(int pos) {
		return zzBuffer[zzStartRead + pos];
	}

	/**
	 * Returns the length of the matched text region.
	 */
	public final int yylength() {
		return zzMarkedPos - zzStartRead;
	}

	/**
	 * Reports an error that occured while scanning.
	 *
	 * In a wellformed scanner (no or only correct usage of yypushback(int) and a
	 * match-all fallback rule) this method will only be called with things that
	 * "Can't Possibly Happen". If this method is called, something is seriously
	 * wrong (e.g. a JFlex bug producing a faulty scanner etc.).
	 *
	 * Usual syntax/scanner level error handling should be done in error fallback
	 * rules.
	 *
	 * @param errorCode the code of the errormessage to display
	 */
	private void zzScanError(int errorCode) {
		String message;
		try {
			message = ZZ_ERROR_MSG[errorCode];
		} catch (ArrayIndexOutOfBoundsException e) {
			message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
		}
		throw new Error(message);
	}

	/**
	 * Pushes the specified amount of characters back into the input stream.
	 *
	 * They will be read again by then next call of the scanning method
	 *
	 * @param number the number of characters to be read again. This number must not
	 *               be greater than yylength()!
	 */
	public void yypushback(int number) {
		if (number > yylength())
			zzScanError(ZZ_PUSHBACK_2BIG);
		zzMarkedPos -= number;
	}

	/**
	 * Resumes scanning until the next regular expression is matched, the end of
	 * input is encountered or an I/O-Error occurs.
	 *
	 * @return the next token
	 * @exception java.io.IOException if any I/O-Error occurs
	 */
	public org.fife.ui.rsyntaxtextarea.Token yylex() throws java.io.IOException {
		int zzInput;
		int zzAction;
		// cached fields:
		int zzCurrentPosL;
		int zzMarkedPosL;
		int zzEndReadL = zzEndRead;
		char[] zzBufferL = zzBuffer;
		char[] zzCMapL = ZZ_CMAP;
		int[] zzTransL = ZZ_TRANS;
		int[] zzRowMapL = ZZ_ROWMAP;
		int[] zzAttrL = ZZ_ATTRIBUTE;
		while (true) {
			zzMarkedPosL = zzMarkedPos;
			zzAction = -1;
			zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
			zzState = zzLexicalState;
			zzForAction: {
				while (true) {
					if (zzCurrentPosL < zzEndReadL)
						zzInput = zzBufferL[zzCurrentPosL++];
					else if (zzAtEOF) {
						zzInput = YYEOF;
						break zzForAction;
					} else {
						// store back cached positions
						zzCurrentPos = zzCurrentPosL;
						zzMarkedPos = zzMarkedPosL;
						boolean eof = zzRefill();
						// get translated positions and possibly new buffer
						zzCurrentPosL = zzCurrentPos;
						zzMarkedPosL = zzMarkedPos;
						zzBufferL = zzBuffer;
						zzEndReadL = zzEndRead;
						if (eof) {
							zzInput = YYEOF;
							break zzForAction;
						} else {
							zzInput = zzBufferL[zzCurrentPosL++];
						}
					}
					int zzNext = zzTransL[zzRowMapL[zzState] + zzCMapL[zzInput]];
					if (zzNext == -1)
						break zzForAction;
					zzState = zzNext;
					int zzAttributes = zzAttrL[zzState];
					if ((zzAttributes & 1) == 1) {
						zzAction = zzState;
						zzMarkedPosL = zzCurrentPosL;
						if ((zzAttributes & 8) == 8)
							break zzForAction;
					}
				}
			}
			// store back cached position
			zzMarkedPos = zzMarkedPosL;
			switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
			case 1: {
				addToken(Token.IDENTIFIER);
			}
			case 13:
				break;
			case 8: {
				addToken(Token.FUNCTION);
			}
			case 14:
				break;
			case 2: {
				addToken(Token.WHITESPACE);
			}
			case 15:
				break;
			case 12: {
				int temp = zzStartRead;
				addToken(temp, temp + 5, Token.RESERVED_WORD);
				addToken(temp + 6, temp + 6, Token.SEPARATOR);
				addToken(temp + 7, zzMarkedPos - 2, Token.RESERVED_WORD);
				addToken(zzMarkedPos - 1, zzMarkedPos - 1, Token.SEPARATOR);
			}
			case 16:
				break;
			case 10: {
				int temp = zzStartRead;
				addToken(start, zzStartRead - 1, Token.COMMENT_EOL);
				addHyperlinkToken(temp, zzMarkedPos - 1, Token.COMMENT_EOL);
				start = zzMarkedPos;
			}
			case 17:
				break;
			case 3: {
				start = zzMarkedPos - 1;
				yybegin(EOL_COMMENT);
			}
			case 18:
				break;
			case 11: {
				int temp = zzStartRead;
				addToken(temp, temp + 3, Token.RESERVED_WORD);
				addToken(temp + 4, temp + 4, Token.SEPARATOR);
				addToken(temp + 5, zzMarkedPos - 2, Token.RESERVED_WORD);
				addToken(zzMarkedPos - 1, zzMarkedPos - 1, Token.SEPARATOR);
			}
			case 19:
				break;
			case 5: {
				addNullToken();
				return firstToken;
			}
			case 20:
				break;
			case 7: {
				addToken(start, zzStartRead - 1, Token.COMMENT_EOL);
				addNullToken();
				return firstToken;
			}
			case 21:
				break;
			case 9: {
				int temp = zzStartRead;
				addToken(temp, temp, Token.SEPARATOR);
				addToken(temp + 1, temp + 1, Token.IDENTIFIER);
			}
			case 22:
				break;
			case 6: {
			}
			case 23:
				break;
			case 4: {
				addToken(Token.SEPARATOR);
			}
			case 24:
				break;
			default:
				if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
					zzAtEOF = true;
					switch (zzLexicalState) {
					case EOL_COMMENT: {
						addToken(start, zzStartRead - 1, Token.COMMENT_EOL);
						addNullToken();
						return firstToken;
					}
					case 44:
						break;
					case YYINITIAL: {
						addNullToken();
						return firstToken;
					}
					case 45:
						break;
					default:
						return null;
					}
				} else {
					zzScanError(ZZ_NO_MATCH);
				}
			}
		}
	}
}
