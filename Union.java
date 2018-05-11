import java.util.HashSet;

import com.github.javaparser.ast.Node;

public class Union {
	
	public float union(HashSet<NodeProperties> x, HashSet<NodeProperties> y){
		
		HashSet<NodeProperties> union = new HashSet<NodeProperties>();
		union.addAll(x);
		for(NodeProperties r:x){
			System.out.println(r.name);
		}
		System.out.println("----------------");
		for(NodeProperties r:y){
			System.out.println(r.name);
		}
		System.out.println("----------------");
		boolean in = false;
		for(NodeProperties z:y){
			for(NodeProperties d:union){
				if( d.name.equals(z.name)){
					in=true;
				}
				
			}
			if(!in){
				union.add(z);
				in=false;
			}
			in=false;
		}
		for(NodeProperties z: union){
			System.out.println(z.name);
		}
		System.out.println("--------------------------------------");
		return union.size();
		
	}
	

}
