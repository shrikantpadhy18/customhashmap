import java.util.*;
public class HashMapImplementation{


    static class MyHashMap<K,V>{
        private static  final int DEFAULT_CAPACITY = 4;
        private static  final float DEFAULT_LOAD_FACTOR = 0.75f;
        private class Node{
            K key;
            V value;
            Node(K key,V value){
                this.key = key;
                this.value = value;
            }
        }

            private int n; // number of entruies in map

            private LinkedList<Node>[] bucket;


            private void initBuckets(int N){
                // N : size of bucket array
                bucket = new LinkedList[N];

                for(int i = 0; i < bucket.length; i++){
                    bucket[i] = new LinkedList<>();
                }
            }

            private int HashFunc(K key){
                int hc = key.hashCode();
                return Math.abs(hc)%bucket.length;            
            }

            private int searchInBucket(K key,LinkedList<Node> ll){
                // traverse linkedlist and looks for node with key if found it return key or else it return null

                for(int i = 0; i < ll.size();i++){
                    if(ll.get(i).key.equals(key)){
                        return i;
                    }
                }
                return -1;
            }

            public MyHashMap(){
                initBuckets(DEFAULT_CAPACITY);
            }

            public int size(){
                // return number of entries in map
                return n;

            }

            public int capacity(){
                return bucket.length;
            }
            public float load(){
                return n*1.0f/bucket.length;
            }

            public boolean containsKey(K key){
                int bi = HashFunc(key);
                LinkedList<Node> currBucket = bucket[bi];
                int ei = searchInBucket(key,currBucket);
                if(ei == -1){
                    return false;

                }
                return true;
            }

            private void rehash(){
                LinkedList<Node> [] oldbucket = bucket;
                n = 0;
                initBuckets(oldbucket.length*2);
                for(var bucket : oldbucket){
                    for( var node : bucket){
                        put(node.key,node.value);
                    }
                }

            }

            public void put(K key,V value){
                int bi  = HashFunc(key);
                LinkedList<Node> currBucket = bucket[bi];
                int ei = searchInBucket(key,currBucket);
                if(ei == -1){
                    // key dont exist insert new node

                    Node node = new Node(key,value);
                    currBucket.add(node);
                    n++;
                }
                else{
                    Node currNode = currBucket.get(ei);
                    currNode.value = value;
                }

                if(n >= bucket.length*DEFAULT_LOAD_FACTOR){
                    rehash();
                }
            }


            public V get(K key){
                int bi  = HashFunc(key);
                LinkedList<Node> currBucket = bucket[bi];
                int ei = searchInBucket(key,currBucket);
                if(ei == -1){
                    return null;
                }
                Node currNode = currBucket.get(ei);
                return currNode.value;
            }

            public V remove(K key){
                int bi  = HashFunc(key);
                LinkedList<Node> currBucket = bucket[bi];
                int ei = searchInBucket(key,currBucket);
                if(ei == -1){
                    return null;
                }
                Node currNode =  currBucket.get(ei);
                V val = currNode.value;
                currBucket.remove(ei);
                n--;
                return val;
            }

    }

    public static void main(String [] args){
        MyHashMap<String,Integer> mp = new MyHashMap<>();

        System.out.println("Testing ... ");

        mp.put("a",1);
        mp.put("b",2);
        System.out.println(mp.capacity());
        System.out.println(mp.load());
        mp.put("c",3);
        mp.put("c",30);
        mp.put("x",133);
        mp.put("y",73);
        System.out.println(mp.containsKey("c"));
        System.out.println(mp.capacity());
        System.out.println(mp.load());
        System.out.println(" size : "+mp.size());

        System.out.println("Testing get ...");

        System.out.println(mp.get("a"));
        System.out.println(mp.get("b"));
        System.out.println(mp.get("c"));
        System.out.println(mp.get("x"));
        System.out.println(mp.get("y"));
        System.out.println(mp.get("HSDKJGH"));
        System.out.println("remove test..");

        System.out.println(mp.remove("c"));
        System.out.println(mp.remove("c"));

        System.out.println(mp.containsKey("c"));
        
    }
}