import collections.BiMap;
import org.hyperskill.hstest.common.ReflectionUtils;
import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import static org.hyperskill.hstest.testcase.CheckResult.correct;
import static org.hyperskill.hstest.testcase.CheckResult.wrong;

@SuppressWarnings("unused")
public class BiMapTests extends StageTest<Object> {
    private Class<?> biMapClass;

    @DynamicTest(order = 1)
    CheckResult itShouldTestForPresenceOfClass() {
        try {
            biMapClass = Class.forName("collections.BiMap");
        } catch (ClassNotFoundException e) {
            return wrong("Could not Find Class BiMap");
        }
        return correct();
    }

    @DynamicTest(order = 2)
    CheckResult itShouldTestForFinalClass() {
        int modifiers = biMapClass.getModifiers();
        if (Modifier.isPublic(modifiers)) {
            return correct();
        }
        return wrong("BiMap must be a public Class");
    }

    @DynamicTest(order = 3)
    CheckResult itShouldTestForMethodsInClass() {
        if (biMapClass.getTypeParameters().length == 0) {
            return wrong("Bimap must be a Generic Class.");
        }
        return correct();
    }

    @DynamicTest(order = 4, feedback = "All fields should be private and final")
    CheckResult itShouldTestForPrivateField() {
        for (var f : biMapClass.getFields()) {
            if (Modifier.isPrivate(f.getModifiers())) {
                return wrong("All fields should be private");
            }

            if (Modifier.isFinal(f.getModifiers())) {
                return wrong("All fields should be final");
            }
        }
        return correct();
    }

    @DynamicTest(order = 4, feedback = "All fields should be private and final")
    CheckResult itShouldTestForFinalField() {
        for (var f : biMapClass.getFields()) {
            if (Modifier.isFinal(f.getModifiers())) {
                return wrong("All fields should be final");
            }
        }
        return correct();
    }

    private final Object[][] methods = new Object[][]{
            new Object[]{"put", false, new Object(), new Class[]{Object[].class, Object[].class}},
            new Object[]{"forcePut", false, new Object(), new Class[]{Object[].class, Object[].class}},
            new Object[]{"putAll", false, void.class, new Class[]{Object[].class}},
            new Object[]{"inverse", false, new Object[]{Object.class, Object.class}, new Class[]{}},
    };

    private final LinkedHashMap<String, Method> methodMap = new LinkedHashMap<>();

    @DynamicTest(order = 5, data = "methods")
    CheckResult itShouldTestForThePresenceOfValidMethods(String methodName, boolean isStatic, Class<?> returnType, Class<?>[] args) {
        Method method;
        try {
            method = biMapClass.getMethod(methodName, args);
        } catch (NoSuchMethodException e) {
            return wrong(methodName + "() method with args" + Arrays.toString(args) + " is not found");
        }

        if (isStatic && !Modifier.isStatic(method.getModifiers())) {
            return wrong("Method " + methodName + "() must be static");
        }

        returnType = Objects.equals(returnType, getClass()) ? biMapClass : returnType;

        if (!isStatic && !Objects.equals(method.getReturnType(), returnType)) {
            return wrong("Method " + methodName + "() must return " + returnType);
        }

        if (!Modifier.isPublic(method.getModifiers())) {
            return wrong("Method " + methodName + "() must be public");
        }

        methodMap.put(methodName, method);

        return correct();
    }
}