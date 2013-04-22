package CSP.CSP;

import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;

/**  
 * @author José Carlos Ortiz Bayliss 
 * Monterrey, Nuevo León, México
 * August 2006 - March 2012
 * ----------------------------------------------------------------------------
 * This class implements the XML parser functionality.
 * ----------------------------------------------------------------------------
*/

public class XMLLoader {

    private final int Start = 0;
    private final int Tag = 1;
    private final int Parameter = 2;
    private final int ValueStart = 3;
    private final int Value = 4;
    private final int NewTag = 5;
    private final int Text = 6;
    
    private int arity = -1, nbDomains = -1, nbVariables = -1, nbRelations = -1, nbPredicates = -1, nbConstraints = -1;
    private String text, parameter, function;    
    private ArrayList variables;
    private ArrayList domainNames, domains;
    private ArrayList relationNames, relations, arities, semantics;
    private ArrayList predicateNames, predicates, parameters;
    private ArrayList constraintNames, scopes, references, parameterConstraints;
    private ArrayList constraints;
    
    /**
     * Creates a new instance of XMLLoader.
    */    
    public XMLLoader() {
        variables = new ArrayList(0);
        domainNames = new ArrayList(0);
        domains = new ArrayList(0);
        relationNames = new ArrayList(0);
        relations = new ArrayList(0);
        arities = new ArrayList(0);
        semantics = new ArrayList(0);
        predicateNames = new ArrayList(0);
        predicates = new ArrayList(0);
        parameters = new ArrayList(0);
        constraintNames = new ArrayList(0);
        scopes = new ArrayList(0);
        references = new ArrayList(0);
        parameterConstraints = new ArrayList(0);
        constraints = new ArrayList(0);
    }
    
    /**
     * Loads text into the parser.
     * @param text The text to be parsed.
    */
    public void loadText(String text) {
        this.text = text;
    }
    
    /**
     * Loads a text file into the parser.
     * @param fileName The file name where the data to be parsed are stored.
    */
    public void loadTextFromFile(String fileName) {
        text = readFile(fileName);        
    }
    
    /** 
     * Parses the text to generate the tokens list that will be used in the next 
     * step of the process.
    */
    public Token[] parse() {
        boolean closing = false;
        int i, currentState = Start;
        String currentText = "";
        ArrayList tokens = new ArrayList(200);
        Token tokenList[];
        for (i = 0; i < text.length(); i++) {
            switch (currentState) {
                case Start:
                    if (text.charAt(i) == '<') {
                        currentText = "";
                        currentState = Tag;
                    } else if (text.charAt(i) == ' ') {
                         currentState = Start;
                         // We keep reading every space                        
                    } else {
                        System.out.println("There was an error when parsing the text.");
                        System.out.println("Unexpected character: \'" + text.charAt(i) + "\'");
                        System.exit(1);
                    }
                    break;
                case Tag:
                    if (text.charAt(i) == ' ') {
                        if (currentText.equalsIgnoreCase("")) {
                            currentText = "";
                            currentState = Tag;
                        } else {                            
                            tokens.add(new Token(Token.TAG_START, currentText));
                            currentText = "";
                            currentState = Parameter;
                        }
                    } else if (text.charAt(i) == '>') {
                        if (closing) {                            
                            tokens.add(new Token(Token.TAG_END, currentText));
                            currentText = "";
                            currentState = NewTag;
                            closing = false;
                        } else {                            
                            tokens.add(new Token(Token.TAG_START, currentText));
                            currentText = "";
                            currentState = NewTag;
                        }                        
                    } else if (text.charAt(i) == '/') {                        
                        currentText = "";
                        currentState = Tag;
                        closing = true;
                    } else {
                        currentText = currentText + text.charAt(i);
                        currentState = Tag;
                    }
                    break;
                case Parameter:
                    if (text.charAt(i) == '>') {
                        currentText = "";
                        currentState = NewTag;
                    } else if (text.charAt(i) == '?') {                        
                        currentState = Tag;
                        closing = true;
                    } else if (text.charAt(i) == '=') {
                        if(currentText.equalsIgnoreCase("")) {
                            System.out.println("There was an error when parsing the text.");
                            System.out.println("Missing parameter name.");
                            System.exit(1);
                        }                        
                        tokens.add(new Token(Token.PARAMETER, currentText));
                        currentText = "";
                        currentState = ValueStart;
                    } else if (text.charAt(i) == ' ') {
                        currentState = Parameter;
                    // We keep reading every space                        
                    } else if (text.charAt(i) == '/') {
                        currentState = Tag;
                        closing = true;                    
                    } else {
                        currentText = currentText + text.charAt(i);
                        currentState = Parameter;
                    }
                    break;
                case ValueStart:
                    if (text.charAt(i) == '"') {
                        currentText = "";
                        currentState = Value;
                    } else if (text.charAt(i) == ' ') {
                        currentState = ValueStart;
                    // We keep reading every space                        
                    } else {
                        System.out.println("There was an error when parsing the text.");
                        System.out.println("Unexpected character: \'" + text.charAt(i) + "\'");
                        System.exit(1);
                    }
                    break;
                case Value:
                    if (text.charAt(i) == '"') {                       
                        tokens.add(new Token(Token.VALUE, currentText));
                        currentText = "";
                        currentState = Parameter;
                    } else {
                        currentText = currentText + text.charAt(i);
                        currentState = Value;
                    }
                    break;
                case NewTag:
                    if (text.charAt(i) == '<') {
                        currentText = "";
                        currentState = Tag;                    
                    }  else if (text.charAt(i) == ' ' || text.charAt(i) == 10 || text.charAt(i) == 13) {
                        currentState = NewTag;
                    // We keep reading every space                        
                    }  else {
                        currentText = currentText + text.charAt(i);
                        currentState = Text;
                        
                    }
                    break;
                case Text:
                    if (text.charAt(i) == '<') {                        
                        tokens.add(new Token(Token.TEXT, currentText));
                        currentText = "";
                        currentState = Tag;                 
                    } else {
                        currentText = currentText + text.charAt(i);
                        currentState = Text;
                    }
                    break;
            }
        }
        tokenList = new Token[tokens.size()];
        for (i = 0; i < tokens.size(); i++) {
            tokenList[i] = new Token((Token)tokens.get(i));
        }
        return tokenList;
    }                        
    
