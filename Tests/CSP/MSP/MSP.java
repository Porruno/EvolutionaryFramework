package CSP.MSP;

import CSP.CSP.CSP;
import CSP.CSP.Variable;

/**
 * @author José Carlos Ortiz Bayliss 
 * Monterrey, Nuevo León, México
 * August 2006 - March 2012
 * ----------------------------------------------------------------------------
 * This class containts the functions and fields to implement and handle Meeting
 * Scheduling Problems (MSP).
 * ----------------------------------------------------------------------------
*/

public class MSP {
    
    private Meeting meetings[];
    private int locations, agents;
    private int distances[][];
    
    /**
     * Creates a new instance of MSP.
    */
    public MSP(Meeting meetings[], int distances[][], int agents) {
        int i;        
        if (distances == null) {
            System.out.println("The matrix of distances must be specified.");
            System.out.println("The system will halt.");
            System.exit(1);
            return;
        }                
        for (i = 0; i < distances.length; i++) {
            if (distances[i].length != distances.length) {
                System.out.println("The matrix of distances must be a square matrix.");
                System.out.println("The system will halt.");
                System.exit(1);
                return;
            }
        }
        this.meetings = meetings;
        this.distances = distances;
        this.locations = distances.length;
        this.agents = agents;        
    }
    
    /**
     * Returns the instance coded as a CSP.
     * @return The instance coded as a CSP.
    */
    public CSP toCSP() {
        int i, j, k = 0, duration1, duration2, distance;
        CSP csp;
        Variable variables[] = new Variable[meetings.length];
        for (i = 0; i < variables.length; i++) {
            variables[i] = new Variable(meetings[i].getId(), meetings[i].getTimeSlots());
        }
        csp = new CSP(variables);             
        for (i = 0; i < variables.length; i++) {
            for (j = i + 1; j < variables.length; j++) {
                duration1 = meetings[i].getDuration();
                duration2 = meetings[j].getDuration();
                distance = distances[i][j];                
                if (shareAgents(meetings[i], meetings[j]) || meetings[i].getLocation() == meetings[j].getLocation()) {
                    //csp.addConstraint("C" + (k++), new String[]{variables[i].getId(), variables[j].getId()}, "AND(GE(SUB(ABS(SUB(" + variables[i].getId() + ", " + variables[j].getId() + ")), " + Integer.toString(duration1) + "), " + Integer.toString(distance) + "), " + "GE(SUB(ABS(SUB(" + variables[j].getId() + ", " + variables[i].getId() + ")), " + Integer.toString(duration2) + "), " + Integer.toString(distance) + ")) ");
                    csp.addConstraint("C" + (k++), new String[]{variables[i].getId(), variables[j].getId()}, 
                            "OR(LE(ADD(ADD(" + variables[i].getId() + ", " + Integer.toString(duration1) + "), " + Integer.toString(distance) + "), " + variables[j].getId() + "), " +
                            "LE(ADD(ADD(" + variables[j].getId() + ", " + Integer.toString(duration2) + "), " + Integer.toString(distance) + "), " + variables[i].getId() + ")");
                }
            }
        }
        return csp;
    }
    
    @Override
    /**
     * Returns the string representation of the instance.
     * @return The string representation of the instance.
    */    
    public String toString() {
        int i;
        String text = "MEETING: [DURATION] [LOCATION] [SLOTS] [AGENTS] \r\n";
        for (i = 0; i < meetings.length; i++) {
            text += meetings[i].toString() + "\r\n";
        }        
        return text;
    }
    
    /* ------------------------------------------------------------------------ 
    BEGIN - UTILITY FUNCTIONS
    ------------------------------------------------------------------------- */
    
    /**
     * Returns true if the meetings share at least one agent, false otherwise.
     * @param a The first meeting.
     * @param b The second meeting.
     * @return true if the meetings share at least one agent, false otherwise.
    */
    private boolean shareAgents(Meeting a, Meeting b) {
        int i, j;
        for (i = 0; i < a.getAgents().length; i++) {
            for (j = 0; j < b.getAgents().length; j++) {
                if (a.getAgents()[i] == b.getAgents()[j]) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /* ------------------------------------------------------------------------ 
    END - UTILITY FUNCTIONS
    ------------------------------------------------------------------------- */
    
}
