package jdt.jdt;
import java.io.*;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
public class VariablePrinter {
	public static String readFromFile(String filePath) {
		StringBuilder stringBuilder = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filePath));
			String line = null;
			String ls = System.getProperty("line.separator");
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line);
				stringBuilder.append(ls);
			}
			reader.close();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return stringBuilder.toString();
	}
	public static ASTNode getASTNode(String codeFragment) {
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setSource(codeFragment.toCharArray());
		parser.setResolveBindings(false);
		
		ASTNode node = null;
		try {
			node = (CompilationUnit)parser.createAST(null);
			
		}catch(Exception e) {
			return null;
		}
		return node;
		
	}
	public static void printVariables(String codeFragment) {
		ASTNode node = getASTNode(codeFragment);
		node.accept(new ASTVisitor(){
			int varCount = 0;
			public boolean visit(VariableDeclarationFragment node) {
				System.out.println(node.getName().toString());
				return super.visit(node);
			};
		});
	}
	public static void main(String[] args) {
		String codeFragment = readFromFile("/home/swarnadeep/TestPGA.java");
		printVariables(codeFragment);
	}
}
