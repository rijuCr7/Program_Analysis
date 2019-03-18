package jdt.jdt;
import java.io.*;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.InfixExpression;

import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.WhileStatement;

public class Code2Vec {
	//global variables
	public static StringBuilder vector = new StringBuilder("00000");
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
	public static void convert2Vec(String codeFragment) {
		ASTNode node = getASTNode(codeFragment);
		node.accept(new ASTVisitor(){
			int varCount = 0;
			public boolean visit(VariableDeclarationFragment node) {
				Code2Vec.vector.setCharAt(0, '1');  
				return super.visit(node);
			};
			public boolean visit(InfixExpression node) {
				Code2Vec.vector.setCharAt(1, '1');
				return super.visit(node);
			};
			public boolean visit(ExpressionStatement node) {
				Code2Vec.vector.setCharAt(2, '1');
				return super.visit(node);
			};
			public boolean visit(IfStatement node) {
				Code2Vec.vector.setCharAt(3, '1');
				return super.visit(node);
			};
			public boolean visit(ForStatement node) {
				Code2Vec.vector.setCharAt(4, '1');
				return super.visit(node);
			};
			public boolean visit(WhileStatement node) {
				Code2Vec.vector.setCharAt(4, '1');
				return super.visit(node);
			};
		});
	}
	public static void main(String[] args) {
		String codeFragment = readFromFile("/home/swarnadeep/TestPGA2.java");
		System.out.println("The Vector Represantation For Supplied Code is");
		convert2Vec(codeFragment);
		System.out.println(Code2Vec.vector);
	}
}