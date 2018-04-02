
public class NodeProperties {
	enum Type{
		METHOD,
		ATTRIBUTE
	}
	String name;
	String from_class;
	Type type;
	
	public NodeProperties(String the_name, String the_class, Type the_type) {
		name = the_name;
		from_class = the_class;
		type = the_type;
	}
}
