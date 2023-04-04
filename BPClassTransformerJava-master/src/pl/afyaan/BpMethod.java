package pl.afyaan;

import java.util.Objects;

public class BpMethod {
    private String className;
    private String methodName;
    private Object signature;

    public BpMethod(String className, String methodName, Object signature) {
        this.className = className;
        this.methodName = methodName;
        this.signature = signature;
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    public Object getSignature() {
        return signature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BpMethod method = (BpMethod) o;
        return Objects.equals(className, method.className) && Objects.equals(methodName, method.methodName) && Objects.equals(signature.toString(), method.signature.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(className, methodName, signature);
    }
}
