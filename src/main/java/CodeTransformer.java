import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.utils.Pair;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CodeTransformer {

  public static void main(String[] args) throws Throwable {
    Files.walk(Paths.get("src/main/java"))
        .filter(f -> f.toFile().getParent().endsWith("impl"))
        .map(f -> new Pair<>(f.toFile(), replaceImplementation(f)))
        .forEach(pair -> writeToFile(pair.a, pair.b));
  }

  static void writeToFile(File f, String contents) {
    try {
      new FileOutputStream(f).write(contents.getBytes());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  static String replaceImplementation(Path fileName) {
    CompilationUnit compilationUnit;
    try {
      compilationUnit = StaticJavaParser.parse(new FileInputStream(fileName.toFile()));
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }

    return compilationUnit
        .findAll(ClassOrInterfaceDeclaration.class).stream()
        .map(c -> {
          c.getMethods()
              .stream()
              .filter(m -> m.getAnnotationByName(Override.class.getSimpleName()).isPresent())
              .forEach(m -> m.setBody(new BlockStmt()));

          return c.toString();
        }).findFirst().get();
  }
}
