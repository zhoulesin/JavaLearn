# ThreadPool用法与优势

## 线程池优点:

- 降低资源消耗:通过重复利用已创建的线程降低线程创建和销毁造成的消耗.
- 提高响应速度:当任务到达时,任务可以不需要等到线程创建就能立即执行
- 提高线程的可管理性:线程是稀缺资源,如果无限制的创建,不仅会消耗系统资源,还会降低系统的稳定性,使用线程池可以进行统一的分配,调优和监控

## 线程池框架Executor

java中的线程池是通过Executor框架实现的,ThreadPoolExecutor:线程池的具体实现类,一般用的各种线程池都是基于这个类实现的.

```java
public ThreadPoolExecutor(int corePoolSize,int maximumPoolSize,long keepAliveTime,TimeUtil unit,BlockingQueue<Runnable> workQueue){
    
}
```

- corePoolSize:线程池的核心线程数,线程池中运行的线程数永远不会超过corePoolSize,默认情况下可以一直存货
- keepAliveTime:默认情况下,





















