    /**
     * Creates a CSP instance by using the tokens list obtained in the parsing
     * process.
     * @param fileName The name of the XML file that contains the instance.
    */
    public CSP createCSP(String fileName) {
        int i, referenceIndex = -1;
        String reference = "", function = "";
        String scope[];
        Token tokens[];        
        Variable variablesArray[];        
        CSP csp;
        ArrayList tuples;
        XMLLoader parser = new XMLLoader();
        parser.loadTextFromFile(fileName);
        tokens = parser.parse();                
        for (i = 0; i < tokens.length; i++) {
            if (tokens[i].getType() == Token.TAG_START && tokens[i].getValue().startsWith("?")) {
                i = readCommentaryTag(tokens, i);
            } else if (tokens[i].getType() == Token.TAG_START && tokens[i].getValue().equalsIgnoreCase("INSTANCE")) {
                i = readInstanceTag(tokens, i);
            }            
        }
        
        variablesArray = new Variable[variables.size()];
        for (i = 0; i < variables.size(); i++) {
            variablesArray[i] = (Variable)variables.get(i);            
        }        
        csp = new CSP(variablesArray);
        /*        
        for (i = 0; i < relationNames.size(); i++) {            
            System.out.println(relationNames.get(i).toString() + ": " + relations.get(i).toString() + ", " + arities.get(i).toString() + ", " + semantics.get(i).toString());
        }
        
        for (i = 0; i < predicateNames.size(); i++) {
            System.out.println(predicateNames.get(i).toString() + ": " + predicates.get(i).toString() + ", " + parameters.get(i).toString());
        }
        */
        for (i = 0; i < constraintNames.size(); i++) {
            //System.out.println(constraintNames.get(i).toString() + ": " + scopes.get(i).toString() + ", " + references.get(i).toString()  + ", " + parameterConstraints.get(i).toString());
            reference = (String )references.get(i);
            if (relationNames.contains(reference)) {
                referenceIndex = relationNames.indexOf(reference);
                tuples = textToTuples((String) relations.get(referenceIndex), (Integer)arities.get(referenceIndex));
                scope = textToVariableIds((String)scopes.get(i));
                csp.addConstraint((String)constraintNames.get(i), scope, tuples, (Boolean)semantics.get(referenceIndex));
            } else if (predicateNames.contains(reference)) {
                referenceIndex = predicateNames.indexOf(reference);
                scope = textToVariableIds((String)scopes.get(i));
                function = textToFunction((String)predicates.get(referenceIndex), (String)parameters.get(referenceIndex), (String)parameterConstraints.get(i));
                csp.addConstraint((String)constraintNames.get(i), scope, function);           
            } else if (reference.equalsIgnoreCase("global:allDifferent")) {
                scope = textToVariableIds((String)scopes.get(i));
                csp.addConstraint((String)constraintNames.get(i), scope, reference);
            } else {
                System.out.println("The reference '" + reference + "' has not been defined.");
                System.out.println("The assignation of reference '" + reference + "' to constraint '" + constraintNames.get(i).toString() + "' cannot be done.");
                System.exit(1);
            }                    
        }        
        return csp;
    }   
    
    /* ------------------------------------------------------------------------ 
    BEGIN - UTILITY FUNCTIONS
    ------------------------------------------------------------------------- */               
    
