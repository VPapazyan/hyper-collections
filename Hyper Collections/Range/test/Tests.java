import collections.Range;
import org.junit.Test;

public class Tests {

  @Test(expected = IllegalArgumentException.class)
  public void testSolution() {
    Range<Integer> openRange = Range.open(3, 2);
  }
}
