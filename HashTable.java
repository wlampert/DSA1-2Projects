package hash;

/**
 * Hash Table implementation.
 */
public class HashTable<K, V> implements Map<K, V> {

    private static final int INITIAL_CAPACITY = 100;
    private int size = 0;
    private int capacity = INITIAL_CAPACITY;
    private HashNode<K, V>[] array;
    public int numCollisions = 0;

    /* Max allowed load factor */
    private double maxLoad = 0.75;

    /* Sentinel value for removes */
    private HashNode<K, V> sentinel = new HashNode<K, V>(null, null);

    public HashTable() {
        this(INITIAL_CAPACITY);
    }

    public HashTable(int initialCapacity) {
        //make underling array of size initialCapacity
        array = new HashNode[initialCapacity];

        //TODO: Write this method
    }

    @Override
    public void insert(K key, V value) {
        //First need to check if array is full
        double load = (double) size / (double) capacity;
        if (load >= maxLoad)
            this.resize();

        //Now can insert the value into the array
        HashNode<K, V> newNode = new HashNode<>(key, value);

        int hash = Math.abs(key.hashCode()%capacity);
        if (array[hash] == null || array[hash].getValue() == null || array[hash].equals(newNode)) {
            if (array[hash] != null && array[hash].getKey().equals(key)) {
                array[hash] = newNode;
                return;
            }
            else
                array[hash] = newNode;

        }
        //Deals with collision
        else {
            int i = hash;
            numCollisions++;
            while (array[i] != null && array[i].getValue() != null) {
                if (i + 1 == capacity) {
                    i = 0;
                } else {
                    i++;
                }
                if(array[i] != null && array[i].getKey().equals(key)){
                    array[i] = newNode;
                    return;
                }
            }
            array[i] = newNode;
        }
        this.size++;
        //TODO: Write this method
    }

    @Override
    public V retrieve(K key) {
        int hash = Math.abs(key.hashCode()%capacity);
        if (!this.contains(key)) {
            return null;
        }


        if (array[hash].getKey().equals(key)) {
            return array[hash].getValue();
        } else {
            int i = hash;
            while (array[i] != null && !array[i].getKey().equals(key)) {
                if (i + 1 == capacity) {
                    i = 0;
                } else {
                    i++;
                }
            }
            return array[i].getValue();
        }

        //TODO: Write this method
    }

    @Override
    public boolean contains(K key) {
        int hash = Math.abs(key.hashCode()%capacity);
        if(array[hash] != null && array[hash].getValue() == null)
            return false;

        if (array[hash] != null && array[hash].getKey().equals(key)) {
            return true;
        } else {
            int i = hash;
            while (array[i] != null && !array[i].getKey().equals(key)) {
                if (i + 1 == capacity) {
                    i = 0;
                } else {
                    i++;
                }
                if (array[i] != null && array[i].getKey().equals(key)) {
                    return true;
                }
            }
        }
        return false;
        //TODO: Write this method
    }

    @Override
    public void remove(K key) {
        if(!this.contains(key))
            return;

        int hash = Math.abs(key.hashCode()%capacity);
        if (array[hash] != null && array[hash].getKey().equals(key)) {
            array[hash] = sentinel;
            return;
        } else {
            int i = hash;
            while (array[i] != null && !array[i].getKey().equals(key)) {
                if (i + 1 == capacity) {
                    i = 0;
                } else {
                    i++;
                }
                if (array[i].getKey().equals(key)) {
                    array[i] = sentinel;
                    return;
                }
            }
        }


        //TODO: Write this method
    }

    private void resize() {
        //make new array of size n*2, copy over old values, but need to rehash them to find new place in array
        HashNode<K, V>[] tempArray = array;
        array = new HashNode[capacity * 2];
        capacity *=2;
        for (HashNode<K, V> node : tempArray) {
            if(node != null) {
                insert(node.getKey(), node.getValue());
            }
        }
        //TODO: Write this method
    }

    /**
     * Getting and setting the maxLoad field
     *
     * @return
     */
    public double getMaxLoad() {
        return maxLoad;
    }

    public void setMaxLoad(double maxLoad) {
        this.maxLoad = maxLoad;
    }


}