    /**
     * Reads the commentary tag. The function retuns the index of the next token to
     * be read.
     * @return The index of the next token to be read.
    */
    private int readCommentaryTag(Token tokens[], int index) {        
        //System.out.println("COMMENTARY");    
        do {            
            index++;
        } while (tokens[index].getType() != Token.TAG_END);
        return index;
    }
    
    /**
     * Reads the instance tag. The function retuns the index of the next token to
     * be read.
     * @return The index of the next token to be read.
    */
    private int readInstanceTag(Token tokens[], int index) {
        //System.out.println("INSTANCE");
        do {            
            index++;
            if (tokens[index].getType() == Token.TAG_START && tokens[index].getValue().equalsIgnoreCase("PRESENTATION")) {
                index = readPresentationTag(tokens, index);
            } else if (tokens[index].getType() == Token.TAG_START && tokens[index].getValue().equalsIgnoreCase("DOMAINS")) {
                index = readDomainsTag(tokens, index);
            } else if (tokens[index].getType() == Token.TAG_START && tokens[index].getValue().equalsIgnoreCase("VARIABLES")) {
                index = readVariablesTag(tokens, index);
            } else if (tokens[index].getType() == Token.TAG_START && tokens[index].getValue().equalsIgnoreCase("PREDICATES")) {
                index = readPredicatesTag(tokens, index);
            } else if (tokens[index].getType() == Token.TAG_START && tokens[index].getValue().equalsIgnoreCase("CONSTRAINTS")) {
                index = readConstraintsTag(tokens, index);
            } else if (tokens[index].getType() == Token.TAG_START && tokens[index].getValue().equalsIgnoreCase("RELATIONS")) {
                index = readRelationsTag(tokens, index);
            }
        } while (tokens[index].getType() != Token.TAG_END || !tokens[index].getValue().equalsIgnoreCase("INSTANCE"));
        return index;
    }        
    
    /**
     * Reads the presentation tag. The function retuns the index of the next token
     * to be read.
     * @return The index of the next token to be read.
    */
    private int readPresentationTag(Token tokens[], int index) {        
        //System.out.println("\tPRESENTATION:");
        index++;       
        do {
            if (tokens[index].getType() == Token.PARAMETER && tokens[index].getValue().equalsIgnoreCase("NAME")) {
                index++;
                //System.out.println("\t\tname = " + tokens[index].getValue());                
            } else if (tokens[index].getType() == Token.PARAMETER && tokens[index].getValue().equalsIgnoreCase("MAXCONSTRAINTARITY")) {
                index++;
                //System.out.println("\t\tmaxConstraintArity = " + tokens[index].getValue());                
            } else if (tokens[index].getType() == Token.PARAMETER && tokens[index].getValue().equalsIgnoreCase("MINVIOLATEDCONSTRAINTS")) {
                index++;
                //System.out.println("\t\tminViolatedConstraints = " + tokens[index].getValue());                
            } else if (tokens[index].getType() == Token.PARAMETER && tokens[index].getValue().equalsIgnoreCase("NBSOLUTIONS")) {
                index++;
                //System.out.println("\t\tnbSolutions = " + tokens[index].getValue());                
            } else if (tokens[index].getType() == Token.PARAMETER && tokens[index].getValue().equalsIgnoreCase("TYPE")) {
                index++;
                //System.out.println("\t\ttype = " + tokens[index].getValue());                
            } else if (tokens[index].getType() == Token.PARAMETER && tokens[index].getValue().equalsIgnoreCase("FORMAT")) {
                index++;
                //System.out.println("\t\tformat = " + tokens[index].getValue());                
            }           
            index++;
        } while (tokens[index].getType() != Token.TAG_END);       
        return index;
    }
    
    /**
     * Reads the domains tag. The function retuns the index of the next token to
     * be read.
     * @return The index of the next token to be read.
    */
    private int readDomainsTag(Token tokens[], int index) {
        //System.out.println("\tDOMAINS:");
        do {
            index++;
            if (tokens[index].getType() == Token.TAG_START && tokens[index].getValue().equalsIgnoreCase("DOMAIN")) {
                index = readDomainTag(tokens, index);
            } else if (tokens[index].getType() == Token.PARAMETER && tokens[index].getValue().equalsIgnoreCase("NBDOMAINS")) {
                index++;
                //System.out.println("\t\tnbDomains = " + tokens[index].getValue());
                setNbDomains(Integer.parseInt(tokens[index].getValue()));
            }
        } while (tokens[index].getType() != Token.TAG_END || !tokens[index].getValue().equalsIgnoreCase("DOMAINS"));
        return index;
    }
    
