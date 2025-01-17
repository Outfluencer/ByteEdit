/* The following code was generated by JFlex 1.4.1 on 10/16/06 10:31 AM */
/*
 * 07/14/2006
 * LuaTokenMaker.java - Scanner for the Lua programming language.
 * This library is distributed under a modified BSD license. See the included
 * RSyntaxTextArea.License.txt file for details.
 */
package org.fife.ui.rsyntaxtextarea.modes;

import java.io.IOException;
import java.io.Reader;

import javax.swing.text.Segment;

import org.fife.ui.rsyntaxtextarea.AbstractJFlexTokenMaker;
import org.fife.ui.rsyntaxtextarea.Token;
import org.fife.ui.rsyntaxtextarea.TokenImpl;

/**
 * Scanner for the Lua programming language.
 * <p>
 *
 * This implementation was created using
 * <a href="http://www.jflex.de/">JFlex</a> 1.4.1; however, the generated file
 * was modified for performance. Memory allocation needs to be almost completely
 * removed to be competitive with the handwritten lexers (subclasses of
 * <code>AbstractTokenMaker</code>, so this class has been modified so that
 * Strings are never allocated (via yytext()), and the scanner never has to
 * worry about refilling its buffer (needlessly copying chars around). We can
 * achieve this because RText always scans exactly 1 line of tokens at a time,
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
 * <li>The generated <code>LuaTokenMaker.java</code> file will contain two
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
 * @version 0.4
 *
 */
public class LuaTokenMaker extends AbstractJFlexTokenMaker {

