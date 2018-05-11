import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;

public class MoveDecider {
	
	public static String moveDecider(HashMap<NodeProperties, HashMap<NodeProperties, Float>> result){
		float countClass1=0;
		float countClass2=0;
		int counter1=0;
		int counter2=0;
		String Class1 = "";
		String Class2="";
		String recommendations = "";
		
		Iterator<Entry<NodeProperties, HashMap<NodeProperties, Float>>> res = result.entrySet().iterator();
		while (res.hasNext()) {
			Entry<NodeProperties, HashMap<NodeProperties, Float>> ent = res.next();
			Iterator<Entry<NodeProperties, Float>> values = ent.getValue().entrySet().iterator();
			while (values.hasNext()) {
				Entry<NodeProperties, Float> value = values.next();
				if(value.getValue()!=0.0){
					if (value.getKey().from_class.equals(ent.getKey().from_class)){
						countClass1=countClass1+value.getValue();
						counter1=counter1+1;
						Class1=value.getKey().from_class;
					}else{
						countClass2=countClass2+value.getValue();;
						counter2=counter2+1;
						Class2 = ent.getKey().from_class;
					}
				}
				
			}
			float average = countClass1/counter1;
			float average2=countClass2/counter2;
			System.out.println(average);
			System.out.println(average2);
			if ((countClass1/counter1)>(countClass2/counter2)){
				recommendations = recommendations + ent.getKey().name + " should be moved to " + Class2 +System.lineSeparator() ;
				countClass1=0;
			    countClass2=0;
			    counter1=0;
				counter2=0;
			}else{
				recommendations = recommendations + ent.getKey().name + " should stay in " +Class1 +System.lineSeparator() ;
				countClass1=0;
			    countClass2=0;
			    counter1=0;
				counter2=0;
			}
		} 
	 return recommendations;}

}