    /**
     * Reads the domain tag. The function retuns the index of the next token
     * to be read.
     * @return The index of the next token to be read.
    */
    private int readDomainTag(Token tokens[], int index) {        
        int domain[];
        String domainName = ""; 
        //System.out.println("\t\t\tDOMAIN:");
        index++;
        do {
            if (tokens[index].getType() == Token.PARAMETER && tokens[index].getValue().equalsIgnoreCase("NAME")) {
                index++;
                //System.out.println("\t\t\t\tname = " + tokens[index].getValue());
                domainName = tokens[index].getValue();
            } else if (tokens[index].getType() == Token.PARAMETER && tokens[index].getValue().equalsIgnoreCase("NBVALUES")) {
                index++;
                //System.out.println("\t\t\t\tnbValues = " + tokens[index].getValue());                
            } else if (tokens[index].getType() == Token.TEXT) {
                //System.out.println("\t\t\t\tdomainValues = " + tokens[index].getValue());
                domain = this.readDomain(tokens[index].getValue());
                setDomain(domainName, domain);
                
            }
            index++;
        } while (tokens[index].getType() != Token.TAG_END || !tokens[index].getValue().equalsIgnoreCase("DOMAIN"));
        return index;
    }
    
    /**
     * Reads the variables tag. The function retuns the index of the next token to
     * be read.
     * @return The index of the next token to be read.
    */
    private int readVariablesTag(Token tokens[], int index) {     
        //System.out.println("\tVARIABLES:");
        do {
            index++;
            if (tokens[index].getType() == Token.TAG_START && tokens[index].getValue().equalsIgnoreCase("VARIABLE")) {
                index = readVariableTag(tokens, index);                
            } else if (tokens[index].getType() == Token.PARAMETER && tokens[index].getValue().equalsIgnoreCase("NBVARIABLES")) {
                index++;
                //System.out.println("\t\tnbVariables = " + tokens[index].getValue());
                setNbVariables(Integer.parseInt(tokens[index].getValue()));
            }
        } while (tokens[index].getType() != Token.TAG_END || !tokens[index].getValue().equalsIgnoreCase("VARIABLES"));
        return index;
    }
    
    /**
     * Reads the variable tag. The function retuns the index of the next token
     * to be read.
     * @return The index of the next token to be read.
    */
    private int readVariableTag(Token tokens[], int index) {
        String variableName = "", domainName = "";   
        //System.out.println("\t\t\tVARIABLE:");
        index++;
        do {
            if (tokens[index].getType() == Token.PARAMETER && tokens[index].getValue().equalsIgnoreCase("NAME")) {
                index++;
                //System.out.println("\t\t\t\tname = " + tokens[index].getValue());
                variableName = tokens[index].getValue();
            } else if (tokens[index].getType() == Token.PARAMETER && tokens[index].getValue().equalsIgnoreCase("DOMAIN")) {
                index++;
                //System.out.println("\t\t\t\tdomain = " + tokens[index].getValue());
                domainName = tokens[index].getValue();
                setVariable(variableName, domainName);                
            }
            index++;
        } while (tokens[index].getType() != Token.TAG_END);
        return index;
    }
    
    /**
     * Reads the relations tag. The function retuns the index of the next token to
     * be read.
     * @return The index of the next token to be read.
    */
    private int readRelationsTag(Token tokens[], int index) {
        //System.out.println("\tRELATIONS:");
        do {
            index++;
            if (tokens[index].getType() == Token.TAG_START && tokens[index].getValue().equalsIgnoreCase("RELATION")) {
                index = readRelationTag(tokens, index);
            } else if (tokens[index].getType() == Token.PARAMETER && tokens[index].getValue().equalsIgnoreCase("NBRELATIONS")) {
                index++;
                //System.out.println("\t\tnbRelations = " + tokens[index].getValue());
                setNbRelations(Integer.parseInt(tokens[index].getValue()));
            }
        } while (tokens[index].getType() != Token.TAG_END || !tokens[index].getValue().equalsIgnoreCase("RELATIONS"));
        return index;
    }
    
    /**
     * Reads the relation tag. The function retuns the index of the next token
     * to be read.
     * @return The index of the next token to be read.
    */
    private int readRelationTag(Token tokens[], int index) {        
        boolean semantic = false;
        String relationName = "", relation = "";        
        //System.out.println("\t\t\tRELATION:");
        
        index++;       
        do {
            if (tokens[index].getType() == Token.PARAMETER && tokens[index].getValue().equalsIgnoreCase("NAME")) {
                index++;
                //System.out.println("\t\t\t\tname = " + tokens[index].getValue());
                relationName = tokens[index].getValue();
            } else if (tokens[index].getType() == Token.PARAMETER && tokens[index].getValue().equalsIgnoreCase("ARITY")) {
                index++;
                //System.out.println("\t\t\t\tarity = " + tokens[index].getValue());
                arity = Integer.parseInt(tokens[index].getValue());
            } else if (tokens[index].getType() == Token.PARAMETER && tokens[index].getValue().equalsIgnoreCase("NBTUPLES")) {
                index++;
                //System.out.println("\t\t\t\tnbTuples = " + tokens[index].getValue());
            } else if (tokens[index].getType() == Token.PARAMETER && tokens[index].getValue().equalsIgnoreCase("SEMANTICS")) {
                index++;
                //System.out.println("\t\t\t\tsemantics = " + tokens[index].getValue());
                if (tokens[index].getValue().equalsIgnoreCase("SUPPORTS")) {
                    semantic = true;
                } else {
                    semantic = false;
                }
            } else if (tokens[index].getType() == Token.TEXT) {
                //System.out.println("\t\t\t\trelation = " + tokens[index].getValue());
                relation = tokens[index].getValue() + "|";    
            }          
            index++;
            setRelation(relationName, relation, arity, semantic);
        } while (tokens[index].getType() != Token.TAG_END || !tokens[index].getValue().equalsIgnoreCase("RELATION"));       
        return index;
    }
    
