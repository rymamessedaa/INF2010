import java.util.*;


public class BinaryHeap<AnyType extends Comparable<? super AnyType>> extends AbstractQueue<AnyType>
{
    private static final int DEFAULT_CAPACITY = 100;
    private int currentSize;      // Nombre d'elements
    private AnyType [ ] array;    // Tableau contenant les donnees (premier element a l'indice 1)
    private boolean min;
    private int modifications;    // Nombre de modifications apportees a ce monceau

    @SuppressWarnings("unchecked")
    public BinaryHeap( boolean min ){
        this.min = min;
        currentSize = 0;
        array = (AnyType[]) new Comparable[ DEFAULT_CAPACITY + 1];
    }

    @SuppressWarnings("unchecked")
    public BinaryHeap( AnyType[] items, boolean min ){
        this.min = min;
        // COMPLETEZ
        // invoquez buildMinHeap() ou buildMaxHeap() en fonction du parametre min;
        currentSize = items.length;
        array = (AnyType[]) new Comparable[ currentSize +1 ];
        int i = 1;
        for( AnyType item : items )
            array[ i++ ] = item;

        if(min == true)
            buildMinHeap( );  
        else
            buildMaxHeap();


    }

    public boolean offer( AnyType x ){

        if (x == null)
            throw new NullPointerException("Cannot insert null in a BinaryHeap");

        if( currentSize + 1 == array.length )
            doubleArray();

        // COMPLETEZ
        int hole = ++currentSize;
        //minheap
        if(min == true){
            for( ; hole > 1 && x.compareTo( array[ hole / 2 ] ) < 0; hole /= 2)
                array[ hole ] = array[ hole / 2 ];
            array[ hole ] = x;
            modifications++;
        }
        //maxheap
        else{
            for( ; hole > 1 && x.compareTo( array[ hole / 2 ] ) > 0; hole /= 2)
                array[ hole ] = array[ hole / 2 ];
            array[ hole ] = x;
            modifications++;
        }
        return true;
    }

    public AnyType peek(){
        if(!isEmpty())
            return array[1];

        return null;
    }



    public AnyType poll(){
        //COMPLETEZ
        AnyType tmp;
        if(array[1] != null) {
            tmp = array[1];
            modifications++;
            array[currentSize++] = null;
            array[1] = array[currentSize-=2];
            percolateDownMinHeap(1, currentSize);
            return  tmp;
        }



        return null;
    }

    public Iterator<AnyType> iterator(){
        return new HeapIterator();
    }

    private void buildMinHeap(){
        //COMPLETEZ
        for(int i = currentSize / 2; i > 0; i--) {
            percolateDownMinHeap(i, currentSize + 1);
            modifications++;
        }
    }

    private void buildMaxHeap(){
        //COMPLETEZ
        for(int i = currentSize / 2; i > 0; i--) {
            percolateDownMaxHeap(i, currentSize + 1);
            modifications++;
        }
    }

    public boolean isEmpty(){
        return currentSize == 0;
    }

    public int size(){
        return currentSize;
    }

    public void clear(){
        currentSize = 0;
        modifications = 0;
        array = (AnyType[]) new Comparable[ DEFAULT_CAPACITY + 1];
    }

    private static int leftChild( int i, boolean heapIndexing ){
        return ( heapIndexing ? 2*i : 2*i+1 );
    }

    private void swapReferences( int index1, int index2 ){
        swapReferences(array, index1, index2);
    }

    private static <AnyType extends Comparable<? super AnyType>>
    void swapReferences( AnyType[] array, int index1, int index2 ){

        AnyType tmp = array[ index1 ];
        array[ index1 ] = array[ index2 ];
        array[ index2 ] = tmp;
    }

    @SuppressWarnings("unchecked")
    private void doubleArray(){
        AnyType [ ] newArray;

        newArray = (AnyType []) new Comparable[ array.length * 2 ];
        for( int i = 0; i < array.length; i++ )
            newArray[ i ] = array[ i ];
        array = newArray;
    }


    /**
     * @param hole    Position a percoler
     * @param size    Indice max du tableau
     */
    private void percolateDownMinHeap( int hole, int size ){
        percolateDownMinHeap(array, hole, size, true);
    }

    /**
     * @param array   Tableau d'element
     * @param hole    Position a percoler
     * @param size    Indice max du tableau
     * @param heapIndexing  True si les elements commencent a l'index 1, false sinon
     */
    private static <AnyType extends Comparable<? super AnyType>>
    void percolateDownMinHeap( AnyType[] array, int hole, int size, boolean heapIndexing )
    {
        //COMPLETEZ
        int child;
        AnyType tmp = array[hole];

        for(; leftChild(hole, heapIndexing) < size; hole = child)
        {
            child = leftChild(hole, heapIndexing);
            if(child != size - 1 && array[child + 1].compareTo(array[child]) < 0)
                child++;
            // percolation pour avoir minheap
            if(array[child].compareTo(tmp) < 0)
                array[hole] = array[child];
            else
                break;
        }
        array[hole] = tmp;
    }

