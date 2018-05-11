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
		CompilationUnit cu = Property.get_compilation_unit("/Users/akshatsingh/CodeFactory/test1.java");
		CompilationUnit cu1 = Property.get_compilation_unit("/Users/akshatsingh/CodeFactory/test2.java");
		AttributePropertyFinder t = new AttributePropertyFinder();
		HashMap<NodeProperties, HashSet<NodeProperties>> attr_prop = (HashMap<NodeProperties, HashSet<NodeProperties>>) t.get_attributes(cu);
		HashMap<NodeProperties, HashSet<NodeProperties>> attr_prop1 = (HashMap<NodeProperties, HashSet<NodeProperties>>) t.get_attributes(cu1);
		attr_prop.putAll(attr_prop1);
		HashMap<NodeProperties, HashSet<NodeProperties>> attr_prop3 = (HashMap<NodeProperties, HashSet<NodeProperties>>) t.get_attributes(cu);
		HashMap<NodeProperties, HashSet<NodeProperties>> attr_prop4 = (HashMap<NodeProperties, HashSet<NodeProperties>>) t.get_attributes(cu1);
		attr_prop3.putAll(attr_prop4);
		HashMap<NodeProperties,HashSet<NodeProperties>> attribute_prop = (HashMap<NodeProperties, HashSet<NodeProperties>>) t.attributepropertyfinder(attr_prop, cu);
		Iterator<Entry<NodeProperties, HashSet<NodeProperties>>> entity_g = attribute_prop.entrySet().iterator();
		while(entity_g.hasNext()) {
			Entry<NodeProperties, HashSet<NodeProperties>> y = entity_g.next();
			System.out.println("Attribute: " + y.getKey().name);
			Iterator<NodeProperties> th = y.getValue().iterator();
			while (th.hasNext()) {
					NodeProperties nde = th.next();
					System.out.println("prop: " + nde.name);
					System.out.println("class: " + nde.from_class);
				}
			System.out.println("--------------");
		} 
		System.out.println("--------------------------------------");
		HashMap<NodeProperties,HashSet<NodeProperties>> attribute1_prop = (HashMap<NodeProperties, HashSet<NodeProperties>>)t.attributepropertyfinder(attr_prop3, cu1);
		Iterator<Entry<NodeProperties, HashSet<NodeProperties>>> attr = attribute1_prop.entrySet().iterator();
		
		while (attr.hasNext()) {
			Entry<NodeProperties, HashSet<NodeProperties>> it = attr.next();
			Iterator<Entry<NodeProperties, HashSet<NodeProperties>>> check = attribute_prop.entrySet().iterator();
			boolean new_value = true;
			while(check.hasNext()) {
				Entry<NodeProperties, HashSet<NodeProperties>> check_item = check.next();
				HashSet<NodeProperties> item = attribute_prop.get(check_item.getKey());
				if (it.getKey().name==check_item.getKey().name) {
					Iterator<NodeProperties> add_v= it.getValue().iterator();
					while(add_v.hasNext()) {
						NodeProperties add_v_node = add_v.next();
						//HashSet<NodeProperties> item = attribute_prop.get(check_item.getKey());
						//attribute_prop.get(check_item.getKey()).add(add_v_node);
						item.add(add_v_node);
					}
					attribute_prop.put(check_item.getKey(), item);
					new_value = false;
					break;
				} 
			}
			if (new_value) {
				attribute_prop.put(it.getKey(), it.getValue());
				new_value = true;
			}
		}
		
		
		Iterator<Entry<NodeProperties, HashSet<NodeProperties>>> entity_z = attribute_prop.entrySet().iterator();
		while(entity_z.hasNext()) {
			Entry<NodeProperties, HashSet<NodeProperties>> y = entity_z.next();
			System.out.println("Attribute: " + y.getKey().name);
			Iterator<NodeProperties> th = y.getValue().iterator();
			while (th.hasNext()) {
					NodeProperties nde = th.next();
					System.out.println("prop: " + nde.name);
					System.out.println("class: " + nde.from_class);
				}
			System.out.println("--------------");
		} 
		System.out.println("--------------------------------------");
		HashMap<MethodDeclaration, String> methods = (HashMap<MethodDeclaration, String>) MethodPropertyFinder.get_methods(cu);
		HashMap<NodeProperties, HashSet<NodeProperties>> method_prop = (HashMap<NodeProperties, HashSet<NodeProperties>>) MethodPropertyFinder.get_method_map(methods, attribute_prop);
		HashMap<NodeProperties, HashMap<NodeProperties, Float>> result = new HashMap<NodeProperties, HashMap<NodeProperties, Float>>();
		
		HashMap<MethodDeclaration, String> methods1 = (HashMap<MethodDeclaration, String>) MethodPropertyFinder.get_methods(cu1);
		HashMap<NodeProperties, HashSet<NodeProperties>> method_prop1 = (HashMap<NodeProperties, HashSet<NodeProperties>>) MethodPropertyFinder.get_method_map(methods1, attribute_prop);
		Iterator<Entry<NodeProperties, HashSet<NodeProperties>>> meth = method_prop1.entrySet().iterator();
		while (meth.hasNext()) {
			Entry<NodeProperties, HashSet<NodeProperties>> it1 = meth.next();
			if (method_prop.containsKey(it1.getKey())) {
				method_prop.get(it1.getKey()).addAll(it1.getValue());
			} else {
				method_prop.put(it1.getKey(), it1.getValue());
			}
		}
		Iterator<Entry<NodeProperties, HashSet<NodeProperties>>> entity_a= method_prop.entrySet().iterator();
		while(entity_a.hasNext()) {
			Entry<NodeProperties, HashSet<NodeProperties>> y = entity_a.next();
			System.out.println("Attribute: " + y.getKey().name);
			Iterator<NodeProperties> th = y.getValue().iterator();
			while (th.hasNext()) {
					NodeProperties nde = th.next();
					System.out.println("prop: " + nde.name);
					System.out.println("class: " + nde.from_class);
				}
			System.out.println("--------------");
		}
		System.out.println("--------------------------------------");

		method_prop.putAll(attribute_prop);
		
		Iterator<Entry<NodeProperties, HashSet<NodeProperties>>> entity_x = method_prop.entrySet().iterator();
		Distance d = new Distance();
		while (entity_x.hasNext() ) {
			Entry<NodeProperties, HashSet<NodeProperties>> x = entity_x.next();
			HashMap<NodeProperties, Float> distances_x = new HashMap<NodeProperties, Float>();
			Iterator<Entry<NodeProperties, HashSet<NodeProperties>>> entity_y = method_prop.entrySet().iterator();
			while(entity_y.hasNext()) {
				Entry<NodeProperties, HashSet<NodeProperties>> y = entity_y.next();
				System.out.println(x.getKey().name.toUpperCase());
				System.out.println(y.getKey().name.toUpperCase());
				float val = d.get_distance(x.getValue(), y.getValue());
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
		String reco = MoveDecider.moveDecider(result);
		System.out.println(reco);
	}
	
}