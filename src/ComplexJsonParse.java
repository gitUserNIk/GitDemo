import files.PayLoad;
import files.ReusableMethods;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String strCourse = PayLoad.coursesPrice();
		
		JsonPath jsonCourse = ReusableMethods.stringToJson(strCourse);
		
		//Print No of courses returned by API
		int noOfCourses = jsonCourse.getInt("courses.size()");
		System.out.println("No of courses are: "+noOfCourses);
		
		//Print Purchase Amount
		int purchaseAmount = jsonCourse.getInt("dashboard.purchaseAmount");
		System.out.println("Purchase Amount is: "+purchaseAmount);
		
		//Print Title of the first course
		String strFirstCourseTitle = jsonCourse.getString("courses[0].title");
		System.out.println("Title of the first course is : "+strFirstCourseTitle);
		
		//Print All course titles and their respective Prices
		for(int i=0;i<noOfCourses;i++)
		{
			System.out.println("Title : "+jsonCourse.getString("courses["+i+"].title"));
			System.out.println("Price : "+jsonCourse.getInt("courses["+i+"].price"));	
		}
		
		//Print no of copies sold by RPA Course
		for(int i=0;i<noOfCourses;i++)
		{
			String strTitle = jsonCourse.getString("courses["+i+"].title");
			if(strTitle.equalsIgnoreCase("RPA"))
			{
				System.out.println("No of copies sold by RPA Course is : "+jsonCourse.getInt("courses["+i+"].copies"));
				break;
			}
		}
		
		//Verify if Sum of all Course prices matches with Purchase Amount
		int totalAmount = 0;
		for(int i=0;i<noOfCourses;i++)
		{
			int price = jsonCourse.getInt("courses["+i+"].price");
			int copies = jsonCourse.getInt("courses["+i+"].copies");
			totalAmount = totalAmount+(price*copies);
		}
		
		if(totalAmount==purchaseAmount)
		{
			System.out.println("Sum of all Course prices matches with Purchase Amount i.e. "+totalAmount);
		}
		else
		{
			System.out.println("Sum of all Course prices does not matche with Purchase Amount. \nPurchase Amount: "+purchaseAmount
					+"\nTotal Amount: "+totalAmount);
		}
	}

}
;