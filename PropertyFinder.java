import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;


public class PropertyFInder {
	
	
	public static void main(String[] args) throws Exception {	
		String s1 = "'abcd'";
		s1 = s1.replaceAll("[\\p{Punct}&&[^'.]]+", "");
		System.out.println(s1);
		
	}
	
	
	
	public static HashSet<Node> property(Node n,CompilationUnit cu1, CompilationUnit cu2) throws FileNotFoundException {
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
		
		HashSet<Node> properties = new HashSet<Node>();
		List<MethodDeclaration> mthds1 = cu1.findAll(MethodDeclaration.class);
		List<MethodDeclaration> mthds2 = cu2.findAll(MethodDeclaration.class);
		List<Expression> expsns = new ArrayList<Expression>();
		List<Statement> stmts =  new ArrayList<Statement>();
		AssignExpr assign;
		IfStmt ifstmt;
		ForStmt forstmt;
		for (int i=0; i<mthds1.size();i++) {	
			expsns.addAll(mthds1.get(i).findAll(Expression.class));
			stmts.addAll(mthds1.get(i).findAll(Statement.class));
			for(int x=0;x<expsns.size();x++){
				Expression z = expsns.get(i);
				if (z.isAssignExpr()){
					assign = (AssignExpr) z;
					String s1 = assign.getTarget().toString();
					s1 = s1.replaceAll("[\\p{Punct}&&[^.]]+", "");
					String s2 = assign.getValue().toString();
					s2 = s2.replaceAll("[\\p{Punct}&&[^.]]+", "");
					String[] splitStr = s1.split("\\s+");
					String[] splitStr2 = s2.split("\\s+");
					List<String> strlist1 = Arrays.asList(splitStr);
					List<String> strlist2 = Arrays.asList(splitStr2);
					String[] splitted;
					for(String a:strlist1){
						splitted = a.split(".");
						if(Arrays.asList(splitted).contains(n.toString())){
							properties.add(mthds1.get(i));
						}
						
					}
					for(String a:strlist2){
						splitted = a.split(".");
						if(Arrays.asList(splitted).contains(n.toString())){
							properties.add(mthds1.get(i));
						}
						
					}
				}
			}
			for(int x=0;x<stmts.size();x++){
				Statement z = stmts.get(i);
				if(z.isIfStmt()){
					ifstmt = (IfStmt) z;
					String s1 = ifstmt.getCondition().toString();
					s1 = s1.replaceAll("[\\p{Punct}&&[^\\'.]]+", "");
					String s2 = ifstmt.getThenStmt().toString();
					s2 = s2.replaceAll("[\\p{Punct}&&[^.]]+", "");
					String s3 = ifstmt.getElseStmt().toString();
					String[] splitStr = s1.split("\\s+");
					String[] splitStr2 = s2.split("\\s+");
					String[] splitStr3 = s3.split("\\s+");
					List<String> strlist1 = Arrays.asList(splitStr);
					List<String> strlist2 = Arrays.asList(splitStr2);
					List<String> strlist3 = Arrays.asList(splitStr3);
					String[] splitted;
					for(String a:strlist1){
						splitted = a.split(".");
						if(Arrays.asList(splitted).contains(n.toString())){
							properties.add(mthds1.get(i));
						}
						
					}
					for(String a:strlist2){
						splitted = a.split(".");
						if(Arrays.asList(splitted).contains(n.toString())){
							properties.add(mthds1.get(i));
						}
					}
					for(String a:strlist3){
						splitted = a.split(".");
						if(Arrays.asList(splitted).contains(n.toString())){
								properties.add(mthds1.get(i));
						}	
					
					
				
					
				}
				
			}
		}

		
		
		
		}
		return null;
	}
}
		//if((A.a)==st)
		//A.a=2
		//x = "A.a" + 4
		//abcd=5
		//b=2*a
		//A.a.toString()
	


