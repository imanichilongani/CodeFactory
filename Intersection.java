import java.util.HashSet;

import com.github.javaparser.ast.Node;

public class Intersection {
	
	public float intersection(HashSet<Node> x, HashSet<Node> y){
		
		int count =0;
		for(Node z:x){
			if (y.contains(z)){
				count = count +1;
				
			}
		}
		
		
		return count;
		
	}

}
