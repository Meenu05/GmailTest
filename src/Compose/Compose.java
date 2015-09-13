package Compose;

import org.testng.annotations.Test;
import AllMethods.AllMethods1;



public class Compose extends AllMethods1{
		
	@Test
		public void compose_mail() throws Exception{
		AllMethods1 almethods=new AllMethods1();
		almethods.Gmail_Compose();
			
		}
}
