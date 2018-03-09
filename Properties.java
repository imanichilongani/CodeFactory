import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.Expression;


public class Properties {
	public static void main(String[] args) throws Exception {
		property();
	}
	
	public static List<Node> property() throws FileNotFoundException {
		FileInputStream in = new FileInputStream("/Users/imanichilongani/Documents/workspace/A4/src/Human.java");	
		CompilationUnit cu = JavaParser.parse(in);
		@SuppressWarnings("deprecation")
		List<ClassOrInterfaceDeclaration> rtt = cu.getNodesByType(ClassOrInterfaceDeclaration.class);
		for (int i=0; i<rtt.size();i++) {
			System.out.println(rtt.get(i).getNameAsString());
		}
		List<MethodDeclaration> mthds = cu.getNodesByType(MethodDeclaration.class);
		List<Expression> expsns = new ArrayList<Expression>();
		for (int i=0; i<mthds.size();i++) {	
			expsns.addAll(mthds.get(i).getChildNodesByType(Expression.class));
		}
		for (int i=0; i<expsns.size();i++) {
			if (expsns.get(i).isAssignExpr()) {
				AssignExpr assign = (AssignExpr) expsns.get(i);
				System.out.println(expsns.get(i).toString());
				System.out.println(assign.getValue().toString());
			}
		}
		return null;
		//if(A.a==st)
		//A.a = 2
		//x = A.a + 4
	}
	
}

