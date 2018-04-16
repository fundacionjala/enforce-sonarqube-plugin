public class Foo {
	public void bar() { 
		if (true) {
		} else if (true) {
		} else if (true) {
		} else {
			// this ain't good code, but it shouldn't trigger this rule
		}
	}
}