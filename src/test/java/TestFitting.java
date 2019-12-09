import org.apache.commons.math3.fitting.WeightedObservedPoints;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

import java.util.Arrays;


interface TrendLine {
  public void setValues(double[] y, double[] x); // y ~ f(x)

  public double predict(double x); // get a predicted y for a given x
}


abstract class OLSTrendLine implements TrendLine {

  RealMatrix coef = null; // will hold prediction coefs once we get values
  OLSMultipleLinearRegression ols;

  protected abstract double[] xVector(double x); // create vector of values from x

  protected abstract boolean logY(); // set true to predict log of y (note: y must be positive)

  @Override
  public void setValues(double[] y, double[] x) {
    if (x.length != y.length) {
      throw new IllegalArgumentException(String.format("The numbers of y and x values must be equal (%d != %d)", y.length, x.length));
    }
    double[][] xData = new double[x.length][];
    for (int i = 0; i < x.length; i++) {
      // the implementation determines how to produce a vector of predictors from a single x
      xData[i] = xVector(x[i]);
    }
    if (logY()) { // in some models we are predicting ln y, so we replace each y with ln y
      y = Arrays.copyOf(y, y.length); // user might not be finished with the array we were given
      for (int i = 0; i < x.length; i++) {
        y[i] = Math.log(y[i]);
      }
    }
    ols = new OLSMultipleLinearRegression();
    ols.setNoIntercept(true); // let the implementation include a constant in xVector if desired
    ols.newSampleData(y, xData); // provide the data to the model

    coef = MatrixUtils.createColumnRealMatrix(ols.estimateRegressionParameters()); // get our coefs
  }

  @Override
  public double predict(double x) {
    double yhat = coef.preMultiply(xVector(x))[0]; // apply coefs to xVector
    if (logY()) yhat = (Math.exp(yhat)); // if we predicted ln y, we still need to get y
    return yhat;
  }
}

class PolyTrendLine extends OLSTrendLine {
  final int degree;

  public PolyTrendLine(int degree) {
    if (degree < 0) throw new IllegalArgumentException("The degree of the polynomial must not be negative");
    this.degree = degree;
  }

  protected double[] xVector(double x) { // {1, x, x*x, x*x*x, ...}
    double[] poly = new double[degree + 1];
    double xi = 1;
    for (int i = 0; i <= degree; i++) {
      poly[i] = xi;
      xi *= x;
    }
    return poly;
  }

  @Override
  protected boolean logY() {
    return false;
  }
}

// https://stackoverflow.com/questions/17592139/trend-lines-regression-curve-fitting-java-library/17634728
public class TestFitting {

  public static void main(String[] args) {
    double[] xvals = {10,
        100,
        1000,
        2500,
        10000};

    double[] yvals = {
        0.19227651949091287,
        1.9485861737372534,
        19.299185546277933,
        51.70082742263944,
        180.83947695669448
    };

    final WeightedObservedPoints obs = new WeightedObservedPoints();
    for (int i = 0; i < xvals.length; i++) {
      obs.add(xvals[i], yvals[i]);
    }

    PolyTrendLine t = new PolyTrendLine(0);
    t.setValues(yvals, xvals);
    System.out.println(t.ols.estimateErrorVariance());

    t = new PolyTrendLine(1);
    t.setValues(yvals, xvals);
    System.out.println(t.ols.estimateErrorVariance());

    t = new PolyTrendLine(2);
    t.setValues(yvals, xvals);
    System.out.println(t.ols.estimateErrorVariance());
  }
}