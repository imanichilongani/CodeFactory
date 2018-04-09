
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
}
