# Thread--join

https://www.cnblogs.com/huangzejun/p/7908898.html

## join的示例和作用

当我们调用某个线程的join方法时，这个方法会挂起调用线程，直到被调用线程结束执行，调用线程才会继续执行.

```java
//父线程
public class Parent extends Thread{
    public void run(){
        Child child = new Child();
        child.start();
        child.join();
        //...
    }
}
```

```java
//子线程
public void Child extends Thread{
    public void run(){
        //...
    }
}
```

在Parent.run中，通过Child child = new Child();新建了child子线程(child处于NEW状态),调用child.start()(child处于RUNNABLE状态),调用child.join()

在Parent中调用child.join()后，child子线程正常运行，Parent父线程会等待child子线程结束后再继续运行.