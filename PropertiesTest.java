import static org.junit.jupiter.api.Assertions.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;

class PropertiesTest {

	@Test
	void testGet_compilation_unit() throws FileNotFoundException {
		FileInputStream in = new FileInputStream("/Users/imanichilongani/Documents/workspace/A4/src/Human.java");
		CompilationUnit cu = JavaParser.parse(in);
		assertEquals(cu, Properties.get_compilation_unit("/Users/imanichilongani/Documents/workspace/A4/src/Human.java"));
	}

	@Test
	void testGet_attributes() throws FileNotFoundException {
		FileInputStream in = new FileInputStream("/Users/imanichilongani/Documents/workspace/A4/src/Human.java");
		CompilationUnit cu = JavaParser.parse(in);
		assertEquals(8,Properties.get_attributes(cu).size());
	}

}
