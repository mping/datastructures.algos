package org.datastructures.algos.benchmark;

import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.ChainedOptionsBuilder;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;
import org.openjdk.jmh.runner.options.VerboseMode;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public abstract class DABenchmark {

  public static class Result {
    final String name;
    final Integer size;
    final Double elapsed;

    Result(String name, Integer size, Double elapsed) {
      this.name = name;
      this.size = size;
      this.elapsed = elapsed;
    }
  }

  public static Integer[] getRandomValues(int size, int seed) {
    return getRandomValues(size, false, new Random(seed));
  }

  public static Integer[] getRandomValues(int size, boolean nonNegative, Random random) {
    final Integer[] results = new Integer[size];
    for (int i = 0; i < size; i++) {
      final int value = random.nextInt(size) - (nonNegative ? 0 : (size / 2));
      results[i] = value;
    }
    return results;
  }

  public static Map<String, List<Result>> startBenchmark(int warmupIterations, int measurementIterations, int millis, VerboseMode verboseMode) {
    Collection<RunResult> result = run(warmupIterations, measurementIterations, millis, verboseMode);

    Map<String, List<Result>> aggr = result.stream().map(r -> {
      String name = r.getPrimaryResult().getLabel();
      Integer size = Integer.valueOf(r.getParams().getParam("CONTAINER_SIZE"));
      Double elapsed = r.getPrimaryResult().getScore();
      return new Result(name, size, elapsed);
    }).collect(Collectors.groupingBy(m -> m.name, Collectors.toList()));

    for (Map.Entry<String, List<Result>> e : aggr.entrySet()) {
      System.out.println("Name " + e.getKey());
      // TODO infer Big-O from size x elapsed
      for (Result r : e.getValue()) {
        System.out.println("size:" + r.size + ", elapsed: " + r.elapsed);
      }
    }
    return aggr;

  }

  public static Collection<RunResult> run(int warmupIterations, int measurementIterations, int millis, VerboseMode verboseMode) {
    try {
      final ChainedOptionsBuilder builder = new OptionsBuilder()
          .shouldDoGC(true)
          .verbosity(verboseMode)
          .shouldFailOnError(true)
          .mode(Mode.AverageTime)
          .timeUnit(TimeUnit.MICROSECONDS)
          .warmupTime(TimeValue.milliseconds(millis))
          .warmupIterations(warmupIterations)
          .measurementTime(TimeValue.milliseconds(millis))
          .measurementIterations(measurementIterations)
          .forks(1)
          /* We are using 4Gb and setting NewGen to 100% to avoid GC during testing.
             Any GC during testing will destroy the iteration (i.e. introduce unreliable noise in the measurement), which should get ignored as an outlier */
          .jvmArgsAppend("-XX:+UseG1GC", "-Xss100m", "-Xms4g", "-Xmx4g", "-XX:MaxGCPauseMillis=1000", "-XX:+UnlockExperimentalVMOptions", "-XX:G1NewSizePercent=100", "-XX:G1MaxNewSizePercent=100");

      return new Runner(builder.build()).run();
    } catch (RunnerException e) {
      throw new RuntimeException(e);
    }
  }
}
