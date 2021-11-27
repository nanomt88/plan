package com.nanomt88.demo.cache.lru;

import java.util.HashMap;
import java.util.Map;

/**
 * Google的一道面试题：
 * Design an LRU cache with all the operations to be done in O(1)。
 *
 * 所有操作时间复杂度都为O(1): 插入(insert)、替换(replace)、查找（lookup）
 * 利用双向链表+哈希表:前者支持插入和替换 O(1), 后者支持查询 O(1)
 *
 * 
 * @author nanomt88@gmail.com
 * @create 2020-11-27 20:31
 **/
public class LruCacheImpl<K, V> extends LruCache<K, V> {

    private Map<K, Node<K, V>> map;

    private Node header;

    private Node end;

    private int size;

    public LruCacheImpl(int capacity, Storage lowSpeedStorage) {
        super(capacity, lowSpeedStorage);
        map = new HashMap<>((int) (capacity * 1.5) +1);
        size = 0;
    }


    @Override
    public V get(K key) {
        V val;
        if (!map.containsKey(key)) {
            System.out.println("load low cache value by : " + key);
            val = lowSpeedStorage.get(key);
            //添加新元素
            addNew(key, val);
        } else {
            Node<K, V> node = map.get(key);
            val = node.value;
            //设置node到链表头
            moveToHeader(node);
        }
        return val;
    }

    /**
     * 添加新元素到队列头
     *
     * @param key
     * @param value
     */
    private void addNew(K key, V value) {
        if ((size + 1) > capacity) {
            //队列满了
            removeEnd();
        }
        Node newNode = new Node(key, value);
        //挪动原有的header元素
        Node tmp = header;
        if (tmp == null) {
            //如果是空表，则直接添加
            header = newNode;
            end = newNode;
            //添加元素
            map.put(key, newNode);
            size++;
            return;
        }
        //添加元素
        map.put(key, newNode);

        tmp.before = newNode;
        newNode.after = tmp;
        header = newNode;
        size++;
    }

    /**
     * 删除最后一个元素
     */
    private void removeEnd() {
        Node nodeEnd = end;
        //设置倒数第二个元素为第一个
        if (nodeEnd.before != null) {
            nodeEnd.before.after = null;
        }
        //设置链表队尾为node前一个
        if (nodeEnd.before != null) {
            end = nodeEnd.before;
        } else {
            end = null;
        }
        //容量加1
        size--;
        //删除node
        nodeEnd.before = null;
        nodeEnd.after = null;
        map.remove(nodeEnd.key);

        System.out.println("Cache full , remove element : " + nodeEnd.key);
    }

    /**
     * 移动当前元素到队列头部
     *
     * @param node
     */
    private void moveToHeader(Node node) {
        //如果当前没有前后，则头尾都是它
        if (node.before == null && node.after == null) {
            header = node;
            end = node;
            return;
        }
        //将node从原有的链表中删除
        if (node.before != null && node.after != null) {
            //在队列中部，讲它从队列中挪出来
            node.before.after = node.after;
            node.after.before = node.before;
        } else if (node.after == null) {
            //队列尾部，设置倒数第二个为end
            node.before.after = null;
            end = node.before;
        } else if (node.before == null) {
            //队列头部
            return;
        }

        //将原有的header的before设置为node
        Node _first = header;
        _first.before = node;
        node.after = _first;
        node.before = null;
        //设置第一个元素为node
        header = node;
    }


    class Node<K, V> {
        K key;
        V value;
        private Node before;

        private Node after;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}


