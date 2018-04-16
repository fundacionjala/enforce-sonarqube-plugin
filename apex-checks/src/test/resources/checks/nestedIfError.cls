public class Foo {
	public void bar() { 
		Integer x=2; 
		Integer y=3; 
		Integer z=4; 
		if (x>y) {
			if (y>z) {
				if (z==x) { 
					// this is officially out of control now 
				} 
			} 
		}
	}
}