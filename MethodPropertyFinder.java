
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;


import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;



public class MethodPropertyFinder {
	
	/*Returns a HashMap with a MethodDeclaration as key and String as value*/
	public static HashMap<MethodDeclaration, String>get_methods(CompilationUnit cu) {
		List<MethodDeclaration> all_methods = cu.findAll(MethodDeclaration.class);
		ClassOrInterfaceDeclaration the_class = cu.findAll(ClassOrInterfaceDeclaration.class).get(0);
		HashMap<MethodDeclaration, String> method_set = 
				new HashMap<MethodDeclaration, String>();
		for (int i=0;i<all_methods.size();i++) {
			method_set.put(all_methods.get(i), the_class.getNameAsString()); 
		}
		return method_set;
	}
	
	
	
	/*Returns a HashMap with a NodeProperties instance as key and HashSet of Node Properties as value*/
	public static HashMap<NodeProperties, HashSet<NodeProperties>>get_method_map(HashMap<MethodDeclaration, String> methods,
				HashMap<NodeProperties, HashSet<NodeProperties>> attr){
		HashMap<NodeProperties, HashSet<NodeProperties>> map = new HashMap<NodeProperties, HashSet<NodeProperties>>();
		Iterator<Entry<MethodDeclaration, String>> it = methods.entrySet().iterator();
		while(it.hasNext()) {
			Entry<MethodDeclaration, String> ent = it.next();
			String name = ent.getKey().getNameAsString();
			NodeProperties.Type the_type = NodeProperties.Type.METHOD;
			NodeProperties method_node = new NodeProperties(name,ent.getValue(),the_type);
			HashSet<NodeProperties> props = get_method_prop(ent.getKey(), ent.getValue());
			props.add(method_node);
			HashSet<NodeProperties> final_prop = get_used_attributes(attr,method_node,props);
			map.put(method_node,final_prop);

		}
		
		return map;
	}	
	
	
	/*Returns a HashSet of NodeProperties, used by get_method_map*/
	public static  HashSet<NodeProperties> get_method_prop(MethodDeclaration method, String the_class){
		HashSet<NodeProperties> set = new HashSet<NodeProperties>();
		List<MethodDeclaration> used_methods = method.findAll(MethodDeclaration.class);		
		for (int i=0;i<used_methods.size();i++) {
			String name = used_methods.get(i).getNameAsString();
			NodeProperties.Type the_type = NodeProperties.Type.METHOD;
			NodeProperties the_node = new NodeProperties(name,the_class, the_type);
			set.add(the_node);
		}	
		
		
		List<MethodCallExpr> method_calls= method.findAll(MethodCallExpr.class);
		for (int i=0;i<method_calls.size();i++) {
			 String name = method_calls.get(i).getNameAsString();
			 
			try {the_class = method_calls.get(i).getScope().toString();}
			catch (Exception NoSuchElementException) { ; }
			NodeProperties.Type the_type = NodeProperties.Type.METHOD;
			NodeProperties the_node = new NodeProperties(name,the_class, the_type);
			set.add(the_node);
			
		}
			
		return set;
	} 
	
	
	/*Adds attributes used by method to map*/
	public static HashSet<NodeProperties> get_used_attributes(Map<NodeProperties, HashSet<NodeProperties>> attr,
				NodeProperties method,HashSet<NodeProperties> result){	
		Iterator<Entry<NodeProperties, HashSet<NodeProperties>>> it = attr.entrySet().iterator();
		while(it.hasNext()) {
			Entry<NodeProperties, HashSet<NodeProperties>> ent = it.next();
			Iterator<NodeProperties> attr_prop_set = ent.getValue().iterator();
			while(attr_prop_set.hasNext()) {
				NodeProperties the_node = attr_prop_set.next();
				if (the_node==method) {
					result.add(ent.getKey());
				}
			}
		}
		return result;	
	}		
}

