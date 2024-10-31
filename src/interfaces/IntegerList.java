package interfaces;

public interface IntegerList {

    enum SORT {
        BUBBLE, SELECT, INSERT
    }

    enum DELETE_MODE {
        ID, VALUE
    }

    int MAX_STRING_LIST_SIZE = 1000;

    void sort(IntegerList.SORT sortMode);
    Integer add(Integer item);
    Integer add(int index, Integer item);
    Integer set(int index, Integer item);
    Integer remove(Integer index, IntegerList.DELETE_MODE deleteMode);
    boolean contains(Integer item);
    int indexOf(Integer item);
    int lastIndexOf(Integer item);
    Integer get(int index);
    boolean equals(IntegerList otherList);
    int size();
    boolean isEmpty();
    void clear();
    Integer[] toArray();
}
