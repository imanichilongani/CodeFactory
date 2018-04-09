import java.util.HashSet;
import java.util.Map;

import com.github.javaparser.ast.body.MethodDeclaration;

public class Distance {
	public static float get_distance(HashSet<NodeProperties> prop_x, HashSet<NodeProperties> prop_y) {
		float inter = Intersection.intersection(prop_x,prop_y);
		float union = Union.union(prop_x,prop_y);
		float inter_over_union = Math.abs(inter)/Math.abs(union);
		return (1-inter_over_union);
		
	}
}
