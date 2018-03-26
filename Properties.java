import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;


public class Properties {
	
	
	public static void main(String[] args) throws Exception {	
		String s1 = "abcd = efgh.1234";
		s1 = s1.replaceAll("[\\p{Punct}&&[^'.]]+", " ");
		String[] splitted = s1.split("\\s+");
		for (String x: splitted){
			System.out.println(x);
			
		}
		
	}
	
	
	
	public static HashSet<MethodDeclaration> property(VariableDeclarator n,CompilationUnit cu1, CompilationUnit cu2) throws FileNotFoundException {
		/* FileInputStream in = new FileInputStream("/Users/akshatsingh/Downloads/TestVectors.java");	
		CompilationUnit cu = JavaParser.parse(in);
		
		
		
		List<ClassOrInterfaceDeclaration> rtt = cu.findAll(ClassOrInterfaceDeclaration.class);
		for (int i=0; i<rtt.size();i++) {
			System.out.println(rtt.get(i).getNameAsString());
		}
		List<MethodDeclaration> mthds = cu.findAll(MethodDeclaration.class);
		List<Expression> expsns = new ArrayList<Expression>();
		for (int i=0; i<mthds.size();i++) {	
			expsns.addAll(mthds.get(i).findAll(Expression.class));
		}
		for (int i=0; i<expsns.size();i++) {
			if (expsns.get(i).isAssignExpr()) {
				AssignExpr assign = (AssignExpr) expsns.get(i);
				System.out.println(expsns.get(i).toString());
				System.out.println(assign.getValue().toString());
			}
		}*/
		
		HashSet<MethodDeclaration> properties = new HashSet<MethodDeclaration>();
		List<MethodDeclaration> mthds1 = cu1.findAll(MethodDeclaration.class);
		List<MethodDeclaration> mthds2 = cu2.findAll(MethodDeclaration.class);
		ArrayList<Expression> expsns = new ArrayList<Expression>();
		List<Statement> stmts =  new ArrayList<Statement>();
		AssignExpr assign;
		IfStmt ifstmt;
		ForStmt forstmt;
		for (int i=0; i<mthds1.size();i++) {	
			expsns =  (ArrayList<Expression>) mthds1.get(i).findAll(Expression.class);
			stmts = (mthds1.get(i).findAll(Statement.class));
			for(int x=0;x<expsns.size();x++){
				Expression z = (Expression) expsns.toArray()[x];
				if (z.isAssignExpr()){
					assign = (AssignExpr) z;
					
					String s1 = assign.getTarget().toString();
					s1 = s1.replaceAll("[\\p{Punct}&&[^'.]]+", " ");
					String s2 = assign.getValue().toString();
					s2 = s2.replaceAll("[\\p{Punct}&&[^'.]]+", " ");
					String[] splitStr = s1.split("[\\s.]");
					String[] splitStr2 = s2.split("[\\s+.]");
					List<String> strlist1 = Arrays.asList(splitStr);
					List<String> strlist2 = Arrays.asList(splitStr2);
					String[] splitted;
					if(strlist1.contains(n.getNameAsString())){
							properties.add(mthds1.get(i));
					}
					
					
					if(strlist2.contains(n.getNameAsString())){
							properties.add(mthds1.get(i));
					}
						
				}
			}
			for(int x=0;x<stmts.size();x++){
				Statement z = stmts.get(x);
				if(z.isIfStmt()){
					ifstmt = (IfStmt) z;
					String s1 = ifstmt.getCondition().toString();
					s1 = s1.replaceAll("[\\p{Punct}&&[^'.]]+", " ");
					String s2 = ifstmt.getThenStmt().toString();
					s2 = s2.replaceAll("[\\p{Punct}&&[^'.]]+", " ");
					String s3 = ifstmt.getElseStmt().toString();
					String[] splitStr1 = s1.split("[\\s+.]");
					String[] splitStr2 = s2.split("[\\s+.]");
					String[] splitStr3 = s3.split("[\\s+.]");
					List<String> strlist1 = Arrays.asList(splitStr1);
					List<String> strlist2 = Arrays.asList(splitStr2);
					List<String> strlist3 = Arrays.asList(splitStr3);
					String[] splitted;
					if(strlist1.contains(n.getNameAsString())){
						properties.add(mthds1.get(i));
					}
					if(strlist2.contains(n.getNameAsString())){
						properties.add(mthds1.get(i));
					}
					if(strlist3.contains(n.getNameAsString())){
						properties.add(mthds1.get(i));
					}	
				}
				if (z.isReturnStmt()){
					String s1 = z.toString();
					s1 = s1.replaceAll("[\\p{Punct}&&[^'.]]+", " ");
					String[] splitStr1 = s1.split("[\\s+.]");
					List<String> strlist1 = Arrays.asList(splitStr1);
					if(strlist1.contains(n.getNameAsString())){
						properties.add(mthds1.get(i));
					}
				}
				if(z.isForStmt()){
					forstmt = (ForStmt) z;
					NodeList<Expression> init = forstmt.getInitialization();
					for (Expression a: init){
						if(a.isAssignExpr()){
							AssignExpr b = (AssignExpr) a;
							String s1 = b.getTarget().toString();
							s1 = s1.replaceAll("[\\p{Punct}&&[^'.]]+", " ");
							String s2 = b.getValue().toString();
							s2 = s2.replaceAll("[\\p{Punct}&&[^'.]]+", " ");
							String[] splitStr = s1.split("[\\s+.]");
							String[] splitStr2 = s2.split("[\\s+.]");
							List<String> strlist1 = Arrays.asList(splitStr);
							List<String> strlist2 = Arrays.asList(splitStr2);
							String[] splitted;
							if(strlist1.contains(n.getNameAsString())){
									properties.add(mthds1.get(i));
							}
							if(strlist2.contains(n.getNameAsString())){
									properties.add(mthds1.get(i));
							}
						}
						
					}
					Optional<Expression> temp = forstmt.getCompare();
					Expression compare = temp.get();
					
					
					
					
				}
				
				
			}
		}
		
		
		
		return properties;
	}
}
		//if((A.a)==st)
		//A.a=2
		//x = "A.a" + 4
		//abcd=5
		//b=2*a
		//A.a.toString()
	


