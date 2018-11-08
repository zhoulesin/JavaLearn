# Java线程与线程,进程与进程间通信

- 进程是什么

  是具有一定独立功能的程序,它是系统进行资源分配和调度的一个独立单位,重点在系统调度和单独的单位,也就是说进程是可以独立运行的一段程序

- 线程是什么

  线程是进程的一个实体,是cpu调度和分派的基本单位,他是比进程更小的能独立运行的基本单位,线程自己基本上不拥有系统资源

  在运行时,只是占用一些计数器,寄存器和栈

- 他们之间的关系

  - 一个线程只能属于一个进程,而一个进程可以有多个线程,但至少有一个线程(主线程)
  - 资源分配给进程,同一个进程的所有线程共享该进程的所有资源
  - 线程在执行过程中,需要协作同步..不同的进程的线程间要利用消息通信的办法实现同步
  - 处理机分给线程,即真正在处理机上运行的是线程
  - 线程是指进程内的一个执行单元,也是进程内的可调度实体

- 他们之间的区别

  - 调度:线程作为调度和分配的基本单位,进程作为拥有资源的基本单位
  - 并发性:不仅进程之间可以并发执行,同一个进程的多个线程之间也可以并发执行
  - 拥有资源:进程是拥有资源的一个独立单位,线程不拥有系统资源,但可以访问隶属于进程的资源.

## 线程与线程间通信

- 共享变量
- wait/notify机制
- lock/condition机制
- 管道

### 共享变量

线程间发送信号的一个简单方式是在共享对象的变量里设置信号值.线程A在一个同步块里设置boolean型成员变量aaa为true,线程B也在同步块里读取aaa这个成员变量.

```java
public class MySignal{
    protected boolean aaa = false;
    
    public synchronized boolean aaa(){
        return this.aaa;
    }
    
    public synchronized void setAaa(boolean aaa){
        this.aaa = aaa;
    }
}
```

线程A和B必须获得指向一个MySignal共享实例的引用,以便进行通信.如果他们持有的引用指向不同的MySignal实例,那么彼此将不能检测到对方的信号.需要处理的数据可以存放在一个共享缓存区里,他和Signal实例是分开存放的.

### wait/notify机制

为了实现线程通信,我们可以使用object提供的wait,notify方法,调用wait方法会释放对该同步监视器的锁定.这几个方法必须由同步监视器对象来调用..

- 对于使用synchronized修饰的同步方法,因为该类的默认实例this就是同步监视器,所以可以直接调用这几个方法
- 对于synchronized修饰的同步代码块,同步监视器是synchronized括号里的对象,所以必须使用该对象调用这几个方法

假设系统中有两条线程,这两条线程分别代表取钱者和存钱者.现在系统有一种特殊的要求,系统要求存款者可取款者不断的实现存款和取钱的动作,而且要求每当存款折将钱存入指定的账户后,取钱者立即将钱取走.不允许存款折两次存钱,也不允许取钱者两次取钱.

我们通过设置一个旗标来表示账户中是否已有存款,有就为true,没有就为false

```java
public class Account{
    private String accountNo;
    private double balance;
    
   	private boolean flag = false;
    
    public Account(){}
    public Account(String accountNo,double balance){
        this.accountNO = accountNo;
        this.balance = balance;
    }
    
    public synchronized void draw(double drawAmount){
        try{
            if(!flag){
                this.wait();
            }else{
                //取钱
                syso(Thread.currentThread().getName()+"取钱"+drawAmount);
                balance = balance-drawAmount;
                syso("余额"+balance);
                //标识账户无存款
                flag = false;
                //唤醒其他线程
                this.notifyAll();
            }
        }catch(Exception e){
            e.print...();
        }
    }
    
    public synchronized void deposit(double depositAmount){
        try{
            if(flag){
                this.wait();
            }else{
                syso(Thread.currentThread().getName()+"存钱:" + depositAmount);
                balance = balance + depositAmount;
                syso("账户余额为:" + balance);
                flag = true;
                //唤醒其他线程
                this.notifyAll();
            }
        }catch(Exception e){
            e.print...();
        }
    }
}
```

两个线程类

取钱类

```java
public void DrawThread implements Runnable{
    private Account account;
    private double drawAmount;
    
    public DrawThread(Account account,double drwaAmount){
        this.account = account;
        this.drawAmount = drawAmount;
    }
    
    public void run(){
        for(int i=0;i<100;i++){
            account.draw(drawAmount);
        }
    }
}
```

存钱类

```java
public class DepositThread implements Runnable{
    private Account account;
    private double depositAmount;
    
    public DepositThread(Account account,double depositAmount){
        this.account = account;
        this.depositAmount = depositAmount;
    }
    
    public void run(){
        for(int i=0;i<100;i++){
            account.deposit(depositAmount);
        }
    }
}
```

测试

```java
public class Test{
    public static void main(String[] args){
        Account account = new Account();
        new Thread(new DrawThread(account,800),"取钱").start();
        new Thread(new DepositThread(account,800),"存钱A").start();
        new Thread(new DepositThread(account,800),"存钱B").start();
        new Thread(new DepositThread(account,800),"存钱C").start();
    }
}
```

### Lock/Condition机制

如何在程序不适用synchronized关键字来保持同步,而是直接使用Lock对象来保持同步,则系统中不存在隐式的同步监视器对象,也就不能使用wait,notify来协调线程的运行

