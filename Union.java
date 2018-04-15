import java.util.HashSet;

import com.github.javaparser.ast.Node;

public class Union {
	
	public float union(HashSet<NodeProperties> x, HashSet<NodeProperties> y){
		
		HashSet<NodeProperties> union = new HashSet<NodeProperties>();
		union.addAll(x);
		for(NodeProperties z:y){
			if(!union.contains(z)){
				union.add(z);
			}
		}
		
		return union.size();
		
	}

}
