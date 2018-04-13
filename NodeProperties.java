
public class NodeProperties {
	enum Type{
		METHOD,
		ATTRIBUTE
	}
	public String name;
	public String from_class;
	public Type type;
	
	public NodeProperties(String the_name, String the_class, Type the_type) {
		name = the_name;
		from_class = the_class;
		type = the_type;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof NodeProperties)) {
            return false;
        }
		final NodeProperties node = (NodeProperties) obj;
		if (this.name != node.name ){
			return false;
		} 
		if (this.from_class != node.from_class ){
			return false;
		} 
		if (this.type != node.type ){
			return false;
		} 
		return true;
	}
}
