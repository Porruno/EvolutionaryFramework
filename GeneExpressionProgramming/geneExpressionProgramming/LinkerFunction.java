package geneExpressionProgramming;

import grammar.Function;

public abstract class LinkerFunction extends Function {
    
    public abstract int getNumberOfORFs();

    public abstract Class<?> getORFReturnType();

    @Override
    public Class<?>[] getParameterTypes() {
        Class<?>[] orfsParameterTypes = new Class<?>[this.getNumberOfORFs()];
        for (int i = 0; i < this.getNumberOfORFs(); i++) {
            orfsParameterTypes[i] = this.getORFReturnType();
        }
        return orfsParameterTypes;
    }
    
    @Override
    public String toString(){
        return "linker function";
    }
}
