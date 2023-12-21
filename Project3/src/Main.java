public class Main {
    public static int nextEmpty = 6;
    public static void main(String[] args) {

        LinkedList<Integer> temp = new LinkedList<>();

        for(int i = 0; i < 10; i++)
            temp.add(i);

        System.out.println(temp);
//        temp.equalTo(11);
//        System.out.println(temp);
        System.out.println(temp.size());
        temp.clear();
        System.out.println(temp);
        System.out.println(temp.size());
    }

    public static boolean sortBoo(int[] arr) {
        boolean isSorted = true;
        for (int i = 1; i < nextEmpty; i++) {
            // starts looping at 1 bc there's at least one element by this point & we're gonna compare to the previous ele
            if (arr[i - 1] > arr[i]) {
                // isSorted becomes false if the previous element is greater
                isSorted = false;
                // ends the for loop early if isSorted becomes false
                break;
            }
        }
        return isSorted;
    }
}