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
		CompilationUnit cu = Property.get_compilation_unit("/Users/imanichilongani/Documents/workspace/A4/src/Human.java");
		Map<NodeProperties, HashSet<NodeProperties>> attr_prop = AttributePropertyFinder.get_attributes(cu);		
		Map<MethodDeclaration, String> methods = MethodPropertyFinder.get_methods(cu);
		Map<NodeProperties, HashSet<NodeProperties>> method_prop = MethodPropertyFinder.get_method_map(methods, attr_prop);
		HashMap<NodeProperties, HashMap<NodeProperties, Float>> result = new HashMap<NodeProperties, HashMap<NodeProperties, Float>>();
		Iterator<Entry<NodeProperties, HashSet<NodeProperties>>> entity_x = method_prop.entrySet().iterator();
		while (entity_x.hasNext() ) {
			Entry<NodeProperties, HashSet<NodeProperties>> x = entity_x.next();
			HashMap<NodeProperties, Float> distances_x = new HashMap<NodeProperties, Float>();
			Iterator<Entry<NodeProperties, HashSet<NodeProperties>>> entity_y = method_prop.entrySet().iterator();
			while(entity_y.hasNext()) {
				Entry<NodeProperties, HashSet<NodeProperties>> y = entity_y.next();
				distances_x.put(y.getKey(), Distance.get_distance(x.getValue(), y.getValue()) );
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
