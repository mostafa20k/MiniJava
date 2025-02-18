package gen;// Generated from D:/Atoosi/Final_project/Grammar\MiniJava.g4 by ANTLR 4.9.1
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MiniJavaLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.9.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, AND=23, LT=24, PLUS=25, 
		MINUS=26, TIMES=27, POWER=28, NOT=29, LSB=30, RSB=31, DOTLENGTH=32, LP=33, 
		RP=34, RETURN=35, EQ=36, Final=37, BooleanLiteral=38, IntegerLiteral=39, 
		NullLiteral=40, Identifier=41, String=42, Override=43, WS=44, MULTILINE_COMMENT=45, 
		LINE_COMMENT=46;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
			"T__9", "T__10", "T__11", "T__12", "T__13", "T__14", "T__15", "T__16", 
			"T__17", "T__18", "T__19", "T__20", "T__21", "AND", "LT", "PLUS", "MINUS", 
			"TIMES", "POWER", "NOT", "LSB", "RSB", "DOTLENGTH", "LP", "RP", "RETURN", 
			"EQ", "Final", "BooleanLiteral", "IntegerLiteral", "NullLiteral", "Identifier", 
			"String", "Override", "JavaLetter", "JavaLetterOrDigit", "DecimalIntegerLiteral", 
			"IntegertypeSuffix", "DecimalNumeral", "Digits", "Digit", "NonZeroDigit", 
			"DigitsAndUnderscores", "DigitOrUnderscore", "Underscores", "WS", "MULTILINE_COMMENT", 
			"LINE_COMMENT"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'class'", "'{'", "'}'", "'public'", "'static'", "'void'", "'main'", 
			"'inherits'", "'implements'", "','", "'interface'", "';'", "'boolean'", 
			"'number'", "'private'", "'if'", "'else'", "'while'", "'print'", "'.'", 
			"'new'", "'this'", "'&&'", "'<'", "'+'", "'-'", "'*'", "'**'", "'<>'", 
			"'['", "']'", "'.length'", "'('", "')'", "'ret'", "'='", "'final'", null, 
			null, "'null'", null, null, "'@Override'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, "AND", 
			"LT", "PLUS", "MINUS", "TIMES", "POWER", "NOT", "LSB", "RSB", "DOTLENGTH", 
			"LP", "RP", "RETURN", "EQ", "Final", "BooleanLiteral", "IntegerLiteral", 
			"NullLiteral", "Identifier", "String", "Override", "WS", "MULTILINE_COMMENT", 
			"LINE_COMMENT"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public MiniJavaLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "MiniJava.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\60\u019b\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t"+
		"+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64"+
		"\t\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\3\2\3\2\3\2\3\2"+
		"\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3"+
		"\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t"+
		"\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13"+
		"\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\16\3\16\3\16\3\16\3"+
		"\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3"+
		"\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\23\3"+
		"\23\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3\24\3\25\3\25\3\26\3"+
		"\26\3\26\3\26\3\27\3\27\3\27\3\27\3\27\3\30\3\30\3\30\3\31\3\31\3\32\3"+
		"\32\3\33\3\33\3\34\3\34\3\35\3\35\3\35\3\36\3\36\3\36\3\37\3\37\3 \3 "+
		"\3!\3!\3!\3!\3!\3!\3!\3!\3\"\3\"\3#\3#\3$\3$\3$\3$\3%\3%\3&\3&\3&\3&\3"+
		"&\3&\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\5\'\u0126\n\'\3(\3(\3)\3)\3)"+
		"\3)\3)\3*\3*\7*\u0131\n*\f*\16*\u0134\13*\3+\3+\7+\u0138\n+\f+\16+\u013b"+
		"\13+\3+\3+\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3-\3-\3.\3.\3/\3/\5/\u014f\n"+
		"/\3\60\3\60\3\61\3\61\3\61\5\61\u0156\n\61\3\61\3\61\3\61\5\61\u015b\n"+
		"\61\5\61\u015d\n\61\3\62\3\62\5\62\u0161\n\62\3\62\5\62\u0164\n\62\3\63"+
		"\3\63\5\63\u0168\n\63\3\64\3\64\3\65\6\65\u016d\n\65\r\65\16\65\u016e"+
		"\3\66\3\66\5\66\u0173\n\66\3\67\6\67\u0176\n\67\r\67\16\67\u0177\38\6"+
		"8\u017b\n8\r8\168\u017c\38\38\39\39\39\39\79\u0185\n9\f9\169\u0188\13"+
		"9\39\39\39\39\39\3:\3:\3:\3:\7:\u0193\n:\f:\16:\u0196\13:\3:\3:\3:\3:"+
		"\4\u0186\u0194\2;\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31"+
		"\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65"+
		"\34\67\359\36;\37= ?!A\"C#E$G%I&K\'M(O)Q*S+U,W-Y\2[\2]\2_\2a\2c\2e\2g"+
		"\2i\2k\2m\2o.q/s\60\3\2\b\3\2$$\6\2&&C\\aac|\7\2&&\62;C\\aac|\4\2NNnn"+
		"\3\2\63;\5\2\13\f\17\17\"\"\2\u019f\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2"+
		"\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23"+
		"\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2"+
		"\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2"+
		"\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3"+
		"\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2"+
		"\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2"+
		"\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2o\3\2\2\2\2q"+
		"\3\2\2\2\2s\3\2\2\2\3u\3\2\2\2\5{\3\2\2\2\7}\3\2\2\2\t\177\3\2\2\2\13"+
		"\u0086\3\2\2\2\r\u008d\3\2\2\2\17\u0092\3\2\2\2\21\u0097\3\2\2\2\23\u00a0"+
		"\3\2\2\2\25\u00ab\3\2\2\2\27\u00ad\3\2\2\2\31\u00b7\3\2\2\2\33\u00b9\3"+
		"\2\2\2\35\u00c1\3\2\2\2\37\u00c8\3\2\2\2!\u00d0\3\2\2\2#\u00d3\3\2\2\2"+
		"%\u00d8\3\2\2\2\'\u00de\3\2\2\2)\u00e4\3\2\2\2+\u00e6\3\2\2\2-\u00ea\3"+
		"\2\2\2/\u00ef\3\2\2\2\61\u00f2\3\2\2\2\63\u00f4\3\2\2\2\65\u00f6\3\2\2"+
		"\2\67\u00f8\3\2\2\29\u00fa\3\2\2\2;\u00fd\3\2\2\2=\u0100\3\2\2\2?\u0102"+
		"\3\2\2\2A\u0104\3\2\2\2C\u010c\3\2\2\2E\u010e\3\2\2\2G\u0110\3\2\2\2I"+
		"\u0114\3\2\2\2K\u0116\3\2\2\2M\u0125\3\2\2\2O\u0127\3\2\2\2Q\u0129\3\2"+
		"\2\2S\u012e\3\2\2\2U\u0135\3\2\2\2W\u013e\3\2\2\2Y\u0148\3\2\2\2[\u014a"+
		"\3\2\2\2]\u014c\3\2\2\2_\u0150\3\2\2\2a\u015c\3\2\2\2c\u015e\3\2\2\2e"+
		"\u0167\3\2\2\2g\u0169\3\2\2\2i\u016c\3\2\2\2k\u0172\3\2\2\2m\u0175\3\2"+
		"\2\2o\u017a\3\2\2\2q\u0180\3\2\2\2s\u018e\3\2\2\2uv\7e\2\2vw\7n\2\2wx"+
		"\7c\2\2xy\7u\2\2yz\7u\2\2z\4\3\2\2\2{|\7}\2\2|\6\3\2\2\2}~\7\177\2\2~"+
		"\b\3\2\2\2\177\u0080\7r\2\2\u0080\u0081\7w\2\2\u0081\u0082\7d\2\2\u0082"+
		"\u0083\7n\2\2\u0083\u0084\7k\2\2\u0084\u0085\7e\2\2\u0085\n\3\2\2\2\u0086"+
		"\u0087\7u\2\2\u0087\u0088\7v\2\2\u0088\u0089\7c\2\2\u0089\u008a\7v\2\2"+
		"\u008a\u008b\7k\2\2\u008b\u008c\7e\2\2\u008c\f\3\2\2\2\u008d\u008e\7x"+
		"\2\2\u008e\u008f\7q\2\2\u008f\u0090\7k\2\2\u0090\u0091\7f\2\2\u0091\16"+
		"\3\2\2\2\u0092\u0093\7o\2\2\u0093\u0094\7c\2\2\u0094\u0095\7k\2\2\u0095"+
		"\u0096\7p\2\2\u0096\20\3\2\2\2\u0097\u0098\7k\2\2\u0098\u0099\7p\2\2\u0099"+
		"\u009a\7j\2\2\u009a\u009b\7g\2\2\u009b\u009c\7t\2\2\u009c\u009d\7k\2\2"+
		"\u009d\u009e\7v\2\2\u009e\u009f\7u\2\2\u009f\22\3\2\2\2\u00a0\u00a1\7"+
		"k\2\2\u00a1\u00a2\7o\2\2\u00a2\u00a3\7r\2\2\u00a3\u00a4\7n\2\2\u00a4\u00a5"+
		"\7g\2\2\u00a5\u00a6\7o\2\2\u00a6\u00a7\7g\2\2\u00a7\u00a8\7p\2\2\u00a8"+
		"\u00a9\7v\2\2\u00a9\u00aa\7u\2\2\u00aa\24\3\2\2\2\u00ab\u00ac\7.\2\2\u00ac"+
		"\26\3\2\2\2\u00ad\u00ae\7k\2\2\u00ae\u00af\7p\2\2\u00af\u00b0\7v\2\2\u00b0"+
		"\u00b1\7g\2\2\u00b1\u00b2\7t\2\2\u00b2\u00b3\7h\2\2\u00b3\u00b4\7c\2\2"+
		"\u00b4\u00b5\7e\2\2\u00b5\u00b6\7g\2\2\u00b6\30\3\2\2\2\u00b7\u00b8\7"+
		"=\2\2\u00b8\32\3\2\2\2\u00b9\u00ba\7d\2\2\u00ba\u00bb\7q\2\2\u00bb\u00bc"+
		"\7q\2\2\u00bc\u00bd\7n\2\2\u00bd\u00be\7g\2\2\u00be\u00bf\7c\2\2\u00bf"+
		"\u00c0\7p\2\2\u00c0\34\3\2\2\2\u00c1\u00c2\7p\2\2\u00c2\u00c3\7w\2\2\u00c3"+
		"\u00c4\7o\2\2\u00c4\u00c5\7d\2\2\u00c5\u00c6\7g\2\2\u00c6\u00c7\7t\2\2"+
		"\u00c7\36\3\2\2\2\u00c8\u00c9\7r\2\2\u00c9\u00ca\7t\2\2\u00ca\u00cb\7"+
		"k\2\2\u00cb\u00cc\7x\2\2\u00cc\u00cd\7c\2\2\u00cd\u00ce\7v\2\2\u00ce\u00cf"+
		"\7g\2\2\u00cf \3\2\2\2\u00d0\u00d1\7k\2\2\u00d1\u00d2\7h\2\2\u00d2\"\3"+
		"\2\2\2\u00d3\u00d4\7g\2\2\u00d4\u00d5\7n\2\2\u00d5\u00d6\7u\2\2\u00d6"+
		"\u00d7\7g\2\2\u00d7$\3\2\2\2\u00d8\u00d9\7y\2\2\u00d9\u00da\7j\2\2\u00da"+
		"\u00db\7k\2\2\u00db\u00dc\7n\2\2\u00dc\u00dd\7g\2\2\u00dd&\3\2\2\2\u00de"+
		"\u00df\7r\2\2\u00df\u00e0\7t\2\2\u00e0\u00e1\7k\2\2\u00e1\u00e2\7p\2\2"+
		"\u00e2\u00e3\7v\2\2\u00e3(\3\2\2\2\u00e4\u00e5\7\60\2\2\u00e5*\3\2\2\2"+
		"\u00e6\u00e7\7p\2\2\u00e7\u00e8\7g\2\2\u00e8\u00e9\7y\2\2\u00e9,\3\2\2"+
		"\2\u00ea\u00eb\7v\2\2\u00eb\u00ec\7j\2\2\u00ec\u00ed\7k\2\2\u00ed\u00ee"+
		"\7u\2\2\u00ee.\3\2\2\2\u00ef\u00f0\7(\2\2\u00f0\u00f1\7(\2\2\u00f1\60"+
		"\3\2\2\2\u00f2\u00f3\7>\2\2\u00f3\62\3\2\2\2\u00f4\u00f5\7-\2\2\u00f5"+
		"\64\3\2\2\2\u00f6\u00f7\7/\2\2\u00f7\66\3\2\2\2\u00f8\u00f9\7,\2\2\u00f9"+
		"8\3\2\2\2\u00fa\u00fb\7,\2\2\u00fb\u00fc\7,\2\2\u00fc:\3\2\2\2\u00fd\u00fe"+
		"\7>\2\2\u00fe\u00ff\7@\2\2\u00ff<\3\2\2\2\u0100\u0101\7]\2\2\u0101>\3"+
		"\2\2\2\u0102\u0103\7_\2\2\u0103@\3\2\2\2\u0104\u0105\7\60\2\2\u0105\u0106"+
		"\7n\2\2\u0106\u0107\7g\2\2\u0107\u0108\7p\2\2\u0108\u0109\7i\2\2\u0109"+
		"\u010a\7v\2\2\u010a\u010b\7j\2\2\u010bB\3\2\2\2\u010c\u010d\7*\2\2\u010d"+
		"D\3\2\2\2\u010e\u010f\7+\2\2\u010fF\3\2\2\2\u0110\u0111\7t\2\2\u0111\u0112"+
		"\7g\2\2\u0112\u0113\7v\2\2\u0113H\3\2\2\2\u0114\u0115\7?\2\2\u0115J\3"+
		"\2\2\2\u0116\u0117\7h\2\2\u0117\u0118\7k\2\2\u0118\u0119\7p\2\2\u0119"+
		"\u011a\7c\2\2\u011a\u011b\7n\2\2\u011bL\3\2\2\2\u011c\u011d\7v\2\2\u011d"+
		"\u011e\7t\2\2\u011e\u011f\7w\2\2\u011f\u0126\7g\2\2\u0120\u0121\7h\2\2"+
		"\u0121\u0122\7c\2\2\u0122\u0123\7n\2\2\u0123\u0124\7u\2\2\u0124\u0126"+
		"\7g\2\2\u0125\u011c\3\2\2\2\u0125\u0120\3\2\2\2\u0126N\3\2\2\2\u0127\u0128"+
		"\5]/\2\u0128P\3\2\2\2\u0129\u012a\7p\2\2\u012a\u012b\7w\2\2\u012b\u012c"+
		"\7n\2\2\u012c\u012d\7n\2\2\u012dR\3\2\2\2\u012e\u0132\5Y-\2\u012f\u0131"+
		"\5[.\2\u0130\u012f\3\2\2\2\u0131\u0134\3\2\2\2\u0132\u0130\3\2\2\2\u0132"+
		"\u0133\3\2\2\2\u0133T\3\2\2\2\u0134\u0132\3\2\2\2\u0135\u0139\7$\2\2\u0136"+
		"\u0138\n\2\2\2\u0137\u0136\3\2\2\2\u0138\u013b\3\2\2\2\u0139\u0137\3\2"+
		"\2\2\u0139\u013a\3\2\2\2\u013a\u013c\3\2\2\2\u013b\u0139\3\2\2\2\u013c"+
		"\u013d\7$\2\2\u013dV\3\2\2\2\u013e\u013f\7B\2\2\u013f\u0140\7Q\2\2\u0140"+
		"\u0141\7x\2\2\u0141\u0142\7g\2\2\u0142\u0143\7t\2\2\u0143\u0144\7t\2\2"+
		"\u0144\u0145\7k\2\2\u0145\u0146\7f\2\2\u0146\u0147\7g\2\2\u0147X\3\2\2"+
		"\2\u0148\u0149\t\3\2\2\u0149Z\3\2\2\2\u014a\u014b\t\4\2\2\u014b\\\3\2"+
		"\2\2\u014c\u014e\5a\61\2\u014d\u014f\5_\60\2\u014e\u014d\3\2\2\2\u014e"+
		"\u014f\3\2\2\2\u014f^\3\2\2\2\u0150\u0151\t\5\2\2\u0151`\3\2\2\2\u0152"+
		"\u015d\7\62\2\2\u0153\u015a\5g\64\2\u0154\u0156\5c\62\2\u0155\u0154\3"+
		"\2\2\2\u0155\u0156\3\2\2\2\u0156\u015b\3\2\2\2\u0157\u0158\5m\67\2\u0158"+
		"\u0159\5c\62\2\u0159\u015b\3\2\2\2\u015a\u0155\3\2\2\2\u015a\u0157\3\2"+
		"\2\2\u015b\u015d\3\2\2\2\u015c\u0152\3\2\2\2\u015c\u0153\3\2\2\2\u015d"+
		"b\3\2\2\2\u015e\u0163\5e\63\2\u015f\u0161\5i\65\2\u0160\u015f\3\2\2\2"+
		"\u0160\u0161\3\2\2\2\u0161\u0162\3\2\2\2\u0162\u0164\5e\63\2\u0163\u0160"+
		"\3\2\2\2\u0163\u0164\3\2\2\2\u0164d\3\2\2\2\u0165\u0168\7\62\2\2\u0166"+
		"\u0168\5g\64\2\u0167\u0165\3\2\2\2\u0167\u0166\3\2\2\2\u0168f\3\2\2\2"+
		"\u0169\u016a\t\6\2\2\u016ah\3\2\2\2\u016b\u016d\5k\66\2\u016c\u016b\3"+
		"\2\2\2\u016d\u016e\3\2\2\2\u016e\u016c\3\2\2\2\u016e\u016f\3\2\2\2\u016f"+
		"j\3\2\2\2\u0170\u0173\5e\63\2\u0171\u0173\7a\2\2\u0172\u0170\3\2\2\2\u0172"+
		"\u0171\3\2\2\2\u0173l\3\2\2\2\u0174\u0176\7a\2\2\u0175\u0174\3\2\2\2\u0176"+
		"\u0177\3\2\2\2\u0177\u0175\3\2\2\2\u0177\u0178\3\2\2\2\u0178n\3\2\2\2"+
		"\u0179\u017b\t\7\2\2\u017a\u0179\3\2\2\2\u017b\u017c\3\2\2\2\u017c\u017a"+
		"\3\2\2\2\u017c\u017d\3\2\2\2\u017d\u017e\3\2\2\2\u017e\u017f\b8\2\2\u017f"+
		"p\3\2\2\2\u0180\u0181\7\61\2\2\u0181\u0182\7,\2\2\u0182\u0186\3\2\2\2"+
		"\u0183\u0185\13\2\2\2\u0184\u0183\3\2\2\2\u0185\u0188\3\2\2\2\u0186\u0187"+
		"\3\2\2\2\u0186\u0184\3\2\2\2\u0187\u0189\3\2\2\2\u0188\u0186\3\2\2\2\u0189"+
		"\u018a\7,\2\2\u018a\u018b\7\61\2\2\u018b\u018c\3\2\2\2\u018c\u018d\b9"+
		"\2\2\u018dr\3\2\2\2\u018e\u018f\7\61\2\2\u018f\u0190\7\61\2\2\u0190\u0194"+
		"\3\2\2\2\u0191\u0193\13\2\2\2\u0192\u0191\3\2\2\2\u0193\u0196\3\2\2\2"+
		"\u0194\u0195\3\2\2\2\u0194\u0192\3\2\2\2\u0195\u0197\3\2\2\2\u0196\u0194"+
		"\3\2\2\2\u0197\u0198\7\f\2\2\u0198\u0199\3\2\2\2\u0199\u019a\b:\2\2\u019a"+
		"t\3\2\2\2\23\2\u0125\u0132\u0139\u014e\u0155\u015a\u015c\u0160\u0163\u0167"+
		"\u016e\u0172\u0177\u017c\u0186\u0194\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}