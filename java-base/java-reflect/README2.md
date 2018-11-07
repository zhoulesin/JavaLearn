## java中的反射机制详解

## 什么是反射

在运行状态中,对于任意一个类,都能够获取到这个类的所有属性和方法,对于任意一个对象,都能调用它的任意一个方法和属性(包括私有的方法和属性),这种动态获取的信息以及动态调用对象的方法的功能就称为java语言的反射机制.通过反射,该类对我们来说是完全透明的,想要获取任何东西都可以.

想要使用反射机制,就要先获取到该类的字节码文件对象.class,通过字节码文件对象,就能够通过该类中的方法获取到我们想要的所有信息(方法,属性,类名,父类名,实现的接口等),每一个类对应着一个字节码文件也就对应着一个class类型的对象,也就是字节码文件对象.

获取字节码文件对象的三种方式

- Class claz = Class.forName("全限定类名"); 此时该类还是源文件阶段,并没有变为字节码文件
- Class clz = Person.class; //该类处于字节码阶段
- Class cla = p.getClass(); //创建对象阶段

## Class类的API

### 通过字节码对象创建实例对象

```java
//User类还没有加载,在源文件阶段就获取其字节码文件对象
Class clz = Class.forName("com.zhoulesin.base.User");
//创建User实例
User user = (User)clz.newInstance();
```

###  获取构造器方法

- 获取指定构造器方法

```java
Class clz = Class.forName("com.zhoulesin.base.User");
Constructor constructor = clz.getConstructor(int.class,String.class);
User user = (User)constructor.newInstance(12,"asd");
```

- 获取全部构造器方法

```java
Class clz = Class.forName("...User");
Constructor[] constructors = clz.getConstructors();
for(int i=0;i<constructors.length;i++){
    Class[] parameterTypes = constructors[i].getParameterTypes();
    System.out.print("第"+i+"个构造函数");
    for(int j=0;j<parameterTypes.length;j++){
        syso(parameterTypes[j].getName()+" ");
    }
}
```

### 获取成员变量

- 获取指定成员变量

```java
Class clz = Class.forName("...User");
User user = (User)clz.newInstance();
Field field = clz.getDeclaredField("id");
field.setAccessible(true);
field.setInt(user,6);
syso(field.getInt(user));
```

​	Class.getFile(string)方法可以获取类中的指定字段,如果是自由的可以用clz.getDeclaredField方法获取,通过set(obj,"asd");可以设置指定对象上该字段的值,如果是私有的需要先调用setAccessible(true)设置访问权限,用获取的指定的字段调用get(obj)可以获取指定对象中该字段的值

- 获取全部成员变量

```java
Class clz = Class.forName("...User");
User user = (User)clz.newInstance();
user.setId(3);
user.setName("asd");
Field[] fields = clz.getDeclaredFields();
for(int i=0;i<fields.length;i++){
    fields[i].setAccessible(true);
    syso(fields[i].get(user));
}
```



























