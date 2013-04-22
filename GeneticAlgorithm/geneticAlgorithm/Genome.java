package geneticAlgorithm;

import java.util.ArrayList;

import geneticAlgorithm.phenotype.ProgramNode;
import grammar.Input;
import grammar.adf.ADF;
import grammar.adf.ADFDelegate;
import grammar.adf.ADFDelegate.ADFType;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Genome implements Comparable<Genome> {

    protected Population population;
    protected int indexInPopulation;
    protected double aptitude;
    protected ArrayList<ADF> adfs;
    protected int[] genomeIndexForADFs;
    protected Genome[] bestGenomeForEachADF;
    protected int iterationsToDo;
    protected GeneticAlgorithm geneticAlgorithm;
    protected boolean isFromNewGeneration;
    protected boolean maximization;
    protected ProgramNode phenotype;

    public Genome() {
    }

    public Genome(Population population, Integer indexInPopulation) {
        this.population = population;
        this.indexInPopulation = indexInPopulation;
        this.aptitude = this.population.getMaximization() ? 0 : Double.MAX_VALUE;
    }

    public void setAptitude(double aptitude) {
        this.aptitude = aptitude;
    }

    public void sendAptitude(double aptitude) {
        if (maximization && this.aptitude < aptitude
                || !maximization && this.aptitude > aptitude) {
            this.setAptitude(aptitude);
            for (int i = 0; i < this.adfs.size(); i++) {
                this.bestGenomeForEachADF[i] = this.getADFGenomeAtIndex(i).clone();
                this.bestGenomeForEachADF[i].aptitude = this.aptitude;
                this.getADFGenomeAtIndex(i).sendAptitude(aptitude);
            }
        }
    }

    public Genome getADFGenomeAtIndex(int index) {
        boolean onlyFromNewGeneration = !this.isFromNewGeneration;
        Genome adf = this.getADFs().get(index).getPopulation().getGenomeAtIndex(this.genomeIndexForADFs[index], onlyFromNewGeneration);
        return adf;
    }

    public double getAptitude() {
        return this.aptitude;
    }

    
    //returns -1 when this is better than the genome passed as parameter
    @Override
    public int compareTo(Genome otherGenome) {
        if (this.aptitude == otherGenome.getAptitude()) {
            return 0;
        }
        if (this.maximization && this.aptitude > otherGenome.getAptitude()
                || !this.maximization && this.aptitude < otherGenome.getAptitude()) {
            return -1;
        } else {
            return 1;
        }
    }

    public Object evaluate(Input input) {
        return this.phenotype.evaluateProgramNode(input);
    }

    public void evaluate(EvaluationInterface evaluator) {
        for (int i = 0; i < iterationsToDo; i++) {
            double evaluation = evaluator.evaluateGenome(this);
            this.sendAptitude(evaluation);
            for (int j = 0; j < this.adfs.size(); j++) {
                ADF adf = this.adfs.get(j);
                adf.sendAptitudeToGenome(evaluation, this.genomeIndexForADFs[j]);
            }
            this.increaseIndexes(this.adfs.size() - 1);
        }
    }

    public void resetAptitude() {
        this.aptitude = this.maximization ? Double.MIN_VALUE : Double.MAX_VALUE;
    }

    public void setPhenotype(ProgramNode newPhenotype) {
        this.phenotype = newPhenotype;
    }

    public ProgramNode getPhenotype() {
        return this.phenotype;
    }

    public Genome clone() {
        Genome clone = null;
        try {
            clone = this.getClass().getConstructor(Population.class, Integer.class).newInstance(this.population, this.indexInPopulation);
            clone.population = this.population;
            clone.adfs = (ArrayList<ADF>) this.adfs.clone();
            clone.bestGenomeForEachADF = new Genome[this.adfs.size()];
            for (int i = 0; i < this.bestGenomeForEachADF.length; i++) {
                clone.bestGenomeForEachADF[i] = this.bestGenomeForEachADF[i];
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return clone;
    }

    public void increaseIndexes(int startingFrom) {
        if (this.adfs.size() <= 0) {
            return;
        }
        this.genomeIndexForADFs[startingFrom]++;
        if (this.adfs.get(startingFrom).getPopulationSize() == this.genomeIndexForADFs[startingFrom]
                || this.adfs.get(startingFrom).getADFType() != ADFType.COEVOLUTIVE) {
            this.genomeIndexForADFs[startingFrom] = 0;
            if (startingFrom == 0) {
                return;
            } else {
                this.increaseIndexes(startingFrom - 1);
            }
        }
    }

    public void computeIterationsToDo() {
        this.iterationsToDo = 1;

        int i = 0;
        for (ADF adf : this.adfs) {
            if (adf.getADFType() == ADFType.COEVOLUTIVE) {
                if (isFromNewGeneration) {
                    this.iterationsToDo *= adf.getPopulationSize();
                } else {
                    this.iterationsToDo *= adf.getNewGenerationSize();
                }
            } else {
                this.genomeIndexForADFs[i] = this.indexInPopulation;
            }
            i++;
        }
        this.genomeIndexForADFs = new int[this.adfs.size()];
    }

    public int[] getGenomeIndexForADFs() {
        return this.genomeIndexForADFs;
    }

    public int getGenomeIndexForADF(ADF adf) {
        if (adf.getADFType() != ADFDelegate.ADFType.COEVOLUTIVE) {
            return this.getIndexInPopulation();
        } else {
            for (int i = 0; i < this.adfs.size(); i++) {
                if (this.adfs.get(i) == adf) {
                    return this.getGenomeIndexForADFs()[i];
                }
            }
        }
        return -1;
    }
    
    public int getGenomeIndexForBestADF(ADF adf){
        for (int i = 0; i < this.adfs.size(); i++) {
                if (this.adfs.get(i) == adf) {
                    return this.bestGenomeForEachADF[i].indexInPopulation;
                }
            }
        return -1;
    }

    public ArrayList<ADF> getADFs() {
        return this.adfs;
    }

    public int getIterationsToDo() {
        return this.iterationsToDo;
    }

    public boolean getMaximization() {
        return this.maximization;
    }

    public void consolidatePhenotype() {
        this.phenotype.propagateNewGenome(this);
        this.phenotype.computeLevelOfDeepestNode();
        this.phenotype.computeNumberOfSubgenes();
    }

    public boolean isFromNewGeneration() {
        return this.isFromNewGeneration;
    }

    public void lookForADFs() {
        this.adfs = new ArrayList<ADF>();
        this.phenotype.lookForADFs(this.adfs);
        this.genomeIndexForADFs = new int[this.adfs.size()];
        this.bestGenomeForEachADF = new Genome[this.adfs.size()];
    }

    public Genome[] getBestGenomeForBestADF() {
        return this.bestGenomeForEachADF;
    }

    public void setIsFromNewGeneration(boolean isFromNewGeneration) {
        this.isFromNewGeneration = isFromNewGeneration;
    }

    public int getIndexInPopulation() {
        return this.indexInPopulation;
    }

    public void prepareForNextGeneration() {
        this.isFromNewGeneration = true;
        this.lookForADFs();
        this.computeIterationsToDo();
    }

    public void graph(String fileName, Input input) {
        this.prepareToBeGraphed();
        try {
            FileWriter fstream = new FileWriter(fileName + ".dot");
            BufferedWriter out = new BufferedWriter(fstream);


            boolean graphWithResults = input != null;
            if (graphWithResults) {
                phenotype.evaluateProgramNodeUsingOnlyBestADFs(input);
            }

            String graphString = "graph \"\"{ \n"
                    + "# node[fontsize=10,width=\".2\".height=\".2\", margin=0]; \n"
                    + "# graph[fontsize=8];\n"
                    + "label=\"\"\n";

            graphString += phenotype.graphAsRoot(graphWithResults, 0);

            int adfIterator = 1;
            for (Genome bestGenomeForADF : this.bestGenomeForEachADF) {
                bestGenomeForADF.prepareToBeGraphed();
                graphString += bestGenomeForADF.phenotype.graphAsRoot(false, adfIterator);
                adfIterator++;
            }
            graphString += "\n}";

            out.write(graphString);
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(Genome.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public abstract void prepareToBeGraphed();
    
    @Override public String toString(){
        return this.phenotype.toDescription();
    }
}