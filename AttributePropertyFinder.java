import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.ConditionalExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.stmt.WhileStmt;


public class AttributePropertyFinder {
	
	
	/*Returns a HashMap with VariableDeclarators as keys and A HashSet containing Nodes as values */

public static Map<NodeProperties, HashSet<NodeProperties>>get_attributes(CompilationUnit cu)  {
		

		List<VariableDeclarator> all_attributes = cu.findAll(VariableDeclarator.class); //All variables from CU
		ClassOrInterfaceDeclaration the_class = cu.findAll(ClassOrInterfaceDeclaration.class).get(0);
		List<MethodDeclaration> methods = cu.findAll(MethodDeclaration.class); //All method declarations from CU
		for (int i=0; i<methods.size();i++) {
			List<VariableDeclarator> m_variables = methods.get(i).findAll(VariableDeclarator.class); 
			for (int m_var=0; m_var<m_variables.size();m_var++) {
				if (all_attributes.contains(m_variables.get(m_var))) {
					all_attributes.remove(m_variables.get(m_var));
				
				}
			}
		}
		Map<NodeProperties, HashSet<NodeProperties>> attribute_set1 =new HashMap<NodeProperties, HashSet<NodeProperties>>();
		for (int i=0;i<all_attributes.size();i++) {
			String name = all_attributes.get(i).getNameAsString();
			NodeProperties.Type the_type = NodeProperties.Type.ATTRIBUTE;
			NodeProperties new_node = new NodeProperties(name, the_class.getNameAsString(),the_type);
			attribute_set1.put(new_node, new HashSet<NodeProperties>()); //add  attributes to hashmap
		}				
		

		return attribute_set1;
	}
	
	
	
	public static Map<NodeProperties, HashSet<NodeProperties>> property(Map<NodeProperties, HashSet<NodeProperties>> attributes,CompilationUnit cu1) throws FileNotFoundException {
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
		Set<NodeProperties> attributeset = attributes.keySet();
		ArrayList<NodeProperties>attributelist  = new ArrayList<NodeProperties>(attributeset);
		for(NodeProperties n:attributelist){
			List<MethodDeclaration> properties = new ArrayList<MethodDeclaration>();
			HashSet<NodeProperties> finalproperties = new HashSet<NodeProperties>();
			List<MethodDeclaration> mthds1 = cu1.findAll(MethodDeclaration.class);
			ArrayList<Expression> expsns = new ArrayList<Expression>();
			List<Statement> stmts =  new ArrayList<Statement>();
			AssignExpr assign;
			IfStmt ifstmt;
			ForStmt forstmt;
			WhileStmt whilestmt;
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
						if(strlist1.contains(n.name)){
								properties.add(mthds1.get(i));
						}
						
						
						if(strlist2.contains(n.name)){
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
						if(strlist1.contains(n.name)){
							properties.add(mthds1.get(i));
						}
						if(strlist2.contains(n.name)){
							properties.add(mthds1.get(i));
						}
						if(strlist3.contains(n.name)){
							properties.add(mthds1.get(i));
						}	
					}
					if (z.isReturnStmt()){
						String s1 = z.toString();
						s1 = s1.replaceAll("[\\p{Punct}&&[^'.]]+", " ");
						String[] splitStr1 = s1.split("[\\s+.]");
						List<String> strlist1 = Arrays.asList(splitStr1);
						if(strlist1.contains(n.name)){
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
								if(strlist1.contains(n.name)){
										properties.add(mthds1.get(i));
								}
								if(strlist2.contains(n.name)){
										properties.add(mthds1.get(i));
								}
							}
							
						}
						Optional<Expression> temp = forstmt.getCompare();
						Expression compare = temp.get();
					if(z.isWhileStmt()){
						whilestmt = (WhileStmt) z;
						Expression a = whilestmt.getCondition();
						String s1 =a.toString();
						String[] splitStr = s1.split("[\\s+.]");
						List<String> strlist1 = Arrays.asList(splitStr);
						if(strlist1.contains(n.name)){
							properties.add(mthds1.get(i));
					}
					}
						
						
						
						
					}
					
					
				}
			}
			for(int i=0;i<properties.size();i++){
				String name = properties.get(i).getNameAsString();
				NodeProperties.Type the_type = NodeProperties.Type.METHOD;
				ClassOrInterfaceDeclaration the_class = cu1.findAll(ClassOrInterfaceDeclaration.class).get(0);
				NodeProperties new_node = new NodeProperties(name, the_class.getNameAsString(),the_type);
				finalproperties.add(new_node);
				
			}
			
		finalproperties.add(n);	
		attributes.put(n, finalproperties);	
			
			
		}
		return attributes;
	}
			
}
		
		
		//if((A.a)==st)
		//A.a=2
		//x = "A.a" + 4
		//abcd=5
		//b=2*a
		//A.a.toString()
	


