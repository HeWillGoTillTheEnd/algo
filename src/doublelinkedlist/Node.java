package doublelinkedlist;

public class Node<E> {

    E data;
    Node<E> next; // 다음 노드객체를 가리키는 레퍼런스 변수
    Node<E> prev; // 이전 논드 객체를 가리키는 레퍼런스 변수

    Node(E data){
        this.data = data;
        this.prev = null;
        this.prev = null;
    }
}
