package CSP.MSP;

/**
 * @author José Carlos Ortiz Bayliss 
 * Monterrey, Nuevo León, México
 * August 2006 - March 2012
 * ----------------------------------------------------------------------------
 * This class contains the functions and fields to implement and hanlde the 
 * meetings in a MSP.
 * ----------------------------------------------------------------------------
*/

public class Meeting 
{
    
    private String meetingId;
    private int locationId, duration;
    private int agentIds[], timeSlots[];    

    /**
     * Creates a new instance of Meeting.
     * 
    */
    public Meeting(String meetingId, int locationId, int duration, int timeSlots[], int agentIds[]) {
        int i;
        this.meetingId = meetingId;
        this.locationId = locationId;
        this.duration = duration;
        this.timeSlots = new int[timeSlots.length];
        for (i = 0; i < timeSlots.length; i++) {
            this.timeSlots[i] = timeSlots[i];            
        }
        this.agentIds = new int[agentIds.length];
        for (i = 0; i < agentIds.length; i++) {            
            this.agentIds[i] = agentIds[i];
        }
    }
    
    /**
     * Returns the meeting id.
     * @return The meeting id.
    */
    public String getId() {
        return meetingId;
    }
    
    /**
     * Returns the time slots where the meeting can be scheduled.
     * return The time slots where the meeting can be scheduled.
    */
    public int[] getTimeSlots() {
        return timeSlots;
    }
    
    /**
     * Returns the duration of the meeting in time slots.
     * @return The duration of the meeting in time slots.
    */
    public int getDuration() {
        return duration;
    }
    
    /**
     * Returns the agents that must attend to the meeting.
     * @return The agents that must attend to the meeting.
    */
    public int[] getAgents() {
        return agentIds;
    }
    
    /**
     * Returns the location of the meeting.
     * @retunr The location of the meeting.
    */
    public int getLocation() {
        return locationId;
    }
    
    @Override
    /**
     * Returns the string representation of the instance.
     * @return The string representation of the instance.
    */    
    public String toString() {
        int i;
        String text = meetingId + ": [ " + duration + " ] [ " + locationId + " ] [ ";
        for (i = 0; i < timeSlots.length; i++) {
            text += timeSlots[i] + " ";
        }
        text += "] [ ";        
        for (i = 0; i < agentIds.length; i++) {
            text += agentIds[i] + " ";
        }
        text += "]";
        return text;
    }
    
}
