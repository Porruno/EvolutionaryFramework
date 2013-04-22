package CSP.HyperHeuristic;

import CSP.Framework.Framework;
import CSP.Framework.EvolutionaryFramework;
import java.util.Random;
import java.util.ArrayList;

/**
 * @author José Carlos Ortiz Bayliss 
 * Monterrey, Nuevo León, México
 * August 2006 - March 2012
 * ----------------------------------------------------------------------------
 * This class implements the functions to create and handle a block hyper-heuristic.
 * ----------------------------------------------------------------------------
 *
 * The description of the hyper-heuristic is specified as follows:
 * 
 *  -----------------------------------
 * | BLOCK 1 | BLOCK 2 | ... | BLOCK n |
 *  -----------------------------------
*/

public class BlockHyperHeuristic extends HyperHeuristic {

    private boolean blockUseStatus = false;
    private ArrayList blocks;    
    private double blockUse[];    
    
    /**
     * Creates a new random instance of BlockHyperHeuristic with the default number of 
     * blocks.      
    */
    public BlockHyperHeuristic() {
        int i, size, index;
        Block block;
        Random generator = new Random();
        if (Framework.getHeuristics().length > 5) {
            size = 25;
        } else {
            size = 10;
        }                
        blocks = new ArrayList(size);
        for (i = 0; i < size; i++) {
            block = new Block();
            index = generator.nextInt(Framework.getHeuristics().length);
            block.setHeuristic(Framework.getHeuristics()[index]);
            blocks.add(block);  
        }
        type = HyperHeuristic.BLOCK;
    }
    
    /**
     * Creates a new random instance of BlockHyperHeuristic with a given number of 
     * blocks.
     * @param size The number of blocks within the hyper-heuristic.
    */
    public BlockHyperHeuristic(int size) {        
        int i, index;
        Block block;
        Random generator = new Random();
        blocks = new ArrayList(size);
        for (i = 0; i < size; i++) {
            block = new Block();
            index = generator.nextInt(Framework.getHeuristics().length);
            block.setHeuristic(Framework.getHeuristics()[index]);
            blocks.add(block);
        }
        type = HyperHeuristic.BLOCK;
    }
    
    /** 
     * Creates a new instance of BlockHyperHeuristic from an existing instance. 
     * This is a copy constructor. 
     * @param hyperHeuristic The instance that will be copied into the new hyperHeuristic.
    */
    public BlockHyperHeuristic(BlockHyperHeuristic hyperHeuristic) {
        int i;
        this.blocks = new ArrayList(hyperHeuristic.size());
        for (i = 0; i < hyperHeuristic.size(); i++) {
            this.blocks.add(new Block(hyperHeuristic.get(i)));
        }
        type = HyperHeuristic.BLOCK;
    }
    
    /** 
     * Creates a new instance of BlockHyperHeuristic from an array of doubles.
     * @param hyperHeuristic The array of doubles that will be used to generate the 
     * new instance.
    */  
    public BlockHyperHeuristic(double hyperHeuristic[]) {
        int i, j, counter = 0, size = hyperHeuristic.length / (EvolutionaryFramework.getFeatures().length + 1);
        Block block;
        blocks = new ArrayList(1);
        for (i = 0; i < size; i++) {
            block = new Block();
            for (j = 0; j < EvolutionaryFramework.getFeatures().length; j++) {
                block.set(j, hyperHeuristic[counter++]);
            }
            block.setHeuristic((int) hyperHeuristic[counter++]);
            blocks.add(block);
        }
        type = HyperHeuristic.BLOCK;
    }
    
    /** 
     * Adds a block to the hyper-heuristic.
     * @param block The block to be added to the hyper-heuristic.
    */
    public void add(Block block) {
        blocks.add(block);
    }
    
    /** 
     * Deletes the block with the given index.
     * @param index The index of the block to be deleted.
    */
    public void delete(int index) {
        blocks.remove(index);
    }
    
    /** 
     * Sets the block with the given index to the block specified as parameter.
     * @param index The index of the block to be set.
     * @param block The block that will be allocated in the hyperHeuristic.
    */
    public void set(int index, Block block) {
        blocks.set(index, block);
    }
    
    /** 
     * Returns a reference to the block at the position of the given index.
     * @param index The index of the block to be obtained.
     * @return A reference to the block at the position of the given index.
    */
    public Block get(int index) {  
        return (Block)blocks.get(index);
    }
    
    /** 
     * Returns the number of blocks contained in the hyperHeuristic.
     * @return The number of blocks contained in the hyperHeuristic.     
    */
    public int size() {
        return blocks.size();
    }
    
