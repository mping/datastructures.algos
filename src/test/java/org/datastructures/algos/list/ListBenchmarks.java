package org.datastructures.algos.list;

import org.datastructures.algos.benchmark.DABenchmark;
import org.datastructures.algos.list.impl.DAArrayList;
import org.datastructures.algos.list.impl.DALinkedList;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.options.VerboseMode;

import java.util.ArrayList;

public class ListBenchmarks extends DABenchmark {

  public static void main(String... args) {
    startBenchmark(3, 3, 300, VerboseMode.NORMAL);
  }

  @State(Scope.Benchmark)
  public static class Base {
    @Param({"10", "100", "1000", "2500", "10000"})
    public int CONTAINER_SIZE;

    Integer[] ELEMENTS;

    DALinkedList daLinkedList;
    DAArrayList daArrayList;
    ArrayList javaList;

    @Setup(Level.Iteration)
    public void setup() {
      ELEMENTS = getRandomValues(CONTAINER_SIZE, 0);

      daLinkedList = new DALinkedList();
      daArrayList = new DAArrayList();
      javaList = new ArrayList();
    }
  }


  public static class Insert extends Base {

    @Benchmark
    public Object DALinkedList() {
      for (Integer i : ELEMENTS) {
        daLinkedList.add(i);
      }
      return daLinkedList;
    }

    @Benchmark
    public Object DAArrayList() {
      for (Integer i : ELEMENTS) {
        daArrayList.add(i);
      }
      return daArrayList;
    }
    @Benchmark
    public Object JavaArrayList() {
      for (Integer i : ELEMENTS) {
        javaList.add(i);
      }
      return javaList;
    }
  }

}
