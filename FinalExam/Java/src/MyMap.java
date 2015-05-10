import java.util.ArrayList;


public class MyMap {

	private ArrayList<Object> keys = new ArrayList<Object>();
	private ArrayList<Object> values = new ArrayList<Object>();
	
	/**
	 * 
	 * Requires: Nothing
	 * Effects: Creates an instance of the MyMap class with keys and values both empty
	 * Modifies: Nothing
	 */
	public MyMap()	{
		
	}
	
	/**
	 * 
	 * @param keys list of key values, must not be null
	 * @param values list of map values, must not be null
	 * Effects: Creates a new MyMap object with the given keys and values
	 * Modifies: Alters class variables keys and values to equal given key and values
	 */
	public MyMap(ArrayList<Object> keys, ArrayList<Object> values)	{
		
	}
	
	/**
	 * 
	 * @param toCopy is a MyMap that you want to make a copy out of. toCopy must not be null;
	 * Effects: creates a new MyMap object that is a copy of the passed MyMap
	 * Modifies: Alters class variables keys and values to equal given key and values from toCopy variable
	 */
	public MyMap(MyMap toCopy)	{
		
	}
	
	/**
	 * 
	 * @param key must not be null
	 * @return Value corresponding to the given key, or null if the given key is not found
	 * Modifies: Nothing 
	 */
	public Object get(Object key)	{
		
	}
	
	/**
	 * 
	 * @param key must not be null
	 * @param value must not be null
	 * @return true if object and value successfully added to their respective ArrayLists, otherwise false
	 * Modifies: keys and values to include passed in key and value
	 */
	public boolean add(Object key, Object value)	{
		
	}
	
	/**
	 * 
	 * @param key must not be null
	 * @return true if key successfully removed from keys and corresponding value is removed from values, otherwise false
	 * Modifies: keys and values to not include given key and matching value
	 */
	public boolean remove(Object key)	{
		
	}
	
	/**
	 * Requires: nothing
	 * @return size of MyMap
	 * Modifies: nothing
	 */
	public int size()	{
		
	}
	
	/**
	 * 
	 * @param key must not be null
	 * @return true if values contains key, otherwise false
	 * Modifies: nothing
	 */
	public boolean containsKey(Object key)	{
		
	}
	
	/**
	 * Requires: nothing
	 * @return true is values and keys are empty, otherwise false
	 * Modifies: nothing
	 */
	public boolean isEmpty()	{
		
	}
}