    /**
     * Reads the predicates tag. The function retuns the index of the next token to
     * be read.
     * @return The index of the next token to be read.
    */
    private int readPredicatesTag(Token tokens[], int index) {
        //System.out.println("\tPREDICATES:");         
        do {
            index++;
            if (tokens[index].getType() == Token.TAG_START && tokens[index].getValue().equalsIgnoreCase("PREDICATE")) {
                index = readPredicateTag(tokens, index);
            } else if (tokens[index].getType() == Token.PARAMETER && tokens[index].getValue().equalsIgnoreCase("NBPREDICATES")) {
                index++;
                //System.out.println("\t\tnbPredicates = " + tokens[index].getValue());
                setNbPredicates(Integer.parseInt(tokens[index].getValue()));
            }
        } while (tokens[index].getType() != Token.TAG_END || !tokens[index].getValue().equalsIgnoreCase("PREDICATES"));
        return index;
    }
    
    /**
     * Reads the predicate tag. The function retuns the index of the next token
     * to be read.
     * @return The index of the next token to be read.
    */
    private int readPredicateTag(Token tokens[], int index) {
        //System.out.println("\t\t\tPREDICATE:");
        String predicateName = ""; 
        index++;
        do {                   
            if (tokens[index].getType() == Token.PARAMETER && tokens[index].getValue().equalsIgnoreCase("NAME")) {
                index++;
                //System.out.println("\t\t\t\tname = " + tokens[index].getValue());
                predicateName = tokens[index].getValue();
            } else if (tokens[index].getType() == Token.TAG_START && tokens[index].getValue().equalsIgnoreCase("PARAMETERS")) {
                index = readParametersTag(tokens, index);
            } else if (tokens[index].getType() == Token.TAG_START && tokens[index].getValue().equalsIgnoreCase("EXPRESSION")) {
                index = readExpressionTag(tokens, index);
            }            
            index++;            
            setPredicate(predicateName, function, parameter);
        } while (tokens[index].getType() != Token.TAG_END || !tokens[index].getValue().equalsIgnoreCase("PREDICATE"));
        return index;
    }        
    
    /**
     * Reads the parameters tag. The function retuns the index of the next token
     * to be read.
     * @return The index of the next token to be read.
    */
    private int readParametersTag(Token tokens[], int index) {
        //System.out.println("\t\t\t\t\tPARAMETERS:");
        index++;
        do {
            if (tokens[index].getType() == Token.TEXT) {                
                //System.out.println("\t\t\t\t\t\tparameters = " + tokens[index].getValue());   
                parameter = tokens[index].getValue();
            }
            index++;
        } while (tokens[index].getType() != Token.TAG_END || !tokens[index].getValue().equalsIgnoreCase("PARAMETERS"));
        return index;
    }
    
    /**
     * Reads the expression tag. The function retuns the index of the next token
     * to be read.
     * @return The index of the next token to be read.
    */
    private int readExpressionTag(Token tokens[], int index) {
        //System.out.println("\t\t\t\t\tEXPRESSION:");
        index++;
        do {            
            if (tokens[index].getType() == Token.TAG_START && tokens[index].getValue().equalsIgnoreCase("FUNCTIONAL")) {                
                index = readFunctionalTag(tokens, index);
            } else if (tokens[index].getType() == Token.TAG_START && tokens[index].getValue().equalsIgnoreCase("MATH")) {                
                index = readFunctionalTag(tokens, index);
            }
            index++;
        } while (tokens[index].getType() != Token.TAG_END || !tokens[index].getValue().equalsIgnoreCase("EXPRESSION"));        
        return index;
    }
    