	/** This character denotes the end of file */
	public static final int YYEOF = -1;
	/** lexical states */
	public static final int YYINITIAL = 0;
	public static final int LONGSTRING = 2;
	public static final int LINECOMMENT = 3;
	public static final int MLC = 1;
	/**
	 * Translates characters to character classes
	 */
	private static final String ZZ_CMAP_PACKED = "\11\0\1\4\1\3\1\0\1\4\23\0\1\4\1\0\1\7\1\27"
			+ "\1\0\1\27\1\0\1\5\2\26\1\27\1\15\1\0\1\12\1\13" + "\1\27\12\2\2\0\1\30\1\32\1\31\2\0\4\1\1\14\1\1"
			+ "\1\54\1\1\1\60\2\1\1\52\1\1\1\50\1\61\2\1\1\56" + "\1\57\1\1\1\51\1\55\4\1\1\10\1\6\1\11\1\27\1\53"
			+ "\1\0\1\23\1\37\1\42\1\35\1\21\1\22\1\47\1\44\1\41" + "\1\1\1\40\1\24\1\46\1\34\1\36\1\43\1\64\1\17\1\25"
			+ "\1\16\1\20\1\62\1\45\1\63\1\65\1\1\1\26\1\0\1\26" + "\1\33\uff81\0";
	/**
	 * Translates characters to character classes
	 */
	private static final char[] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);
	/**
	 * Translates DFA states to action switch labels.
	 */
	private static final int[] ZZ_ACTION = zzUnpackAction();
	private static final String ZZ_ACTION_PACKED_0 = "\4\0\2\1\1\2\1\3\1\4\1\5\1\6\2\7"
			+ "\1\10\1\1\1\10\10\1\2\10\16\1\1\11\1\12" + "\1\11\1\13\2\11\1\14\2\2\1\15\1\5\1\6"
			+ "\1\16\1\17\1\20\1\10\21\1\3\0\3\1\1\21" + "\1\10\1\1\1\21\11\1\1\22\2\1\1\23\1\24"
			+ "\1\15\1\16\1\0\24\1\4\0\13\1\1\25\1\26" + "\11\1\1\21\2\1\1\22\3\1\4\0\6\1\1\27"
			+ "\13\1\1\27\2\0\10\1\2\0\15\1";

	private static int[] zzUnpackAction() {
		int[] result = new int[201];
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
	private static final String ZZ_ROWMAP_PACKED_0 = "\0\0\0\66\0\154\0\242\0\330\0\u010e\0\u0144\0\330"
			+ "\0\u017a\0\u01b0\0\u01e6\0\u021c\0\330\0\u0252\0\u0288\0\330"
			+ "\0\u02be\0\u02f4\0\u032a\0\u0360\0\u0396\0\u03cc\0\u0402\0\u0438"
			+ "\0\u046e\0\u04a4\0\u04a4\0\u04da\0\u0510\0\u0546\0\u057c\0\u05b2"
			+ "\0\u05e8\0\u061e\0\u0654\0\u068a\0\u06c0\0\u06f6\0\u072c\0\u0762"
			+ "\0\u0798\0\330\0\u07ce\0\330\0\u0804\0\u083a\0\330\0\u0870"
			+ "\0\u08a6\0\330\0\u08dc\0\u0912\0\330\0\330\0\u0948\0\u097e"
			+ "\0\u09b4\0\u09ea\0\u0a20\0\u0a56\0\u0a8c\0\u0ac2\0\u0af8\0\u0b2e"
			+ "\0\u0b64\0\u0b9a\0\u0bd0\0\u0c06\0\u0c3c\0\u0c72\0\u0ca8\0\u0cde"
			+ "\0\u0d14\0\u0d4a\0\u0d80\0\u0db6\0\u0dec\0\u0e22\0\u0e58\0\u0e8e"
			+ "\0\u010e\0\u0ec4\0\u010e\0\u0efa\0\u0f30\0\u0f66\0\u0f9c\0\u0fd2"
			+ "\0\u1008\0\u103e\0\u1074\0\u10aa\0\u010e\0\u10e0\0\u1116\0\330"
			+ "\0\330\0\u01b0\0\u01e6\0\u114c\0\u1182\0\u11b8\0\u11ee\0\u1224"
			+ "\0\u125a\0\u1290\0\u12c6\0\u12fc\0\u1332\0\u1368\0\u139e\0\u13d4"
			+ "\0\u140a\0\u1440\0\u1476\0\u14ac\0\u14e2\0\u1518\0\u154e\0\u1584"
			+ "\0\u15ba\0\u15f0\0\u1626\0\u165c\0\u1692\0\u16c8\0\u16fe\0\u1734"
			+ "\0\u176a\0\u17a0\0\u17d6\0\u180c\0\u1842\0\u1878\0\u18ae\0\330"
			+ "\0\u010e\0\u18e4\0\u191a\0\u1950\0\u1986\0\u19bc\0\u19f2\0\u1a28"
			+ "\0\u1a5e\0\u1a94\0\u1aca\0\u1b00\0\u1b36\0\u1b6c\0\u1ba2\0\u1bd8"
			+ "\0\u1c0e\0\u1c44\0\u1c7a\0\u1cb0\0\u1ce6\0\u1d1c\0\u1d52\0\u1d88"
			+ "\0\u1dbe\0\u1df4\0\u1e2a\0\u010e\0\u1e60\0\u1e96\0\u1ecc\0\u1f02"
			+ "\0\u1f38\0\u1f6e\0\u1fa4\0\u1fda\0\u2010\0\u2046\0\u207c\0\330"
			+ "\0\u20b2\0\u20e8\0\u211e\0\u2154\0\u218a\0\u21c0\0\u21f6\0\u222c"
			+ "\0\u2262\0\u2298\0\u22ce\0\u2304\0\u233a\0\u2370\0\u23a6\0\u23dc"
			+ "\0\u2412\0\u2448\0\u247e\0\u24b4\0\u24ea\0\u2520\0\u2556\0\u258c" + "\0\u25c2";

	private static int[] zzUnpackRowMap() {
		int[] result = new int[201];
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
	private static final String ZZ_TRANS_PACKED_0 = "\1\5\1\6\1\7\1\10\1\11\1\12\1\5\1\13"
			+ "\1\14\1\15\1\16\1\17\1\6\1\20\1\21\1\22" + "\1\23\1\24\1\25\1\26\1\27\1\30\1\15\1\20"
			+ "\1\31\1\32\2\33\1\34\1\35\1\36\1\37\1\6" + "\1\40\1\41\1\42\1\6\1\43\1\44\1\45\1\46"
			+ "\2\6\1\47\7\6\1\50\2\6\3\51\1\52\5\51" + "\1\53\57\51\1\54\5\51\1\55\54\51\3\56\1\57"
			+ "\62\56\67\0\2\6\11\0\1\6\1\0\10\6\6\0" + "\32\6\1\0\1\60\1\7\10\0\1\7\1\61\1\0"
			+ "\3\60\1\61\4\60\6\0\32\60\4\0\1\11\61\0" + "\3\12\1\0\1\12\1\62\1\63\57\12\3\13\1\0"
			+ "\2\13\1\64\1\65\56\13\10\0\1\66\67\0\1\67" + "\55\0\1\7\10\0\1\70\53\0\2\6\11\0\1\6"
			+ "\1\0\1\6\1\71\6\6\6\0\2\6\1\72\5\6" + "\1\73\20\6\1\74\1\0\2\6\11\0\1\6\1\0"
			+ "\3\6\1\75\1\6\1\76\2\6\6\0\32\6\1\0" + "\2\6\11\0\1\6\1\0\10\6\6\0\1\77\31\6"
			+ "\1\0\2\6\11\0\1\6\1\0\1\6\1\100\4\6" + "\1\101\1\6\6\0\1\102\31\6\1\0\2\6\11\0"
			+ "\1\6\1\0\2\6\1\103\2\6\1\104\2\6\6\0" + "\2\6\1\105\27\6\1\0\2\6\11\0\1\6\1\0"
			+ "\7\6\1\106\6\0\1\107\31\6\1\0\2\6\11\0" + "\1\6\1\0\10\6\6\0\2\6\1\110\27\6\1\0"
			+ "\2\6\11\0\1\6\1\0\3\6\1\111\4\6\6\0" + "\32\6\21\0\1\112\3\0\1\113\4\0\1\20\1\0"
			+ "\1\114\63\0\1\20\34\0\2\6\11\0\1\6\1\0" + "\3\6\1\115\4\6\6\0\2\6\1\116\2\6\1\117"
			+ "\24\6\1\0\2\6\11\0\1\6\1\0\10\6\6\0" + "\2\6\1\120\27\6\1\0\2\6\11\0\1\6\1\0"
			+ "\1\6\1\121\6\6\6\0\32\6\1\0\2\6\11\0" + "\1\6\1\0\1\6\1\122\6\6\6\0\32\6\1\0"
			+ "\2\6\11\0\1\6\1\0\4\6\1\123\3\6\6\0" + "\7\6\1\124\22\6\1\0\2\6\11\0\1\6\1\0"
			+ "\10\6\6\0\2\6\1\125\27\6\1\0\2\6\11\0" + "\1\6\1\0\1\6\1\126\3\6\1\127\2\6\6\0"
			+ "\6\6\1\130\23\6\1\0\2\6\11\0\1\6\1\0" + "\10\6\6\0\10\6\1\131\21\6\1\0\2\6\11\0"
			+ "\1\6\1\0\10\6\6\0\2\6\1\132\27\6\1\0" + "\2\6\11\0\1\6\1\0\3\6\1\133\4\6\6\0"
			+ "\32\6\1\0\2\6\11\0\1\6\1\0\10\6\6\0" + "\15\6\1\134\14\6\1\0\2\6\11\0\1\6\1\0"
			+ "\10\6\6\0\20\6\1\135\1\136\10\6\1\0\2\6" + "\11\0\1\6\1\0\10\6\6\0\7\6\1\137\22\6"
			+ "\3\51\1\0\5\51\1\0\54\51\11\0\1\140\65\0" + "\1\141\54\0\3\56\1\0\62\56\1\0\2\60\11\0"
			+ "\1\60\1\0\10\60\6\0\32\60\1\0\2\60\7\0" + "\1\60\1\0\12\60\6\0\32\60\3\12\1\0\1\12"
			+ "\1\142\1\63\57\12\3\13\1\0\2\13\1\64\1\143" + "\56\13\10\0\1\144\70\0\1\20\53\0\2\6\11\0"
			+ "\1\6\1\0\2\6\1\145\5\6\6\0\32\6\1\0" + "\2\6\11\0\1\6\1\0\7\6\1\146\6\0\1\147"
			+ "\31\6\1\0\2\6\11\0\1\6\1\0\3\6\1\150" + "\4\6\6\0\32\6\1\0\2\6\11\0\1\6\1\0"
			+ "\10\6\6\0\7\6\1\151\22\6\1\0\2\6\11\0" + "\1\6\1\0\1\152\7\6\6\0\7\6\1\153\20\6"
			+ "\1\154\1\6\1\0\2\6\11\0\1\6\1\0\10\6" + "\6\0\11\6\1\155\20\6\1\0\2\6\11\0\1\6"
			+ "\1\0\1\156\7\6\6\0\7\6\1\157\22\6\1\0" + "\2\6\11\0\1\6\1\0\1\6\1\160\6\6\6\0"
			+ "\32\6\1\0\2\6\11\0\1\6\1\0\7\6\1\161" + "\6\0\32\6\1\0\2\6\11\0\1\6\1\0\10\6"
			+ "\6\0\1\6\1\123\30\6\1\0\2\6\11\0\1\6" + "\1\0\10\6\6\0\1\162\31\6\1\0\2\6\11\0"
			+ "\1\6\1\0\6\6\1\163\1\6\6\0\32\6\1\0" + "\2\6\11\0\1\6\1\0\1\6\1\123\6\6\6\0"
			+ "\32\6\1\0\2\6\11\0\1\6\1\0\7\6\1\164" + "\6\0\32\6\1\0\2\6\11\0\1\6\1\0\10\6"
			+ "\6\0\1\6\1\121\30\6\1\0\2\6\11\0\1\6" + "\1\0\5\6\1\165\2\6\6\0\6\6\1\166\23\6"
			+ "\1\0\2\6\11\0\1\6\1\0\1\167\5\6\1\170" + "\1\6\6\0\32\6\36\0\1\171\45\0\1\172\67\0"
			+ "\1\173\2\0\1\174\43\0\2\6\11\0\1\6\1\0" + "\10\6\6\0\27\6\1\175\2\6\1\0\2\6\11\0"
			+ "\1\6\1\0\1\121\7\6\6\0\32\6\1\0\2\6" + "\11\0\1\6\1\0\6\6\1\123\1\6\6\0\32\6"
			+ "\1\0\2\6\11\0\1\6\1\0\4\6\1\176\3\6" + "\6\0\32\6\1\0\2\6\11\0\1\6\1\0\3\6"
			+ "\1\177\4\6\6\0\32\6\1\0\2\6\11\0\1\6" + "\1\0\5\6\1\127\2\6\6\0\32\6\1\0\2\6"
			+ "\11\0\1\6\1\0\6\6\1\200\1\6\6\0\32\6" + "\1\0\2\6\11\0\1\6\1\0\10\6\6\0\5\6"
			+ "\1\201\24\6\1\0\2\6\11\0\1\6\1\0\10\6" + "\6\0\5\6\1\202\24\6\1\0\2\6\11\0\1\6"
			+ "\1\0\5\6\1\203\2\6\6\0\32\6\1\0\2\6" + "\11\0\1\6\1\0\10\6\6\0\5\6\1\204\24\6"
			+ "\1\0\2\6\11\0\1\6\1\0\10\6\6\0\1\6" + "\1\205\30\6\1\0\2\6\11\0\1\6\1\0\1\167"
			+ "\7\6\6\0\32\6\1\0\2\6\11\0\1\6\1\0" + "\10\6\6\0\16\6\1\206\13\6\1\0\2\6\11\0"
			+ "\1\207\1\0\10\6\6\0\32\6\1\0\2\6\11\0" + "\1\6\1\0\10\6\6\0\6\6\1\130\23\6\10\0"
			+ "\1\210\56\0\2\6\11\0\1\6\1\0\3\6\1\211" + "\4\6\6\0\32\6\1\0\2\6\11\0\1\6\1\0"
			+ "\1\212\7\6\6\0\32\6\1\0\2\6\11\0\1\6" + "\1\0\2\6\1\213\5\6\6\0\32\6\1\0\2\6"
			+ "\11\0\1\6\1\0\10\6\6\0\1\123\31\6\1\0" + "\2\6\11\0\1\6\1\0\3\6\1\135\4\6\6\0"
			+ "\32\6\1\0\2\6\11\0\1\6\1\0\2\6\1\214" + "\5\6\6\0\32\6\1\0\2\6\11\0\1\6\1\0"
			+ "\3\6\1\215\4\6\6\0\32\6\1\0\2\6\11\0" + "\1\6\1\0\2\6\1\216\5\6\6\0\32\6\1\0"
			+ "\2\6\11\0\1\6\1\0\3\6\1\217\3\6\1\220" + "\6\0\13\6\1\220\16\6\1\0\2\6\11\0\1\6"
			+ "\1\0\10\6\6\0\5\6\1\117\24\6\1\0\2\6" + "\11\0\1\6\1\0\5\6\1\221\2\6\6\0\32\6"
			+ "\1\0\2\6\11\0\1\6\1\0\10\6\6\0\2\6" + "\1\222\27\6\1\0\2\6\11\0\1\6\1\0\3\6"
			+ "\1\223\4\6\6\0\32\6\1\0\2\6\11\0\1\6" + "\1\0\10\6\6\0\6\6\1\224\23\6\1\0\2\6"
			+ "\11\0\1\6\1\0\7\6\1\145\6\0\32\6\1\0" + "\2\6\11\0\1\6\1\0\3\6\1\225\4\6\6\0"
			+ "\32\6\1\0\2\6\11\0\1\6\1\0\10\6\6\0" + "\1\6\1\226\30\6\1\0\2\6\11\0\1\6\1\0"
			+ "\5\6\1\117\2\6\6\0\32\6\1\0\2\6\11\0" + "\1\6\1\0\4\6\1\227\3\6\6\0\12\6\1\230"
			+ "\17\6\1\0\2\6\11\0\1\6\1\0\3\6\1\231" + "\4\6\6\0\32\6\22\0\1\232\62\0\1\233\114\0"
			+ "\1\234\65\0\1\235\20\0\2\6\11\0\1\6\1\0" + "\1\135\7\6\6\0\32\6\1\0\2\6\11\0\1\6"
			+ "\1\0\10\6\6\0\5\6\1\236\24\6\1\0\2\6" + "\11\0\1\6\1\0\5\6\1\237\2\6\6\0\32\6"
			+ "\1\0\2\6\11\0\1\6\1\0\6\6\1\240\1\6" + "\6\0\32\6\1\0\2\6\11\0\1\6\1\0\10\6"
			+ "\6\0\1\175\31\6\1\0\2\6\11\0\1\6\1\0" + "\1\6\1\241\6\6\6\0\32\6\1\0\2\6\11\0"
			+ "\1\6\1\0\6\6\1\242\1\6\6\0\32\6\1\0" + "\2\6\11\0\1\6\1\0\6\6\1\243\1\6\6\0"
			+ "\32\6\1\0\2\6\11\0\1\6\1\0\2\6\1\236" + "\5\6\6\0\32\6\1\0\2\6\11\0\1\6\1\0"
			+ "\10\6\6\0\16\6\1\244\13\6\1\0\2\6\11\0" + "\1\6\1\0\10\6\6\0\22\6\1\245\7\6\1\0"
			+ "\2\6\11\0\1\6\1\0\1\6\1\246\6\6\6\0" + "\32\6\1\0\2\6\11\0\1\6\1\0\10\6\6\0"
			+ "\12\6\1\247\17\6\1\0\2\6\11\0\1\6\1\0" + "\1\6\1\150\6\6\6\0\32\6\1\0\2\6\11\0"
			+ "\1\6\1\0\5\6\1\250\2\6\6\0\32\6\1\0" + "\2\6\11\0\1\6\1\0\10\6\6\0\5\6\1\251"
			+ "\24\6\1\0\2\6\11\0\1\6\1\0\10\6\6\0" + "\30\6\1\252\1\6\1\0\2\6\11\0\1\6\1\0"
			+ "\3\6\1\175\4\6\6\0\32\6\1\0\2\6\11\0" + "\1\6\1\0\10\6\6\0\6\6\1\253\23\6\1\0"
			+ "\2\6\11\0\1\6\1\0\1\6\1\135\6\6\6\0" + "\32\6\1\0\2\6\11\0\1\6\1\0\10\6\6\0"
			+ "\5\6\1\254\24\6\1\0\2\6\11\0\1\6\1\0" + "\1\255\7\6\6\0\32\6\1\0\2\6\11\0\1\6"
			+ "\1\0\1\6\1\175\6\6\6\0\32\6\1\0\2\6" + "\11\0\1\6\1\0\4\6\1\176\2\6\1\146\6\0"
			+ "\32\6\1\0\2\6\11\0\1\6\1\0\3\6\1\256" + "\4\6\6\0\32\6\1\0\2\6\11\0\1\6\1\0"
			+ "\3\6\1\257\4\6\6\0\32\6\1\0\2\6\11\0" + "\1\6\1\0\10\6\6\0\6\6\1\175\23\6\31\0"
			+ "\1\260\75\0\1\261\63\0\1\262\47\0\1\232\45\0" + "\2\6\11\0\1\6\1\0\6\6\1\151\1\6\6\0"
			+ "\32\6\1\0\2\6\11\0\1\6\1\0\10\6\6\0" + "\4\6\1\123\25\6\1\0\2\6\11\0\1\6\1\0"
			+ "\3\6\1\263\4\6\6\0\32\6\1\0\2\6\11\0" + "\1\6\1\0\7\6\1\135\6\0\32\6\1\0\2\6"
			+ "\11\0\1\6\1\0\6\6\1\135\1\6\6\0\32\6" + "\1\0\2\6\11\0\1\6\1\0\3\6\1\123\4\6"
			+ "\6\0\32\6\1\0\2\6\11\0\1\6\1\0\10\6" + "\6\0\23\6\1\264\6\6\1\0\2\6\11\0\1\6"
			+ "\1\0\10\6\6\0\5\6\1\265\24\6\1\0\2\6" + "\11\0\1\6\1\0\10\6\6\0\3\6\1\266\26\6"
			+ "\1\0\2\6\11\0\1\6\1\0\1\123\7\6\6\0" + "\32\6\1\0\2\6\11\0\1\6\1\0\1\6\1\151"
			+ "\6\6\6\0\32\6\1\0\2\6\11\0\1\6\1\0" + "\2\6\1\267\5\6\6\0\32\6\1\0\2\6\11\0"
			+ "\1\6\1\0\10\6\6\0\4\6\1\135\25\6\1\0" + "\2\6\11\0\1\6\1\0\4\6\1\123\3\6\6\0"
			+ "\32\6\1\0\2\6\11\0\1\6\1\0\10\6\6\0" + "\5\6\1\270\24\6\1\0\2\6\11\0\1\6\1\0"
			+ "\10\6\6\0\1\271\31\6\1\0\2\6\11\0\1\6" + "\1\0\1\272\7\6\6\0\32\6\34\0\1\273\52\0"
			+ "\1\274\45\0\2\6\11\0\1\6\1\0\10\6\6\0" + "\6\6\1\275\23\6\1\0\2\6\11\0\1\6\1\0"
			+ "\10\6\6\0\24\6\1\276\5\6\1\0\2\6\11\0" + "\1\6\1\0\10\6\6\0\1\277\31\6\1\0\2\6"
			+ "\11\0\1\6\1\0\3\6\1\222\4\6\6\0\32\6" + "\1\0\2\6\11\0\1\6\1\0\5\6\1\242\2\6"
			+ "\6\0\32\6\1\0\2\6\11\0\1\6\1\0\10\6" + "\6\0\2\6\1\150\27\6\1\0\2\6\11\0\1\6"
			+ "\1\0\10\6\6\0\26\6\1\135\3\6\1\0\2\6" + "\11\0\1\6\1\0\5\6\1\300\2\6\6\0\32\6"
			+ "\47\0\1\232\35\0\1\232\47\0\2\6\11\0\1\6" + "\1\0\1\301\7\6\6\0\32\6\1\0\2\6\11\0"
			+ "\1\6\1\0\10\6\6\0\25\6\1\302\4\6\1\0" + "\2\6\11\0\1\6\1\0\10\6\6\0\13\6\1\135"
			+ "\16\6\1\0\2\6\11\0\1\6\1\0\1\303\7\6" + "\6\0\32\6\1\0\2\6\11\0\1\6\1\0\10\6"
			+ "\6\0\13\6\1\304\16\6\1\0\2\6\11\0\1\6" + "\1\0\10\6\6\0\14\6\1\135\15\6\1\0\2\6"
			+ "\11\0\1\6\1\0\5\6\1\305\2\6\6\0\32\6" + "\1\0\2\6\11\0\1\6\1\0\5\6\1\306\2\6"
			+ "\6\0\32\6\1\0\2\6\11\0\1\6\1\0\10\6" + "\6\0\3\6\1\236\26\6\1\0\2\6\11\0\1\6"
			+ "\1\0\1\6\1\307\6\6\6\0\32\6\1\0\2\6" + "\11\0\1\6\1\0\10\6\6\0\3\6\1\310\26\6"
			+ "\1\0\2\6\11\0\1\6\1\0\5\6\1\311\2\6" + "\6\0\32\6\1\0\2\6\11\0\1\6\1\0\10\6" + "\6\0\13\6\1\151\16\6";

	private static int[] zzUnpackTrans() {
		int[] result = new int[9720];
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
	private static final String ZZ_ATTRIBUTE_PACKED_0 = "\4\0\1\11\2\1\1\11\4\1\1\11\2\1\1\11"
			+ "\31\1\1\11\1\1\1\11\2\1\1\11\2\1\1\11" + "\2\1\2\11\23\1\3\0\23\1\2\11\2\1\1\0"
			+ "\24\1\4\0\13\1\1\11\21\1\4\0\22\1\1\11" + "\2\0\10\1\2\0\15\1";

	private static int[] zzUnpackAttribute() {
		int[] result = new int[201];
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
	public LuaTokenMaker() {
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
	 */
	private void addToken(int start, int end, int tokenType) {
		int so = start + offsetShift;
		addToken(zzBuffer, start, end, tokenType, so);
	}

	/**
	 * Adds the token specified to the current linked list of tokens.
	 *
	 * @param array       The character array.
	 * @param start       The starting offset in the array.
	 * @param end         The ending offset in the array.
	 * @param tokenType   The token's type.
	 * @param startOffset The offset in the document at which this token occurs.
	 */
	@Override
	public void addToken(char[] array, int start, int end, int tokenType, int startOffset) {
		super.addToken(array, start, end, tokenType, startOffset);
		zzStartRead = zzMarkedPos;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String[] getLineCommentStartAndEnd(int languageIndex) {
		return new String[] { "--", null };
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
		switch (initialTokenType) {
		case Token.COMMENT_MULTILINE:
			state = MLC;
			start = text.offset;
			break;
		case Token.LITERAL_STRING_DOUBLE_QUOTE:
			state = LONGSTRING;
			start = text.offset;
			break;
		default:
			state = Token.NULL;
		}
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
	public final void yyreset(Reader reader) {
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
	public LuaTokenMaker(java.io.Reader in) {
		this.zzReader = in;
	}

	/**
	 * Creates a new scanner. There is also java.io.Reader version of this
	 * constructor.
	 *
	 * @param in the java.io.Inputstream to read input from.
	 */
	public LuaTokenMaker(java.io.InputStream in) {
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
		while (i < 164) {
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
			case 3: {
				addNullToken();
				return firstToken;
			}
			case 24:
				break;
			case 13: {
				addToken(Token.LITERAL_CHAR);
			}
			case 25:
				break;
			case 15: {
				start = zzMarkedPos - 2;
				yybegin(LONGSTRING);
			}
			case 26:
				break;
			case 19: {
				yybegin(YYINITIAL);
				addToken(start, zzStartRead + 1, Token.COMMENT_MULTILINE);
			}
			case 27:
				break;
			case 4: {
				addToken(Token.WHITESPACE);
			}
			case 28:
				break;
			case 2: {
				addToken(Token.LITERAL_NUMBER_FLOAT);
			}
			case 29:
				break;
			case 17: {
				addToken(Token.RESERVED_WORD);
			}
			case 30:
				break;
			case 21: {
				start = zzMarkedPos - 4;
				yybegin(MLC);
			}
			case 31:
				break;
			case 7: {
				addToken(Token.SEPARATOR);
			}
			case 32:
				break;
			case 1: {
				addToken(Token.IDENTIFIER);
			}
			case 33:
				break;
			case 18: {
				addToken(Token.FUNCTION);
			}
			case 34:
				break;
			case 5: {
				addToken(Token.ERROR_CHAR);
				addNullToken();
				return firstToken;
			}
			case 35:
				break;
			case 6: {
				addToken(Token.ERROR_STRING_DOUBLE);
				addNullToken();
				return firstToken;
			}
			case 36:
				break;
			case 23: {
				addToken(Token.DATA_TYPE);
			}
			case 37:
				break;
			case 22: {
				addToken(Token.LITERAL_BOOLEAN);
			}
			case 38:
				break;
			case 20: {
				yybegin(YYINITIAL);
				addToken(start, zzStartRead + 1, Token.LITERAL_STRING_DOUBLE_QUOTE);
			}
			case 39:
				break;
			case 14: {
				addToken(Token.LITERAL_STRING_DOUBLE_QUOTE);
			}
			case 40:
				break;
			case 11: {
				addToken(start, zzStartRead - 1, Token.LITERAL_STRING_DOUBLE_QUOTE);
				return firstToken;
			}
			case 41:
				break;
			case 12: {
				addToken(start, zzStartRead - 1, Token.COMMENT_EOL);
				return firstToken;
			}
			case 42:
				break;
			case 8: {
				addToken(Token.OPERATOR);
			}
			case 43:
				break;
			case 16: {
				start = zzMarkedPos - 2;
				yybegin(LINECOMMENT);
			}
			case 44:
				break;
			case 9: {
			}
			case 45:
				break;
			case 10: {
				addToken(start, zzStartRead - 1, Token.COMMENT_MULTILINE);
				return firstToken;
			}
			case 46:
				break;
			default:
				if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
					zzAtEOF = true;
					switch (zzLexicalState) {
					case YYINITIAL: {
						addNullToken();
						return firstToken;
					}
					case 202:
						break;
					case LONGSTRING: {
						addToken(start, zzStartRead - 1, Token.LITERAL_STRING_DOUBLE_QUOTE);
						return firstToken;
					}
					case 203:
						break;
					case LINECOMMENT: {
						addToken(start, zzStartRead - 1, Token.COMMENT_EOL);
						return firstToken;
					}
					case 204:
						break;
					case MLC: {
						addToken(start, zzStartRead - 1, Token.COMMENT_MULTILINE);
						return firstToken;
					}
					case 205:
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
