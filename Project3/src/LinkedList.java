public class LinkedList<T extends Comparable<T>> implements List<T> {
    Node<T> head;
    Node<T> tail;
    boolean isSorted;

    // constructor for new LinkedList: defaults head & tail to null & makes isSorted true
    public LinkedList() {
        head = null;
        tail = null;
        isSorted = true;
    }

    // adds element to the end of the LinkedList
    public boolean add(T element) {
        if (element == null) {
            return false;
        } else if (head == null) { // adding to the beginning
            head = new Node(element);
            tail = head;
        } else { // adding to the end
            tail.setNext(new Node(element));
            tail = tail.getNext();
        }
        isSorted = isSortedHelper();
        return true;
    }

    // adds element to specific index
    public boolean add(int index, T element) {
        if (element == null || index < 0 || index > size()) {
            return false;
        } else if (head == null) { // head is null
            head = new Node(element);
            tail = head;
            isSorted = isSortedHelper();
        } else if (index == 0) { // adding to beginning
            Node<T> temp = head.getNext();
            head.setNext(new Node<T>(element, temp));
            isSorted = isSortedHelper();
            return true;
        } else { // adding anywhere else
            Node<T> ptr = head;
            Node<T> trailer = head;
            int idx = 0; // tracks where we are within the LinkedList
            while (ptr != null) {
                if (idx == index) { // add when the idx are equal
                    Node<T> temp = new Node<T>(element);
                    temp.setNext(ptr);
                    trailer.setNext(temp);
                    isSorted = isSortedHelper();
                    return true;
                }
                trailer = ptr;
                ptr = ptr.getNext();
                idx++;
            }
        }
        return false;
    }


    // clears all elements off the LinkedList
    public void clear() {
        head = null;
        tail = null;
        isSorted = true;
    }

    // returns the element at an index
    public T get(int index) {
        if (index >= size() || index < 0) {
            return null;
        } else {
            int idx = 0;
            Node<T> ptr = head;
            while (ptr != null) {
                if (idx == index) {
                    return ptr.getData();
                }
                ptr = ptr.getNext();
                idx++;
            }
        }
        return null;
    }

    // returns the first index of an element in an array -- -1 if it doesn't exist
    public int indexOf(T element) {
        if (element != null) {
            Node<T> ptr = head;
            int idx = 0;
            while (ptr != null) {
                if (ptr.getData().compareTo(element) == 0)
                    return idx;
                if (isSorted && ptr.getData().compareTo(element) > 0)
                    // if it's sorted and the value is already greater then ele -- then stop looping
                    break;
                ptr = ptr.getNext();
                idx++;
            }
        }
        return -1;
    }

    // checks if the LinkedList is empty
    public boolean isEmpty() {
        // if the head is null, then it should be considered empty
        return head == null;
    }

    // returns num of elements in the LinkedList
    public int size() {
            int count = 0;
            Node<T> ptr = head;
            while (ptr != null) {
                count++;
                ptr = ptr.getNext();
            }
            return count;
    }

    // sorts LinkedList if it's not already sorted using insertion
    public void sort() {
        if (!isSorted) {
            // only sort if not sorted
            if (head == null || head.getNext() == null) {
                isSorted = true;
                return;
            }
            Node<T> ptr = head;
            Node<T> next;
            T temp;
            while (ptr != null) {
                next = ptr.getNext();
                while (next != null) {
                    if (ptr.getData().compareTo(next.getData()) > 0) {
                        temp = ptr.getData();
                        ptr.setData(next.getData());
                        next.setData(temp);
                    }
                    next = next.getNext();
                }
                ptr = ptr.getNext();
            }
            this.isSorted = true;
        }
    }

    // removes the elements at a specific index -- returns value at the index
    public T remove(int index) {
        // calls get(index) to get value
        T temp = get(index);
        if (temp == null) {
            return null;
        } else if (index == 0) { // beginning
            T out = get(index);
            head = head.getNext();
            isSorted = isSortedHelper();
            return out;
        } else { // anywhere else
            int idx = 1;
            Node<T> ptr = head.getNext(), trailer = head;
            T out;
            while (ptr != null) {
                if (idx == index) {
                    out = get(idx);
                    trailer.setNext(ptr.getNext());
                    isSorted = isSortedHelper();
                    return out;
                }
                trailer = ptr;
                ptr = ptr.getNext();
                idx++;
            }
        }
        return null;
    }

    // removes all elements that aren't equal to the one passed in
    public void equalTo(T element) {
        if (element != null) {
            Node<T> ptr = head;
            int idx = 0;
            while (ptr != null) {
                if (!(ptr.getData().compareTo(element) == 0)) {
                    // remove if not equal
                    remove(idx);
                } else if (isSorted && ptr.getData().compareTo(element) > 0) {
                    // if greater than element -- stop looping
                    break;
                } else {
                    idx++;
                }
                ptr = ptr.getNext();
            }
        }
        isSorted = isSortedHelper();
    }

//    puts the elements in the LinkedList in reverse order
    public void reverse() {
        Node<T> ptr = head, front, back = null;
        tail = head;
        while (ptr != null) {
            // reverses pointers backwards
            front = ptr.getNext();
            ptr.setNext(back);
            back = ptr;
            ptr = front;
        }
        head = back;
        isSorted = isSortedHelper();
    }

    public void merge(List<T> otherList) {
        if (!otherList.isEmpty()) {
            sort();
            otherList.sort();
            LinkedList<T> other = (LinkedList<T>) otherList;
            Node<T> ptr = head, ptrOther = other.head;

            // new Array size of the num of elements in both arrays
            LinkedList<T> newLL = new LinkedList<>();

            // loops while both have elements not added to new
            while (ptr != null && ptrOther != null) {
                // adds lower
                if (ptr.getData().compareTo(ptrOther.getData()) <= 0){
                    newLL.add(ptr.getData());
                    ptr = ptr.getNext();
                }
                else {
                    newLL.add(ptrOther.getData());
                    ptrOther = ptrOther.getNext();
                }
            }

            // adds remaining elements

            if (ptr != null) {
                while (ptr.getNext() != null) {
                    newLL.add(ptr.getData());
                    ptr = ptr.getNext();
                }

                newLL.tail = ptr;
            }
            if (ptrOther != null) {
                while (ptrOther != null) {
                    newLL.add(ptrOther.getData());
                    ptrOther = ptrOther.getNext();
                }
                newLL.tail = ptrOther;
            }

            // set head & tail to new
            head = newLL.head;
            tail = newLL.tail;
        }
        isSorted = true;
    }

    // rotates all the elements by n
    public boolean rotate(int n) {
     //System.out.println("Size b4: " + size());
        if (n <= 0 || size() <= 2) {
            return false;
        } else {
            Node<T> ptr = head;
            for (int i = 1; i < size() - n; i++) {
                // finds the nth node from the end
                ptr = ptr.getNext();
            }
            // moves nodes behind the size() - n to the front
            Node<T> temp = ptr.getNext();
            ptr.setNext(null);
            tail.setNext(head);
            head = temp;
            tail = ptr;
            isSorted = isSortedHelper();
            //System.out.println("Size after: " + size());
            return true;
        }
    }




    // returns String of all the elements in the LinkedList in its own line
    public String toString() {
        String out = "";
        Node<T> ptr = head;
        while (ptr != null) {
            out += ptr.toString();
            ptr = ptr.getNext();
        }
        return out;
    }

    private boolean isSortedHelper() {
        if (head == null || head.getNext() == null) {
            return true;
        } else {
            Node<T> ptr = head;
            while (ptr.getNext() != null) {
                // if current element greater than next == LinkedList is not sorted.
                if (ptr.getData().compareTo(ptr.getNext().getData()) > 0) {
                    return false;
                }
                ptr = ptr.getNext();
            }
        }
        return true;
    }


    // returns current value of isSorted
    public boolean isSorted() {
        return isSorted;
    }
}
