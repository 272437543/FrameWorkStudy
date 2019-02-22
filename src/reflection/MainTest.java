package reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;

public class MainTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Class<Son> sonClass = Son.class;
		System.out.println("������" + sonClass.getName());
		Field[] fields = sonClass.getFields();
		for (Field field : fields) {
			System.out.println("��" + field.getType().getName() + " - " + field.getName());
		}
		System.out.println(" ----------------- ");
		Method[] methods = sonClass.getMethods();
		for (Method method : methods) {
			//System.out.println("������" + method);
			int modifiers = method.getModifiers();
	        System.out.print(modifiers + " " + Modifier.toString(modifiers) + " : ");
	        
	        Class<?> returnType = method.getReturnType();
	        System.out.print(returnType.getTypeName() + " - ");

	        System.out.print(method.getName());
	        
	        Parameter[] parameters = method.getParameters();
	        System.out.print("[");
	        for (Parameter parameter : parameters) {
				System.out.print(parameter.getType().getName() + ",");
			}
	        System.out.print("]");
	        System.out.println();
		}
	}

}
