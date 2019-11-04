package org.datastructures.algos.benchmark;

import org.apache.commons.math3.analysis.ParametricUnivariateFunction;
import org.apache.commons.math3.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math3.fitting.AbstractCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoint;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresBuilder;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresProblem;
import org.apache.commons.math3.linear.DiagonalMatrix;
import org.apache.commons.math3.linear.RealVector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class DAFitter {
  /*

Inserts:

Name arrayList
size:10, elapsed: 0.15729305385092499
size:100, elapsed: 1.4962465367494369
size:1000, elapsed: 15.243277256739326
size:2500, elapsed: 30.49370349828025
size:10000, elapsed: 123.03258979071495
Name linkedList
size:10, elapsed: 159.87557777503608
size:100, elapsed: 1606.683179902909
size:1000, elapsed: 16792.51463157895
size:2500, elapsed: 43554.39233333334
size:10000, elapsed: 173813.285

   */
  public static void main(String[] args) {

    double[] xs = {
        10,
        100,
        1000,
        2500,
        10000
    };

    double[] zs = {
        0.1572532293365471,
        1.7772657460591823,
        14.043162845970947,
        30.21218710466327,
        135.06562407177316};

    ArrayList<WeightedObservedPoint> points = new ArrayList<>();

    // Add points here; for instance,
    for (int i = 0; i < xs.length; i++) {
      WeightedObservedPoint point = new WeightedObservedPoint(1.0,
          xs[i],
          zs[i]);
      points.add(point);
    }

    FunctionFitter fitter = new FunctionFitter(new LinearFunc());
    final double coeffs[] = fitter.fit(points);
    System.out.println(Arrays.toString(coeffs));
    System.out.println(fitter.residuals(points));
    System.out.println(fitter.rms(points));
  }

  static class LinearFunc implements ParametricUnivariateFunction {
    public double value(double t, double... parameters) {
      //a + m*b
      // return parameters[0] + parameters[1] * t;
      System.out.println(" t: " +t );
      return parameters[0] + Math.log(t);
    }

    // Jacobian matrix of the above. In this case, this is just an array of
    // partial derivatives of the above function, with one element for each parameter.
    public double[] gradient(double t, double... parameters) {
      final double a = parameters[0];
      final double b = parameters[1];
      final double c = parameters[2];

      // Jacobian Matrix Edit

      // Using Derivative Structures...
      // constructor takes 4 arguments - the number of parameters in your
      // equation to be differentiated (3 in this case), the order of
      // differentiation for the DerivativeStructure, the index of the
      // parameter represented by the DS, and the value of the parameter itself
      DerivativeStructure aDev = new DerivativeStructure(3, 1, 0, a);
      DerivativeStructure bDev = new DerivativeStructure(3, 1, 1, b);
      DerivativeStructure cDev = new DerivativeStructure(3, 1, 2, c);

      // define the equation to be differentiated using another DerivativeStructure
      DerivativeStructure y = aDev.multiply(DerivativeStructure.pow(t, bDev))
          .multiply(cDev.negate().multiply(t).exp());

      // then return the partial derivatives required
      // notice the format, 3 arguments for the method since 3 parameters were
      // specified first order derivative of the first parameter, then the second,
      // then the third
      return new double[]{
          y.getPartialDerivative(1, 0, 0),
          y.getPartialDerivative(0, 1, 0),
          y.getPartialDerivative(0, 0, 1)
      };

    }
  }

  static class FunctionFitter extends AbstractCurveFitter {

    private ParametricUnivariateFunction func;

    public FunctionFitter(ParametricUnivariateFunction f) {
      func = f;
    }

    public RealVector residuals(Collection<WeightedObservedPoint> points) {
      return getOptimizer().optimize(getProblem(points)).getResiduals();
    }

    public double rms(Collection<WeightedObservedPoint> points) {
      return getOptimizer().optimize(getProblem(points)).getRMS();
    }

    protected LeastSquaresProblem getProblem(Collection<WeightedObservedPoint> points) {
      final int len = points.size();
      final double[] target = new double[len];
      final double[] weights = new double[len];
      final double[] initialGuess = {1.0, 1.0, 1.0};

      int i = 0;
      for (WeightedObservedPoint point : points) {
        target[i] = point.getY();
        weights[i] = point.getWeight();
        i += 1;
      }

      final AbstractCurveFitter.TheoreticalValuesFunction model = new AbstractCurveFitter.TheoreticalValuesFunction(func, points);

      return new LeastSquaresBuilder().
          maxEvaluations(Integer.MAX_VALUE).
          maxIterations(Integer.MAX_VALUE).
          start(initialGuess).
          target(target).
          weight(new DiagonalMatrix(weights)).
          model(model.getModelFunction(), model.getModelFunctionJacobian()).
          build();
    }
  }
}
