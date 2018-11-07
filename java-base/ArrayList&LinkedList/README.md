# ArrayLlist和LinkedList的区别

- ArrayList的实现是基于数组,LinkedList的实现是基于双向链表
- 对于随机访问,ArrayList优于LinkedList
- 对于插入和删除,LinkedList优于ArrayList
- LinkedList比ArrayList更占内存,因为LinkedList的节点除了存储数据,还存储了两个引用,一个指向前一个元素,一个指向后一个元素

## 时间复杂度上的区别

- 随机访问的速度 ArrayList要比LinkedList快
- 插入的速度 LinkedList要比ArrayList快

## 控件复杂度上的区别

LinkedList中有一个私有内部类

```java
private static class Entry{
    Object element;
    Entry next;
    Entry previous;
}
```

LinkedList中的每一个元素还存储了它的前一个和后一个元素的索引

## 总结

- ArrayList的实现是基于数组,LinkedList的实现是基于双向链表
- 对于随机访问,ArrayList优于LinkedList,ArrayList可以根据下标以O(1)时间复杂度对元素进行随机访问.而LinkedList的每一个元素都依靠地址指针和它后一个元素连接在一起,在这种情况下,查找某个元素的时间复杂度是O(n)
- 对于插入和删除操作,LinkedList优于ArrayList,因为当元素被添加到LinkedList任意位置的时候,不需要像ArrayList那样重新计算大小或者是更新索引
- LinkedList比ArrayList更占内存,因为LinkedList的节点除了存储数据,还存储了两个引用,一个指向前一个元素,一个指向后一个元素