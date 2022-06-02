package collections;

import java.io.Serializable;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

@SuppressWarnings("rawtypes | unchecked | unused")
public class Range<C extends Comparable> implements Serializable {

  private static final Comparable infinity = c -> Integer.MAX_VALUE;

  private final C lowerBound;
  private final C upperBound;
  private final boolean lowerBoundInclusive;
  private final boolean upperBoundInclusive;

  private Range(C lowerBound, C upperBound, boolean lowerBoundInclusive, boolean upperBoundInclusive) {
    this.lowerBound = lowerBound;
    this.upperBound = upperBound;
    this.lowerBoundInclusive = lowerBoundInclusive;
    this.upperBoundInclusive = upperBoundInclusive;

    if (lowerBound.compareTo(upperBound) > 0)
      throw new IllegalArgumentException("Invalid Range: [(" + lowerBound + ") > (" + upperBound + ")]");

    if (lowerBoundInclusive && upperBoundInclusive && Objects.equals(lowerBound, upperBound))
      throw new IllegalArgumentException("Invalid Range: (" + lowerBound + ", " + upperBound + ")");
  }

  public boolean contains(C value) {

    requireNonNull(value);

    if (lowerBoundInclusive && value.compareTo(lowerBound) < 0) {
      return false;
    }

    if (!lowerBoundInclusive && value.compareTo(lowerBound) <= 0) {
      return false;
    }

    if (upperBoundInclusive && value.compareTo(upperBound) > 0) {
      return false;
    }

    if (!upperBoundInclusive && value.compareTo(upperBound) >= 0) {
      return false;
    }

    return true;
  }

  public boolean encloses(Range<C> other) {

    requireNonNull(other);

    /*
     * Both Upper Bound and Lower Bound Inclusive : -
     * upper bound is in the range
     * lower bound is in the range
     * */
    if (other.upperBoundInclusive &&
        this.contains(other.upperBound) &&
        other.lowerBoundInclusive &&
        this.contains(other.lowerBound))
      return true;

    /*
     * Upper Bound Not Inclusive but Lower Bound Inclusive : -
     * this upper bound > other upperbound
     * lower bound is in the range
     * */
    if (!other.upperBoundInclusive &&
        this.upperBound.compareTo(other.upperBound) > 0 &&
        other.lowerBoundInclusive &&
        this.contains(other.lowerBound))
      return true;

    /*
     * Upper Bound is Inclusive but Lower Bound is not Inclusive : -
     * upper bound is in the range
     * this lower bound < other lower bound
     * */
    if (other.upperBoundInclusive &&
        this.contains(other.upperBound) &&
        !other.lowerBoundInclusive &&
        this.lowerBound.compareTo(other.lowerBound) < 0)
      return true;

    /*
     * Both Upper Bound and Lower Bound Not Inclusive : -
     * this upper bound > other upper bound
     * this lower bound < other lower bound
     * */
    if (!other.upperBoundInclusive &&
        this.upperBound.compareTo(other.upperBound) > 0 &&
        !other.lowerBoundInclusive &&
        this.lowerBound.compareTo(other.lowerBound) < 0)
      return true;

    return false;
  }

  public Range<C> intersection(Range<C> other) {
    if (this.lowerBound.compareTo(other.lowerBound) > 0) {
      return intersection(this, other);
    } else {
      return intersection(other, this);
    }
  }

  private Range<C> intersection(Range<C> a, Range<C> b) {
    if (a.lowerBound.compareTo(b.upperBound) < 0) {
      return Range.openClosed(a.lowerBound, a.lowerBound);
    }
    return new Range<>(b.upperBound, a.lowerBound, b.upperBoundInclusive, a.lowerBoundInclusive);
  }

  public Range<C> span(Range<C> other) {
    if (this.lowerBound.compareTo(other.lowerBound) > 0) {
      return span(this, other);
    } else {
      return span(other, this);
    }
  }

  private Range<C> span(Range<C> a, Range<C> b) {
    return new Range<>(a.upperBound, b.lowerBound, a.upperBoundInclusive, b.lowerBoundInclusive);
  }

  public boolean isEmpty() {
    return upperBound.equals(lowerBound) &&
        ((!upperBoundInclusive && lowerBoundInclusive) ||
            (upperBoundInclusive && !lowerBoundInclusive));
  }

  public static <C extends Comparable> Range<C> open(C a, C b) {
    return new Range<>(requireNonNull(a),
        requireNonNull(b),
        false,
        false);
  }

  public static <C extends Comparable> Range<C> closed(C a, C b) {
    return new Range<>(requireNonNull(a),
        requireNonNull(b),
        true,
        true);
  }

  public static <C extends Comparable> Range<C> openClosed(C a, C b) {
    return new Range(requireNonNull(a),
        requireNonNull(b),
        false,
        true);
  }

  public static <C extends Comparable> Range<C> closedOpen(C a, C b) {
    return new Range(requireNonNull(a),
        requireNonNull(b),
        true,
        false);
  }

  public static <C extends Comparable> Range<C> greaterThan(C a) {
    return new Range(requireNonNull(a),
        infinity,
        false,
        true);
  }

  public static <C extends Comparable> Range<C> atLeast(C a) {
    return new Range(requireNonNull(a),
        infinity,
        true,
        true);
  }

  public static <C extends Comparable> Range<C> lessThan(C a) {
    return new Range(infinity,
        requireNonNull(a),
        true,
        false);
  }

  public static <C extends Comparable> Range<C> atMost(C a) {
    return new Range(infinity,
        requireNonNull(a),
        true,
        true);
  }

  public static <C extends Comparable> Range<C> all() {
    return new Range(infinity,
        infinity,
        true,
        true);
  }

  @Override
  public String toString() {

    char l, u;
    if (lowerBoundInclusive)
      l = '(';
    else
      l = '[';

    if (upperBoundInclusive)
      u = ')';
    else
      u = ']';

    return "Range: " + l + lowerBound +
        ", " + upperBound + u;
  }
}
