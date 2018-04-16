import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;

public class Main {
	public static void main(String[] args) throws FileNotFoundException {		
		CompilationUnit cu = Property.get_compilation_unit("/Users/imanichilongani/Desktop/test1.java");
		CompilationUnit cu1 = Property.get_compilation_unit("/Users/imanichilongani/Desktop/test2.java");
		HashMap<NodeProperties, HashSet<NodeProperties>> attr_prop = (HashMap<NodeProperties, HashSet<NodeProperties>>) AttributePropertyFinder.get_attributes(cu);
		HashMap<NodeProperties, HashSet<NodeProperties>> attr_prop1 = (HashMap<NodeProperties, HashSet<NodeProperties>>) AttributePropertyFinder.get_attributes(cu1);
		attr_prop.putAll(attr_prop);
		HashMap<NodeProperties,HashSet<NodeProperties>> attribute_prop = (HashMap<NodeProperties, HashSet<NodeProperties>>) AttributePropertyFinder.attributepropertyfinder(attr_prop, cu);
		HashMap<NodeProperties,HashSet<NodeProperties>> attribute1_prop = (HashMap<NodeProperties, HashSet<NodeProperties>>) AttributePropertyFinder.attributepropertyfinder(attr_prop, cu1);
		attribute_prop.putAll(attribute1_prop);
		HashMap<MethodDeclaration, String> methods = (HashMap<MethodDeclaration, String>) MethodPropertyFinder.get_methods(cu);
		HashMap<NodeProperties, HashSet<NodeProperties>> method_prop = (HashMap<NodeProperties, HashSet<NodeProperties>>) MethodPropertyFinder.get_method_map(methods, attribute_prop);
		HashMap<NodeProperties, HashMap<NodeProperties, Float>> result = new HashMap<NodeProperties, HashMap<NodeProperties, Float>>();
		
		HashMap<MethodDeclaration, String> methods1 = (HashMap<MethodDeclaration, String>) MethodPropertyFinder.get_methods(cu1);
		HashMap<NodeProperties, HashSet<NodeProperties>> method_prop1 = (HashMap<NodeProperties, HashSet<NodeProperties>>) MethodPropertyFinder.get_method_map(methods1, attribute1_prop);
		method_prop.putAll(method_prop1);
		Iterator<Entry<NodeProperties, HashSet<NodeProperties>>> entity_x = method_prop.entrySet().iterator();
		Distance d = new Distance();
		while (entity_x.hasNext() ) {
			Entry<NodeProperties, HashSet<NodeProperties>> x = entity_x.next();
			HashMap<NodeProperties, Float> distances_x = new HashMap<NodeProperties, Float>();
			Iterator<Entry<NodeProperties, HashSet<NodeProperties>>> entity_y = method_prop.entrySet().iterator();
			while(entity_y.hasNext()) {
				Entry<NodeProperties, HashSet<NodeProperties>> y = entity_y.next();
				System.out.println("Method: " + y.getKey().name);
				Iterator<NodeProperties> th = y.getValue().iterator();
				while (th.hasNext()) {
						NodeProperties nde = th.next();
						System.out.println("prop: " + nde.name);
						System.out.println("class: " + nde.from_class);
					}
				System.out.println("--------------");
			}
			while(entity_y.hasNext()) {
				Entry<NodeProperties, HashSet<NodeProperties>> y = entity_y.next();
				float val = d.get_distance(x.getValue(), y.getValue());
				if (val==0.0) {
					Iterator<NodeProperties> st = x.getValue().iterator();
					while (st.hasNext()) {
						NodeProperties nde = st.next();
					}
					Iterator<NodeProperties> st2 = y.getValue().iterator();
					while (st2.hasNext()) {
						NodeProperties nde = st2.next();
					}
				}
				distances_x.put(y.getKey(), d.get_distance(x.getValue(), y.getValue()) );
			}
			result.put(x.getKey(), distances_x);
		}
		
	
		//Print 
		Iterator<Entry<NodeProperties, HashMap<NodeProperties, Float>>> res = result.entrySet().iterator();
		while (res.hasNext()) {
			Entry<NodeProperties, HashMap<NodeProperties, Float>> ent = res.next();
			System.out.println(ent.getKey().name);
			Iterator<Entry<NodeProperties, Float>> values = ent.getValue().entrySet().iterator();
			while (values.hasNext()) {
				Entry<NodeProperties, Float> value = values.next();
				System.out.println(value.getKey().name + ": " + value.getValue());
			}
			System.out.println("--------------------------");
		}
	}
}
