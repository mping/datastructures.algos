package org.datastructures.algos.xform;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.BooleanLiteralExpr;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.NullLiteralExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.utils.Pair;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RemoveImplsTask extends DefaultTask {

  @TaskAction
  public void greet() throws IOException {
    String rootDir = "src/main/java";

    Files.walk(Paths.get(rootDir))
        .filter(f -> f.toFile().getParent().endsWith("impl"))
        .map(f -> new Pair<>(f.toFile(), replaceImplementation(f)))
        .forEach(pair -> writeToFile(pair.a, pair.b));
  }

  void writeToFile(File f, String contents) {
    try {
      new FileOutputStream(f).write(contents.getBytes());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  String replaceImplementation(Path fileName) {
    CompilationUnit compilationUnit;
    try {
      System.out.printf("[CodeTransformer] Processing classfile %s\n", fileName.toString());
      compilationUnit = StaticJavaParser.parse(new FileInputStream(fileName.toFile()));
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }

    compilationUnit
        .findAll(ClassOrInterfaceDeclaration.class).stream()
        .forEach(c -> {

          if (c.isInnerClass() || c.isStatic()) {
            compilationUnit.remove(c);
            return;
          }

          //remove fields
          c.getFields()
              .stream()
              .forEach(c::remove);

          //set constructor to empty
          c.getConstructors()
              .stream()
              .forEach(cons -> cons.setBody(new BlockStmt()));

          // remove private/protected methods
          c.getMethods()
              .stream()
              .filter(m -> m.isPrivate() || m.isProtected() || m.isDefault())
              .forEach(c::remove);

          // delete @Override method bodies
          c.getMethods()
              .stream()
              .filter(m -> m.getAnnotationByName(Override.class.getSimpleName()).isPresent())
              .forEach(m -> {
                BlockStmt body = new BlockStmt();
                Type type = m.getType();
                if (!type.isVoidType()) {
                  ReturnStmt ret;
                  if (type.isPrimitiveType()) {
                    if (type.asPrimitiveType().equals(PrimitiveType.booleanType())) {
                      ret = new ReturnStmt(new BooleanLiteralExpr(false));
                    } else if (type.asPrimitiveType().equals(PrimitiveType.intType())) {
                      ret = new ReturnStmt(new IntegerLiteralExpr(0));
                    } else {
                      // impls only use int's or boolean's
                      ret = null;
                    }
                  } else {
                    ret = new ReturnStmt(new NullLiteralExpr());
                  }

                  body.addStatement(ret);
                }
                m.setBody(body);
              });

        });
    return compilationUnit.toString();
  }
}
