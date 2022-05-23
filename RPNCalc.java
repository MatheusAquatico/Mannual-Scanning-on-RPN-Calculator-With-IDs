package rpn;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

public class RPNCalc {

	public static void main(String[] args) {
		Stack<Double> stack = new Stack<>();
		Map<String, Double> variables = new HashMap<>();
		Scanner scan = new Scanner(System.in);
		double res=0;
		String var;
		System.out.println("Digite sua string de operação no console em formato RPN e aperte duas vezes enter");
		System.out.println("Para adicionar variáveis, digite a mesma em seguida do valor atribuído. Ex: x 10");
		while(scan.hasNextLine()){
			var= scan.nextLine();
			if (var.equals(""))
		        break;
			
			String linha = var;
			boolean atribuiuId= false;
			Token token= new Token();
			int tam=0;
			while (tam<linha.length()) {
				char c=linha.charAt(tam);
				if (isDigit(c)) {
					if((linha.length()-1)==tam) {
						token.setType("NUM");
						token.setLexeme(linha);
						break;
					}else {
						c=linha.charAt(tam+1);
						if (isDigit(c)) {
							tam++;
							continue;
						}else {
							throw new RuntimeException("UNEXPECTED TOKEN");
							
						}
					}
				}else {
					if(linha.length()>1){
						String [] variavel = linha.split(" ");
						String variable=variavel[0];
						boolean valid =false;
						for (int i=0; i<variable.length();i++) {
							if((c>='0'&& c<='9') || (c>='a'&& c<='z') || (c>='A'&& c<='Z')) {
								valid=true;
								
							}
						}
						if(valid) {
							if(variables.get(variable)==null) {
								Double value=Double.parseDouble(variavel[1]);
								variables.put(variable,value);// formato "var num"
								token.setType("ID");
								token.setLexeme(variable);
								tam=linha.length();
								atribuiuId=true;
							}else {
								tam=linha.length();
								continue;
							}
							
							
						}else {
						throw new RuntimeException("UNEXPECTED TOKEN");//error
						}
					}else {
						switch (c) {
						
						case '+':
							token.setType("PLUS");
							token.setLexeme(linha);
							tam++;
							break;
						case '-':
							token.setType("MINUS");
							token.setLexeme(linha);
							tam++;
							break;
						case '*':
							token.setType("TIMES");
							token.setLexeme(linha);
							tam++;
							break;
						case '/':
							token.setType("DIVIDED BY");
							token.setLexeme(linha);
							tam++;
							break;
						case '^':
							token.setType("EXPONENTIATION");
							token.setLexeme(linha);
							tam++;
							break;
						default:
							if((c>='a'&& c<='z')) {
								String [] variavel = linha.split(" ");
								String variable=variavel[0];
								if(variables.get(variable)==null) {
									Double value=Double.parseDouble(variavel[1]);
									variables.put(variable,value);// formato "var num"
									token.setType("ID");
									token.setLexeme(variable);
									tam=linha.length();
									atribuiuId=true;
								}else {
									tam=linha.length();
									continue;
								}
								
		
					
							}else {
								throw new RuntimeException("UNEXPECTED TOKEN");
							
							}
							break;
							
							
					}
					}
					
				}
			}
			if (token.getType()!=null) {
				System.out.println("Token [Type="+token.getType()+", Lexeme="+token.getLexeme()+"]");
			}
			
			if(!atribuiuId) {
				char op= var.charAt(0);
				double a,b=0;
				switch(op){
					case '+':
						a= stack.lastElement();
						stack.pop();
						b= stack.lastElement();
						stack.pop();
						stack.push(b+a);
						break;
					case '-':
						a= stack.lastElement();
						stack.pop();
						b= stack.lastElement();
						stack.pop();
						stack.push(b-a);
						break;
					case '/':
						a= stack.lastElement();
						stack.pop();
						b= stack.lastElement();
						stack.pop();
						if(a==0) {
							System.out.println("Um número não pode ser dividido por 0!");
						}else {
							stack.push(b/a);
						}
						break;
					case '*':
						a= stack.lastElement();
						stack.pop();
						b= stack.lastElement();
						stack.pop();
						stack.push(b*a);
						break;
					case '^':
						a= stack.lastElement();
						stack.pop();
						b= stack.lastElement();
						stack.pop();
						stack.push(Math.pow(b, a));
						break;
				    default:
				    	if (op>='a' && op <='z') {
				    		if(variables.get(var)!=null) {
				    			double numero=variables.get(var);
					    		stack.push(numero);
				    		}else {
				    			throw new RuntimeException("UNRECOGNIZED ID");
				    		}
				    		
				    	}else if (op>='0' && op <='9') {
				    		double num= Double.parseDouble(var);
					    	stack.push(num);
				    	}else {
				    		throw new RuntimeException("UNRECOGNIZED ID");
				    	}
				    	
				    	break;
				    	
				}
			}
			
			
		}
		System.out.println("Resultado:");
		res= stack.firstElement();
		System.out.println(res);

		
	}
	
	
	private static boolean isDigit(char c) {
		return (c>= '0' && c <= '9');
	}
	
	
	
	

}

class Token{
	public static final int TK_NUM=0;
	public static final int TK_OP=1;
	
	private String type;
	private String lexeme;
	
	
	public String getLexeme() {
		return lexeme;
	}

	public void setLexeme(String lexeme) {
		this.lexeme = lexeme;
	}



	public Token(String type, String lexeme) {
		super ();
		this.type= type;
		this.lexeme=lexeme;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Token() {
		super ();
		
	}
	
	
	
}
