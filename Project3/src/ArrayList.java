public class ArrayList<T extends Comparable<T>> implements List<T> {
    private T[] arr;
    private boolean isSorted;
    private int nextEmpty;

    /*
    INBOX:
    - Take care of updating isSorted
    - Consider making a new function to grow the full array and copy from old.
     */

    // constructor for new ArrayList: defaults to size 2 & makes isSorted true
    public ArrayList() {
        arr = (T[]) new Comparable[2];
        isSorted = true;
        nextEmpty = 0;
    }

    // resizes the array so that it's double the size of the original
    private void resizeArr() {
        T[] temp = (T[]) new Comparable[this.arr.length * 2];
        for (int i = 0; i < nextEmpty; i++) {
            // loops thru old arr and copies values to new
            temp[i] = this.arr[i];
        }
        this.arr = temp;
    }

    // adds element to the end of the array & resizes if needed
    public boolean add(T element) {
        if (element == null) {
            return false;
        } else if (nextEmpty >= arr.length) {
            // doubles size of arr if it's too full to add another element
            resizeArr();
        }
        arr[nextEmpty] = element;
        nextEmpty++;
        isSorted = isSortedHelper();
        return true;
    }

    // adds element to specific index of array
    public boolean add(int index, T element) {
        if (element == null || index < 0 || index >= nextEmpty) {
            return false;
        } else if (nextEmpty >= arr.length) {
            // if it's already full -- resize the array
            resizeArr();
        }
        for (int i = nextEmpty; i > index; i--) {
            // loops through and shifts the elements
            arr[i] = arr[i-1];
        }
        // replaces ele at index to new element
        arr[index] = element;
        nextEmpty++;
        isSorted = isSortedHelper();
        return true;
    }

    // makes the whole array null
    public void clear() {
        // loops through part with elements & makes them null
        arr = (T[]) new Comparable[2];
        isSorted = true;
        nextEmpty = 0;
    }

    // returns the element at an index
    public T get(int index) {
        if (index >= arr.length || index < 0) {
            return null;
        } else {
            return arr[index];
        }
    }

    // returns the first index of an element in an array -- -1 if it doesn't exist
    public int indexOf(T element) {
        if (element != null) {
            for (int i = 0; i < nextEmpty; i++) {
                // loops through the elements
                if (element.equals(arr[i]))
                    // returns index if the value matches the element
                    return i;
                if (isSorted && arr[i].compareTo(element) > 0)
                // breaks for loop if it's sorted and the value is already greater than the element
                    break;
            }
        }
        return -1;
    }

    // checks if the array is empty
    public boolean isEmpty() {
        // if the nextEmpty spot is the first index, then it should be considered empty
        return nextEmpty == 0;
    }

    // returns num of elements in the array
    public int size() {
        int counter = 0;
        for (int i = 0; i < arr.length; i++) {
            // loops through array
            if (arr[i] == null)
                // breaks loop if the element before the end of the array is null
                break;
            // otherwise increment the counter
            counter++;
        }
        return counter;
    }

    // sorts array if it's not already sorted using insertion
    public void sort() {
        if (!isSorted) {
            int i, j;
            T temp;
            for (i = 1; i < nextEmpty; i++) {
                // loops through data and assigns i to a temp var
                temp = arr[i];
                for (j = i - 1; j >= 0 && temp.compareTo(arr[j]) < 0; j--) {
                    // goes through sorted part to see where to insert temp
                    arr[j + 1] = arr[j];
                }
                arr[j + 1] = temp;
            }
        }
        isSorted = true;
    }

    // removes the elements at a specific index and shifts elements over if needed -- returns value at the index
    public T remove(int index) {
        // calls get(index) to get value
        T temp = get(index);
        if (temp == null) {
            return null;
        } else if (index == nextEmpty - 1) {
            arr[index] = null;
            nextEmpty--;
        } else {
            for (int i = index; i < nextEmpty; i++) {
                arr[i] = arr[i + 1];
            }
            nextEmpty--;
        }
        isSorted = isSortedHelper();
        return temp;
    }

    // removes all elements that aren't equal to the one passed in
    public void equalTo(T element) {
        if (element != null) {
            for (int i = 0; i < nextEmpty; i++) {
                // loops through all elements
                if (arr[i].compareTo(element) != 0) {
                    // removes if not the same
                    remove(i);
                    i--;
                }
                if (i != -1 && isSorted && arr[i].compareTo(element) > 0) {
                    // if greater than element -- set rest to null & break
                    for (int j = i; j < nextEmpty; j++) {
                        arr[j] = null;
                    }
                    nextEmpty = size();
                    break;
                }
            }
            isSorted = isSortedHelper();
        }
    }

    // puts elements in the array in reverse order -- keeping nulls at the end
    public void reverse() {
        T temp;
        for (int i = 0; i < nextEmpty/2; i++) {
            // loops through first half & swaps
            temp = arr[i];
            arr[i] = arr[nextEmpty-1 - i];
            arr[nextEmpty-1 - i] = temp;
        }
        isSorted = isSortedHelper();
    }


    // merges to Lists & sorts them
    public void merge(List<T> otherList) {
        if (!otherList.isEmpty()) {
            ArrayList<T> other = (ArrayList<T>) otherList;
            sort();
            other.sort();

            // new Array size of the num of elements in both arrays
            T[] newArr = (T[]) new Comparable[nextEmpty + other.nextEmpty];

            int currInd = 0, thisInd = 0, otherInd = 0;

            // loops while both arrays have elements not added to new array
            while (thisInd < nextEmpty && otherInd < other.nextEmpty) {
                // adds lower value first
                if (arr[thisInd].compareTo(other.arr[otherInd]) <= 0)
                    newArr[currInd++] = arr[thisInd++];
                else
                    newArr[currInd++] = other.arr[otherInd++];
            }

            // adds remaining elements
            while (thisInd < nextEmpty) {
                newArr[currInd++] = arr[thisInd++];
            }

            while (otherInd < other.nextEmpty) {
                newArr[currInd++] = other.arr[otherInd++];
            }

            arr = newArr;
            isSorted = true;

        }
    }

    // rotates all the elements by n
    public boolean rotate(int n) {
        if (!(n <= 0 || nextEmpty <= 1)) {
            T temp;
            for (int j = 0; j < n % arr.length; j++) {
                temp = arr[nextEmpty - 1];
                // moves all the elements one position at a time
                for (int i = nextEmpty - 1; i > 0; i--) {
                    arr[i] = arr[i - 1];
                }
                arr[0] = temp;
            }
            isSorted = isSortedHelper();
            return true;
        }
        return false;
    }

    // returns String of all the elements in the array in its own line
    public String toString() {
        String out = "";
        for (int i = 0; i < nextEmpty; i++) {
            out += arr[i].toString() + "\n";
        }
        return out;
    }

    private boolean isSortedHelper() {
        for (int i = 1; i < nextEmpty; i++) {
            // starts looping at 1 bc there's at least one element by this point & we're gonna compare to the previous ele
            if (arr[i - 1].compareTo(arr[i]) > 0) {
                // isSorted becomes false if the previous element is greater
                return false;
            }
        }
        return true;
    }

    // returns current value of isSorted
    public boolean isSorted() {
        return isSorted;
    }
}