    /**
     * Reads the functional tag. The function retuns the index of the next token
     * to be read.
     * @return The index of the next token to be read.
    */
    private int readFunctionalTag(Token tokens[], int index) {
        //System.out.println("\t\t\t\t\t\tFUNCTIONAL:");
        index++;
        do {
            if (tokens[index].getType() == Token.TEXT) {                
                //System.out.println("\t\t\t\t\t\t\tfunction = " + tokens[index].getValue());  
                function = tokens[index].getValue();
            }
            index++;
        } while (tokens[index].getType() != Token.TAG_END || !tokens[index].getValue().equalsIgnoreCase("FUNCTIONAL"));        
        return index;
    }
    
    /**
     * Reads the constraints tag. The function retuns the index of the next token to
     * be read.
     * @return The index of the next token to be read.
    */
    private int readConstraintsTag(Token tokens[], int index) {
        //System.out.println("\tCONSTRAINTS:");
        do {
            index++;
            if (tokens[index].getType() == Token.TAG_START && tokens[index].getValue().equalsIgnoreCase("CONSTRAINT")) {
                index = readConstraintTag(tokens, index);
            } else if (tokens[index].getType() == Token.PARAMETER && tokens[index].getValue().equalsIgnoreCase("NBCONSTRAINTS")) {
                index++;
                //System.out.println("\t\tnbConstraints = " + tokens[index].getValue());
                setNbConstraints(Integer.parseInt(tokens[index].getValue()));
            }
        } while (tokens[index].getType() != Token.TAG_END || !tokens[index].getValue().equalsIgnoreCase("CONSTRAINTS"));
        return index;
    }
    
    /**
     * Reads the constraint tag. The function retuns the index of the next token
     * to be read.
     * @return The index of the next token to be read.
    */
    private int readConstraintTag(Token tokens[], int index) {
        String constraintName = "", scope = "", reference = "";
        //System.out.println("\t\t\tCONSTRAINT:");
        parameter = "";
        index++;       
        do {
            if (tokens[index].getType() == Token.PARAMETER && tokens[index].getValue().equalsIgnoreCase("NAME")) {
                index++;
                //System.out.println("\t\t\t\tname = " + tokens[index].getValue());
                constraintName = tokens[index].getValue();
            } else if (tokens[index].getType() == Token.PARAMETER && tokens[index].getValue().equalsIgnoreCase("ARITY")) {
                index++;
                //System.out.println("\t\t\t\tarity = " + tokens[index].getValue());                
            } else if (tokens[index].getType() == Token.PARAMETER && tokens[index].getValue().equalsIgnoreCase("SCOPE")) {
                index++;
                //System.out.println("\t\t\t\tscope = " + tokens[index].getValue());
                scope = tokens[index].getValue();
            } else if (tokens[index].getType() == Token.PARAMETER && tokens[index].getValue().equalsIgnoreCase("REFERENCE")) {
                index++;
                //System.out.println("\t\t\t\treference = " + tokens[index].getValue());
                reference = tokens[index].getValue();                                
            } else if (tokens[index].getType() == Token.TAG_START && tokens[index].getValue().equalsIgnoreCase("PARAMETERS")) {
                index = readParametersTag(tokens, index);                
            }      
            index++;
            setConstraint(constraintName, scope, reference, parameter);
        } while (tokens[index].getType() != Token.TAG_END);       
        return index;
    }       
    
    /**
     * Reads and interprets the domain given as a text.
     * @param text The text that contains the information about the domain.
    */
    private int[] readDomain(String text) {
        boolean sequencing = false;
        final int CONSTANT = 0,  SEQUENCE = 1;
        int i, j, sequenceStart, sequenceEnd, currentState = CONSTANT;
        int domain[];
        String currentText = "";
        ArrayList tempDomain = new ArrayList();
        text = text + " ";
        for (i = 0; i < text.length(); i++) {
            switch (currentState) {
                case CONSTANT:
                    if (text.charAt(i) == ' ') {
                        if (!currentText.equalsIgnoreCase("")) {
                            if (sequencing) {
                                sequenceStart = (Integer) tempDomain.get(tempDomain.size() - 1);
                                sequenceEnd = Integer.parseInt(currentText);
                                for (j = sequenceStart + 1; j <= sequenceEnd; j++) {
                                    tempDomain.add(j);
                                }
                                sequencing = false;
                            } else {
                                tempDomain.add(Integer.parseInt(currentText));                                  
                            }
                            currentText = "";
                            currentState = CONSTANT;
                        }
                        currentText = "";
                        currentState = CONSTANT;
                    } else if (text.charAt(i) == '.') {
                        if (!currentText.equalsIgnoreCase("")) {
                            tempDomain.add(Integer.parseInt(currentText));                            
                        }
                        currentText = "";
                        currentState = SEQUENCE;
                    } else {
                        currentText = currentText + text.charAt(i);
                    }
                    break;
                case SEQUENCE:
                    if (text.charAt(i) == '.') {
                        currentState = CONSTANT;
                        sequencing = true;
                    } else {
                        System.out.println("There was an error when parsing the text.");
                        System.out.println("Unexpected character: \'" + text.charAt(i) + "\'");
                        System.exit(1);
                    }
                    break;
            }
        }
        domain = new int[tempDomain.size()];
        for (i = 0; i < domain.length; i++) {
            domain[i] = (Integer) tempDomain.get(i);
        }
        return domain;
    }
    
