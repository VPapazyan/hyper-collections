import org.hyperskill.hstest.common.ReflectionUtils;
import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import static org.hyperskill.hstest.testcase.CheckResult.correct;
import static org.hyperskill.hstest.testcase.CheckResult.wrong;

@SuppressWarnings("unused")
public class ImmutabilityTests extends StageTest<Object> {
  private Class<?> immutableClass;

  @DynamicTest(order = 1, feedback = "Could not Find Class ImmutableCollection")
  CheckResult itShouldTestForPresenceOfClass() {
    try {
      immutableClass = Class.forName("collections.framework.ImmutableCollection");
    } catch (ClassNotFoundException e) {
      return wrong("Could not Find Class ImmutableCollection");
    }
    return correct();
  }

  @DynamicTest(order = 2, feedback = "ImmutableCollection Class must be a final Class")
  CheckResult itShouldTestForFinalClass() {
    int modifiers = immutableClass.getModifiers();
    if (Modifier.isFinal(modifiers)) {
      return correct();
    }
    return wrong("ImmutableCollection Class must be a final Class");
  }

  @DynamicTest(order = 3, feedback = "Class ImmutableCollection must a Generic Class")
  CheckResult itShouldTestForMethodsInClass() {
    if (immutableClass.getTypeParameters().length == 0) {
      return wrong("Class Immutable Collection must a Generic Class.");
    }
    return correct();
  }

  @DynamicTest(order = 4, feedback = "None of the constructors should be public")
  CheckResult itShouldTestForPrivateConstructor() {
    for (var c : immutableClass.getDeclaredConstructors()) {
      if (Modifier.isPublic(c.getModifiers())) {
        return wrong("None of the constructors should be public");
      }
    }
    return correct();
  }

  private final Object[][] methods = new Object[][]{
      new Object[]{"of", true, getClass(), new Class[]{}},
      new Object[]{"of", true, getClass(), new Class[]{Object[].class}},
      new Object[]{"contains", false, boolean.class, new Class[]{Object.class}},
      new Object[]{"size", false, int.class, new Class[]{}},
      new Object[]{"isEmpty", false, boolean.class, new Class[]{}},
  };

  private final LinkedHashMap<String, Method> methodMap = new LinkedHashMap<>();

  @DynamicTest(order = 5, data = "methods")
  CheckResult itShouldTestForThePresenceOfValidMethods(String methodName, boolean isStatic, Class<?> returnType, Class<?>[] args) {
    Method method;
    try {
      method = immutableClass.getMethod(methodName, args);
    } catch (NoSuchMethodException e) {
      return wrong(methodName + "() method with args" + Arrays.toString(args) + " is not found");
    }

    if (isStatic && !Modifier.isStatic(method.getModifiers())) {
      return wrong("Method " + methodName + "() must be static");
    }

    returnType = Objects.equals(returnType, getClass()) ? immutableClass : returnType;

    if (!isStatic && !Objects.equals(method.getReturnType(), returnType)) {
      return wrong("Method " + methodName + "() must return " + returnType);
    }

    if (!Modifier.isPublic(method.getModifiers())) {
      return wrong("Method " + methodName + "() must be public");
    }

    methodMap.put(methodName, method);

    return correct();
  }

  private final Map<String, Object[]> dataMap = Map.of(
      "empty", new Object[]{},
      "int", new Integer[]{1, 2, 3, 4, 5},
      "char", new Character[]{'A', 'B', 'C'},
      "string", new String[]{"ABC", "XYZ", "PQR", "MNO"}
  );

  private final LinkedHashMap<String, Object> instanceMap = new LinkedHashMap<>();

  @DynamicTest(order = 6, feedback = "Could not invoke of() method")
  CheckResult itShouldCreateNewInstances() {
    for (Map.Entry<String, Object[]> args : dataMap.entrySet()) {
      Object instance = getInstance(args.getValue());
      instanceMap.put(args.getKey(), instance);
    }
    return correct();
  }


  @DynamicTest(order = 7, feedback = "Could not invoke isEmpty() method")
  CheckResult itShouldCheckIsEmptyResult() {
    Method isEmpty = methodMap.get("isEmpty");

    for (var e : dataMap.entrySet()) {
      Object instance = instanceMap.get(e.getKey());
      boolean got = (boolean) ReflectionUtils.invokeMethod(isEmpty, instance, new Object[]{});
      boolean expected = e.getValue().length == 0;

      if (!Objects.equals(got, expected)) {
        throw new WrongAnswer("Incorrect result from isEmpty() method.");
      }
    }

    return correct();
  }

  @DynamicTest(order = 8, feedback = "Could not invoke size() method")
  CheckResult itShouldCheckSizeResult() {
    Method size = methodMap.get("size");

    for (var e : dataMap.entrySet()) {
      Object instance = instanceMap.get(e.getKey());
      int got = (int) ReflectionUtils.invokeMethod(size, instance, new Object[]{});
      int expected = e.getValue().length;

      if (!Objects.equals(got, expected)) {
        throw new WrongAnswer("Incorrect result from size() method.");
      }
    }

    return correct();
  }

  private final Map<String, Object[]> doNotContains = Map.of(
      "empty", new Object[]{1, 'a', "XYZ", false},
      "int", new Integer[]{8, 9, 10},
      "char", new Character[]{'8', 'X', 'Y', 'Z'},
      "string", new String[]{"This", "is", "not", "present"}
  );

  @DynamicTest(order = 9, feedback = "Could not invoke contains() method")
  CheckResult itShouldCheckContainsResultToBeFalse() {
    Method contains = methodMap.get("contains");

    for (var e : doNotContains.entrySet()) {
      Object instance = instanceMap.get(e.getKey());

      for (var i : e.getValue()) {
        boolean got = (boolean) ReflectionUtils.invokeMethod(contains, instance, new Object[]{i});

        if (!Objects.equals(got, false)) {
          throw new WrongAnswer("Incorrect result from contains() method.");
        }
      }
    }

    return correct();
  }

  private final Map<String, Object[]> doContains = Map.of(
      "int", new Integer[]{1, 2, 3, 4, 5},
      "char", new Character[]{'A', 'B', 'C'},
      "string", new String[]{"ABC", "XYZ", "MNO", "PQR"}
  );

  @DynamicTest(order = 10, feedback = "Could not invoke contains() method")
  CheckResult itShouldCheckContainsResultToBeTrue() {
    Method contains = methodMap.get("contains");

    for (var e : doContains.entrySet()) {
      Object instance = instanceMap.get(e.getKey());

      for (var i : e.getValue()) {
        boolean got = (boolean) ReflectionUtils.invokeMethod(contains, instance, new Object[]{i});

        if (!Objects.equals(got, true)) {
          throw new WrongAnswer("Incorrect result from contains() method.");
        }
      }
    }

    return correct();
  }

  private Object getInstance(Object[] args) {
    try {
      if (args.length == 0) {
        Method ofWithNoArgs = immutableClass.getMethod("of");
        return ofWithNoArgs.invoke(immutableClass);
      } else {
        Method ofWithArgs = immutableClass.getMethod("of", Object[].class);
        return ReflectionUtils.invokeMethod(ofWithArgs, immutableClass, new Object[]{args});
      }
    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
      throw new WrongAnswer("Could not invoke of() method");
    }
  }
}
