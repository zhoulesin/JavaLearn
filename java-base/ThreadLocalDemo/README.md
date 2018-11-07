# ThreadLocal用法详解和原理

ThreadLocal就是用来保存当前线程共享变量的

## 用法

ThreadLocal用于保存某个线程共享变量,对于同一个static ThreadLocal,不同线程只能冲中get,set,remove自己的变量,而不会影响其他线程的变量

- ThreadLocal.get:获取ThreadLocal中当前线程共享变量的值
- ThreadLocal.set:设置ThreadLoca中当前线程共享变量的值
- ThreadLocal.remove:移除ThreadLocal中当前线程共享变量的值
- ThreadLocal.initialValue:ThreadLocal没有被当前线程赋值时或当前线程刚调用remove方法后调用get方法,返回这个值

```java
public class MyThreadLocal{
    private static final ThreadLocal<Object> threadLocal = new ThreadLocal<Object>(){
        @Override
        protected Object initialValue(){
            syso("调用get方法时,当前线程没有共享变量或没有设置,调用initialValue获取默认值")
                return null;
        }
    }
    
    public static void main(String[] args){
        new Thread(new MyIntegerTask("IntegerTask1")).start();
        new Thread(new MyStringTask("StringTask1")).start();
        new Thread(new MyIntegerTask("IntegerTask2")).start();
        new Thread(new MyStringTask("StringTask2")).start();
    }
    
    public static class MyIntegerTask implements Runnable{
        private String name;
        MyIntegerTask(String name){
            this.name = name;
        }
        
        @Override
        public void run(){
            for(int i=0;i<5;i++){
                if(null == MyThreadLocal.threadlocal.get()){
                    MyThreadLocal.threadlocal.set(0);
                    syso("线程"+name+":0")
                }else{
                    int num = (Integer)MyThreadLocal.threadlocal.get();
                    MyThreadLocal.threadlocal.set(num + 1);
                    syso("线程"+name+":"+MyThreadLocal.threadlocal.get());
                    if(i==3){
                        MyThreadLocal.threadlocal.remove();
                    }
                }
                
                try{
                    Thread.sleep(1000);
                }catch(InterruptedException e){
                    
                }
            }
        }
    }
    
    public static class MyStringTask implements Runnable{
        private String name;
        MyStringTask(String name){
            this.name = name;
        }
        
        @Override
        public void run(){
            for(int i=0;i<5;i++){
                if(null == MyThreadLocal.threadlocal.get()){
                    MyThreadLocal.threadlocal.set("a");
                    syso("线程"+name+":a");
                }else{
                    String str = (String)MyThreadLocal.threadlocal.get();
                    MyThreadLocal.threadlocal.set(str+"a");
                    syso("线程"+name+":"+MyThreadLocal.threadlocal.get());
                }
                
                Thread.sleep(800);
            }
        }
    }
}
```

## 原理

线程共享变量缓存如下

Thread.ThreadLocalMap<ThreadLocal,Object>;

- Thread:当前线程,可以通过Thread.currentThread获取
- ThreadLocal:我们的static ThreadLocal变量
- Object:当前线程共享变量

我们调用ThreadLocal.get方法时,实际上是从当前线程中获取ThreadLocalMap<ThreadLocal,Object>,然后根据当前ThreadLocal获取当前线程共享变量Object



## 这种储存结构的好处

- 线程死去的时候,线程共享变量ThreadLocalMap则销毁
- ThreadLocalMap<ThreadLocal,Object>键值对数量为ThreadLocal的数量,一般ThreadLocal数量很少,相比在ThreadLocal中用Map<Thread,Object>键值对存储线程共享变量,(Thread数量一般来说比ThreadLocal数量多),性能提高很多.

## 关于ThreadLocalMap<ThreadLocal,Object>弱引用的问题

当线程没有结束,但是ThreadLocal已经被回收,则可能导致线程中存在ThreadLocalMap<null,Object>的键值对,造成内存泄漏(ThreadLocal被回收,ThreadLocal关联的线程共享变量还存在)

虽然ThreadLocal的get,set方法可以清除ThreadLocalMap中key为null的value,但是get,set方法在内存泄漏后并不会必然调用,

- 使用完线程共享变量后,显式调用ThreadLocalMap.remove方法清除线程共享变量
- JDK建议ThreadLocal定义为private static,这样ThreadLocal的弱引用问题则不存在了.



































