import java.util.*; 


public class Main 
{
   /**
     * Fonction principale
     */
   public static void main(String[] args)
   {
      // Creer un monceau avec 22 éléments et un tableau équivalent
      int numItems = 22;
      BinaryHeap<Integer> heap = new BinaryHeap<Integer>(true);
      
      Integer [ ] items = new Integer[ numItems ];

      int i;
      int j;

      // En insérant les éléments un a un
      for( i = 11, j = 0; j != numItems; i = ( i + 37 ), j++ )
      {
	  heap.offer( i );
	  items[ j ] = i;

	  i %=  numItems; 
      }
      // Creation d'un heap pour tester iterator
      BinaryHeap<Integer> testHeapIterator = new BinaryHeap<Integer>(items, true);

      // en construisant le monceau depuis le depart
      System.out.println("Monceau min contruit element par element:");
      System.out.println( heap.printFancyTree() );

      heap = new BinaryHeap<Integer>(false);
      // en inserant les elements un a un
      for( Integer item : items)
	  heap.offer( item );

      // en construisant le monceau depuis le depart
      System.out.println("Monceau max contruit element par element:");
      System.out.println( heap.printFancyTree() );

      heap = new BinaryHeap<Integer>(items,false);
      System.out.println("Monceau max contruit a partir d'un tableau:");
      System.out.println( heap.printFancyTree() );

      heap = new BinaryHeap<Integer>(items,true);
      System.out.println("Monceau min contruit a partir d'un tableau:");
      System.out.println( heap.printFancyTree() );

      System.out.println();
      System.out.println("Affichage recursif:");
      System.out.println( heap.printFancyTree() );

      System.out.println("Affichage non recursif:");
      System.out.println( heap.nonRecursivePrintFancyTree() );

      System.out.println();
      System.out.println("Tableau d'origine:");
      System.out.println( printArray( items ) );
      
      BinaryHeap.heapSort( items );
      System.out.println("Tableau ordonne:");
      System.out.println( printArray( items ) );

      BinaryHeap.heapSortReverse( items );
      System.out.println("Tableau inversement ordonne:");
      System.out.println( printArray( items ) );





      //Ajouter appels pour repondre a la question
      // Test du bon fonctionnement de poll
      System.out.println("\nPoll all objects (should be in order)");
      String poll = null;
      for (int d = 0 ; d < 23 ; d++){
         poll +=  heap.poll()+" ";
      }
      System.out.println(poll.substring(4));


      //Test concordance BinaryHeap et PriorityQueue
      System.out.println("Concordance BinaryHeap et PriorityQueue"+ "\nTest de Offer()");
      PriorityQueue <Integer> PriorityQueueContainer = new PriorityQueue<Integer>();
      BinaryHeap<Integer> binaryHeapContainer = new BinaryHeap<Integer>(true);
      // en inserant les elements un a un
      for( Integer item : items) {
         PriorityQueueContainer.offer(item);
         binaryHeapContainer.offer(item);
      }
      System.out.println("BinaryHeap: " +printArray(binaryHeapContainer.toArray()) +
              "\nPriorityQueue: " +printArray(PriorityQueueContainer.toArray()) + "\nTest de poll()");
      binaryHeapContainer.poll();
      PriorityQueueContainer.poll();
      System.out.println("BinaryHeap: " +printArray(binaryHeapContainer.toArray()) +
                      "\nPriorityQueue: " +printArray(PriorityQueueContainer.toArray()) + "\nTest de peek()");
      System.out.println("Print premier element BinaryHeap: " + binaryHeapContainer.peek());
      System.out.println("Print premier element PriorityQueue: " + PriorityQueueContainer.peek());
      System.out.println("BinaryHeap Array: " +printArray(binaryHeapContainer.toArray()) +
                      "\nPriorityQueue Array: " +printArray(PriorityQueueContainer.toArray()));





      //Test du bon fonctionnement l'iterator

      System.out.println("\nTest d'iteration de tout les elements sans modification" +
      "\nAffichage attenedu (array se trouvant dans le heap): \n" + printArray(testHeapIterator.toArray()));
      String printMessage = "";
      Iterator<Integer> it = testHeapIterator.iterator();
      for (int d = 0 ; d < 23 ; d++){
         printMessage += it.next() + ", " ;
      }
      System.out.println("Affichage du array a l'aide du iterator: \n" +printMessage);



      System.out.println("\nTest d'iteration de tout les elements avec modification\n" +
              "Affichage des element avant modification du heap");
      Iterator<Integer> itTest2 = testHeapIterator.iterator();
      System.out.println("Premier element: " + itTest2.next() +
              "\nModification au heap(poll), verification si iterateur fonctionne encore");


      testHeapIterator.poll();
      itTest2.next();





   }

   private static <AnyType> String printArray(AnyType[] a)
   {
      String outputString = "";

      for(AnyType item : a)
      {
         outputString += item;
         outputString += ", ";
      }

      return outputString.substring(0,outputString.length()-2);
   }
}
