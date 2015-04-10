package ExamplePackage;

public class Run {
	public static void main(String[] args)
	{
		Animal[] a = new Animal[3];
		a[0] = new Dog();
		a[1] = new Cat();
		a[2] = new Cat();
		
		for (Animal b: a)
		{
			System.out.print(b.getName() + ", ");
			System.out.println(b.getSound());
		}
	}
}