    /**
     * @param hole    Position a percoler
     * @param size    Indice max du tableau
     */
    private void percolateDownMaxHeap( int hole, int size ){
        percolateDownMaxHeap(array, hole, size, true);
    }

    /**
     * @param array         Tableau d'element
     * @param hole          Position a percoler
     * @param size          Indice max du tableau
     * @param heapIndexing  True si les elements commencent a l'index 1, false sinon
     */
    private static <AnyType extends Comparable<? super AnyType>>
    void percolateDownMaxHeap( AnyType[] array, int hole, int size, boolean heapIndexing )
    {
        //COMPLETEZ
        int child;
        AnyType tmp ;
        for(tmp= array[hole]; leftChild(hole, heapIndexing) < size; hole = child)
        {
            child = leftChild(hole, heapIndexing);
            if(child != size - 1 && array[child + 1].compareTo(array[child]) > 0)
                child++;
            // Percolation pour avoir maxHeap
            if(array[child].compareTo(tmp) > 0)
                array[hole] = array[child];
            else
                break;
        }
        array[hole] = tmp;
    }

    public static <AnyType extends Comparable<? super AnyType>>
    void heapSort( AnyType[] a )
    {
        //COMPLETEZ
        for(int i = a.length / 2 - 1; i >= 0; i--)
            percolateDownMaxHeap(a, i, a.length, false);

        for(int i = a.length - 1; i > 0; i--)
        {
            swapReferences(a, 0, i);
            percolateDownMaxHeap(a, 0, i, false);
        }
    }

    public static <AnyType extends Comparable<? super AnyType>>
    void heapSortReverse( AnyType[] a )
    {
        //COMPLETEZ
        for(int i = a.length / 2 - 1; i >= 0; i--)
            percolateDownMinHeap(a, i, a.length, false);

        for(int i = a.length - 1; i > 0; i--)
        {
            swapReferences(a, 0, i);
            percolateDownMinHeap(a, 0, i, false);
        }
    }

    public String nonRecursivePrintFancyTree()
    {
        int firstNode  = 1;
        String outputString = "|__";
        String prefix = "   ";
        int position = 2;
        String numberInsert = "|__";
        boolean stop = true;

        //COMPLETEZ
        if (currentSize >= firstNode)
            outputString += array[firstNode] + "\n";

        while (stop) {

            if (position <= currentSize) {
                outputString += prefix + numberInsert + array[position] + "\n";
                if (position % 2 != 0)
                    prefix +="    ";
                else
                    prefix += "|   ";
                position = 2 * position;

            }else if(position/2+1 == currentSize +1){
                position = position/2 +1 ;
                prefix = prefix.substring(0,prefix.length()-4);
                outputString += prefix + numberInsert + "null"+"\n";
                while ( position %2 != 0 ){
                    position = position / 2;
                    prefix = prefix.substring(0,prefix.length()-4);
                    if (position == 0)
                        break;
                }
                position+=1;
            }else{
                position = position/2 +1 ;
                prefix = prefix.substring(0,prefix.length()-4);
                outputString += prefix + numberInsert + array[position]+"\n";
                while ( position %2 != 0 ){
                    position = position / 2;
                    if (position == 1) {
                        stop = false;
                        break;
                    }else {
                        prefix = prefix.substring(0, prefix.length() - 4);
                    }

                }
                position+=1;
            }
        }
        return outputString;
    }

    public String printFancyTree()
    {
        return printFancyTree(1, "");
    }

    private String printFancyTree( int index, String prefix)
    {
        String outputString = "";

        outputString = prefix + "|__";

        if( index <= currentSize )
        {
            boolean isLeaf = index > currentSize/2;

            outputString += array[ index ] + "\n";

            String _prefix = prefix;

            if( index%2 == 0 )
                _prefix += "|  "; // un | et trois espace
            else
                _prefix += "   " ; // quatre espaces

            if( !isLeaf ) {
                outputString += printFancyTree( 2*index, _prefix);
                outputString += printFancyTree( 2*index + 1, _prefix);
            }
        }
        else
            outputString += "null\n";

        return outputString;
    }

    private class HeapIterator implements Iterator {
        private final int modifAtConstruction = modifications;
        private int index =0;

        public boolean hasNext() {
            //COMPLETEZ
            if(modifAtConstruction == modifications){
                if(index +1 <= currentSize)
                    return true;
                else
                    return false;


            }else{
                throw new NoSuchElementException();
            }


        }

        public Object next() throws NoSuchElementException,
                ConcurrentModificationException,
                UnsupportedOperationException {
            //COMPLETEZ
            if(modifAtConstruction == modifications){
                if (hasNext()){
                    index++;
                    return array[index];
                }else
                    return null;

            }else{
                throw new ConcurrentModificationException();
            }
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
