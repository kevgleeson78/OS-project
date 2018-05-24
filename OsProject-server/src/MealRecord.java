import java.io.Serializable;
//Meal record class to create a new instance of the user meal records that are entered to the system.
//also used when deleting a meal record
public class MealRecord implements Serializable {

	private String mealType;
	private String mealDescription;

	public MealRecord() {
		mealType = new String();
		mealDescription = new String();
	}
	
	public String getMealType() {
		return mealType;
	}

	public void setMealType(String mealType) {
		this.mealType = mealType;
	}

	public String getMealDescription() {
		return mealDescription;
	}
	//used to limit the amount of characters that the user can enter for the description of the meal they have had.
	public void setMealDescription(String mealDescription) {
		if (mealDescription.length()>100) {
		    String cutName = mealDescription.substring(0, 100);
		    mealDescription = cutName;
		}
		this.mealDescription = mealDescription;
	}
}
