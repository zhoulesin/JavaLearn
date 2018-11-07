# Java垃圾回收机制gc

## 为什么需要垃圾回收

如果不进行垃圾回收,内存迟早被消耗空,因为我们在不断的分配内存空间而不进行回收.除非内存无限大,我们可以任性的分配而不回收,但事实并非如此.

## 那些内存需要回收

所谓要回收的垃圾,无非就是那些不可能再被任何途径使用的对象.

### 引用计数法

这个算法的实现,给对象添加一个引用计数器,每当一个地方引用这个对象时,计数器值+1.当引用失效时,计数器值-1.任何时刻计数器值为0的对象就是不可能再被使用的.这种算法的使用场景很多,但是java没有使用这种算法,因为这种算法很难解决对象之间相互引用的情况

```java
public class ReferenceCountingGC{
    private Object instance = null;
    private static final int _1MB = 1024*1024;
    
    private byte[] bigSize = new byte[2*_1MB];
    public static void main(String[] args){
        ReferenceCountingGC objectA = new ReferenceCountingGC();
        ReferenceCountingGC objectB = new ReferenceCountingGC();
        objectA.instance = objectB;
        objectB.instance = objectA;
        objectA = null;
        objectB = null;
        
        System.gc();
    }
}
```

- 两个对象相互引用着,但是虚拟机还是把两个对象回收掉了,说明虚拟机并不是通过引用计数法来判定对象是否存货的.

### 可达性分析法