当使用lock对象保持同步时,java为我们提供了condition类来协调线程的运行.

```java
public class Account{
    //显式定义Lock对象
    private final Lock lock = new ReentrantLock();
    //获得指定Lock对象对应的条件变量
    private final Condition con = lock.newCondition();
    
    private String accountNo;
    private double balance;
    //是否有存款
    private boolean flag = false;
    
    public Account(){}
    public Account(String no,double balance){
        this.accountNo = no;
        this.balance = balance;
    }
    
    //取钱
    public void draw(double drawAmount){
        //加锁
        lock.lock();
        try{
            if(flag){
                syso(Thread.currentThread().getName()+"取钱:" + drawAmount);
                balance -= drawAmount;
                syso("余额:" + balance);
                flag = false;
                con.signalAll();
            }else{
                con.await();
            }
        }catch(Exception e){
            
        }finally{
            lock.unlock();
        }
    }
    
    //存钱
    public void deposit(double depositAmount){
        lock.lock();
        try{
            if(flag){
                con.await();
            }else{
                syso(Thread.currentThread().getName()+"存钱:" + depositAmount);
                balance += depositAmount;
                syso("余额:" + balance);
                flag = true;
                con.signalAll();
            }   
        }catch(Exception e){}
        finally{
            lock.unlock();
        }
    }
}
```

显式的使用Lock对象来充当同步监视器,使用Condition对象来暂停指定线程,唤醒指定线程!!

### 管道

管道流是Java中线程通讯的常用方式之一,

- 创建管道输出流PipedOutputStream pos和管道输入流PipedInputStream pis
- 将pos和pis匹配,pos.connect(pis)
- 将pos赋给信息输入线程,pis赋给信息获取线程,就可以实现线程间的通讯了

```java
public class TestPipeConnection{
    public static void main(String[] args){
        //管道输出流
        PipedOutputStream pos = new PipedOutputStream();
        //管道输入流
        PipedInputStream pis = new PipedInputStream();
        try{
            //连接管道
            pos.connect(pis);
        }catch(IOException e){
            e.print...();
        }
        //生产者线程
        Producer p = new Producer(pos);
        //消费者线程
        Consumer c = new Consumer(pis);
        
        p.start();
        c.start();
    }
    
    class Producer extends Thread{
        private PipedOutputStream pos;
        public Producer(PipedOutputStream pos){
            this.pos = pos;
        }
        
        public void run(){
            int i=0;
            try{
                while(true){
                    this.sleep(2000);
                    pos.write(i);
                    i++;
                }
            }catch(Exception e){
                e.print();
            }
        }
    }
    
    class Consumer extends Thread{
        private PipedInputStream pis;
        public Consumer(PipedInputStream pis){
            this.pis = pis;
        }
        
        public void run(){
            try{
                while(true){
                    syso("consumer:::" + pis.read());
                }
            }catch...
        }
    }
    
}
```

管道流使用起来方便:

- 管道流只能在两个线程之间传递数据

  线程Consumer1和Consumer2同时从pis中read数据,当线程producer往管道流写入一段数据后,每一个时刻只有一个线程能获取到数据.并不是两个线程都能获取到producer发送来得数据,因此一个管道流只能用于两个线程间的通讯..不仅仅是管道流,其他io方式都是一对一传输

- 管道流只能实现单向发送,如果要两个线程之间互相通讯,则需要两个管道流

  线程producer通过管道流向线程consumer发送数据,如果线程consumer想给线程producer发送数据,则需要新建另一个管道流pos1和pis1,将pos1赋给consumer将pis1赋给producer..

## 进程与进程间通信

- 管道pipe:管道可用于具有亲缘关系进程间的通信,允许一个进程和另一个与他有共同祖先的进程之间进行通信
- 命名管道named pipe:命名管道克服了管道没有名字的限制,因此,除具有管道所具有的功能外,他还允许无亲缘关系进程间的通信.命名管道在文件系统中有对应的文件名.命名管道通过命令mkfifo或系统调用mkfifo来创建
- 信号signal:信号是比较复杂的通信方式,用于通知接收进程有某种事件发生,除了用于进程间通信外,进程还可以发送信号给进程本身;linux除了支持Unix早期信号语义函数igal外,还支持语义符合posix.1标准的信号函数sigaction
- 消息队列message:消息队列是消息的链接表,包括posix消息队列system v消息队列,有足够的权限的进程可以向队列中添加消息,被赋予读权限的进程则可以读走队列中的消息.消息队列克服了信号承载信息量好,管道只能承载无格式字节流以及缓冲区大小受限等缺点
- 共享内存:使得多个进程可以访问同一块内存空间,是最快的可用ipc形式.是针对其他通信机制运行效率较低而设计的.往往与其他通信机制,如信号量结合使用,来达到进程间的同步及互斥
- 内存映射mapped memory:内存映射允许任何多个进程间通信,每一个使用该机制的进程通过把一个共享的文件映射到自己的进程地址空间来实现它
- 信号量semaphore:主要作为进程间以及同一进程不同线程之间的同步手段
- 套接字socket:更为一般的进程间通信机制,可用于不同机器之间的进程间通信.期初是unix系统的bsd分支开发出来的.但现在一般可以一直到其他类unix系统上,linux和system v的变种都支持套接字.









































