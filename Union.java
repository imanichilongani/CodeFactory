import java.util.HashSet;

import com.github.javaparser.ast.Node;

public class Union {
	
	public float union(HashSet<Node> x, HashSet<Node> y){
		
		HashSet<Node> union = x;
		for(Node z:y){
			if(!union.contains(z)){
				union.add(z);
			}
		}
		
		return union.size();
		
	}

}
