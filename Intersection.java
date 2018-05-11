import java.util.HashSet;

import com.github.javaparser.ast.Node;

public class Intersection {
	
	public float intersection(HashSet<NodeProperties> x, HashSet<NodeProperties> y){
		
		int count =0;
		for(NodeProperties z:x){
			for(NodeProperties a:y){
				if(z.name.equals(a.name)){
					count=count+1;
				}
			}
		}
		
		
		return count;
		
	}

}
