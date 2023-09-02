package hashtable;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


class HashTable{
    class Node{
        String key;
        String value;

        public Node(String key, String value) {
            this.key = key;
            this.value = value;
        }

        String value(){
            return value;
        }
        void value(String value){
            this.value = value;
        }
    }

    LinkedList<Node>[] data;
    HashTable(int size){
        this.data = new LinkedList[size];
    }
    int getHashCode(String key){
        int hashcode = 0;
        for (char c : key.toCharArray()) {
            hashcode+= c;
        }
        return hashcode;
    }
    int convertToIndex(int hashcode){
        return hashcode % data.length;
    }
    Node searchKey(LinkedList<Node> list , String key){
        if(list == null) return null;
        for (Node node : list) {
            if(node.key.equals(key)){
                return node;
            }
        }
        return null;
    }
    void put(String key, String value){
        int hashcode = getHashCode(key);
        int index = convertToIndex(hashcode);
        System.out.println(key + ", hashcode(" + hashcode + "), index(" + index +")");
        LinkedList<Node> list = data[index];
        if(list == null){
            list = new LinkedList<>();
            data[index] = list;
        }
        Node node =  searchKey(list, key);
        if(node == null){
            list.addLast(new Node(key, value));
        }else {
            node.value(value);
        }

        }
    String get(String key){
        int hashcode = getHashCode(key);
        int index = convertToIndex(hashcode);
        LinkedList<Node> list = data[index];
        Node node = searchKey(list, key);
        return node == null ? "Not found" : node.value();
    }
}
public class Test {
    public static void main(String[] args) {
        HashTable h = new HashTable(3);
        h.put("a","is a");
        h.put("b","is b");
        h.put("c","is c");
        h.put("d","is d");
        h.put("a","is another a");
        System.out.println(h.get("a"));
        System.out.println(h.get("b"));
        System.out.println(h.get("c"));
        System.out.println(h.get("d"));
        System.out.println(h.get("nothing"));
        Map<Integer, Integer> map = new HashMap<>();
        

    }
}