    /**
     * Reads a text file an converts it into a String object.
     * @param fileName The file name where the data will be read from.
    */
    private String readFile(String fileName) {
        File file = new File(fileName);
        int size = (int) file.length(), i, j, id, d, maxd = 0, r, c, chars_read = 0, n = 0, k, lim, counter;
        FileReader in;
        try {
            in = new FileReader(file);
            char[] data = new char[size];
            while (in.ready()) {
                chars_read += in.read(data, chars_read, size - chars_read);
            }
            in.close();
            return new String(data, 0, chars_read);
        } catch (Exception e) {
            System.out.println("The file cannot be read \'" + fileName + "\'.");
            System.out.println("Exception text: " + e.toString());
            return null;
        }
    }    
    
    /**
     * Sets the number of domains in the instance. If an instance already has
     * information about the domains, this function overrides the previous 
     * information.
    */
    private void setNbDomains(int nbDomains) {        
        this.nbDomains = nbDomains;
        domains = new ArrayList(nbDomains);
        domainNames = new ArrayList(nbDomains);
    }
    
    /**
     * Sets the values for the specified domain. If the domain name has not been
     * used, a new domain is created. Otherwise the values of the existing domain
     * are replaced by the new one.
    */
    private void setDomain(String domainName, int values[]) {
        int domainId;
        if (domainNames.contains(domainName)) {
            domainId = domainNames.indexOf(domainName);
            domains.set(domainId, values);
        } else {
            domainNames.add(domainName);
            domains.add(values);
        }
    }
    
    /**
     * Sets the number of variables in the instance. If an instance already has
     * information about the variables, this function overrides the previous 
     * information.
    */
    private void setNbVariables(int nbVariables) {
        this.nbVariables = nbVariables;
        variables = new ArrayList(nbVariables);
    } 
    
    /**
     * Sets the values for the specified domain. If the domain name has not been
     * used, a new domain is created. Otherwise the values of the existing domain
     * are replaced by the new one.
    */
    private void setVariable(String variableName, String domainName) {
        int domainId;
        int values[];
        if (domainNames.contains(domainName)) {
            domainId = domainNames.indexOf(domainName);
            values = (int[]) domains.get(domainId);
            this.setVariable(variableName, values);
        } else {
            System.out.println("The domain " + domainName + " has not been defined before.");
            System.out.println("Assigning domain " + domainName + " to variable " + variableName + " is not allowed.");
            System.exit(1);
        }
    }
    
    /**
     * Sets the values for the specified domain. If the domain name has not been
     * used, a new domain is created. Otherwise the values of the existing domain
     * are replaced by the new one.
    */
    private void setVariable(String variableName, int values[]) {
        int i, variableId = -1;
        for (i = 0; i < variables.size(); i++) {
            if (((Variable)variables.get(i)).getId().equalsIgnoreCase(variableName)) {
                variableId = i;
                break;
            }
        }             
        if (variableId == -1) {
            variables.add(new Variable(variableName, values));
        } else {
            variables.set(variableId, new Variable(variableName, values));
        }        
    }
    
    /**
     * Sets the number of relations in the instance. If an instance already has
     * information about the relations, this function overrides the previous 
     * information.
    */
    private void setNbRelations(int nbRelations) {
        this.nbRelations = nbRelations;
        relationNames = new ArrayList(nbRelations);
        relations = new ArrayList(nbRelations);
        semantics = new ArrayList(nbRelations);
    } 
        
    /**
     * Sets the values for the specified relation. If the relation name has not been
     * used, a new relation is created. Otherwise the values of the existing relation
     * are replaced by the new one.
    */
    private void setRelation(String relationName, String relation, int arity, boolean semantic) {
        int relationId;
        if (relationNames.contains(relationName)) {
            relationId = relationNames.indexOf(relationName);
            relations.set(relationId, relation);
            arities.set(relationId, arity);
            semantics.set(relationId, semantic);
        } else {
            relationNames.add(relationName);
            relations.add(relation);
            arities.add(arity);
            semantics.add(semantic);
        }
    }
    
    /**
     * Sets the number of predicates in the instance. If an instance already has
     * information about the predicates, this function overrides the previous 
     * information.
    */
    private void setNbPredicates(int nbPredicates) {
        this.nbPredicates = nbPredicates;
        predicateNames = new ArrayList(nbPredicates);
        predicates = new ArrayList(nbPredicates);
    } 
    
