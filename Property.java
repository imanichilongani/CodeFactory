import java.io.FileInputStream;
import java.util.Map;
import java.util.Map.Entry;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.ReturnStmt;



public class Property {
	public void main(String[] args) throws Exception {
		CompilationUnit cu = get_compilation_unit("/Users/akshatsingh/Downloads/CLL.java");
		AttributePropertyFinder a = new AttributePropertyFinder();
		Map<NodeProperties, HashSet<NodeProperties>> attr_set = a.get_attributes(cu);
		Map<MethodDeclaration, HashSet<Node>> method_set = get_methods(cu);
		Iterator<Entry<MethodDeclaration, HashSet<Node>>> it = method_set.entrySet().iterator();
		
	}
	
	/*Returns a CompilationUnit for the given path, throws FileNotFoundException if file is not found in
	 * given path
	 */
	public static CompilationUnit get_compilation_unit(String path) throws FileNotFoundException {
		FileInputStream in = new FileInputStream(path);
		return JavaParser.parse(in);
	}
	

	
public Map<NodeProperties, HashSet<NodeProperties>>get_attributes(CompilationUnit cu)  {
		

		List<VariableDeclarator> all_attributes = cu.findAll(VariableDeclarator.class); //All variables from CU
		ClassOrInterfaceDeclaration the_class = cu.findAll(ClassOrInterfaceDeclaration.class).get(0);
		Map<VariableDeclarator, HashSet<Node>> attribute_set = new HashMap<VariableDeclarator, HashSet<Node>>();
		List<MethodDeclaration> methods = cu.findAll(MethodDeclaration.class); //All method declarations from CU
		for (int i=0; i<methods.size();i++) {
			List<VariableDeclarator> m_variables = methods.get(i).findAll(VariableDeclarator.class); 
			for (int m_var=0; m_var<m_variables.size();m_var++) {
				if (attribute_set.containsKey(m_variables.get(m_var))) {
					attribute_set.remove(m_variables.get(m_var));
				
				}
			}
		}
		Map<NodeProperties, HashSet<NodeProperties>> attribute_set1 =
								new HashMap<NodeProperties, HashSet<NodeProperties>>();
		Set<VariableDeclarator> x = attribute_set.keySet();
		List<VariableDeclarator> y = (List)attribute_set.keySet();
		for (int i=0;i<y.size();i++) {
			String name = y.get(i).getNameAsString();
			NodeProperties.Type the_type = NodeProperties.Type.ATTRIBUTE;
			NodeProperties new_node = new NodeProperties(name, the_class.getNameAsString(),the_type);
			attribute_set1.put(new_node, new HashSet<NodeProperties>()); //add  attributes to hashmap
		}				
		

		return attribute_set1;
	}
	
	
	public Map<MethodDeclaration, HashSet<Node>>get_methods(CompilationUnit cu) {
		List<MethodDeclaration> all_methods = cu.findAll(MethodDeclaration.class);
		Map<MethodDeclaration, HashSet<Node>> method_set = new HashMap<MethodDeclaration, HashSet<Node>>();
		for (int i=0;i<all_methods.size();i++) {
			method_set.put(all_methods.get(i), new HashSet<Node>()); //add  methods to hashmap
		}
		return method_set;
	}
	
	
	
	public  Map.Entry<MethodDeclaration, HashSet<Node>> 
				get_method_prop(Map.Entry<MethodDeclaration, HashSet<Node>> meth_prop){
		MethodDeclaration method = meth_prop.getKey();
		List<MethodDeclaration> used_methods = method.findAll(MethodDeclaration.class);
		for (int i=0;i<used_methods.size();i++) {
			meth_prop.getValue().add(used_methods.get(i)); //add  used methods to hashset
		}	
		List<VariableDeclarator> used_attributes = method.findAll(VariableDeclarator.class);
		for (int i=0;i<used_attributes.size();i++) {
			meth_prop.getValue().add(used_attributes.get(i)); //add  used attributes to hashset
		}
		List<ReturnStmt> returns= method.findAll(ReturnStmt.class);
		for (int i=0;i<returns.size();i++) {
			 Optional<Expression> vd = returns.get(i).getExpression();	
			 meth_prop.getValue().add(vd.get()); //add  used attributes to hashset	
			
		}
		
		/*List<AssignExpr> assign = method.findAll(AssignExpr.class);
		for (int i=0;i<assign.size();i++) {
			AssignExpr b = (AssignExpr) assign.get(i);
			String s1 = b.getTarget().toString();
			s1 = s1.replaceAll("[\\p{Punct}&&[^'.]]+", " ");
			String s2 = b.getValue().toString();
			s2 = s2.replaceAll("[\\p{Punct}&&[^'.]]+", " ");
			String[] splitStr = s1.split("[\\s.]");
			String[] splitStr2 = s2.split("[\\s.]");
			List<String> strlist1 = Arrays.asList(splitStr);
			List<String> strlist2 = Arrays.asList(splitStr2);
			String[] splitted;
			
			for (int j=0 ;j<strlist1.size();j++) {
				meth_prop.getValue().addAll(strlist1.get(j));
			}
			
			meth_prop.add(mthds1.get(i));
			meth_prop.add(mthds1.get(i));
			
		}*/
		
		return meth_prop;
	} 

	
}