    /** Returns the representation of the hyper-heuristic in an array of doubles.
     * @return An array of doubles representation of the hyper-heuristic.  
    */
    public double[] getAsDouble() {
        int i, j, counter = 0;
        double hyperHeuristic[] = new double[blocks.size() * (EvolutionaryFramework.getFeatures().length + 1)];
        Block block;
        for (i = 0; i < blocks.size(); i++) {
            block = get(i);
            for (j = 0; j < EvolutionaryFramework.getFeatures().length; j++) {
                hyperHeuristic[counter++] = block.get(j);                
            }
            hyperHeuristic[counter++] = (int) block.getHeuristic();            
        }
        return hyperHeuristic;
    }        
    
    /**
     * Activates the block use functionality. The block use control maps the
     * number of times each block has been used when solving a given set of
     * instances.     
    */
    public void resetBlockUse() {
        blockUseStatus = true;
        blockUse = new double[blocks.size()];
    }
    
    /**
     * Returns the block use of the hyper-heuristic.
     * @return The block use of the hyper-heuristic.
    */
    public double[] getBlockUse() {
        int i;
        double sum = 0;
        double use[] = new double[blockUse.length];        
        if (blockUseStatus) {
            for (i = 0; i < blockUse.length; i++) {
                sum += blockUse[i];
            }
            for (i = 0; i < blockUse.length; i++) {
                use[i] = blockUse[i] / sum;
            }
            return use;
        } else {
           return null; 
        }
    }
    
    /**
     * Increments the counter associated to the use of the block given as parameter.
     * @param index The index of the used block.
    */
    public void useBlock(int index) {
        if (!blockUseStatus) {
            resetBlockUse();
        }
        blockUse[index]++;
    }
    
    /**
     * Deletes blocks which have never been used during the search.
    */
    public void deleteUnusedBlocks() {
        int i, j;
        if (!blockUseStatus) {
            return;
        }
        for (i = 0, j = 0; i < blocks.size(); i++) {
            if (blockUse[j] == 0) {
                blocks.remove(i);
                i--;
            }
            j++;
        }
        resetBlockUse();
    }
    
    /** Returns a new instance of Individual created fronm the contents of a file.
     * @param fileName File name where the data of the new instance will be read from. 
     * @return A new instance of Individual. 
    */    
    public static BlockHyperHeuristic loadFromFile(String fileName) {        
        int i;
        BlockHyperHeuristic hyperHeuristic = new BlockHyperHeuristic(0);
        String text = CSP.Utils.Files.loadFromFile(fileName);
        String rows[] = CSP.Utils.Misc.toStringArray(text);
        for (i = 0; i < rows.length; i++) {
            hyperHeuristic.add(new Block(rows[i]));
        }
        return hyperHeuristic;
    }
         
    /**
     * Saves the hyper-heuristic to a text file.
     * @param fileName The name of the file where the hyper-heuristic will be stored.
    */
    public void saveToFile(String fileName) {
        int i;
        String string = getHeader();
        for (i = 0; i < blocks.size(); i++) {
            string = string + (Block)(blocks.get(i)) + "\r\n";            
        }
        CSP.Utils.Files.saveToFile(string, fileName, false);        
    }
    
    /**
     * Returns the closest block in the radius of distance. In no block is within
     * the range, the function returns null.
     * @param distance The radius of the range.
     * @return The closest block within the range or null.
    */
    public Block getClosest(Block block, double distance) {
        int i;
        double currentDistance, minDistance = Double.MAX_VALUE;
        Block closestBlock = null;
        for (i = 0; i < blocks.size(); i++) {
            currentDistance = distance(block, get(i));
            if (currentDistance < minDistance) {
                minDistance = currentDistance;
                closestBlock = get(i);
            } 
        }
        if (distance(block, closestBlock) < distance) {
            return closestBlock;
        } else {
            return null;
        }
    }
    
    /** Calculates the euclidean distance between a block and a given CSP instance.
     * @param blockA The first block to be used to calculate the distance.
     * @param blockB The second block to be used to calculate the distance.
     * @return The euclidean distance from the blockA to blockB.
     */
    public static double distance(Block blockA, Block blockB) {
        int i;
        double sum = 0;
        for (i = 0; i < Framework.getFeatures().length; i++) {
            sum = sum + Math.pow(blockA.get(i) - blockB.get(i), 2);
        }        
        return Math.sqrt(sum);
    }
    
    @Override
    /**
     * Returns the string representation of the instance.
     * @return The string representation of the instance.
    */
    public String toString() {
        int i;
        String text = "";
        for (i = 0; i < this.size(); i++) {
            text += "[ " + this.get(i) + " ] ";
        }
        return text;
    }        
    
}