    /**
     * Sets the values for the specified predicate. If the predicate name has not been
     * used, a new predicate is created. Otherwise the values of the existing predicate
     * are replaced by the new one.
    */
    private void setPredicate(String predicateName, String predicate, String parameter) {
        int predicateId;
        if (predicateNames.contains(predicateName)) {
            predicateId = predicateNames.indexOf(predicateName);
            predicates.set(predicateId, predicate);
            parameters.set(predicateId, parameter);
        } else {
            predicateNames.add(predicateName);
            predicates.add(predicate);
            parameters.add(parameter);
        }
    }          
    
    /**
     * Sets the number of constraints in the instance. If an instance already has
     * information about the constraints, this function overrides the previous 
     * information.
    */
    private void setNbConstraints(int nbConstraints) {
        this.nbConstraints = nbConstraints;
        constraintNames = new ArrayList(nbConstraints);
        scopes = new ArrayList(nbConstraints);
        references = new ArrayList(nbConstraints);
        parameterConstraints = new ArrayList(nbConstraints);
        constraints = new ArrayList(nbConstraints);
    } 
    
    /**
     * Sets the values for the specified constraint. If the constraint name has not been
     * used, a new constraint is created. Otherwise the values of the existing constraint
     * are replaced by the new one.
    */
    private void setConstraint(String constraintName, String scope, String reference, String parameter) {
        int constraintId;
        if (constraintNames.contains(constraintName)) {
            constraintId = constraintNames.indexOf(constraintName);
            scopes.set(constraintId, scope);
            references.set(constraintId, reference);
            parameterConstraints.set(constraintId, parameter);
        } else {
            constraintNames.add(constraintName);
            scopes.add(scope);
            references.add(reference);
            parameterConstraints.add(parameter);
        }
    }    
    
    private ArrayList textToTuples(String text, int arity) {
        int i, k = 0;
        int tuple[];
        String currentText = "";
        ArrayList tuples;
        tuple = new int[arity];
        tuples = new ArrayList(5);
        for (i = 0; i < text.length(); i++) {
            if (text.charAt(i) == ' ') {
                if (!currentText.equalsIgnoreCase("")) {
                    tuple[k] = Integer.parseInt(currentText);
                    k++;
                    currentText = "";
                } else {
                    currentText = "";
                }
            } else if (text.charAt(i) == '|') {
                if (!currentText.equalsIgnoreCase("")) {
                    tuple[k] = Integer.parseInt(currentText);                                      
                    k++;
                    currentText = "";
                }
                tuples.add(tuple);
                tuple = new int[arity];
                k = 0;
                currentText = "";
            } else {
                currentText = currentText + text.charAt(i);
            }
        }
        return tuples;
    }
    
    private String[] textToVariableIds(String text) {
        int i;
        String currentText = "";
        String scope[];
        ArrayList vars = new ArrayList(5);
        text = text + " ";
        for (i = 0; i < text.length(); i++) {
            if (text.charAt(i) == ' ') {
                if (currentText.equalsIgnoreCase("")) {
                    currentText = "";
                } else {
                    vars.add(currentText);
                    currentText = "";
                }
            } else {
                currentText = currentText + text.charAt(i);
            } 
        }
        scope = new String[vars.size()];
        for (i = 0; i < vars.size(); i++) {
            scope[i] = (String) vars.get(i);
        }
        return scope;
    }
    
    private String[] textToParameters(String text) {
        int i;
        String currentText = "";
        String scope[];
        ArrayList vars = new ArrayList(5);
        text = text + " ";
        for (i = 0; i < text.length(); i++) {
            if (text.charAt(i) == ' ') {
                if (currentText.equalsIgnoreCase("")) {
                    currentText = "";
                } else {
                    if (!currentText.equalsIgnoreCase("int")) {
                        vars.add(currentText);
                    }
                    currentText = "";
                }
            } else {
                currentText = currentText + text.charAt(i);
            } 
        }
        scope = new String[vars.size()];
        for (i = 0; i < vars.size(); i++) {
            scope[i] = (String) vars.get(i);
        }
        return scope;
    }
    
    private String textToFunction(String function, String parameters, String variableIds) {
        int i;        
        String params[] = textToParameters(parameters);
        String varIds[] = textToVariableIds(variableIds);
        //System.out.println("----------------------");
        //System.out.println("Function = " + function);
        //System.out.println("params = " + Utils.Array.toString(params));
        //System.out.println("varsId = " + Utils.Array.toString(varIds));
        if (params.length != varIds.length) {
            System.out.println("The size of the parameters does not match.");
            System.out.println("The system will halt.");
            System.exit(1);
        }
        for (i = 0; i < params.length; i++) {
            function = function.replaceAll(params[i], " " + varIds[i] + " ");
            //System.out.println("New function = " + function);
        }
        return function;
    }
    
    /* ------------------------------------------------------------------------ 
    END - UTILITY FUNCTIONS
    ------------------------------------------------------------------------- */               
    
}
