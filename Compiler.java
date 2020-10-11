import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


public class Compiler {
	public String inputString;
	public int pos = 0;
	public int num;
	public char readinChar;
	public StringBuilder token = new StringBuilder();
	
	char[] letList = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 
						'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
	char[] numList = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
	public enum Symbol{
		BEGINSY,
		ENDSY,
		FORSY,
		IFSY,
		THENSY,
		ELSESY,
		IDSY,
		INTSY,
		COLONSY,
		PLUSSY,
		STARSY,
		COMMASY,
		LPARSY,
		RPARSY,
		ASSIGNSY,
		UNSY
	}
	public Symbol symbol;
	public void readFile(String inSource) {
		File file = new File(inSource);
		try {
			 FileInputStream in=new FileInputStream(file);
			 int size=in.available();
			 byte[] buffer=new byte[size];
			 in.read(buffer);
			 in.close();
			 this.inputString = new String(buffer,"GB2312");
			} catch (IOException e) {
			 // TODO Auto-generated catch block
			 e.printStackTrace();
			}
	}
	public boolean getchar() {
		try {
			this.readinChar = this.inputString.charAt(pos);
			this.pos ++;
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	public void clearToken() {
		this.token.delete(0, this.token.length());
	}
	public boolean isSpace() {
		if(this.readinChar == ' ') {
			return true;
		}else {
			return false;
		}
	}
	public boolean isNewLine() {
		if(this.readinChar == '\n' || this.readinChar == '\r') {
			return true;
		}else {
			return false;
		}
	}
	public boolean isTab() {
		if(this.readinChar == '\t') {
			return true;
		}else {
			return false;
		}
	}
	public boolean isLetter() {
		for(int i = 0; i < this.letList.length; i++) {
			if(this.readinChar == this.letList[i]) {
				return true;
			}
		}
		return false;
	}
	public boolean isDigit() {
		for(int i = 0; i < this.numList.length; i++) {
			if(this.readinChar == this.numList[i]) {
				return true;
			}
		}
		return false;
	}
	public boolean isColon() {
		if(this.readinChar == ':') {
			return true;
		}else {
			return false;
		}
	}
	public boolean isComma() {
		if(this.readinChar == ',') {
			return true;
		}else {
			return false;
		}
	}
	public boolean isSemi() {
		if(this.readinChar == ';') {
			return true;
		}else {
			return false;
		}
	}
	public boolean isEqu() {
		if(this.readinChar == '=') {
			return true;
		}else {
			return false;
		}
	}
	public boolean isPlus() {
		if(this.readinChar == '+') {
			return true;
		}else {
			return false;
		}
	}
	public boolean isDiv() {
		if(this.readinChar == '/') {
			return true;
		}else {
			return false;
		}
	}
	public boolean isStar() {
		if(this.readinChar == '*') {
			return true;
		}else {
			return false;
		}
	}
	public boolean isLpar() {
		if(this.readinChar == '(') {
			return true;
		}else {
			return false;
		}
	}
	public boolean isRpar() {
		if(this.readinChar == ')') {
			return true;
		}else {
			return false;
		}
	}
	public void catToken() {
		this.token.append(this.readinChar);
	}
	public void retract() {
		this.pos --;
	}
	public Symbol reserver() {
		if(this.token.toString().equals("BEGIN")) {
			return Symbol.BEGINSY;
		}else if(this.token.toString().equals("END")) {
			return Symbol.ENDSY;
		}else if(this.token.toString().equals("FOR")) {
			return Symbol.FORSY;
		}else if(this.token.toString().equals("IF")) {
			return Symbol.IFSY;
		}else if(this.token.toString().equals("THEN")) {
			return Symbol.THENSY;
		}else if(this.token.toString().equals("ELSE")) {
			return Symbol.ELSESY;
		}else {
			return Symbol.IDSY;
		}
	}
	public int transNum() {

		return Integer.parseInt(this.token.toString());
	}
	public void error() {
		this.symbol = Symbol.UNSY;
	}
	
	public int getsym() {
		this.clearToken();
		while(this.isSpace() || this.isNewLine() || this.isTab()) {
			if(!this.getchar()) {
				return -1;
			}
		}
		if(this.isLetter()) {
			while(this.isLetter() || this.isDigit()) {
				this.catToken();
				this.getchar();
			}
			this.retract();
			Symbol resultValue = reserver();
			this.symbol = resultValue;
		}
		else if(this.isDigit()) {
			while(this.isDigit()) {
				this.catToken();
				if(!this.getchar()) {
					break;
				}
			}
			this.retract();
			this.num = this.transNum();
			this.symbol = Symbol.INTSY;
		}
		else if(this.isColon()) {
			if(this.getchar()) {
				if(this.isEqu())	
					this.symbol = Symbol.ASSIGNSY;
				else {
					retract();
					this.symbol = Symbol.COLONSY;
				}
			}else {
				this.symbol = Symbol.COLONSY;
			}
		}
		else if(this.isPlus())	
			this.symbol = Symbol.PLUSSY;
		else if(this.isStar())	
			this.symbol = Symbol.STARSY;
		else if(this.isLpar())	
			this.symbol = Symbol.LPARSY;
		else if(this.isRpar())	
			this.symbol = Symbol.RPARSY;
		else if(this.isComma())	
			this.symbol = Symbol.COMMASY;
		else this.error();
		return 0;
	}
	
	public static void main(String[] args) {
		Compiler compiler = new Compiler();
		compiler.readFile(args[0]);
		while(compiler.pos + 1 < compiler.inputString.length()) {
			if(!compiler.getchar()) {
				break;
			}
			if(compiler.getsym() == -1) {
				return;
			}
			switch(compiler.symbol) {
				case BEGINSY:
					System.out.println("Begin");
					break;
				case ENDSY:
					System.out.println("End");
					break;
				case FORSY:
					System.out.println("For");
					break;
				case IFSY:
					System.out.println("If");
					break;
				case THENSY:
					System.out.println("Then");
					break;
				case ELSESY:
					System.out.println("Else");
					break;
				case IDSY:
					System.out.println("Ident(" + compiler.token + ")");
					break;
				case INTSY:
					System.out.println("Int(" + compiler.num + ")");
					break;
				case COLONSY:
					System.out.println("Colon");
					break;
				case PLUSSY:
					System.out.println("Plus");
					break;
				case STARSY:
					System.out.println("Star");
					break;
				case COMMASY:
					System.out.println("Comma");
					break;
				case LPARSY:
					System.out.println("LParenthesis");
					break;
				case RPARSY:
					System.out.println("RParenthesis");
					break;
				case ASSIGNSY:
					System.out.println("Assign");
					break;
				case UNSY:
					System.out.println("Unknown");
					return;
				default:
			}
		}
	}
}