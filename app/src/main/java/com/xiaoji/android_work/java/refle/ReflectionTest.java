package cn.enjoyedu.refle.more;

public class ReflectionTest {
    public static void main(String[] args) throws Exception {
        String className = "cn.enjoyedu.refle.more.Person";
        Class<Person> clazz = (Class<Person>) Class.forName(className);

        //------------------构造函数的反射---------------------------
        System.out.println("获取全部构造器Constructor对象-----");
        Constructor<Person>[] constructors
                = (Constructor<Person>[]) clazz.getConstructors();
        for(Constructor<Person> constructor: constructors){
            System.out.println(constructor);
        }

        System.out.println("获取某一个Constructor 对象，需要参数列表----");
        Constructor<Person> constructor
                = clazz.getConstructor(String.class, int.class);
        System.out.println(constructor);

        //2. 调用构造器的 newInstance() 方法创建对象
        System.out.println("调用构造器的 newInstance() 方法创建对象-----");
        Person obj = constructor.newInstance("Mark", 18);
        System.out.println(obj.getName());


        //------------------成员变量的反射---------------------------
        System.out.println("获取公用和私有的所有字段，但不能获取父类字段");
        Field[] fields = clazz.getDeclaredFields();
        for(Field field: fields){
            System.out.print(" "+ field.getName());
        }
        System.out.println();
        System.out.println("---------------------------");


        System.out.println("获取指定字段");
        Field field = clazz.getDeclaredField("name");
        System.out.println(field.getName());

        Person person = new Person("ABC",12);
        System.out.println("获取指定字段的值");
        Object val = field.get(person);
        System.out.println(field.getName()+"="+val);

        System.out.println("设置指定对象指定字段的值");
        field.set(person,"DEF");
        System.out.println(field.getName()+"="+person.getName());

        System.out.println("字段是私有的，不管是读值还是写值，" +
                "都必须先调用setAccessible（true）方法");
        //     比如Person类中，字段name字段是非私有的，age是私有的
        field = clazz.getDeclaredField("age");
        field.setAccessible(true);
        System.out.println(field.get(person));


        //------------------方法的反射---------------------------
        System.out.println("获取clazz对应类中的所有方法，" +
                "不能获取private方法,且获取从父类继承来的所有方法");
        Method[] methods = clazz.getMethods();
        for(Method method:methods){
            System.out.print(" "+method.getName()+"()");
        }
        System.out.println("");
        System.out.println("---------------------------");

        System.out.println("获取所有方法，包括私有方法，" +
                "所有声明的方法，都可以获取到，且只获取当前类的方法");
        methods = clazz.getDeclaredMethods();
        for(Method method:methods){
            System.out.print(" "+method.getName()+"()");
        }
        System.out.println("");
        System.out.println("---------------------------");

        System.out.println("获取指定的方法，" +
                "需要参数名称和参数列表，无参则不需要写");
        //  方法public void setName(String name) {  }
        Method method = clazz.getDeclaredMethod("setName", String.class);
        System.out.println(method);
        System.out.println("---");

        //  方法public void setAge(int age) {  }
        /* 这样写是获取不到的，如果方法的参数类型是int型
        如果方法用于反射，那么要么int类型写成Integer： public void setAge(Integer age) {  }
        要么获取方法的参数写成int.class*/
        method = clazz.getDeclaredMethod("setAge", int.class);
        System.out.println(method);
        System.out.println("---------------------------");


        System.out.println("执行方法，第一个参数表示执行哪个对象的方法" +
                "，剩下的参数是执行方法时需要传入的参数");
        Object obje = clazz.newInstance();
        method.invoke(obje,18);

        /*私有方法的执行，必须在调用invoke之前加上一句method.setAccessible（true）;*/
        method = clazz.getDeclaredMethod("privateMethod");
        System.out.println(method);
        System.out.println("---------------------------");
        System.out.println("执行私有方法");
        method.setAccessible(true);
        method.invoke(obje);

    }
}
