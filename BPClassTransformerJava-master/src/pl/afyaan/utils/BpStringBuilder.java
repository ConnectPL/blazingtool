package pl.afyaan.utils;

public class BpStringBuilder {
    private StringBuilder stringBuilder;

    public BpStringBuilder() {
        this.stringBuilder = new StringBuilder();
    }

    public void appendln(Object message){
        stringBuilder.append(message + "\n");
    }

    @Override
    public String toString() {
        return this.stringBuilder.toString();
    }
}
