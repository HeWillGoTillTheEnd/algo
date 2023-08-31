package doublelinkedlist;

import slinkedlist.List;

import java.util.NoSuchElementException;

public class DlinkedList<E> implements List<E> {

    private Node<E> head; //노드의 첫 부분
    private Node<E> tail; // 노드의 마지막 부분
    private int size; // 요소 개수

    public DlinkedList(){
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    //특정 위치의 노드를 반환하는 메소드
    private Node<E> search(int index){

        //범위 밖(잘못된 위치) 일 경우 예외 던지기
        if(index < 0 || index >= size){
            throw new IndexOutOfBoundsException();
        }

        // 뒤에서부터 검색
        if(index + 1 > size / 2){
            Node<E> x = tail;
            for (int i = size; i < index; i--) {
                x = x.prev;
            }
            return x;

        }
        // 앞에서부터 검색
        else {
            Node<E> x = head;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
            return x;
        }
    }
    public void addFirst(E value){
        Node<E> newNode = new Node<>(value); // 새 노드 생성
        newNode.next = head; // 새 노드의 다음 노드로 head 노드를 연결

        /**
         * head가 null이 아닐 경우에만 기존 head노드의 prev 변수가
         * 새 노드를 가리키도록 한다
         * 이유는 기존 head노드가 없는 경우(null)는 데이터가
         * 아무 것도 없던 상태였으므로 head.prev를 하면 잘못된 참조가 된다.
         */
        if(head != null){
            head.prev = newNode;
        }

        head = newNode;
        size++;

        /**
         * 다음에 가리킬 노드가 없는 경우(= 데이터가 새 노드밖에 없는 경우)
         * 데이터가 한 개( 새 노드) 밖에 없으므로 새 노드는 처음 시작노드이자
         * 마지막 노드다. 즉 tail = head다.
         */

        if(head.next ==null){
            tail = head;
        }
    }
    public void addLast(E value){
        Node<E> newNode = new Node<E>(value);

        if(size == 0){
            addFirst(value);
            return;
        }

        /**
         * 마지막 노드(tail)의 다음 노드(next)가 새 노드를 가리키도록 하고
         * tail이 가리키는 노드를 새 노드로 바꿔준다.
         */
        tail.next = newNode;
        newNode.prev = tail;
        tail = newNode;
        size++;
    }

    @Override
    public boolean add(E value) {
        addLast(value);
        return true;
    }

    @Override
    public void add(int index, E value) {
        // 잘못된 인덱스를 참조할 경우 예외 발생
        if(index > size || index < 0){
            throw new IndexOutOfBoundsException();
        }
        //추가하려는 index가 가장 앞에 추가하려는 경우 addFirst를 호출
        if(index ==0){
            addFirst(value);
            return;
        }
        //추가하려는 index가 마지막 위치일 경우 addLast 호출
        if(index == size){
            addLast(value);
            return;
        }
        // 추가하려는 위치의 이전 노드
        Node<E> prev_Node = search(index - 1);

        // 추가하려는 위치의 노드
        Node<E> next_Node = prev_Node.next;
        //추가하려는 노드
        Node<E> newNode = new Node<E>(value);

        //링크 끊기
        prev_Node.next = null;
        next_Node.prev = null;

        //링크 연결하기
        prev_Node.next = newNode;

        newNode.prev = prev_Node;
        newNode.next = next_Node;

        next_Node.prev = newNode;
        size++;
    }

    public E remove(){
        Node<E> headNode = head;

        if(headNode == null){
            throw new NoSuchElementException();
        }
        // 삭제된 노드를 반환하기 위한 임시 변수
        E element = headNode.data;

        // head의 다음 노드
        Node<E> nextNode = head.next;

        head.data = null;
        head.next = null;

        /**
         * head의 다음노드(=nextNode)가 null이 아닐 경우에만
         * prev 변수를 null로 업데이트 해주어야 한다
         * 이유는 nextNode가 없는 경우(null)는 데이터가
         * 아무것도 없던 상태였으므로 nextNode.prev를 하면 잘못된 참조가 된다.
         */
        if(nextNode != null){
            nextNode.prev = null;
        }

        head = nextNode;
        size--;

        /**
         * 삭제된 요소가 리스트의 유일한 요소였을 경우
         * 그 요소는 head이자 tail이었으므로
         * 삭제되면서 tail도 가리킬 요소가 없기 떄문에
         * size가 0일경우 tail도 null로 변환
         */
        if(size ==0){
            tail = null;
        }
        return element;
    }

    @Override
    public E remove(int index) {

        if(index >= size || index < 0){
            throw new IndexOutOfBoundsException();
        }
        // 삭제하려는 노드가 첫 번째 노드일 경우
        if(index == 0){
            E element = head.data;
            remove();
            return element;
        }

        Node<E> prevNode = search(index - 1); // 삭제할 노드의 이전 노드
        Node<E> removedNode = prevNode.next; // 삭제할 노드
        Node<E> nextNode = removedNode.next; // 삭제할 노드의 다음 노드

        E element = removedNode.data; //삭제되는 노드의 데이터를 반환하기 위한 임시 변수

        /**
         * index == 0 일 때의 조건에서 이미 head노드의 삭제에 대한 분기가 있기 때문에
         * prevNode는 항상 존재한다.
         *
         * 그러나 nextNode의 경우는 null일 수 있기 떄문에( = 마지막 노드를 삭제하려는 경우)
         * 이전처럼 반드시 검사를 해준 뒤, nextNode.prev에 접근해야 한다.
         */
        prevNode.next = null;
        removedNode.next = null;
        removedNode.prev = null;
        removedNode.data = null;

        if(nextNode != null){
            nextNode.prev = null;

            nextNode.prev = prevNode;
            prevNode.next = nextNode;
        }

        /**
         * nextNode가 null이라는 것은 마지막 노드를 삭제했다는 의미이므로
         * prevNode가 tail이 된다. (연결 해줄 것이 없음)
         */
        else {
            tail = prevNode;
        }
        size--;

        return element;
    }
    @Override
    public boolean remove(Object value) {
        Node<E> prevNode = head;
        Node<E> x = head; //removedNode

        //value 와 일치하는 노드를 찾는다.
        for(; x!= null; x = x.next){
            if(value.equals(x.data)){
                break;
            }
            prevNode = x;
        }
        // 일치하는 요소가 없을 경우 false 반환
        if(x == null){
            return false;
        }

        // 삭제하려는 노드가 head일 경우 remove()로 삭제
        if(x.equals(head)){
            remove();
            return true;

        }
        //remove(int index)와 같으 ㄴ메커니즘으로 삭제
        else {
            Node<E> nextNode = x.next;

            prevNode.next = null;
            x.data = null;
            x.next = null;
            x.prev = null;

            if(nextNode != null){
                nextNode.prev = null;

                nextNode.prev = prevNode;
                prevNode.next = nextNode;
            } else
            {
                tail = prevNode;
            }
            size--;
            return true;
        }
    }

    @Override
    public E get(int index) {
        return search(index).data;
    }

    @Override
    public boolean contains(Object value) {
        return indexOf(value) >= 0;
    }

    @Override
    public void set(int index, E value) {
        Node<E> replaceNode = search(index);
        replaceNode.data = null;
        replaceNode.data = value;
    }

    @Override
    public int indexOf(Object o) {
        int index = 0;

        for (Node<E> x = head; x != null; x = x.next) {
             if(o.equals(x.data)){
                 return index;
             }
            index++;
        }
        return -1;
    }

    public int lastIndexOf(Object o) {
        int index = size;

        for (Node<E> x = tail; x != null; x = x.prev) {
             index--;
             if(o.equals(x.data)){
                 return index;
             }
        }
        return -1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {

    }
}
